
package com.netease.nim.demo.main.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.NimInitManager;
import com.netease.nim.demo.NimSDKOptionConfig;
import com.netease.nim.demo.chatroom.ChatRoomSessionHelper;
import com.netease.nim.demo.common.util.sys.SystemUtil;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHelper;
import com.netease.nim.demo.login.LogoutHelper;
import com.netease.nim.demo.main.fragment.YunXinFragment;
import com.netease.nim.demo.main.reminder.ReminderItem;
import com.netease.nim.demo.main.reminder.ReminderManager;
import com.netease.nim.demo.mixpush.DemoMixPushMessageHandler;
import com.netease.nim.demo.session.NimDemoLocationProvider;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.uikit.NimUIKit;

import com.netease.nim.uikit.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;

import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.YiModel;
import com.ody.p2p.eventbus.EventMessage;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.msg.UnicornMessage;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2017/12/16.
 */

public class IMRedDotManager implements ReminderManager.UnreadNumChangedCallback {
    private static IMRedDotManager mIMRedDotManager;
    private List<RecentContact> loadedRecents = new ArrayList<RecentContact>();
    private List<RecentContact> items = new ArrayList<RecentContact>();
    private YiModel model = new YiModel();
    private static Context context;

    public static IMRedDotManager newInstent() {
        if (null == mIMRedDotManager) {
            mIMRedDotManager = new IMRedDotManager();
        }
        return mIMRedDotManager;
    }


    public void init(Context context) {

        EventBus.getDefault().register(this);


        IminitView();
        //appInit(context);
        registerMsgUnreadInfoObserver(true);


    }

    private void IminitView() {
        NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

            @Override
            public void onResult(int code, List<RecentContact> recents, Throwable exception) {
                if (code != ResponseCode.RES_SUCCESS || recents == null) {
                    return;
                }
                loadedRecents = recents;
                int unreadNum = 0;
                for (RecentContact r : recents) {
                    unreadNum += r.getUnreadCount();
                }

                onRecentContactsLoaded();
            }
        });
        registerObservers(true);
    }

    private void onRecentContactsLoaded() {
        items.clear();
        if (loadedRecents != null) {
            for (RecentContact loadedRecent : loadedRecents) {
                if (!NimUIKit.getAccount().equals(loadedRecent.getContactId())) {
                    items.add(loadedRecent);
                }
            }

            loadedRecents = null;
        }
        refreshMessages();
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            onRecentContactChanged(recentContacts);

        }
    };

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
    }


    private void onRecentContactChanged(List<RecentContact> recentContacts) {
        int index;
        for (RecentContact r : recentContacts) {
            index = -1;
            for (int i = 0; i < items.size(); i++) {
                if (r.getContactId().equals(items.get(i).getContactId())
                        && r.getSessionType() == (items.get(i).getSessionType())) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                items.remove(index);
            }

            items.add(r);
        }
        refreshMessages();
    }


    private void refreshMessages() {
        // 方式一：累加每个最近联系人的未读（快）
        int unreadNum = 0;
        for (RecentContact r : items) {
            unreadNum += r.getUnreadCount();
        }

        EventMessage msg = new EventMessage();
        msg.flag = EventMessage.REDNUM;
        msg.redNumber = unreadNum;
        EventBus.getDefault().post(msg);

    }


    @Subscribe
    public void onEventMainThread(EventMessage event) {

        if (event.flag == EventMessage.REFRESH_UT) {

            if (TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
                //注销,替换成原来的

                NIMClient.getService(AuthService.class).logout();
                Unicorn.logout();
                LogoutHelper.logout();


            }
        }
    }

    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    public void destoryEvent(){
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        Log.e("ReminderItem", "----------:" + item.getUnread() + "");
        if (item.getId() == 0) {
            // tab_bar.showDot(2, true, item.getUnread());
            EventMessage msg = new EventMessage();
            msg.flag = EventMessage.REDNUM;
            msg.redNumber = item.getUnread();
            EventBus.getDefault().post(msg);
        }

    }

    public void appInit(Context context) {

        DemoCache.setContext(context);

        NIMPushClient.registerMiPush(context, "DEMO_MI_PUSH", "2882303761517502883", "5671750254883");
        // 注册华为推送，参数：华为推送证书名称（需要在云信管理后台配置）
        NIMPushClient.registerHWPush(context, "DEMO_HW_PUSH");

        // 注册自定义推送消息处理，这个是可选项

        // 注册自定义推送消息处理，这个是可选项
        NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
        NIMClient.init(context, getLoginInfo(), NimSDKOptionConfig.getSDKOptions());

        // crash handler
//        AppCrashHandler.getInstance(this);

        // 以下逻辑只在主进程初始化时执行
        if (inMainProcess(context)) {
            // 初始化红包模块，在初始化UIKit模块之前执行
//            NIMRedPacketClient.init(this);
            // init pinyin
            PinYin.init(context);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit(context);
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

    public boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = SystemUtil.getProcessName(context);
        return packageName.equals(processName);
    }

    private void initUIKit(Context context) {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(context);
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
