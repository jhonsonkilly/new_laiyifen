package com.lyfen.android.app;

import com.lyfen.android.entity.network.CommonEntity;
import com.lyfen.android.entity.network.activity.AddToCartEntity;
import com.lyfen.android.entity.network.product.ProductRecommendEntity;
import com.lyfen.android.entity.network.shopCart.ShopCartEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qiujie on 2017/7/11.
 */

public interface ShoppingCartApi {


    //    获取购物车数量
    @GET("/api/cart/count")
    Observable<CommonEntity> areaGroupList(@QueryMap Map<String, String> params);


    //    购物车
    @FormUrlEncoded
    @POST("/api/cart/list")
    Observable<ShopCartEntity> cartList(@FieldMap Map<String, String> map);


    //猜你喜欢
    // params.put("sceneNo", "1");//推荐商品场景，0,首页;1,详情页;,2购物车;3,订单页;4,搜索页无搜索结果
    @GET("/api/read/product/recommendProductList?&pageSize=16&platformId=0&platform=3&platformId=0&pageNo=1")
    Observable<ProductRecommendEntity> recommendProductList(@Query("mpIds") String mpId,
                                                            @Query("areaCode") String areaCode,
                                                            @Query("sessionId") String sessionId,
                                                            @Query("sceneNo") String sceneNo);
    @FormUrlEncoded
    @POST("/api/cart/addItem")
    Observable<CommonEntity> addItem2Cart(@FieldMap Map<String, String> map);

}
