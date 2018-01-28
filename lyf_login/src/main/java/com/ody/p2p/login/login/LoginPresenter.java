package com.ody.p2p.login.login;

/**
 * Created by ody on 2016/6/6.
 */
public interface LoginPresenter {

    //获取第三方登录的图标
    void getThirdLogo();

    //检查手机号是否已经注册(包括检查手机号是否为空,是否合法)
    void checkPhoneIsRegistered(String mobile, String psd);

    void checkPhoneIsRegistered(String mobile, String psd, String imgcheck);

    void getIgraphicCode();

    //登录
    void login(String mobile, String psd, String imgcheck);

    //利用 sharesdk 实现联合登录  gateway 1为QQ，2为微信
    void unionLogin(String appid, String openId, String unionid, String token, int gateway);

    void synHistory(String infoJson);

    void bindGuid();

    void isNewUser();

    void getPolicy();

}
