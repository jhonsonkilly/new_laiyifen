package com.ody.p2p.search.searchkey;

import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;

import java.util.List;

/**
 * Created by ody on 2016/5/31.
 */
public interface SearchView extends BaseView {

    //显示搜索记录和推荐的标签,隐藏搜索框有内容时弹出的recycleView
    void showTag();

    //和showTag()作用相反
    void hideTag();

    //给搜索框有内容时弹出的recycleView设置数据
    void setSearchResultList(List<SearchKeywordListBean.Data> data);

    //设置热搜词
    void setHotWord(List<FuncBean.Data.AdSource> searchHotWordList);

    //设置登录状态下的搜索历史
    void setSearchHistory(List<SearchHistoryBean.SearchHistoryData.SearchHistoryList> searchHistoryList);

    //获取未登录状态下的搜索历史
    void getNoLoginSearchHistory();

    //是否隐藏热门搜索的布局
    void hideHotSearch(boolean flag);

    //设置默认搜索词
    void setDefaultWord(List<FuncBean.Data.AdSource> searchword);

    //清空记录
    void clearn();

    Context context();

    void footHistory(List<HistoryBean.FootStepVO> historylist,int totalCount);

    void setCurrentPrice(StockPriceBean bean);

    void setStoreHotKey(StoreHotKeyBean storeHotKeyBean);

}
