package com.ody.p2p.settings.accountSafe.modifyPhone2;

import android.os.Handler;
import android.os.Message;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.
 */
public class ModifyPhoneSecondPresenterImpl implements ModifyPhoneSecondPresenter {

    int number;
    boolean flage = true;
    private ModifyPhoneSecondView modifyPhoneSecondView;

    public ModifyPhoneSecondPresenterImpl(ModifyPhoneSecondView modifyPhoneSecondView) {
        this.modifyPhoneSecondView = modifyPhoneSecondView;
    }

    @Override
    public void checkPhoneIsRegistered(final String mobile) {
        boolean checkPhone = StringUtils.checkPhone(mobile);
        if (!checkPhone) {
            return;
        }
        if (number > 0) {
            modifyPhoneSecondView.setValidateCodeClickable(false);
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        String url = Constants.IS_REPEAT_PHONE;
        //先换到saas主干
//        String url = "http://saas.test.odianyun.com/ouser-web/mobileRegister/isRepeatPhoneForm.do";

        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (-1 == Integer.valueOf(response.code)) {//手机号已经注册
                    ToastUtils.showShort(response.message);
                } else if (0 == Integer.valueOf(response.code)) {//手机号还未注册
                    getValidateCode(mobile);
                } else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    /**
     * 获取验证码
     */
    public void getValidateCode(String mobile) {
        if (number > 0) {
            modifyPhoneSecondView.setValidateCodeClickable(false);
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        String url = Constants.GET_CAPTCHA;
        //先换到saas主干
//        String url = "http://saas.test.odianyun.com/ouser-web/mobileRegister/sendCaptchasForm.do";

        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
                modifyPhoneSecondView.setValidateCodeClickable(true);
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (0 == Integer.valueOf(response.code)) {
                    number = 60;
                    flage = true;
                    //获取验证码成功后,让光标移动到输入验证码处
                    modifyPhoneSecondView.getValidateFocus();
                    hd.removeMessages(1);
                    hd.sendEmptyMessage(1);
                } else {
                    modifyPhoneSecondView.setValidateCodeClickable(true);
                }
                ToastUtils.showShort(response.message);
            }
        }, params);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (flage) {
                try {
                    sleep(1000);
                    hd.sendEmptyMessageDelayed(1, 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (number < 0) {
                        modifyPhoneSecondView.setValidateText("获取验证码");
                        modifyPhoneSecondView.setValidateCodeClickable(true);
                        hd.removeMessages(1);
                    } else {
                        modifyPhoneSecondView.setValidateText((number--) + "S");
                        hd.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    public void bindPhone(String orgianlMobile, final String mobile, String captchas) {
        boolean checkPhone = StringUtils.checkPhone(mobile);
        if (!checkPhone) {
            return;
        }
        boolean checkValidatCode = StringUtils.checkValidateCode(captchas);
        if (!checkValidatCode) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        if (!StringUtils.isEmpty(modifyPhoneSecondView.getUnionUt())) {
            params.put("ut", modifyPhoneSecondView.getUnionUt());
        }
        params.put("mobile", mobile);
        params.put("captchas", captchas);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        String url;
        if (!StringUtils.isEmpty(modifyPhoneSecondView.getUnionUt())) {
            params.put("ut", modifyPhoneSecondView.getUnionUt());
            url = Constants.UNION_BIND_PHONE;
        } else {
            url = Constants.BIND_PHONE;
            params.put("OrgianlMobile", orgianlMobile);
        }

        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (Integer.valueOf(response.code) == 0) {
                    if (!StringUtils.isEmpty(modifyPhoneSecondView.getUnionUt())) {//德升 登录成功
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, modifyPhoneSecondView.getUnionUt());
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, mobile);
                    } else {
                        //修改手机号成功后,诸葛是让跳转到登录界面
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, null);
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, null);
                        ToastUtils.showShort("手机号修改成功,请重新登录!");
//                        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://login");
//                        activityRoute.open();
                        JumpUtils.ToActivity(JumpUtils.LOGIN);
                    }

                    hd.removeMessages(1);
                    JumpUtils.ToActivity(JumpUtils.MAIN);
                }
            }
        }, params);
    }

    @Override
    public void removeHd() {
        hd.removeMessages(1);
    }

}
