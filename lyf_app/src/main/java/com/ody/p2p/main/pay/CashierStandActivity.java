package com.ody.p2p.main.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bestpay.app.PaymentTask;
import com.ccb.ccbnetpay.CcbNetPay;
import com.ccb.ccbnetpay.dialog.CCBProgressDialog;
import com.ccb.ccbnetpay.util.SyncMessageReminder;
import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.views.AddYCardPopupWindow;
import com.ody.p2p.main.views.TextViewPopupWindow;
import com.ody.p2p.pay.payMode.payOnline.CancelTimeBean;
import com.ody.p2p.pay.payMode.payOnline.PayOnlinePresenter;
import com.ody.p2p.pay.payMode.payOnline.PayOnlinePresenterImpl;
import com.ody.p2p.pay.payMode.payOnline.PayOnlineView;
import com.ody.p2p.pay.payMode.payOnline.PayResult;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.pay.payMode.payOnline.PrePayInfoBean;
import com.ody.p2p.pay.payMode.payOnline.SupportBean;
import com.ody.p2p.pay.payMode.payOnline.WalletBean;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.MyListView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.webactivity.WebActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umaman.laiyifen.wxapi.PayEvent;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.router.router.ActivityRouter;

/**
 * Created by ${tang} on 2016/11/28.
 */

public class CashierStandActivity extends BaseActivity implements View.OnClickListener, PayOnlineView, SyncMessageReminder.OnSyncMessageReceivedListener {

    private ImageView iv_back;
    private TextView tv_time;
    private TextView tv_ordercode;
    private TextView tv_money;
    private TextView tv_yidian;
    private TextView tv_pay_money;

    //伊点卡支付
    private RelativeLayout rl_yidian;
    private TextView tv_card_name;
    private TextView tv_yidian_money;

    //常用支付方式
    private MyListView lv_common_pay;

    private MyListView lv_payway;

    private LinearLayout ll_orderinfo;

    private String userId;
    private String orderId;
    private String orderMoney;
    private String deliveryFee;
    private String orderType;
    private int isfromNative;

    private LYFPayListAdapter payGatewayAdapter;
    private LYFPayListAdapter commonGatewayAdapter;
    private PayOnlinePresenter payOnlinePresenter;

    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    private String mMode = "00";
    private String yCardBalance = "";
    private String eCardBalance = "";

    private CustomDialog customDialog;

    private CountDownTimer timer;
    //建行支付
    private SyncMessageReminder.OnSyncMessageReceivedListener listener = null;
    //伊点卡和悠点卡支付 相关组件
    private RelativeLayout mRelativeLayoutLeisurely; //悠点卡所在布局
    private TextView mTextViewLeisurelyBalance;//悠点卡余额
    private TextView mTextViewLeisurelyUse;//去使用悠点卡

    private RelativeLayout mRelativeLayoutYCard; //伊点卡所在布局
    private TextView mTextViewYCardBalance;//伊点卡余额
    private TextView mTextViewYCardUse;//去使用伊点卡
    private AddYCardPopupWindow mAddYCardPopupWindow;

    private boolean mHaveLeisurely;
    private boolean mHaveYCard;

    private TextViewPopupWindow mTextViewPopupWindow;
    private View mLine;

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
    private String mPayType;
    private int mGateWay;
    private String mPayWay;
    private String versionNo;
    private boolean isAddYcardBack = false;
    private boolean toTopUp = false;
    private int mSupportCanUseUCard = 1;//默认显示悠点卡
    private int mSupportCanUseECard = 1;//默认显示悠点卡
    private String h2BackUrl;


