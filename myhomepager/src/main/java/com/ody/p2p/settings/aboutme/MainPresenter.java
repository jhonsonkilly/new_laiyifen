package com.ody.p2p.settings.aboutme;

import android.app.Activity;

import com.ody.p2p.settings.aboutme.bean.ShareBean;


/**
 * Created by ody on 2016/6/8.
 */
public interface MainPresenter {

    //分享到QQ
    void shareToQQ(ShareBean bean, Activity context);

    //分享到新浪微博
    void shareToWeibo(ShareBean bean, Activity context);

    //分享到微信
    void shareToWeChat(ShareBean bean, Activity context);

    //获取电话号码
    void getTelNum();

    //获取二维码图片
    void getCodeBitmap();

    //获取分享的内容
    void getShare();

}
