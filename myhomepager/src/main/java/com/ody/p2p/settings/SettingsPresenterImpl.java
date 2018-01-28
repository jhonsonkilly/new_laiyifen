package com.ody.p2p.settings;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/7/2.
 */
public class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsView settingsView;

    public SettingsPresenterImpl(SettingsView settingsView){
        this.settingsView = settingsView;
    }

    @Override
    public void exitAccount() {
        Map<String,String> params = new HashMap<>();
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.EXIT_ACCOUNT, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, null);
                OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
                OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, null);
                settingsView.closePopwindow();
                settingsView.finishActivity();
            }
        }, params);
    }
}
