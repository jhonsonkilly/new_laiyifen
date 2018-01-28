package com.lyfen.android.entity.network.product;

import java.util.List;

/**
 * Created by qj on 2017/6/7.
 */

public class ProductPromotionEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"promotionInfo":[{"mpId":1007020801001939,"iconText":null,"iconUrl":null,"promotions":[{"id":null,"description":"618肉肉欢乐颂特价","promotionId":1040031000000944,"promotionType":1,"frontPromotionType":8,"contentType":8,"url":null,"iconText":"直降","iconUrl":"http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487756014291_5897_78.png","weight":2,"startTime":1496419200000,"endTime":1496851200000,"promotionRuleList":[{"promotionId":null,"conditionType":null,"conditionValue":null,"contentType":3,"contentValue":590,"description":"直降5.9元","merchantId":null,"merchantName":null,"flag":true,"level":null,"iconUrl":null}],"promotionGiftDetailList":null,"isJumpPage":0,"jumpPageUrl":null,"individualLimitNum":-1,"totalLimitNum":-1},{"id":null,"description":"满38元送小幸运礼包","promotionId":1048031000000843,"promotionType":4,"frontPromotionType":1005,"contentType":4,"url":null,"iconText":"满赠","iconUrl":"http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png","weight":5,"startTime":1496678400000,"endTime":1496851200000,"promotionRuleList":null,"promotionGiftDetailList":[{"promotionId":1048031000000843,"level":1,"description":"每满38.0元送赠品","specificDescription":"满38.0元,送以下任意1件商品，请在购物车领取","giftType":1,"promotionRuleId":1048031000000874,"conditionType":1,"conditionValue":3800,"contentValue":1,"singleGiftInfoList":[{"promotionId":1048031000000843,"promotionRuleId":1048031000000874,"merchantId":101,"mpId":1008023400000021,"giftName":"会员礼包（伊份小幸运）120g(赠品)","giftNum":1,"picUrl":"http://cdn.oudianyun.com/1490768407995_81.24525880341737_a478a058-7148-4167-88ff-b43848d001c8.jpg","price":0,"explain":null,"soldOut":0}],"singleCouponInfoList":null}],"isJumpPage":1,"jumpPageUrl":null,"individualLimitNum":-1,"totalLimitNum":-1}]}]}
     * trace : 83!$1#@18%&10!$,153537,62780635563334533840818
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<PromotionInfoEntity> promotionInfo;

        public static class PromotionInfoEntity {
            /**
             * mpId : 1007020801001939
             * iconText : null
             * iconUrl : null
             * promotions : [{"id":null,"description":"618肉肉欢乐颂特价","promotionId":1040031000000944,"promotionType":1,"frontPromotionType":8,"contentType":8,"url":null,"iconText":"直降","iconUrl":"http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487756014291_5897_78.png","weight":2,"startTime":1496419200000,"endTime":1496851200000,"promotionRuleList":[{"promotionId":null,"conditionType":null,"conditionValue":null,"contentType":3,"contentValue":590,"description":"直降5.9元","merchantId":null,"merchantName":null,"flag":true,"level":null,"iconUrl":null}],"promotionGiftDetailList":null,"isJumpPage":0,"jumpPageUrl":null,"individualLimitNum":-1,"totalLimitNum":-1},{"id":null,"description":"满38元送小幸运礼包","promotionId":1048031000000843,"promotionType":4,"frontPromotionType":1005,"contentType":4,"url":null,"iconText":"满赠","iconUrl":"http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png","weight":5,"startTime":1496678400000,"endTime":1496851200000,"promotionRuleList":null,"promotionGiftDetailList":[{"promotionId":1048031000000843,"level":1,"description":"每满38.0元送赠品","specificDescription":"满38.0元,送以下任意1件商品，请在购物车领取","giftType":1,"promotionRuleId":1048031000000874,"conditionType":1,"conditionValue":3800,"contentValue":1,"singleGiftInfoList":[{"promotionId":1048031000000843,"promotionRuleId":1048031000000874,"merchantId":101,"mpId":1008023400000021,"giftName":"会员礼包（伊份小幸运）120g(赠品)","giftNum":1,"picUrl":"http://cdn.oudianyun.com/1490768407995_81.24525880341737_a478a058-7148-4167-88ff-b43848d001c8.jpg","price":0,"explain":null,"soldOut":0}],"singleCouponInfoList":null}],"isJumpPage":1,"jumpPageUrl":null,"individualLimitNum":-1,"totalLimitNum":-1}]
             */

            public long mpId;
            public String iconText;
            public String iconUrl;
            public List<PromotionsEntity> promotions;

            public static class PromotionsEntity {
                /**
                 * id : null
                 * description : 618肉肉欢乐颂特价
                 * promotionId : 1040031000000944
                 * promotionType : 1
                 * frontPromotionType : 8
                 * contentType : 8
                 * url : null
                 * iconText : 直降
                 * iconUrl : http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487756014291_5897_78.png
                 * weight : 2
                 * startTime : 1496419200000
                 * endTime : 1496851200000
                 * promotionRuleList : [{"promotionId":null,"conditionType":null,"conditionValue":null,"contentType":3,"contentValue":590,"description":"直降5.9元","merchantId":null,"merchantName":null,"flag":true,"level":null,"iconUrl":null}]
                 * promotionGiftDetailList : null
                 * isJumpPage : 0
                 * jumpPageUrl : null
                 * individualLimitNum : -1
                 * totalLimitNum : -1
                 */

                public String id;
                public String description;
                public long promotionId;
                public int promotionType;
                public int frontPromotionType;
                public int contentType;
                public String url;
                public String iconText;
                public String iconUrl;
                public int weight;
                public long startTime;
                public long endTime;
//                public String promotionGiftDetailList;
                public int isJumpPage;
                public String jumpPageUrl;
                public int individualLimitNum;
                public int totalLimitNum;
                public List<PromotionRuleListEntity> promotionRuleList;

                public static class PromotionRuleListEntity {
                    /**
                     * promotionId : null
                     * conditionType : null
                     * conditionValue : null
                     * contentType : 3
                     * contentValue : 590
                     * description : 直降5.9元
                     * merchantId : null
                     * merchantName : null
                     * flag : true
                     * level : null
                     * iconUrl : null
                     */

                    public String promotionId;
                    public String conditionType;
                    public String conditionValue;
                    public int contentType;
                    public int contentValue;
                    public String description;
                    public String merchantId;
                    public String merchantName;
                    public boolean flag;
                    public String level;
                    public String iconUrl;
                }
            }
        }
    }
}
