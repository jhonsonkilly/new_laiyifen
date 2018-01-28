package com.ody.p2p.check.coupon;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/22.
 */
public class UseCouponBean extends BaseRequestBean {

    public DataBean data;

    public static class DataBean {
        /**
         * couponId : 1085
         * couponCode : ceskZ5UA6RK4T54L8C
         * themeTitle : 测试优惠券
         * startTime : 1454063820000
         * startTimeStr : null
         * endTime : 1454753880000
         * endTimeStr : null
         * couponValue : 10.0
         * useLimit : 20.1
         * individualLimit : 5
         * userPayLimit : [1]
         * limitExplain : null
         * refDescription : 适用支付方式：网上支付
         每个帐号限用数量:5
         适用区域：上海市

         * moneyRule : 满20.1减10.0
         * selected : 1
         * isAvailable : 1
         * unavailableReason :
         */

        public List<CouponBean.DataBean.CanUseCouponListBean> coupons;
//
//        public static class CouponsBean {
//            public int couponId;
//            public String couponCode;
//            public String themeTitle;
//            public long startTime;
//            public Object startTimeStr;
//            public long endTime;
//            public Object endTimeStr;
//            public double couponValue;
//            public double useLimit;
//            public int individualLimit;
//            public String limitExplain;
//            public String refDescription;
//            public String moneyRule;
//            public int selected;
//            public int isAvailable;
//            public String unavailableReason;
//            public List<Integer> userPayLimit;
//        }
    }
}
