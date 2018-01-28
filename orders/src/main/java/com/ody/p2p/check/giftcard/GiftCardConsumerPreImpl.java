package com.ody.p2p.check.giftcard;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/10/18.
 */
public class GiftCardConsumerPreImpl implements GiftCardConsumerPresenter {
    private GiftCardConsumerView consumerView;

    public GiftCardConsumerPreImpl(GiftCardConsumerView consumerView){
        this.consumerView=consumerView;
    }
    @Override
    public void consumerDetail(String giftCardId, int pageNo) {
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("pageNo",pageNo+"");
        params.put("pageSize",10+"");
        params.put("giftCardId",giftCardId);
        consumerView.showLoading();
        OkHttpManager.getAsyn(Constants.GIFTCARD_CONSUMER_DETAIL, params, new OkHttpManager.ResultCallback<GiftCardConsumerBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                consumerView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(GiftCardConsumerBean response) {
                if(response!=null&&response.code.equals("0")){
                    consumerView.consumerDetail(response);
                }
            }
        });
    }
}
