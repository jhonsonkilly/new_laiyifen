package com.lyfen.android.app;

import com.lyfen.android.entity.network.product.ProductCommedEntity;
import com.lyfen.android.entity.network.product.ProductDetailEntity;
import com.lyfen.android.entity.network.product.ProductListEntity;
import com.lyfen.android.entity.network.product.ProductPromotionEntity;
import com.lyfen.android.entity.network.product.ProductRecommendEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qj on 2017/5/17.
 */

public interface ProductApi {

    @GET("/api/search/searchList?&pageSize=20&platformId=0")
    Observable<ProductListEntity> getSearchList(@Query("keyword") String key,
                                                @Query("sortType") String sortType,
                                                @Query("pageNo") int pageNo,
                                                @Query("shoppingGuideJson") String shoppingGuideJson,
                                                @Query("brandIds") String brandIds,
                                                @Query("priceAnger") String priceRange,
                                                @Query("navCategoryIds") String navCategoryIds,
                                                @Query("promotionIds") String promotionIds,
                                                @Query("ut") String ut,
                                                @Query("areaCode") String areaCode,
                                                @Query("sessionId") String sessionId);

    //商品详情
    @GET("/api/product/baseInfo?&platformId=0")
    Observable<ProductDetailEntity> getProductDetail(@Query("mpId") String mpId,
                                                     @Query("areaCode") String areaCode,
                                                     @Query("sessionId") String sessionId);

    //商品活动 信息
    @GET("/api/product/promotionInfo?&platformId=0")
    Observable<ProductPromotionEntity> getPromotionInfo(@Query("mpId") String mpId,
                                                        @Query("areaCode") String areaCode,
                                                        @Query("sessionId") String sessionId);

    //商品评价
    // params.put("mpId", mpId);
//        params.put("hasPic", hasPic + "");    // 	是否有图 0:全部;1:有图;2:无图
//        params.put("rateFlag", 1 + "");//0:全部；1:好评;2:中评;3:差评
//        params.put("pageNo", "1");  //  页数
//        params.put("pageSize", 3 + "");//条数
    @GET("/api/social/mpComment/get?&platformId=0")
    Observable<ProductCommedEntity> getCommed(@Query("mpId") String mpId,
                                              @Query("areaCode") String areaCode,
                                              @Query("sessionId") String sessionId,
                                              @Query("pageNo") String pageNo,
                                              @Query("pageSize") String pageSize,
                                              @Query("rateFlag") String rateFlag,
                                              @Query("hasPic") String hasPic);

    //猜你喜欢
    // params.put("sceneNo", "1");//推荐商品场景，0,首页;1,详情页;,2购物车;3,订单页;4,搜索页无搜索结果
    @GET("/api/read/product/recommendProductList?&pageSize=16&platformId=0&platform=3&platformId=0&pageNo=1")
    Observable<ProductRecommendEntity> recommendProductList(@Query("mpIds") String mpId,
                                                            @Query("areaCode") String areaCode,
                                                            @Query("sessionId") String sessionId,
                                                            @Query("sceneNo") String sceneNo);

}
