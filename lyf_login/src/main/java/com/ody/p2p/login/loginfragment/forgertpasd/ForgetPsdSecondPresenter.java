package com.ody.p2p.login.loginfragment.forgertpasd;

/**
 * Created by ody on 2016/6/7.
 */
public interface ForgetPsdSecondPresenter {

    void checkPsd(String phone,String smsCode, String newPsd, String confirmPsd);

    void checkCaptchas(String phone, String smsCode);
}
