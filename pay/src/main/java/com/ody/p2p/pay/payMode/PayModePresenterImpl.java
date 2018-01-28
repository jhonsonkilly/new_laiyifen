package com.ody.p2p.pay.payMode;

import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

/**
 * Created by ody on 2016/6/20.
 */
public class PayModePresenterImpl implements PayModePresenter {

    private PayModeView payModeView;

    public PayModePresenterImpl(PayModeView payModeView){
        this.payModeView = payModeView;
    }

    @Override
    public void getPayMode() {
        OkHttpManager.getAsyn(Constants.GET_PAYWAY, new OkHttpManager.ResultCallback<PayModeBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(PayModeBean response) {
                if (response!=null && response.data!=null && response.data.size()>0){
                    payModeView.setPayMode(response.data);
                }
            }
        });
    }
}
