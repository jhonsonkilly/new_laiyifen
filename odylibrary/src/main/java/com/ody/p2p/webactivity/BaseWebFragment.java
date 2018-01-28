package com.ody.p2p.webactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.js.InjectedChromeClient;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.odyscorllviews.OdyScrollWebView;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2016/10/17.
 */
public class BaseWebFragment extends BaseFragment {

    public OdyScrollWebView web_show;
    protected RelativeLayout rl_search;
    protected String loadUrl;
    protected RelativeLayout rl_head;
    protected TextView tv_title;
    protected RelativeLayout rl_big_back;
    protected String title;
    protected ImageView iv_menu_more;
    protected String arcId;

    protected boolean isMainFlag = false;
    public boolean isArcFlag = false;

    protected WebSettings ws;
    protected String userAgentString;

    public int help = -1; //是否是帮助页面

    private static final int PICK_PHOTO = 100;//相片code

    @Override
    public void initPresenter() {

    }

    public void setUrl(String url) {
        this.loadUrl = url;
        if (web_show != null) {
            web_show.loadUrl(this.loadUrl);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        if (tv_title != null && rl_head != null && rl_big_back != null) {
            tv_title.setText(title);
            rl_head.setVisibility(View.VISIBLE);
            rl_big_back.setVisibility(View.GONE);
        }
    }

    public void showBack(boolean showFlag) {
        rl_big_back.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        web_show = (OdyScrollWebView) view.findViewById(R.id.web_show);
        rl_search = (RelativeLayout) view.findViewById(R.id.rl_search);
        rl_head = (RelativeLayout) view.findViewById(R.id.rl_head);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_menu_more = (ImageView) view.findViewById(R.id.iv_menu_more);
        if (title != null) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
            rl_head.setVisibility(View.VISIBLE);
            rl_big_back.setVisibility(View.GONE);
        }
        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (web_show.canGoBack()) {
                    web_show.goBack();
                } else {
                    getActivity().finish();
                }
            }
        });

        ws = web_show.getSettings();
        userAgentString = ws.getUserAgentString();
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setJavaScriptEnabled(true);
        EventBus.getDefault().register(this);
    }


    @Override
    public void doBusiness(Context mContext) {

        web_show.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    loadUrl = url;
                    web_show.loadUrl(loadUrl);
                } else {
                    if (url.contains("logout")) {
                        OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, "");
                        OdyApplication.putValueByKey(Constants.LOGIN_STATE, false);
                        OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, "");
                        getActivity().finish();
                    } else if (url.contains("goback")) {
                        //onBackPressed();
                        getActivity().finish();
                    } else if (url.contains("picchoose")) {
                        openPhotos(1);
                    } else if (url.contains("home")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 0);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    } else if (url.contains("share")) {
                        UrlBean bean = JumpUtils.getUrlBean(url);
                        share(bean);
                    } else {
                        JumpUtils.ToActivity(url);
                    }
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //showLoading();
                Log.d("samuel-tag", "onPageStarted" + System.currentTimeMillis() + "");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //hideLoading();
                Log.d("samuel-tag", "onPageFinished" + System.currentTimeMillis() + "");
                super.onPageFinished(view, url);
                if (isMainFlag) {
                    showBack(web_show.canGoBack());
                }
                super.onPageFinished(view, url);
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
        });
        Log.d("samuel-tag", "loadUrl" + System.currentTimeMillis() + "");
        setUserAgent();
        web_show.loadUrl(loadUrl);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public void isSearchShow(boolean showFlag) {
        rl_search.setVisibility(showFlag ? View.VISIBLE : View.GONE);
    }


    public void setHelp(int help) {
        this.help = help;
    }

    public void setMainFlag(boolean isMainFlag) {
        this.isMainFlag = isMainFlag;
    }

    public class CustomChromeClient extends InjectedChromeClient {

        public CustomChromeClient(String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // to do your work
            // ...
//            ToastUtils.showToast(message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // to do your work
            // ...
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            super.onJsPrompt(view, url, message, defaultValue, result);
            return true;
        }
    }

    public void share(UrlBean bean) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        web_show.destroy();
        EventBus.getDefault().unregister(this);
    }

    private void setUserAgent() {
        Map<String, String> ua = new HashMap<>();
        ua.put("company", "ody");
        ua.put("appName", "ds");
        ua.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        ua.put("sessionId", OdyApplication.getSessionId());
        Gson gson = new Gson();
        ws.setUserAgentString(userAgentString + "--[" + gson.toJson(ua) + "]--");
    }

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        if (event.flag == EventMessage.REFRESH_UT) {
            setUserAgent();
            web_show.loadUrl(loadUrl);
        }
    }

    /**
     * 调起相片
     */
    private void openPhotos(int num) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_show_camera", true);
        bundle.putInt("select_mode", 1);
        bundle.putInt("max_num", num);
        JumpUtils.ToActivityFoResult(JumpUtils.PICCHOOSE, bundle, PICK_PHOTO, this);
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
                        web_show.loadUrl("javascript:" + "picchooseCallback('" + response.getData().getFilePath() + "')");
                    }
                }
            }, file, "file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
