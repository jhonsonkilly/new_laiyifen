package com.ody.p2p.main;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.facebook.stetho.Stetho;
import com.laiyifen.lyfframework.base.BaseApplication;
import com.lyfen.android.ui.activity.order.OrderDetailActivity;
import com.lyfen.android.ui.activity.redpacket.redpacket.RedPacketActivity;
import com.lyfen.android.ui.activity.shangou.ShangouActivity;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.NimInitManager;
import com.netease.nim.demo.NimSDKOptionConfig;
import com.netease.nim.demo.chatroom.ChatRoomSessionHelper;
import com.netease.nim.demo.common.util.crash.AppCrashHandler;
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
import com.odianyun.UpLevelActivity;
import com.ody.p2p.Constants;
import com.ody.p2p.Contact.ContactsActivity;
import com.ody.p2p.PolicyBean;
import com.ody.p2p.RefoundInfo.LogisticsCompanyActivity;
import com.ody.p2p.RefoundInfo.RefoundInfoActivity;
import com.ody.p2p.RefoundList.RefoundListActivity;
import com.ody.p2p.addressmanage.editaddress.EditAddressActivity;
import com.ody.p2p.addressmanage.location.ProvinceListActivity;
import com.ody.p2p.addressmanage.receiver.ReceiveAddressActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.aftersale.AftersaleActivity;
import com.ody.p2p.check.bill.BillActivity;
import com.ody.p2p.check.coupon.AddCouponActivity;
import com.ody.p2p.check.coupon.CouponActivity;
import com.ody.p2p.check.giftcard.BindGiftCardActivity;
import com.ody.p2p.check.giftcard.GiftCardActivity;
import com.ody.p2p.check.giftcard.GiftCardConsumerActivity;
import com.ody.p2p.check.giftcard.UsegiftcardActivity;
import com.ody.p2p.check.myorder.ChoosePayWayActivity;
import com.ody.p2p.check.orderlist.EvaluateActivity;
import com.ody.p2p.check.orderlist.ShowImageViewActivity;
import com.ody.p2p.check.orderoinfo.LogisticsActivity;
import com.ody.p2p.check.ordersearch.OrdersearchActivity;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.invitefriends.InviteFriendsActivity;
import com.ody.p2p.main.login.LyfLogin;
import com.ody.p2p.main.login.LyfLoginFragment;
import com.ody.p2p.main.order.ChooseAddressActivity;
import com.ody.p2p.main.order.ChooseStoreActivity;
import com.ody.p2p.main.order.CouponUseActivity;
import com.ody.p2p.main.order.DistributionActivity;
import com.ody.p2p.main.order.LYFConfirmOrderActivity;
import com.ody.p2p.main.pay.CashierStandActivity;
import com.ody.p2p.main.pay.PayFailActivity;
import com.ody.p2p.main.pay.PayForLeisurelyActivity;
import com.ody.p2p.main.pay.PaySuccessActivity;
import com.ody.p2p.main.productDetail.LyfProductDetailActivity;
import com.ody.p2p.main.productDetail.notifacation.ArrivalNotifaActivity;
import com.ody.p2p.main.search.LYFSearchActivity;
import com.ody.p2p.main.search.LyfSearchResultActivity;
import com.ody.p2p.main.search.ScanProductListActivity;
import com.ody.p2p.main.shopcart.LyfShopCartActivity;
import com.ody.p2p.main.specificfunction.DataUtils;
import com.ody.p2p.main.specificfunction.PointCardSearchActivity;
import com.ody.p2p.main.store.LyfStoreHomeActivity;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.YIActivity;
import com.ody.p2p.productdetail.store.StoreDetailActivity;
import com.ody.p2p.productdetail.store.storecategory.StoreCategoryActivity;
import com.ody.p2p.pushlibrary.PushClickEventMessage;
import com.ody.p2p.pushlibrary.PushFirstRegistEvent;
import com.ody.p2p.retrofit.home.AdBean;
import com.ody.p2p.retrofit.home.PersonalBean;
import com.ody.p2p.scanhistory.ScanHistoryActivity;
import com.ody.p2p.settings.SettingsActivity;
import com.ody.p2p.settings.aboutme.AboutMeActivity;
import com.ody.p2p.shoucang.MylikeActivity;
import com.ody.p2p.utils.JumpUtils;
import com.odysaxx.photograph.PhotoPickerActivity;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.squareup.okhttp.Request;
import com.tencent.bugly.crashreport.CrashReport;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import org.datatist.sdk.DatatistSDK;
import org.datatist.sdk.IDatatist;
import org.datatist.sdk.Track;
import org.datatist.sdk.TrackerKernel;
import org.datatist.sdk.data.PushInfo;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import cn.campusapp.router.Router;
import cn.campusapp.router.router.IActivityRouteTableInitializer;
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

