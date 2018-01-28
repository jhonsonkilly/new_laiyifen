package com.ody.ds.lyf_home;

import com.ody.p2p.retrofit.home.AppHomePageBean;

/**
 * Created by lxs on 2016/12/14.
 */
public interface HomeCatergyPresenter {
    public void getHomePage(boolean isFreshAll);

    public void getAdData(String adCode);

    void getFloatTail();

    public void getHeadlines();

    public void getRank(long moduleId);

    public void getCategory(long moduleId);

    public void getCategoryProduct(long moduleId, long categoryId, int pageNo);

    void getMsgSummary();

    void getStockPriceRank(String mpIds);

    void getStockPriceRecommend(String mpIds);

    public void getSpecCategoryProduct(long moduleId, long categoryId, int pageNo,int pageSize);

    void getGiangGouTime(AppHomePageBean.Children children);

    public void getPriceCmsModuleDataVO(String mpIds);
}
