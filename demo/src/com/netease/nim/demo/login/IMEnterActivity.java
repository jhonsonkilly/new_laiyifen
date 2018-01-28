package com.netease.nim.demo.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHttpClient;
import com.netease.nim.demo.contact.activity.UserProfileEditItemActivity;
import com.netease.nim.demo.contact.activity.UserProfileSettingActivity;
import com.netease.nim.demo.contact.constant.UserConstant;
import com.netease.nim.demo.contact.helper.UserUpdateHelper;
import com.netease.nim.demo.main.activity.MainActivity;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.actions.PickImageAction;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.nos.NosService;
import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.GenderEnum;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasmin on 2017/11/20.
 */

public class IMEnterActivity extends UI{

    private String accountId;
    private String mobile;
    private String imageUrl;
    private String nickName;
    private String token;
    private String sex;
    private AbortableFuture<LoginInfo> loginRequest;
    AbortableFuture<String> uploadAvatarFuture;

    public static void start(Context context, String accountId,String mobile,String imageUrl,String nickName,
    String tokenStr,String sex) {
        Intent intent = new Intent();
        intent.setClass(context, IMEnterActivity.class);
        intent.putExtra("accountId",accountId);
        intent.putExtra("mobile",mobile);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("nickName",nickName);
        intent.putExtra("token",tokenStr);
        intent.putExtra("sex",sex);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        register();
    }

    private void register(){
        if (!NetworkUtil.isNetAvailable(IMEnterActivity.this)) {
            Toast.makeText(IMEnterActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }

        DialogMaker.showProgressDialog(this, getString(R.string.loading), false);

        ContactHttpClient.getInstance().register(accountId, nickName, accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//                Toast.makeText(IMEnterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
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
                if (sex.equals("1")){
                    gender = Integer.valueOf(1);
                }else
                    gender =  Integer.valueOf(2);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_NICKNAME), nickName, callback);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_GENDER),gender , callback);
                UserUpdateHelper.update(fields.get(UserConstant.KEY_PHONE), mobile, callback);
                UserUpdateHelper.update(fields.get(8), imageUrl, callback);

                // 进入主界面
                MainActivity.start(IMEnterActivity.this, null);
                finish();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(IMEnterActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(IMEnterActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(IMEnterActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
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


//    /**
//     * 更新头像
//     */
//    private void updateAvatar(final String path) {
//        if (TextUtils.isEmpty(path)) {
//            return;
//        }
//
//        File file = new File(path);
//        if (file == null) {
//            return;
//        }
//
//        uploadAvatarFuture = NIMClient.getService(NosService.class).download(path, NosThumbParam, PickImageAction.MIME_JPEG);
//        uploadAvatarFuture.setCallback(new RequestCallbackWrapper<String>() {
//            @Override
//            public void onResult(int code, String url, Throwable exception) {
//                if (code == ResponseCode.RES_SUCCESS && !TextUtils.isEmpty(url)) {
//
//                    UserUpdateHelper.update(UserInfoFieldEnum.AVATAR, url, new RequestCallbackWrapper<Void>() {
//                        @Override
//                        public void onResult(int code, Void result, Throwable exception) {
//
//                        }
//                    }); // 更新资料
//                } else {
//                    Toast.makeText(IMEnterActivity.this, R.string.user_info_update_failed, Toast
//                            .LENGTH_SHORT).show();
//
//                }
//            }
//        });
//    }
}
