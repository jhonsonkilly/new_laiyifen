package com.ody.p2p.RefoundInfo;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/7/1.
 */
public class RefoundInfoImpr implements RefoundInfoPressenter {
    RefoundInfoView mView;

    public RefoundInfoImpr(RefoundInfoView mView) {
        this.mView = mView;
    }


    @Override
    public void getOrderInfo(String orderAfterSalesId,String returnCode) {
        Map<String, String> params = new HashMap<>();
        if(!TextUtils.isEmpty(orderAfterSalesId)){
            params.put("orderAfterSalesId", orderAfterSalesId);
        }
        if(!TextUtils.isEmpty(returnCode)){
            params.put("returnCode",returnCode);
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.AFTERSALEDETAILS, params, new OkHttpManager.ResultCallback<AfterSaleDetailBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(AfterSaleDetailBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData()) {
                    mView.getData(response.getData());
                }
            }
        });
    }

    @Override
    public void cancelReturnProduct(String returnCode) {
        Map<String, String> params = new HashMap<>();
        params.put("returnCode", returnCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.CANCEL_RETURNP_RODUCT, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.hideLoading();
                if (null != response && response.code.equals("0")) {
                    mView.cancel();
                }
            }
        }, params);
    }

    @Override
    public void courierNumber(String courierNumber, String logisticsCompany, String orderAfterSalesId) {
        Map<String, String> params = new HashMap<>();
        params.put("courierNumber", courierNumber);
        params.put("orderAfterSalesId", orderAfterSalesId);
        params.put("logisticsCompany",logisticsCompany);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.SAVE_COURIERNO, params, new OkHttpManager.ResultCallback<SavaCouriernoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(SavaCouriernoBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData() && response.code.equals("0")) {
                    if (response.getData().getOperationCode().equals("0")) {
                        mView.saveOK();
                    }
                }
            }
        });
    }

    @Override
    public void confirmReceivePro(String returnId) {
        Map<String,String> params=new HashMap<>();
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("returnId",returnId);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.AFTERSALE_CONFIRM_REEIVE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
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
                if(response!=null&&response.code.equals("0")){
                   mView.saveOK();
                }
            }
        },params);
    }

    @Override
    public void isAftersale(String orderCode) {
        mView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.IS_AFTER_SALE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
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

            }

            @Override
            public void onResponse(String json) {
                super.onResponse(json);
                mView.hideLoading();
                if(!TextUtils.isEmpty(json)){
                    mView.isAftersale(json);
                }
            }
        },params);
    }


}
