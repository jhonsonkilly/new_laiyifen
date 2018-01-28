package com.ody.p2p.main.myhomepager.myWallet;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.myhomepager.bean.MyWalletBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caishengya on 2017/2/27.
 */

public class LyfMyWalletImpl implements LyfMyWalletPresenter {

    private LyfMyWalletView lyfMyWalletView;

    public LyfMyWalletImpl(LyfMyWalletView lyfMyWalletView) {
        this.lyfMyWalletView = lyfMyWalletView;
    }

    @Override
    public void getCounts() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("isECard", "1");
        map.put("isYCard", "1");
        map.put("isBean", "1");
        map.put("isCoupon", "1");
        map.put("isPoint", "1");
        map.put("platformId", "0");
        OkHttpManager.getAsyn(Constants.MY_WALLET, map, new OkHttpManager.ResultCallback<MyWalletBean>() {
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
            public void onResponse(MyWalletBean response) {
                if (response != null) {
                    lyfMyWalletView.showCounts(response);
                }
            }
        });
    }
}
