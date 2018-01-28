package com.lyfen.android.app;

import com.lyfen.android.entity.network.mine.MyOrderEntity;
import com.lyfen.android.entity.network.mine.MyWalletEntity;
import com.lyfen.android.entity.network.mine.PersonalEntity;
import com.lyfen.android.entity.network.mine.PrecommissonEntity;
import com.lyfen.android.entity.network.mine.UserInfoEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * <p> Created by qiujie on 2017/12/14/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public interface MineApi {

    //    获取用户 头像和 姓名
    @GET("/api/my/user/info")
    Observable<UserInfoEntity> getHeadImg(@QueryMap Map<String, String> map);


    //    我的钱包
    @GET("/api/my/wallet/summary")
    Observable<MyWalletEntity> wallet(@QueryMap Map<String, String> map);

    //    我的订单数

    @GET("/api/my/order/summary")
    Observable<MyOrderEntity> order(@QueryMap Map<String, String> map);

    //    预估一起赚 金额
    @FormUrlEncoded
    @POST("/back-finance-web/api/commission/queryPreCommissionAmount.do")
    Observable<PrecommissonEntity> getCommison(@FieldMap Map<String, String> map);

    //    九宫格
    @GET("/ouser-web/personalCenter/queryMy.do")
    Observable<PersonalEntity> personalCenter(@QueryMap Map<String, String> map);
}
