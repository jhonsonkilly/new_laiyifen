package com.ody.p2p.okhttputils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.utils.GsonUtils;
import com.ody.p2p.utils.NetworkUtils;
import com.ody.p2p.utils.StringUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lxs on 2016/5/19.
 */
public class OkHttpManager {
    private static OkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    //public static final String UT = "3135e42ce3bc435692881006760c0cf3";
    public static final String provinceId = "10";
    public static final String companyId = "1";
    // public static final String sessionId = "860735030066732";
    // public static final String platfromId = "0";

    private static final String TAG = "OkHttpManager";

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
        if (!StringUtils.isEmpty(OdyApplication.USER_AGENT)) {
            mOkHttpClient.networkInterceptors().add(new UserAgentInterceptor(OdyApplication.USER_AGENT));
        }
//        if (BuildConfig.DEBUG) {
            mOkHttpClient.networkInterceptors().add(new StethoInterceptor());
//        }
        mGson = GsonUtils.buildGson();
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException {
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback) {
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .build();
        Log.v("DataOpt:URL", url);
        deliveryResult(callback, request);
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback, String tagClass) {
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagClass)
                .build();
        Log.v("DataOpt:URL", url);
        deliveryResult(callback, request);
    }


    private void _getAsyn(String url, Map<String, String> params, ResultCallback callback) {
        if (!url.contains("?")) {
            url += "?";
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += "&" + entry.getKey() + "=" + entry.getValue();
        }
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .build();
        Log.v("DataOpt:URL", url);
        deliveryResult(callback, request);
    }

    private void _getAsyn(String url, Map<String, String> params, ResultCallback callback, String tagClass) {
        if (!url.contains("?")) {
            url += "?";
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += "&" + entry.getKey() + "=" + entry.getValue();
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagClass)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("deviceId", OdyApplication.getGUID())
                .build();
        Log.v("DataOpt:URL", url);
        deliveryResult(callback, request);
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     * @param tagClass
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params, String tagClass) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr, tagClass);
        deliveryResult(callback, request);
    }

    /**
     * 针对充值创建支付，sessionId要穿ut，单独处理
     *
     * @param url
     * @param callback
     * @param params
     */
    public static void postPayAsyn(String url, ResultCallback callback, Map<String, String> params) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("platformId", "0");
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
//        params.put("provinceId", provinceId);
        getInstance()._postAsyn(url, callback, params);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final DownLoadCallback callback) {
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        final Request request = new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    long current = 0;
                    sendStartCallback(total, callback);
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        sendProgressCallback(total, current, callback);
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }

    /**
     * 取消请求
     *
     * @return
     * @String url
     */
    private void _cancelRequest(Object obj) {
        mOkHttpClient.cancel(obj);
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }


    private void setErrorResId(final ImageView view, final int errorResId) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }


    //*************对外公布的方法************


    public static Response getAsyn(String url) throws IOException {
        return getInstance()._getAsyn(url);
    }


    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("platformId", "0");
        getInstance()._getAsyn(url, params, callback);
    }

    public static void getAsyn(String url, String tagClass, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("platformId", "0");
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
        params.put("sessionId", OdySysEnv.getSessionId());
        getInstance()._getAsyn(url, params, callback, tagClass);
    }

    public static void getAsyn(String url, Map<String, String> params, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
        params.put("sessionId", OdySysEnv.getSessionId());
        params.put("platformId", 0 + "");
        params.put("platform", 3 + "");
        getInstance()._getAsyn(url, params, callback);
    }

    public static void getAsynTask(String url, Map<String, String> params, String tagClass, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
        getInstance()._getAsyn(url, params, callback, tagClass);
    }

    public static void getAsyn(String url, Map<String, String> params, String tagClass, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
        params.put("platformId", 0 + "");
        params.put("sessionId", OdySysEnv.getSessionId());
//        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
        getInstance()._getAsyn(url, params, callback, tagClass);
    }

    public static void getAsyn(String url, Map<String, String> params, Context context, ResultCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("platformId", 0 + "");
        params.put("sessionId", OdySysEnv.getSessionId());
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
        getInstance()._getAsyn(url, params, callback, context.getClass().getSimpleName());
    }

    public static Response post(String url, Param... params) throws IOException {
        return getInstance()._post(url, params);
    }

    public static String postAsString(String url, Param... params) throws IOException {
        return getInstance()._postAsString(url, params);
    }

    public static void postAsyn(String url, final ResultCallback callback, Param... params) {
        getInstance()._postAsyn(url, callback, params);
    }


    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("platformId", "0");
        params.put("sessionId", OdySysEnv.getSessionId());
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
//        params.put("provinceId", provinceId);
        getInstance()._postAsyn(url, callback, params);
    }

    /**
     * U点卡充值 createPay
     *
     * @param url
     * @param callback
     * @param params
     */
    public static void _noSessionId(String url, final ResultCallback callback, Map<String, String> params) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("platformId", "0");
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
//        params.put("provinceId", provinceId);
        getInstance()._postAsyn(url, callback, params);
    }

    public static void noSessionIdArea(String url, final ResultCallback callback, Map<String, String> params) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);

