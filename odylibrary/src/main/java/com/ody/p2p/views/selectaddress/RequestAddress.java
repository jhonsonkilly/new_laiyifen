package com.ody.p2p.views.selectaddress;

import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class RequestAddress  {

    private selectAddressPopupWindowView mView;
    public RequestAddress(selectAddressPopupWindowView view){
        mView = view;
    }
    private List<RequestAddressBean.Data> address;

    public void  getAddressProvince() {

        OkHttpManager.getAsyn(Constants.ADDRESS + "100000", new OkHttpManager.ResultCallback<RequestAddressBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(RequestAddressBean response) {
                mView.showProvince(response);
            }
        });
    }

    //获得市地址

    public void getCityAddress(String code){

        OkHttpManager.getAsyn(Constants.ADDRESS + code, new OkHttpManager.ResultCallback<RequestAddressBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }
            @Override
            public void onResponse(RequestAddressBean response) {
              mView.showCity(response);
            }
        });
    }

    //获得区地址

    public void getRegionAddress(String code){

        OkHttpManager.getAsyn(Constants.ADDRESS + code, new OkHttpManager.ResultCallback<RequestAddressBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }
            @Override
            public void onResponse(RequestAddressBean response) {
                mView.showRegion(response);
            }
        });
    }



}
