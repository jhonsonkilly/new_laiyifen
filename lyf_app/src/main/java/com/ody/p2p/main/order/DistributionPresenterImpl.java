package com.ody.p2p.main.order;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/12/6.
 */

public class DistributionPresenterImpl implements DistruibutionPresenter {

    private Distributionview distributionview;
    public DistributionPresenterImpl(Distributionview distributionview){
        this.distributionview=distributionview;
    }
    @Override
    public void saveDeliveryMode(String merchantId, String deliverycode) {
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("merchantId",merchantId);
        params.put("deliveryModeCodeChecked",deliverycode);
        distributionview.showLoading();
        OkHttpManager.postAsyn(Constants.SAVE_DELIVERY_MODE, new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                distributionview.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    distributionview.finishActivity();
                }
            }
        },params);
    }
}
