package com.ody.p2p.shopcart;


import com.ody.p2p.PromotionInfo;
import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class ShopCartBean extends BaseRequestBean {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private List<MerchantList> merchantList;

        private List<ProductList> failureProducts;

        private Summary summary;

        public void setMerchantList(List<MerchantList> merchantList) {
            this.merchantList = merchantList;
        }

        public List<MerchantList> getMerchantList() {
            return this.merchantList;
        }

        public void setFailureProducts(List<ProductList> failureProducts) {
            this.failureProducts = failureProducts;
        }

        public List<ProductList> getFailureProducts() {
            return this.failureProducts;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public Summary getSummary() {
            return this.summary;
        }

        public int itemNum;//该商品剩余总数
        public int itemAmount;//该商品总价格

    }

    public class MerchantList {
        private String merchantId;

        private String merchantName;

        private List<ProductList> productList;

        private Summary summary;

        private List<Overseas> overseas;

        private List<ProductGroups> productGroups;

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantId() {
            return this.merchantId;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantName() {
            return this.merchantName;
        }

        public void setProductList(List<ProductList> productList) {
            this.productList = productList;
        }

        public List<ProductList> getProductList() {
            return this.productList;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public Summary getSummary() {
            return this.summary;
        }

        public void setOverseas(List<Overseas> overseas) {
            this.overseas = overseas;
        }

        public List<Overseas> getOverseas() {
            return this.overseas;
        }

        public void setProductGroups(List<ProductGroups> productGroups) {
            this.productGroups = productGroups;
        }

        public List<ProductGroups> getProductGroups() {
            return this.productGroups;
        }

    }

    public class Overseas {
        private String overseaId;

        private String overseaName;

        private int isSeparatePayment;

        private double promotionDiscount;

        private double amount;

        private int checkedNum;

        private List<ProductGroups> productGroups;

        public void setOverseaId(String overseaId) {
            this.overseaId = overseaId;
        }

        public String getOverseaId() {
            return this.overseaId;
        }

        public void setOverseaName(String overseaName) {
            this.overseaName = overseaName;
        }

        public String getOverseaName() {
            return this.overseaName;
        }

        public void setIsSeparatePayment(int isSeparatePayment) {
            this.isSeparatePayment = isSeparatePayment;
        }

        public int getIsSeparatePayment() {
            return this.isSeparatePayment;
        }

        public void setPromotionDiscount(double promotionDiscount) {
            this.promotionDiscount = promotionDiscount;
        }

        public double getPromotionDiscount() {
            return this.promotionDiscount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getAmount() {
            return this.amount;
        }

        public void setCheckedNum(int checkedNum) {
            this.checkedNum = checkedNum;
        }

        public int getCheckedNum() {
            return this.checkedNum;
        }

        public void setProductGroups(List<ProductGroups> productGroups) {
            this.productGroups = productGroups;
        }

        public List<ProductGroups> getProductGroups() {
            return this.productGroups;
        }

    }

    public class ProductGroups {
        private Promotion promotion;

        private Summary summary;

        private List<ProductList> productList;

        private List<GiftProductList> giftProductList;

        public void setPromotion(Promotion promotion) {
            this.promotion = promotion;
        }

        public Promotion getPromotion() {
            return this.promotion;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public Summary getSummary() {
            return this.summary;
        }

        public void setProductList(List<ProductList> productList) {
            this.productList = productList;
        }

        public List<ProductList> getProductList() {
            return this.productList;
        }

        public void setGiftProductList(List<GiftProductList> giftProductList) {
            this.giftProductList = giftProductList;
        }

        public List<GiftProductList> getGiftProductList() {
            return this.giftProductList;
        }

    }

    public class GiftProductList {
        private int canSelectedGiftsNum;

        private int giveGiftCount;

        private List<GiftProducts> giftProducts;

        private List<String> mainMpIdList;

        public int getCanSelectedGiftsNum() {
            return canSelectedGiftsNum;
        }

        public void setCanSelectedGiftsNum(int canSelectedGiftsNum) {
            this.canSelectedGiftsNum = canSelectedGiftsNum;
        }

        public void setGiveGiftCount(int giveGiftCount) {
            this.giveGiftCount = giveGiftCount;
        }

        public int getGiveGiftCount() {
            return this.giveGiftCount;
        }

        public void setGiftProducts(List<GiftProducts> giftProducts) {
            this.giftProducts = giftProducts;
        }

        public List<GiftProducts> getGiftProducts() {
            return this.giftProducts;
        }

        public void setMainMpIdList(List<String> mainMpIdList) {
            this.mainMpIdList = mainMpIdList;
        }

        public List<String> getMainMpIdList() {
            return this.mainMpIdList;
        }

    }

    public class GiftProducts {
        private long promotionId;

        private String merchantId;

        private long mpId;

        private String giftName;

        private String picUrl;

        private String weight;

        private double price;

        private int checked;

        private int canSaleNum;

        private int checkNum;

        List<PropertyTags> propertyTags;

        public List<PropertyTags> getPropertyTags() {
            return propertyTags;
        }

        public void setPropertyTags(List<PropertyTags> propertyTags) {
            this.propertyTags = propertyTags;
        }

        public void setPromotionId(long promotionId) {
            this.promotionId = promotionId;
        }

        public long getPromotionId() {
            return this.promotionId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantId() {
            return this.merchantId;
        }

        public long getMpId() {
            return mpId;
        }

        public void setMpId(long mpId) {
            this.mpId = mpId;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getGiftName() {
            return this.giftName;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPicUrl() {
            return this.picUrl;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getWeight() {
            return this.weight;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return this.price;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public int getChecked() {
            return this.checked;
        }

        public void setCanSaleNum(int canSaleNum) {
            this.canSaleNum = canSaleNum;
        }

        public int getCanSaleNum() {
            return this.canSaleNum;
        }

        public void setCheckNum(int checkNum) {
            this.checkNum = checkNum;
        }

        public int getCheckNum() {
            return this.checkNum;
        }

    }

    public class ProductList {
        private String mpId;
        private String seriesParentId;
        private String code;
        private String tipsMsg;
        private int itemType;//商品类型,普通商品:0,奖品:3,赠品:4,换购:5。不传默认为0
        private int tipsType; //1总量超限、2个人超限
        private String itemTxt;//商品类型描述
        private String itemIconUrl;//商品类型的图标
        private String objectId;//itemType对应的活动或促销id


        private String productId;

        private String merchantId;

        private String name;

        private String picUrl;

        private int num;

        private double weight;

        private double price;

        private int stockNum;

        private int checked;
        private List<PromotionInfo> promotions;

        private List<PropertyTags> propertyTags;

        private int lackOfStock;

        private String disabledReason;
        private int disabled;

        private double preferentialPrice;
        private double promotionPrice;
        private String promotionIconUrl;

        private int saleType;

        private String saleIconUrl;



        private boolean gift;

        private String url60x60;

        private String url100x100;

        private String url120x120;

        private String url160x160;

        private String url220x220;

        private String url500x500;

        private String url800x800;

        public String getDisabledReason() {
            return disabledReason;
        }

        public void setDisabledReason(String disabledReason) {
            this.disabledReason = disabledReason;
        }

        public int getTipsType() {
            return tipsType;
        }

        public ProductList setTipsType(int tipsType) {
            this.tipsType = tipsType;
            return this;
        }

        public String getItemTxt() {
            return itemTxt;
        }

        public void setItemTxt(String itemTxt) {
            this.itemTxt = itemTxt;
        }

        public String getItemIconUrl() {
            return itemIconUrl;
        }

        public void setItemIconUrl(String itemIconUrl) {
            this.itemIconUrl = itemIconUrl;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getCode() {
            return code;
        }

        public String getSaleIconUrl() {
            return saleIconUrl;
        }

        public void setSaleIconUrl(String saleIconUrl) {
            this.saleIconUrl = saleIconUrl;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isGift() {
            return gift;
        }

        public List<PromotionInfo> getPromotions() {
            return promotions;
        }

        public void setPromotions(List<PromotionInfo> promotions) {
            this.promotions = promotions;
        }

        public List<PropertyTags> getPropertyTags() {
            return propertyTags;
        }

        public void setPropertyTags(List<PropertyTags> propertyTags) {
            this.propertyTags = propertyTags;
        }

        public String getTipsMsg() {
            return tipsMsg;
        }

        public void setTipsMsg(String tipsMsg) {
            this.tipsMsg = tipsMsg;
        }

        public String getPromotionIconUrl() {
            return promotionIconUrl;
        }

        public void setPromotionIconUrl(String promotionIconUrl) {
            this.promotionIconUrl = promotionIconUrl;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public void setMpId(String mpId) {
            this.mpId = mpId;
        }

        public String getMpId() {
            return this.mpId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getSeriesParentId() {
            return seriesParentId;
        }

        public void setSeriesParentId(String seriesParentId) {
            this.seriesParentId = seriesParentId;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantId() {
            return this.merchantId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPicUrl() {
            return this.picUrl;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int  getNum() {
            return this.num;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getWeight() {
            return this.weight;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return this.price;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getStockNum() {
            return this.stockNum;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public void setLackOfStock(int lackOfStock) {
            this.lackOfStock = lackOfStock;
        }

        public int getChecked() {
            return this.checked;
        }

        public int getLackOfStock() {
            return this.lackOfStock;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public int getDisabled() {
            return this.disabled;
        }

        public void setPreferentialPrice(double preferentialPrice) {
            this.preferentialPrice = preferentialPrice;
        }

        public double getPreferentialPrice() {
            return this.preferentialPrice;
        }

        public void setSaleType(int saleType) {
            this.saleType = saleType;
        }

        public int getSaleType() {
            return this.saleType;
        }

        public void setGift(boolean gift) {
            this.gift = gift;
        }

        public boolean getGift() {
            return this.gift;
        }

        public void setUrl60x60(String url60x60) {
            this.url60x60 = url60x60;
        }

        public String getUrl60x60() {
            return this.url60x60;
        }

        public void setUrl100x100(String url100x100) {
            this.url100x100 = url100x100;
        }

        public String getUrl100x100() {
            return this.url100x100;
        }

        public void setUrl120x120(String url120x120) {
            this.url120x120 = url120x120;
        }

        public String getUrl120x120() {
            return this.url120x120;
        }

        public void setUrl160x160(String url160x160) {
            this.url160x160 = url160x160;
        }

        public String getUrl160x160() {
            return this.url160x160;
        }

        public void setUrl220x220(String url220x220) {
            this.url220x220 = url220x220;
        }

        public String getUrl220x220() {
            return this.url220x220;
        }

        public void setUrl500x500(String url500x500) {
            this.url500x500 = url500x500;
        }

        public String getUrl500x500() {
            return this.url500x500;
        }

        public void setUrl800x800(String url800x800) {
            this.url800x800 = url800x800;
        }

        public String getUrl800x800() {
            return this.url800x800;
        }

    }

    public static class PropertyTags {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public class Promotion {
        private long promotionId;

        private boolean flag;
        private int isReachCondition;
        private int promotionType;
        private List<String> mainMpIds;
        private String displayName;
        private String promIconText;
        private long promotionRuleId;
        private String promIconUrl;

        public int getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(int promotionType) {
            this.promotionType = promotionType;
        }

        public int getIsReachCondition() {
            return isReachCondition;
        }

        public void setIsReachCondition(int isReachCondition) {
            this.isReachCondition = isReachCondition;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public long getPromotionRuleId() {
            return promotionRuleId;
        }

        public void setPromotionRuleId(long promotionRuleId) {
            this.promotionRuleId = promotionRuleId;
        }

        /**
         * @return the displayName
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * @param displayName the displayName to set
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public void setPromotionId(long promotionId) {
            this.promotionId = promotionId;
        }

        public long getPromotionId() {
            return this.promotionId;
        }

        public void setMainMpIds(List<String> mainMpIds) {
            this.mainMpIds = mainMpIds;
        }

        public List<String> getMainMpIds() {
            return this.mainMpIds;
        }

        public void setPromIconText(String promIconText) {
            this.promIconText = promIconText;
        }

        public String getPromIconText() {
            return this.promIconText;
        }

        public void setPromIconUrl(String promIconUrl) {
            this.promIconUrl = promIconUrl;
        }

        public String getPromIconUrl() {
            return this.promIconUrl;
        }

    }

    public class Summary {
        private double amount;

        private double discount;

        private double beforeAmount;

        private int totalNum;

        private double amountAll;

        private int checkAndUncheckNum;

        private double totalDeliveryFee;

        private double vipSavedAmount;
        private double vipDiscount;

        public double getVipDiscount() {
            return vipDiscount;
        }

        public void setVipDiscount(double vipDiscount) {
            this.vipDiscount = vipDiscount;
        }

        public double getVipSavedAmount() {
            return vipSavedAmount;
        }

        public void setVipSavedAmount(double vipSavedAmount) {
            this.vipSavedAmount = vipSavedAmount;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getAmount() {
            return this.amount;
        }

        public void setBeforeAmount(double beforeAmount) {
            this.beforeAmount = beforeAmount;
        }

        public double getBeforeAmount() {
            return this.beforeAmount;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getTotalNum() {
            return this.totalNum;
        }

        public void setAmountAll(double amountAll) {
            this.amountAll = amountAll;
        }

        public double getAmountAll() {
            return this.amountAll;
        }

        public void setCheckAndUncheckNum(int checkAndUncheckNum) {
            this.checkAndUncheckNum = checkAndUncheckNum;
        }

        public int getCheckAndUncheckNum() {
            return this.checkAndUncheckNum;
        }

        public void setTotalDeliveryFee(double totalDeliveryFee) {
            this.totalDeliveryFee = totalDeliveryFee;
        }

        public double getTotalDeliveryFee() {
            return this.totalDeliveryFee;
        }

    }
}