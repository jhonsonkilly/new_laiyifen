package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.Date;
import java.util.List;

/**
 * Created by ody on 2016/7/6.
 */
public class PromotionBean extends BaseRequestBean {


    public Data data;

    public static class Data {
        /**
         * mpId : 1682
         * iconText : 满减
         * iconUrl : http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png
         * promotions : [{"id":null,"description":"满100.0元减30.0元","promotionId":null,"promotionType":null,"url":null,"iconText":"满减","iconUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png","startTime":1471956548000,"endTime":1471956548000,"promotionRuleList":[{"promotionId":1,"conditionType":1,"conditionValue":1,"contentType":1,"contentValue":1,"description":"1","merchantId":1,"merchantName":"1","level":1,"iconUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png"}],"promotionGiftDetailList":[{"promotionId":1,"level":1,"description":"dd","specificDescription":"ss","giftType":1,"promotionRuleId":1,"conditionType":1,"conditionValue":1,"contentValue":"1","singleGiftInfoVOList":[{"promotionId":1,"promotionRuleId":1,"merchantId":1,"mpId":1,"giftName":"1","giftNum":1,"price":1,"picUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png","explain":"满赠"}]}]}]
         */

        public List<PromotionInfo> promotionInfo;

        public static class PromotionInfo {


            public long mpId;
            public String iconText;
            public String iconUrl;
            /**
             * id : null
             * description : 满100.0元减30.0元
             * promotionId : null
             * promotionType : null
             * url : null
             * iconText : 满减
             * iconUrl : http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png
             * startTime : 1471956548000
             * endTime : 1471956548000
             * promotionRuleList : [{"promotionId":1,"conditionType":1,"conditionValue":1,"contentType":1,"contentValue":1,"description":"1","merchantId":1,"merchantName":"1","level":1,"iconUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png"}]
             * promotionGiftDetailList : [{"promotionId":1,"level":1,"description":"dd","specificDescription":"ss","giftType":1,"promotionRuleId":1,"conditionType":1,"conditionValue":1,"contentValue":"1","singleGiftInfoVOList":[{"promotionId":1,"promotionRuleId":1,"merchantId":1,"mpId":1,"giftName":"1","giftNum":1,"price":1,"picUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png","explain":"满赠"}]}]
             */


            public List<Promotions> promotions;

            public static class Promotions {
                public long id;
                public String description;
                public long promotionId;
                public int promotionType;
                public int contentType;
                public String url;
                public String iconText;
                public String iconUrl;
                public String startTime;
                public String endTime;
                public int isJumpPage;
                public String jumpPageUrl;

                public String getJumpPageUrl() {
                    return jumpPageUrl;
                }

                public void setJumpPageUrl(String jumpPageUrl) {
                    this.jumpPageUrl = jumpPageUrl;
                }

                public int getIsJumpPage() {
                    return isJumpPage;
                }

                public void setIsJumpPage(int isJumpPage) {
                    this.isJumpPage = isJumpPage;
                }

                /**
                 * promotionId : 1
                 * conditionType : 1
                 * conditionValue : 1
                 * contentType : 1
                 * contentValue : 1
                 * description : 1
                 * merchantId : 1
                 * merchantName : 1
                 * level : 1
                 * iconUrl : http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png
                 */

                public List<PromotionRuleList> promotionRuleList;
                /**
                 * promotionId : 1
                 * level : 1
                 * description : dd
                 * specificDescription : ss
                 * giftType : 1
                 * promotionRuleId : 1
                 * conditionType : 1
                 * conditionValue : 1
                 * contentValue : 1
                 * singleGiftInfoVOList : [{"promotionId":1,"promotionRuleId":1,"merchantId":1,"mpId":1,"giftName":"1","giftNum":1,"price":1,"picUrl":"http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png","explain":"满赠"}]
                 */

                public List<PromotionGiftDetailList> promotionGiftDetailList;


                public static class PromotionRuleList {
                    public long promotionId;
                    public int conditionType;
                    public long conditionValue;
                    public int contentType;
                    public String contentValue;
                    public String description;
                    public long merchantId;
                    public String merchantName;
                    public int level;
                    public String iconUrl;

                    public long getPromotionId() {
                        return promotionId;
                    }