import static com.tendcloud.tenddata.TalkingDataSMS.mContext;

//import com.networkbench.agent.impl.NBSAppAgent;

//import android.support.multidex.MultiDex;


/**
 * Created by Sun on 2016/6/22.
 */
public class App extends BaseApplication implements IDatatist {

    //final String APP_KEY = "23310446";

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public String setTrackerUrl() {

        return "https://t.analyzer.datatist.cn/c.gif";
    }

    @Override
    public String setSiteId() {
        return "dmtY9Tmjcryw8PKC";
    }

    @Override
    public String setOTrackerUrl() {
        return "https://tracker.analyzer.datatist.cn";
    }

    @Override
    public String setOSiteId() {
        return "43";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        //集成极光推送
        DatatistSDK.init(this, this);


        //RestRetrofit.init(this, BuildConfig.BASEURL);

        //mContext = getApplicationContext();


        //IMRedDotManager.newInstent().appInit(this);

        DemoCache.setContext(this);
        // 注册小米推送，参数：小米推送证书名称（需要在云信管理后台配置）、appID 、appKey，该逻辑放在 NIMClient init 之前
        NIMPushClient.registerMiPush(this, "DEMO_MI_PUSH", "2882303761517502883", "5671750254883");
        // 注册华为推送，参数：华为推送证书名称（需要在云信管理后台配置）
        NIMPushClient.registerHWPush(this, "DEMO_HW_PUSH");

        // 注册自定义推送消息处理，这个是可选项
        NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions());

