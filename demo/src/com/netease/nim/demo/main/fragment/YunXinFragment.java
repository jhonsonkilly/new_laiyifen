package com.netease.nim.demo.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.NimApplication;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.ui.viewpager.FadeInOutPageTransformer;
import com.netease.nim.demo.common.ui.viewpager.PagerSlidingTabStrip;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHttpClient;
import com.netease.nim.demo.contact.activity.AddFriendActivity;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.contact.constant.UserConstant;
import com.netease.nim.demo.contact.helper.UserUpdateHelper;
import com.netease.nim.demo.login.LogoutHelper;
import com.netease.nim.demo.main.activity.CustomScanActivity;
import com.netease.nim.demo.main.activity.GlobalSearchActivity;
import com.netease.nim.demo.main.activity.MainActivity;
import com.netease.nim.demo.main.activity.SettingsActivity;
import com.netease.nim.demo.main.adapter.MainTabPagerAdapter;
import com.netease.nim.demo.main.helper.SystemMessageUnreadManager;
import com.netease.nim.demo.main.model.MainTab;
import com.netease.nim.demo.main.reminder.ReminderItem;
import com.netease.nim.demo.main.reminder.ReminderManager;
import com.netease.nim.demo.main.widget.MyPopWindow;
import com.netease.nim.demo.team.TeamAVChatHelper;
import com.netease.nim.demo.team.TeamCreateHelper;
import com.netease.nim.demo.teamavchat.activity.TeamAVChatActivity;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.drop.DropCover;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.contact.core.item.ContactIdFilter;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nim.uikit.plugin.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nim.uikit.team.model.TeamRequestCode;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.ody.p2p.utils.JumpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云信主界面（导航页）
 */
public class YunXinFragment extends TFragment implements OnPageChangeListener, ReminderManager.UnreadNumChangedCallback {

    private PagerSlidingTabStrip tabs;

    private ViewPager pager;

    private int scrollState;

    private MainTabPagerAdapter adapter;

    private View rootView;

    private Toolbar toolbar;

    private Button btLogin;

    private LinearLayout noLogin;

    private AlertDialog mDialog;

    //传入参数
    private String accountId;
    private String mobile;
    private String imageUrl;
    private String nickName;
    private String token;
    private String sex;


    private AbortableFuture<LoginInfo> loginRequest;
    AbortableFuture<String> uploadAvatarFuture;

    private static final String EXTRA_APP_QUIT = "APP_QUIT";
    private static final int REQUEST_CODE_NORMAL = 1;
    private static final int REQUEST_CODE_ADVANCED = 2;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;

    private static final int REQUEST_SCAN = 11;//扫描
    private RelativeLayout main_rl;

    public YunXinFragment() {
        setContainerId(R.id.welcome_container);
    }

    public YunXinFragment(String accountId, String mobile, String imageUrl
            , String nickName, String token, String sex) {
        setContainerId(R.id.welcome_container);

    }

    public static YunXinFragment newInstance(String accountId, String mobile, String imageUrl
            , String nickName, String token, String sex) {

        YunXinFragment fragment = new YunXinFragment();


        Bundle args = new Bundle();

        args.putString("accountId", accountId);
        args.putString("mobile", mobile);
        args.putString("imageUrl", imageUrl);
        args.putString("nickName", nickName);
        args.putString("token", token);
        args.putString("sex", sex);
        fragment.setArguments(args);

        return fragment;
    }

    public void setData(String accountId, String mobile, String imageUrl
            , String nickName, String token, String sex) {

        this.accountId = accountId;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.token = token;
        this.sex = sex;

        if (token.equals("")) {
            Preferences.saveUserAccount("");
            Preferences.saveUserMainToken("");
            noLogin.setVisibility(View.VISIBLE);
            main_rl.setVerticalGravity(View.GONE);
            // 清理缓存&注销监听
            LogoutHelper.logout();
            NIMClient.getService(AuthService.class).logout();
            registerMsgUnreadInfoObserver(false);
            registerSystemMessageObservers(false);
        } else {
            main_rl.setVerticalGravity(View.VISIBLE);
            noLogin.setVisibility(View.GONE);

        }
        if (!token.equals(Preferences.getUserMainToken())) {
            register(true);
        }
    }

