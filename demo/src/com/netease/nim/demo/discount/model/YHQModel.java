package com.netease.nim.demo.discount.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author SevenCheng
 */

public class YHQModel implements Serializable{


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"canUseCouponList":[{"couponId":1005048101000044,"couponCode":"D3200B3813CA4A69","themeTitle":"zzz优惠券","startTime":1506837816000,"startTimeStr":"2017/10/01","endTime":1540965818000,"endTimeStr":"2018/10/31","couponValue":100,"useLimit":0.01,"useUpLimit":null,"couponDiscountType":0,"individualLimit":10,"userPayLimit":[],"limitExplain":null,"refDescription":"每个帐号限用数量:10\n","moneyRule":"满0.01减100.00","themeType":11,"merchantId":1011039500000122,"merchantName":"zzz","pwd":null,"type":1,"canShare":0,"couponUseType":1,"themeDesc":"100","useRange":1,"rebateUserId":null},{"couponId":106328077,"couponCode":"106328077","themeTitle":"优惠券测试-20170614","startTime":1497369600000,"startTimeStr":null,"endTime":1560441600000,"endTimeStr":null,"couponValue":10,"useLimit":20,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满20.00元减10.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"0027","type":2,"canShare":0,"couponUseType":1,"themeDesc":null,"useRange":3,"rebateUserId":null},{"couponId":276729310,"couponCode":"276729310","themeTitle":"线下优惠券测试-20171121","startTime":1509465600000,"startTimeStr":null,"endTime":1525017600000,"endTimeStr":null,"couponValue":30,"useLimit":100,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满100.00元减30.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"9410","type":2,"canShare":0,"couponUseType":3,"themeDesc":null,"useRange":2,"rebateUserId":null},{"couponId":276729311,"couponCode":"276729311","themeTitle":"线下优惠券测试-20171121","startTime":1509465600000,"startTimeStr":null,"endTime":1525017600000,"endTimeStr":null,"couponValue":30,"useLimit":100,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满100.00元减30.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"5859","type":2,"canShare":0,"couponUseType":3,"themeDesc":null,"useRange":2,"rebateUserId":null}],"canUserCount":4,"inactiveCouponList":[],"inactiveCount":0,"expiredCouponList":[],"expiredCount":0,"usedCouponList":[],"usedCount":0,"shareCouponList":[],"sharedCount":0,"count":4}
     * trace : 34!$9#@2%&10!$,197461,63397606962081260964053
     */

    private String code;
    private String message;
    private Object errMsg;
    private DataBean data;
    private String trace;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public static class DataBean {
        /**
         * canUseCouponList : [{"couponId":1005048101000044,"couponCode":"D3200B3813CA4A69","themeTitle":"zzz优惠券","startTime":1506837816000,"startTimeStr":"2017/10/01","endTime":1540965818000,"endTimeStr":"2018/10/31","couponValue":100,"useLimit":0.01,"useUpLimit":null,"couponDiscountType":0,"individualLimit":10,"userPayLimit":[],"limitExplain":null,"refDescription":"每个帐号限用数量:10\n","moneyRule":"满0.01减100.00","themeType":11,"merchantId":1011039500000122,"merchantName":"zzz","pwd":null,"type":1,"canShare":0,"couponUseType":1,"themeDesc":"100","useRange":1,"rebateUserId":null},{"couponId":106328077,"couponCode":"106328077","themeTitle":"优惠券测试-20170614","startTime":1497369600000,"startTimeStr":null,"endTime":1560441600000,"endTimeStr":null,"couponValue":10,"useLimit":20,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满20.00元减10.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"0027","type":2,"canShare":0,"couponUseType":1,"themeDesc":null,"useRange":3,"rebateUserId":null},{"couponId":276729310,"couponCode":"276729310","themeTitle":"线下优惠券测试-20171121","startTime":1509465600000,"startTimeStr":null,"endTime":1525017600000,"endTimeStr":null,"couponValue":30,"useLimit":100,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满100.00元减30.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"9410","type":2,"canShare":0,"couponUseType":3,"themeDesc":null,"useRange":2,"rebateUserId":null},{"couponId":276729311,"couponCode":"276729311","themeTitle":"线下优惠券测试-20171121","startTime":1509465600000,"startTimeStr":null,"endTime":1525017600000,"endTimeStr":null,"couponValue":30,"useLimit":100,"useUpLimit":0,"couponDiscountType":0,"individualLimit":null,"userPayLimit":[],"limitExplain":null,"refDescription":"整单","moneyRule":"满100.00元减30.00元","themeType":null,"merchantId":null,"merchantName":null,"pwd":"5859","type":2,"canShare":0,"couponUseType":3,"themeDesc":null,"useRange":2,"rebateUserId":null}]
         * canUserCount : 4
         * inactiveCouponList : []
         * inactiveCount : 0
         * expiredCouponList : []
         * expiredCount : 0
         * usedCouponList : []
         * usedCount : 0
         * shareCouponList : []
         * sharedCount : 0
         * count : 4
         */

        private int canUserCount;
        private int inactiveCount;
        private int expiredCount;
        private int usedCount;
        private int sharedCount;
        private int count;
        private List<CanUseCouponListBean> canUseCouponList;
        private List<?> inactiveCouponList;
        private List<?> expiredCouponList;
        private List<?> usedCouponList;
        private List<?> shareCouponList;

        public int getCanUserCount() {
            return canUserCount;
        }

        public void setCanUserCount(int canUserCount) {
            this.canUserCount = canUserCount;
        }

        public int getInactiveCount() {
            return inactiveCount;
        }

        public void setInactiveCount(int inactiveCount) {
            this.inactiveCount = inactiveCount;
        }

        public int getExpiredCount() {
            return expiredCount;
        }

        public void setExpiredCount(int expiredCount) {
            this.expiredCount = expiredCount;
        }

        public int getUsedCount() {
            return usedCount;
        }

        public void setUsedCount(int usedCount) {
            this.usedCount = usedCount;
        }

        public int getSharedCount() {
            return sharedCount;
        }

        public void setSharedCount(int sharedCount) {
            this.sharedCount = sharedCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<CanUseCouponListBean> getCanUseCouponList() {
            return canUseCouponList;
        }

        public void setCanUseCouponList(List<CanUseCouponListBean> canUseCouponList) {
            this.canUseCouponList = canUseCouponList;
        }

        public List<?> getInactiveCouponList() {
            return inactiveCouponList;
        }

        public void setInactiveCouponList(List<?> inactiveCouponList) {
            this.inactiveCouponList = inactiveCouponList;
        }

        public List<?> getExpiredCouponList() {
            return expiredCouponList;
        }

        public void setExpiredCouponList(List<?> expiredCouponList) {
            this.expiredCouponList = expiredCouponList;
        }

        public List<?> getUsedCouponList() {
            return usedCouponList;
        }

        public void setUsedCouponList(List<?> usedCouponList) {
            this.usedCouponList = usedCouponList;
        }

        public List<?> getShareCouponList() {
            return shareCouponList;
        }

        public void setShareCouponList(List<?> shareCouponList) {
            this.shareCouponList = shareCouponList;
        }

        public static class CanUseCouponListBean implements Serializable{
            /**
             * couponId : 1005048101000044
             * couponCode : D3200B3813CA4A69
             * themeTitle : zzz优惠券
             * startTime : 1506837816000
             * startTimeStr : 2017/10/01
             * endTime : 1540965818000
             * endTimeStr : 2018/10/31
             * couponValue : 100
             * useLimit : 0.01
             * useUpLimit : null
             * couponDiscountType : 0
             * individualLimit : 10
             * userPayLimit : []
             * limitExplain : null
             * refDescription : 每个帐号限用数量:10

             * moneyRule : 满0.01减100.00
             * themeType : 11
             * merchantId : 1011039500000122
             * merchantName : zzz
             * pwd : null
             * type : 1
             * canShare : 0
             * couponUseType : 1
             * themeDesc : 100
             * useRange : 1
             * rebateUserId : null
             */

            private long couponId;
            private String couponCode;
            private String themeTitle;
            private long startTime;
            private String startTimeStr;
            private long endTime;
            private String endTimeStr;
            private int couponValue;
            private double useLimit;
            private Object useUpLimit;
            private int couponDiscountType;
            private int individualLimit;
            private Object limitExplain;
            private String refDescription;
            private String moneyRule;
            private int themeType;
            private long merchantId;
            private String merchantName;
            private Object pwd;
            private int type;
            private int canShare;
            private int couponUseType;
            private String themeDesc;
            private int useRange;
            private Object rebateUserId;
            private List<?> userPayLimit;
            private boolean isShow = false;
            private String status;
            private String  statusType;//判断优惠券，伊点卡
            private String memberId;
            private int amount;
            private String couponReceiveId;
            private String iconUrl;
            private String sendUserId;//发送人

            public String getSendUserId() {
                return sendUserId;
            }

            public void setSendUserId(String sendUserId) {
                this.sendUserId = sendUserId;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getCouponReceiveId() {
                return couponReceiveId;
            }

            public void setCouponReceiveId(String couponReceiveId) {
                this.couponReceiveId = couponReceiveId;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusType() {
                return statusType;
            }

            public void setStatusType(String statusType) {
                this.statusType = statusType;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getCouponId() {
                return couponId;
            }

            public void setCouponId(long couponId) {
                this.couponId = couponId;
            }

            public String getCouponCode() {
                return couponCode;
            }

            public void setCouponCode(String couponCode) {
                this.couponCode = couponCode;
            }

            public String getThemeTitle() {
                return themeTitle;
            }

            public void setThemeTitle(String themeTitle) {
                this.themeTitle = themeTitle;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public String getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(String startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getEndTimeStr() {
                return endTimeStr;
            }

            public void setEndTimeStr(String endTimeStr) {
                this.endTimeStr = endTimeStr;
            }

            public int getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(int couponValue) {
                this.couponValue = couponValue;
            }

            public double getUseLimit() {
                return useLimit;
            }

            public void setUseLimit(double useLimit) {
                this.useLimit = useLimit;
            }

            public Object getUseUpLimit() {
                return useUpLimit;
            }

            public void setUseUpLimit(Object useUpLimit) {
                this.useUpLimit = useUpLimit;
            }

            public int getCouponDiscountType() {
                return couponDiscountType;
            }

            public void setCouponDiscountType(int couponDiscountType) {
                this.couponDiscountType = couponDiscountType;
            }

            public int getIndividualLimit() {
                return individualLimit;
            }

            public void setIndividualLimit(int individualLimit) {
                this.individualLimit = individualLimit;
            }

            public Object getLimitExplain() {
                return limitExplain;
            }

            public void setLimitExplain(Object limitExplain) {
                this.limitExplain = limitExplain;
            }

            public String getRefDescription() {
                return refDescription;
            }

            public void setRefDescription(String refDescription) {
                this.refDescription = refDescription;
            }

            public String getMoneyRule() {
                return moneyRule;
            }

            public void setMoneyRule(String moneyRule) {
                this.moneyRule = moneyRule;
            }

            public int getThemeType() {
                return themeType;
            }

            public void setThemeType(int themeType) {
                this.themeType = themeType;
            }

            public long getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(long merchantId) {
                this.merchantId = merchantId;
            }

            public String getMerchantName() {
                return merchantName;
            }

            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }

            public Object getPwd() {
                return pwd;
            }

            public void setPwd(Object pwd) {
                this.pwd = pwd;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getCanShare() {
                return canShare;
            }

            public void setCanShare(int canShare) {
                this.canShare = canShare;
            }

            public int getCouponUseType() {
                return couponUseType;
            }

            public void setCouponUseType(int couponUseType) {
                this.couponUseType = couponUseType;
            }

            public String getThemeDesc() {
                return themeDesc;
            }

            public void setThemeDesc(String themeDesc) {
                this.themeDesc = themeDesc;
            }

            public int getUseRange() {
                return useRange;
            }

            public void setUseRange(int useRange) {
                this.useRange = useRange;
            }

            public Object getRebateUserId() {
                return rebateUserId;
            }

            public void setRebateUserId(Object rebateUserId) {
                this.rebateUserId = rebateUserId;
            }

            public List<?> getUserPayLimit() {
                return userPayLimit;
            }

            public void setUserPayLimit(List<?> userPayLimit) {
                this.userPayLimit = userPayLimit;
            }
        }
    }
}
