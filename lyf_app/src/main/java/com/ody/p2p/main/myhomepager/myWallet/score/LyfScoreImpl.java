package com.ody.p2p.main.myhomepager.myWallet.score;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreDetailsBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 来伊份 积分
 * <p>
 * Created by caishengya on 2017/3/20.
 */

public class LyfScoreImpl implements LyfScorePresenter {

    private LyfScoreView mLyfScoreView;
    public String pageType = "1";//查询类型”1” 来伊份积分、”2” 伊豆（欧电云积分）

    public LyfScoreImpl(LyfScoreView mLyfScoreView) {
        this.mLyfScoreView = mLyfScoreView;
    }

    @Override
    public void getLyfScore() {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("accountType", pageType);
        map.put("companyId", "30");

        OkHttpManager.postAsyn(Constants.LYF_ACCOUNT, mLyfScoreView.getNetTAG(), new OkHttpManager.ResultCallback<LyfScoreBean>() {

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(LyfScoreBean response) {
                if (response != null) {
                    mLyfScoreView.initScore(response);
                }
            }
        }, map);
    }

    @Override
    public void getList(String pageNo, String pointStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        map.put("companyId", "30");
        map.put("pageSize", "10");
        map.put("pageNo", pageNo);
        map.put("pointStatus", pointStatus);
        map.put("accountType", pageType);

        OkHttpManager.postAsyn(Constants.LYF_ACCOUNT_DETAILS_LIST, mLyfScoreView.getNetTAG(), new OkHttpManager.ResultCallback<LyfScoreDetailsBean>() {

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(LyfScoreDetailsBean response) {
                if (response != null) {
                    mLyfScoreView.initListDetails(response);
                }
            }
        }, map);
    }

    @Override
    public void getExchangeRule() {

        Map<String, String> map = new HashMap<>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        map.put("platformId", "1");

        OkHttpManager.postAsyn(Constants.EXCHANG_RULE, mLyfScoreView.getNetTAG(), new OkHttpManager.ResultCallback<LyfRuleBean>() {

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(LyfRuleBean response) {
                if (response != null) {
                    mLyfScoreView.initRule(response);
                }
            }
        }, map);
    }
}
