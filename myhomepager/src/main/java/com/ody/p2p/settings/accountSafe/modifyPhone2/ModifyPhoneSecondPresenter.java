package com.ody.p2p.settings.accountSafe.modifyPhone2;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPhoneSecondPresenter {

    void checkPhoneIsRegistered(String mobile);

    void bindPhone(String orgianlMobile, String mobile, String captchas);

    void removeHd();

}
