
package com.ody.p2p.webactivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.wxlib.util.NetworkUtil;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;
import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.QiYuServicePolicy;
import com.ody.p2p.R;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.entity.QIYuEntity;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.eventbus.TaklingDataEventMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.receiver.AlarmReceiver;
import com.ody.p2p.receiver.NotifyUtils;
import com.ody.p2p.receiver.RemmindReceiver;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.BitmapUtils;
import com.ody.p2p.utils.FileUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.PermissionUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.OdyWebView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.datatist.sdk.DatatistSDK;
import org.datatist.sdk.Track;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.campusapp.router.router.ActivityRouter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.ody.p2p.R.id.web_show;
import static com.ody.p2p.utils.JumpUtils.getUrlBean;

/**
 * Created by lxs on 2016/8/26.
 */
public class WebActivity extends BaseActivity {
    private boolean loadFinish = false;
    protected OdyWebView webView;
    protected RelativeLayout rl_search;
    protected RelativeLayout rl_head;
    protected String loadUrl;
    protected RelativeLayout rl_big_back;
    protected ImageView iv_title;
    protected TextView tv_title;
    protected ImageView iv_menu_more;
    private ImageView shareImg;
    protected String arcId;

    protected int title_res;
    protected String title;
    protected int showType;

    public final static int SEARCH_TITLE = 1;
    public final static int CONTENT_TITLE = 2;
    public final static int PIC_TITLE = 3;
    public final static int NO_TITLE = 4;

    public final static int CROP_PICTURE = 101;
    private static final int PICK_PHOTO = 100;//相片code

    public boolean isArcFlag = false;
    private List<String> urls = new ArrayList<>();
    protected int help;
    protected WebSettings webSetting;
    protected String userAgentString;
    protected SwipeRefreshLayout refreshLayout;
    private int tag;
    private String uploadCallback;
    private List<YWMessage> msgList;
    private YWIMKit mIMKit;
    private YWConversation conversation;
    private ProgressBar progressBar;

    protected LocationManager locationManager;

    public List<String> titleList = new ArrayList<>();

    protected int isNeedCut = 0;
    private boolean backAndFinish;
    private boolean isBack = false;
    SystemBarTintManager tintManager;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // create our manager instance after the content view is set
        tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        tintManager.setTintColor(Color.parseColor("#FFFFFF"));

        initBC();
        tag = this.hashCode();
        EventBus.getDefault().register(this);
        webView = (OdyWebView) view.findViewById(web_show);
        rl_search = (RelativeLayout) view.findViewById(R.id.rl_search);
        rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);
        shareImg = (ImageView) findViewById(R.id.share);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_title = (ImageView) view.findViewById(R.id.iv_title);
        iv_menu_more = (ImageView) view.findViewById(R.id.iv_menu_more);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        showTop(webView);
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            setFailedMargins(0, 0, 0, 0);
            showFailed(true, 1);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void initListener() {
        super.initListener();
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShareInfo();
            }
        });
        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    try {
                        titleList.remove(titleList.size() - 1);
                        if (tv_title != null && titleList != null) {
                            tv_title.setText(titleList.get(titleList.size() - 1));
                        }
                    } catch (Exception e) {
                    }
                } else {
                    finish();
                }
