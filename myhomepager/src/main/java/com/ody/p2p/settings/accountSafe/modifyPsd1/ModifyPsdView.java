package com.ody.p2p.settings.accountSafe.modifyPsd1;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPsdView {

    void setValidateCodeClickable(boolean flag);

    void getValidateFocus();

    void setValidateText(String text);

    boolean checkValidateCode(String captchas);

    void toActivity(Class clazz);

}
