package com.ody.p2p.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.ody.p2p.base.OdyApplication;

/**
 * Created by Sun on 2016/10/13.
 */

public class RUtils {
    private static String LOG_TAG = RUtils.class.getName();

    public static final String SYS_THEME_COLOR = "theme_color";//主题色

    public static final String BTN_TEXT_COLOR = "white";//按钮文字颜色
    public static final String BTN_BACKGROUND_COLOR = "theme_btn_background";//按钮背景颜色

    public static final String THEME_COLOR = "theme_color";//主题色
    public static final String COUPON = "coupon";// 优惠券
    public static final String MY_COUPON = "my_coupon";//我的优惠券
    public static final String NO_COUPON = "no_coupon";//暂无可用优惠券
    public static final String USE_COUPON = "use_coupon";//使用优惠券
    public static final String INPUT_COUPON_NUMBER = "input_coupon_number";//输入优惠券号
    public static final String WX_APP_ID = "wx_app_id"; //微信appid
    public static final String ADD_COUPON="add_coupon";//添加优惠券
    public static final String COUPON_DECORATION = "coupon_decoration";
    public static final String LYF_SURE_COLOR = "sure_dialog_color";//蓝色


    public static final String THEME_BUTTON="shape_login_normal";
    public static final String THEME_BTN_TEXTCOLOR="btn_textcolor";

    protected static int getResId(Context context, String resType, String resName) {
        int resId = 0;
        resName = resName.toLowerCase();
        if (context != null && !TextUtils.isEmpty(resType) && !TextUtils.isEmpty(resName)) {
            String pck1 = context.getPackageName();
            if (TextUtils.isEmpty(pck1)) {
                return resId;
            }
            resId = context.getResources().getIdentifier(OdyApplication.SCHEME + "_" + resName, resType, pck1);
            if (resId <= 0) {
                resId = context.getResources().getIdentifier(resName, resType, pck1);
            }

            if (resId <= 0) {
                Log.e(LOG_TAG, "failed to parse " + resType + " resource \"" + resName + "\"");
            }
            return resId;
        } else {
            return resId;
        }
    }

    public static int getDrawableRes(Context context, String resName) {
        return getResId(context, "drawable", resName);
    }

    public static int getStringRes(Context context, String resName) {
        return getResId(context, "string", resName);
    }

    public static int getStringArrayRes(Context context, String resName) {
        return getResId(context, "array", resName);
    }

    public static int getLayoutRes(Context context, String resName) {
        return getResId(context, "layout", resName);
    }

    public static int getStyleRes(Context context, String resName) {
        return getResId(context, "style", resName);
    }

    public static int getIdRes(Context context, String resName) {
        return getResId(context, "id", resName);
    }

    public static int getColorRes(Context context, String resName) {
        return getResId(context, "color", resName);
    }

    public static int getColorById(Context context, String resName) {
        return ContextCompat.getColor(context, getResId(context, "color", resName));
    }

    public static int getRawRes(Context context, String resName) {
        return getResId(context, "raw", resName);
    }

    public static int getPluralsRes(Context context, String resName) {
        return getResId(context, "plurals", resName);
    }

    public static int getAnimRes(Context context, String resName) {
        return getResId(context, "anim", resName);
    }
}
