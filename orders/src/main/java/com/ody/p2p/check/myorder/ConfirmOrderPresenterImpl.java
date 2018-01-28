package com.ody.p2p.check.myorder;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.ErrorBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.NetworkUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tangmeijuan on 16/6/13.
 */
public class ConfirmOrderPresenterImpl implements ConfirmOrderPresenter {

    private ConfirmOrderView confirmOrderView;

    public ConfirmOrderPresenterImpl(ConfirmOrderView confirmOrderView) {
        this.confirmOrderView = confirmOrderView;
    }

    @Override
    public void initOrder(int ignoreChange, String delMpIds) {
        if (!NetworkUtils.getInstance().isAvailable()) {
            confirmOrderView.onNetworkErr();
            return;
        }
        confirmOrderView.showLoading();
        Map<String, String> map = new HashMap<>();
        if (ignoreChange == 1) {
            map.put("ignoreChange", ignoreChange + "");
        }
        if (!TextUtils.isEmpty(delMpIds)) {
            map.put("delMpIds", delMpIds);
        }
        map.put("receiverId", OdyApplication.getValueByKey(Constants.ADDRESS_ID, ""));
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        map.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        OkHttpManager.postAsyn(Constants.INIT_ORDER, new OkHttpManager.ResultCallback<ConfirmOrderBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                confirmOrderView.onErr();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                confirmOrderView.onErr();
            }

            @Override
            public void onResponse(ConfirmOrderBean response) {
                if (response != null && response.data != null && response.code.equals("0")) {
                    confirmOrderView.result(response, 1);
                } else if (response != null && response.code.equals("99")) {
                    confirmOrderView.toLogin();
                } else if (response != null && response.code.equals("10200002")) {
                    if (response.data != null && response.data.error != null) {
                        confirmOrderView.showErrorDialog(response.data.error);
                    }
                }
            }
        }, map);


    }


    @Override
    public void initOldOrder(int ignoreChange, String items) {
        confirmOrderView.showLoading();
        Map<String, String> map = new HashMap<>();
        if (ignoreChange == 1) {
            map.put("ignoreChange", ignoreChange + "");
        }
        map.put("v", "1.1");
        map.put("items", items);
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
//        map.put("platformId",0+"");
        OkHttpManager.postAsyn(Constants.INIT_ORDER, new OkHttpManager.ResultCallback<ConfirmOrderBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ConfirmOrderBean response) {
                confirmOrderView.hideLoading();
                if (response != null && response.data != null && response.code.equals("0")) {
                    confirmOrderView.result(response, 1);
                } else if (response != null && response.code.equals("99")) {
                    confirmOrderView.toLogin();
                } else if (response != null && response.code.equals("10200002")) {
                    if (response.data != null && response.data.error != null) {
                        confirmOrderView.showErrorDialog(response.data.error);
                    }
                }
            }
        }, map);


    }

    @Override
    public void submitorder() {
        confirmOrderView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SUBMIT_ORDER, new OkHttpManager.ResultCallback<SubmitOrderBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if (code.equals("10200127")) {
                    confirmOrderView.showMoneyExceedDialog();
                } else {
                    ToastUtils.showToast(msg);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(SubmitOrderBean response) {
                confirmOrderView.hideLoading();
                if (response == null) {
                    return;
                }
                if (response.code.equals("0") && response.data != null) {
                    confirmOrderView.dealOrder(response);
                }
            }
        }, params);
    }

    @Override
    public void quickpurchase(String mpid, String num, String merchantId, int ignoreChange) {
        confirmOrderView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("num", num + "");
        params.put("mpId", mpid + "");
        params.put("merchantId", merchantId);
        params.put("ignoreChange", ignoreChange + "");
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.QUICK_PURCHASE, new OkHttpManager.ResultCallback<ConfirmOrderBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if (code.equals("001001000")) {
                    if (!TextUtils.isEmpty(msg)) {
                        confirmOrderView.showOutNumberDialog(msg);
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ConfirmOrderBean response) {
                confirmOrderView.hideLoading();
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    confirmOrderView.result(response, 1);
                }
            }
        }, params);
    }

    @Override
    public void showOrder() {
        confirmOrderView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SHOW_ORDER, new OkHttpManager.ResultCallback<ConfirmOrderBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ConfirmOrderBean response) {
                confirmOrderView.hideLoading();
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    confirmOrderView.result(response, 0);
                }
            }
        }, params);
    }

    public void showNewOrder() {
        confirmOrderView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SHOW_ORDER, new OkHttpManager.ResultCallback<ConfirmOrderBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                confirmOrderView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ConfirmOrderBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    confirmOrderView.result(response, 0);
                }
            }
        }, params);
    }

    @Override
    public void saveAddress(final String addressId) {
        Map<String, String> params = new HashMap<>();
        params.put("receiverId", addressId + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_ADDRESS, new OkHttpManager.ResultCallback<ErrorBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ErrorBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    confirmOrderView.saveAddress();
                    OdyApplication.putValueByKey(Constants.ADDRESS_ID, addressId);
                } else if (response != null && response.code.equals("10200002")) {
                    OdyApplication.putValueByKey(Constants.ADDRESS_ID, addressId);
                    confirmOrderView.saveAddress();
//                    ConfirmOrderPresenterImpl
//                    if(response.data!=null&&response.data.error!=null){
//                        confirmOrderView.showErrorDialog(response.data.error);
//                    }
                }
            }
        }, params);
    }

    @Override
    public void saveBrokerage(String usageAmount) {
        Map<String, String> params = new HashMap<>();
        params.put("usageAmount", usageAmount + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_BROKERAGE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    showNewOrder();
                } else {
                    ToastUtils.showToast(response.message);
                }
            }
        }, params);
    }

    @Override
    public void savePoints(int points) {
        Map<String, String> params = new HashMap<>();
        params.put("points", points + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_POINTS, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                confirmOrderView.savePointsFail();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    showOrder();
                }
            }
        }, params);
    }

    @Override
    public void savePayment(long paymentId) {
        Map<String, String> params = new HashMap<>();
        params.put("paymentId", paymentId + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_PAYMENT, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    showOrder();
                }
            }
        }, params);
    }

    @Override
    public void saveCoupon(String couponId) {
        Map<String, String> params = new HashMap<>();
        params.put("couponId", couponId);
        params.put("selected", 1 + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response == null) {
                    return;
                }
                if (response.code.equals("0")) {
                    showOrder();
                }
            }
        }, params);
    }

