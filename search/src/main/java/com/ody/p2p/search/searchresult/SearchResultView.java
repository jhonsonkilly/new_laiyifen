package com.ody.p2p.search.searchresult;

import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;

import java.util.List;

/**
 * Created by lxs on 2016/6/17.
 */
public interface SearchResultView extends BaseView {

    void initSearchList(ResultBean bean);

    void addSuccessful(int position);

    Context context();

    void initPromotion(CartExtBean bean);

    void initPromotionDetail(PromotionDetailBean bean);

    void initCartNum(int num);

    void setHotWord(List<FuncBean.Data.AdSource> hotWord);

    void setCurrentPrice(StockPriceBean bean);

    void loadList(ResultBean bean);

    void setStoreHotKey(StoreHotKeyBean storeHotKeyBean);
}
