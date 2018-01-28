package com.ody.p2p.views;

import android.content.Context;
import android.util.AttributeSet;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.webactivity.JavaToH5Api;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/23.
 */

public class OdyWebView extends WebView {
    private static final int PICK_PHOTO = 100;//相片code
    private int tag;
    private WebSettings ws;

    public OdyWebView(Context context) {
        super(context);
        init(context);
    }

    public OdyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OdyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        tag = getContext().hashCode();
        addJavascriptInterface(new JavaToH5Api(tag), "mobileAPI");
    }

    /**
     * 执行js 回调
     *
     * @param cmd 回调命令
     */
    public void execJs(JsEventMessage cmd) {
        if (cmd != null && !StringUtils.isEmpty(cmd.callback)) {
            if (cmd.params.contains("[") && cmd.params.contains("]")) {
                String js = "javascript:" + cmd.callback + "(" + cmd.params + ")"; //当返回载体是数组的时候
                loadUrl(js);
            } else {
                String js = "javascript:" + cmd.callback + "('" + cmd.params + "')";//当返回载体是字符串的时候
                loadUrl(js);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void pageShow() {
        JSONObject params = new JSONObject();
        try {
            params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
            execJs("h5EventEmit", "pageShow", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void execJs(String function) {
        execJs(function, null, null);
    }

    public void execJs(String function, String event) {
        execJs(function, event, null);
    }

    public void execJs(String function, String event, String params) {
        String js;
        if (StringUtils.isEmpty(event)) {
            js = "javascript:" + function + "()"; //当返回载体是数组的时候
        } else if (StringUtils.isEmpty(params)) {
            js = "javascript:" + function + "('" + event + "')"; //当返回载体是数组的时候
        } else {
            js = "javascript:" + function + "('" + event + "', '" + params + "')"; //当返回载体是数组的时候
        }
        loadUrl(js);
    }

}
