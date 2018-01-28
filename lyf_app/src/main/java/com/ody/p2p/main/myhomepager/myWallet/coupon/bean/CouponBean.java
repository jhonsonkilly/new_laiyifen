package com.ody.p2p.main.myhomepager.myWallet.coupon.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by meijunqiang on 2017/3/27.
 * 描述：
 */

public class CouponBean extends BaseRequestBean {

    /**
     * data : {"inactiveCount":0,"usedCouponList":[{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2}],"count":10,"expiredCount":3,"canUseCouponList":[{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2}],"expiredCouponList":[{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2}],"usedCount":4,"canUserCount":3,"inactiveCouponList":[]}
     */
    public DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * inactiveCount : 0
         * usedCouponList : [{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2}]
         * count : 10
         * expiredCount : 3
         * canUseCouponList : [{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2},{"moneyRule":"满8.0减8.8","limitExplain":null,"themeTitle":"本来测试","refDescription":"每个帐号限用数量:2 ","startTime":1453290600000,"endTime":1454846340000,"useLimit":8,"userPayLimit":1,"couponValue":8.8,"individualLimit":2}]
         * expiredCouponList : [{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2},{"moneyRule":"满2.1减1.0","limitExplain":null,"themeTitle":"本来测试2","refDescription":"每个帐号限用数量:2 适用区域：天津 河北省 山西省 ","startTime":1453342200000,"endTime":1453342800000,"useLimit":2.1,"userPayLimit":0,"couponValue":1,"individualLimit":2}]
         * usedCount : 4
         * canUserCount : 3
         * inactiveCouponList : []
         */
        public int checkStatus;
        public int inactiveCount;
        public List<CouponListEntity> usedCouponList;
        public int count;
        public int expiredCount;
        public int sharedCount;
        public List<CouponListEntity> canUseCouponList;
        public List<CouponListEntity> shareCouponList;
        public List<CouponListEntity> expiredCouponList;
        public int usedCount;
        public int canUserCount;
        public List<?> inactiveCouponList;

        public static class CouponListEntity {
            /**
             * moneyRule : 满2.1减1.0
             * limitExplain : null
             * themeTitle : 本来测试2
             * refDescription : 每个帐号限用数量:2 适用区域：天津 河北省 山西省
             * startTime : 1453342200000
             * endTime : 1453342800000
             * useLimit : 2.1
             * userPayLimit : 0
             * couponValue : 1
             * individualLimit : 2
             */
            public String couponId;
            public String couponCode;
            public String moneyRule;
            public String limitExplain;
            public String themeTitle;
            public String refDescription;
            public long startTime;
            public int couponDiscountType;
            public long endTime;
            public double useLimit;
            public double useUpLimit;//优惠券最高金额
            public int canShare;
            public double couponValue;
            public int individualLimit;
            public boolean showRule = false;
        }
    }
}
