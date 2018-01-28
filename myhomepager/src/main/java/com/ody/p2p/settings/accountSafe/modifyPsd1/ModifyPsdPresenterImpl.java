package com.ody.p2p.settings.accountSafe.modifyPsd1;

import android.os.Handler;
import android.os.Message;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.settings.accountSafe.modifyPsd2.ModifyPsdSecondActivity;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.
 */
public class ModifyPsdPresenterImpl implements ModifyPsdPresenter {

    private ModifyPsdView modifyPsdView;
    int number;
    boolean flage = true;

    public ModifyPsdPresenterImpl(ModifyPsdView modifyPsdView){
        this.modifyPsdView = modifyPsdView;
    }

    /**
     * 获取验证码
     */
    @Override
    public void getValidateCode(String mobile) {
        if (number > 0) {
            modifyPsdView.setValidateCodeClickable(false);
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("mobile", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null));
        OkHttpManager.postAsyn(Constants.GET_CAPTCHA,new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                modifyPsdView.setValidateCodeClickable(true);
//                modifyPsdView.showToast(msg == null ? "发送失败" : msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                ToastUtils.showShort(response.message);
                number = 60;
                flage = true;
                //获取验证码成功后,让光标移动到输入验证码处
                modifyPsdView.getValidateFocus();
                hd.removeMessages(1);
                hd.sendEmptyMessage(1);
            }
        },params);
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
                        modifyPsdView.setValidateText("获取验证码");
                        modifyPsdView.setValidateCodeClickable(true);
                        hd.removeMessages(1);
                    } else {
                        modifyPsdView.setValidateText((number--) + "S");
                        hd.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    public void toNext(final String mobile, String captchas) {
        boolean checkValidatCode = modifyPsdView.checkValidateCode(captchas);
        if (!checkValidatCode){
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("mobile", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null));
        params.put("captchas",captchas);
        OkHttpManager.postAsyn(Constants.CHECK_CAPTCHA, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response.code.equals("0")){
                    modifyPsdView.toActivity(ModifyPsdSecondActivity.class);
                    hd.removeMessages(1);
                }
                else{
                    ToastUtils.showShort(response.message);
                }

            }
        },params);
    }

    @Override
    public void removeHd() {
        hd.removeMessages(1);
    }
}
