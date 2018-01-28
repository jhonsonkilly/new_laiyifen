package com.ody.p2p.main.views;

import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.main.R;
import com.ody.p2p.main.pay.LYFPayListAdapter;
import com.ody.p2p.main.pay.PayForLeisurelyActivity;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.pay.payMode.payOnline.PrePayInfoBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.MyListView;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengya on 2017/4/21.
 */

public class PayForLeisurelyPopupWindow extends PopupWindow {

    private LYFPayListAdapter payGatewayAdapter;
    private View view;
    private String mPayType;
    PayForLeisurelyActivity context;

    public PayForLeisurelyPopupWindow(PayForLeisurelyActivity context, PayTypeListBean payTypeListBean, final String orderType, final String topUpMoney, final String userId) {
        super(context);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_pay_for_leisurely, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView mImageViewClose = (ImageView) view.findViewById(R.id.popupwindow_pay_for_leisurely_close);
        TextView mTextViewClose = (TextView) view.findViewById(R.id.popupwindow_pay_for_leisurely_cancel);
        mImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTextViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        MyListView mMyListView = (MyListView) view.findViewById(R.id.popupwindow_pay_for_leisurely_payway);
        payGatewayAdapter = new LYFPayListAdapter(context);
        payGatewayAdapter.addData(payTypeListBean.data.commonPayGatewayList);
        mMyListView.setAdapter(payGatewayAdapter);
        mMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPayType = payGatewayAdapter.getItem(position).promLabel;
                //mTODO:meiyizhi 调用接口创建充值信息
                createPay(payGatewayAdapter.getItem(position).paymentConfigId, topUpMoney, orderType, userId);

                if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {

//                    payOnlinePresenter.getPayInfoByNum(orderId, orderMoney, userId, payGatewayAdapter.getItem(position).paymentConfigId, orderType);
                } else {
//                    payOnlinePresenter.getPayInfo(payGatewayAdapter.getItem(position).paymentConfigId, orderId, payGatewayAdapter.getItem(position).promotionId);
                }
            }
        });

        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
    }

    /**
     * 创建充值信息
     */
    private void createPay(String paymentConfigId, String orderMoney, String orderType, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNo", "");
        map.put("paymentConfigId", paymentConfigId + "");
        map.put("money", orderMoney);
        map.put("userId", userId);
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        if (!TextUtils.isEmpty(orderType)) {
            map.put("orderType", orderType);
        }
        if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
            map.put("sessionId", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        } else {
            map.put("sessionId", OdySysEnv.getSessionId());
        }
        OkHttpManager._noSessionId(Constants.GET_PAYINFO, new OkHttpManager.ResultCallback<PrePayInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showToast(e.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(PrePayInfoBean response) {
                if (response != null) {
                    //mTODO:meiyizhi 开始充值
                    context.startPay(response);
                    dismiss();
                }
            }
        }, map);
    }
}
