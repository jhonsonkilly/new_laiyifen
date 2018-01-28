package com.ody.p2p.pay.payMode.payOnline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.ody.ds.desproject.wxapi.PayEvent;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.pay.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.webactivity.WebActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import cn.campusapp.router.router.ActivityRouter;

/**
 * 支付方式---在线支付
 */
public class PayOnlineActivity extends BaseActivity implements View.OnClickListener, PayOnlineView {

    private TextView tv_name;
    private RelativeLayout rl_big_back;
    private RecyclerView rv_pay_list;

    private String userId;
    private String orderId;
    private String orderMoney;
    private String deliveryFee;
    private String payMethod;
    private String mMode = "00";
    private PayOnlinePresenter payOnlinePresenter;

    private IWXAPI api;

    private CustomDialog customDialog;

    private static final int SDK_PAY_FLAG = 1;

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
                        ToastUtils.showToast(getString(R.string.pay_succeed));
//                        RecordHelper.getInstance().payOrder(Long.valueOf(orderId),Long.valueOf(orderMoney),Long.valueOf(deliveryFee),
//                                payMethod,"PayModeActivity","PayOnlineActivity","null","PayOnlineActivity");
                        payResult(0);
                        finish();
                    } else {
                        payResult(1);
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToast(getString(R.string.pay_result_confirmed));
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToast(getString(R.string.pay_fail));
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    @Override
    public void initPresenter() {
        payOnlinePresenter = new PayOnlinePresenterImpl(this);
        payOnlinePresenter.getPayList(orderId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_online;
    }

    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);
        String keyUrl = getIntent().getStringExtra(ActivityRouter.KEY_URL);
        if (!StringUtils.isEmpty(keyUrl)) {
            orderId = JumpUtils.getValueFromUrl(keyUrl, "orderCode");
            orderMoney = JumpUtils.getValueFromUrl(keyUrl, "amount");
        }

        userId = getIntent().getStringExtra(Constants.USER_ID);
        if (StringUtils.isEmpty(orderId)) {
            orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        }
        if (StringUtils.isEmpty(orderMoney)) {
            orderMoney = getIntent().getStringExtra(Constants.ORDER_MONEY);
        }
        deliveryFee = getIntent().getStringExtra(Constants.ORDER_DELIVERYfEE);

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        rv_pay_list = (RecyclerView) view.findViewById(R.id.rv_pay_list);

        rv_pay_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pay_list.setLayoutManager(linearLayoutManager);

        tv_name.setText(getString(R.string.pay_way));

        customDialog=new CustomDialog(this,"确定放弃支付",2);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                if (OdyApplication.SCHEME.equals("ds")) {
                    JumpUtils.ToActivity(JumpUtils.ORDERLIST);
                } else {
                    String url = OdyApplication.H5URL + "/my/my-order.html";
                    JumpUtils.ToWebActivity(url, WebActivity.NO_TITLE, -1, "");
                }
                finish();
            }
        });

        rl_big_back.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        String wxAppID = getString(RUtils.getStringRes(this, RUtils.WX_APP_ID));
        api = WXAPIFactory.createWXAPI(this, wxAppID);
        api.registerApp(wxAppID);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)) {
            customDialog.show();
        }
    }

    long shipPrice;
    String paymentMethod;

    @Override
    public void setPayList(final PayTypeListBean bean) {
        final PayModeListAdapter pmlAdapter = new PayModeListAdapter(this);
        rv_pay_list.setAdapter(pmlAdapter);
        if (bean.data.payGatewayList != null && bean.data.payGatewayList.size() > 0) {
            pmlAdapter.setDatas(bean.data.payGatewayList);
        }
        pmlAdapter.setOnItemClickListener(new PayModeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < pmlAdapter.getItemCount(); i++) {
                    pmlAdapter.getItem(i).isChoose = false;
                }
                pmlAdapter.getItem(position).isChoose = true;
                pmlAdapter.notifyDataSetChanged();
                String money = "";
//                if (pmlAdapter.getItem(position).paymentConfigId == 32) {
//                    BigDecimal bigDecimal = new BigDecimal(orderMoney);
//                    BigDecimal m = bigDecimal.multiply(new BigDecimal(100));
//                    money = m.toString();
//                } else {
                money = new BigDecimal(orderMoney).toString();
//                }
                payMethod = bean.data.payGatewayList.get(position).paymentGateway + "";
                payOnlinePresenter.getPayInfoByNum(orderId, orderMoney, userId, pmlAdapter.getItem(position).paymentConfigId, "");//orderType 普通订单
            }
        });
    }

    @Override
    public void startPay(final PrePayInfoBean bean) {
        if (bean.data != null) {
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
                UPPayAssistEx.startPay(PayOnlineActivity.this, null, null, bean.data.tn, mMode);
            } else {
                if (!api.isWXAppInstalled()) {
                    ToastUtils.showShort(getString(R.string.wx_no_install_tip));
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

    }

    /**
     * 支付结果
     *
     * @param code
     */
    void payResult(int code) {
        if (code == 0) {
            Bundle bd = new Bundle();
            bd.putString(Constants.ORDER_ID, orderId);
            JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            finish();
        } else if (code == 1) {
            Bundle bd = new Bundle();
            bd.putString(Constants.USER_ID, userId);
            bd.putString(Constants.ORDER_MONEY, orderMoney);
            bd.putString(Constants.ORDER_ID, orderId);
            JumpUtils.ToActivity(JumpUtils.PAYFAIL, bd);
            finish();
        }
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
                        msg = getString(R.string.pay_succeed)+"！";
                        payResult(0);
                    } else {
                        msg = getString(R.string.pay_fail)+"！";
                        payResult(1);
                    }
                } catch (JSONException e) {
                    msg = getString(R.string.pay_fail)+"！";
                    payResult(1);
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = getString(R.string.pay_succeed)+"！";
                payResult(0);
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = getString(R.string.pay_fail)+"！";
            payResult(1);
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = getString(R.string.user_cancel_pay);
            payResult(1);
        }

        ToastUtils.failToast(msg);
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }

    @Subscribe
    public void onEvent(PayEvent event) {
        if (event != null){
            payResult(event.getCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            customDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setWalletMessage(WalletBean walletBean) {

    }

    @Override
    public void lyfSupportPayment(SupportBean walletBean) {

    }
}
