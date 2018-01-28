package com.ody.p2p.login.loginfragment;


import android.util.Log;

import com.ody.p2p.AliBCBean;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.entity.NewUserBean;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.CheckImgVerificationBean;
import com.ody.p2p.login.Bean.IsRepeatPhoneBean;
import com.ody.p2p.login.Bean.LayRegusterBean;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.utils.LoginConstants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzy on 2016/10/18.
 */
public class LoginFragmentPressenterImpr implements LoginFragmentPressenter {

    LoginFragmentView mView;

    public LoginFragmentPressenterImpr(LoginFragmentView mView) {
        this.mView = mView;
    }

    /**
     * 检测手机是否注册过
     */
    @Override
    public void checkPhoneIsRegistered(final LoginDocument document) {
        String mobile = document.getTelephone();
        boolean checkPhone = StringUtils.checkPhone(mobile);
        if (!checkPhone) {
            return;
        }
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        OkHttpManager.postAsyn(Constants.IS_REPEAT_PHONE, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
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
                mView.hideLoading();
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (-1 == Integer.valueOf(response.code)) {//手机号已经注册
//                    getValidateCode(mobile);
                    document.setMessage(response.message);
                    mView.onTelephoneAlreadyRegistered(document);
                } else if (0 == Integer.valueOf(response.code)) {//手机号还未注册
//                    ToastUtils.showShort(response.message);
//                    JumpUtils.ToActivity(JumpUtils.DSREGISTERFIRST);
                    document.setMessage(response.message);
                    mView.onTelephoneUnregistered(document);
                } else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    /**
     * 获取验证码
     */
    @Override
    public void getValidateCode(final LoginDocument document) {
        String mobile = document.getTelephone();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("checkImageCode", document.getImgVerificationCode());
        params.put("imgeKey", document.getImgVerificationCodeToken());
        params.put("captchasType", document.getCaptchasType() + "");
        OdyApplication.putValueByKey(Constants.FORGET_MOBILE_PHONE, mobile);
        OdyApplication.putValueByKey(Constants.SMS_REGISTER_MOBILE_PHONE, mobile);
        String url = "";
        if (document.getCaptchasType() == 2) {//忘记密码不走来伊份的
            url = Constants.GET_CAPTCHA;
        } else {
            url = LoginConstants.GET_CAPTCHA;
        }
        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
//                ToastUtils.showShort(msg);
                document.setMessage(msg);
                mView.sendVerificationCodeFailed(document);
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                document.setMessage(response.message);
                if (0 == Integer.valueOf(response.code)) {//发送验证码成功
                    //获取验证码成功后,让光标移动到输入验证码处
//                    forgetPsdView.getValidateFocus();
                    mView.sendVerificationCodeSuccessful(document);
                } else {//发送失败
                    mView.sendVerificationCodeFailed(document);
                }
            }
        }, params);
    }

    @Override
    public void quickLogin(final String phone, String psd) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("captchas", psd);
        params.put("deviceId", OdySysEnv.getSessionId());
        OkHttpManager.postAsyn(Constants.LOGIN, new OkHttpManager.ResultCallback<LayRegusterBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "LOGIN  onError===" + request.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(LayRegusterBean response) {
                if (null != response && response.code.equals("0")) {
                    OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                    OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                    OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                    OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, phone);
                    OdyApplication.putString(Constants.HEAD_PIC_URL, response.data.headPicUrl);
                    OdyApplication.putString(Constants.NICK_NAME, response.data.nickname);
                    EventMessage message = new EventMessage();
                    message.flag = EventMessage.REFRESH_UT;
                    getAppKey(phone);
                    EventBus.getDefault().post(message);
                    if (response.isPwd()) {
                        mView.loginSuccess();
                    } else {
                        mView.registerNext();
                    }
                } else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    /**
     * 注册验证下一步
     *
     * @param mobile
     * @param captchas
     */
    @Override
    public void toNext(final String mobile, String captchas) {

        boolean checkPhone = StringUtils.checkPhone(mobile);
        if (!checkPhone) {
            return;
        }
        boolean checkValidatCode = StringUtils.checkValidateCode(captchas);
        if (!checkValidatCode) {
            return;
        }
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("captchas", captchas);
        OkHttpManager.postAsyn(Constants.CHECK_CAPTCHA, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {

                ToastUtils.showShort(request.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (Integer.valueOf(response.code) == 0) {
                    OdyApplication.putValueByKey(Constants.REGISTER_MOBILE_PHONE, mobile);//电话号码放进去,注册页面二要用
                    ToastUtils.showShort(response.message);
                    mView.registerNext();
                } else {
                    ToastUtils.showShort(response.message);
                }
                getAppKey(mobile);
            }
        }, params);
    }

    @Override
    public void Register(final String phone, final String captchas) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("captchas", captchas);
        OkHttpManager.postAsyn(Constants.REGISTER_SECOND, new OkHttpManager.ResultCallback<LayRegusterBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "finishRegister  onError==" + request.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(LayRegusterBean response) {
                if (Integer.valueOf(response.code) == 20) {
                    OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                    OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                    OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, phone);
                    OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                    EventMessage message = new EventMessage();
                    message.flag = EventMessage.REFRESH_UT;
                    EventBus.getDefault().post(message);
                    if (!response.isPwd()) {//需要跳转至设置密码界面
                        mView.registerNext();
                    } else {
                        JumpUtils.ToActivity(JumpUtils.MAIN);
                    }
                }
                ToastUtils.showShort(response.message);
            }
        }, params);
    }


    /**
     * 绑定手机号
     *
     * @param mobile
     * @param captchas
     */
    @Override
    public void bindPhone(final String mobile, String captchas, String ut) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("captchas", captchas);
        params.put("ut", ut);
        OkHttpManager.postAsyn(LoginConstants.BIND_MOBILE, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "finishRegister  onError==" + request.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (Integer.valueOf(response.code) == 0) {
                    if (response.data.isResult()) {
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.data.getUt());
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);

                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, mobile);
                        EventMessage message = new EventMessage();
                        message.flag = EventMessage.REFRESH_UT;
                        EventBus.getDefault().post(message);
                        JumpUtils.ToActivity(JumpUtils.MAIN);
                        ToastUtils.showShort(response.message);
                    } else {
                        ToastUtils.showShort("该手机号已绑定第三方账号");
                    }
                } else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    @Override
    public void isNewUser() {
        Map<String, String> map = new HashMap<String, String>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        map.put("ut", ut);
        map.put("userExtKeysStr", "newuser");
        OkHttpManager.postAsyn(Constants.IS_NEW_USER, new OkHttpManager.ResultCallback<NewUserBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(NewUserBean response) {
                if (response != null) {
                    if (response != null && response.data != null && response.data.userExtMap != null) {
                        mView.isNewUser(response.data.userExtMap.newuser);
                    }
                }
            }
        }, map);
    }

    @Override
    public void checkImgVerificationAvailable(final LoginDocument document) {
        String mobile = document.getTelephone();
        boolean checkPhone = StringUtils.checkPhone(mobile);
        if (!checkPhone) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("width", "160");
        map.put("height", "80");
        map.put("initType", "2");
        OkHttpManager.postAsyn(LoginConstants.CHECK_IMG_VERIFICATION_AVAILABLE, new OkHttpManager.ResultCallback<CheckImgVerificationBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(CheckImgVerificationBean response) {
                if (response != null) {
                    CheckImgVerificationBean.DataBean data = response.getData();
                    if (data != null) {
                        if (data.isNeedImgCaptcha()) {
                            document.setImgVerificationCodeToken(data.getImageKey())
                                    .setNeedImgVerificationCode(true)
                                    .setImgVerificationCodeBytes(data.getImage());
                            mView.needCheckImgVerificationCode(document);
                        } else {
                            document.setImgVerificationCodeToken(data.getImageKey())
                                    .setNeedImgVerificationCode(false);
                            mView.notNeedCheckImgVerificationCode(document);
                        }
                    } else {
                        ToastUtils.showShort(response.message);
                    }
                }
            }
        }, map);
    }

    public void getAppKey(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", mobile);
        params.put("companyId", "30");
        OkHttpManager.getAsyn(Constants.GET_ALIBC, params, new OkHttpManager.ResultCallback<AliBCBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(AliBCBean response) {
                if (response != null && response.data != null) {
                    OdyApplication.putValueByKey(Constants.APP_KEY, response.data.appKey);
                    OdyApplication.putValueByKey(Constants.BC_PASS, response.data.password);
                    OdyApplication.putValueByKey(Constants.BC_USER_ID, response.data.userId);
                    OdyApplication.putValueByKey(Constants.RECEIVER_ID, response.data.receiveId);
                }
            }
        });
    }

}
