package com.ody.p2p.search.searchresult;

import java.util.List;

/**
 * Created by lxs on 2017/2/10.
 */
public class PromotionDetailBean {

    public Data data;

    public static class Data{
        public long id;
        public String description;
        public long promotionId;
        public int promotionType;
        public int contentType;
        public String iconText;
        public String url;
        public String iconUrl;
        public String startTime;
        public String endTime;
        public List<PromotionRuleVO> promotionRuleList;
        public List<PromotionGiftDetailVO> promotionGiftDetailList;
    }

    public static class PromotionRuleVO{
        public long promotionId;
        public int conditionType;
        public int contentType;
        public int contentValue;
        public long conditionValue;
        public String description;
        public long merchantId;
        public String merchantName;
        public int level;
        public String iconUrl;
    }

    public static class PromotionGiftDetailVO{
        public long promotionId;
        public int level;
        public String description;
        public String specificDescription;
        public long promotionRuleId;
        public int conditionType;
        public int contentValue;
        public double conditionValue;
        public int giftType;
        public List<SingleGiftInfoVO> singleGiftInfoList;
        public List<SingleCouponInfoVO> singleCouponInfoList;
    }

    public static class SingleGiftInfoVO{
        public long  promotionId;
        public long promotionRuleId;
        public long merchantId;
        public long mpId;
        public String giftName;
        public int giftNum;
        public String picUrl;
        public double price;
        public String explain;
        public int soldOut;
    }

    public static class SingleCouponInfoVO{
        public long id;
        public String themeTitle;
        public double useLimit;
        public int couponDiscountType;
        public int couponDiscount;
        public int amountRule;
        public double couponAmount;
        public int soldOut;

    }


}
