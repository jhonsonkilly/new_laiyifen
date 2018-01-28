package com.ody.p2p.check.coupon;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/8/22.
 */
public class UseCouponImpl implements UseCouponPresenter {

    private UseCouponView useCouponView;
    public UseCouponImpl(UseCouponView useCouponView){
        this.useCouponView=useCouponView;
    }
    @Override
    public void useCouponlist() {
        useCouponView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.USE_COUPON,params, new OkHttpManager.ResultCallback<UseCouponBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                useCouponView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                useCouponView.onError();
            }

            @Override
            public void onResponse(UseCouponBean response) {
                useCouponView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                    useCouponView.useCouponList(response);
                }
            }
        });
    }

    @Override
    public void saveUseCoupon(String couponId) {
        useCouponView.showLoading();
        Map<String,String> params=new HashMap<>();
        if(TextUtils.isEmpty(couponId)){
            params.put("selected",0+"");
            params.put("couponId","0");
        }else{
            params.put("selected",1+"");
            params.put("couponId",couponId+"");
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.SAVE_COUPON, params,new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                useCouponView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                useCouponView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                useCouponView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                   useCouponView.finishActivity();
                }
            }
        });
    }

    @Override
    public void addCoupon(String couponCode) {
        useCouponView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("couponCode",couponCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.BIND_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                useCouponView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    useCouponlist();
                }
            }
        },params);
    }
}
