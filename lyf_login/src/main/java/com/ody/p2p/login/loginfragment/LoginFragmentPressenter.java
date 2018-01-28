package com.ody.p2p.login.loginfragment;

import com.ody.p2p.login.Bean.LoginDocument;

/**
 * Created by pzy on 2016/10/18.
 */
public interface LoginFragmentPressenter {
    //快速登陆
    void quickLogin(final String phone, String psd);
    //发送验证码
    void getValidateCode(LoginDocument document);

    //检验是否注册
    void checkPhoneIsRegistered(LoginDocument document);

    //下一步
    void toNext(String mobile, String captchas);

    void Register(String mobile, String captchas);

    void bindPhone(String mobile, String captchas,String ut);

    void isNewUser();

    /**
     * 检查是否需要进行图形验证码验证
     * @param document 页面单据
     */
    void checkImgVerificationAvailable(LoginDocument document);
}