    @Override
    public void initPresenter() {
        payOnlinePresenter = new PayOnlinePresenterImpl(this);
        payOnlinePresenter.getPayList(orderId, orderType);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_cashier;
    }

    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);
        this.listener = this;
        String keyUrl = getIntent().getStringExtra(ActivityRouter.KEY_URL);
        if (!StringUtils.isEmpty(keyUrl)) {
            orderId = JumpUtils.getValueFromUrl(keyUrl, "orderCode");
            orderMoney = JumpUtils.getValueFromUrl(keyUrl, "amount");
            orderType = JumpUtils.getValueFromUrl(keyUrl, "orderType");
            h2BackUrl = JumpUtils.getValueFromUrl(keyUrl, "url");
        }


        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_ordercode = (TextView) findViewById(R.id.tv_ordercode);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_yidian = (TextView) findViewById(R.id.tv_yidian);
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        tv_card_name = (TextView) findViewById(R.id.tv_card_name);
        tv_yidian_money = (TextView) findViewById(R.id.tv_yidian_money);
        lv_common_pay = (MyListView) findViewById(R.id.lv_common_pay);
        rl_yidian = (RelativeLayout) findViewById(R.id.rl_yidian);
        rl_yidian.setOnClickListener(this);
        lv_payway = (MyListView) findViewById(R.id.lv_payway);
        iv_back.setOnClickListener(this);
        ll_orderinfo = (LinearLayout) findViewById(R.id.ll_orderinfo);

        payGatewayAdapter = new LYFPayListAdapter(this);
        commonGatewayAdapter = new LYFPayListAdapter(this);
        lv_payway.setAdapter(payGatewayAdapter);
        lv_common_pay.setAdapter(commonGatewayAdapter);

        isfromNative = getIntent().getIntExtra("isfromNative", 0);
        userId = getIntent().getStringExtra(Constants.USER_ID);
        if (StringUtils.isEmpty(orderId)) {
            orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        }
        if (StringUtils.isEmpty(orderMoney)) {
            orderMoney = getIntent().getStringExtra(Constants.ORDER_MONEY);
        }

        if (TextUtils.isEmpty(orderType)) {
            orderType = getIntent().getStringExtra("orderType");
        }
        if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {//orderType=1表示充值支付
            ll_orderinfo.setVisibility(View.GONE);
        } else {
            ll_orderinfo.setVisibility(View.VISIBLE);
        }
        deliveryFee = getIntent().getStringExtra(Constants.ORDER_DELIVERYfEE);

        customDialog = new CustomDialog(this, "确定放弃支付", 2);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                if (isfromNative == 1) {
                    String url = OdyApplication.H5URL + "/my/my-order.html";
                    JumpUtils.ToWebActivity(url, WebActivity.NO_TITLE, -1, "");
                }
                finish();
            }
        });

        mRelativeLayoutLeisurely = (RelativeLayout) findViewById(R.id.layout_cashier_linearlayout_leisurely);
        mTextViewLeisurelyBalance = (TextView) findViewById(R.id.leisurely_balance);
        mTextViewLeisurelyUse = (TextView) findViewById(R.id.leisurely_use);
        mTextViewLeisurelyUse.setOnClickListener(this);
        mRelativeLayoutYCard = (RelativeLayout) findViewById(R.id.layout_cashier_linearlayout_yCard);
        mTextViewYCardBalance = (TextView) findViewById(R.id.yCard_balance);
        mTextViewYCardUse = (TextView) findViewById(R.id.yCard_use);
        mTextViewYCardUse.setOnClickListener(this);
        mLine = findViewById(R.id.layout_cashier_cards_lines);
    }

    @Override
    public void doBusiness(Context mContext) {
        String wxAppID = "wxfac2b15cf7763717";
        api = WXAPIFactory.createWXAPI(this, wxAppID);
        api.registerApp(wxAppID);

        tv_ordercode.setText(orderId);
        tv_money.setText("￥" + orderMoney);
        lv_common_pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPayType = commonGatewayAdapter.getItem(position).promLabel;
                mGateWay = commonGatewayAdapter.getItem(position).paymentGateway;
                mPayWay = commonGatewayAdapter.getItem(position).paymentThirdparty;
                if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                    payOnlinePresenter.getPayInfoByNum(orderId, orderMoney, userId, commonGatewayAdapter.getItem(position).paymentConfigId, orderType);
                } else {
                    payOnlinePresenter.getPayInfo(commonGatewayAdapter.getItem(position).paymentConfigId, orderId, commonGatewayAdapter.getItem(position).promotionId);
                }

            }
        });

        lv_payway.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPayType = payGatewayAdapter.getItem(position).promLabel;
                mGateWay = payGatewayAdapter.getItem(position).paymentGateway;
                mPayWay = payGatewayAdapter.getItem(position).paymentThirdparty;
                if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                    payOnlinePresenter.getPayInfoByNum(orderId, orderMoney, userId, payGatewayAdapter.getItem(position).paymentConfigId, orderType);
                } else {
                    payOnlinePresenter.getPayInfo(payGatewayAdapter.getItem(position).paymentConfigId, orderId, payGatewayAdapter.getItem(position).promotionId);
                }
            }
        });
        if (!StringUtils.isEmpty(orderId)) {
            payOnlinePresenter.getPaytime(orderId);
        }
    }

    @Override
    public void resume() {
        toTopUp = false;
        if (!StringUtils.isEmpty(orderId)) {
            payOnlinePresenter.getPaytime(orderId);
        }
        payOnlinePresenter.lyfSupportPayment();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_yidian) {
//            OrderCommonPopwindow popwindow = new OrderCommonPopwindow(this, 1);
//            popwindow.setListener(new OrderCommonPopwindow.SubmitListener() {
//                @Override
//                public void submit(String cardnum, String psw) {
//
//                }
//            });
//            popwindow.showAtLocation(tv_time, Gravity.CENTER, 0, 0);
        } else if (v.getId() == R.id.iv_back) {
            if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                finish();
            } else {
                customDialog.show();
            }
        } else if (v.getId() == R.id.leisurely_use) {
            if (mHaveLeisurely) {
                //悠点卡 使用
                String cardPayMoney = "0";
                if (Double.valueOf(yCardBalance) >= Double.valueOf(orderMoney)) {
                    cardPayMoney = orderMoney;
                } else {
                    cardPayMoney = yCardBalance;
                }
                useCard(0, cardPayMoney);
            } else {
                //悠点卡 充值
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, orderId);
                bd.putString("orderType", orderType);
                bd.putString(Constants.USER_ID, userId);
                toTopUp = true;
                JumpUtils.ToActivity(JumpUtils.PAY_FOR_LEISURELY, bd);
            }
        } else if (v.getId() == R.id.yCard_use) {
            if (mHaveYCard) {
                //伊点卡 使用
                String cardPayMoney = "0";
                if (Double.valueOf(eCardBalance) >= Double.valueOf(orderMoney)) {
                    cardPayMoney = orderMoney;
                } else {
                    cardPayMoney = eCardBalance;
                }
                useCard(1, cardPayMoney);
            } else {
                //伊点卡 绑定
                if (mAddYCardPopupWindow != null) {
                    mAddYCardPopupWindow.dismiss();
                }
                mAddYCardPopupWindow = new AddYCardPopupWindow(this, orderId, orderType);
                //添加成功刷新伊点卡信息
                mAddYCardPopupWindow.setAddSuccessListener(new AddYCardPopupWindow.AddSuccessListener() {
                    @Override
                    public void addSuccess() {
                        isAddYcardBack = true;
                        payOnlinePresenter.getWalletSummary();
                    }
                });
                setPopupWindowBackgroundBlack(mAddYCardPopupWindow);
                mAddYCardPopupWindow.setAnimationStyle(R.style.popWindow_animation);
                mAddYCardPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        }
    }

    /**
     * 卡券支付
     *
     * @param type
     * @param cardPayMoney
     */
    private void useCard(int type, String cardPayMoney) {
        if (mTextViewPopupWindow != null) {
            mTextViewPopupWindow.dismiss();
            mTextViewPopupWindow = null;
        }
        mTextViewPopupWindow = new TextViewPopupWindow(this, type, cardPayMoney, versionNo, orderId);
        setPopupWindowBackgroundBlack(mTextViewPopupWindow);
        mTextViewPopupWindow.setCardPaySuccessListener(new TextViewPopupWindow.CardPaySuccessListener() {
            @Override
            public void cardPaySuccess(int isPaid) {
                // 	是否已支付,0:未支付 1:已支付
                if (isPaid == 0) {//未支付完
                    ToastUtils.showShort("支付成功！");
                    resume();
                } else {//支付成功
                    payResult(0);
                }
            }

            @Override
            public void cardPayFaild() {
                payResult(1);
            }
        });
        mTextViewPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void setPayList(PayTypeListBean bean) {
        if (bean.data != null) {
            if (bean.data.payGatewayList != null && bean.data.payGatewayList.size() > 0) {
                payGatewayAdapter.addData(bean.data.payGatewayList);
            }
            if (bean.data.commonPayGatewayList != null && bean.data.commonPayGatewayList.size() > 0) {
                lv_common_pay.setVisibility(View.VISIBLE);
                commonGatewayAdapter.addData(bean.data.commonPayGatewayList);
            }
        }
    }

    /**
     * 支付结果
     *
     * @param code
     */
    void payResult(int code) {
        if (code == 0) {
            //mTODOa:meiyizhi 熊文强协定--orderType=2&&返回url不为空，支付成功则跳转到url
//            if ("2".equals(orderType) && !TextUtils.isEmpty(h2BackUrl)) {
//                TaklingDataEventMessage msg = new TaklingDataEventMessage();
//                msg.setAction(TaklingDataEventMessage.ONORDERPAYSUCC);
//                Map<String, String> map = new HashMap<>();
//                String account = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名");
//                map.put("account", account);
//                map.put("orderid", orderId);
//                map.put("amount", orderMoney);
//                map.put("currencyType", "CNY");
//                map.put("payType", mPayType);
//                msg.setExtra(map);
//                EventBus.getDefault().post(msg);
//                JumpUtils.ToWebActivity(this, h2BackUrl);
//                finish();
//                return;
//            }
            if (!StringUtils.isEmpty(h2BackUrl)) {
                JumpUtils.ToWebActivity(h2BackUrl, WebActivity.CONTENT_TITLE, -1, "");
            } else {
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, orderId);
                JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            }
            finish();
            if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                //充值打点
                TaklingDataEventMessage msg = new TaklingDataEventMessage();
                msg.setAction(TaklingDataEventMessage.ONCHARGE);
                Map<String, String> map = new HashMap<>();
                map.put("amount", orderMoney);
                map.put("couponAMT", "0");
                map.put("payStatus", true + "");
                map.put("payType", mPayWay);
                msg.setExtra(map);
                EventBus.getDefault().post(msg);
                finish();
                return;
            }
            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONORDERPAYSUCC);
            Map<String, String> map = new HashMap<>();
            String account = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名");
            map.put("account", account);
            map.put("orderid", orderId);
            map.put("amount", orderMoney);
            map.put("currencyType", "CNY");
            map.put("payStatus", true + "");
            map.put("payType", mPayWay);
            msg.setExtra(map);
            EventBus.getDefault().post(msg);
            Bundle bd = new Bundle();
            bd.putString(Constants.ORDER_ID, orderId);
            bd.putString("orderType", orderType);
            bd.putString("url", h2BackUrl);
            //拼团流程中，结算页是h5的，当跳到app支付时，根据group-detail定义，需要入参instId
