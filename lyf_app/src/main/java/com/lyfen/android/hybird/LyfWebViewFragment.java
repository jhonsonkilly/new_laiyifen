package com.lyfen.android.hybird;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.base.BaseFragment;
import com.laiyifen.lyfframework.utils.GsonUtils;
import com.laiyifen.lyfframework.utils.LogUtils;
import com.laiyifen.lyfframework.utils.SystemUtils;
import com.lyfen.android.utils.RouterHelper;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.BuildConfig;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 作者：qiujie on 16/5/24
 * 邮箱：qiujie@laiyifen.com
 */
public class LyfWebViewFragment extends BaseFragment {
    private WebView mWebView;
    private Context mContext;


    /**
     * File upload callback for platform versions prior to Android 5.0
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * File upload callback for Android 5.0+
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    HybridEntity mModel;
    private HybridEvent mHybridEvent;
    private int mtag;
    String originAgent;
    private com.tencent.smtt.sdk.WebSettings mWebSettings;

    public LyfWebViewFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mWebView = new WebView(getActivity());

        mWebView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mModel = getArguments().getParcelable("mode");


        mtag = getContext().hashCode();
        mHybridEvent = new HybridEvent(mtag);
        mHybridEvent.setEventSource(mWebView);


        mWebSettings = mWebView.getSettings();
//        if (BuildConfig.DEBUG) {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
//        }

        mWebSettings.setAllowFileAccess(false);
        if (Build.VERSION.SDK_INT >= 16) {
            mWebSettings.setAllowFileAccessFromFileURLs(true);
            mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setRenderPriority(com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        originAgent = mWebSettings.getUserAgentString();
        setAgent();
        mWebView.addJavascriptInterface(mHybridEvent, "mobileAPI");
        CookieSyncManager.createInstance(getContext());
        CookieManager.getInstance().setAcceptCookie(true);


        initWebClient();
        initWebChromeClient();


        mWebView.loadUrl(mModel.param.url);


        return mWebView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setAgent() {

        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "lyf");
        ua.put("ut", OdyApplication.getLoginUt());
        ua.put("sessionId", SystemUtils.getUUid());
        ua.put("version", BuildConfig.VERSION_NAME);
        ua.put("platform", "android");


        mWebSettings.setUserAgentString(originAgent + "--[" + GsonUtils.toJson(ua) + "]--");
    }


    private void initWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                ((ActionBarActivity) mContext).setTitleProgressSmooth(newProgress);
//                GrowingIO.trackWebView(mWebView, this);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                if (TextUtils.isEmpty (mTitle)) {

                ((ActionBarActivity) mContext).getActionTitleBar().setTitle(title);


//                }
            }

            // file upload callback (Android 5.0 (API level 21) -- current) (public method)
            @SuppressWarnings("all")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                openFileInput(null, filePathCallback);
                return true;
            }

            // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, null);
            }

            // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg, acceptType, null);
            }

            // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileInput(uploadMsg, null);
            }
        });


    }

    static boolean urlShouldBeHandledByWebView(@NonNull String url) {
        // file: Resolve requests to local files such as files from cache folder via WebView itself
        return url.startsWith("file:");
    }

    private void initWebClient() {
        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                LogUtils.i(LogUtils.WEB_TAG, "onPageStarted");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.i(LogUtils.WEB_TAG, "onPageFinished");

//                UserInfoEntity userInfoEntity = new UserInfoEntity();
//                userInfoEntity.ut = PrefrenceKey.UT;
//
//                LogUtils.i("h5EventEmit", GsonUtils.toJson(userInfoEntity));
//                String cmdUri = "javascript:h5EventEmit('pageShow', '" +GsonUtils.toJson(LoginHelper.getUserInfoEntity()) + "')";

//                LogUtils.i("h5EventEmit", cmdUri);
//                javascript:h5EventEmit('pageShow', '{"ut":"0ae14913a6cee70b08cc304ff7393c4871"}')
//                  javascript:h5EventEmit('pageShow', '{"ut":"0ae14913a6cee70b08cc304ff7393c4871"}')
//                HybridEntity hybridEntity = new HybridEntity();
//                hybridEntity.callback="h5EventEmit";
//                hybridEntity.function="pageShow";
//                hybridEntity.params="{ut:0ae14913a6cee70b08cc304ff7393c4871}";
//                mHybridEvent.execJs(hybridEntity);
//                mMSwipeLayout.setRefreshing (false);
//                mWebView.loadUrl(cmdUri);
//                setAgent(view.getSettings());
//                mWebView.postDelayed(() -> {
//                    ActionBarOdyActivity activity = (ActionBarOdyActivity) getActivity();
//                    Bitmap bitmap = null;
//                    try {
//                        bitmap = getBitmapFromView(mWebView);
//                        if (null != bitmap) {
//                            int pixel = bitmap.getPixel(200, 5);
//                            //获取颜色
//                            int redValue = Color.red(pixel);
//                            int greenValue = Color.green(pixel);
//                            int blueValue = Color.blue(pixel);
//                            Log.i(TAG, "【颜色值】 #" + Integer.toHexString(pixel).toUpperCase());
//
//                            if (activity instanceof MainActivity) {
//                                activity.setKatStatusBarColor(Color.parseColor("#" + Integer.toHexString(pixel).toUpperCase()));
//                            } else {
//                                activity.setKatStatusBarColor(Color.WHITE);
//                            }
//                            bitmap.recycle();
//
//
////                            HybridEntity msg = new HybridEntity();
////                            msg.tag = pixel;//包装颜色
////                            msg.function = HybridEntity.GET_WEB_COLOEL;
////                            EventBus.getDefault().post(msg);
//
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//
//                    }
//
//                }, 100);


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                LogUtils.i(LogUtils.WEB_TAG, "onLoadResource");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.i(LogUtils.WEB_TAG, "shouldOverrideUrlLoading " + url);
                if (url.startsWith("tel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
//特殊处理的 SCMHEA
                    String host = Uri.parse(url).getHost();
//                    if (host.equals("home")) {
//                        UrlEntity urlEntity = new UrlEntity();
//                        urlEntity.index = R.id.rb_home;
//                        RouterHelper.getHelper().Native2Activity(RouterHelper.getHelper().Schema(MainActivity.class.getName()), urlEntity);
//                    } else if (host.equals("myhome")) {
//                        UrlEntity urlEntity = new UrlEntity();
//                        urlEntity.index = R.id.rb_mine;
//                        RouterHelper.getHelper().Native2Activity(RouterHelper.getHelper().Schema(MainActivity.class.getName()), urlEntity);
//                    } else if (host.equals("logout")) {
//                        LoginHelper.cleanInfo();
//                        PreferenceUtils.edit().putBoolean(PrefrenceKey.LOGIN_STATE, false).apply();
//
//                        EventbusMessage msg = new EventbusMessage();
//                        msg.flag = EventbusMessage.REFRESH_UT;
//                        EventBus.getDefault().post(msg);
//                        ((LyfWebViewActivity) mContext).finish();
//
//
//                    } else

                    if (host.equals("login")) {
//                        测试
                        RouterHelper.getHelper().Web2Activity(url + "/fastlogin");


                    } else {
                        RouterHelper.getHelper().Web2Activity(url);
                    }

//                    if (url.contains("logout")) {
//                        UIUtils.showToastSafe("logout");
////                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, "");
////                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
////                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, "");
////                        OdyApplication.putValueByKey(Constants.BC_USER_ID, "");
////                        EventMessage msg = new EventMessage();
////                        msg.flag = EventMessage.REFRESH_UT;
////                        EventBus.getDefault().post(msg);
////                        WebActivity.this.finish();
//                    } else if (url.contains("goback")) {
//
//                        LyfWebViewActivity activity = (LyfWebViewActivity) getActivity();
//                        if (canGoBack()) {
//                            webViewGoBack();
//                        } else {
//                            activity.finish();
//                        }
//
//                    } else if (url.contains("picchoose")) {
//                        UIUtils.showToastSafe("picchoose");
////                        openPhotos(1);
//                    } else if (url.contains("home")) {
//                        UIUtils.showToastSafe("home");
////                        Bundle bundle = new Bundle();
////                        bundle.putInt(Constants.GO_MAIN, 0);
////                        RouterHelper.web2Activity(RouterHelper.MAIN, bundle);
//                    } else if (url.contains("share")) {
//                        UIUtils.showToastSafe("share");
//
//                    } else if (url.contains("addCartSuccess")) {//刷新购物车数量
//                        UIUtils.showToastSafe("addCartSuccess");
////                        EventbusMessage msg = new EventbusMessage();
////                        msg.flag = EventbusMessage.GET_CART_COUNT;
////                        EventBus.getDefault().post(msg);
////                    } else if (url.contains(HybridEntity.TO_SHOPCART)) {
////                        RouterHelper.web2Activity(RouterHelper.SHOPCART);
//                    } else {
//                        UIUtils.showToastSafe(url);
////                        RouterHelper.web2Activity(url);
//                    }
                    return true;
                }
                return true;

            }


            @Override
            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                LogUtils.i(LogUtils.WEB_TAG, "onReceivedHttpError ");
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.proceed();
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                LogUtils.i(LogUtils.WEB_TAG, "onReceivedError111" + webResourceError.getErrorCode());

                int errorCode = webResourceError.getErrorCode();
                String description = null;
                switch (errorCode) {
                    case ERROR_UNKNOWN:
                        description = "未知错误";
                        break;
                    case ERROR_HOST_LOOKUP:
                        description = "域名解析失败";
                        break;
                    case ERROR_UNSUPPORTED_AUTH_SCHEME:
                        description = "Scheme验证失败";
                        break;
                    case ERROR_AUTHENTICATION:
                        description = "验证失败";
                        break;
                    case ERROR_PROXY_AUTHENTICATION:
                        description = "代理验证失败";
                        break;
                    case ERROR_CONNECT:
                        description = "网络连接失败";
                        break;
                    case ERROR_IO:
                        description = "IO错误";
                        break;
                    case ERROR_TIMEOUT:
                        description = "请求超时";
                        break;
                    case ERROR_REDIRECT_LOOP:
                        description = "错误：重复重定向";
                        break;
                    case ERROR_UNSUPPORTED_SCHEME:
                        description = "不支持的Scheme";
                        break;
                    case ERROR_FAILED_SSL_HANDSHAKE:
                        description = "SSL handshake错误";
                        break;
                    case ERROR_BAD_URL:
                        description = "错误的地址";
                        break;
                    case ERROR_FILE:
                        description = "写入失败";
                        break;
                    case ERROR_FILE_NOT_FOUND:
                        description = "文件不存在";
                        break;
                    case ERROR_TOO_MANY_REQUESTS:
                        description = "错误：请求已到上限";
                        break;
                }
                webView.loadUrl("file:///android_asset/index.html");
                UIUtils.showToastSafe(description);
//                view.loadUrl("javascript:document.body.innerHtml='';");
//                webView.loadUrl("javascript:document.body.innerHtml='+" + description + "xxxxxxxxxxxxxxxxxxxx" + "';");
//                LogUtils.d(LogUtils.WEB_TAG, "Html Receive Error: " + description);


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtils.i(LogUtils.WEB_TAG, "onReceivedError222");
                switch (errorCode) {
                    case ERROR_UNKNOWN:
                        description = "未知错误";
                        break;
                    case ERROR_HOST_LOOKUP:
                        description = "域名解析失败";
                        break;
                    case ERROR_UNSUPPORTED_AUTH_SCHEME:
                        description = "Scheme验证失败";
                        break;
                    case ERROR_AUTHENTICATION:
                        description = "验证失败";
                        break;
                    case ERROR_PROXY_AUTHENTICATION:
                        description = "代理验证失败";
                        break;
                    case ERROR_CONNECT:
                        description = "网络连接失败";
                        break;
                    case ERROR_IO:
                        description = "IO错误";
                        break;
                    case ERROR_TIMEOUT:
                        description = "请求超时";
                        break;
                    case ERROR_REDIRECT_LOOP:
                        description = "错误：重复重定向";
                        break;
                    case ERROR_UNSUPPORTED_SCHEME:
                        description = "不支持的Scheme";
                        break;
                    case ERROR_FAILED_SSL_HANDSHAKE:
                        description = "SSL handshake错误";
                        break;
                    case ERROR_BAD_URL:
                        description = "错误的地址";
                        break;
                    case ERROR_FILE:
                        description = "写入失败";
                        break;
                    case ERROR_FILE_NOT_FOUND:
                        description = "文件不存在";
                        break;
                    case ERROR_TOO_MANY_REQUESTS:
                        description = "错误：请求已到上限";
                        break;
                }
                view.loadUrl("file:///android_asset/index.html");
//                view.loadUrl("javascript:document.body.innerHtml='';");
//                view.loadUrl("javascript:document.body.innerHtml='+" + description + "xxxxxxxxxxxxxxxxxxxx" + "';");
                LogUtils.d(LogUtils.WEB_TAG, "Html Receive Error: " + description);
            }


        });


    }

    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        if (getActivity() != null) {
            getActivity().startActivityForResult(Intent.createChooser(i, "File Chooser!"), REQUEST_CODE_FILE_PICKER);
        }
    }

    /**
     * webView canGoBack
     *
     * @return boolean
     */
    public boolean canGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    /**
     * webViewGoBack
     */
    public void webViewGoBack() {
        if (mWebView != null) {
            mWebView.goBack();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mWebView) {
            mWebView.onPause();
            mWebView.pauseTimers();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mWebView) {
            mWebView.onResume();
            mWebView.resumeTimers();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mWebView) {
            mWebView.destroy();
        }

    }

