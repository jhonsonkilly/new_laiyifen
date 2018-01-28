package com.lyfen.android.app;

import com.lyfen.android.entity.network.FuckEntity;
import com.lyfen.android.entity.network.StockPriceEntity;
import com.lyfen.android.entity.network.search.FootHistoryEntity;
import com.lyfen.android.entity.network.search.SearchHistoryEntity;
import com.lyfen.android.entity.network.search.SearchHotWordEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qj on 2017/5/18.
 */

public interface SearchApi {
    //    热刺
    @GET("/api/dolphin/list?&platform=3&platformId=0&pageCode=APP_HOME&adCode=hotword")
    Observable<SearchHotWordEntity> getHotWord(@Query("areaCode") String areaCode,
                                               @Query("sessionId") String sessionId);


    //    搜索历史
    @GET("/api/search/searchHistoryList?&platform=3&platformId=0&count=20")
    Observable<SearchHistoryEntity> getHistoryList(@Query("areaCode") String areaCode,
                                                   @Query("sessionId") String sessionId,
                                                   @Query("ut") String ut);

    //用户足迹
    @GET("/api/my/foot/list?&platform=3&platformId=0&pageSize=20")
    Observable<FootHistoryEntity> getFootList(@Query("areaCode") String areaCode,
                                              @Query("sessionId") String sessionId,
                                              @Query("ut") String ut,
                                              @Query("pageNo") String pageNo);


    //校验 价格
    @GET("/api/realTime/getPriceStockList")
    Observable<StockPriceEntity> getStockPrice(@Query("mpIds") String mpIds);


    //校验 价格
    @GET("/back-finance-web/api/commission/queryPreCommissionAmount.do")
    Observable<FuckEntity> fuck1(@Query("ut") String ut);


}
