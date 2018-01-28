package com.ody.p2p.login.utils;

import static com.ody.p2p.Constants.BASEURL;

/**
 * Created by pzy on 2017/1/11.
 */
public class LoginConstants {
    //来伊份接口
    //获取验证码
//    public static final String GET_CAPTCHA = BASEURL + "/ouser-web/mobileRegister/sendCaptchasCodeForm.do";
    public static final String GET_CAPTCHA = BASEURL + "/ouser-web/mobileRegister/sendCaptchasCodeFormNew.do";

    //获取短信验证码前调用，查询是否需要验证图形验证码
    public static final String CHECK_IMG_VERIFICATION_AVAILABLE = BASEURL + "/ouser-web/api/user/init.do";

    //来伊份新用户设置密码
//    public static final String UNION_ADD_PSD = BASEURL + "/ouser-web/user/setUserPasswordForm.do";
    public static final String UNION_ADD_PSD = BASEURL + "/ouser-web/user/setUserPassword.do";

    //来伊份重置密码
//    public static final String MODIFY_PSD = BASEURL + "/ouser-web/user/resetUserPassword.do";
    public static final String MODIFY_PSD = BASEURL + "/ouser-web/user/resetUserPasswordForm.do";

    public static final String BIND_MOBILE = BASEURL + "/api/my/user/bindMobile";

    public static final String CHECK_CAPTCHAS = BASEURL + "/ouser-web/mobileRegister/checkCaptchasForm.do";

}
