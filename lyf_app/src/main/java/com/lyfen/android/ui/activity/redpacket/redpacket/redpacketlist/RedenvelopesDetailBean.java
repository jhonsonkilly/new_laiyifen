package com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist;

import java.util.List;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedenvelopesDetailBean  {
    public RedDetail data;

    public class RedDetail {
        public String name;
        public String bestred;
        public String getnum;
        public String getamount;
        public List<RedenvelopesListBean> getred;
        //send
        public String totalnum;
        public String totalamount;
        public List<RedenvelopesListBean> sendred;

    }
}