                    public void setPromotionId(long promotionId) {
                        this.promotionId = promotionId;
                    }

                    public int getConditionType() {
                        return conditionType;
                    }

                    public void setConditionType(int conditionType) {
                        this.conditionType = conditionType;
                    }

                    public long getConditionValue() {
                        return conditionValue;
                    }

                    public void setConditionValue(long conditionValue) {
                        this.conditionValue = conditionValue;
                    }

                    public int getContentType() {
                        return contentType;
                    }

                    public void setContentType(int contentType) {
                        this.contentType = contentType;
                    }

                    public String getContentValue() {
                        return contentValue;
                    }

                    public void setContentValue(String contentValue) {
                        this.contentValue = contentValue;
                    }

                    public long getMerchantId() {
                        return merchantId;
                    }

                    public void setMerchantId(long merchantId) {
                        this.merchantId = merchantId;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getMerchantName() {
                        return merchantName;
                    }

                    public void setMerchantName(String merchantName) {
                        this.merchantName = merchantName;
                    }


                    public String getIconUrl() {
                        return iconUrl;
                    }

                    public void setIconUrl(String iconUrl) {
                        this.iconUrl = iconUrl;
                    }
                }

                public static class PromotionGiftDetailList {
                    public long promotionId;
                    public int level;
                    public String description;
                    public String specificDescription;
                    public int giftType;
                    public String promotionRuleId;
                    public int conditionType;
                    public int contentType;
                    public String conditionValue;
                    public String contentValue;

                    public List<singleCouponInfoList> singleCouponInfoList;

                    public static class singleCouponInfoList {
                        public long id;
                        public String themeTitle;
                        public double useLimit;
                        public int couponDiscountType;
                        public int couponDiscount;
                        public String amountRule;
                        public double couponAmount;
                        public String soldOut;

                        public long getId() {
                            return id;
                        }

                        public void setId(long id) {
                            this.id = id;
                        }

                        public String getThemeTitle() {
                            return themeTitle;
                        }

                        public void setThemeTitle(String themeTitle) {
                            this.themeTitle = themeTitle;
                        }

                        public double getUseLimit() {
                            return useLimit;
                        }

                        public void setUseLimit(double useLimit) {
                            this.useLimit = useLimit;
                        }

                        public int getCouponDiscountType() {
                            return couponDiscountType;
                        }

                        public void setCouponDiscountType(int couponDiscountType) {
                            this.couponDiscountType = couponDiscountType;
                        }

                        public int getCouponDiscount() {
                            return couponDiscount;
                        }

                        public void setCouponDiscount(int couponDiscount) {
                            this.couponDiscount = couponDiscount;
                        }

                        public String getAmountRule() {
                            return amountRule;
                        }

                        public void setAmountRule(String amountRule) {
                            this.amountRule = amountRule;
                        }

                        public double getCouponAmount() {
                            return couponAmount;
                        }

                        public void setCouponAmount(double couponAmount) {
                            this.couponAmount = couponAmount;
                        }

                        public String getSoldOut() {
                            return soldOut;
                        }

                        public void setSoldOut(String soldOut) {
                            this.soldOut = soldOut;
                        }
                    }

                    /**
                     * promotionId : 1
                     * promotionRuleId : 1
                     * merchantId : 1
                     * mpId : 1
                     * giftName : 1
                     * giftNum : 1
                     * price : 1
                     * picUrl : http://ody-open.kssws.ks-cdn.com/yh/%E5%B0%8F%E5%9B%BE/manjian_icon.png
                     * explain : 满赠
                     */

                    public List<SingleGiftInfoVOList> singleGiftInfoList;

                    public List<PromotionGiftDetailList.singleCouponInfoList> getSingleCouponInfoList() {
                        return singleCouponInfoList;
                    }

                    public void setSingleCouponInfoList(List<PromotionGiftDetailList.singleCouponInfoList> singleCouponInfoList) {
                        this.singleCouponInfoList = singleCouponInfoList;
                    }

                    public int getContentType() {
                        return contentType;
                    }

                    public void setContentType(int contentType) {
                        this.contentType = contentType;
                    }

                    public static class SingleGiftInfoVOList {
                        public long promotionId;
                        public long promotionRuleId;
                        public long merchantId;
                        public long mpId;
                        public String giftName;
                        public String giftNum;
                        public double price;
                        public String picUrl;
                        public String explain;
                        public int soldOut;

