package com.ody.p2p.settings.accountSafe.modifyPsd1;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPsdPresenter {

    void getValidateCode(String mobile);

    void toNext(final String mobile, String captchas);

    void removeHd();
}
