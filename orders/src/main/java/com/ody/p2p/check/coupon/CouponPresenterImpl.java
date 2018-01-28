package com.ody.p2p.check.coupon;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public class CouponPresenterImpl implements CouponPresenter {

    private CouponView couponView;
    public CouponPresenterImpl(CouponView couponView){
        this.couponView=couponView;
    }

    @Override
    public void couponlist() {
        couponView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.COUPON_LIST,new OkHttpManager.ResultCallback<CouponBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                couponView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                couponView.onError();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CouponBean response) {
                couponView.hideLoading();
                if(response!=null&response.code.equals("0")){
                    couponView.couponlist(response);
                }else{
                    couponView.onError();
                }
            }
        },params);
    }

    @Override
    public void couponCount() {
        couponView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.COUPON_COUNT, new OkHttpManager.ResultCallback<CouponCountBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                couponView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CouponCountBean response) {
                couponView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                    couponView.couponCount(response);
                }
            }
        },params);
    }

}
