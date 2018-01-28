package com.ody.p2p.check.giftcard;



import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by tangmeijuan on 16/6/15.
 */
public class GiftCardBean extends BaseRequestBean {


    /**
     * giftCardList : [{"giftCardId":2827,"themeTitle":"生鲜o2o产业联盟B1","cardType":0,"effStartDate":1439222400000,"effStartDateStr":"2016-07-01","effEndDate":1470758400000,"effEndDateStr":"2016-07-09","bindTime":1457489438000,"bindTimeStr":"2016-07-06","cardAmount":200,"cardBalance":200,"describe":"使用说明么么么么","payType":[],"available":true}]
     * inactiveGiftCardList : [{"giftCardId":2826,"themeTitle":"生鲜o2o产业联盟B1","cardType":0,"effStartDate":1439222400000,"effStartDateStr":"2016-07-01","effEndDate":1470758400000,"effEndDateStr":"2016-07-09","bindTime":1457489438000,"bindTimeStr":"2016-07-06","cardAmount":200,"cardBalance":200,"describe":"使用说明么么么么","payType":[],"available":false}]
     * usedList : []
     * expiredList : []
     * giftCardBalance : 200
     * allBalance : 400
     */

    public DataBean data;

    public static class DataBean {
        public String giftCardBalance;
        public String allBalance;
        /**
         * giftCardId : 2827
         * themeTitle : 生鲜o2o产业联盟B1
         * cardType : 0
         * effStartDate : 1439222400000
         * effStartDateStr : 2016-07-01
         * effEndDate : 1470758400000
         * effEndDateStr : 2016-07-09
         * bindTime : 1457489438000
         * bindTimeStr : 2016-07-06
         * cardAmount : 200
         * cardBalance : 200
         * describe : 使用说明么么么么
         * payType : []
         * available : true
         */
        public List<GiftCardListBean> giftCartBindRecords;
        public List<GiftCardListBean> giftCardList;
        /**
         * giftCardId : 2826
         * themeTitle : 生鲜o2o产业联盟B1
         * cardType : 0
         * effStartDate : 1439222400000
         * effStartDateStr : 2016-07-01
         * effEndDate : 1470758400000
         * effEndDateStr : 2016-07-09
         * bindTime : 1457489438000
         * bindTimeStr : 2016-07-06
         * cardAmount : 200
         * cardBalance : 200
         * describe : 使用说明么么么么
         * payType : []
         * available : false
         */

        public List<GiftCardListBean> inactiveGiftCardList;
        public List<GiftCardListBean> usedList;
        public List<GiftCardListBean> expiredList;

        public static class GiftCardListBean {
            public String giftCardId;
            public String themeTitle;
            public int cardType;
            public long effStartDate;
            public String effStartDateStr;
            public long effEndDate;
            public String effEndDateStr;
            public long bindTime;
            public String bindTimeStr;
            public String cardAmount;
            public String cardBalance;
            public String describe;
            public boolean available;
            public List<?> payType;
            public int isAvailable;
            public int selected;
            public int cardstatus;//0-可使用 1-已过期 2-已使用
        }

    }
}
