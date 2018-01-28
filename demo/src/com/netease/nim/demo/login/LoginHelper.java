package com.netease.nim.demo.login;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.contact.ContactHttpClient;
import com.netease.nim.demo.contact.constant.UserConstant;
import com.netease.nim.demo.contact.helper.UserUpdateHelper;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasmin on 2018/1/4.
 */

public class LoginHelper {

    public static void register(final Context context, final String accountId,final String mobile
            , final String nickName,final String token,final String sex,final String imgUrl) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.customDialog);

//        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, null));
//        mDialog = builder.create();
//        mDialog.show();

        if (!NetworkUtil.isNetAvailable(context)) {
            Toast.makeText(context, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }

        ContactHttpClient.getInstance().register(accountId, nickName, accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                login(context,accountId,nickName,token,sex,mobile,imgUrl);
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                if (code == 414) {
                    ContactHttpClient.getInstance().updateAccounyt(accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            login(context,accountId,nickName,token,sex,mobile,imgUrl);
                        }

                        @Override
                        public void onFailed(int code, String errorMsg) {
                            Toast.makeText(context,"IM登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static void login(final Context context, final String accountId,final String nickName
            ,final String token,final String sex,final String mobile,final String imgUrl) {
        // 登录
        AbortableFuture<LoginInfo> loginRequest = NimUIKit.login(new LoginInfo(accountId, accountId), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
//                LogUtil.i(TAG, "login success");
//                onLoginDone();

                DemoCache.setAccount(accountId);
                saveLoginInfo(accountId, token);

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
                UserUpdateHelper.update(fields.get(8), imgUrl, callback);


                // 加载主界面
//                addUser();

//                initData(isReload);
            }

            @Override
            public void onFailed(int code) {
//                onLoginDone();
                if (code == 302 || code == 404) {
                    ContactHttpClient.getInstance().updateAccounyt(accountId, new ContactHttpClient.ContactHttpCallback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            login(context,accountId,nickName,token,sex,mobile,imgUrl);
                        }

                        @Override
                        public void onFailed(int code, String errorMsg) {
//                            Toast.makeText(getActivity(),"登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                    register(context,accountId,mobile,nickName,token,sex,imgUrl);
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(context, R.string.login_exception, Toast.LENGTH_LONG).show();
//                onLoginDone();
            }
        });
    }

    //保存登录信息
    private static void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(account);
        Preferences.saveUserMainToken(token);
    }

    private static void initNotificationConfig() {
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
}
