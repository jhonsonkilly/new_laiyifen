package com.ody.p2p;

import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.
 */
public class HomePageImrp implements HomePagePressenter {
    HomePageView mView;


    public HomePageImrp(HomePageView mView) {
        this.mView = mView;
    }

    @Override
    public void getSummary() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.USER_SUMMARY, params, new OkHttpManager.ResultCallback<SummaryBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(SummaryBean response) {
                if (null != response && null != response.getData()) {
                    mView.getSummary(response.getData());
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.USER_INFO, new OkHttpManager.ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mView.hideLoading();
            }

            @Override
            public void onResponse(UserInfoBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData()) {
                    mView.getUserinfo(response.getData());
                }
            }
        }, params);
    }
}
