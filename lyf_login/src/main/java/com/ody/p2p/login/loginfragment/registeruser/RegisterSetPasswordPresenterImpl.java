package com.ody.p2p.login.loginfragment.registeruser;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.login.utils.LoginConstants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/6.
 */
public class RegisterSetPasswordPresenterImpl implements RegisterSetPasswordPresenter {

    private RegisterSetPasswordView registerSecondView;

    public RegisterSetPasswordPresenterImpl(RegisterSetPasswordView registerSecondView) {
        this.registerSecondView = registerSecondView;
    }

    @Override
    public void finishRegister(String et_psd_first, final String et_psd_second, final int registerFlag) {
        boolean checkPsd = registerSecondView.checkPsd(et_psd_first, et_psd_second);
        if (!checkPsd) {
            return;
        }
        final String phone = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, null);

        Map<String, String> params = new HashMap<>();
        final String url;
        url= LoginConstants.UNION_ADD_PSD;
        params.put("password1",et_psd_first);
        params.put("password2",et_psd_second);
        params.put("mobile", phone);
//        if (registerSecondView.getAddPsd() != 1) {
//            String SHARE_CODE = OdyApplication.getValueByKey(Constants.SHARE_CODE, "");
//            if (!StringUtils.isEmpty(SHARE_CODE)) {
//                params.put("inviteCode", SHARE_CODE);
//            } else if (!StringUtils.isEmpty(registerSecondView.getInviterMobile())) {//德升新增 邀请人手机号
//                params.put("inviteMobile", registerSecondView.getInviterMobile());
//            }
//            params.put("mobile", phone);
//            params.put("confirmPassword", et_psd_second);
//            params.put("password", et_psd_first);
//        } else {//德升,添加密码只需要这个参数
//            params.put("password", et_psd_first);
//        }
//
//        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
//        if (!StringUtils.isEmpty(ut)) {
//            params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        }
//        if (registerSecondView.getAddPsd() != 1) {
//            url = Constants.REGISTER_SECOND;
//        } else {
//            url = Constants.UNION_ADD_PSD;
//        }
        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "finishRegister  onError==" + request.toString());
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (url.equals(Constants.REGISTER_SECOND)){
                    if (Integer.valueOf(response.code) == 20) {
                        registerSecondView.showToast(response.message);
                        login(phone, et_psd_second, registerFlag);
                    } else {
                        ToastUtils.showShort(response.message);
                    }
                }else {
                    if (Integer.valueOf(response.code) == 0) {
                        registerSecondView.finishActivity();
                        JumpUtils.ToActivity(JumpUtils.MAIN);
                        if (registerFlag == 1) {
//                            EventMessage msg = new EventMessage();
//                            msg.flag = EventMessage.FIRST_LOGIN;
//                            EventBus.getDefault().post(msg);
                        }
                    }
                    ToastUtils.showShort(response.message);
                }

            }
        }, params);
    }

    public void login(final String phone, String et_psd_second, final int registerFlag) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", et_psd_second);

        OkHttpManager.postAsyn(Constants.LOGIN, new OkHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showShort(request.toString());
            }

            @Override
            public void onResponse(LoginBean response) {
                if (Integer.valueOf(response.code) == 0) {
                    OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                    OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                    OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                    OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, phone);
                    OdyApplication.putString(Constants.HEAD_PIC_URL, response.data.headPicUrl);
                    OdyApplication.putString(Constants.NICK_NAME, response.data.nickname);
                    EventMessage message = new EventMessage();
                    message.flag = EventMessage.REFRESH_UT;
                    EventBus.getDefault().post(message);
                    if (registerFlag == 1) {
//                        EventMessage msg = new EventMessage();
//                        msg.flag = EventMessage.FIRST_LOGIN;
//                        EventBus.getDefault().post(msg);
                    }
                    registerSecondView.showToast(response.message);
                    JumpUtils.ToActivity(JumpUtils.MAIN);
                    bindGuid();
                } else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    private void bindGuid() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("deviceNo", OdyApplication.getGUID());
        params.put("appType", "1");

        OkHttpManager.postAsyn(Constants.BUNDLE_ALIAS, new OkHttpManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                registerSecondView.finishActivity();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String alias = json.optString("data");
                    if (!StringUtils.isEmpty(alias)) {
                        registerSecondView.setAlias(alias);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, params);
    }
}
