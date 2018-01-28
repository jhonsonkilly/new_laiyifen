package com.ody.p2p.settings.accountSafe.modifyPhone1;

import android.os.Handler;
import android.os.Message;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.settings.accountSafe.modifyPhone2.IsRepeatPhoneBean;
import com.ody.p2p.settings.accountSafe.modifyPhone2.ModifyPhoneSecondActivity;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.   和ForgetPsdPresenterImpl里的几乎一样,照它来的
 */
public class ModifyPhonePresenterImpl implements ModifyPhonePresenter {

    private ModifyPhoneView modifyPhoneView;
    int number;
    boolean flage = true;

    public ModifyPhonePresenterImpl(ModifyPhoneView modifyPhoneView){
        this.modifyPhoneView = modifyPhoneView;
    }

    /**
     * 获取验证码
     */
    public void getValidateCode() {
        if (number > 0) {
            modifyPhoneView.setValidateCodeClickable(false);
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("mobile",OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null));
        OkHttpManager.postAsyn(Constants.GET_CAPTCHA, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
                modifyPhoneView.setValidateCodeClickable(true);
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (0==Integer.valueOf(response.code)){
                    number = 60;
                    flage = true;
                    //获取验证码成功后,让光标移动到输入验证码处
                    modifyPhoneView.getValidateFocus();
                    hd.removeMessages(1);
                    hd.sendEmptyMessage(1);
                }else {
                    modifyPhoneView.setValidateCodeClickable(true);
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
                    hd.sendEmptyMessageDelayed(1,1000);
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
                        modifyPhoneView.setValidateText("获取验证码");
                        modifyPhoneView.setValidateCodeClickable(true);
                        hd.removeMessages(1);
                    } else {
                        modifyPhoneView.setValidateText((number--) + "S");
                        hd.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    public void toNext(String captchas) {
        boolean checkValidatCode = modifyPhoneView.checkValidateCode(captchas);
        if (!checkValidatCode){
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("mobile",OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null));
        params.put("captchas",captchas);

        OkHttpManager.postAsyn(Constants.CHECK_CAPTCHA, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (Integer.valueOf(response.code)==0){
                    ToastUtils.showShort(response.message);
                    modifyPhoneView.toActivity(ModifyPhoneSecondActivity.class);
                    modifyPhoneView.finishActivity();
                    hd.removeMessages(1);
                }else {
                    ToastUtils.showShort(response.message);
                }
            }
        }, params);
    }

    @Override
    public void removeHd() {
        hd.removeMessages(1);
    }
}
