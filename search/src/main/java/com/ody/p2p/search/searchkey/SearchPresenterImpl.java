package com.ody.p2p.search.searchkey;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ody on 2016/5/31.
 */
public class SearchPresenterImpl implements SearchPresenter {

    private SearchView mSearchView;

    public SearchPresenterImpl(SearchView mSearchView) {
        this.mSearchView = mSearchView;
    }

    /**
     * @param keyword 关键字联想的列表
     */
    @Override
    public void getSearchList(String keyword) {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        OkHttpManager.getAsyn(Constants.SEARCH_KEYWORD_LIST, params,new OkHttpManager.ResultCallback<SearchKeywordListBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(SearchKeywordListBean response) {
                if (response != null && response.data != null && response.data.size() > 0) {
                    mSearchView.setSearchResultList(response.data);
                } else {
                    mSearchView.setSearchResultList(new ArrayList<SearchKeywordListBean.Data>());
                }
            }
        });
    }

    @Override
    public void getHotWord() {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", "hotword");//目前这个值肯定是没有的   OdyApplication.getValueByKey("areaCode", "")
        params.put("pageCode", "APP_HOME");//拿几个热词暂时没限制,本来便利是拿5个
        params.put("platform", "3");

        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null && response.data != null && response.data.hotword != null){
                    mSearchView.setHotWord(response.data.hotword);
                }
            }
        });
    }

    /**
     * 刚进入搜索界面时,在搜索框内就会出现默认的搜索词
     */
    @Override
    public void getDefaultWord() {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", "searchword");
        params.put("pageCode", "APP_HOME");
        params.put("platform", "3");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(FuncBean response) {//DolphinBean
                if (response != null && response.data != null && response.data.searchword != null && response.data.searchword.size() > 0) {
                    mSearchView.setDefaultWord(response.data.searchword);
                }
            }
        });
    }

    /**
     * 搜索历史和清除搜索历史 走接口
     */
    @Override
    public void getSearchHistory(int count) {
        Map<String, String> params = new HashMap<>();
        params.put("count", count + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.getAsyn(Constants.SEARCH_HISTORY, params, new OkHttpManager.ResultCallback<SearchHistoryBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                Log.e("test", "SEARCH_HISTORY  onFailed==" + msg);
            }

            @Override
            public void onResponse(SearchHistoryBean response) {
                if (response != null && response.data != null) {
                    mSearchView.setSearchHistory(response.data.searchHistoryList);
                }
            }
        });
    }

    //historyKeywordJson:非必传字段  需要清除的用户搜索词列表，eg：{“historyKeywordList”: [“皮质”,”皮革”,”毛呢”]}
    @Override
    public void cleanSearchHistory(String historyKeywordJson) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        if (!StringUtils.isEmpty(historyKeywordJson)) {
            params.put("historyKeywordJson", historyKeywordJson);
        }
        OkHttpManager.postAsyn(Constants.CLEAN_SEARCH_HISTORY,new OkHttpManager.ResultCallback<ClearSearchHistoryBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                Log.e("test", "CLEAN_SEARCH_HISTORY  onFailed==" + msg);
            }

            @Override
            public void onResponse(ClearSearchHistoryBean response) {
                if (response != null) {
                    ToastUtils.showShort(response.data);
                    mSearchView.clearn();
//                    getSearchHistory(10);
                }
            }
        }, params);
    }

    @Override
    public void traceProduct(int pageNo) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");
        mSearchView.showLoading();
        OkHttpManager.getAsyn(Constants.HISTORY_LIST, params, new OkHttpManager.ResultCallback<HistoryBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSearchView.hideLoading();
            }

            @Override
            public void onResponse(HistoryBean response) {
                if(response!=null&&response.code.equals("0")){
                    if(response.data!=null&&response.data.data!=null&&response.data.data.size()>0){
                        List<HistoryBean.FootStepVO> historylist=new ArrayList<>();
                        for(int i=0;i<response.data.data.size();i++){
                            if(response.data.data.get(i)!=null&&response.data.data.get(i).values!=null&&response.data.data.get(i).values.size()>0){
                                historylist.addAll(response.data.data.get(i).values);
                            }
                        }
                        if(historylist.size()>0){
                            mSearchView.footHistory(historylist,response.data.totalPage);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void getCurrentPrice(String mpIds) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpIds", mpIds);//商品ID
        OkHttpManager.getAsyn(Constants.PRODUCT_CURRENT_PRICE, map , new OkHttpManager.ResultCallback<StockPriceBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(StockPriceBean response) {
                if (response != null && response.data != null){
                    mSearchView.setCurrentPrice(response);
                }
            }
            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
