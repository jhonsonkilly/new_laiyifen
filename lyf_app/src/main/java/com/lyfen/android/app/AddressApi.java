package com.lyfen.android.app;

import com.lyfen.android.entity.network.AreaCodeEntity;
import com.lyfen.android.entity.network.address.AddressUserEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qj on 2017/6/21.
 * 定位  和 城市列表相关接口
 */

public interface AddressApi {
//    //    获取父城市
//    @GET("/api/location/areaGroupList")
//    Observable<AddressListEntity> areaGroupList(@Query("areaLevel") String areaLevel);






//    //获取子城市
//
//    @GET("/api/location/list/{code}")
//    Observable<AddressSubEntity> getAreaList(@Path("code") String code);

    //得到用户的收获地址
    @FormUrlEncoded
    @POST("/ouser-web/address/getAllAddressForm.do")
    Observable<AddressUserEntity> getAllAddress(@FieldMap() Map<String, String> params);


    // 根据定位获取地址信息
    @GET("/api/location/getArea?countryName=中国")
    Observable<AreaCodeEntity> getArea(@Query("provinceName") String provinceName,
                                       @Query("cityName") String cityName,
                                       @Query("areaName") String areaName);

}