        // crash handler
        AppCrashHandler.getInstance(this);

//         以下逻辑只在主进程初始化时执行
        if (inMainProcess()) {
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

        Unicorn.init(this, "5690717053c82f04333c095b681c5121", options(), new GlideImageLoader(this));


        //td 统计
        TCAgent.LOG_ON = true;
        TCAgent.init(this, "6A8EEFEAC7F546CEA39F077D95AD9D2C", BuildConfig.FLAVOR);
        TCAgent.setReportUncaughtExceptions(true);

        /*if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }*/
        BASE_URL = BuildConfig.BASEURL;//"http://p2ptest.oudianyun.com"
//        COMPANYID = BuildConfig.COMPANYID;//
        H5URL = BuildConfig.H5URL;
        SCHEME = BuildConfig.SCHEME;
//        APP_KEY  = "23015524";
        APP_KEY = OdyApplication.getValueByKey(Constants.APP_KEY, "");

//        LeakCanary.install(this);
        VERSION_NAME = BuildConfig.VERSION_NAME;
//        H5URL = BuildConfig.H5URL;
//        OVERSEA = BuildConfig.OVERSEA;
//        NBSAppAgent.setLicenseKey("a475f55f031f47e79cad33577165d28e").withLocationServiceEnabled(true).splash(this.getApplicationContext());
        Router.initActivityRouter(getApplicationContext(), BuildConfig.SCHEME, new IActivityRouteTableInitializer() {
            @Override
            public void initRouterTable(Map<String, Class<? extends Activity>> router) {

                router.put(getRouterUrl(JumpUtils.FFRAGMENT_LOGIN), LyfLoginFragment.class);//登录
                router.put(getRouterUrl(JumpUtils.COMMISSION_CODE), LyfCommissionCode.class);//分享佣金说明页
//                router.put(getRouterUrl(JumpUtils.REGISTER), LyfLoginFragment.class);//注册
                router.put(getRouterUrl(JumpUtils.SEARCH_RESULT), LyfSearchResultActivity.class);
                router.put(getRouterUrl(JumpUtils.ARRIVAL_NOTIF), ArrivalNotifaActivity.class);//到货通知
                router.put(getRouterUrl(JumpUtils.MAIN), MainActivity.class);
                router.put(getRouterUrl(JumpUtils.LOGIN), LyfLoginFragment.class);
                router.put(getRouterUrl(JumpUtils.SMSLOGIN), LyfLogin.class);
                router.put(getRouterUrl(JumpUtils.SEARCH), LYFSearchActivity.class);
                router.put(getRouterUrl(JumpUtils.PAYFAIL), PayFailActivity.class);
                router.put(getRouterUrl(JumpUtils.PAYSUCCESS), PaySuccessActivity.class);
                router.put(getRouterUrl(JumpUtils.PRODUCTDETAIL), LyfProductDetailActivity.class);
                router.put(getRouterUrl(JumpUtils.CONFIRMORDER), LYFConfirmOrderActivity.class);
                router.put(getRouterUrl(JumpUtils.SELECT_ADDRESS), ChooseAddressActivity.class);
                router.put(getRouterUrl(JumpUtils.ABOUTUS), AboutMeActivity.class);
                router.put(getRouterUrl(JumpUtils.SETTING), SettingsActivity.class);
                router.put(getRouterUrl(JumpUtils.PAYONLINE), CashierStandActivity.class);
                router.put(getRouterUrl(JumpUtils.ORDERLIST), com.lyfen.android.ui.activity.order.OrderListActivity.class);
                router.put(getRouterUrl(JumpUtils.ORDERDETAIL), OrderDetailActivity.class);
                router.put(getRouterUrl(JumpUtils.SCANHISTORY), ScanHistoryActivity.class);
                router.put(getRouterUrl(JumpUtils.LOGISTICS), LogisticsActivity.class);
                router.put(getRouterUrl(JumpUtils.AFTERSALE), AftersaleActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOPCART), LyfShopCartActivity.class);
                router.put(getRouterUrl(JumpUtils.MYLIKE), MylikeActivity.class);
                router.put(getRouterUrl(JumpUtils.REFOUNDLIST), RefoundListActivity.class);
                router.put(getRouterUrl(JumpUtils.COUPON), CouponActivity.class);
                router.put(getRouterUrl(JumpUtils.USECOUPON), CouponUseActivity.class);
                router.put(getRouterUrl(JumpUtils.ADDCOUPON), AddCouponActivity.class);
                router.put(getRouterUrl(JumpUtils.ADDRESS_MANAGER), ReceiveAddressActivity.class);
                router.put(getRouterUrl(JumpUtils.EDIT_ADDRESS), EditAddressActivity.class);
                router.put(getRouterUrl(JumpUtils.AFTERSALEINFO), RefoundInfoActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOWIMAGE), ShowImageViewActivity.class);
                router.put(getRouterUrl(JumpUtils.CHOOSEPAYWAY), ChoosePayWayActivity.class);
                router.put(getRouterUrl(JumpUtils.ORDERSEARCH), OrdersearchActivity.class);
                router.put(getRouterUrl(JumpUtils.SWEEP), LyfSweepActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOP_EVALUATE), EvaluateActivity.class);
                router.put(getRouterUrl(JumpUtils.INVOICE), BillActivity.class);
                router.put(getRouterUrl(JumpUtils.PICCHOOSE), PhotoPickerActivity.class);
                router.put(getRouterUrl(JumpUtils.CHOOSE_LOGISTICS), LogisticsCompanyActivity.class);
                router.put(getRouterUrl(JumpUtils.GIFTCARD), GiftCardActivity.class);
                router.put(getRouterUrl(JumpUtils.BIND_GIFTCARD), BindGiftCardActivity.class);
                router.put(getRouterUrl(JumpUtils.USE_GIFTCARD), UsegiftcardActivity.class);
                router.put(getRouterUrl(JumpUtils.GIFTCARD_CONSUMER), GiftCardConsumerActivity.class);
                router.put(getRouterUrl(JumpUtils.LOCATION), ProvinceListActivity.class);
                router.put(getRouterUrl(JumpUtils.DISTRIBUTION), DistributionActivity.class);
                router.put(getRouterUrl(JumpUtils.H5), LyfWebActivity.class);
                router.put(getRouterUrl(JumpUtils.CHOOSE_STORE), ChooseStoreActivity.class);
                router.put(getRouterUrl(JumpUtils.SCAN_LIST), ScanProductListActivity.class);
                router.put(getRouterUrl(JumpUtils.POINT_CARD), PointCardSearchActivity.class);
                router.put(getRouterUrl(JumpUtils.CHOOSE_CONTACTS), ContactsActivity.class);
                router.put(getRouterUrl(JumpUtils.UPLEVEL), UpLevelActivity.class);

                router.put(getRouterUrl(JumpUtils.RED_PACKET), RedPacketActivity.class);
                router.put(getRouterUrl(JumpUtils.PAY_FOR_LEISURELY), PayForLeisurelyActivity.class);
                router.put(getRouterUrl(JumpUtils.YWTPAY), YIActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOP_HOME), LyfStoreHomeActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOP_INFO), StoreDetailActivity.class);
                router.put(getRouterUrl(JumpUtils.SHOP_CATEGORT), StoreCategoryActivity.class);
                router.put(getRouterUrl(JumpUtils.INVITE_FRIENDS), InviteFriendsActivity.class);//邀请好友
                router.put(getRouterUrl(JumpUtils.QIANGGOU), com.lyfen.android.ui.activity.qianggou.QiangGouActivity.class);
                router.put(getRouterUrl(JumpUtils.FLASHSALE), ShangouActivity.class);
            }
        });

        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, APP_KEY);
        }
        Router.initBrowserRouter(getApplicationContext());
        initTalkingData();
        EventBus.getDefault().register(App.this);
