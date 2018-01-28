package com.ody.p2p.login.loginfragment.smslogin;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/7.
 */
public class SmsLoginSecondPresenterImpl implements SmsLoginSecondPresenter {

    private SmsLoginSecondView smsLoginSecondView;

    public SmsLoginSecondPresenterImpl(SmsLoginSecondView smsLoginSecondView){
        this.smsLoginSecondView = smsLoginSecondView;
    }

    @Override
    public void finishRegister(String et_psd_first, final String et_psd_second) {
        boolean checkPsd = smsLoginSecondView.checkPsd(et_psd_first,et_psd_second);
        if (!checkPsd){
            return;
        }
        final String phone = OdyApplication.getValueByKey(Constants.SMS_REGISTER_MOBILE_PHONE,null);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", et_psd_first);
        params.put("confirmPassword", et_psd_second);
        OkHttpManager.postAsyn(Constants.REGISTER_SECOND, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if (code.equals("20") && null != msg) {//注册成功在这!!!!
                    smsLoginSecondView.showToast(msg);
                    login(phone,et_psd_second);
                } else {
                    smsLoginSecondView.showToast(msg == null ? "注册异常" : msg);
                }
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                smsLoginSecondView.showToast(response.message);
                login(phone,et_psd_second);//诸葛的注册成功走的onError!!这块咋整??????
            }
        },params);
    }

    public void login(final String phone, String et_psd_second) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", et_psd_second);
        OkHttpManager.postAsyn(Constants.LOGIN, new OkHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(LoginBean response) {
                OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, phone);
                OdyApplication.putString(Constants.HEAD_PIC_URL, response.data.headPicUrl);
                OdyApplication.putString(Constants.NICK_NAME, response.data.nickname);
                EventMessage message = new EventMessage();
                message.flag = EventMessage.REFRESH_UT;
                EventBus.getDefault().post(message);
                smsLoginSecondView.showToast(response.message);
                JumpUtils.ToActivity(JumpUtils.MAIN);
//                bindGuid();
            }
        },params);
    }
}