//                if (urls != null && urls.size() > 1) {
//                    urls.remove(urls.size() - 1);
//                    web_show.loadUrl(urls.get(urls.size() - 1));
//                } else {
//                    finish();
//                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(loadUrl);
            }
        });
        refreshLayout.setEnabled(false);
    }

    private void getShareInfo() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

        } else {
            webView.evaluateJavascript("getShareInfo()", new ValueCallback<String>() {

                @Override
                public void onReceiveValue(String value) {
                    JavaToH5Api.share(mContext, value);
                }
            });
        }
    }

    /**
     * 初始化百川
     */
    private void initBC() {
        String userId = OdyApplication.getString(Constants.BC_USER_ID, "");
        if (StringUtils.isEmpty(userId)) return;
        String password = OdyApplication.getValueByKey(Constants.BC_PASS, "");
        String app_key = OdyApplication.getValueByKey(Constants.APP_KEY, "");
        mIMKit = YWAPI.getIMKitInstance(userId, app_key);
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userId, password);
        loginService.login(loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                YWIMCore imCore = mIMKit.getIMCore();
                List<YWConversation> conversationList = imCore.getConversationService().getConversationList();
                if (conversationList == null || conversationList.size() == 0) return;
                conversation = conversationList.get(0);
            }

            @Override
            public void onProgress(int arg0) {
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                ToastUtils.showShort(description);
            }
        });
    }

    private void getMessageInfo(final String callback) {
        if (conversation == null) return;
        msgList = conversation.getMessageLoader().loadMessage(1, new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                //到这里表明loadMessage同步返回的List已经有内容，请在这里同步更新UI，比如进行notifyDataSetChange调用
                if (msgList != null && msgList.size() > 0) {
                    YWMessage msg = msgList.get(0);
                    JSONObject params = new JSONObject();
                    JSONObject m = new JSONObject();
                    try {
                        m.put("time", msg.getTime());
                        m.put("content", msg.getMessageBody().getContent());
                        params.put("lastMsg", m);
                        params.put("count", conversation.getUnreadCount());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsEventMessage jsMessage = new JsEventMessage();
                    jsMessage.callback = callback;
                    jsMessage.params = params.toString();
                    webView.execJs(jsMessage);
                }
            }

            @Override
            public void onError(int code, String info) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    @Override
    public void doBusiness(final Context mContext) {
        showType = getIntent().getIntExtra("showType", -1);
        help = getIntent().getIntExtra("help", -1);


        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);


        webSetting = webView.getSettings();
        userAgentString = webSetting.getUserAgentString();


        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http")) {
                    if (url.contains("login")) {
                        JumpUtils.ToActivity(JumpUtils.LOGIN);
                        finish();
                        //东方财富的
                    } else if (url.contains("d.eastmoney.cn")) {
                        goToMarket(WebActivity.this, "com.eastmoney.android.berlin");
                    } else if (url.contains("/my/my-order.html")) {


                        JumpUtils.ToActivity(JumpUtils.ORDERLIST);
                        finish();

//                    } else if (url.contains("/my/order-detail.html")) {
//
//
//                        JumpUtils.ToActivity(JumpUtils.ORDERDETAIL);
//                        finish();

                    } else if (url.contains("/my/home.html")) {

                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 4);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                        finish();


                    } else {
                        loadUrl = url;
                        webView.loadUrl(loadUrl);
                        return false;
                    }
                } else {
                    if (url.contains("logout")) {
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, "");
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, "");
                        OdyApplication.putValueByKey(Constants.BC_USER_ID, "");
                        EventMessage msg = new EventMessage();
                        msg.flag = EventMessage.REFRESH_UT;
                        EventBus.getDefault().post(msg);
                        WebActivity.this.finish();
                    } else if (url.contains("goback")) {
                        finish();
                    } else if (url.contains("picchoose")) {
                        openPhotos(1);
                    } else if (url.contains("lyf://myhome")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 4);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    } else if (url.contains("lyf://home")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 0);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    } else if (url.contains("lyf://category")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 1);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    } else if (url.contains("share")) {
                        UrlBean bean = getUrlBean(url);
                        share(bean);
                    } else if (url.contains("addCartSuccess")) {//刷新购物车数量
                        EventbusMessage msg = new EventbusMessage();
                        msg.flag = EventbusMessage.GET_CART_COUNT;
                        EventBus.getDefault().post(msg);
                    } else if (url.contains(JsEventMessage.TO_SHOPCART)) {
                        JumpUtils.ToActivity(JumpUtils.SHOPCART);
                    } else {
                        JumpUtils.ToActivity(url);
                        Log.d("payUrl", url);
                        if (url.contains("lyf://pay") && loadUrl.contains("pay/pay.html")) {
                            view.goBack();
                        }
                    }
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                setDefaultStatusBarColor();
                if (backAndFinish && !url.contains("order-detail.html")) {
                    finish();
                }
                if (url.contains("pay/pay.html")) {
                    if (isBack) {
                        view.goBack();
                        isBack = false;
                    }
                }
                super.onPageStarted(view, url, favicon);

            }


            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                resend.sendToTarget();
                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
                if (url.contains("/placeChannel/index.html")) {//分享赚钱
                    tintManager.setStatusBarTintResource(R.drawable.status);
//                    StatusBarCompat.setStatusBarColor(WebActivity.this, Color.parseColor("#FFB300"), false);
                } else if (url.contains("/my/my-income.html")) {//我的收入
                    tintManager.setStatusBarTintResource(R.drawable.status);
                } else {
                    setDefaultStatusBarColor();
                }
                // hideLoading();
                super.onPageFinished(view, url);
                if (!isWhiteUrl(url)) {
                    showTitle();
                }
                loadFinish = true;
                refreshLayout.setRefreshing(false);
                if (!urls.contains(url)) {
                    urls.add(url);
                }
                if (help != 1) {
                    if (url.contains("/article/")) {
                        isArcFlag = true;
                        arcId = url.substring(url.lastIndexOf("/") + 1, url.length() - 5);
                    } else {
                        isArcFlag = false;
                    }
                } else {
                    arcId = url.substring(url.lastIndexOf("/") + 1, url.length() - 5);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.stopLoading();
                view.clearView();
                setFailedMargins(0, 0, 0, 0);
                showFailed(true, 1);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.stopLoading();
                view.clearView();
                setFailedMargins(0, 0, 0, 0);
                showFailed(true, 1);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtils.showShort(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleList.add(title);
                if (!StringUtils.isEmpty(title) && tv_title != null) {
                    tv_title.setText(title);
                    tv_title.setVisibility(View.VISIBLE);
                }
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        if (help == 2) {
            iv_menu_more.setVisibility(View.GONE);
        }
        if (showType == SEARCH_TITLE) {
            rl_search.setVisibility(View.VISIBLE);
            rl_head.setVisibility(View.GONE);
        } else if (showType == CONTENT_TITLE) {
            title = getIntent().getStringExtra("titleContent");
            tv_title.setText(title);
            rl_head.setVisibility(View.VISIBLE);
            rl_search.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            iv_title.setVisibility(View.GONE);
        } else if (showType == PIC_TITLE) {
            title_res = getIntent().getIntExtra("titleRes", -1);
            iv_title.setImageResource(title_res);
            rl_head.setVisibility(View.VISIBLE);
            rl_search.setVisibility(View.GONE);
            iv_title.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
        } else if (showType == NO_TITLE) {
            rl_head.setVisibility(View.GONE);
            //  rl_head.setVisibility(View.VISIBLE);
        }

        if (getIntent().getStringExtra("loadUrl") != null) {
            loadUrl = getIntent().getStringExtra("loadUrl");
        } else {
            String url = getIntent().getStringExtra(ActivityRouter.KEY_URL);
            try {
                url = URLDecoder.decode(url, "utf-8");
                url = Uri.parse(url).getQueryParameter("body");
                Gson gson = new Gson();
                UrlBean bean = gson.fromJson(url, UrlBean.class);
                loadUrl = bean.url;
            } catch (Exception e) {
//                ToastUtils.showShort(getString(R.string.invalid_url));
                finish();
                return;
            }
        }
        setUserAgent();

        //对直播的处理
        if (loadUrl.contains("zhibo")) {

            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                shareImg.setVisibility(View.VISIBLE);
                try {
                    loadUrl = loadUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, "");
                } catch (UnsupportedEncodingException e) {
                    loadUrl = loadUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, "");
                    e.printStackTrace();
                }

               /* Intent intent = new Intent(this, NewWebActivity.class);
                startActivity(intent);
                finish();*/
            } else {
                JumpUtils.ToActivity(JumpUtils.LOGIN);
                finish();
            }

        }

        if (loadUrl.contains("seckill")) {


            JumpUtils.ToActivity(JumpUtils.QIANGGOU);
            finish();

        }

        if (loadUrl.contains("flashSales")) {
            JumpUtils.ToActivity(JumpUtils.FLASHSALE);
            finish();

        }
        webView.loadUrl(loadUrl);


        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void setDefaultStatusBarColor() {
        if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
//            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#B0B0B0"), true);
            tintManager.setStatusBarTintColor(Color.parseColor("#B0B0B0"));
        } else {
//            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FFFFFF"), true);
            tintManager.setStatusBarTintColor(Color.parseColor("#FFFFFF"));
        }
    }


    @Override
    public void resume() {
        if (loadFinish) {
            webView.pageShow();
        }
    }


    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        if (locationManager != null) {
            locationManager.stopLocation();
        }
    }


    public void isSearchShow(boolean showFlag) {
        rl_search.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            try {
                titleList.remove(titleList.size() - 1);
                if (tv_title != null && titleList != null) {
                    tv_title.setText(titleList.get(titleList.size() - 1));
                }
            } catch (Exception e) {
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 调起相片
     */
    private void openPhotos(final int num) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                //mTODO:meiyizhi
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA//相机
        )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("is_show_camera", true);
                            bundle.putInt("select_mode", 1);
                            bundle.putInt("max_num", num);
                            JumpUtils.ToActivityFoResult(JumpUtils.PICCHOOSE, bundle, PICK_PHOTO, WebActivity.this);
                        } else {
                            ToastUtils.showShort("为了更好的使用体验，请开启相机和读取存储卡权限!");
                        }
                    }
                });
    }

    private Uri uritempFile;

    String filepath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {//选择照片回
                List<String> result = data.getStringArrayListExtra("picker_result");
                if (result != null && result.size() > 0) {
                    if (isNeedCut == 1) {
                        Intent intent = new Intent();
                        intent.setAction("com.android.camera.action.CROP");
                        intent.setDataAndType(Uri.fromFile(new File(result.get(0))), "image/*");// mUri是已经选择的图片Uri
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);// 裁剪框比例
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 150);//输出图片大小
                        intent.putExtra("outputY", 150);
                        File file = new File(BitmapUtil.PIC_PATH, "smaller.jpg");
                        try {
                            if (!file.exists()) {
                                file.getParentFile().mkdirs();
                            }
                            filepath = file.getAbsolutePath();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        uritempFile = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        startActivityForResult(intent, CROP_PICTURE);
                    } else {
                        final List<String> imgUrls = new ArrayList<>();
                        //图片上传
                        Observable.from(result)
                                .subscribeOn(Schedulers.computation())
                                .filter(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String s) {
                                        return !StringUtils.isEmpty(s) && new File(s).exists();
                                    }
                                })
                                .flatMap(new Func1<String, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(String s) {
                                        String photoPath = BitmapUtil.compressImage(s);
                                        return RetrofitFactory.uploadFile(photoPath);
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriber<String>(new SubscriberListener<String>() {
                                    @Override
                                    public void onNext(String fileUrl) {
                                        imgUrls.add(fileUrl);
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        JsEventMessage p = new JsEventMessage();
                                        p.callback = uploadCallback;
                                        p.params = new Gson().toJson(imgUrls);
                                        webView.execJs(p);
                                    }
                                }));
                    }
                }
            }
        } else if (requestCode == CROP_PICTURE) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            final List<String> imgUrls = new ArrayList<>();
            List<String> result = new ArrayList<>();
            result.add(filepath);
            //图片上传
            Observable.from(result)
                    .subscribeOn(Schedulers.computation())
                    .filter(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String s) {
                            return !StringUtils.isEmpty(s) && new File(s).exists();
                        }
                    })
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            String photoPath = BitmapUtil.compressImage(s);
                            return RetrofitFactory.uploadFile(photoPath);
                        }
                    })
                    .subscribeOn(Schedulers.io())


                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ApiSubscriber<String>(new SubscriberListener<String>() {
                        @Override
                        public void onNext(String fileUrl) {
                            imgUrls.add(fileUrl);
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            JsEventMessage p = new JsEventMessage();
                            p.callback = uploadCallback;
                            p.params = new Gson().toJson(imgUrls);
                            webView.execJs(p);

                            EventMessage msg = new EventMessage();
                            msg.flag = EventMessage.REFRESH_UT;
                            EventBus.getDefault().post(msg);
                        }
                    }));
        }
    }


    public void goBack(final boolean refresh) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (webView.canGoBack()) {
                    webView.goBack();
                    if (refresh) {
                        webView.loadUrl(loadUrl);
                    }
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * 拍照上传
     *
     * @param path
     */
    public void uploadingPhotos(final String path) {
        showLoading();
        String photoPath = BitmapUtil.compressImage(path);
        File file = new File(photoPath);
        try {
            OkHttpManager.postAsyn(Constants.UPLOADING_PHOTOS, new OkHttpManager.ResultCallback<PhotosBean>() {
                @Override
                public void onError(Request request, Exception e) {
                    hideLoading();
                }

                @Override
                public void onResponse(PhotosBean response) {
                    hideLoading();
                    if (null != response && null != response.getData() && null != response.getData().getFilePath()) {
                        webView.loadUrl("javascript:" + "picchooseCallback('" + response.getData().getFilePath() + "')");
                    }
                }
            }, file, "file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUserAgent() {
        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "ds");
        ua.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        ua.put("version", OdyApplication.VERSION_NAME);
        ua.put("sessionId", OdyApplication.getSessionId());
        ua.put("deviceId", OdyApplication.getDeviceId());

        Gson gson = new Gson();
        webSetting.setUserAgentString(userAgentString + "--[" + gson.toJson(ua) + "]--");
    }

    public void share(UrlBean bean) {

    }

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        if (event.flag == EventMessage.REFRESH_UT) {
            setUserAgent();
            webView.loadUrl(loadUrl);
        }
        if (event.flag == EventMessage.REFRESH_H5) {
            if (!StringUtils.isEmpty(event.h5Url)) {
                backAndFinish = true;
                webView.loadUrl(event.h5Url);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(EventbusMessage event) {
        if (event.flag == EventbusMessage.GET_CART_COUNT) {
            webView.loadUrl(loadUrl);
        }
    }

    @Subscribe
    public void onEventMainThread(JsEventMessage event) {
        if (event.msgTag != tag) return;
        Observable.just(event)
                .map(new Func1<JsEventMessage, JsEventMessage>() {
                    @Override
                    public JsEventMessage call(JsEventMessage eventMessage) {
                        switch (eventMessage.function) {
                            case JsEventMessage.FUNCTION_CLEAR_CACHE:
                                FileUtils.clearAllCache(getContext());
                                break;
                            case JsEventMessage.FUNCTION_GET_CACHE:
                                try {
                                    eventMessage.params = FileUtils.getTotalCacheSize(getContext());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case JsEventMessage.FUNCTION_GET_VERSION_NAME:
                                eventMessage.params = OdyApplication.VERSION_NAME;
                                break;
                            case JsEventMessage.FUNCTION_SET_ALARM://领早餐提醒

                                break;
                            case JsEventMessage.FUNCTION_CANCEL_ALARM://取消领早餐提醒

                                break;
                            case JsEventMessage.FUNCTION_BINDTHIRD_QQ://qq授权
                                JavaToH5Api.bindThird(getContext(), JsEventMessage.FUNCTION_BINDTHIRD_QQ);
                                break;
                            case JsEventMessage.FUNCTION_BINDTHIRD_WX://微信授权
                                JavaToH5Api.bindThird(getContext(), JsEventMessage.FUNCTION_BINDTHIRD_WX);
                                break;
                        }
                        return eventMessage;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<JsEventMessage>(new SubscriberListener<JsEventMessage>() {
                    @Override
                    public void onNext(JsEventMessage cmd) {
                        if (cmd.function.equals(JsEventMessage.FUNCTION_SHARE)) {
                            if (cmd.params.contains("platform")) {
                                JavaToH5Api.sharePlatform(getContext(), cmd.params);
                            } else {
                                JavaToH5Api.share(getContext(), cmd.params);
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GET_CAMERA) || cmd.function.equals(JsEventMessage.FUNCTION_UPLOAD_PHOTO)) {
                            uploadCallback = cmd.callback;
                            JSONObject json = null;
                            try {
                                json = new JSONObject(cmd.params);
                                isNeedCut = json.optInt("isNeedCut");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            getCamera();
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GET_LOCATION)) {
                            getLocation(getContext(), cmd.callback);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_PAGE_REFRESH)) {
                            EventMessage msg = new EventMessage();
                            msg.flag = EventMessage.REFRESH_UT;
                            EventBus.getDefault().post(msg);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_BACK)) {
                            JSONObject json = null;
                            try {
//                                {"refresh":1,"forceBack":1}
                                if (TextUtils.isEmpty(cmd.params)) {
                                    json = new JSONObject();
                                } else {
                                    json = new JSONObject(cmd.params);
                                }
                                int refresh = json.optInt("refresh");
                                int forceBack = json.optInt("forceBack");

                                if (forceBack == 1) {
                                    finish();
                                } else /*if (refresh==1)*/ {
                                    if (webView.canGoBack()) {
                                        if (loadUrl.contains("group/index.html")) {
                                            finish();
                                        } else {
                                            if (loadUrl.contains("/pay-success.html")) {
                                                isBack = true;
                                            }
                                            webView.goBack();
                                            try {
                                                titleList.remove(titleList.size() - 1);
                                                if (tv_title != null && titleList != null) {
                                                    tv_title.setText(titleList.get(titleList.size() - 1));
                                                }
                                            } catch (Exception e) {
                                            }
                                        }
                                    } else {
                                        finish();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_HIDE_TITLE)) {

                            JSONObject json = null;
                            try {
                                json = new JSONObject(cmd.params);
                                int isHidden = json.optInt("isHidden");
                                if (isHidden == 1) {
                                    hideTitle();
                                } else {
                                    showTitle();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_ADDTOCART)) {
                            try {
                                JSONObject json = null;
                                json = new JSONObject(cmd.params);
                                int quantity = json.optInt("quantity");
                                double realPrice = json.optDouble("realPrice");
                                int id = json.optInt("id");

                                /*TaklingDataEventMessage msg = new TaklingDataEventMessage();
                                msg.setAction(TaklingDataEventMessage.ONADDITEMTOSHOPPINGCART);
                                Map<String, String> map = new HashMap<>();
                                map.put("itemId", id + "");
                                map.put("category", "");
                                map.put("name", "");
                                map.put("quantity", quantity + "");
                                map.put("amount", realPrice + "");
                                msg.setExtra(map);
                                EventBus.getDefault().post(msg);*/

                                Track.track().cart()
                                        .trackAddCart(id + "", quantity, realPrice, new HashMap<String, String>())
                                        .submit(DatatistSDK.getTracker());


                            } catch (Exception e) {

                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_PAGEVIEW)) {
                            try {
                                JSONObject json = null;
                                json = new JSONObject(cmd.params);

                                String link = json.optString("link");
                                String title = json.optString("title");

                                Track.track().pageview().setTitle(title).setUrl(link).trackPageview(new HashMap<String, String>()).submit(DatatistSDK.getTracker());


                            } catch (Exception e) {

                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_NAVIGATE)) {
                            JavaToH5Api.navigate(mContext, cmd.params);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GET_MESSAGE_INFO)) {
                            getMessageInfo(cmd.callback);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_CALL_CMS_SHARE)) {
                            JavaToH5Api.share(mContext, cmd.params);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_CALL_CUSTOMS_SERVICE)) {

                            if (OdyApplication.getString(Constants.SERVICE_TOGGLE, "0").equals("0")) {

                                ServiceManager.getInstance().doPolicy(new AliServicePolicy(), WebActivity.this);
                            } else {
                                ServiceManager.getInstance().doPolicy(new QiYuServicePolicy(), QIYuEntity.MY, WebActivity.this);
                            }


                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_STATUS_AREA_CODE)) {
                            cmd.params = OdyApplication.getValueByKey(Constants.AREA_CODE, "");
                            webView.execJs(cmd);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_SET_ALARM)) {
                            try {
                                JSONObject json = null;
                                json = new JSONObject(cmd.params);
                                long time = json.optLong("firetime");
                                String url = json.optString("linkurl");
                                String message = json.optString("firemessage");
                                NotifyUtils.pendingNotify(getContext(), AlarmReceiver.class, Integer.parseInt(getTime(time)[0]), Integer.parseInt(getTime(time)[1]), url, message);
                                webView.execJs(cmd);
                                OdyApplication.putValueByKey("alarm", 1);
                            } catch (Exception e) {

                            }


                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_CANCEL_ALARM)) {
                            NotifyUtils.cancelAlarm(getContext(), AlarmReceiver.class);
                            webView.execJs(cmd);
                            OdyApplication.putValueByKey("alarm", 0);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_STATUS_ALARM)) {
                            if (OdyApplication.getValueByKey("alarm", 0) == 0) {
                                cmd.params = "0";
                                webView.execJs(cmd);
                            } else {
                                cmd.params = "1";
                                webView.execJs(cmd);
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GO_BACK_HOME)) {
                            JumpUtils.ToActivity(JumpUtils.MAIN);
                        } else if (cmd.function.equals(JsEventMessage.SET_STATUS_BAR_COLOR)) {
//                            JSONObject json = null;
//                            try {
//                                json = new JSONObject(cmd.params);
//                                String color = json.optString("color");
//                                if (!TextUtils.isEmpty(color)) {
//                                    StatusBarCompat.setStatusBarColor(WebActivity.this, Color.parseColor(color), false);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_PURCHASE)) {
                            JSONObject json = null;
                            try {
//
                                json = new JSONObject(cmd.params);
                                String id = json.optString("mpid");
                                long time = json.optLong("firetime");
                                String remind = json.optString("cancel");


                                String proId = json.optString("firename");
                                String message = json.optString("firemessage");
                                String url = json.optString("linkurl");


                                //NotifyUtils.purchaseNotify(getContext(), WebActivity.class, time);
                                //NotifyUtils.pendingNotify(getContext(), WebActivity.class, 12, 00);
                                //webView.execJs(cmd);

                                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                // create a PendingIntent that will perform a broadcast
                                Intent intent = new Intent(getContext(), RemmindReceiver.class);
                                if (!TextUtils.isEmpty(id)) {
                                    intent.putExtra("id", id);
                                }
                                if (!TextUtils.isEmpty(proId)) {
                                    intent.putExtra("id", proId);
                                }
                                if (!TextUtils.isEmpty(message)) {
                                    intent.putExtra("mes", message);
                                }
                                if (!TextUtils.isEmpty(url)) {
                                    intent.putExtra("url", url);
                                }


                                PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                                if (TextUtils.isEmpty(remind)) {
                                    Calendar c = Calendar.getInstance();
                                    c.setTimeInMillis(time);
                                    // schedule an alarm
                                    am.set(AlarmManager.RTC_WAKEUP, time * 1000, pi);

                                    //Toast.makeText(getContext(), "提醒成功", Toast.LENGTH_SHORT).show();

                                } else {
                                    am.cancel(pi);
                                    //Toast.makeText(getContext(), "提醒取消", Toast.LENGTH_SHORT).show();
                                }

                                //NotifyUtils.purchaseNotify(getContext(), WebActivity.class, time);
                                //NotifyUtils.pendingNotify(getContext(), WebActivity.class, 12, 00);
                                //webView.execJs(cmd);


                            } catch (Exception e) {

                            }
                        } else if (cmd.function.equals(JsEventMessage.POSTER)) {//保存二维码海报
                            savePoster("确定保存二维码海报？", cmd.params);
                        } else {
                            try {
                                //兼容国泰
                                JSONObject json = null;
                                json = new JSONObject(cmd.params);

                                long time = json.optLong("firetime");

                                String proId = json.optString("firename");
                                String message = json.optString("firemessage");
                                String url = json.optString("linkurl");


                                //NotifyUtils.purchaseNotify(getContext(), WebActivity.class, time);
                                //NotifyUtils.pendingNotify(getContext(), WebActivity.class, 12, 00);
                                //webView.execJs(cmd);


                                //设置提醒
                                if (!TextUtils.isEmpty(proId)) {

                                    //国泰的链接


                                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    // create a PendingIntent that will perform a broadcast
                                    Intent intent = new Intent(getContext(), RemmindReceiver.class);
                                    if (!TextUtils.isEmpty(message)) {
                                        intent.putExtra("mes", message);
                                    }
                                    if (!TextUtils.isEmpty(url)) {
                                        intent.putExtra("url", url);
                                    }


                                    PendingIntent pi = PendingIntent.getBroadcast(getContext(), Integer.parseInt(proId), intent, 0);
                                    Calendar c = Calendar.getInstance();
                                    c.setTimeInMillis(time);

                                    if (!TextUtils.isEmpty(url)) {
                                        am.set(AlarmManager.RTC_WAKEUP, time * 1000, pi);
                                    } else {
                                        am.cancel(pi);
                                    }


                                } else {
                                    webView.execJs(cmd);
                                }


                            } catch (Exception e) {

                            }
                        }
                    }
                }));
    }

    /**
     * 获取当前定位
     */
    public void getLocation(Context context, final String callback) {
        locationManager = new LocationManager(getContext());
        locationManager
                .setOnceLocation(true)
                .setLocationListener(new LocationManager.LocationListener() {
                    @Override
                    public void onLocationChanged(LocationManager.MapLocation location) {
                        if (location == null) return;
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("city", location.city);
                        params.put("latitude", "" + location.lan);
                        params.put("longitude", "" + location.lon);
                        JsEventMessage p = new JsEventMessage();
                        p.callback = callback;
                        p.params = new Gson().toJson(params);
                        webView.execJs(p);
                    }
                }).startLocation(this);
    }

    public void hideTitle() {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rl_head.setVisibility(View.GONE);
            }
        });
    }

    public void showTitle() {
        getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rl_head.setVisibility(View.VISIBLE);
                rl_search.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                iv_title.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 打开相册选择界面
     */
    private void getCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                //mTODO:meiyizhi
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA//相机
        )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("is_show_camera", true);
                            bundle.putInt("select_mode", 1);
                            bundle.putInt("max_num", 1);
                            JumpUtils.ToActivityFoResult(JumpUtils.PICCHOOSE, bundle, PICK_PHOTO, WebActivity.this);
                        } else {
                            ToastUtils.showShort("为了更好的使用体验，请开启相机和读取存储卡权限!");
                        }
                    }
                });
    }

    /**
     * 保存二维码海报
     */
    private void savePoster(final String title, final String json) {
        PermissionUtils.checkPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            CustomDialog customDialog = new CustomDialog(getContext(), title);
                            final UrlBean bean = JumpUtils.getUrlBean(json);
                            customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                @Override
                                public void Confirm() {
                                    try {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Bitmap bitmap = BitmapUtils.drawableBitMap(getContext(),
                                                        bean.posterPicUrl, bean.qrCodeUrl);
                                                String url = BitmapUtils.saveBitmapFile(getContext(), bitmap);
                                                Log.d(getNetTAG(), "成功保存图片" + url);
                                            }
                                        }).start();
                                        ToastUtils.showShort("成功保存图片");
                                        // 最后通知图库更新
                                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Constants.IMAGEPATH)));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            customDialog.show();
                        } else {
                            ToastUtils.showShort(getString(R.string.permissiontext));
                        }
                    }
                });
    }


    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            setFailedMargins(0, 0, 0, 0);
            showFailed(true, 1);
        } else {
            showFailed(false, 1);
            webView.loadUrl(loadUrl);
        }
    }

    /**
     * url 白名单
     */
    String[] whiteUrls = new String[]{
            "touch.laiyifen.com",
            "www.laiyifen.com",
            "m.laiyifen.com",
            "m.lyf.com",
            "m.lyf.dev.laiyifen.com",
            "m.lyf.test.laiyifen.com",
            "m.ody.dev.laiyifen.com",
            "zhibo"};

    /**
     * 是否是白名单
     *
     * @param url
     * @return
     */
    public boolean isWhiteUrl(String url) {
        boolean isWhite = false;
        for (int i = 0; i < whiteUrls.length; i++) {
            if (url.contains(whiteUrls[i])) {
                isWhite = true;
                break;
            }
        }
        return isWhite;
    }

    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String[] getTime(long time) {


        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        return sf.format(d).split(":");

    }


}
