package com.lyfen.android.ui.activity.redpacket;


import com.lyfen.android.ui.activity.redpacket.entity.RedPacketCreateEntity;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketDetailEntity;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketInitEntity;
import com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist.RedenvelopesDetailBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 * text
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public interface RedPacketNetWorkApi {

    @POST("/runabout/service/redpacketInit")
    Observable<RedPacketInitEntity> redpacketInit(@QueryMap Map<String, String> params);

    @POST("/runabout/service/redpacketSendList")
    Observable<RedenvelopesDetailBean> redpacketSendList(@QueryMap Map<String, String> params);

    @POST("/runabout/service/redpacketReceiveList")
    Observable<RedenvelopesDetailBean> redpacketReceiveList(@QueryMap Map<String, String> params);

    @POST("/runabout/service/redpacketReceiveDetail")
    Observable<RedPacketDetailEntity> redpacketReceiveDetail(@QueryMap Map<String, String> params);

    @POST("/runabout/service/redpacketSendDetail")
    Observable<RedPacketDetailEntity> redpacketSendDetail(@QueryMap Map<String, String> params);

    @POST("/runabout/service/redpacketCreate")
    Observable<RedPacketCreateEntity> redpacketCreate(@QueryMap Map<String, String> params);





}
