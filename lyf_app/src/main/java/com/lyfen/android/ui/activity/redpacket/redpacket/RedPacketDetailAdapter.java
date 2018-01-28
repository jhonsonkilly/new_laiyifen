package com.lyfen.android.ui.activity.redpacket.redpacket;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist.RedenvelopesListBean;
import com.ody.p2p.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketDetailAdapter extends BaseQuickAdapter<RedenvelopesListBean, BaseViewHolder> {
    public RedPacketDetailAdapter() {
        this(R.layout.item_reddetaillist, new ArrayList<RedenvelopesListBean>());
    }


    public RedPacketDetailAdapter(int layoutResId, List<RedenvelopesListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedenvelopesListBean item) {


        helper.setText(R.id.name, item.login_name);
        helper.setText(R.id.amount, item.sub_amount + "元");
        helper.setText(R.id.time, item.send_time);

    }
}
