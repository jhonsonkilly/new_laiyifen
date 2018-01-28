package com.ody.p2p.main.order;

import java.util.List;

/**
 * Created by caishengya on 2017/7/3.
 */

public class LyfCouponBean {


    public static class CouponBean {

        public String merchantName;
        public String merchantId;

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public List<Bean> couponlist;

        public List<Bean> getCouponlist() {
            return couponlist;
        }

        public void setCouponlist(List<Bean> couponlist) {
            this.couponlist = couponlist;
        }

        public static class Bean {

            public String getCouponId() {
                return couponId;
            }

            public void setCouponId(String couponId) {
                this.couponId = couponId;
            }

            public String getCouponCode() {
                return couponCode;
            }

            public void setCouponCode(String couponCode) {
                this.couponCode = couponCode;
            }

            public String getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(String startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public String getEndTimeStr() {
                return endTimeStr;
            }

            public void setEndTimeStr(String endTimeStr) {
                this.endTimeStr = endTimeStr;
            }

            public int getSelected() {
                return selected;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }

            public int getIsAvailable() {
                return isAvailable;
            }

            public void setIsAvailable(int isAvailable) {
                this.isAvailable = isAvailable;
            }

            public String getUnavailableReason() {
                return unavailableReason;
            }

            public void setUnavailableReason(String unavailableReason) {
                this.unavailableReason = unavailableReason;
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

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(String couponValue) {
                this.couponValue = couponValue;
            }

            public String getUseLimit() {
                return useLimit;
            }

            public void setUseLimit(String useLimit) {
                this.useLimit = useLimit;
            }

            public int getIndividualLimit() {
                return individualLimit;
            }

            public void setIndividualLimit(int individualLimit) {
                this.individualLimit = individualLimit;
            }

            public List<Integer> getUserPayLimit() {
                return userPayLimit;
            }

            public void setUserPayLimit(List<Integer> userPayLimit) {
                this.userPayLimit = userPayLimit;
            }

            public String getLimitExplain() {
                return limitExplain;
            }

            public void setLimitExplain(String limitExplain) {
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
            public int individualLimit;
            public List<Integer> userPayLimit;
            public String limitExplain;
            public String refDescription;
            public String moneyRule;
        }
    }

}
