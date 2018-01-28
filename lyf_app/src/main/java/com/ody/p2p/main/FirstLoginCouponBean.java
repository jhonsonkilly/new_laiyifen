package com.ody.p2p.main;

import com.odianyun.BaseRequestBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by user on 2017/5/22.
 */

public class FirstLoginCouponBean extends BaseRequestBean {

    public Data data;

    public static class Data {
        public List<MyCouponOutputDTO> canUseCouponList;
    }

    public static class MyCouponOutputDTO {


        public long couponId;
        public String couponCode;
        public String themeTitle;
        public String startTime;
        public String startTimeStr;
        public String endTime;
        public String endTimeStr;
        public double couponValue;
        public double useLimit;
        public double useUpLimit;
        public int couponDiscountType;
        public int individualLimit;
        public String refDescription;
        public String moneyRule;
        public int themeType;
        public long merchantId;
        public String merchantName;
        public int canShare;
        public int couponUseType;
        public String themeDesc;
        public int useRange;
    }

}
