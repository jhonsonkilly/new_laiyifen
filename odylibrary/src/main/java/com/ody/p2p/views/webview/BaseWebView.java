package com.ody.p2p.views.webview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.utils.PxUtils;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by pzy on 2017/3/23.
 */
public class BaseWebView extends WebView {
    public String url;
    public View viewFailed;
    public TextView tv_faild;
    public LinearLayout ll_notdataH5;
    public BaseWebView(Context context) {
        super(context);
        initFailed();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFailed();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFailed();
    }

    public void initFailed() {
        viewFailed = LayoutInflater.from(getContext()).inflate(R.layout.view_failed_load, null);
        viewFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url)){
                    reload();
                }
            }
        });
        tv_faild = (TextView) viewFailed.findViewById(R.id.tv_faild);
        ll_notdataH5 = (LinearLayout) viewFailed.findViewById(R.id.ll_notdataH5);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.TOP | Gravity.RIGHT;
        lp.setMargins(0, PxUtils.dipTopx(43), 0, 0);
        viewFailed.setLayoutParams(lp);
        this.addView(viewFailed,0);
        viewFailed.setVisibility(View.GONE);
    }

    protected void showFailed(boolean showFlag, int type) {
        if (viewFailed != null) {
            viewFailed.setVisibility(showFlag ? View.VISIBLE : View.GONE);
            if (type == 0) {
                tv_faild.setText(R.string.a_o_load_faile);
            } else if (type == 1) {
                tv_faild.setText(R.string.Network_convulsions_please_checking);
            }
        }
    }

    @Override
    public void loadUrl(String url) {
        this.url=url;
        if (isNetworkConnected(getContext())){
            showFailed(false,1);
            super.loadUrl(url);
        }else{
            showFailed(true,1);
        }
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        this.url=url;
        if (isNetworkConnected(getContext())){
            showFailed(false,1);
            super.loadUrl(url, additionalHttpHeaders);
        }else{
            showFailed(true,1);
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
