package com.ody.p2p.settings.aboutme;

import android.app.Activity;
import android.util.Log;

import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.settings.aboutme.bean.AboutBean;
import com.ody.p2p.settings.aboutme.bean.ShareBean;
import com.ody.p2p.settings.aboutme.net.NetConstant;
import com.squareup.okhttp.Request;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by ody on 2016/6/8.
 */
public class MainPresenterImpl implements MainPresenter ,PlatformActionListener {

    private MainView mView;


    public MainPresenterImpl(MainView view){
        mView = view;
    }




    @Override
    public void shareToQQ(ShareBean bean,Activity context) {
        ShareSDK.initSDK(context);

        QQ.ShareParams sp = new QQ.ShareParams();

//        if (bean != null && bean.data != null) {
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("点评身边的修车承诺店");
            sp.setTitleUrl("http://www.ixingzhe.com/index_wap.html"); // 标题的超链接
            sp.setText("服务怎样？哪家口碑好？车主说了算，一切听您的！");
            sp.setImageUrl("http://img1.zgxcw.com/v1/tfs//T2NChTB7CT1RCvBVdK");
            sp.setUrl("http://www.ixingzhe.com/index_wap.html");

            cn.sharesdk.framework.Platform qzone = ShareSDK.getPlatform(QQ.NAME);
            qzone.setPlatformActionListener(this); // 设置分享事件回调
            qzone.share(sp);
//        }

    }

    @Override
    public void shareToWeibo(ShareBean bean,Activity context) {
        ShareSDK.initSDK(context);

        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();

//        if (bean != null && bean.data != null) {
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("点评身边的修车承诺店");
            sp.setTitleUrl("http://www.ixingzhe.com/index_wap.html"); // 标题的超链接
            sp.setText("服务怎样？哪家口碑好？车主说了算，一切听您的！");
            sp.setImageUrl("http://img1.zgxcw.com/v1/tfs//T2NChTB7CT1RCvBVdK");
            sp.setUrl("http://www.ixingzhe.com/index_wap.html");


            cn.sharesdk.framework.Platform qzone = ShareSDK.getPlatform(SinaWeibo.NAME);
            qzone.setPlatformActionListener(this); // 设置分享事件回调
            qzone.share(sp);

//        }
    }

    @Override
    public void shareToWeChat(ShareBean bean,Activity context) {
        ShareSDK.initSDK(context);

        Wechat.ShareParams sp = new Wechat.ShareParams();

//        if (bean != null && bean.data != null) {
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("点评身边的修车承诺店");
            sp.setTitleUrl("http://www.ixingzhe.com/index_wap.html"); // 标题的超链接
            sp.setText("服务怎样？哪家口碑好？车主说了算，一切听您的！");
            sp.setImageUrl("http://img1.zgxcw.com/v1/tfs//T2NChTB7CT1RCvBVdK");
            sp.setUrl("http://www.ixingzhe.com/index_wap.html");


            cn.sharesdk.framework.Platform qzone = ShareSDK.getPlatform(Wechat.NAME);
            qzone.setPlatformActionListener(this); // 设置分享事件回调
            qzone.share(sp);

//        }
    }


    @Override
    public void getTelNum() {
        OkHttpManager.getAsyn(NetConstant.ABOUT_ME, new OkHttpManager.ResultCallback<AboutBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.showToast("网络错误");
            }

            @Override
            public void onResponse(AboutBean response) {
                String telNum = response.data.telNum + "ceshi";
                Log.e("TAG", telNum);
//                tv_phoneNum.setText(telNum);
//                tv_telNum.setText(telNum);

                mView.showTelNum(telNum);
            }
        });

    }

    @Override
    public void getCodeBitmap() {

        mView.showCodeView();
    }

    @Override
    public void getShare() {
        OkHttpManager.Param p1 = new OkHttpManager.Param("merchantId","ceshi");
        OkHttpManager.Param p2 = new OkHttpManager.Param("entryType",""+12);

        OkHttpManager.postAsyn(NetConstant.SHARE, new OkHttpManager.ResultCallback<ShareBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.showToast("网络错误");
            }

            @Override
            public void onResponse(ShareBean response) {
                Log.e("TAG", response.data.linkUrl);
                mView.getShareBean(response);

            }
        }, p1, p2);
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        mView.showToast("分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
