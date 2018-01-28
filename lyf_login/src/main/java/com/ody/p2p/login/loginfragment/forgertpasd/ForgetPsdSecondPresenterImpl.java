package com.ody.p2p.login.loginfragment.forgertpasd;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.login.R;
import com.ody.p2p.login.utils.LoginConstants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/7.
 */
public class ForgetPsdSecondPresenterImpl implements ForgetPsdSecondPresenter {

    private ForgetPsdSecondView forgetPsdSecondView;

    public ForgetPsdSecondPresenterImpl(ForgetPsdSecondView forgetPsdSecondView) {
        this.forgetPsdSecondView = forgetPsdSecondView;
    }

    @Override
    public void checkPsd(String phone,String smsCode, String newPsd, String confirmPsd) {
        if (!StringUtils.checkPsdLength(newPsd)) {//如果密码长度不对
            forgetPsdSecondView.showToast(forgetPsdSecondView.getClassContext().getString(R.string.password_lenght_6for18));
            return;
        } else if (!newPsd.equals(confirmPsd)) {
            forgetPsdSecondView.showToast(forgetPsdSecondView.getClassContext().getString(R.string.Password_please_enter_again));
        } else {
            modifyPsd(phone,smsCode, newPsd, confirmPsd);
        }
    }

    @Override
    public void checkCaptchas(String phone, String smsCode) {
        Map<String, String> params = new HashMap<>();
        params.put("captchas", smsCode);
        params.put("mobile", phone);
        params.put("captchasType", "5");
        forgetPsdSecondView.showLoading();
        OkHttpManager.postAsyn(LoginConstants.CHECK_CAPTCHAS, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                forgetPsdSecondView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }


            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response.code.equals("0")) {
                    forgetPsdSecondView.finishActivity();
                } else {
                    ToastUtils.showToast(response.message);
                }
            }
        }, params);
    }

    public void modifyPsd(String phone,String smsCode,String newPsd, String confirmPsd) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("smsCode", smsCode);
        params.put("password1", newPsd);
        params.put("password2", confirmPsd);
        params.put("captchasType", "5");
        forgetPsdSecondView.showLoading();
        OkHttpManager.postAsyn(LoginConstants.MODIFY_PSD, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                forgetPsdSecondView.showToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                forgetPsdSecondView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (Integer.valueOf(response.code) == 0) {
                    forgetPsdSecondView.finishActivity();
                }
                ToastUtils.showShort(response.message);
            }
        }, params);
    }
}