//    @Override
//    public void saveRemark(String remark, String id) {
//        Map<String,String> params=new HashMap<>();
//        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
//        params.put("remark",remark);
//        params.put("id",id);
//        OkHttpManager.postAsyn(Constants.SAVE_REMARK ,new OkHttpManager.ResultCallback<BaseRequestBean>() {
//            @Override
//            public void onFinish() {
//                super.onFinish();
//            }
//            @Override
//            public void onError(Request request, Exception e) {
//            }
//
//            @Override
//            public void onResponse(BaseRequestBean response) {
//            }
//        },params);
//    }

    @Override
    public void getAdvertisement() {

        RetrofitFactory.getAd(AdPageCode.APP_SETTLEMENT_PAGE, "notice_settle")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (adData != null && adData.notice_settle != null && adData.notice_settle.size() > 0) {
                            confirmOrderView.announcementList(adData);
                        }
                    }
                }));
    }

    @Override
    public void saveEdian(int selected) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("selected", selected + "");
        OkHttpManager.postAsyn(Constants.SAVE_EDIAN, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    showOrder();
                }
            }
        }, params);
    }

    @Override
    public void saveUdian(int selected) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("selected", selected + "");
        OkHttpManager.postAsyn(Constants.SAVE_UDIAN, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    showOrder();
                }
            }
        }, params);
    }
}
