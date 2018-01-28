package com.ody.p2p.addressmanage.receiver;

import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.bean.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.
 */
public class ReceiverPresenterImp implements ReceiverPresenter {

    private ReceiverView mView;


    public ReceiverPresenterImp(ReceiverView view) {
        mView = view;
    }

    @Override
    public void getAddressListByNet() {

        Map<String, String> params = new HashMap<>();
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);

        OkHttpManager.postAsyn(Constants.ADDRESS_LIST, new OkHttpManager.ResultCallback<AddressBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onResponse(AddressBean response) {
                mView.refreshAddresList(response);
            }
        }, params);
    }

    @Override
    public void deleteAddressByNet(int defaultIs, String id) {

        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("defaultIs", defaultIs + "");
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);


        OkHttpManager.postAsyn(Constants.DELETE_ADDRESS, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.deleteAddress(response);
            }
        }, params);

    }

    @Override
    public void setDefaultAddressByNet(String id, String userName,String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault) {
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


        OkHttpManager.postAsyn(Constants.EDIT_ADDRESS, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.setDefaultAddress(response);


            }
        }, params);
    }

}
