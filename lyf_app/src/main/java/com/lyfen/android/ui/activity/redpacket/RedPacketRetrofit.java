package com.lyfen.android.ui.activity.redpacket;

import com.ody.p2p.retrofit.RetrofitHelper;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketRetrofit extends RetrofitHelper {

    public RedPacketRetrofit() {
    }

    public RedPacketRetrofit(String url) {
        super(url);
    }

    public RedPacketNetWorkApi getRedPacketService() {

        return getRetrofit().create(RedPacketNetWorkApi.class);
    }
}
