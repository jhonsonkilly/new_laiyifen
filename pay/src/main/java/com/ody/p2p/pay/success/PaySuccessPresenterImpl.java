package com.ody.p2p.pay.success;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.recmmend.Recommedbean;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/12/12.
 */

public class PaySuccessPresenterImpl implements PaySucessPresenter {
    public PaySuccessView paySuccessView;

    public PaySuccessPresenterImpl(PaySuccessView paySuccessView){
        this.paySuccessView=paySuccessView;
    }

    @Override
    public void payInfo(String orderCode) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("v",2.0+"");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        paySuccessView.showLoading();
        OkHttpManager.getAsyn(Constants.ORDER_INFO, params, new OkHttpManager.ResultCallback<PaySuccessInfoBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                paySuccessView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(PaySuccessInfoBean response) {
                paySuccessView.hideLoading();
                if (null != response && null != response.data) {
                    paySuccessView.payInfo(response);
                }
            }
        });
    }

    @Override
    public void guessYouLike(int pageNo) {
        Map<String,String> params=new HashMap<>();
        params.put("sceneNo",3+"");
        params.put("pageNo",pageNo+"");
        params.put("pageSize",10+"");
        params.put("areaCode",OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT,""));
        paySuccessView.showLoading();
        OkHttpManager.getAsyn(Constants.SHOPCART_RECOMMEND, params, new OkHttpManager.ResultCallback<Recommedbean>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Recommedbean response) {
                if(response!=null&&response.code.equals("0")){
                    paySuccessView.likeList(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                paySuccessView.hideLoading();
            }
        });
    }

    @Override
    public void getAd(String adCode) {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", adCode);
        params.put("platform", "3");
        params.put("pageCode", "APP_PAYMENT_SUCCESS_PAGE");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null) {
                    paySuccessView.initAd(response);
                }
            }
        });
    }
}
