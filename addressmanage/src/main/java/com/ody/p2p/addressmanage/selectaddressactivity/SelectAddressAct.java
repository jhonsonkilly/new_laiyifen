package com.ody.p2p.addressmanage.selectaddressactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.ProgressDialog.ProductChangeDialog;


/**
 * Created by ody on 2016/6/17.
 */
public class SelectAddressAct extends BaseActivity implements SelectAddressView,View.OnClickListener{

    private SelectAddressPresenterimpl presenter;
    private RecyclerView rv_select;
    public Button btn_select_address;
    private ImageView iv_back;
    private TextView tv_title,tv_right;

    public SelectAddressAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void initPresenter() {
        presenter = new SelectAddressPresenterimpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.addressmanage_activity_select_address;
    }

    @Override
    public void initView(View view) {

        iv_back = (ImageView) view.findViewById(R.id.iv_head_back);
        tv_title = (TextView) view.findViewById(R.id.tv_head_title);
        tv_right = (TextView) view.findViewById(R.id.tv_head_right);

        rv_select = (RecyclerView) view.findViewById(R.id.rv_select_address);
        btn_select_address = (Button) view.findViewById(R.id.btn_select_add_newAddress);
        tv_title.setText(getString(R.string.addressmanage_select_address));
        tv_right.setText(getString(R.string.manage));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.main_title_color));

        mLinearLayoutManager = new LinearLayoutManager(SelectAddressAct.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_select.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        btn_select_address.setOnClickListener(this);
    }


    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    @Override
    public void resume() {
        presenter.getAddressList();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void refreshAddresList(AddressBean list) {
        adapter = new SelectAddressAdapter(SelectAddressAct.this,list.getData());
        adapter.setOnItemClickListener(new SelectAddressAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Bundle bd=new Bundle();
//                bd.putString("addressId",adapter.getDatas().get(position).getId()+"");
//                JumpUtils.ToActivity(JumpUt   ils.CONFIRMORDER,bd);
//                finish();
                presenter.saveAddress(adapter.getDatas().get(position).getId());
            }
        });
        rv_select.setAdapter(adapter);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showStr(msg);
    }

    @Override
    public void finishActivity(ConfirmOrderBean.DataEntity.Errors errors) {
        Bundle db=new Bundle();
        if (null!=errors){
            db.putSerializable("error",errors);
        }
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER,db);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_head_back) {
            finish();
        } else if (i == R.id.tv_head_right) {
            JumpUtils.ToActivity(JumpUtils.ADDRESS_MANAGER);
        } else if (i == R.id.btn_select_add_newAddress) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isFromOrder", true);
            JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS,bundle);
        }

    }
}
