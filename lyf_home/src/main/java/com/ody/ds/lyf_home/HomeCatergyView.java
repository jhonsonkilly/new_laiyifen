package com.ody.ds.lyf_home;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.retrofit.home.QiangGouBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.util.List;

/**
 * Created by lxs on 2016/12/27.
 */
public interface HomeCatergyView extends BaseView {

    void initPager(AppHomePageBean bean,boolean isFreshAll);

    void initAdData(String adCode, String adData);

    void initHeadlinesData(HeadLinesBean bean);

    void initRankData(ModuleDataBean moduleDataBean);

    void initCategory(long moduleId, List<ModuleDataCategoryBean.CategoryBean> list);

    void initCategoryProduct(long moduleId, ModuleDataBean bean ,long categoryId);

    void onRefreshComplete();

    void initFloatTail(Ad ad);

    void setUnReadCount(int count);

    void setRankPrice(StockPriceBean bean);

    void setRecommendPrice(StockPriceBean bean);

    void initSpecCategoryProduct(long moduleId, ModuleDataBean bean ,long categoryId);

    void initTimeList(QiangGouBean response,AppHomePageBean.Children children);

    void setPriceCmsModuleDataVO(StockPriceBean o);
}
