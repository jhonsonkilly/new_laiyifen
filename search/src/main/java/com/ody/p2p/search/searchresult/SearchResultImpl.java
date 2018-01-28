package com.ody.p2p.search.searchresult;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.utils.StringUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2016/6/17.
 */
public class SearchResultImpl implements SearchResultPresenter {

    private SearchResultView mView;
    private static final int PAGE_SIZE = 20;
    protected int i;

    public SearchResultImpl(SearchResultView mView) {
        this.mView = mView;
    }

    public void getList(String merchantId,  int pageNo){
        //        if (pageNo == 1){
        mView.showLoading();
//        }
        Map<String, String> params = new HashMap<>();
        if(!StringUtils.isEmpty(merchantId)){
            params.put("merchantId", merchantId);
        }
        params.put("pageNo", pageNo + "");
        params.put("pageSize", String.valueOf(PAGE_SIZE));
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));

        OkHttpManager.getAsyn(Constants.GET_SEARCH_LIST, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ResultBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
//                if (pageNo == 1){
                mView.hideLoading();
//                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ResultBean response) {
                Log.e("response", "response: " + response);
                if (response != null) {
                    mView.loadList(response);
                }
            }
        });
    }

    @Override
    public void getList(String key, String sortType, final int pageNo, String shoppingGuideJson, String brandIds, String priceAnger, String navCategoryIds, String promotionIds, String merchantId) {
//        if (pageNo == 1){
        mView.showLoading();
//        }
        Map<String, String> params = new HashMap<>();
        if(!StringUtils.isEmpty(merchantId)){
            params.put("merchantId", merchantId);
        }
        params.put("pageNo", pageNo + "");
        params.put("pageSize", String.valueOf(PAGE_SIZE));
        if (!StringUtils.isEmpty(brandIds)) {
            params.put("brandIds", brandIds);
        } else if (!StringUtils.isEmpty(navCategoryIds)) {
            params.put("navCategoryIds", navCategoryIds);
        } else if (!StringUtils.isEmpty(key)) {
            params.put("keyword", key);
        }if (!StringUtils.isEmpty(promotionIds)){
            params.put("promotionIds", promotionIds);
        }
        if (!StringUtils.isEmpty(sortType)){
            params.put("sortType", sortType);
        }
        if (!StringUtils.isEmpty(shoppingGuideJson)){
            params.put("shoppingGuideJson", shoppingGuideJson);
        }

        if (priceAnger != null && priceAnger.length() > 1) {
            params.put("priceRange", priceAnger);
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        params.put("areaCode",OdyApplication.getValueByKey(Constants.AREA_CODE,""));

        OkHttpManager.getAsyn(Constants.GET_SEARCH_LIST, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ResultBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
//                if (pageNo == 1){
                mView.hideLoading();
//                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ResultBean response) {
                Log.e("response", "response: " + response);
                if (response != null) {
                    mView.initSearchList(response);
                }
            }
        });
    }

    @Override
    public void getList(String key, String sortType, final int pageNo, String shoppingGuideJson, String brandIds, String priceAnger, String navCategoryIds, String promotionIds, String merchantId, String filterType) {
//        if (pageNo == 1){
        mView.showLoading();
//        }
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("filterType", filterType);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", String.valueOf(PAGE_SIZE));
        if (!StringUtils.isEmpty(brandIds)) {
            params.put("brandIds", brandIds);
        } else if (!StringUtils.isEmpty(navCategoryIds)) {
            params.put("navCategoryIds", navCategoryIds);
        } else if (!StringUtils.isEmpty(key)) {
            params.put("keyword", key);
        }if (!StringUtils.isEmpty(promotionIds)){
            params.put("promotionIds", promotionIds);
        }
        if (!StringUtils.isEmpty(sortType)){
            params.put("sortType", sortType);
        }
        if (!StringUtils.isEmpty(shoppingGuideJson)){
            params.put("shoppingGuideJson", shoppingGuideJson);
        }

        if (priceAnger != null && priceAnger.length() > 1) {
            params.put("priceRange", priceAnger);
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        params.put("areaCode",OdyApplication.getValueByKey(Constants.AREA_CODE,""));
        OkHttpManager.getAsyn(Constants.GET_SEARCH_LIST, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ResultBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
//                if (pageNo == 1){
                mView.hideLoading();
//                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ResultBean response) {
                Log.e("response", "response: " + response);
                if (response != null) {
                    mView.initSearchList(response);
                }
            }
        });
    }

    @Override
    public void getList(String key, String sortType, final int pageNo, String shoppingGuideJson, String brandIds, String priceAnger, String navCategoryIds, String promotionIds) {
//        if (pageNo == 1){
        mView.showLoading();
//        }
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", pageNo + "");
        params.put("pageSize", String.valueOf(PAGE_SIZE));
        if (!StringUtils.isEmpty(brandIds)) {
            params.put("brandIds", brandIds);
        } else if (!StringUtils.isEmpty(navCategoryIds)) {
            params.put("navCategoryIds", navCategoryIds);
        } else if (!StringUtils.isEmpty(key)) {
            params.put("keyword", key);
        }if (!StringUtils.isEmpty(promotionIds)){
            params.put("promotionIds", promotionIds);
        }
        if (!StringUtils.isEmpty(sortType)){
            params.put("sortType", sortType);
        }
        if (!StringUtils.isEmpty(shoppingGuideJson)){
            params.put("shoppingGuideJson", shoppingGuideJson);
        }

        if (priceAnger != null && priceAnger.length() > 1) {
            params.put("priceRange", priceAnger);
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
//        params.put("areaCode",OdyApplication.getValueByKey(Constants.AREA_CODE,""));
        OkHttpManager.getAsyn(Constants.GET_SEARCH_LIST, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ResultBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
//                if (pageNo == 1){
                mView.hideLoading();
//                }
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(ResultBean response) {
                Log.e("response", "response: " + response);
                if (response != null) {
                    mView.initSearchList(response);
                }
            }
        });
    }

    @Override
    public void addToCart(String mpId, final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    mView.addSuccessful(position);
                }
            }
        });
    }

    @Override
    public void getPromotionInfo(String promotionIds) {
        Map<String, String> params = new HashMap<>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
        params.put("promotionId",promotionIds);
        OkHttpManager.getAsyn(Constants.CART_EXT, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<CartExtBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(CartExtBean response) {
                if (response != null) {
                    mView.initPromotion(response);
                }
            }
        });
    }


    @Override
    public void initCartNum() {
        Map<String, String> map = new HashMap<String, String>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        map.put("sessionId", OdySysEnv.getSessionId());
        map.put("ut",ut);
        OkHttpManager.getAsyn(Constants.PRODUCT_CARTCOUNT, map, mView.getNetTAG(),new OkHttpManager.ResultCallback<ShoppingCountBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ShoppingCountBean response) {
                ShoppingCountBean bean = response;
                if (null!=response&&response.code.equals("0")){
                    if (bean.getData()> 0 ) {
                        mView.initCartNum(bean.getData());
                    }else{
                        //ToastUtils.showShort(response.message);
                    }
                }
            }

        });
    }

    @Override
    public void getPromotionInfoDetail(String promotionIds) {
        Map<String, String> params = new HashMap<>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
        params.put("promotionId",promotionIds);
        OkHttpManager.getAsyn(Constants.PROMOTION_DETAIL, params,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<PromotionDetailBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(PromotionDetailBean response) {
                if (response != null) {
                    mView.initPromotionDetail(response);
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
                    mView.setHotWord(response.data.hotword);
                }
            }
        });
    }

    @Override
    public void getCurrentPrice(String mpIds) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpIds", mpIds);//商品ID
//        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_CURRENT_PRICE, map,mView.context().getClass().toString(), new OkHttpManager.ResultCallback<StockPriceBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }
            @Override
            public void onResponse(StockPriceBean response) {
                if (response != null && response.data != null){
                    mView.setCurrentPrice(response);
                }
            }
            @Override
            public void onFinish() {
                super.onFinish();
//                mView.hideLoading();
            }

        });
    }

    @Override
    public void getStoreHotKey(String merchantId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantId", merchantId);//商品ID
        OkHttpManager.getAsyn(Constants.STORE_HOT_KEY, map , new OkHttpManager.ResultCallback<StoreHotKeyBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(StoreHotKeyBean storeHotKeyBean) {
                if (storeHotKeyBean != null && storeHotKeyBean.getData() != null){
                    mView.setStoreHotKey(storeHotKeyBean);
                }
            }
            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

}
