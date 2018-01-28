package com.lyfen.android.app;

import com.lyfen.android.entity.network.QrcodeEntity;
import com.lyfen.android.entity.network.dialog.HomeNewUserEntity;
import com.lyfen.android.entity.network.home.UpdataAppEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * <p> Created by qiujie on 2017/12/5/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public interface DialogApi {
    // 首页 新人 对话框
    @GET("/api/dolphin/list")
    Observable<HomeNewUserEntity> getHomeDialog(@QueryMap Map<String, String> map);


    //    appChannel:Anzhi
//    uniqueCode:com.umaman.laiyifen
//    sessionId:866090036870248
//    platformId:0
//    versionCode:5.0.8
//    appVersionCode:508
    //    app 升级 对话框
    @GET("/api/app/upgrade")
    Observable<UpdataAppEntity> updataApp(@QueryMap Map<String, String> map);

    //    专属吗
    @FormUrlEncoded
    @POST("/ouser-web/api/user/getSingleToken.do")
    Observable<QrcodeEntity> getQrcode(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("/ouser-web/api/user/checkSingleTokenStatus.do")
    Observable<QrcodeEntity> getcheckSingleTokenStatus(@FieldMap Map<String, String> map);

}
