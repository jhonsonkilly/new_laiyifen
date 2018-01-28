package com.ody.p2p.addressmanage.receiver;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.bean.BaseRequestBean;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/6/13.
 */
public class ReceiveAddressActivity extends BaseActivity implements OnClickListener, ReceiverView {

    private ImageView iv_back;
    private TextView tv_title, tv_right;
    private RecyclerView rv_receiver_address;
    public RvReveiverAdapter adapter;
    public Button btn_add_address;
    private List<AddressBean.Address> mData;
    private LinearLayout lay_null;
    private FrameLayout lay_address_list;
    private ReceiverPresenterImp presenter;
    private TextView btn_address_null_add;
    private LinearLayoutManager mLinearLayoutManager;
    protected ImageView iv_no_address;


    @Override
    public void initPresenter() {
        presenter = new ReceiverPresenterImp(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.addressmanage_activity_receive_address;
    }

    @Override
    public void initView(View view) {

        iv_back = (ImageView) view.findViewById(R.id.iv_head_back);
        tv_title = (TextView) view.findViewById(R.id.tv_head_title);
        tv_right = (TextView) view.findViewById(R.id.tv_head_right);
        rv_receiver_address = (RecyclerView) view.findViewById(R.id.rv_receiver_address);
        btn_add_address = (Button) view.findViewById(R.id.btn_receiver_add_newAddress);
        tv_title.setText(getResources().getText(R.string.receive_address));
        lay_null = (LinearLayout) view.findViewById(R.id.lay_address_null);
        lay_address_list = (FrameLayout) view.findViewById(R.id.lay_address_list);
        btn_address_null_add = (TextView) view.findViewById(R.id.btn_address_null_add);
        iv_no_address = (ImageView) view.findViewById(R.id.iv_no_address);
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        btn_add_address.setOnClickListener(this);
        btn_address_null_add.setOnClickListener(this);
    }


    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        mLinearLayoutManager = new LinearLayoutManager(ReceiveAddressActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_receiver_address.addItemDecoration();
        rv_receiver_address.setLayoutManager(mLinearLayoutManager);
        mData = new ArrayList<>();
        adapter = new RvReveiverAdapter(ReceiveAddressActivity.this, mData, presenter, this);
        rv_receiver_address.setAdapter(adapter);
    }

    @Override
    public void resume() {

        presenter.getAddressListByNet();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_head_back) {
            finish();
        } else if (i == R.id.btn_receiver_add_newAddress) {
            JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS);
        } else if (i == R.id.btn_address_null_add) {
            JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS);
        }

    }

    @Override
    public void refreshAddresList(AddressBean list) {

        Log.e("TAG", "refresh Address");
        if (list.getData() == null || list.getData().size() == 0) {
            lay_null.setVisibility(View.VISIBLE);
            lay_address_list.setVisibility(View.GONE);
        } else if (list.getData().size() > 0) {
            adapter.setDatas(list.getData());
            lay_null.setVisibility(View.GONE);
            lay_address_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showStr(msg);
    }

    @Override
    public void deleteAddress(BaseRequestBean bean) {
        if (bean.code.equals("0")) {
            presenter.getAddressListByNet();
        } else {
            ToastUtils.showStr("failure");
        }
    }

    @Override
    public void setDefaultAddress(BaseRequestBean bean) {
        if (bean.code.equals("0")) {
            presenter.getAddressListByNet();
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
