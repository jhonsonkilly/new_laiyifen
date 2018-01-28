package com.lyfen.android.entity.network.mine;

/**
 * <p> Created by qiujie on 2017/12/14/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class MyWalletEntity {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"eCardBalance":25.72,"yCardBalance":0,"yBean":7360,"coupon":2,"point":152}
     * trace : 42!$1#@18%&10!$,197461,63468932766398506411712
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * eCardBalance : 25.72
         * yCardBalance : 0.0
         * yBean : 7360
         * coupon : 2
         * point : 152
         */

        public String eCardBalance;
        public String yCardBalance;
        public String yBean;
        public String coupon;
        public String point;
    }
}
