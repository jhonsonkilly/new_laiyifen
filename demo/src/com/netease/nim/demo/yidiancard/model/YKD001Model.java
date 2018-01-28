package com.netease.nim.demo.yidiancard.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author SevenCheng
 */

public class YKD001Model {

    /**
     * rows : [{"amount":10,"beReced":false,"bindTime":1510126754,"canBeSended":true,"cardNo":"8311000000000240","endTime":1604734754,"intro":"专用定额伊点卡","isBeReced":false,"isUsed":false,"mobile":"15202146557","originAmount":10,"pwd":"209542","reason":"欧电云调接口绑定","startTime":1510126754}]
     * total : 1
     */

    private int            total;
    private String         code;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable {
        /**
         * amount : 10
         * beReced : false
         * bindTime : 1510126754
         * canBeSended : true
         * cardNo : 8311000000000240
         * endTime : 1604734754
         * intro : 专用定额伊点卡
         * isBeReced : false
         * isUsed : false
         * mobile : 15202146557
         * originAmount : 10
         * pwd : 209542
         * reason : 欧电云调接口绑定
         * startTime : 1510126754
         */

        private double amount;
        private boolean isShow = false;
        private boolean beReced;
        private int     bindTime;
        private boolean canBeSended;
        private String  cardNo;
        private long    endTime;
        private String  intro;
        private boolean isBeReced;
        private boolean isUsed;
        private String  mobile;
        private String  statusType;//判断优惠券，伊点卡
        private String  useRange;
        private double  originAmount;
        private String  pwd;
        private String  reason;
        private int     startTime;
        private String  memberId;
        private String  status;
        private String  sendUserId;

        public String getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(String sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getUseRange() {
            return useRange;
        }

        public void setUseRange(String useRange) {
            this.useRange = useRange;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getType() {
            return statusType;
        }

        public void setType(String type) {
            this.statusType = type;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public boolean isUsed() {
            return isUsed;
        }

        public void setUsed(boolean used) {
            isUsed = used;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public boolean isBeReced() {
            return beReced;
        }

        public void setBeReced(boolean beReced) {
            this.beReced = beReced;
        }

        public int getBindTime() {
            return bindTime;
        }

        public void setBindTime(int bindTime) {
            this.bindTime = bindTime;
        }

        public boolean isCanBeSended() {
            return canBeSended;
        }

        public void setCanBeSended(boolean canBeSended) {
            this.canBeSended = canBeSended;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public boolean isIsBeReced() {
            return isBeReced;
        }

        public void setIsBeReced(boolean isBeReced) {
            this.isBeReced = isBeReced;
        }

        public boolean isIsUsed() {
            return isUsed;
        }

        public void setIsUsed(boolean isUsed) {
            this.isUsed = isUsed;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getOriginAmount() {
            return originAmount;
        }

        public void setOriginAmount(double originAmount) {
            this.originAmount = originAmount;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }
    }

    public static class CanUseCouponListBean implements Serializable {
        /**
         * couponId : 106328077
         * couponCode : 106328077
         * themeTitle : 优惠券测试-20170614
         * startTime : 1497369600000
         * startTimeStr : null
         * endTime : 1560441600000
         * endTimeStr : null
         * couponValue : 10.0
         * useLimit : 20.0
         * useUpLimit : 0.0
         * couponDiscountType : 0
         * individualLimit : null
         * userPayLimit : []
         * limitExplain : null
         * refDescription : 整单
         * moneyRule : 满20.00元减10.00元
         * themeType : null
         * merchantId : null
         * merchantName : null
         * pwd : 0027
         * type : 2
         * canShare : 0
         * couponUseType : 1
         * themeDesc : null
         * useRange : 3
         * rebateUserId : null
         */

        private int     couponId;
        private String  couponCode;
        private String  themeTitle;
        private long    startTime;
        private Object  startTimeStr;
        private long    endTime;
        private Object  endTimeStr;
        private double  couponValue;
        private double  useLimit;
        private double  useUpLimit;
        private int     couponDiscountType;
        private Object  individualLimit;
        private Object  limitExplain;
        private String  refDescription;
        private String  moneyRule;
        private Object  themeType;
        private Object  merchantId;
        private Object  merchantName;
        private String  pwd;
        private int     type;
        private int     canShare;
        private int     couponUseType;
        private Object  themeDesc;
        private int     useRange;
        private Object  rebateUserId;
        private List<?> userPayLimit;
        private boolean isShow = false;
        private String status;
        private String statusType;//判断优惠券，伊点卡

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
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

        public Object getStartTimeStr() {
            return startTimeStr;
        }

        public void setStartTimeStr(Object startTimeStr) {
            this.startTimeStr = startTimeStr;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public Object getEndTimeStr() {
            return endTimeStr;
        }

        public void setEndTimeStr(Object endTimeStr) {
            this.endTimeStr = endTimeStr;
        }

        public double getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(double couponValue) {
            this.couponValue = couponValue;
        }

        public double getUseLimit() {
            return useLimit;
        }

        public void setUseLimit(double useLimit) {
            this.useLimit = useLimit;
        }

        public double getUseUpLimit() {
            return useUpLimit;
        }

        public void setUseUpLimit(double useUpLimit) {
            this.useUpLimit = useUpLimit;
        }

        public int getCouponDiscountType() {
            return couponDiscountType;
        }

        public void setCouponDiscountType(int couponDiscountType) {
            this.couponDiscountType = couponDiscountType;
        }

        public Object getIndividualLimit() {
            return individualLimit;
        }

        public void setIndividualLimit(Object individualLimit) {
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

        public Object getThemeType() {
            return themeType;
        }

        public void setThemeType(Object themeType) {
            this.themeType = themeType;
        }

        public Object getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Object merchantId) {
            this.merchantId = merchantId;
        }

        public Object getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(Object merchantName) {
            this.merchantName = merchantName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
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

        public Object getThemeDesc() {
            return themeDesc;
        }

        public void setThemeDesc(Object themeDesc) {
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
