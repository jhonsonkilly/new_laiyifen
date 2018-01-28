package com.ody.p2p.main.myhomepager.myWallet.score;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.myhomepager.bean.LyfExchangeBean;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengya on 2017/3/21.
 */

public class LyfExchangeImpl implements LyfExchangePresenter {

    LyfExchangeView mLyfExchangeView;

    public LyfExchangeImpl(LyfExchangeView mLyfExchangeView) {
        this.mLyfExchangeView = mLyfExchangeView;
    }

    @Override
    public void getRule() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        map.put("platformId", "1");

        OkHttpManager.postAsyn(Constants.EXCHANG_RULE, mLyfExchangeView.getNetTAG(), new OkHttpManager.ResultCallback<LyfRuleBean>() {

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(LyfRuleBean response) {
                if (response != null) {
                    mLyfExchangeView.initRule(response);
                }
            }
        }, map);
    }

    @Override
    public void exchange(long i) {

        //兑换积分
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("amount", String.valueOf(i));

        OkHttpManager.postAsyn(Constants.EXCHANG, mLyfExchangeView.getNetTAG(), new OkHttpManager.ResultCallback<LyfExchangeBean>() {

            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
                mLyfExchangeView.toast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(LyfExchangeBean response) {
                if (response != null && response.code.equals("0")) {
                    mLyfExchangeView.exchangeYPeas(response);
                }
            }
        }, map);
    }

}
