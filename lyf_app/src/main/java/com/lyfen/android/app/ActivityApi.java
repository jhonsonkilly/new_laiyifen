package com.lyfen.android.app;

import com.lyfen.android.entity.network.activity.AddToCartEntity;
import com.lyfen.android.entity.network.activity.CancelNotifyBean;
import com.lyfen.android.entity.network.activity.NotifyBean;
import com.lyfen.android.entity.network.activity.QiangGouFrgBean;
import com.lyfen.android.entity.network.activity.QiangGouTimeEntity;
import com.lyfen.android.entity.network.activity.ShangouEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by qiujie on 2017/7/21.
 */

public interface ActivityApi {


    //来抢购时间头
    @GET("/api/promotion/secondkill/lyfTimeList")
    Observable<QiangGouTimeEntity> lyfTimeList(@Query("nocache") String nocache);

    @GET("/api/promotion/secondkill/lyfKillList")
    Observable<QiangGouFrgBean> lyfKillList(@QueryMap Map<String, String> parae);


    //    抢购
    @GET("/api/promotion/secondkill/lyfList")
    Observable<ShangouEntity> getShangou(@QueryMap Map<String, String> map);


    @FormUrlEncoded
    @POST("/api/promotion/secondkill/saveNotice")
    Observable<NotifyBean> saveNotice(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("/api/promotion/secondkill/cancelNotice")
    Observable<CancelNotifyBean> cancelNotice(@FieldMap Map<String, String> map);




}
