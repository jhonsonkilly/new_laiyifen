package com.ody.p2p.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.dao.DaoMaster;
import com.ody.p2p.dao.DaoSession;
import com.ody.p2p.utils.NetworkUtils;
import com.ody.p2p.utils.StringUtils;
import com.tencent.smtt.sdk.QbSdk;

import org.apache.commons.codec.binary.Base64;
import org.greenrobot.greendao.database.Database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;


/**
 * 整个应用程序Applicaiton
 *
 * @author lxs
 * @version 1.0
 */
public class OdyApplication extends MultiDexApplication   {


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    private static SharedPreferences mPreferences;

    /**
     * 对外提供整个应用生命周期的Context
     **/
    private static Context instance;
    /**
     * 整个应用全局可访问数据集合
     **/
    private static Map<String, Object> gloableData = new HashMap<String, Object>();
    /***
     * 寄存整个应用Activity
     **/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    public static String BASE_URL = "";
    public static String COMPANYID = "";
    public static String SCHEME = "";
    public static String H5URL = "";
    public static String VERSION_NAME = "";
    public static String USER_AGENT = "";
    public static int OVERSEA = 0;
    public static int resPlaceHolder = R.drawable.icon_stub;
    public static boolean IS_OPEN_PUSH = true;
    public static DaoSession daoSession;
    private static String ROOT_PATH;
    public static String APP_KEY = "";

    /**
     * 对外提供Application Context
     *
     * @return
     */
    public static Context gainContext() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();


