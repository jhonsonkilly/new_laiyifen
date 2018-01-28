package com.ody.p2p.check.coupon;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ${tang} on 2016/8/22.
 */
public class CouponCountBean extends BaseRequestBean {


    /**
     * errMsg : null
     * data : {"canUserCount":10,"inactiveCount":1,"expiredCount":0,"count":12}
     * trace : 56!$6#@16%&172!$,null,61646825178100334291331
     */

    public Object errMsg;
    /**
     * canUserCount : 10
     * inactiveCount : 1
     * expiredCount : 0
     * count : 12
     */

    public DataBean data;
    public String trace;

    public static class DataBean {
        public int canUserCount;
        public int inactiveCount;
        public int expiredCount;
        public int usedCount;
        public int count;
    }
}
