package com.ody.p2p.main.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LeisurelyPointBean;
import com.ody.p2p.main.views.PayForLeisurelyPopupWindow;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.payOnline.PayResult;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.pay.payMode.payOnline.PrePayInfoBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umaman.laiyifen.wxapi.PayEvent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 悠点卡充值 界面
 * Created by caishengya on 2017/4/19.
 */

public class PayForLeisurelyActivity extends BaseActivity implements View.OnClickListener {

    private String orderId;
    private String orderType;

    private ImageView mImageViewBack;
    private static final int SDK_PAY_FLAG = 1;
    private String mMode = "00";
    private String mPayType;
    private EditText mEditTextMoney;//充值金额
    private TextView mTextViewBalance;//悠点卡余额
    private PayForLeisurelyPopupWindow mPayForLeisurelyPopupWindow;

    private TextView mTextView;
    private IWXAPI api;
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
                        ToastUtils.showToast(getString(com.ody.p2p.pay.R.string.pay_succeed));
//                        RecordHelper.getInstance().payOrder(Long.valueOf(orderId),Long.valueOf(orderMoney),Long.valueOf(deliveryFee),
//                                payMethod,"PayModeActivity","PayOnlineActivity","null","PayOnlineActivity");
                        payResult(0);
//                        finish();
                    } else {
                        payResult(1);
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToast(getString(com.ody.p2p.pay.R.string.pay_result_confirmed));
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToast(getString(com.ody.p2p.pay.R.string.pay_fail));
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private String userId;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_for_leisurely;
    }

    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        orderType = getIntent().getStringExtra("orderType");
        userId = getIntent().getStringExtra(Constants.USER_ID);
        mImageViewBack = (ImageView) findViewById(R.id.iv_back);
        mImageViewBack.setOnClickListener(this);
        mTextViewBalance = (TextView) findViewById(R.id.activity_pay_for_leisurely_balance);
        mEditTextMoney = (EditText) findViewById(R.id.activity_pay_for_leisurely_pay_money);
        mTextView = (TextView) findViewById(R.id.activity_pay_for_leisurely_recharge);
        mTextView.setOnClickListener(this);
        mEditTextMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_theme_solid_bg));
                } else {
                    mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_theme_solid_bg_color_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable edt) {
                //mTODO:meiyizhi 充值金额最多输入两位小数
                String temp = edt.toString();
                if (temp.startsWith(".")) {
                    edt.delete(0, 1);
                    return;
                }
                int posDot = temp.indexOf(".");
                if (posDot < 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    ToastUtils.showStr("最多两位小数");
                    edt.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        String wxAppID = "wxfac2b15cf7763717";
        api = WXAPIFactory.createWXAPI(this, wxAppID);
        api.registerApp(wxAppID);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void resume() {
        getLeisurePointCount();
    }

    @Override
    public void destroy() {

    }

    public void getPayList(String orderCode) {
        Map<String, String> map = new HashMap<>();
//        if (!TextUtils.isEmpty(orderCode)) {
//            map.put("orderCode", orderCode);
//        }
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("businessType", "2");
        OkHttpManager.getAsyn(Constants.GET_PAYTYPE, map, new OkHttpManager.ResultCallback<PayTypeListBean>() {
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
            public void onResponse(PayTypeListBean bean) {
                if (bean != null) {
                    if (bean.data != null) {
                        if (bean.data.payGatewayList != null && bean.data.payGatewayList.size() > 0) {
                            if (bean.data.commonPayGatewayList == null) {
                                bean.data.commonPayGatewayList = new ArrayList<PayTypeListBean.PayType>();
                            }
                            bean.data.commonPayGatewayList.addAll(bean.data.payGatewayList);
                        }
                        if (bean.data.commonPayGatewayList != null && bean.data.commonPayGatewayList.size() > 0) {
                            if (mPayForLeisurelyPopupWindow != null) {
                                mPayForLeisurelyPopupWindow.dismiss();
                            }
                            mPayForLeisurelyPopupWindow = new PayForLeisurelyPopupWindow(PayForLeisurelyActivity.this, bean, "1", mEditTextMoney.getText().toString().trim(), userId);
                            setPopupWindowBackgroundBlack(mPayForLeisurelyPopupWindow);
                            mPayForLeisurelyPopupWindow.setAnimationStyle(R.style.popWindow_animation);
                            mPayForLeisurelyPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            this.finish();
        } else if (v.getId() == R.id.activity_pay_for_leisurely_recharge) {
            if (!TextUtils.isEmpty(mEditTextMoney.getText().toString().trim())) {
                getPayList(orderId);
            } else {
                Toast.makeText(this, "请输入充值金额!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getLeisurePointCount() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("platformId", "0");
        OkHttpManager.getAsyn(Constants.YBALANCE, map, new OkHttpManager.ResultCallback<LeisurelyPointBean>() {
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
            public void onResponse(LeisurelyPointBean response) {
                if (response != null) {
                    mTextViewBalance.setText(String.valueOf(response.data.yCardBalance));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        msg = "充值成功!";
                        payResult(0);
                    } else {
                        msg = "充值失败!";
                        payResult(1);
                    }
                } catch (JSONException e) {
                    msg = "充值失败!";
                    payResult(1);
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "充值成功!";
                payResult(0);
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "充值失败!";
            payResult(1);
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = getString(com.ody.p2p.pay.R.string.user_cancel_pay);
            payResult(1);
        }

        ToastUtils.failToast(msg);
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }

    public void startPay(final PrePayInfoBean bean) {
        if (bean.data != null && bean.data.paymentMessage != null) {
            if (!StringUtils.isEmpty(bean.data.paymentMessage.od)) {
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(getContext());
                        // 调用支付接口，获取支付结果
//                        String od = "partner=\\\"2088021480610734\\\"&seller_id=\\\"zfb@odianyun.com.cn\\\"&out_trade_no=\\\"201607041827391217053311\\\"&subject=\\\"201607040001\\\"&body=\\\"201607040001\\\"&total_fee=\\\"0.01\\\"&notify_url=\\\"http://180.167.73.34:13125/opay-web/updateOrderFromAli.do\\\"&service=\\\"mobile.securitypay.pay\\\"&payment_type=\\\"1\\\"&_input_charset=\\\"utf-8\\\"&it_b_pay=\\\"30m\\\"&sign=\\\"csUZg6eIIxnZKiG69NIbGkAh5Nj3WXyJV1o5ifaZfI4xdfwoX%2Bl6IJ4fVKLtgISw%2FuhJcZ%2FF%2BTbAzwGRFstL%2B96qe9oFD3keZAClStc38W9nNavdooVWW4an6U6ykfpTkIfC9foyO5pXr2r9q%2FSzsytmW0iuKApc%2BsqLoYgAFEc%3D\\\"&sign_type=\\\"RSA\\\"";
                        String result = alipay.pay(bean.data.paymentMessage.od, true);
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
                UPPayAssistEx.startPay(this, null, null, bean.data.tn, mMode);
            } else {
                if (!api.isWXAppInstalled()) {
                    ToastUtils.showShort(getString(com.ody.p2p.pay.R.string.wx_no_install_tip));
                    return;
                }
                PayReq payReq = new PayReq();
                payReq.appId = bean.data.paymentMessage.appid;
                payReq.partnerId = bean.data.paymentMessage.partnerid;
                payReq.prepayId = bean.data.paymentMessage.prepayid;
                payReq.nonceStr = bean.data.paymentMessage.noncestr;
                payReq.timeStamp = bean.data.paymentMessage.timestamp;
                payReq.packageValue = bean.data.paymentMessage.packageX;
                payReq.sign = bean.data.paymentMessage.sign;
                api.sendReq(payReq);
            }
        }

        if (bean.data != null && bean.data.paymentMessage == null) {
            if (!StringUtils.isEmpty(bean.data.od)) {
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(getContext());
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
                UPPayAssistEx.startPay(this, null, null, bean.data.tn, mMode);
            } else {
                if (!api.isWXAppInstalled()) {
                    ToastUtils.showShort(getString(com.ody.p2p.pay.R.string.wx_no_install_tip));
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
    }

    @Subscribe
    public void onEvent(PayEvent event) {
        if (event != null) {
            payResult(event.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    void payResult(int code) {
        if (code == 0) {
            ToastUtils.showShort("充值成功");
        } else if (code == 1) {
            ToastUtils.showShort("充值失败");
        }
        finish();
    }
}