        try {
            instance = this;
            mPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
            USER_AGENT = getUserAgent2();
            NetworkUtils.getInstance().init(this);
            JPushInterface.setDebugMode(false);
            //数据库初始化
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "des-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
            ROOT_PATH = getDir("lyf", MODE_PRIVATE).getAbsolutePath();
            if (!ROOT_PATH.endsWith("/")) {
                ROOT_PATH += "/";
            }

            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is " + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };

            //初始化X5浏览器内核
            QbSdk.initX5Environment(getApplicationContext(), cb);

        } catch (Exception e) {

        }




    }
    private static boolean isAppInBackground = false;

    private static Object mLifecycleCallbacks;



    public static String getRootPath() {
        return ROOT_PATH;
    }

    private String getUserAgent() {

        String userAgentString = "";
        WebView webView = new WebView(this);
        userAgentString = webView.getSettings().getUserAgentString();
        webView.destroy();
        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "ds");
        ua.put("ut", getValueByKey(Constants.USER_LOGIN_UT, ""));
        ua.put("sessionId", getSessionId());
        Gson gson = new Gson();
        //对agent信息包含中文转utf-8的处理
        return getValueEncoded(userAgentString) + "--[" + gson.toJson(ua) + "]--";
    }

    //由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符
    private static String getValueEncoded(String value) {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }

    private static String getUserAgent2() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(instance);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "ds");
        ua.put("ut", getValueByKey(Constants.USER_LOGIN_UT, ""));
        ua.put("sessionId", getSessionId());
        Gson gson = new Gson();
        sb.append("--[" + gson.toJson(ua) + "]--");
        return sb.toString();
    }
    /*******************************************************Application数据操作API（开始）********************************************************/

    /**
     * 往Application放置数据（最大不允许超过5个）
     *
     * @param strKey   存放属性Key
     * @param strValue 数据对象
     */
    public static void assignData(String strKey, Object strValue) {
        if (gloableData.size() > 5) {
            throw new RuntimeException(instance.getString(R.string.exceed_max_number));
        }
        gloableData.put(strKey, strValue);
    }

    /**
     * 从Applcaiton中取数据
     *
     * @param strKey 存放数据Key
     * @return 对应Key的数据对象
     */
    public static Object gainData(String strKey) {
        return gloableData.get(strKey);
    }

    /*
     * 从Application中移除数据
     */
    public static void removeData(String key) {
        if (gloableData.containsKey(key)) gloableData.remove(key);
    }

    /*******************************************************Application数据操作API（结束）********************************************************/


    /*******************************************Application中存放的Activity操作（压栈/出栈）API（开始）*****************************************/

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public void removeAll() {
        //finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
    }

    /*******************************************Application中存放的Activity操作（压栈/出栈）API（结束）*****************************************/

    /**
     * SharedPreferences
     */
    public static void putValueByKey(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    /**
     * SharedPreferences
     */
    public static void putString(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    public static void putValueByKey(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putValueByKey(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    public static void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    public static String getValueByKey(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public static boolean getValueByKey(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public static int getValueByKey(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public static long getValueByKey(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    /**
     * Object Set
     *
     * @param name
     * @param obj
     */
    public static void putValueByKey(String name, Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            String obj_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            mPreferences.edit().putString(name, obj_Base64).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Object Set
     *
     * @param name
     * @param obj
     */
    public static void putObject(String name, Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            String obj_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            mPreferences.edit().putString(name, obj_Base64).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Object Get
     *
     * @param name
     * @return
     */
    public static Object getValueByKey(String name) {
        Object obj = null;
        try {
            String data = mPreferences.getString(name, " ");
            if ((null == data || " ".equals(data))) {
                return null;
            }
            byte[] base64 = Base64.decodeBase64(data.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            ObjectInputStream bis = new ObjectInputStream(bais);
            obj = bis.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Object Get
     *
     * @param name
     * @return
     */
    public static Object getObject(String name) {

        Object obj = null;
        try {
            String data = mPreferences.getString(name, " ");
            if ((null == data || " ".equals(data))) {
                return null;
            }
            byte[] base64 = Base64.decodeBase64(data.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            ObjectInputStream bis = new ObjectInputStream(bais);
            obj = bis.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String getSessionId() {
        String sessionId;
        sessionId = getValueByKey("sessionId", "");
        if (ContextCompat.checkSelfPermission(OdyApplication.gainContext(),
                Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            if (StringUtils.isEmpty(sessionId)) {
//            TelephonyManager telephonyManager = (TelephonyManager) instance.getSystemService(Context.TELEPHONY_SERVICE);
//            sessionId = telephonyManager.getDeviceId();
                if (StringUtils.isEmpty(sessionId)) {
                    sessionId = UUID.randomUUID().toString();
                }
                putValueByKey("sessionId", sessionId);
            }
        }
        return sessionId;
    }

    public static String getGUID() {
        String guid;
        guid = getString("guid", "");
        if (ContextCompat.checkSelfPermission(OdyApplication.gainContext(),
                Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            if (StringUtils.isEmpty(guid)) {
//            TelephonyManager telephonyManager = (TelephonyManager) instance.getSystemService(Context.TELEPHONY_SERVICE);
//            guid = telephonyManager.getDeviceId();
                if (StringUtils.isEmpty(guid)) {
                    guid = UUID.randomUUID().toString();
                }
                putString("guid", guid);
            }
        }
        return guid;
    }

    public static String getDeviceId() {
        if (ContextCompat.checkSelfPermission(OdyApplication.gainContext(),
                Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) instance.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = telephonyManager.getDeviceId();
            if (!StringUtils.isEmpty(deviceId)) {
                return deviceId;
            }
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    public static boolean getIsLogin() {
        if (StringUtils.isEmpty(getLoginUt())) {
            return false;
        }
        return getValueByKey(Constants.LOGIN_STATE, false);
    }

    /**
     * @return 用户是否是分销商
     */
    public static boolean isDistributor() {
        return getValueByKey(Constants.USER_IS_DISTRIBUTOR, false);
    }

    /**
     * 获取登录的ut
     *
     * @return
     */
    public static String getLoginUt() {
        return getValueByKey(Constants.USER_LOGIN_UT, "");
    }

    public static String getRouterUrl(String tag) {
        return SCHEME + "://" + tag;
    }
}
