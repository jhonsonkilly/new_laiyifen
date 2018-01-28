package com.ody.p2p.search.searchkey;

/**
 * Created by ody on 2016/5/31.
 */
public interface SearchPresenter {

    //获取搜索框有内容时弹出的recycleView里的数据------------目前宜和和本来均无此接口
    void getSearchList(String keyword);

    //获取热搜词
    void getHotWord();

    //刚进入搜索界面时,在搜索框内就会出现默认的搜索词
    void getDefaultWord();


    /**
     * 走接口的 搜索历史和清除搜索历史
     */
    //登录状态下获取搜索历史
    void getSearchHistory(int count);

    //清除搜索历史
    void cleanSearchHistory(String historyKeywordJson);

    //足迹
    void traceProduct(int pageNo);

    void getCurrentPrice(String mpIds);


}
