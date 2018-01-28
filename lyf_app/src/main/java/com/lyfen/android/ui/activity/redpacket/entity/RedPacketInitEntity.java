package com.lyfen.android.ui.activity.redpacket.entity;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketInitEntity {

    /**
     * msg : 0
     * uname :
     * data : {"deposit":"499673.24","maxAmount":2000,"minSimpleAmount":0.01}
     * session :
     * servertime : 1487147507
     * status : 0
     */

    public String msg;
    public String uname;
    public DataEntity data;
    public String session;
    public String servertime;
    public String status;

    public static class DataEntity {
        /**
         * deposit : 499673.24
         * maxAmount : 2000
         * minSimpleAmount : 0.01
         */

        public String deposit;
        public int maxAmount;
        public double minSimpleAmount;
    }
}
