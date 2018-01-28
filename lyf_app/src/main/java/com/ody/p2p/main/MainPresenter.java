package com.ody.p2p.main;

/**
 * Created by lxs on 2016/7/6.
 */
public interface MainPresenter {

    void initApp(String versionCode, String uniqueCode);

    void initCartNum();

    void getUpgrade(String versionName, String uniqueCode, String versionCode, String appChannel);

    void getTanPin(String adCode);

    void isNewUser(int flag);

    void firstLoginCoupon();

    void getCurrDistributor();

    void getPersonalMes(boolean isIMlogin);

    void getGuangGao();
}
