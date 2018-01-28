package com.ody.p2p.webactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.ody.p2p.Constants;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Sun on 2016/9/23.
 */

public class NoTitleWebFragment extends WebFragment {

    public static final int JUMP_NORMAL = 1000;
    public static final int JUMP_REPLACE = 1001;

    public int loadType = JUMP_NORMAL;

    @Override
    public void initView(View view) {
        super.initView(view);
        setNoTitle(true);
    }


    public void reload() {
        setUa();
        if (null != webView) {
            webView.reload();
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            setUa();
//            if (!StringUtils.isEmpty(firstUrl)) {
//                if (StringUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
//                    webView.loadUrl(loadUrl);
//                } else {
//                    webView.loadUrl(firstUrl);
//                    firstUrl = null;
//                }
//            } else {
//                webView.loadUrl(loadUrl);
//            }
//        }
//    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http")) {
                    if (loadType == JUMP_REPLACE) {
                        if (StringUtils.isEmpty(firstUrl) || firstUrl.equals(url)) {
                            firstUrl = url;
                            webView.loadUrl(firstUrl);
                        } else {
                            JumpUtils.ToWebActivity(JumpUtils.H5, url, WebActivity.NO_TITLE, -1, "");
                        }
                    } else {
                        JumpUtils.ToWebActivity(JumpUtils.H5, url, WebActivity.NO_TITLE, -1, "");
                    }
                } else if (url.contains(JsEventMessage.ADDRESS_MANAGER)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("isHomeCenter", 1);
                    JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS, bundle);
                } else if (url.contains("lyf://category")) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.GO_MAIN,1);
                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                } else if (url.equals(JumpUtils.HOME)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.GO_MAIN, 0);
                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                } else if (url.equals(JumpUtils.MY)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.GO_MAIN, 4);
                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                } else {
                    JumpUtils.ToActivity(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //showLoading();
                super.onPageStarted(view, url, favicon);
                loadCom = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //hideLoading();
                loadFinish = true;
//                refreshLayout.setRefreshing(false);
//                webView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
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
                if (!isHidden()) {
                    setFailedMargins(0, 0, 0, 55);
                    showFailed(true, 1);
                }
                loadCom = false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.stopLoading();
                view.clearView();
                if (!isHidden()) {
                    setFailedMargins(0, 0, 0, 55);
                    showFailed(true, 1);
                }
                loadCom = false;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                //避免证书加载问题
                sslErrorHandler.proceed();
            }
        });
        webView.addJavascriptInterface(this, "App");
    }

    @JavascriptInterface
    public void resize(final float height) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("addOnChangedListener", "resize");
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                //此处的 layoutParmas 需要根据父控件类型进行区分，这里为了简单就不这么做了
                ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density);
                webView.setLayoutParams(layoutParams);
            }
        });
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

}
