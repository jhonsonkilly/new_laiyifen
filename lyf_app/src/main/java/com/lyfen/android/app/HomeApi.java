package com.lyfen.android.app;

import com.laiyifen.lyfframework.network.ModuleDateCategoryEntity;
import com.lyfen.android.entity.network.StockPriceEntity;
import com.lyfen.android.entity.network.home.HomeBannerEntity;
import com.lyfen.android.entity.network.home.HomeChannalEntity;
import com.lyfen.android.entity.network.home.HomeNewsEntity;
import com.lyfen.android.entity.network.home.HomeProductListEntity;
import com.lyfen.android.entity.network.home.HomePropertiesEntity;
import com.lyfen.android.entity.network.home.HomeRankEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qj on 2017/5/4.
 */

public interface HomeApi {

    //模板设置
    @GET("/cms/page/getAppHomePage")
    Observable<HomePropertiesEntity> getAppHomePage();

    //悬浮分类
    @GET("/cms/page/module/getModuleDataCategory")
    Observable<ModuleDateCategoryEntity> getModuleDataCategory(@Query("moduleId") String moduleId);

    //底部商品列表
    @GET("/cms/page/module/getModuleData?pageSize=20")
    Observable<HomeProductListEntity> getModuleData(@Query("moduleId") String moduleId,
                                                    @Query("categoryId") String categoryId,
                                                    @Query("pageNo") int pageNo);

    //banner
    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Observable<HomeBannerEntity> getHomeBanner(@Query("pageCode") String pageeCode,
                                               @Query("adCode") String adCode,
                                               @Query("areaCode") String areaCode);

    //频道
    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Observable<HomeChannalEntity> getHomeChanner(@Query("pageCode") String pageCode,
                                                 @Query("adCode") String adCode,
                                                 @Query("areaCode") String areaCode);


    //热点 新闻
    @GET("/cms/view/h5/headlinesList?platformId=0&categoryType=2&currentPage=1&itemsPerPage=5&code=headlines")
    Observable<HomeNewsEntity> getHomeNews();

    //热销 排名
    @GET("/cms/page/module/getModuleData?pageSize=10&pageNo=1&categoryId")
    Observable<HomeRankEntity> getHomeRank(@Query("moduleId") String moduleId);


    //校验 价格
    @GET("/api/realTime/getPriceStockList")
    Observable<StockPriceEntity> getStockPrice(@Query("mpIds") String mpIds);




}


