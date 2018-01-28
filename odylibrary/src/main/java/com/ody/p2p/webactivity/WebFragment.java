package com.ody.p2p.webactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.google.gson.Gson;

import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.QiYuServicePolicy;
import com.ody.p2p.R;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.entity.QIYuEntity;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.FileUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.OdyWebView;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.ody.p2p.R.id.web_show;

/**
 * Created by lxs on 2016/8/26.
 */
public class WebFragment extends BaseFragment {
    protected boolean loadFinish = false;
    private static final int PICK_PHOTO = 100;//相片code
    public OdyWebView webView;
    protected RelativeLayout rl_search;
    protected String loadUrl;
    protected RelativeLayout rl_head;
    protected TextView tv_title;
    protected ImageView iv_title;
    protected RelativeLayout rl_big_back;
    protected String title;
    protected int titleRes = -1;
    protected ImageView iv_menu_more;
    protected String arcId;
    private String userAgentString;
    private WebSettings webSetting;
    public boolean isArcFlag = false;

    public boolean isMainFlag = false;
    private JavaToH5Api javascriptInterface;
    protected String firstUrl;
    //    protected SwipeRefreshLayout refreshLayout;
    private String uploadCallback;
    protected int tag;
    private ProgressBar progress;
    protected boolean loadCom;
    protected LocationManager locationManager;
    protected boolean hide;

    @Override
    public void initPresenter() {

    }

    public void setNoTitle(boolean isNoTitle) {
        if (rl_head != null) {
            rl_head.setVisibility(isNoTitle ? View.GONE : View.VISIBLE);
        }
    }

    public void setUrl(String url) {
        this.loadUrl = url;
        if (webView != null) {
            webView.loadUrl(this.loadUrl);
        }
    }

    public void setMainFlag(boolean isMainFlag) {
        this.isMainFlag = isMainFlag;
        if (rl_big_back != null) {
            rl_big_back.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        if (tv_title != null && rl_head != null && rl_big_back != null) {
            tv_title.setText(title);
            rl_head.setVisibility(View.VISIBLE);
            iv_title.setVisibility(View.GONE);
            rl_big_back.setVisibility(View.GONE);
        }
    }

    public void setImageTitle(int titleRes) {
        this.title = title;
        if (iv_title != null && rl_head != null && rl_big_back != null) {
            iv_title.setImageResource(titleRes);
            rl_head.setVisibility(View.VISIBLE);
            iv_title.setVisibility(View.VISIBLE);
        }
    }


    public void showBack(boolean showFlag) {
        rl_big_back.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_web;
    }

    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);
        tag = getContext().hashCode();
        webView = (OdyWebView) view.findViewById(web_show);
        rl_search = (RelativeLayout) view.findViewById(R.id.rl_search);
        rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_title = (ImageView) view.findViewById(R.id.iv_title);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_menu_more = (ImageView) view.findViewById(R.id.iv_menu_more);
//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        if (title != null) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
            iv_title.setVisibility(View.GONE);
            rl_head.setVisibility(View.VISIBLE);
        }
        if (titleRes != -1) {
            iv_title.setImageResource(titleRes);
            rl_head.setVisibility(View.VISIBLE);
            iv_title.setVisibility(View.VISIBLE);
        }
        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    getActivity().finish();
                }
            }
        });