    //让Fragment中的onCreateOptionsMenu生效
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main, container, false);

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            accountId = bundle.getString("accountId");
            mobile = bundle.getString("mobile");
            imageUrl = bundle.getString("imageUrl");
            nickName = bundle.getString("nickName");
            token = bundle.getString("token");
//            token = "";
            sex = bundle.getString("sex");
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolBar(R.id.toolbar, R.string.Main_tab_title, R.drawable.actionbar_dark_logo);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        btLogin = findView(R.id.im_login);
        main_rl = findView(R.id.main_rl);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.ToActivity(JumpUtils.LOGIN);
            }
        });
        noLogin = findView(R.id.ll_nologin);

        setTitle(R.string.Main_tab_title);

        Drawable drawable = getResources().getDrawable(R.drawable.icon_cuxiao);
        drawable.setBounds(10, 10, 10, 10);
        toolbar.setOverflowIcon(drawable);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.Main_tab_title;
        options.isNeedNavigate = false;
        setToolBar(R.id.toolbar, options);


        if (token.equals("")) {
            Preferences.saveUserAccount(accountId);
            Preferences.saveUserMainToken(this.token);
            noLogin.setVisibility(View.VISIBLE);
        } else {
            Log.e("112312313", "register: ");
            noLogin.setVisibility(View.GONE);
            register(false);
        }
    }


    private void initFragment() {
        findViews();
        setupPager();
        setupTabs();
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
        initUnreadCover();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TO TABS
        tabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
        // TO ADAPTER
        adapter.onPageScrolled(position);
    }

    @Override
    public void onPageSelected(int position) {
        // TO TABS
        tabs.onPageSelected(position);

        selectPage(position);

//        enableMsgNotification(false);

        if (position == 1) {
            ContactListFragment item = (ContactListFragment) adapter.getItem(1);
            item.showDialog();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TO TABS
        tabs.onPageScrollStateChanged(state);

        scrollState = state;

        selectPage(pager.getCurrentItem());
    }

    private void selectPage(int page) {
        // TO PAGE
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            adapter.onPageSelected(pager.getCurrentItem());
        }


    }

    public void switchTab(int tabIndex, String params) {
        pager.setCurrentItem(tabIndex);
    }

    /**
     * 查找页面控件
     */
    private void findViews() {
        tabs = findView(R.id.tabs);
        pager = findView(R.id.main_tab_pager);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
        registerSystemMessageObservers(false);
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean onClick(View v) {
        return true;
    }

    /**
     * 设置viewPager
     */
    private void setupPager() {
        // CACHE COUNT
        adapter = new MainTabPagerAdapter(getFragmentManager(), getActivity(), pager);
        pager.setOffscreenPageLimit(adapter.getCacheCount());
        // page swtich animation
        pager.setPageTransformer(true, new FadeInOutPageTransformer());
        // ADAPTER
        pager.setAdapter(adapter);
        // TAKE OVER CHANGE
        pager.setOnPageChangeListener(this);


    }

    /**
     * 设置tab条目
     */
    private void setupTabs() {
        tabs.setOnCustomTabListener(new PagerSlidingTabStrip.OnCustomTabListener() {
            @Override
            public int getTabLayoutResId(int position) {
                return R.layout.tab_layout_main;
            }

            @Override
            public boolean screenAdaptation() {
                return true;
            }
        });
        tabs.setViewPager(pager);
        tabs.setOnTabClickListener(adapter);
        tabs.setOnTabDoubleTapListener(adapter);
    }

    private void enableMsgNotification(boolean enable) {
        boolean msg = (pager.getCurrentItem() != MainTab.RECENT_CONTACTS.tabIndex);
        if (enable | msg) {
            /**
             * 设置最近联系人的消息为已读
             *
             * @param account,    聊天对象帐号，或者以下两个值：
             *                    {@link #MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
             *                    {@link #MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
             */
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 未读消息数量观察者实现
     */
    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        MainTab tab = MainTab.fromReminderId(item.getId());
        if (tab != null) {
            //去除通讯录未读消息红点
            if (item.getId() == 0) {
                tabs.updateTab(tab.tabIndex, item);
            }
        }
    }

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    /**
     * 初始化未读红点动画
     */
    private void initUnreadCover() {
        DropManager.getInstance().init(getContext(), (DropCover) findView(R.id.unread_cover),
                new DropCover.IDropCompletedListener() {
                    @Override
                    public void onCompleted(Object id, boolean explosive) {
                        if (id == null || !explosive) {
                            return;
                        }

                        if (id instanceof RecentContact) {
                            RecentContact r = (RecentContact) id;
                            NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                            LogUtil.i("YunXinFragment", "clearUnreadCount, sessionId=" + r.getContactId());
                        } else if (id instanceof String) {
                            if (((String) id).contentEquals("0")) {
                                NIMClient.getService(MsgService.class).clearAllUnreadCount();
                                LogUtil.i("YunXinFragment", "clearAllUnreadCount");
                            } else if (((String) id).contentEquals("1")) {
                                NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
                                LogUtil.i("YunXinFragment", "clearAllSystemUnreadCount");
                            }
                        }
                    }
                });
    }

    public void setToolBar(int toolBarId, ToolBarOptions options) {
        //        toolbar = (Toolbar) findViewById(toolBarId);
        if (options.titleId != 0) {
            toolbar.setTitle(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            toolbar.setTitle(options.titleString);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
        mAppCompatActivity.setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setContentInsetStartWithNavigation(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }


    //查找用户
    private void query(String accountStr) {
//        DialogMaker.showProgressDialog(getActivity(), null, false);
        final String account = accountStr;
        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo user) {
                DialogMaker.dismissProgressDialog();
                if (user == null) {
                    EasyAlertDialogHelper.showOneButtonDiolag(getActivity(), R.string.user_not_exsit,
                            R.string.user_tips, R.string.ok, false, null);
                } else {
                    UserProfileActivity.start(getActivity(), account,"扫描二维码添加");
                }
            }

            @Override
            public void onFailed(int code) {
//                DialogMaker.dismissProgressDialog();
                if (code == 408) {
                    Toast.makeText(getActivity(), R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "on failed:" + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
//                DialogMaker.dismissProgressDialog();
                Toast.makeText(getActivity(), "on exception:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //注册登录
    private void register(final boolean isReload) {
        Log.e("register", "register" + isReload);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.customDialog);

        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, null));
        mDialog = builder.create();
        mDialog.show();

        if (!NetworkUtil.isNetAvailable(getActivity())) {
            Toast.makeText(getActivity(), R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }

        ContactHttpClient.getInstance().register(accountId, nickName, accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                login(isReload);
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                try {
                    if (code == 414) {
                        ContactHttpClient.getInstance().updateAccounyt(accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                login(isReload);
                            }

                            @Override
                            public void onFailed(int code, String errorMsg) {
                                Toast.makeText(getActivity(),"登录失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(),"登录失败，请重试", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void login(final boolean isReload) {
        Log.e("login", "login" + isReload);
        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(accountId, accountId), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
//                LogUtil.i(TAG, "login success");
                onLoginDone();

                DemoCache.setAccount(accountId);
                saveLoginInfo(accountId, accountId);

                // 初始化消息提醒配置
                initNotificationConfig();

                RequestCallbackWrapper callback = new RequestCallbackWrapper() {
                    @Override
                    public void onResult(int code, Object result, Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                        if (code == ResponseCode.RES_SUCCESS) {

                        } else if (code == ResponseCode.RES_ETIMEOUT) {
                        }
                    }
                };

                Map<Integer, UserInfoFieldEnum> fields = new HashMap<>();
                fields.put(UserConstant.KEY_NICKNAME, UserInfoFieldEnum.Name);
                fields.put(UserConstant.KEY_PHONE, UserInfoFieldEnum.MOBILE);
                fields.put(UserConstant.KEY_SIGNATURE, UserInfoFieldEnum.SIGNATURE);
                fields.put(UserConstant.KEY_EMAIL, UserInfoFieldEnum.EMAIL);
                fields.put(UserConstant.KEY_BIRTH, UserInfoFieldEnum.BIRTHDAY);
                fields.put(UserConstant.KEY_GENDER, UserInfoFieldEnum.GENDER);
                fields.put(8, UserInfoFieldEnum.AVATAR);

                Integer gender;
                if (sex.equals("1")) {
                    gender = Integer.valueOf(1);
                } else
                    gender = Integer.valueOf(2);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_NICKNAME), nickName, callback);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_GENDER), gender, callback);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_PHONE), mobile, callback);
                UserUpdateHelper.update(fields.get(8), imageUrl, callback);


                // 加载主界面
                addUser();

                initData(isReload);
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    ContactHttpClient.getInstance().updateAccounyt(accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            login(isReload);
                        }

                        @Override
                        public void onFailed(int code, String errorMsg) {
//                            Toast.makeText(getActivity(),"登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "登录失败: " + code, Toast.LENGTH_SHORT).show();
                    register(false);
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(getActivity(), R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

    private void changePwd(final boolean isReload) {
        ContactHttpClient.getInstance().updateAccounyt(accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                login(isReload);
            }

            @Override
            public void onFailed(int code, String errorMsg) {

                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void onLoginDone() {
        loginRequest = null;
        if (mDialog != null) {
            mDialog.dismiss();
        }

//        DialogMaker.dismissProgressDialog();
    }

    /**
     * 修改信息
     */
    private void addUser() {
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", accountId);
        map.put("userName", nickName);
        map.put("mobile", mobile);
        map.put("photoUrl", imageUrl);
        String gender = "";
        if (sex.equals("1")) {
            gender = "男";
        } else
            gender = "女";
        map.put("sex", gender);

        String body = new Gson().toJson(map);


        OKhttpHelper.send(getActivity(), body, Common.AdapterPath + "addUser", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

    //保存登录信息
    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
        Preferences.saveUserMainToken(this.token);
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }


    private void initData(final Boolean isReload) {
        Log.e("initData", "initData" + isReload);
        requestBasicPermission();

//        onParseIntent();

        // 等待同步数据完成
        boolean syncCompleted = LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(new Observer<Void>() {
            @Override
            public void onEvent(Void v) {

                syncPushNoDisturb(UserPreferences.getStatusConfig());

                DialogMaker.dismissProgressDialog();
            }
        });

        LogUtil.i(TAG, "sync completed = " + syncCompleted);
        if (!syncCompleted) {
            DialogMaker.showProgressDialog(getActivity(), getString(R.string.prepare_data)).setCanceledOnTouchOutside(false);
        } else {
            syncPushNoDisturb(UserPreferences.getStatusConfig());
        }
        Log.e("syncCompleted", "syncCompleted" + syncCompleted);

        if (isReload) {
            registerMsgUnreadInfoObserver(false);
            registerMsgUnreadInfoObserver(true);
            if (adapter != null) {
//                pager.removeAllViews();
//                setupPager();
//                adapter.notifyDataSetChanged();
//                onInit();
            } else {
                register(false);

            }

        } else {
            onInit();
        }
    }

    /**
     * 基本权限管理
     */
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, getActivity(), BASIC_PERMISSIONS);
        MPermission.with(getActivity())
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, getActivity(), BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(getActivity(), "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, getActivity(), BASIC_PERMISSIONS);
    }

    private void onInit() {
        // 加载主页面
        initFragment();

        // 聊天室初始化
//        ChatRoomHelper.init();

        LogUtil.ui("NIM SDK cache path=" + NIMClient.getSdkStorageDirPath());
    }


    /**
     * 若增加第三方推送免打扰（V3.2.0新增功能），则：
     * 1.添加下面逻辑使得 push 免打扰与先前的设置同步。
     * 2.设置界面{@link SettingsActivity} 以及
     * 免打扰设置界面{@link com.netease.nim.demo.main.activity.NoDisturbActivity} 也应添加 push 免打扰的逻辑
     * <p>
     * 注意：isPushDndValid 返回 false， 表示未设置过push 免打扰。
     */
    private void syncPushNoDisturb(StatusBarNotificationConfig staConfig) {

        boolean isNoDisbConfigExist = NIMClient.getService(MixPushService.class).isPushNoDisturbConfigExist();

        if (!isNoDisbConfigExist && staConfig.downTimeToggle) {
            NIMClient.getService(MixPushService.class).setPushNoDisturbConfig(staConfig.downTimeToggle,
                    staConfig.downTimeBegin, staConfig.downTimeEnd);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("yunxingonActivityResult",resultCode+"");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_NORMAL) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    TeamCreateHelper.createNormalTeam(getActivity(), selected, false, null);
                } else {
                    Toast.makeText(getActivity(), "请选择至少一个联系人！", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_CODE_ADVANCED) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                TeamCreateHelper.createAdvancedTeam(getActivity(), selected);


            } else if (requestCode == REQUEST_SCAN) {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                String result = data.getStringExtra("scanResult");

                if (result == null) {
                    Toast.makeText(getActivity(), "内容为空", Toast.LENGTH_LONG).show();
                } else {
                    if (result.contains("imGroupQrCode")) {
                        //加群提醒
                        final String code = result.substring(result.indexOf(":\"") + 2, result.length() - 2);
                        NIMClient.getService(TeamService.class).applyJoinTeam(code, null).setCallback(new RequestCallback<Team>() {
                            @Override
                            public void onSuccess(Team team) {
                                ToastUtil.showLongToast(getActivity(), "发送申请成功");
                                //申请入群
                                applyTeam(team);
                            }

                            @Override
                            public void onFailed(int i) {
                                if (i == 808) { //申请已发出
                                    ToastUtil.showLongToast(getActivity(), "发送申请成功");
                                    Team team = TeamDataCache.getInstance().getTeamById(code);
                                    applyTeam(team);
                                } else if (i == 809) { //已经在群里
                                    ToastUtil.showLongToast(getActivity(), "您已经在群里");
                                } else {
                                    ToastUtil.showLongToast(getActivity(), "申请失败,未知异常");
                                }
                            }

                            @Override
                            public void onException(Throwable throwable) {

                            }
                        });

                    } else {
                        getMemberId(result);
                    }
                }
            }
            else if (requestCode == TeamRequestCode.REQUEST_TEAM_VIDEO) {
                NimApplication.chatStatus = 0;
                ArrayList<String> selectedAccounts = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                onSelectedAccountsResult(selectedAccounts);
            } else if (requestCode == TeamRequestCode.REQUEST_TEAM_AUDIO) {
                NimApplication.chatStatus = 1;
                ArrayList<String> selectedAccounts = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                onSelectedAccountsResultAudio(selectedAccounts);
            }
        }

    }

    private void applyTeam(Team team) {
        UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(NimUIKit.getAccount());
        Map<Object, Object> bo = new HashMap();

        Map<Object, Object> body = new HashMap();
        body.put("sysFriendInfoTitle", "入群申请");
        body.put("sysFriendInfoMessage", userInfo.getName() + "申请加入群组<" + team.getName()+">");
        body.put("sysFriendInfoFormid", "desmart4");
        body.put("sysFriendInfoFormName", "系统");
        body.put("sysFriendInfoToId", userInfo.getAccount());
        body.put("sysFriendInfoToName", userInfo.getName());
        body.put("sysFriendInfoGroupId", team.getId());
        body.put("sysFriendInfoIsTip", "0");
        body.put("sysFriendInfoValidID", "");

        bo.put("type","11");
        bo.put("data",body);


        OKhttpHelper.send(getActivity(), new Gson().toJson(bo), Common.AdapterPath + "qrCodeGroup", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });
    }

    /**
     * 音频邀请成功后
     *
     * @param selectedAccounts
     */
    private void onSelectedAccountsResultAudio(final ArrayList<String> selectedAccounts) {
        final String roomName = StringUtil.get32UUID();
        LogUtil.ui("create room " + roomName);
        // 创建房间
        boolean webRTCCompat = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(getString(R.string.nrtc_setting_other_webrtc_compat_key), true);
        AVChatManager.getInstance().createRoom(roomName, "音频", webRTCCompat, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                LogUtil.ui("create room " + roomName + " success !");

                onCreateRoomSuccess(roomName, selectedAccounts, "1");

                String teamName = TeamDataCache.getInstance().getTeamName("");

                TeamAVChatHelper.sharedInstance().setTeamAVChatting(true);
                TeamAVChatActivity.startActivity(getActivity(), false, "", roomName, selectedAccounts, teamName);
            }

            @Override
            public void onFailed(int code) {
                ToastUtil.showLongToast(getActivity(), "创建多人语音失败");
            }

            @Override
            public void onException(Throwable exception) {
                ToastUtil.showLongToast(getActivity(), "创建多人语音失败");
            }
        });
    }

    /**
     * 视频邀请成功后
     *
     * @param selectedAccounts
     */
    private void onSelectedAccountsResult(final ArrayList<String> selectedAccounts) {
        final String roomName = StringUtil.get32UUID();
        LogUtil.ui("create room " + roomName);
        // 创建房间
        boolean webRTCCompat = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(getString(R.string.nrtc_setting_other_webrtc_compat_key), true);
        AVChatManager.getInstance().createRoom(roomName, "视频", webRTCCompat, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                LogUtil.ui("create room " + roomName + " success !");

                onCreateRoomSuccess(roomName, selectedAccounts, "2");

                String teamName = TeamDataCache.getInstance().getTeamName("");

                TeamAVChatHelper.sharedInstance().setTeamAVChatting(true);
                TeamAVChatActivity.startActivity(getActivity(), false, "", roomName, selectedAccounts, teamName);
            }

            @Override
            public void onFailed(int code) {
                ToastUtil.showLongToast(getActivity(), "创建多人视频失败");
            }

            @Override
            public void onException(Throwable exception) {
                ToastUtil.showLongToast(getActivity(), "创建多人视频失败");
            }
        });

    }

    /**
     * @param roomName
     * @param selectedAccounts
     * @param type             1--音频 2--视频
     */
    private void onCreateRoomSuccess(String roomName, ArrayList<String> selectedAccounts, String type) {
        // 对各个成员发送点对点自定义通知

        String content = TeamAVChatHelper.sharedInstance().buildContent(roomName, "", selectedAccounts, type.equals("1") ? "多人语音" : "多人视频", type);
        CustomNotificationConfig config = new CustomNotificationConfig();
        config.enablePush = true;
        config.enablePushNick = false;
        config.enableUnreadCount = true;

        for (String account : selectedAccounts) {
            CustomNotification command = new CustomNotification();
            command.setSessionId(account);
            command.setSessionType(SessionTypeEnum.P2P);
            command.setConfig(config);
            command.setContent(content);
            command.setApnsText("" + this.getString(R.string.t_avchat_push_content));

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int itemId = item.getItemId();
//        if(itemId == R.id.about){
//            startActivity(new Intent(getActivity(), SettingsActivity.class));
//        }else
        if (itemId == R.id.create_regular_team) {
            ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
            advancedOption.multi = false;
            NimUIKit.startContactSelector(getActivity(), advancedOption, REQUEST_CODE_ADVANCED);

        } else if (itemId == R.id.add_btn) {
            if (token.equals("")) {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
            } else {
                MyPopWindow popWindow = new MyPopWindow(getActivity(), this);
                popWindow.showAtDropDownLeft(rootView.findViewById(R.id.add_btn));
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onVideoCall() {
                        createVideo();
                    }

                    @Override
                    public void onAudioCall() {
                        createAudio();
                    }
                });
            }
//                login2();
//               adapter.notifyDataSetChanged();
        } else if (itemId == R.id.add_buddy) {
            AddFriendActivity.start(getActivity());
        } else if (itemId == R.id.search_btn) {
            if (token.equals("")) {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
            } else {
                GlobalSearchActivity.start(getActivity());
            }
        } else if (itemId == R.id.sao) {
            new IntentIntegrator(getActivity())
                    .setOrientationLocked(false)
                    .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                    .initiateScan();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMemberId(String code) {
        Log.e("code", code);
        Map<String, Object> body = new HashMap<>();
        body.put("key", code);
        com.netease.nim.demo.sync.OKhttpHelper.send(getActivity(), new Gson().toJson(body), com.netease.nim.demo.sync.util.Common.AdapterPath + "scancode", new com.netease.nim.demo.sync.OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                JSONObject object = JSON.parseObject(s);
                String memberId = object.getString("memberId");
                String code = object.getString("code");
                Log.e("getMemberId", memberId);
                if (!code.equals("200")) {
                    Toast.makeText(getActivity(), "扫码失败", Toast.LENGTH_LONG).show();
                } else {
                    if (!memberId.equals("0")) {
                        addIMFriend(memberId);
                    } else {
                        Toast.makeText(getActivity(), "扫码失败", Toast.LENGTH_LONG).show();
                    }

                }
            }


            @Override
            public void onSendFail(String err) {

            }
        });

    }

    private void addIMFriend(final String accountId) {
        Map<String, Object> body = new HashMap<>();
        body.put("faccid", accountId);
        com.netease.nim.demo.sync.OKhttpHelper.send(getActivity(), new Gson().toJson(body), com.netease.nim.demo.sync.util.Common.AdapterPath + "addIMFriend", new com.netease.nim.demo.sync.OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                JSONObject object = JSON.parseObject(s);
                String code = object.getString("code");
                if (!code.equals("200")) {
                    Toast.makeText(getActivity(), "扫码失败", Toast.LENGTH_LONG).show();
                } else {
                    query(accountId);
                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }


    /**
     * 发起多人语音
     */
    private void createAudio() {
        List<NimUserInfo> allUsersOfMyFriend = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
        final ArrayList<String> strings = new ArrayList<>();
        for (NimUserInfo nimUserInfo : allUsersOfMyFriend) {
            strings.add(nimUserInfo.getAccount());
        }

        NimUIKit.startContactSelector(this,getActivity(), getContactSelectOption(strings), TeamRequestCode.REQUEST_TEAM_AUDIO);
    }

    /**
     * 发起多人视频
     */
    private void createVideo() {
        List<NimUserInfo> allUsersOfMyFriend = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
        final ArrayList<String> strings = new ArrayList<>();
        for (NimUserInfo nimUserInfo : allUsersOfMyFriend) {
            strings.add(nimUserInfo.getAccount());
        }

        NimUIKit.startContactSelector(this,getActivity(), getContactSelectOption(strings), TeamRequestCode.REQUEST_TEAM_VIDEO);
    }

    private ContactSelectActivity.Option getContactSelectOption(ArrayList<String> accounts) {

        ContactSelectActivity.Option option = new ContactSelectActivity.Option();
        option.title = NimUIKit.getContext().getString(R.string.invite_member);
        if (accounts != null) {
            ArrayList<String> disableAccounts = new ArrayList<>();
            disableAccounts.add(DemoCache.getAccount());
            option.itemDisableFilter = new ContactIdFilter(disableAccounts);
        }
        return option;
    }

}