package com.ody.p2p.settings.aboutme;


import com.ody.p2p.settings.aboutme.bean.ShareBean;

/**
 * Created by ody on 2016/6/8.
 */
public interface MainView {

    void showCodeView();

    void showTelNum(String telNum);

    void showToast(String msg);

    void getShareBean(ShareBean bean);

}
