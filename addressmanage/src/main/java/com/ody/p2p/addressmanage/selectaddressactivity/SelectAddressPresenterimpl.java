package com.ody.p2p.addressmanage.selectaddressactivity;

import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.ErrorBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/17.
 */
public class SelectAddressPresenterimpl implements SelectAddressPresenter {

    private SelectAddressView mView;
    public SelectAddressPresenterimpl(SelectAddressView view){
        mView = view;
    }

    @Override
    public void getAddressList(){
        Map<String,String> params = new HashMap<>();
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.ADDRESS_LIST, new OkHttpManager.ResultCallback<AddressBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(AddressBean response) {
                mView.refreshAddresList(response);
            }
        }, params);
    }

    @Override
    public void saveAddress(final String addressId) {
        Map<String,String> params=new HashMap<>();
        params.put("receiverId",addressId);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.SAVE_ADDRESS,new OkHttpManager.ResultCallback<ErrorBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ErrorBean response) {
                if(response==null){
                    return;
                }
                if(response.code.equals("0")){
//                    confirmOrderView.saveAddress();
                    OdyApplication.putValueByKey(Constants.ADDRESS_ID,addressId);
                    mView.finishActivity(null);
                }else if(response!=null&&response.code.equals("10200002")){
                    if(response.getData()!=null&&response.getData().getError()!=null){
                        mView.finishActivity(response.getData().getError());
                    }
                }
            }
        },params);
    }

    @Override
    public void setDefault(String id, String userName,String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("provinceCode", provinceCode);
        params.put("cityId", "" + cityId);
        params.put("regionId", regionId + "");
        params.put("detailAddress", detailAddress);
        params.put("mobile", telNum);
        params.put("defaultIs", isDefault + "");
        params.put("id", id);
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.EDIT_ADDRESS, new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    getAddressList();
                }
            }
        }, params);
    }

    @Override
    public void deleteAddress(int defaultIs, String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("defaultIs", defaultIs + "");
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.DELETE_ADDRESS, new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    getAddressList();
                }
            }
        }, params);
    }
}
