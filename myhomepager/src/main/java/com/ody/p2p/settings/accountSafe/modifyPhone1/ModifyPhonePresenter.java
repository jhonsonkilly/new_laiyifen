package com.ody.p2p.settings.accountSafe.modifyPhone1;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPhonePresenter {

    //获取验证码
    void getValidateCode();

    //移除handler
    void removeHd();

    //下一步
    void toNext(String captchas);
}