//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                setUa();
//                webView.reload();
//            }
//        });
//        refreshLayout.setEnabled(false);
    }

    @Override
    public void onResume() {
        if (loadFinish) {
            webView.pageShow();
        }
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        hide = hidden;
        if (hidden) {
            EventBus.getDefault().unregister(this);
            showFailed(false, 1);
        } else {
            EventBus.getDefault().register(this);
            if (loadFinish) {
                webView.pageShow();
            }
            if (!NetworkUtil.isNetworkAvailable(getContext())) {
                setFailedMargins(0, 0, 0, 55);
                showFailed(true, 1);
            }
            if (!loadCom) {
                setUa();
                webView.loadUrl(loadUrl);
            }
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void doBusiness(Context mContext) {
        webSetting = webView.getSettings();
        userAgentString = webSetting.getUserAgentString();
//        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getActivity().getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getActivity().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getActivity().getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http")) {
                    webView.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
                Log.d("samuel-tag", "onPageStarted" + System.currentTimeMillis() + "");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                loadFinish = true;
//                refreshLayout.setRefreshing(false);
                Log.d("samuel-tag", "onPageFinished" + System.currentTimeMillis() + "");
                super.onPageFinished(view, url);
                showBack(webView.canGoBack());
                if (url.contains("/article/")) {
                    isArcFlag = true;
                    arcId = url.substring(url.lastIndexOf("/") + 1, url.length() - 5);
                } else {
                    isArcFlag = false;
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.stopLoading();
                view.clearView();
                setFailedMargins(0, 0, 0, 55);
                showFailed(true, 1);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.stopLoading();
                view.clearView();
                setFailedMargins(0, 0, 0, 55);
                showFailed(true, 1);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtils.showShort(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                } else {
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        setUa();
        firstUrl = loadUrl;
        webView.loadUrl(loadUrl);

        CookieSyncManager.createInstance(getActivity());
        CookieSyncManager.getInstance().sync();
    }

    public void setUa() {
        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "lyf");
        ua.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        ua.put("sessionId", OdyApplication.getSessionId());
//        ua.put("versionName", OdyApplication.VERSION_NAME);
        ua.put("version", OdyApplication.VERSION_NAME);
        ua.put("platform", "android");
        Gson gson = new Gson();
        if (null == webSetting || null == userAgentString) {
            return;
        }
        webSetting.setUserAgentString(userAgentString + "--[" + gson.toJson(ua) + "]--");

    }

    public void isSearchShow(boolean showFlag) {
        rl_search.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {//选择照片回
                List<String> result = data.getStringArrayListExtra("picker_result");
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
                                return RetrofitFactory.uploadFile(s);
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

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        if (event.flag == EventMessage.REFRESH_UT) {
            setUa();
            if (!StringUtils.isEmpty(firstUrl)) {
                webView.loadUrl(firstUrl);
                firstUrl = null;
            } else {
                webView.loadUrl(loadUrl);
            }
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
                        }
                        return eventMessage;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<JsEventMessage>(new SubscriberListener<JsEventMessage>() {
                    @Override
                    public void onNext(JsEventMessage cmd) {
                        if (cmd.function.equals(JsEventMessage.FUNCTION_SHARE)) {
                            JavaToH5Api.share(getContext());
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GET_CAMERA) || cmd.function.equals(JsEventMessage.FUNCTION_UPLOAD_PHOTO)) {
                            uploadCallback = cmd.callback;
                            getCamera();
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GET_LOCATION)) {
                            getLocation(getContext(), cmd.callback);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_PAGE_REFRESH)) {
                            EventMessage msg = new EventMessage();
                            msg.flag = EventMessage.REFRESH_UT;
                            EventBus.getDefault().post(msg);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_CALL_CUSTOMS_SERVICE)) {
                            //new ChatUtils().callService(getContext());

                            if (OdyApplication.getString(Constants.SERVICE_TOGGLE, "0").equals("0")) {
                                ServiceManager.getInstance().doPolicy(new AliServicePolicy(),getContext());
                            } else {
                                ServiceManager.getInstance().doPolicy(new QiYuServicePolicy(), QIYuEntity.MY,getContext());
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_RED_PACKAGE)) {
                            JumpUtils.ToActivity(JumpUtils.RED_PACKET);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_BACK)) {
                            if (webView.canGoBack()) {
                                webView.goBack();
                            } else {
                                getActivity().finish();
                            }
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_HIDE_TITLE)) {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(cmd.params);
                                String isHidden = json.optString("isHidden");
                                if (isHidden.equals("1")) {
                                    hideTitle();
                                } else {
                                    showTitle();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (cmd.function.equals(JsEventMessage.ADD_CART_SUCCESS)) {
                            EventbusMessage eventbusMessage = new EventbusMessage();
                            eventbusMessage.flag = EventbusMessage.GET_CART_COUNT;
                            EventBus.getDefault().post(eventbusMessage);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_CALL_CMS_SHARE)) {
                            JavaToH5Api.share(getActivity(), cmd.params);
                        } else if (cmd.function.equals(JsEventMessage.FUNCTION_GO_BACK_HOME)) {
                            JumpUtils.ToActivity(JumpUtils.MAIN);
                        } else {
                            webView.execJs(cmd);
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
                        params.put("latitude", location.lan);
                        params.put("longitude", location.lon);
                        JsEventMessage p = new JsEventMessage();
                        p.callback = callback;
                        p.params = new Gson().toJson(params);
                        webView.execJs(p);
                    }
                }).startLocation(getContext());
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
            }
        });
    }

    /**
     * 打开相册选择界面
     */
    private void getCamera() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_show_camera", true);
        bundle.putInt("select_mode", 1);
        bundle.putInt("max_num", 1);
        JumpUtils.ToActivityFoResult(JumpUtils.PICCHOOSE, bundle, PICK_PHOTO, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (locationManager != null) {
            locationManager.stopLocation();
        }
    }


    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        } else {
            webView.clearView();
            showFailed(false, 1);
            setUa();
            webView.loadUrl(loadUrl);
        }
    }
}
