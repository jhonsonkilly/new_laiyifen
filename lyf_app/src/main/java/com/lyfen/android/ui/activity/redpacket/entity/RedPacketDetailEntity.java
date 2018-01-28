package com.lyfen.android.ui.activity.redpacket.entity;


import com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist.RedenvelopesListBean;

import java.util.List;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketDetailEntity {
    public RedDescriptionData data;

    public class RedDescriptionData {
        public String name;
        public String number;
        public String time;
        public String amount;
        public String subamount;
        public List<RedenvelopesListBean> reds;
        public String sharecontent;
        public String shareimg;
        public String sharetitle;
        public String shareurl;

    }
}
