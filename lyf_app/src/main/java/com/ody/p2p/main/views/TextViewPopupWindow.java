package com.ody.p2p.main.views;

import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.pay.CardPayBean;
import com.ody.p2p.main.pay.CashierStandActivity;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengya on 2017/4/20.
 */

public class TextViewPopupWindow extends PopupWindow {

    private View view;

    //type 类型 0:悠点卡 1:伊点卡
    public TextViewPopupWindow(final CashierStandActivity context, final int type, String money, final String versionNo, final String orderCode) {
        Log.d("money", "卡内余额：" + money);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_textview, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) view.findViewById(R.id.popupwindow_textview_string);
        if (type == 0) {
            text.setText(Html.fromHtml("确定使用悠点卡<br/>支付<font color='#FF6900'><small>¥</small><big><big><big>" + money + "</big></big></big></font>"));
        } else if (type == 1) {
            text.setText(Html.fromHtml("确定使用伊点卡<br/>支付<font color='#FF6900'><small>¥ </small><big><big><big>" + money + "</big></big></big></font>"));
        }

        TextView sure = (TextView) view.findViewById(R.id.popupwindow_textview_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                payByCard(type, versionNo, orderCode, context);
            }
        });
        TextView cancel = (TextView) view.findViewById(R.id.popupwindow_textview_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
    }

    /**
     * @param type
     * @param versionNo
     * @param orderCode
     * @param context
     */
    private void payByCard(int type, String versionNo, String orderCode, final CashierStandActivity context) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("orderCode", orderCode);
        params.put("versionNo", versionNo);
        if (type == 0) {
            params.put("payMethod", "1");
        } else {
            params.put("payMethod", "2");
        }
        context.showLoading();
        OkHttpManager.getAsyn(Constants.PAYBYCARD, params, new OkHttpManager.ResultCallback<CardPayBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                context.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
//                ToastUtils.showShort(msg);
                if (cardPaySuccessListener != null) {
                    cardPaySuccessListener.cardPayFaild();
                }
            }

            @Override
            public void onResponse(CardPayBean response) {
                if (cardPaySuccessListener != null) {
                    if (response != null && "0".equals(response.code)) {
                        //mTODO:meiyizhi 悠点卡/伊点卡抵扣成功，刷新收银台数据
                        cardPaySuccessListener.cardPaySuccess(response.data.isPaid);
                    } else {
                        cardPaySuccessListener.cardPayFaild();
                    }
                }
            }
        });
    }

    private CardPaySuccessListener cardPaySuccessListener;

    public void setCardPaySuccessListener(CardPaySuccessListener cardPaySuccessListener) {
        this.cardPaySuccessListener = cardPaySuccessListener;
    }

    public interface CardPaySuccessListener {
        void cardPaySuccess(int isPaid);

        void cardPayFaild();
    }
}
