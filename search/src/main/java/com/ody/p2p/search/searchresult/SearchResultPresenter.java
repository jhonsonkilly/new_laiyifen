package com.ody.p2p.search.searchresult;

/**
 * Created by lxs on 2016/6/17.
 */
public interface SearchResultPresenter {

    void getList(String key, String sortType, int pageNo, String shoppingGuideJson, String brandIds, String priceRange, String navCategoryIds, String promotionIds);

    void getList(String key, String sortType, int pageNo, String shoppingGuideJson, String brandIds, String priceRange, String navCategoryIds, String promotionIds, String merchantId);

    void getList(String key, String sortType, int pageNo, String shoppingGuideJson, String brandIds, String priceAnger, String navCategoryIds, String promotionIds, String merchantId, String filterType);

    void addToCart(String mpId, int position);

    void getPromotionInfo(String promotionIds);

    void initCartNum();

    void getPromotionInfoDetail(String promotionIds);

    void getHotWord();

    void getCurrentPrice(String mpIds);

    void getList(String merchantId, int pageNo);

    void getStoreHotKey(String merchantId);

}
