package com.netease.nim.demo.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.avchat.AVChatProfile;
import com.netease.nim.demo.avchat.activity.AVChatActivity;
import com.netease.nim.demo.chatroom.helper.ChatRoomHelper;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHttpClient;
import com.netease.nim.demo.contact.activity.AddFriendActivity;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.contact.constant.UserConstant;
import com.netease.nim.demo.contact.helper.UserUpdateHelper;
import com.netease.nim.demo.login.LoginActivity;
import com.netease.nim.demo.login.LogoutHelper;
import com.netease.nim.demo.main.fragment.YunXinFragment;
import com.netease.nim.demo.main.widget.MyPopWindow;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.demo.team.TeamCreateHelper;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.plugin.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * 主界面
 * <p/>
 * Created by huangjun on 2015/3/25.
 */
public class MainActivity extends UI {

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
    private static final int REQUEST_SCAN = 11;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;

    //private static final int REQUEST_SCAN = 11;//扫描

    private YunXinFragment mainFragment;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void start(Context context, String accountId, String mobile, String imageUrl, String nickName,
                             String tokenStr, String sex) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("accountId", accountId);
        intent.putExtra("mobile", mobile);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("nickName", nickName);
        intent.putExtra("token", tokenStr);
        intent.putExtra("sex", sex);
        context.startActivity(intent);
    }

    // 注销
    public static void logout(Context context, boolean quit) {
        Intent extra = new Intent();
        extra.putExtra(EXTRA_APP_QUIT, quit);
        start(context, extra);
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);


        accountId = getIntent().getStringExtra("accountId");
        mobile = getIntent().getStringExtra("mobile");
        imageUrl = getIntent().getStringExtra("imageUrl");
        nickName = getIntent().getStringExtra("nickName");
        token = getIntent().getStringExtra("token");
        sex = getIntent().getStringExtra("sex");

        register();
    }

    private void initData() {
        requestBasicPermission();

        onParseIntent();

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
            DialogMaker.showProgressDialog(MainActivity.this, getString(R.string.prepare_data)).setCanceledOnTouchOutside(false);
        } else {
            syncPushNoDisturb(UserPreferences.getStatusConfig());
        }

        onInit();
    }

    /**
     * 若增加第三方推送免打扰（V3.2.0新增功能），则：
     * 1.添加下面逻辑使得 push 免打扰与先前的设置同步。
     * 2.设置界面{@link com.netease.nim.demo.main.activity.SettingsActivity} 以及
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
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(MainActivity.this)
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
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "未全部授权，部分功能可能无法正常运行！", Toast.LENGTH_SHORT).show();
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    private void onInit() {
        // 加载主页面
        showMainFragment();

        // 聊天室初始化
        ChatRoomHelper.init();

        LogUtil.ui("NIM SDK cache path=" + NIMClient.getSdkStorageDirPath());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        onParseIntent();
    }

    @Override
    public void onBackPressed() {
        if (mainFragment != null) {
            if (mainFragment.onBackPressed()) {
                return;
            } else {
                moveTaskToBack(true);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * 通过反射，设置menu显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int itemId = item.getItemId();
        /*if(itemId == R.id.about){
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }else*/
        if (itemId == R.id.create_regular_team) {
            ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
            advancedOption.multi = false;
            NimUIKit.startContactSelector(MainActivity.this, advancedOption, REQUEST_CODE_ADVANCED);

        } else if (itemId == R.id.add_btn) {
           /* MyPopWindow popWindow = new MyPopWindow(MainActivity.this);
            popWindow.showAtDropDownLeft(findViewById(R.id.add_btn));*/
        } else if (itemId == R.id.add_buddy) {
            AddFriendActivity.start(MainActivity.this);
        } else if (itemId == R.id.search_btn) {
            GlobalSearchActivity.start(MainActivity.this);
        } else if (itemId == R.id.sao) {

            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(
                    //mTODO:meiyizhi 定位需要的权限
                    android.Manifest.permission.CAMERA)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                startActivityForResult(new Intent(MainActivity.this, SweepActivity.class), REQUEST_SCAN);
                            } else {

                                Toast.makeText(MainActivity.this, "为了更好的使用体验，请开启相机使用权限!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
           /* new IntentIntegrator(this)
                    .setOrientationLocked(false)
                    .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                    .initiateScan();*/
        }

        /*switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
//            case R.id.create_normal_team:
//                ContactSelectActivity.Option option = TeamHelper.getCreateContactSelectOption(null, 50);
//                NimUIKit.startContactSelector(MainActivity.this, option, REQUEST_CODE_NORMAL);
//                break;
            case R.id.create_regular_team:
                ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
                advancedOption.multi = false;
                NimUIKit.startContactSelector(MainActivity.this, advancedOption, REQUEST_CODE_ADVANCED);
                break;
//            case R.id.search_advanced_team:
//                AdvancedTeamSearchActivity.start(MainActivity.this);
//                break;
            case R.id.add_btn:
                MyPopWindow popWindow = new MyPopWindow(MainActivity.this);
                popWindow.showAtDropDownLeft(findViewById(R.id.add_btn));

                break;
            case R.id.add_buddy:
                AddFriendActivity.start(MainActivity.this);
                break;
            case R.id.search_btn:
                GlobalSearchActivity.start(MainActivity.this);
                break;
            case R.id.sao:
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan();
                break;

            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }


    private void onParseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) getIntent().getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    SessionHelper.startP2PSession(this, message.getSessionId(), false);
                    break;
                case Team:
                    SessionHelper.startTeamSession(this, message.getSessionId());
                    break;
                default:
                    break;
            }
        } else if (intent.hasExtra(EXTRA_APP_QUIT)) {
//            onLogout();
            return;
        } else if (intent.hasExtra(AVChatActivity.INTENT_ACTION_AVCHAT)) {
            if (AVChatProfile.getInstance().isAVChatting()) {
                Intent localIntent = new Intent();
                localIntent.setClass(this, AVChatActivity.class);
                startActivity(localIntent);
            }
        } else if (intent.hasExtra(com.netease.nim.demo.main.model.Extras.EXTRA_JUMP_P2P)) {
            Intent data = intent.getParcelableExtra(com.netease.nim.demo.main.model.Extras.EXTRA_DATA);
            String account = data.getStringExtra(com.netease.nim.demo.main.model.Extras.EXTRA_ACCOUNT);
            if (!TextUtils.isEmpty(account)) {
                SessionHelper.startP2PSession(this, account, false);
            }
        }
    }

    private void showMainFragment() {
        if (mainFragment == null && !isDestroyedCompatible()) {
            mainFragment = new YunXinFragment();
            switchFragmentContent(mainFragment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_NORMAL) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    TeamCreateHelper.createNormalTeam(MainActivity.this, selected, false, null);
                } else {
                    Toast.makeText(MainActivity.this, "请选择至少一个联系人！", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_CODE_ADVANCED) {
                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                TeamCreateHelper.createAdvancedTeam(MainActivity.this, selected);


            } else {
                /*IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                if(intentResult != null) {
                    if(intentResult.getContents() == null) {
                        Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
                    } else {

                        // ScanResult 为 获取到的字符串
                        String ScanResult = intentResult.getContents();
//                        Toast.makeText(this,"扫描成功"+ScanResult,Toast.LENGTH_LONG).show();

                        query(ScanResult);
                    }
                } else {
                    super.onActivityResult(requestCode,resultCode,data);
                }*/

                String result = data.getStringExtra("scanResult");

                if (result == null) {
                    Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
                } else {

                    // ScanResult 为 获取到的字符串
                    Toast.makeText(this, "扫描成功" + result, Toast.LENGTH_LONG).show();

                    query(result);
                }
                //设置结果显示框的显示数值


            }
        }

    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听
        LogoutHelper.logout();

        // 启动登录
        LoginActivity.start(this);
        finish();
    }

    //查找用户
    private void query(String accountStr) {
        DialogMaker.showProgressDialog(this, null, false);
        final String account = accountStr;
        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo user) {
                DialogMaker.dismissProgressDialog();
                if (user == null) {
                    EasyAlertDialogHelper.showOneButtonDiolag(MainActivity.this, R.string.user_not_exsit,
                            R.string.user_tips, R.string.ok, false, null);
                } else {
                    UserProfileActivity.start(MainActivity.this, account,"");
                }
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                if (code == 408) {
                    Toast.makeText(MainActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(MainActivity.this, "on exception:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //注册登录
    private void register() {
        if (!NetworkUtil.isNetAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }

        DialogMaker.showProgressDialog(this, getString(R.string.loading), false);

        ContactHttpClient.getInstance().register(accountId, nickName, accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//                Toast.makeText(IMEnterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
                addUser();
                login();
            }

            @Override
            public void onFailed(int code, String errorMsg) {

//                DialogMaker.dismissProgressDialog();
                login();
            }
        });
    }

    private void login() {
//        DialogMaker.showProgressDialog(this, null, getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if (loginRequest != null) {
//                    loginRequest.abort();
//                    onLoginDone();
//                }
//            }
//        }).setCanceledOnTouchOutside(false);


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

//                fields.put(UserInfoFieldEnum.Name,nickName);
//                fields.put(UserInfoFieldEnum.AVATAR, imageUrl);
//                fields.put(UserInfoFieldEnum.GENDER, genderEnum);
//                fields.put(UserInfoFieldEnum.MOBILE, mobile);
//                NIMClient.getService(UserService.class).updateUserInfo(fields)
//                        .setCallback(new RequestCallbackWrapper<Void>() {
//                            @Override
//                            public void onResult(int i, Void aVoid, Throwable throwable) {
//
//                            }
//                        });
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
                initData();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(MainActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        loginRequest = null;
        DialogMaker.dismissProgressDialog();
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

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
        Preferences.saveUserMainToken(this.token);
    }

    /**
     * 修改群信息
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


        OKhttpHelper.send(this, body, Common.AdapterPath + "addUser", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }
}
