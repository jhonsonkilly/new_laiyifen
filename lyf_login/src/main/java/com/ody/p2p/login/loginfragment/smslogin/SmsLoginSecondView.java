package com.ody.p2p.login.loginfragment.smslogin;

/**
 * Created by ody on 2016/6/7.
 */
public interface SmsLoginSecondView {

    boolean checkPsd(String et_psd_first, String et_psd_second);

    void showToast(String msg);

    void finishActivity();

}