//        LocationManager.initLocation(this);
//       Timber.plant(new Timber.DebugTree());
        //IMHelper.getInstance().init(getApplicationContext());


        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(BuildConfig.FLAVOR);
        strategy.setAppVersion(BuildConfig.VERSION_NAME);

        CrashReport.initCrashReport(this, "834971cd4b", !BuildConfig.DEBUG, strategy);

        //统计的
        TrackerKernel trackerKernel = DatatistSDK.getTracker();
        if (trackerKernel != null) {
            trackerKernel.setChannelId(BuildConfig.FLAVOR);//渠道信息
        }

        //获取动态参数
        getgobalParams();

        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONJPUSH);
        EventBus.getDefault().post(msg);

        getPersonalMes();

        getGuangGao();


    }

    public void getGuangGao() {


    }

    public void getPersonalMes() {

        if (!TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
            Map<String, String> params = new HashMap<>();
            params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));

            OkHttpManager.getAsyn(Constants.PERSONALDATA, params, new OkHttpManager.ResultCallback<PersonalBean>() {
                @Override
                public void onError(Request request, Exception e) {


                }

                @Override
                public void onResponse(PersonalBean response) {


                    if (response != null) {
                        try {

                            OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
                            TaklingDataEventMessage msg = new TaklingDataEventMessage();
                            msg.setAction(TaklingDataEventMessage.ONSPLASH);
                            Map<String, String> map = new HashMap<>();
                            EventBus.getDefault().post(msg);

                        } catch (Exception e) {

                        }

                    }
                }
            });
        } else {

            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONSPLASH);
            Map<String, String> map = new HashMap<>();
            EventBus.getDefault().post(msg);
        }
    }

    public void getgobalParams() {


        if (!TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
            Map<String, String> params = new HashMap<>();
            params.put("token", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
            params.put("terminal", "Andriod");
            OkHttpManager.getAsyn(Constants.POLICY, params, new OkHttpManager.ResultCallback<PolicyBean>() {
                @Override
                public void onError(Request request, Exception e) {


                }

                @Override
                public void onFailed(String code, String msg) {
                    super.onFailed(code, msg);
                }

                @Override
                public void onResponse(PolicyBean response) {
                    if (null != response && response.data.size() != 0) {
                        for (int i = 0; i < response.data.size(); i++) {
                            if (response.data.get(i).type.equals("1")) {
                                OdyApplication.putValueByKey(Constants.IM_TOGGLE, response.data.get(i).status);
                                Log.i("Main", response.data.get(i).status);
                            }
                            if (response.data.get(i).type.equals("2")) {

                                OdyApplication.putValueByKey(Constants.SERVICE_TOGGLE, response.data.get(i).status);
                            }
                            if (response.data.get(i).type.equals("3")) {
                                Preferences.saveRedPacketCanSend(response.data.get(i).status.equals("1"));
                            }
                        }
                        //发送eventbus
                        //saveUserInfo(response.getData());
                    }
                }
            });
        }


    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        return options;
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


    /**
     * TalkingData集成
     */
    private void initTalkingData() {
        //        请你们 下次开发 仔细阅读文档 文档读一下
        TalkingDataAppCpa.init(this, "90c2f0a4393440cf85ed3cd90e00acb2", BuildConfig.FLAVOR);
    }

    /**
     * 数据统计统一处理入口
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(TaklingDataEventMessage event) {

        Map<String, String> extra = event.getExtra();
        if (extra == null) {
            extra = new HashMap<>();
        }
        switch (event.getAction()) {
            case TaklingDataEventMessage.ONRECEIVEDEEPLINK://应用唤起

                Log.d("TDLog", "应用唤起");
                TalkingDataAppCpa.onReceiveDeepLink(extra.get("link"));
                break;
            case TaklingDataEventMessage.ONREGISTER://注册成功


                Log.d("TDLog", "注册成功");
                TalkingDataAppCpa.onRegister(extra.get("userId"));


                try {

                    Map regMap = new HashMap();
                    regMap.put("userid", extra.get("userId"));
                    MobclickAgent.onEvent(this, "__register", regMap);

                    Track.track().tracker().setUserId(OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));

                    Track.track().register().trackRegister(extra.get("id"), "", true, new HashMap<>()).submit(DatatistSDK.getTracker());


                } catch (Exception e) {

                }
                break;
            case TaklingDataEventMessage.ONLOGIN://登录成功
                Log.d("TDLog", "登录成功");
                TalkingDataAppCpa.onLogin(extra.get("userId"));


                try {

                    Map loginMap = new HashMap();
                    MobclickAgent.onEvent(this, "__login", loginMap);


                    Track.track().tracker().setUserId(OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));
                    final Map<String, String> eventMap = new HashMap<>();
                    eventMap.put("手机号", extra.get("userId"));
                    Track.track().login().trackLogin(extra.get("id"), eventMap).submit(DatatistSDK.getTracker());
                } catch (Exception e) {

                }


                break;
            case TaklingDataEventMessage.ONORDERPAYSUCC://支付成功
                Log.d("TDLog", "支付成功");


                TalkingDataAppCpa.onOrderPaySucc(extra.get("account"), extra.get("orderid"), DataUtils.parseStringToTalkingPrice(extra.get("amount")),
                        extra.get("currencyType"), extra.get("payType"));


                try {


                    final Map<String, String> eventMap = new HashMap<>();

                    Track.track().payment().trackPayment(extra.get("orderid"), extra.get("payType"), Boolean.parseBoolean(extra.get("payStatus")), Double.parseDouble(extra.get("amount")), eventMap).submit(DatatistSDK.getTracker());

                    Map successPayMap = new HashMap();
                    successPayMap.put("userid", extra.get("userId"));
                    successPayMap.put("orderid", extra.get("orderid"));
                    successPayMap.put("item", extra.get("name"));
                    successPayMap.put("amount", DataUtils.parseStringToTalkingPrice(extra.get("amount")));
                    MobclickAgent.onEvent(this, "__finish_payment", successPayMap);
                } catch (Exception e) {

                }

                break;
            case TaklingDataEventMessage.ONVIEWITEM://查看商品
                Log.d("TDLog", "进入商详页，查看商品");
                String unitPrice = extra.get("unitPrice");
                TalkingDataAppCpa.onViewItem(extra.get("itemId"), extra.get("category"), extra.get("name"), DataUtils.parseStringToTalkingPrice(extra.get("unitPrice")));

                try {
                    Track.track().productPage().trackProductPage(extra.get("itemId"), "商品列表", extra.get("category"), extra.get("name"), Double.parseDouble(extra.get("orginalPrice")), Double.parseDouble(extra.get("nowPrice")), new HashMap<>()).submit(DatatistSDK.getTracker());
                } catch (Exception e) {

                }

                break;
            case TaklingDataEventMessage.ONPLACEORDER://提交订单
                Log.d("TDLog", "提交订单");
                TalkingDataAppCpa.onPlaceOrder(extra.get("accountid"), event.getOrder());


                try {
                    Track.track().order().trackOrder(event.orderInfo, event.couponInfo, event.productInfo, new HashMap<>()).submit(DatatistSDK.getTracker());


                    Map beginPayMap = new HashMap();
                    beginPayMap.put("userid", extra.get("userId"));
                    beginPayMap.put("orderid", extra.get("accountid"));
                    beginPayMap.put("item", extra.get("name"));
                    beginPayMap.put("amount", Double.parseDouble(extra.get("amount")));
                    MobclickAgent.onEvent(this, "__submit_payment", beginPayMap);


                } catch (Exception e) {

                }


                break;
            case TaklingDataEventMessage.ONADDITEMTOSHOPPINGCART://加车
                Log.d("TDLog", "添加商品到购物车");
                // TalkingDataAppCpa.onAddItemToShoppingCart(extra.get("itemId"), extra.get("category"), extra.get("name"), DataUtils.parseStringToTalkingPrice(extra.get("unitPrice")), Integer.parseInt(extra.get("amount")));


                try {

                    Track.track().cart()
                            .trackAddCart(extra.get("itemId"), 1, Double.parseDouble(extra.get("unitPrice")), new HashMap<>())
                            .submit(DatatistSDK.getTracker());


                    Map mAddCartMap = new HashMap();
                    mAddCartMap.put("item", extra.get("name"));
                    mAddCartMap.put("amount", Double.parseDouble(extra.get("unitPrice")));
                    MobclickAgent.onEvent(mContext, "__add_cart", mAddCartMap);
                } catch (Exception e) {

                }


                break;
            case TaklingDataEventMessage.ONVIEWSHOPPINGCART://查看购物车
                Log.d("TDLog", "查看购物车");
                TalkingDataAppCpa.onViewShoppingCart(event.getShoppingCart());
                break;
            case TaklingDataEventMessage.ONJPUSH://推送
                Log.d("TDLog", "推送");
                //TalkingDataAppCpa.onViewShoppingCart(event.getShoppingCart());

                String id = JPushInterface.getRegistrationID(App.this);
                if (!TextUtils.isEmpty(id)) {
                    Log.i("JPUSH", id);
                    Track.track().initJPushEvent().trackInitJPush(JPushInterface.getRegistrationID(App.this), new HashMap<>()).submit(DatatistSDK.getTracker());
                }


                break;

            case TaklingDataEventMessage.ONSEARCH://搜索
                Log.d("TDLog", "搜索");
                //TalkingDataAppCpa.onViewShoppingCart(event.getShoppingCart());
                try {
                    Track.track().search().trackSearch(extra.get("searchword"), Boolean.parseBoolean(extra.get("recommend")), Boolean.parseBoolean(extra.get("historySearch")), new HashMap<>()).submit(DatatistSDK.getTracker());
                } catch (Exception e) {

                }

                break;

            case TaklingDataEventMessage.ONSPLASH://启动页
                if (!TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""))) {
                    Track.track().tracker().setUserId(OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));
                }

                Track.track().event().trackEvent("init", new HashMap<>()).submit(DatatistSDK.getTracker());
                break;

            case TaklingDataEventMessage.ONCHARGE://充值
                try {
                    Track.track().preCharge().trackPreCharge(Double.parseDouble(extra.get("amount")), extra.get("payType"), Double.parseDouble(extra.get("couponAMT")), Boolean.parseBoolean(extra.get("payStatus")), new HashMap<>()).submit(DatatistSDK.getTracker());
                } catch (Exception e) {

                }
                break;
            case "":
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(PushClickEventMessage event) {

        try {

            PushInfo pushInfo = new PushInfo("", event.registId, "");
            Track.track().jpushEvent().trackJPush(pushInfo, event.intent, new HashMap<>()).submit(DatatistSDK.getTracker());
        } catch (Exception e) {

        }

    }

    @Subscribe
    public void onEventMainThread(PushFirstRegistEvent event) {

        try {


            Track.track().initJPushEvent().trackInitJPush(event.id, new HashMap<>()).submit(DatatistSDK.getTracker());
        } catch (Exception e) {

        }

    }


}
