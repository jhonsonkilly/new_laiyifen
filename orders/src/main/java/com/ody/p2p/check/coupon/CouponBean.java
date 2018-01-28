package com.ody.p2p.check.coupon;

import java.util.List;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public class CouponBean {

    /**
     * code : 0
     * message : 操作成功
     * data : {"canUseCouponList":[{"themeTitle":"本来测试","startTime":1453290600000,"endTime":1454846340000,"couponValue":8.8,"useLimit":8,"individualLimit":2,"userPayLimit":1,"limitExplain":null,"refDescription":"每个帐号限用数量:2 ","moneyRule":"满8.0减8.8"},{"themeTitle":"本来测试","startTime":1453290600000,"endTime":1454846340000,"couponValue":8.8,"useLimit":8,"individualLimit":2,"userPayLimit":1,"limitExplain":null,"refDescription":"每个帐号限用数量:2 ","moneyRule":"满8.0减8.8"},{"themeTitle":"本来测试","startTime":1453290600000,"endTime":1454846340000,"couponValue":8.8,"useLimit":8,"individualLimit":2,"userPayLimit":1,"limitExplain":null,"refDescription":"每个帐号限用数量:2 ","moneyRule":"满8.0减8.8"}],"canUserCount":3,"inactiveCouponList":[],"inactiveCount":0,"expiredCouponList":[{"themeTitle":"本来测试2","startTime":1453342200000,"endTime":1453342800000,"couponValue":1,"useLimit":2.1,"individualLimit":2,"userPayLimit":0,"limitExplain":null,"refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","moneyRule":"满2.1减1.0"},{"themeTitle":"本来测试2","startTime":1453342200000,"endTime":1453342800000,"couponValue":1,"useLimit":2.1,"individualLimit":2,"userPayLimit":0,"limitExplain":null,"refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","moneyRule":"满2.1减1.0"},{"themeTitle":"本来测试2","startTime":1453342200000,"endTime":1453342800000,"couponValue":1,"useLimit":2.1,"individualLimit":2,"userPayLimit":0,"limitExplain":null,"refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","moneyRule":"满2.1减1.0"}],"expiredCount":3,"usedCouponList":[{"themeTitle":"本来测试2","startTime":1453342200000,"endTime":1453342800000,"couponValue":1,"useLimit":2.1,"individualLimit":2,"userPayLimit":0,"limitExplain":null,"refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","moneyRule":"满2.1减1.0"},{"themeTitle":"本来测试2","startTime":1453342200000,"endTime":1453342800000,"couponValue":1,"useLimit":2.1,"individualLimit":2,"userPayLimit":0,"limitExplain":null,"refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","moneyRule":"满2.1减1.0"},{"themeTitle":"本来测试","startTime":1453290600000,"endTime":1454846340000,"couponValue":8.8,"useLimit":8,"individualLimit":2,"userPayLimit":1,"limitExplain":null,"refDescription":"每个帐号限用数量:2 ","moneyRule":"满8.0减8.8"},{"themeTitle":"本来测试","startTime":1453290600000,"endTime":1454846340000,"couponValue":8.8,"useLimit":8,"individualLimit":2,"userPayLimit":1,"limitExplain":null,"refDescription":"每个帐号限用数量:2 ","moneyRule":"满8.0减8.8"}],"usedCount":4,"count":10}
     */

    public String code;
    public String message;

    public DataBean data;

    public static class DataBean {
        public int canUserCount;
        public int inactiveCount;
        public int expiredCount;
        public int usedCount;
        public int count;
        /**
         * themeTitle : 本来测试
         * startTime : 1453290600000
         * endTime : 1454846340000
         * couponValue : 8.8
         * useLimit : 8
         * individualLimit : 2
         * userPayLimit : 1
         * limitExplain : null
         * refDescription : 每个帐号限用数量:2
         * moneyRule : 满8.0减8.8
         */

        public List<CanUseCouponListBean> canUseCouponList;
        /**
         * themeTitle : 本来测试2
         * startTime : 1453342200000
         * endTime : 1453342800000
         * couponValue : 1
         * useLimit : 2.1
         * individualLimit : 2
         * userPayLimit : 0
         * limitExplain : null
         * refDescription : 每个帐号限用数量:2 适用区域：天津 河北省 山西省
         * moneyRule : 满2.1减1.0
         */

        public List<CanUseCouponListBean> expiredCouponList;
        /**
         * themeTitle : 本来测试2
         * startTime : 1453342200000
         * endTime : 1453342800000
         * couponValue : 1
         * useLimit : 2.1
         * individualLimit : 2
         * userPayLimit : 0
         * limitExplain : null
         * refDescription : 每个帐号限用数量:2 适用区域：天津 河北省 山西省
         * moneyRule : 满2.1减1.0
         */

        public List<CanUseCouponListBean> usedCouponList;

        public static class CanUseCouponListBean {
            public String couponId;
            public String couponCode;
            public String startTimeStr;
            public String endTimeStr;
            public int selected;
            public int isAvailable;
            public int themeType;
            public String unavailableReason;
            public String themeTitle;
            public long startTime;
            public long endTime;
            public String couponValue;
            public String useLimit;
            public String merchantName;
            public String merchantId;
            public int individualLimit;
            public List<Integer> userPayLimit;
            public String limitExplain;
            public String refDescription;
            public String moneyRule;

        }

//        public static class ExpiredCouponListBean {
//            public String themeTitle;
//            public long startTime;
//            public long endTime;
//            public int couponValue;
//            public double useLimit;
//            public int individualLimit;
//            public int userPayLimit;
//            public Object limitExplain;
//            public String refDescription;
//            public String moneyRule;
//        }
//
//        public static class UsedCouponListBean {
//            public String themeTitle;
//            public long startTime;
//            public long endTime;
//            public int couponValue;
//            public double useLimit;
//            public int individualLimit;
//            public int userPayLimit;
//            public Object limitExplain;
//            public String refDescription;
//            public String moneyRule;
//        }
    }
}
