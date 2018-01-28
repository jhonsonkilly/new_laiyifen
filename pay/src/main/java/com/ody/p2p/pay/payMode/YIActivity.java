package com.ody.p2p.pay.payMode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.pay.R;
import com.ody.p2p.pay.payMode.payOnline.PrePayInfoBean;
import com.ody.p2p.utils.JumpUtils;

import java.net.URLEncoder;

import cmb.pb.util.CMBKeyboardFunc;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.ody.pay.zh</p>
 * <p>文件名:未命名文件夹</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:zhoujiawei@laiyifen.com">vernal(周佳伟)</a>
 */
@SuppressWarnings("deprecation")

public class YIActivity extends BaseActivity {

    private WebView webView;
    private CMBKeyboardFunc kbFunc;

    private PrePayInfoBean.PaymentMessage paymentMessage;

    @Override
    public int bindLayout() {
        return R.layout.activity_webview_pay;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView(View view) {
        webView = (WebView) view.findViewById(R.id.webview);
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setSaveFormData(false);
        set.setSavePassword(false);
        set.setSupportZoom(false);


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void doBusiness(Context mContext) {
        try {
            String payJson = getIntent().getStringExtra(Constants.PAY_LIST);
            final String order = getIntent().getStringExtra(Constants.ORDER_ID);
            paymentMessage = new Gson().fromJson(payJson, PrePayInfoBean.PaymentMessage.class);

            String payMes = "Amount=" + URLEncoder.encode(paymentMessage.p_data.Amount, "utf-8") + "&" +
                    "BillNo=" + URLEncoder.encode(paymentMessage.p_data.BillNo, "utf-8") + "&" +
                    "BranchID=" + URLEncoder.encode(paymentMessage.p_data.BranchID, "utf-8") + "&" +
                    "Cono=" + URLEncoder.encode(paymentMessage.p_data.Cono, "utf-8") + "&" +
                    "Date=" + URLEncoder.encode(paymentMessage.p_data.Date, "utf-8") + "&" +
                    "ExpireTimeSpan=" + URLEncoder.encode(paymentMessage.p_data.ExpireTimeSpan, "utf-8") + "&" +
                    "MerchantCode=" + URLEncoder.encode(paymentMessage.p_data.MerchantCode, "utf-8") + "&" +
                    "MerchantRetUrl=" + URLEncoder.encode(paymentMessage.p_data.MerchantRetUrl, "utf-8") + "&" +
                    "MerchantUrl=" + URLEncoder.encode(paymentMessage.p_data.MerchantUrl, "utf-8");
            Log.i("YIActivity", payMes);
            Log.i("YIActivity1", paymentMessage.PayUrl);

            webView.postUrl(paymentMessage.PayUrl, payMes.getBytes());
            kbFunc = new CMBKeyboardFunc(this);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 使用当前的WebView加载页面
                    if (url.contains(OdyApplication.H5URL)) {
                        Bundle bd = new Bundle();
                        bd.putString(Constants.ORDER_ID, order);
                        JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
                        finish();
                    }
                    if (kbFunc != null && kbFunc.HandleUrlCall(view, url) == false) {
                        return super.shouldOverrideUrlLoading(view, url);
                    } else {
                        return true;
                    }
                }
            });

        } catch (Exception e) {

        }


    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {

    }


}
