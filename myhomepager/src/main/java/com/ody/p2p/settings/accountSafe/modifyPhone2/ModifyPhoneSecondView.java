package com.ody.p2p.settings.accountSafe.modifyPhone2;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPhoneSecondView {

    void setValidateCodeClickable(boolean flag);

    void getValidateFocus();

    void setValidateText(String text);

    void toAccountSafeActivity();

    void finishActivity();

    //德升首次联合登录成功后需要绑定手机号,请求需要这个参数
    String getUnionUt();

}
