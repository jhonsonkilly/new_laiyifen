package com.lyfen.android.app;


import com.lyfen.android.entity.network.AlibcEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.lyfen.android</p>
 * <p>文件名:Lyfen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public interface OtherApi {

    // idfa
    @GET("/api/social/write/pandaPlay/activateApp")
    Observable<String> activateApp(@Query("idfa") String idfa);


    //获取阿里 云旺聊天相关信息
    @GET("/admin-web/getTaoBaoOpenIM.json")
    Observable<AlibcEntity> getTaoBaoOpenIM(@Query("userId") String userid,
                                            @Query("companyId") String companyId);



    //购物车数量
    @GET("/api/cart/count?platformId=0")
    Observable<String> count(@Query("ut") String ut,
                             @Query("sessionId") String sessionId,
                             @Query("areaCode") String areaCode);









}
