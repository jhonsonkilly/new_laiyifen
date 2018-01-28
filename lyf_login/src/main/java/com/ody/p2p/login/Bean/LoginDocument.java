package com.ody.p2p.login.Bean;

import android.text.TextUtils;

/**
 * 登录注册单据信息
 */

public class LoginDocument {

    public final static int REGISTER = 1; //注册
    public final static int FORGET_PASSWORD = 5;//忘记密码
    public final static int SMS_LOGIN = 3;//短信登陆
    public final static int BIND_PHONE = 7;//第三方登录绑定手机


    private String telephone;//手机号
    private String password; //密码
    private String verificationCode; //短信验证码
    private String imgVerificationCodeBytes;  //图形验证码二进制数据
    private String imgVerificationCode; //图形验证码
    private String imgVerificationCodeToken; //图形验证码token
    private boolean needImgVerificationCode = false; //是否需要验证图形验证码
    private String message; //接口返回错误信息
    private int captchasType = SMS_LOGIN;

    public int getCaptchasType() {
        return captchasType;
    }

    public LoginDocument setCaptchasType(int captchasType) {
        this.captchasType = captchasType;
        return this;
    }

    public String getImgVerificationCodeBytes() {
        return imgVerificationCodeBytes;
    }

    public LoginDocument setImgVerificationCodeBytes(String imgVerificationCodeBytes) {
        this.imgVerificationCodeBytes = imgVerificationCodeBytes;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginDocument setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isNeedImgVerificationCode() {
        return needImgVerificationCode;
    }

    public LoginDocument setNeedImgVerificationCode(boolean needImgVerificationCode) {
        this.needImgVerificationCode = needImgVerificationCode;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public LoginDocument setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDocument setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public LoginDocument setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public String getImgVerificationCode() {
        return imgVerificationCode;
    }

    public LoginDocument setImgVerificationCode(String imgVerificationCode) {
        this.imgVerificationCode = imgVerificationCode;
        return this;
    }

    public String getImgVerificationCodeToken() {
        return imgVerificationCodeToken;
    }

    public LoginDocument setImgVerificationCodeToken(String imgVerificationCodeToken) {
        this.imgVerificationCodeToken = imgVerificationCodeToken;
        return this;
    }

    public boolean isTelephoneEmpty() {
        return TextUtils.isEmpty(telephone);
    }

    public boolean isImgVerificationCodeEmpty() {
        return TextUtils.isEmpty(imgVerificationCode);
    }
}
