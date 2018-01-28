package com.ody.p2p.check.giftcard;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangmeijuan on 16/6/15.
 */
public class GiftCardPresenterImpl implements GiftCardPresenter {

    private GiftCardView giftCardView;
    public GiftCardPresenterImpl(GiftCardView giftCardView){
        this.giftCardView=giftCardView;
    }

    @Override
    public void requestgiftcardlist() {
        giftCardView.showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.GIFT_CARD_LIST, new OkHttpManager.ResultCallback<GiftCardBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                giftCardView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(GiftCardBean response) {
                giftCardView.giftcardlist(response);
            }

        },params);
    }
}
