package com.ody.p2p.login.loginfragment.registeruser;

/**
 * Created by ody on 2016/6/6.
 */
public interface RegisterSetPasswordView {
    void showToast(String msg);

    void finishActivity();

    //校验密码的长度以及两次密码是否一样
    boolean checkPsd(String et_psd_first, String et_psd_second);

    //获取邀请人手机号
    String getInviterMobile();

    //标记 德升首次联合登录时添加密码的情况
    int getAddPsd();

    void setAlias(String alias);
}