    public void onEventMainThread(HybridEntity event) {
        LogUtils.i("web---HybridEntity", event.toString());
        if (mtag == event.tag) {
            Observable.just(event).map(new Func1<HybridEntity, HybridEntity>() {
                @Override
                public HybridEntity call(HybridEntity hybridEntity) {

                    switch (hybridEntity.function) {
                        case HybridEntity.FUNCTION_CLEAR_CACHE:
//                            FileUtils.clearAllCache(getContext());
                            break;
                        case HybridEntity.FUNCTION_GET_CACHE:
                            try {
                                hybridEntity.params = "12.m";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case HybridEntity.FUNCTION_GET_VERSION_NAME:
//                            eventMessage.params = OdyApplication.VERSION_NAME;
                            break;
                        case HybridEntity.FUNCTION_SET_ALARM://领早餐提醒

                            break;
                        case HybridEntity.FUNCTION_CANCEL_ALARM://取消领早餐提醒

                            break;
                        case HybridEntity.FUNCTION_BINDTHIRD_QQ://qq授权
//                            JavaToH5Api.bindThird(getContext(), HybridEntity.FUNCTION_BINDTHIRD_QQ);
                            break;
                        case HybridEntity.FUNCTION_BINDTHIRD_WX://微信授权
//                            JavaToH5Api.bindThird(getContext(), HybridEntity.FUNCTION_BINDTHIRD_WX);
                            break;
                    }
                    return hybridEntity;
                }
            }).subscribe(new Action1<HybridEntity>() {
                @Override
                public void call(HybridEntity hybridEntity) {

                    if (hybridEntity.function.equals(HybridEntity.FUNCTION_SHARE)) {
//                        if (hybridEntity.contains("platform")) {
//                            //{"function":"share","param":{"url":"http://m.lyf.dev.laiyifen.com:8123/download/download.html","title":"来伊份APP免费早餐~我领好啦啦啦~！","description":"来伊份APP免费早餐~我领好啦啦啦~！","pic":"此处应有图片地址","platform":"SinaWeibo"}}
//                            JavaToH5Api.sharePlatform(getContext(), hybridEntity);
//                        } else {
//                            JavaToH5Api.share(getContext(), hybridEntity);
//                        }
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_GET_CAMERA) || hybridEntity.function.equals(HybridEntity.FUNCTION_UPLOAD_PHOTO)) {
//                        uploadCallback = cmd.callback;
//                        JSONObject json = null;
//                        try {
//                            json = new JSONObject(hybridEntity);
//                            isNeedCut = json.optInt("isNeedCut");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        getCamera();
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_GET_LOCATION)) {
//                        getLocation(getContext(), cmd.callback);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_PAGE_REFRESH)) {
//                        EventMessage msg = new EventMessage();
//                        msg.flag = EventMessage.REFRESH_UT;
//                        EventBus.getDefault().post(msg);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_BACK)) {
                        LogUtils.i(LogUtils.WEB_TAG, GsonUtils.toJson(hybridEntity));
                        LyfWebViewActivity activity = (LyfWebViewActivity) getActivity();
                        if (canGoBack()) {
                            webViewGoBack();
                        } else {
                            activity.finish();
                        }
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_HIDE_TITLE)) {
                        ActionBarActivity activity = (ActionBarActivity) getActivity();
                        if (hybridEntity.param.isHidden.equals("1")) {
//                            activity.setKatStatusBarColor(ContextCompat.getColor(getActivity(),R.color.white));
                            activity.hideActionbar();
                        } else {
                            activity.mTitleBar.show();
                        }
                        LogUtils.i(LogUtils.WEB_TAG, GsonUtils.toJson(hybridEntity));
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_NAVIGATE)) {
//                        JavaToH5Api.navigate(mContext, hybridEntity);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_GET_MESSAGE_INFO)) {
//                        getMessageInfo(cmd.callback);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_CALL_CMS_SHARE)) {
//                        JavaToH5Api.share(mContext, hybridEntity);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_CALL_CUSTOMS_SERVICE)) {
//                        ChatUtils.callService(getContext());
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_STATUS_AREA_CODE)) {
//                        hybridEntity = OdyApplication.getValueByKey(Constants.AREA_CODE, "");
//                        webView.execJs(cmd);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_SET_ALARM)) {
//                        NotifyUtils.pendingNotify(getContext(), WebActivity.class, 9, 57);
//                        webView.execJs(cmd);
//                        OdyApplication.putValueByKey("alarm", 1);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_CANCEL_ALARM)) {
//                        NotifyUtils.cancelAlarm(getContext(), AlarmReceiver.class);
//                        webView.execJs(cmd);
//                        OdyApplication.putValueByKey("alarm", 0);
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_STATUS_ALARM)) {
//                        if (OdyApplication.getValueByKey("alarm", 0) == 0) {
////                            hybridEntity = "0";
////                            webView.execJs(cmd);
//                        } else {
////                            hybridEntity = "1";
////                            webView.execJs(cmd);
//                        }
                    } else if (hybridEntity.function.equals(HybridEntity.FUNCTION_GO_BACK_HOME)) {
//                        JumpUtils.ToActivity(JumpUtils.MAIN);
                    } else {
                        mHybridEvent.execJs(hybridEntity);
                    }

                }
            });
        }
    }

//    public void onEventMainThread(EventbusMessage event) {
//        LogUtils.i("web---onEventMainThread", event.toString());
//        if (event.flag == EventbusMessage.REFRESH_UT) {
//            setAgent();
//            mWebView.loadUrl(mModel.param.url);
//        }
//    }


    @Override
    protected void onFragmentShowed() {
        super.onFragmentShowed();

        JSONObject params = new JSONObject();
        try {
            params.put("ut", OdyApplication.getLoginUt());
            mHybridEvent.execJs("h5EventEmit", "pageShow", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromView(View v) throws Exception {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

}