//        params.put("provinceId", provinceId);
        getInstance()._postAsyn(url, callback, params);
    }


    public static void postAsyn(String url, String tagClass, final ResultCallback callback, Map<String, String> params) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        if (null == params) {
            params = new HashMap<>();
        }
//        params.put("companyId", OdyApplication.COMPANYID);
        params.put("platformId", "0");
        params.put("sessionId", OdySysEnv.getSessionId());
        params.put("areaCode", OdyApplication.getString(Constants.AREA_CODE, ""));
//        params.put("provinceId", provinceId);
        getInstance()._postAsyn(url, callback, params, tagClass);
    }


    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    public static void downloadAsyn(String url, String destDir, DownLoadCallback callback) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            callback.onNetError();
            callback.onFinish();
            return;
        }
        getInstance()._downloadAsyn(url, destDir, callback);
    }

    public static void cancelRequest(Object obj) {
        getInstance()._cancelRequest(obj);
    }

    //****************************


    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        return new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";
    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, final Request request) {
        callback.onStart();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    String string = response.body().string();
                    Log.i(TAG, string);
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, string, callback);
                    } else {
                        JSONObject jsonObject = new JSONObject(string);
                        String code = jsonObject.getString("code");
                        //手机号已经注册了的code为-1,然后才能往下走获取验证码
                        //注册界面2完成注册成功时code为20...
                        if (code != null && (code.equals("0") || code.equals("-1") || "20".equals(code) || "10200002".equals(code) || "10200006".equals(code) || "1".equals(code))) {
                            Object o = mGson.fromJson(string, callback.mType);
                            sendSuccessResultCallback(o, string, callback);
                        } else if (code != null && code.equals("99")) {
                            OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, "");
                            OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
                            OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, "");
                            EventMessage message = new EventMessage();
                            message.flag = EventMessage.JUMP_TO_LOGIN;
                            EventBus.getDefault().post(message);
                            JPushInterface.setAlias(OdyApplication.gainContext(), OdyApplication.getGUID(), new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });
                            Object o = mGson.fromJson(string, callback.mType);
                            sendSuccessResultCallback(o, string, callback);
                        } else {
                            String msg = jsonObject.getString("message");
                            sendFailedStringCallback(code, msg, string, callback);
                        }
                    }
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e) {//Json解析的错误
                    sendFailedStringCallback(response.request(), e, callback);
                    Log.e("tag", e.toString());
                } catch (Exception e) {
                    sendFailedStringCallback(response.request(), e, callback);
                    Log.e("tag", e.toString());
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final DownLoadCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                    callback.onFinish();
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null && null != request) {
                    callback.onError(request, e);
                    callback.onFinish();
                }
            }
        });
    }

    private void sendFailedStringCallback(final String code, final String msg, final String json, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailed(code, msg == null ? "" : msg);
                    callback.onFailed(code, json, msg == null ? "" : msg);
                    callback.onFinish();
                }
//                if (!StringUtils.isEmpty(msg)) {
//                    ToastUtils.showShort(msg);
//                }
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final String json, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    try {
                        callback.onResponse(json);
                        Log.d("DataOpt", json);
                        callback.onResponse(object);
                        callback.onFinish();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final DownLoadCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                    callback.onFinish();
                }
            }
        });
    }

    private void sendProgressCallback(final long total, final long current, final DownLoadCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onProgress(total, current);
                }
            }
        });
    }

    private void sendStartCallback(final long total, final DownLoadCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onStart(total);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        Log.i(TAG, "url : " + url);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            if (param.value != null) {
                builder.add(param.key, param.value);
                Log.i(TAG, param.key + " : " + param.value);
            }
        }
        RequestBody requestBody = builder.build();
        String tagUrl = "";
        if (url.contains("?")) {
            tagUrl = url.substring(0, url.indexOf("?"));
        } else {
            tagUrl = url;
        }
        return new Request.Builder()
                .url(url)
                .tag(tagUrl)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("deviceId", OdyApplication.getGUID())
                .post(requestBody)
                .build();
    }

    private Request buildPostRequest(String url, Param[] params, String tagClass) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            if (param.value != null) {
                builder.add(param.key, param.value);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .tag(tagClass)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("deviceId", OdyApplication.getGUID())
                .post(requestBody)
                .build();
    }

    public static void postAsyn(String initData, Context context, ResultCallback test, Map<String, String> params) {

    }


    public static abstract class DownLoadCallback<T> {
        Type mType;

        public DownLoadCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);

        public abstract void onProgress(long total, long current);

        public abstract void onStart(long total);

        public void onNetError() {
        }

        public void onFinish() {
        }


    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public void onFailed(String code, String msg) {

        }

        public void onFailed(String code, String json, String msg) {

        }

        public abstract void onResponse(T response);

        public void onResponse(String json) {
        }

        public void onNetError() {
        }


        public void onFinish() {
        }

        public void onStart() {
        }

        ;

    }


    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }
}
