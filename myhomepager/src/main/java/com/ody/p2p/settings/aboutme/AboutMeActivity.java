package com.ody.p2p.settings.aboutme;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.settings.aboutme.bean.ShareBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.ToastUtils;

import java.util.HashMap;

import cn.campusapp.router.annotation.RouterMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

@RouterMap("activity://aboutus")
public class AboutMeActivity extends BaseActivity implements View.OnClickListener, MainView, PlatformActionListener {

    private ImageView iv_back, iv_scan;
    private LinearLayout lay_share_toFriend, lay_evaluate, lay_bg;
    private PopupWindow mPopupWindow, mPhoneWindow;

    private View popupView, mPhoneView;

    private ImageView iv_qq, iv_wechat, iv_weibo;

    private ColorDrawable mColorDrawable;

    private LinearLayout about_main, lay_hotLine, lay_phone;

    private TextView btn_cancel;

    private TextView tv_cancel, tv_phoneNum, tv_telNum;

    private Intent mIntent;

    private String telNum;

    private MainPresenter presenter;

    private ShareBean mBean;

    /**
     * 朋友圈注册数据
     */
    private HashMap<String, Object> map_circle;

    /**
     * 微信好友注册数据
     */
    private HashMap<String, Object> map_wxFriend;

    /**
     * QQ空间注册数据
     */
    private HashMap<String, Object> map_qzone;

    /**
     * QQ好友注册数据
     */
    private HashMap<String, Object> map_qqFriend;

    /**
     * 短信注册数据
     */
    private HashMap<String, Object> map_shortMessage;

    /**
     * 新浪微博注册数据
     */
    private HashMap<String, Object> map_sina;



    @Override
    public void initPresenter() {

        presenter = new MainPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        initSdk();
        return R.layout.setting_activity_aboutme;
    }

    @Override
    public void initView(View view) {

        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        lay_share_toFriend = (LinearLayout) view.findViewById(R.id.lay_share_toFriend);
        about_main = (LinearLayout) findViewById(R.id.lay_about_main);
        lay_hotLine = (LinearLayout) view.findViewById(R.id.lay_hotLine);
        lay_evaluate = (LinearLayout) findViewById(R.id.lay_about_evaluate);
        iv_scan = (ImageView) findViewById(R.id.iv_about_scan);
        tv_telNum = (TextView) findViewById(R.id.tv_about_telNum);

        mPhoneView = LayoutInflater.from(getContext()).inflate(R.layout.setting_popup_phone, null);

        popupView = LayoutInflater.from(getContext()).inflate(R.layout.setting_popup_share_window, null);
        lay_bg = (LinearLayout) popupView.findViewById(R.id.bg);
        iv_qq = (ImageView) popupView.findViewById(R.id.iv_share_qq);
        iv_wechat = (ImageView) popupView.findViewById(R.id.iv_share_wechat);
        iv_weibo = (ImageView) popupView.findViewById(R.id.iv_share_weibo);

        btn_cancel = (TextView) popupView.findViewById(R.id.btn_about_cancel);

        tv_phoneNum = (TextView) mPhoneView.findViewById(R.id.tv_about_phoneNum);
        tv_cancel = (TextView) mPhoneView.findViewById(R.id.tv_about_cancel);
        lay_phone = (LinearLayout) mPhoneView.findViewById(R.id.lay_about_phone);

        tv_phoneNum.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        iv_back.setOnClickListener(this);
        lay_share_toFriend.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        lay_hotLine.setOnClickListener(this);
        lay_evaluate.setOnClickListener(this);
        lay_phone.setOnClickListener(this);
        lay_bg.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        showPhonePopupWindow();
        showSharePopupWindow();


    }


