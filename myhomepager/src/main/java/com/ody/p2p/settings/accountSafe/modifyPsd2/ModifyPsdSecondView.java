package com.ody.p2p.settings.accountSafe.modifyPsd2;

/**
 * Created by ody on 2016/6/16.
 */
public interface ModifyPsdSecondView {

    boolean checkPsd(String et_psd1, String et_psd2, String et_psd3);

    void toAccountSafeActivity();

    //德升 个人设置  密码修改 接口路径
    String getDsPsdModify();
}
