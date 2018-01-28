package com.netease.nim.demo.contact;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.sync.CheckSumBuilder;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.uikit.common.http.NimHttpClient;
import com.netease.nim.demo.config.DemoServers;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nimlib.sdk.SDKOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.Checksum;

/**
 * 通讯录数据获取协议的实现
 * <p/>
 * Created by huangjun on 2015/3/6.
 */
public class ContactHttpClient {
    private static final String TAG = "ContactHttpClient";

    // code
    private static final int RESULT_CODE_SUCCESS = 200;

    // api
    private static final String API_NAME_REGISTER = "createDemoUser";

    // header
    private static final String HEADER_KEY_APP_KEY  = "appkey";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_USER_AGENT   = "User-Agent";
    private static final String HEADER_NONCE   = "Nonce";

    // request
    private static final String REQUEST_USER_NAME = "accid";
    private static final String REQUEST_NICK_NAME = "name";
    private static final String REQUEST_PASSWORD  = "token";

    // result
    private static final String RESULT_KEY_RES       = "res";
    private static final String RESULT_KEY_ERROR_MSG = "errmsg";


    public interface ContactHttpCallback<T> {
        void onSuccess(T t);

        void onFailed(int code, String errorMsg);
    }

    private static ContactHttpClient instance;

    public static synchronized ContactHttpClient getInstance() {
        if (instance == null) {
            instance = new ContactHttpClient();
        }

        return instance;
    }

    private ContactHttpClient() {
        NimHttpClient.getInstance().init(DemoCache.getContext());
    }


    /**
     * 向应用服务器创建账号（注册账号）
     * 由应用服务器调用WEB SDK接口将新注册的用户数据同步到云信服务器
     */
    public void register(String account, String nickName, String password, final ContactHttpCallback<Void> callback) {
        String url = "https://api.netease.im/nimserver/user/create.action";
//        password = MD5.getStringMD5(password);
        password = account;
        try {
            nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Random rand = new Random();
        String nonce = rand.nextInt(99999)+"";
        String appSecret = Common.appSecret;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
//        headers.put(HEADER_USER_AGENT, "nim_demo_android");
        headers.put(HEADER_KEY_APP_KEY, appKey);
        headers.put("Nonce",nonce);
        headers.put("CurTime", curTime);
        headers.put("CheckSum", checkSum);


        StringBuilder body = new StringBuilder();
        body.append(REQUEST_USER_NAME).append("=").append(account.toLowerCase()).append("&")
                .append(REQUEST_NICK_NAME).append("=").append(nickName).append("&")
                .append(REQUEST_PASSWORD).append("=").append(password);
        String bodyString = body.toString();

        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    if (code==200){
                        int requestCode = resObj.getInteger("code");
                        if (requestCode == 414 )
                        callback.onFailed(requestCode,"已注册");
                        else {
                            callback.onSuccess(null);
                        }
                    }
                    else
                    {
                        int resCode = resObj.getIntValue(RESULT_KEY_RES);
                        if (resCode == RESULT_CODE_SUCCESS) {
                            callback.onSuccess(null);
                        } else {
                            String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                            callback.onFailed(resCode, error);
                        }
                    }

                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }

    public void updateAccounyt(String account, final ContactHttpCallback<Void> callback) {
        String url = "https://api.netease.im/nimserver/user/update.action";
//

        Random rand = new Random();
        String nonce = rand.nextInt(99999)+"";
        String appSecret = Common.appSecret;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
//        headers.put(HEADER_USER_AGENT, "nim_demo_android");
        headers.put(HEADER_KEY_APP_KEY, appKey);
        headers.put("Nonce",nonce);
        headers.put("CurTime", curTime);
        headers.put("CheckSum", checkSum);


        StringBuilder body = new StringBuilder();
        body.append(REQUEST_USER_NAME).append("=").append(account).append("&")
                .append(REQUEST_PASSWORD).append("=").append(account);
        String bodyString = body.toString();

        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {
                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                            + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    if (code==200)
                        callback.onSuccess(null);
                    else
                    {
                        int resCode = resObj.getIntValue(RESULT_KEY_RES);
                        if (resCode == RESULT_CODE_SUCCESS) {
                            callback.onSuccess(null);
                        } else {
                            String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                            callback.onFailed(resCode, error);
                        }
                    }

                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }

    private String readAppKey() {
        try {
            ApplicationInfo appInfo = DemoCache.getContext().getPackageManager()
                    .getApplicationInfo(DemoCache.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