//            JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            JumpUtils.ToWebActivity(CashierStandActivity.this, h2BackUrl);
            finish();
        } else if (code == 1) {

            if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                //充值打点
                TaklingDataEventMessage msg = new TaklingDataEventMessage();
                msg.setAction(TaklingDataEventMessage.ONCHARGE);
                Map<String, String> map = new HashMap<>();
                map.put("amount", orderMoney);
                map.put("couponAMT", "0");
                map.put("payStatus", false + "");
                map.put("payType", mPayWay);
                msg.setExtra(map);
                EventBus.getDefault().post(msg);
                finish();
                return;
            }
            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONORDERPAYSUCC);
            Map<String, String> map = new HashMap<>();
            String account = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名");
            map.put("account", account);
            map.put("orderid", orderId);
            map.put("amount", orderMoney);
            map.put("currencyType", "CNY");
            map.put("payType", mPayWay);
            map.put("payStatus", false + "");
            msg.setExtra(map);
            EventBus.getDefault().post(msg);


            Bundle bd = new Bundle();
            bd.putString(Constants.USER_ID, userId);
            bd.putString(Constants.ORDER_MONEY, orderMoney);
            bd.putString(Constants.ORDER_ID, orderId);
            bd.putString("orderType", orderType);
            JumpUtils.ToActivity(JumpUtils.PAYFAIL, bd);
            finish();
        }
    }

    @Override
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
            } else if (!StringUtils.isEmpty(bean.data.paymentMessage.PayUrl)) {
                //招行支付，打开新的app
                Gson gson = new Gson();
                String json = gson.toJson(bean.data.paymentMessage, PrePayInfoBean.PaymentMessage.class);

                Bundle bd = new Bundle();
                bd.putString(Constants.PAY_LIST, json);
                bd.putString(Constants.ORDER_ID, orderId);
                JumpUtils.ToActivity(JumpUtils.YWTPAY, bd);
                finish();


            } else if (!StringUtils.isEmpty(bean.data.paymentMessage.orderUrl)) {

                if (mGateWay == 14) {
                    //翼支付
                    PaymentTask paymentTask = new PaymentTask(this);
                    paymentTask.pay(bean.data.paymentMessage.orderUrl);

                } else {
                    final CCBProgressDialog dialog = new CCBProgressDialog(this);
                    if (!dialog.isShowing())
                        dialog.showDialog();
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            //商户参数串

                            // 用户下单后，选择建行支付时，将调用如下方法跳转建行支付页面
                            CcbNetPay ccb = new CcbNetPay(CashierStandActivity.this, dialog);

                            ccb.doStartAppOrH5(bean.data.paymentMessage.orderUrl);
                            Log.i("CashierStandActivity", bean.data.paymentMessage.orderUrl);

                            OdyApplication.putString(Constants.ORDER_ID, orderId);
                            finish();

                            //ccb.doStartH5(params);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }


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

    @Override
    public void countdowntime(CancelTimeBean cancelTimeBean) {
        if (cancelTimeBean.data.cancelTime <= 0) {
            return;
        }
        versionNo = cancelTimeBean.data.versionNo;
        orderMoney = cancelTimeBean.data.paymentAmount;
        tv_money.setText("￥" + orderMoney);
        if (timer != null) return;

        timer = new CountDownTimer(cancelTimeBean.data.cancelTime * 1000, 1000) {//给他加5秒，防止后台状态来不及刷新

            @Override
            public void onTick(long millisUntilFinished) {
                tv_time.setText("剩余支付时间" + DateTimeUtils.formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                tv_time.setText("订单已超时，被自动取消");
                tv_time.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String url = OdyApplication.H5URL + "/my/order-detail.html?orderCode=" + orderId;
                        EventMessage message = new EventMessage();
                        message.flag = EventMessage.REFRESH_H5;
                        message.h5Url = url;
                        EventBus.getDefault().post(message);
                        JumpUtils.ToWebActivity(url, WebActivity.NO_TITLE, -1, "");
                        finish();
                    }
                }, 1500);
            }
        };
        timer.start();
    }

    @Override
    public void setWalletMessage(WalletBean walletBean) {
        if (walletBean.code.equals("-1234.515")) {
            isAddYcardBack = false;
            return;
        }
        //悠点卡的信息
        yCardBalance = walletBean.data.yCardBalance;
        OdyApplication.putBoolean(Constants.IS_YCARD, !TextUtils.isEmpty(yCardBalance));
        if ((!TextUtils.isEmpty(yCardBalance)) && mSupportCanUseUCard == 1) {
            mTextViewLeisurelyBalance.setText("余额 : " + yCardBalance + "元");
            mRelativeLayoutLeisurely.setVisibility(View.VISIBLE);
            mLine.setVisibility(View.VISIBLE);
            if (Double.valueOf(yCardBalance) > 0) {
                mTextViewLeisurelyUse.setText("使用");
                mHaveLeisurely = true;
            } else {
                mTextViewLeisurelyUse.setText("去充值");
                mHaveLeisurely = false;
            }
        } else {
            mRelativeLayoutLeisurely.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        }

        //伊点卡信息
        eCardBalance = walletBean.data.eCardBalance;
        if ((!TextUtils.isEmpty(walletBean.data.eCardBalance)) && mSupportCanUseECard == 1) {
            mRelativeLayoutYCard.setVisibility(View.VISIBLE);
            mTextViewYCardBalance.setText("余额 : " + walletBean.data.eCardBalance + "元");
            if (Double.valueOf(walletBean.data.eCardBalance) > 0) {
                mTextViewYCardUse.setText("使用");
                mHaveYCard = true;
            } else {
                mTextViewYCardUse.setText("绑定");
                mHaveYCard = false;
            }
        } else {
            mRelativeLayoutYCard.setVisibility(View.GONE);
            mHaveYCard = false;
            mTextViewYCardBalance.setText("余额 : 0");
            mTextViewYCardUse.setText("绑定");
        }
        if (isAddYcardBack) {
            String cardPayMoney = "0";
            if (Double.valueOf(eCardBalance) >= Double.valueOf(orderMoney)) {
                cardPayMoney = orderMoney;
            } else {
                cardPayMoney = eCardBalance;
            }
            useCard(1, cardPayMoney);
        }
    }

    @Override
    public void lyfSupportPayment(SupportBean walletBean) {
        mSupportCanUseUCard = walletBean.getData().getCanUseUCard();
        mSupportCanUseECard = walletBean.getData().getCanUseECard();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/

        if (data == null) {
            return;
        }
        /*String res = data.getStringExtra("resultCode");
        if(res.equals("512")){

        }*/
        if (!data.getStringExtra("result").isEmpty()) {
            if (resultCode == 512) {
                payResult(1);
            } else if (resultCode == -1) {
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, orderId);
                JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
                finish();
            }
        }




        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        try {
            String msg = "";
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
                            msg = getString(com.ody.p2p.pay.R.string.pay_succeed) + "！";
                            payResult(0);
                        } else {
                            msg = getString(com.ody.p2p.pay.R.string.pay_fail) + "！";
                            payResult(1);
                        }
                    } catch (JSONException e) {
                        msg = getString(com.ody.p2p.pay.R.string.pay_fail) + "！";
                        payResult(1);
                    }
                } else {
                    // 未收到签名信息
                    // 建议通过商户后台查询支付结果
                    msg = getString(com.ody.p2p.pay.R.string.pay_succeed) + "！";
                    payResult(0);
                }
            } else if (str.equalsIgnoreCase("fail")) {
                msg = getString(com.ody.p2p.pay.R.string.pay_fail) + "！";
                payResult(1);
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = getString(com.ody.p2p.pay.R.string.user_cancel_pay);
                payResult(1);
            }

            ToastUtils.failToast(msg);
        } catch (Exception e) {

        }


    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }

    @Subscribe
    public void onEvent(PayEvent event) {
        if (toTopUp) {
            return;
        }
        if (event != null) {
            payResult(event.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus

        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!StringUtils.isEmpty(orderType) && orderType.equals("1")) {
                finish();
            } else {
                customDialog.show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void receivedMessage(int what, String message) {
        switch (what) {
            case 1:
                Log.i("---相关异常---", message);
                Toast.makeText(this, message, Toast.LENGTH_SHORT)
                        .show();
                break;
            case 2:
                Log.i("---商户url参数串为空---", "---商户url参数串为空---");
                Toast.makeText(this, "商户参数串不能为空",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
