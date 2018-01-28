package com.lyfen.android.app;

import com.lyfen.android.entity.network.CommonEntity;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.entity.network.activity.OrderListEntity;
import com.lyfen.android.entity.network.activity.OrderSearchEntity;
import com.lyfen.android.entity.network.dialog.BuyAgainEntity;


import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public interface OrderApi {


    //    订单列表 api/my/order/getOrderStockState
    @GET("/api/my/order/list")
    Observable<OrderListEntity> getOrderList(@QueryMap Map<String, String> map);

    //    售后进度
    @GET("/api/my/orderAfterSale/afterSaleList")
    Observable<OrderListEntity> afterSaleList(@QueryMap Map<String, String> map);

    //    订单搜索
    @GET("/api/my/order/search")
    Observable<OrderSearchEntity> getorderSearchList(@QueryMap Map<String, String> map);

    //    订单详情
    @GET("/api/my/order/detail")
    Observable<OrderDetailEntity> getdetailList(@QueryMap Map<String, String> map);


    //    删除订单
    @FormUrlEncoded
    @POST("/api/my/order/delete")
    Observable<CommonEntity> deleteOrder(@FieldMap() Map<String, String> map);


    //    确认订单
    @FormUrlEncoded
    @POST("/api/my/order/confirmReceived")
    Observable<CommonEntity> confirm(@FieldMap() Map<String, String> map);

//    再次购买

    @GET("api/my/order/getOrderStockState")
    Observable<BuyAgainEntity> getOrderStockState(@QueryMap Map<String, String> map);

}