    private void initSdk() {

        // 初始化sdk分享资源
        ShareSDK.initSDK(getApplicationContext(), "122f529533373");

        map_circle = new HashMap<String, Object>();
        map_wxFriend = new HashMap<String, Object>();
        map_qzone = new HashMap<String, Object>();
        map_qqFriend = new HashMap<String, Object>();
        map_shortMessage = new HashMap<String, Object>();
        map_sina = new HashMap<String, Object>();

        map_wxFriend.put("AppId", "wx576c5a702343ec56");
        map_wxFriend.put("Enable", "true");
        map_wxFriend.put("AppSecret", "f92a079dd629e2746e5364df156ea7a4");
        map_wxFriend.put("BypassApproval", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, map_wxFriend);


        map_circle.put("AppId", "wx576c5a702343ec56");
        map_circle.put("Enable", "true");
        map_circle.put("AppSecret", "f92a079dd629e2746e5364df156ea7a4");
        map_circle.put("BypassApproval", "false");

        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, map_circle);


        map_qqFriend.put("AppId", "1105257193");
        map_qqFriend.put("AppKey", "dKgPyY4alsX3q7zI");
        map_qqFriend.put("Enable", "true");
        map_qqFriend.put("ShareByAppClient", "true");
        ShareSDK.setPlatformDevInfo(QQ.NAME, map_qqFriend);


        map_sina.put("AppKey", "601612353");
        map_sina.put("AppSecret", "669f8957f49b52ff9606c9ec2095bc29");
        map_sina.put("RedirectUrl", "http://www.sharesdk.cn");
        map_sina.put("ShareByAppClient", "false");//true
        map_sina.put("Enable", "true");//true
        map_sina.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME, map_sina);
    }


    @Override
    public void doBusiness(Context mContext) {
        //第一版不做这个功能，页面全部写成固定，不走接口

//        presenter.getCodeBitmap();
//        presenter.getShare();
//        presenter.getTelNum();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.iv_back) {
                finish();
        } else if (i == R.id.lay_share_toFriend) {
            mPopupWindow.showAtLocation(findViewById(R.id.lay_about_main), Gravity.BOTTOM, 0, 0);

        } else if (i == R.id.btn_about_cancel) {
            mPopupWindow.dismiss();
        } else if (i == R.id.lay_hotLine) {
            mPhoneWindow.showAtLocation(findViewById(R.id.lay_about_main), Gravity.TOP, 0, 0);
        } else if (i == R.id.tv_about_phoneNum) {
            mIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_phoneNum.getText().toString()));
            startActivity(mIntent);
        } else if (i == R.id.tv_about_cancel) {
            mPhoneWindow.dismiss();
        } else if (i == R.id.lay_about_phone) {
            mPhoneWindow.dismiss();
        } else if (i == R.id.bg) {
            mPopupWindow.dismiss();
        } else if (i == R.id.iv_share_qq) {
//                ToastUtils.showShort("qq");
            presenter.shareToQQ(mBean, AboutMeActivity.this);
//                shareToQQ(mBean,MainActivitySec.this);
        } else if (i == R.id.iv_share_wechat) {
//                ToastUtils.showShort("wechat");
            presenter.shareToWeChat(mBean, AboutMeActivity.this);
//                shareToWeChat(mBean, MainActivitySec.this);
        } else if (i == R.id.iv_share_weibo) {
//                ToastUtils.showShort("weibo");
            presenter.shareToWeibo(mBean, AboutMeActivity.this);
//                shareToWeibo(mBean, MainActivitySec.this);
        } else if (i == R.id.lay_about_evaluate) {
            Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
            mIntent = new Intent(Intent.ACTION_VIEW, uri);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }

    }




    private void showSharePopupWindow() {
        mPopupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mColorDrawable = new ColorDrawable(0x20000000);
        mPopupWindow.setBackgroundDrawable(mColorDrawable);

    }

    private void showPhonePopupWindow() {
        mPhoneWindow = new PopupWindow(mPhoneView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPhoneWindow.setFocusable(true);
        mPhoneWindow.setOutsideTouchable(true);
        mPhoneWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mColorDrawable = new ColorDrawable(0x20000000);
        mPhoneWindow.setBackgroundDrawable(mColorDrawable);

    }

    @Override
    public void showCodeView() {
        GlideUtil.display(AboutMeActivity.this, "http://www.baidu.com/img/bd_logo1.png").into(iv_scan);
    }

    @Override
    public void showTelNum(String telNum) {
        tv_phoneNum.setText(telNum);
        tv_telNum.setText(telNum);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getShareBean(ShareBean bean) {
        mBean = bean;

    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
