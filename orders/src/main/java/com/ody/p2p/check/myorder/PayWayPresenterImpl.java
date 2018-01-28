package com.ody.p2p.check.myorder;

import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public class PayWayPresenterImpl implements PayWayPresenter{

    private PayWayView payWayView;
    public PayWayPresenterImpl(PayWayView payWayView){
        this.payWayView=payWayView;
    }
    @Override
    public void paywaylist() {

        OkHttpManager.getAsyn("http://p2p.odianyun.com/api/checkout/paywaylist.do", new OkHttpManager.ResultCallback<PayWayBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(PayWayBean response) {

                payWayView.paywaylist(response);
            }
        });
    }
}