                        public long getPromotionId() {
                            return promotionId;
                        }

                        public void setPromotionId(long promotionId) {
                            this.promotionId = promotionId;
                        }

                        public long getPromotionRuleId() {
                            return promotionRuleId;
                        }

                        public void setPromotionRuleId(long promotionRuleId) {
                            this.promotionRuleId = promotionRuleId;
                        }

                        public long getMerchantId() {
                            return merchantId;
                        }

                        public void setMerchantId(long merchantId) {
                            this.merchantId = merchantId;
                        }

                        public long getMpId() {
                            return mpId;
                        }

                        public void setMpId(long mpId) {
                            this.mpId = mpId;
                        }

                        public void setPrice(double price) {
                            this.price = price;
                        }

                        public void setSoldOut(int soldOut) {
                            this.soldOut = soldOut;
                        }

                        public String getGiftName() {
                            return giftName;
                        }

                        public void setGiftName(String giftName) {
                            this.giftName = giftName;
                        }

                        public String getGiftNum() {
                            return giftNum;
                        }

                        public void setGiftNum(String giftNum) {
                            this.giftNum = giftNum;
                        }

                        public double getPrice() {
                            return price;
                        }

                        public String getPicUrl() {
                            return picUrl;
                        }

                        public int getSoldOut() {
                            return soldOut;
                        }

                        public void setPicUrl(String picUrl) {
                            this.picUrl = picUrl;
                        }

                        public String getExplain() {
                            return explain;
                        }

                        public void setExplain(String explain) {
                            this.explain = explain;
                        }
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getSpecificDescription() {
                        return specificDescription;
                    }

                    public void setSpecificDescription(String specificDescription) {
                        this.specificDescription = specificDescription;
                    }

                    public int getGiftType() {
                        return giftType;
                    }

                    public void setGiftType(int giftType) {
                        this.giftType = giftType;
                    }


                    public String getContentValue() {
                        return contentValue;
                    }

                    public void setContentValue(String contentValue) {
                        this.contentValue = contentValue;
                    }

                    public List<SingleGiftInfoVOList> getSingleGiftInfoList() {
                        return singleGiftInfoList;
                    }

                    public void setSingleGiftInfoList(List<SingleGiftInfoVOList> singleGiftInfoList) {
                        this.singleGiftInfoList = singleGiftInfoList;
                    }
                }

                public Object getId() {
                    return id;
                }


                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public Object getPromotionId() {
                    return promotionId;
                }


                public int getPromotionType() {
                    return promotionType;
                }


                public Object getUrl() {
                    return url;
                }


                public String getIconText() {
                    return iconText;
                }

                public void setIconText(String iconText) {
                    this.iconText = iconText;
                }

                public String getIconUrl() {
                    return iconUrl;
                }


                public void setPromotionType(int promotionType) {
                    this.promotionType = promotionType;
                }

                public int getContentType() {
                    return contentType;
                }

                public void setContentType(int contentType) {
                    this.contentType = contentType;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public List<PromotionRuleList> getPromotionRuleList() {
                    return promotionRuleList;
                }

                public void setPromotionRuleList(List<PromotionRuleList> promotionRuleList) {
                    this.promotionRuleList = promotionRuleList;
                }

                public List<PromotionGiftDetailList> getPromotionGiftDetailList() {
                    return promotionGiftDetailList;
                }

                public void setPromotionGiftDetailList(List<PromotionGiftDetailList> promotionGiftDetailList) {
                    this.promotionGiftDetailList = promotionGiftDetailList;
                }
            }


            public String getIconText() {
                return iconText;
            }

            public void setIconText(String iconText) {
                this.iconText = iconText;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public List<Promotions> getPromotions() {
                return promotions;
            }

            public void setPromotions(List<Promotions> promotions) {
                this.promotions = promotions;
            }

            public long getMpId() {
                return mpId;
            }

            public void setMpId(long mpId) {
                this.mpId = mpId;
            }
        }

        public List<PromotionInfo> getPromotionInfo() {
            return promotionInfo;
        }

        public void setPromotionInfo(List<PromotionInfo> promotionInfo) {
            this.promotionInfo = promotionInfo;
        }

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
