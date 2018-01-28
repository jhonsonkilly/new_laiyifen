package com.ody.p2p.check.orderoinfo;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;
import com.ody.p2p.check.aftersale.Bean.ApplyReturnResultBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/20.
 */
public class OrderInfoImpr implements OrderInfoPressenter {


    private OrderInfoView mView;
    private String ORDER_INFO=Constants.ORDER_INFO;

    public OrderInfoImpr(OrderInfoView mView) {
        this.mView = mView;
    }

    public void setOrderinfoUrl(String url){
        this.ORDER_INFO=url;
    }

    @Override
    public void getOrderInfo(String orderCode) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("v",2.0+"");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(ORDER_INFO, new OkHttpManager.ResultCallback<OrderInfoBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(OrderInfoBean response) {
                mView.hideLoading();
                if (null != response && null != response.data) {
                    mView.initOrderInfo(response.data);
                }
            }
        }, params);
    }

    @Override
    public void cancleOrder(String orderCode) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.CANCEL_ORDER, new OkHttpManager.ResultCallback<OrderInfoBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
//                ToastUtils.showToast("onError");
            }

            @Override
            public void onResponse(OrderInfoBean response) {
                mView.cancleOrder();
            }
        }, params);
    }

    @Override
    public void confrimReceive(final String orderCode) {
        mView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.CONFIRM_RECEIVE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
//                ToastUtils.showToast("onError");

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.hideLoading();
                if(response.code.equals("0")){
                    getOrderInfo(orderCode);
                }
            }
        },params);
    }

//    @Override
//    public void isAftersale(String orderCode) {
//        mView.showLoading();
//        Map<String,String> params=new HashMap<>();
//        params.put("orderCode",orderCode);
//        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
//        OkHttpManager.postAsyn(Constants.IS_AFTER_SALE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                mView.hideLoading();
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(BaseRequestBean response) {
//
//            }
//
//            @Override
//            public void onResponse(String json) {
//                super.onResponse(json);
//                mView.hideLoading();
//                if(!TextUtils.isEmpty(json)){
//                    mView.isAftersale(json);
//                }
//            }
//        },params);
//    }

    @Override
    public void afterSaleReasonlist() {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("afterSaleType", 1 + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.AFTERSALE_CAUSE_LIST, params, new OkHttpManager.ResultCallback<ApplyAfterSaleCauseListBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ApplyAfterSaleCauseListBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData() && null != response.getData().getOrderAfterSalesCauseVOs()) {
                    mView.aftersaleReasonList(response.getData().getOrderAfterSalesCauseVOs());
                }
            }
        });
    }

    @Override
    public void deleteOrder(String orderCode) {
        mView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.DELETE_ORDER, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                    mView.deleteOrder();
                }
            }

        },params);
    }

    @Override
    public void applyRefund(final String orderCode, String refundReasonId) {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("refundReasonId",refundReasonId);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.RETURN_REFUND, new OkHttpManager.ResultCallback<ApplyReturnResultBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ApplyReturnResultBean response) {
                if(response!=null&&response.code.equals("0")){
                    getOrderInfo(orderCode);
                }
            }
        },params);
    }


}
