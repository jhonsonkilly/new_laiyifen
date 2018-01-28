package com.ody.p2p.main.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseAdapter;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.payOnline.PayResult;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.pay.payMode.payOnline.PrePayInfoBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.unionpay.UPPayAssistEx;

import java.util.HashMap;
import java.util.Map;

/**
 * 来伊份  充值支付类型
 * Created by caishengya on 2017/2/28.
 */

public abstract class LyfPayTypePopupWindow extends BasePopupWindow {
    private static final int SDK_PAY_FLAG = 1;
    private String money;
    private String mMode = "00";
    private IWXAPI api;
    private View mView;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showToast(mContext.getString(com.ody.p2p.pay.R.string.pay_succeed));
//                        RecordHelper.getInstance().payOrder(Long.valueOf(orderId),Long.valueOf(orderMoney),Long.valueOf(deliveryFee),
//                                payMethod,"PayModeActivity","PayOnlineActivity","null","PayOnlineActivity");
                        payResult(0);
//                        finish();
                    } else {
                        payResult(1);
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToast(mContext.getString(com.ody.p2p.pay.R.string.pay_result_confirmed));
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToast(mContext.getString(com.ody.p2p.pay.R.string.pay_fail));
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    public LyfPayTypePopupWindow(Context mContext, final PayTypeListBean bean, IWXAPI api, String money) {
        super(mContext);
        this.api = api;
        this.money = money;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popupwindow_choose_pay_type_lyf, null);
        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setTouchable(true);
        this.setOutsideTouchable(true);

        ListView listviewType = (ListView) mView.findViewById(R.id.popupwindow_choose_pay_type_list);
        listviewType.setAdapter(new PayTypeAdapter(mContext, bean));
        listviewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String paymentConfigId = bean.data.commonPayGatewayList.get(position).paymentConfigId;
                getPayInfo(paymentConfigId);
            }
        });
        TextView textViewCancel = (TextView) mView.findViewById(R.id.popupwindow_choose_pay_type_cancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    class PayTypeAdapter extends BaseAdapter {

        private Context mContext;
        private PayTypeListBean bean;

        public PayTypeAdapter(Context mContext, PayTypeListBean bean) {
            this.mContext = mContext;
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean.data.commonPayGatewayList.size();
        }

        @Override
        public Object getItem(int position) {
            return bean.data.commonPayGatewayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_textview, null);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.cell_textview_text);
                viewHolder.mView = convertView.findViewById(R.id.cell_textview_line);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mTextView.setText(bean.data.commonPayGatewayList.get(position).paymentThirdparty);
            if (position == bean.data.commonPayGatewayList.size() - 1) {
                viewHolder.mView.setVisibility(View.GONE);
            } else {
                viewHolder.mView.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

    class ViewHolder {
        private TextView mTextView;
        private View mView;
    }

    /**
     * 获取充值信息
     *
     * @param paymentConfigId
     */
    public void getPayInfo(String paymentConfigId) {
        Map<String, String> map = new HashMap<>();
        map.put("paymentConfigId", paymentConfigId + "");
        map.put("money", money);
        map.put("orderType", "1");//0普通订单支付（默认为0，不传为0），1订单充值，2扫码支付
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("sessionId", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postPayAsyn(Constants.GET_PAYINFO, new OkHttpManager.ResultCallback<PrePayInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showToast(e.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismiss();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(final PrePayInfoBean bean) {
                if (bean != null && bean.data != null) {
                    startPay(bean);
                }
            }
        }, map);
    }

    /**
     * 开始支付
     *
     * @param bean
     */
    public void startPay(final PrePayInfoBean bean) {
        if (!StringUtils.isEmpty(bean.data.od)) {
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask((Activity) mContext);
                    // 调用支付接口，获取支付结果
//                        String od = "partner=\\\"2088021480610734\\\"&seller_id=\\\"zfb@odianyun.com.cn\\\"&out_trade_no=\\\"201607041827391217053311\\\"&subject=\\\"201607040001\\\"&body=\\\"201607040001\\\"&total_fee=\\\"0.01\\\"&notify_url=\\\"http://180.167.73.34:13125/opay-web/updateOrderFromAli.do\\\"&service=\\\"mobile.securitypay.pay\\\"&payment_type=\\\"1\\\"&_input_charset=\\\"utf-8\\\"&it_b_pay=\\\"30m\\\"&sign=\\\"csUZg6eIIxnZKiG69NIbGkAh5Nj3WXyJV1o5ifaZfI4xdfwoX%2Bl6IJ4fVKLtgISw%2FuhJcZ%2FF%2BTbAzwGRFstL%2B96qe9oFD3keZAClStc38W9nNavdooVWW4an6U6ykfpTkIfC9foyO5pXr2r9q%2FSzsytmW0iuKApc%2BsqLoYgAFEc%3D\\\"&sign_type=\\\"RSA\\\"";
                    String result = alipay.pay(bean.data.od, true);
                    //String result = alipay.pay(od, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } else if (!StringUtils.isEmpty(bean.data.tn)) {
            UPPayAssistEx.startPay(mContext, null, null, bean.data.tn, mMode);
        } else {
            if (!api.isWXAppInstalled()) {
                ToastUtils.showShort(mContext.getString(R.string.wx_no_install_tip));
                return;
            }
            PayReq payReq = new PayReq();
            payReq.appId = bean.data.appid;
            payReq.partnerId = bean.data.partnerid;
            payReq.prepayId = bean.data.prepayid;
            payReq.nonceStr = bean.data.noncestr;
            payReq.timeStamp = bean.data.timestamp;
            payReq.packageValue = bean.data.packageX;
            payReq.sign = bean.data.sign;
            api.sendReq(payReq);

        }
    }

    /**
     * 支付结果
     *
     * @param code
     */
    public abstract void payResult(int code);
}
