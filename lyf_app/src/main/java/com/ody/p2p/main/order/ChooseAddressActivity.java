package com.ody.p2p.main.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.selectaddressactivity.SelectAddressPresenter;
import com.ody.p2p.addressmanage.selectaddressactivity.SelectAddressPresenterimpl;
import com.ody.p2p.addressmanage.selectaddressactivity.SelectAddressView;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.JumpUtils;

/**
 * Created by ${tang} on 2016/11/30.
 */

public class ChooseAddressActivity extends BaseActivity implements SelectAddressView, View.OnClickListener, ChooseAddressAdapter.ItemClickListener {
    private ImageView iv_back;
    private RecyclerView rv_address;
    private SelectAddressPresenter presenter;
    private ChooseAddressAdapter adapter;
    private TextView tv_add_address;
    private int isHomeCenter=0;

    @Override
    public void initPresenter() {
        presenter=new SelectAddressPresenterimpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_choose_address;
    }

    @Override
    public void initView(View view) {
        isHomeCenter=getIntent().getIntExtra("isHomeCenter",0);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        rv_address= (RecyclerView) findViewById(R.id.rv_address);
        tv_add_address= (TextView) findViewById(R.id.tv_add_address);
        tv_add_address.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_address.setLayoutManager(linearLayoutManager);
        adapter=new ChooseAddressAdapter(presenter,isHomeCenter);
        adapter.setListener(this);
        rv_address.setAdapter(adapter);
        if (!NetworkUtil.isNetworkAvailable(getContext())){
            showFailed(true,1);
        }
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
        if(list!=null&&list.getData()!=null&&list.getData().size()>0){
            adapter.setData(list.getData());
        } else {
            adapter.clear();
        }
    }

    @Override
    public void showToast(String msg) {
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
        if(v.getId()==R.id.tv_add_address){
            Bundle bundle = new Bundle();
            if(isHomeCenter==0){
                bundle.putBoolean("isFromOrder", true);
            }
            JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS,bundle);
        }else if(v.getId()==R.id.iv_back){
            finish();
        }
    }

    @Override
    public void onClickListener(AddressBean.Address address) {
        OdyApplication.putString(Constants.AREA_CODE, address.getRegionCode());
        OdyApplication.putString(Constants.AREA_NAME, address.getRegionName());
        OdyApplication.putString(Constants.PROVINCE, address.getProvinceName());
        OdyApplication.putString(Constants.CITY, address.getCityName());
        OdyApplication.putString(Constants.PROVINCE, address.getProvinceName());
        OdyApplication.putString(Constants.AREA_CODE_ADDRESS, address.getProvinceName()+"\t"+address.getCityName()+"\t"+address.getRegionName()+"\t"+address.getDetailAddress());
        OdyApplication.putValueByKey(Constants.ADDRESS_ID,address.getId());
        presenter.saveAddress(address.getId());
    }


    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())){
            showFailed(true,1);
        }else {
            showFailed(false,1);
            presenter.getAddressList();
        }
    }
}
