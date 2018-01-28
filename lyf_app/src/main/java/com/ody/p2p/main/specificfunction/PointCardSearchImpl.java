package com.ody.p2p.main.specificfunction;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.main.InitDataBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2017/2/24.
 */
public class PointCardSearchImpl implements PointCardSearchPresenter {

    private PointCardSearchView mView;

    public PointCardSearchImpl(PointCardSearchView mView){
        this.mView = mView;
    }

    @Override
    public void searchCardInfo(String cardCode, String cardPwd) {
        Map<String, String> params = new HashMap<>();
        params.put("cardCode", cardCode);
        params.put("cardPwd", cardPwd);
        OkHttpManager.getAsyn(Constants.POINT_CARD_SEARCH , params ,new OkHttpManager.ResultCallback<PointCardBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(PointCardBean response) {
                if (response != null) {
                    mView.initData(response);
                }
            }
        });
    }
}
