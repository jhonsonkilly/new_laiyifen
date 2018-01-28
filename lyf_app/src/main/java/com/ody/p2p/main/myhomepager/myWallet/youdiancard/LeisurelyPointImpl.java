package com.ody.p2p.main.myhomepager.myWallet.youdiancard;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.myhomepager.bean.LeisurelyPointBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengya on 2017/2/28.
 */

public class LeisurelyPointImpl implements LeisurelyPointPresenter {

    private LeisurelyPointView view;

    public LeisurelyPointImpl(LeisurelyPointView view) {
        this.view = view;
    }

    @Override
    public void getLeisurePointCount() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("platformId", "0");
        OkHttpManager.getAsyn(Constants.YBALANCE, map, new OkHttpManager.ResultCallback<LeisurelyPointBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showToast(e.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(LeisurelyPointBean response) {
                if (response != null) {
                    view.initLeisurelyPoint(response);
                }
            }
        });
    }

    @Override
    public void getPayType() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("isOverseas", "0");//1是海购
        map.put("businessType", "2");
        OkHttpManager.getAsyn(Constants.GET_PAYTYPE, map, new OkHttpManager.ResultCallback<PayTypeListBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showToast(e.toString());
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(PayTypeListBean response) {
                if (response != null) {
                    view.initPayType(response);
                }
            }
        });
    }
}
