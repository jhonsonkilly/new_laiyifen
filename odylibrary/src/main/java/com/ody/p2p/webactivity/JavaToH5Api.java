package com.ody.p2p.webactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/12/5.
 */

public class JavaToH5Api {
    private static final int PICK_PHOTO = 100;//相片code
    private String uploadCallback;
    private int mTag;
    private static long sCurrentTimeMillis;

    public JavaToH5Api(int tag) {
        mTag = tag;
    }

    /**
     * 原声方法注入， 供js调用
     *
     * @param params
     */
    @android.webkit.JavascriptInterface
    public void postMessage(String params) {
        if (!StringUtils.isEmpty(params)) {
            try {
                JSONObject json = new JSONObject(params);
                String function = json.optString("function");
                String callback = json.optString("callback");
                String param = json.optString("param");
                JsEventMessage msg = new JsEventMessage();
                msg.function = function;
                msg.params = param;
                msg.callback = callback;
                msg.msgTag = mTag;    //增加hashCode作为发起消息的标识符 在处理一些特定事件的时候用作过滤器
                EventBus.getDefault().post(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 打开分享界面
     */
    public static void share(Context context) {
        try {
            Class c = Class.forName("dsshare.SharePopupWindow");
            Constructor<?> con = c.getDeclaredConstructor(new Class[]{Context.class, int.class, String.class}); //调用了Employee的无参数构造方法.
            Object o = con.newInstance(context, 6, "");
            Method method = c.getMethod("showAtLocation", new Class[]{View.class, int.class, int.class, int.class});
            method.invoke(o, ((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开第三方导航
     */
    public static void navigate(Context context, String params) {
        String lat, lon, loc;
        try {
            JSONObject jsonObject = new JSONObject(params);
            lat = jsonObject.optString("latitude");
            lon = jsonObject.optString("longitude");
            loc = jsonObject.optString("location");
            Uri mUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + loc);
            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
            context.startActivity(mIntent);
        } catch (Exception e) {
            ToastUtils.showShort("您还未安装导航!");
        }
    }

    /**
     * 打开分享界面
     */
    public static void share(Context context, String params) {
        if (System.currentTimeMillis() - sCurrentTimeMillis < 2000) {
            return;
        }
        sCurrentTimeMillis = System.currentTimeMillis();
        try {
            Class c = Class.forName("dsshare.SharePopupWindow");
            Constructor<?> con = c.getDeclaredConstructor(new Class[]{Context.class, int.class, String.class}); //调用了Employee的无参数构造方法.
            Object o = con.newInstance(context, 6, params);
            Method method = c.getMethod("showAtLocation", new Class[]{View.class, int.class, int.class, int.class});
            method.invoke(o, ((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三方绑定
     */
    public static void bindThird(Context context, String type) {
        try {
            Class c = Class.forName("ThirdAuthorization");
            Method method = c.getMethod("getAuthorization", new Class[]{Context.class, String.class});
            method.invoke(null, context, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享
     */
    public static void sharePlatform(Context context, String params) {
        try {
            JSONObject p = new JSONObject(params);
            if (p.has("param")) {
                p = p.optJSONObject("param");
            }
            Class c = Class.forName("dsshare.ShareFactory");
            Method method = c.getMethod("create", Context.class);
            Object shareObj = method.invoke(null, context);
            Method share = c.getDeclaredMethod("share", String.class);
            share.invoke(shareObj, p.toString());
        } catch (Exception ex) {

        }
    }

}
