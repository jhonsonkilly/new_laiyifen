package com.ody.p2p.pay.payMode.payOnline;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/20.
 */
public class PayOnlinePresenterImpl implements PayOnlinePresenter {

    private PayOnlineView payOnlineView;

    public PayOnlinePresenterImpl(PayOnlineView payOnlineView) {
        this.payOnlineView = payOnlineView;
    }

    @Override
    public void getPayList(String orderCode) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(orderCode)) {
            map.put("orderCode", orderCode);
        }
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
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
            public void onResponse(PayTypeListBean response) {
                if (response != null) {
                    payOnlineView.setPayList(response);
                }
            }
        });
    }

    @Override
    public void getPayList(String orderCode, String orderType) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(orderCode)) {
            map.put("orderCode", orderCode);
        }
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        if ("1".equals(orderType)) {
            map.put("businessType","2");
        }
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
            public void onResponse(PayTypeListBean response) {
                if (response != null) {
                    payOnlineView.setPayList(response);
                }
            }
        });
    }

    @Override
    public void getPayInfoByNum(String orderId, String orderMoney, String userId, String paymentConfigId, final String orderType) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNo", orderId);
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
                    payOnlineView.startPay(response);
                }
            }
        }, map);
    }

    @Override
    public void getPayInfo(String paymentConfigId, String ordercode, String promotionId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderCode", ordercode);
        map.put("paymentConfigId", paymentConfigId + "");
        map.put("promotionId", promotionId);
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.GET_PAYINFO_PROMOTION, new OkHttpManager.ResultCallback<PrePayInfoBean>() {
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
                    payOnlineView.startPay(response);
                }
            }
        }, map);

    }

    @Override
    public void getPaytime(String orderCode) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("orderCode", orderCode);
        params.put("sceneType", 1 + "");
        params.put("v", 2.0 + "");
        OkHttpManager.getAsyn(Constants.ORDER_INFO, params, new OkHttpManager.ResultCallback<CancelTimeBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                payOnlineView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(CancelTimeBean response) {
                payOnlineView.hideLoading();
                if (response.data != null && response.code.equals("0")) {
                    payOnlineView.countdowntime(response);
                }
            }
        });
    }

    @Override
    public void getWalletSummary() {

        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("isECard", "1");
        map.put("isYCard", "1");
        map.put("isBean", "1");
        map.put("isCoupon", "1");
        map.put("isPoint", "1");
        map.put("platformId", "0");
        OkHttpManager.getAsyn(Constants.MY_WALLET, map, new OkHttpManager.ResultCallback<WalletBean>() {

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
            public void onFinish() {
                super.onFinish();
                WalletBean bean = new WalletBean();
                bean.code = "-1234.515";
                payOnlineView.setWalletMessage(bean);
            }

            @Override
            public void onResponse(WalletBean response) {
                if (response != null && response.code.equals("0")) {
                    payOnlineView.setWalletMessage(response);
                }
            }
        });
    }
    @Override
    public void lyfSupportPayment() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("platformId", "0");
        OkHttpManager.getAsyn(Constants.LYF_SUPPORT_PAYMENT, map, new OkHttpManager.ResultCallback<SupportBean>() {

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
            public void onResponse(SupportBean response) {
                if (response != null && response.code.equals("0")&&response.getData()!=null) {
                    if (response.getData().getCanUseECard()==0&&response.getData().getCanUseUCard()==0) {
                        return;
                    }
                    payOnlineView.lyfSupportPayment(response);
                    getWalletSummary();
                }
            }
        });
    }
}
