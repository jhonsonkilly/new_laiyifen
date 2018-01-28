package com.lyfen.android.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.utils.GsonUtils;
import com.laiyifen.lyfframework.utils.PreferenceUtils;
import com.lyfen.android.app.OtherApi;
import com.lyfen.android.app.PrefrenceKey;
import com.lyfen.android.entity.network.AlibcEntity;
import com.lyfen.android.entity.network.login.UserInfoEntity;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.login.LyfLoginFragment;

import rx.Observer;

/**
 * Created by qiujie on 2017/6/30.
 */

public class LoginHelper {
    private static UserInfoEntity userInfoEntity;

    public static void init() {

        String string = PreferenceUtils.getString(PrefrenceKey.USER_INFO, "");
        if (!TextUtils.isEmpty(string)) {

            userInfoEntity = GsonUtils.fromJson(string, UserInfoEntity.class);
            setUserinfo(userInfoEntity);
        }
    }

    public static void setUserinfo(UserInfoEntity userInfoEntity) {
        try {
            userInfoEntity = userInfoEntity;
            if (null != userInfoEntity) {

                PreferenceUtils.edit().putString(PrefrenceKey.USER_INFO, com.laiyifen.lyfframework.utils.GsonUtils.toJson(userInfoEntity)).apply();
                PreferenceUtils.edit().putString(PrefrenceKey.USER_LOGIN_UT, userInfoEntity.ut).apply();
                PreferenceUtils.edit().putString(PrefrenceKey.LOGIN_MOBILE_PHONE, userInfoEntity.data.mobile).apply();
                PreferenceUtils.edit().putString(PrefrenceKey.HEAD_PIC_URL, userInfoEntity.data.headPicUrl).apply();
                PreferenceUtils.edit().putString(PrefrenceKey.NICK_NAME, userInfoEntity.data.nickname).apply();
                PreferenceUtils.edit().putBoolean(PrefrenceKey.LOGIN_STATE, true).apply();


            }
        } catch (Exception e) {

        }


    }

    public static void cleanInfo() {

        try {
            userInfoEntity = null;
            PreferenceUtils.edit().putString(PrefrenceKey.USER_INFO, "").apply();
            PreferenceUtils.edit().putString(PrefrenceKey.USER_LOGIN_UT, "").apply();
            PreferenceUtils.edit().putString(PrefrenceKey.LOGIN_MOBILE_PHONE, "").apply();
            PreferenceUtils.edit().putString(PrefrenceKey.HEAD_PIC_URL, "").apply();
            PreferenceUtils.edit().putString(PrefrenceKey.NICK_NAME, "").apply();
        } catch (Exception e) {

        }


    }


    public static String getUt() {
        try {


            if (null != userInfoEntity) {
                return userInfoEntity.ut;
            } else {
                return OdyApplication.getLoginUt();
            }
        } catch (Exception e) {
            return null;
        }


    }

    public static String getMobile() {
        try {

            if (null != userInfoEntity) {
                return userInfoEntity.data.mobile;
            } else {
                return PreferenceUtils.getString(PrefrenceKey.LOGIN_MOBILE_PHONE, "");
            }
        } catch (Exception e) {
            return null;

        }


    }

    public static UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public static void InitBaiChuan() {
        RestRetrofit.getBeanOfClass(OtherApi.class).getTaoBaoOpenIM(LoginHelper.getMobile(), "30").subscribe(new Observer<AlibcEntity>() {

            @Override
            public void onCompleted() {
                Log.i("aaa4", "ok");

            }

            @Override
            public void onError(Throwable e) {
                Log.i("aaa5", "nook");
            }

            @Override
            public void onNext(AlibcEntity carBrands) {
                Log.i("aaa2", carBrands.toString());


                RestRetrofit.getObjectCache().add("aaaa1", carBrands.toString());


                try {
                    Log.i("aaa3", RestRetrofit.getObjectCache().get("aaaa1", "kong"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static boolean isLogin() {

//        return PreferenceUtils.getBoolean (PrefrenceKey.LOGIN_STATE, false);
        return OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);
    }

    public static boolean needLogin(Context context) {
        if (LoginHelper.isLogin()) {
            return true;
        } else {
//            UIUtils.showToastSafe("未登录,请登录");
            try {
                Intent intent = new Intent(context, LyfLoginFragment.class);

                context.startActivity(intent);
                return false;
            } catch (Exception e) {
                Intent intent = new Intent(context, LyfLoginFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return false;
            }

        }
    }

}
