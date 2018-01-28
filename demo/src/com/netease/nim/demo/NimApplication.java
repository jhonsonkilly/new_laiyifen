package com.netease.nim.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.netease.nim.demo.chatroom.ChatRoomSessionHelper;
import com.netease.nim.demo.common.util.sys.SystemUtil;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHelper;
import com.netease.nim.demo.mixpush.DemoMixPushMessageHandler;
import com.netease.nim.demo.session.NimDemoLocationProvider;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;


public class NimApplication extends Application {

    public static Context mContext;
    public static int mainPage;
    public static int dex;
    public static int chatStatus; //0--视频  1--音频


    public static String QZ   = "0"; //新建群组
    public static String QF   = "0"; //群发消息
    public static String DDH  = "0"; //创建多人电话
    public static String DSP  = "0"; //创建多人视频
    public static String CZHY = "0"; //查找好友
    public static String QR   = "0"; //扫一扫
    public static String HB1  = "0"; //红包（一对一）
    public static String HB2  = "0"; //红包（群）
    public static String HB3  = "0"; //红包（聊天室）
    public static String YDK  = "0"; //伊点卡
    public static String YHQ  = "0"; //优惠券
    public static String YD   = "0"; //伊豆
    public static String SP1  = "0"; //视频聊天（一对一）
    public static String SP2  = "0"; //视频聊天（群）
    public static String SP3  = "0"; //视频聊天（聊天室）
    public static String YY1  = "0"; //视频聊天（一对一）
    public static String YY2  = "0"; //视频聊天（群）
    public static String YY3  = "0"; //视频聊天（聊天室）
    public static String MP   = "0"; //名片
    public static String SC   = "0"; //我的收藏
    public static String XC1  = "0"; //相册（一对一）
    public static String XC2  = "0"; //相册（群）
    public static String XC3  = "0"; //相册（聊天室）
    public static String PS1  = "0"; //拍摄（一对一）
    public static String PS2  = "0"; //拍摄（群）
    public static String PS3  = "0"; //拍摄（聊天室）



    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        DemoCache.setContext(this);
        // 注册小米推送，参数：小米推送证书名称（需要在云信管理后台配置）、appID 、appKey，该逻辑放在 NIMClient init 之前
        NIMPushClient.registerMiPush(this, "DEMO_MI_PUSH", "2882303761517502883", "5671750254883");
        // 注册华为推送，参数：华为推送证书名称（需要在云信管理后台配置）
        NIMPushClient.registerHWPush(this, "DEMO_HW_PUSH");

        // 注册自定义推送消息处理，这个是可选项
        NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions());



        // crash handler
//        AppCrashHandler.getInstance(this);

        // 以下逻辑只在主进程初始化时执行
        if (inMainProcess()) {
            //手机权限开关
//            JurisdictionButton.querySwitch(this);

            // 初始化红包模块，在初始化UIKit模块之前执行
//            NIMRedPacketClient.init(this);
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块

            initUIKit();
            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

            // 云信sdk相关业务初始化
            NimInitManager.getInstance().init(true);
        }


    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private void initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(this);
        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
        ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
        ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        //NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());

//        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }
}
