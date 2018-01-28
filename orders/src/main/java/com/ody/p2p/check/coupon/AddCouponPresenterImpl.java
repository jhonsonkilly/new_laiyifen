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
public class AddCouponPresenterImpl implements AddCouponPresenter {

    private AddCouponView addCouponView;
    public AddCouponPresenterImpl(AddCouponView addCouponView){
        this.addCouponView=addCouponView;
    }

    @Override
    public void addCoupon(String couponCode) {
        addCouponView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("couponCode",couponCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.BIND_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                addCouponView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                addCouponView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                    addCouponView.finishActivity();
                }
            }
        },params);
    }
}
