package com.ody.p2p.login.login;

import android.util.Log;

import com.ody.p2p.AliBCBean;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.NewUserBean;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.DSUnionLoginBean;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.login.R;
import com.ody.p2p.okhttputils.OkHttpManager;
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
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    String imageKey;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void getThirdLogo() {
//        OkHttpManager.getAsyn(Constants.THIRD_LOGIN_LOGO, new OkHttpManager.ResultCallback<ThirdLoginLogoBean>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(ThirdLoginLogoBean response) {
//                if (response!=null && response.data!=null && response.data.logos!=null
//                        && response.data.logos.size()>0){
//                    loginView.setThirdLoginLogo(response.data.logos);
//                }
//            }
//        });
    }

    @Override
    public void checkPhoneIsRegistered(String mobile, String psd) {
        login(mobile, psd, "");
    }

    @Override
    public void checkPhoneIsRegistered(final String mobile, final String psd, final String imgcheck) {//先检查手机号的合法性,再检测手机号是否已经注册,最后才能获取验证码
        login(mobile, psd, imgcheck);

//        boolean checkPhone = loginView.checkPhone(mobile);
//        if (!checkPhone) {
//            return;
//        }
//
//        boolean checkPsd = loginView.checkPsd(psd);
//        if (!checkPsd) {
//            return;
//        }
//
//        Map<String, String> params = new HashMap<>();
//        params.put("mobile", mobile);
//
//        OkHttpManager.postAsyn(Constants.IS_REPEAT_PHONE, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
//
//            @Override
//            public void onFailed(String code, String msg) {
//                super.onFailed(code, msg);
//                Log.e("test", "IS_REPEAT_PHONE  onFailed==" + msg);
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(IsRepeatPhoneBean response) {
//                if (-1 == Integer.valueOf(response.code)) {
//                    login(mobile, psd);
//                } else {
//                    ToastUtils.showShort(response.message);
//                }
//            }
//        }, params);
    }

    public void login(final String phone, String psd, String imgcheck) {
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", psd);
        if (!StringUtils.isEmpty(imgcheck) && !StringUtils.isEmpty(imageKey)) {//校验图片验证码
            params.put("checkImageCode", imgcheck);
            params.put("imgeKey", imageKey);
            params.put("hasCheckImage", "true");
        }
        OkHttpManager.postAsyn(Constants.LOGIN, new OkHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "LOGIN  onError===" + request.toString());
            }

            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
                ToastUtils.showShort(msg);
                JSONObject jsonString = null;
                try {
                    jsonString = new JSONObject(json);
                    boolean checkImage = jsonString.getBoolean("checkImage");
                    if (checkImage) {//获取图片验证码
                        getIgraphicCode();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(LoginBean response) {
                if (null != response) {
                   /* EventMessage message = new EventMessage();
                    message.flag = EventMessage.REFRESH_UT;
                    EventBus.getDefault().post(message);*/
                    loginView.loginResult(response);
                    getAppKey(phone);
                }
            }
        }, params);
    }

    @Override
    public void bindGuid() {
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

            }

            @Override
            public void onFinish() {
                super.onFinish();
                loginView.finishActivity();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String alias = json.optString("data");
                    if (!StringUtils.isEmpty(alias)) {
                        loginView.setAlias(alias);
                    }
                } catch (JSONException e) {
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
                        loginView.isNewUser(response.data.userExtMap.newuser);
                    }
                }
            }
        }, map);
    }



    @Override
    public void unionLogin(String appid, String openId, String unionid, final String token, int gateway) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("openid", openId);
        params.put("unionId", unionid);
        params.put("token", token);
        params.put("gateway", gateway + "");
        String url = Constants.UNION_LOGIN;
//        loginView.showLoading();
        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<DSUnionLoginBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loginView.hideLoading();
            }

            @Override
            public void onResponse(DSUnionLoginBean response) {
                if (response == null) {
                    return;
                }
                if ("0".equals(response.code)) {
                    //德升联合登录 mobile为空的时候 跳转到绑定手机号界面,如果直接不绑定手机号按下返回键,那么登录界面不消失,土司"授权失败",尽管实际上已经授权成功了
                    //但是按产品要求,还是相当于授权失败,不算登录上了
                    if (response.data != null && StringUtils.isEmpty(response.data.mobile)) {
                        loginView.toBindThirdPlatform(response.ut);
                    } else {//如果已经绑定了手机号,那么就是登录成功了
                        ToastUtils.showShort(loginView.getClassContext().getString(R.string.login_succeed));
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                        OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, response.data.mobile);
                        OdyApplication.putValueByKey(Constants.HEAD_PIC_URL, response.data.headPicUrl);
                        OdyApplication.putValueByKey(Constants.NICK_NAME, response.data.nickname);
                        bindGuid();
                        getAppKey(response.data.mobile);
                        EventMessage message = new EventMessage();
                        message.flag = EventMessage.REFRESH_UT;
                        EventBus.getDefault().post(message);
                        loginView.loginSuccess();
                        String info = OdyApplication.getValueByKey("history", null);
                        if (info != null && info.length() > 0) {
                            synHistory(info);
                        }
                    }
                }
            }
        }, params);
    }


    @Override
    public void getPolicy() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        OkHttpManager.postAsyn(Constants.HISTORY_SYN, new OkHttpManager.ResultCallback<DSUnionLoginBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(DSUnionLoginBean response) {
                if (response != null && response.code.equals("0")) {
                    OdyApplication.putValueByKey("history", null);
                }
            }
        }, params);
    }

    @Override
    public void synHistory(String infoJson) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("infoJson", infoJson);
        OkHttpManager.postAsyn(Constants.HISTORY_SYN, new OkHttpManager.ResultCallback<DSUnionLoginBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(DSUnionLoginBean response) {
                if (response != null && response.code.equals("0")) {
                    OdyApplication.putValueByKey("history", null);
                }
            }
        }, params);
    }

    /**
     * 获取图形码
     */
    @Override
    public void getIgraphicCode() {
        Map<String, String> params = new HashMap<>();
        params.put("width", "160");
        params.put("height", "80");
        params.put("codeCount", "4");
        loginView.showLoading();
        OkHttpManager.postAsyn(Constants.CHECK_IMAGGE/*url*/, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                loginView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(String json) {
                super.onResponse(json);
                JSONObject jsonString = null;
                try {
                    jsonString = new JSONObject(json);
                    String imageurl = jsonString.getString("image");
                    imageKey = jsonString.getString("imageKey");
                    loginView.getImageCheck("" + imageurl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(BaseRequestBean response) {
            }
        }, params);
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