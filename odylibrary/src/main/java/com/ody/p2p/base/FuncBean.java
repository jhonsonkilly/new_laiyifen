package com.ody.p2p.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2016/9.20
 */
public class FuncBean extends BaseRequestBean {
    public Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        public List<AdSource> tan_ping;
        public List<AdSource> ad_login_coupon;
        public List<AdSource> channel;
        public List<AdSource> hot_activity;
        public List<AdSource> special;
        public List<AdSource> lunbo;
        public List<AdSource> ad_pic;
        public List<AdSource> seckill1;
        public List<AdSource> seckill2;
        public List<AdSource> refound;
        public List<AdSource> join_process;
        public List<AdSource> hotline;
        public List<AdSource> home_video;
        public List<AdSource> searchword;

        public List<AdSource> ad_brand;
        public List<AdSource> ad_woman;
        public List<AdSource> ad_man;
        public List<AdSource> ad_child;
        public List<AdSource> ad_decoration;
        public List<AdSource> ad_sport;
        public List<AdSource> hotword;

        public List<AdSource> ad_world;

        public List<AdSource> miaosha_rukou;

        public List<AdSource> img_pay_spread;

        private List<Comment_rule> comment_rule;//评价

        public List<AdSource> guide_pages;

        public List<AdSource> notice_settle;

        public List<AdSource> newcomers_popup;

        public List<AdSource> registered_coupon;

        public List<AdSource> pintuan_entry;

        public List<AdSource> kanjia_entry;

        public List<AdSource> pintuan_rule_instruction;

        public List<Comment_rule> getComment_rule() {
            return comment_rule;
        }

        public class Comment_rule {
            private long id;

            private String name;

            private String startTime;

            private String endTime;

            private int sort;

            private int type;

            private String title;

            private String content;

            private int refType;

            private String refId;

            private String linkUrl;

            private String imageUrl;

            private String imageTitle;

            private RefObject refObject;

            private boolean goods;

            public void setId(long id) {
                this.id = id;
            }

            public long getId() {
                return this.id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getStartTime() {
                return this.startTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getEndTime() {
                return this.endTime;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getSort() {
                return this.sort;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType() {
                return this.type;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent() {
                return this.content;
            }

            public void setRefType(int refType) {
                this.refType = refType;
            }

            public int getRefType() {
                return this.refType;
            }

            public void setRefId(String refId) {
                this.refId = refId;
            }

            public String getRefId() {
                return this.refId;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getLinkUrl() {
                return this.linkUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getImageUrl() {
                return this.imageUrl;
            }

            public void setImageTitle(String imageTitle) {
                this.imageTitle = imageTitle;
            }

            public String getImageTitle() {
                return this.imageTitle;
            }

            public void setRefObject(RefObject refObject) {
                this.refObject = refObject;
            }

            public RefObject getRefObject() {
                return this.refObject;
            }

            public void setGoods(boolean goods) {
                this.goods = goods;
            }

            public boolean getGoods() {
                return this.goods;
            }

        }

        public class RefObject {
            private String productId;

            private String mpId;

            private String mpsId;

            private String code;

            private String name;

            private String subTitle;

            private String type;

            private String price;

            private String marketPrice;

            private String tax;

            private String stockNum;

            private String lackOfStock;

            private String brandId;

            private String brandName;

            private String brandImgUrl;

            private String categoryId;

            private String promotionType;

            private String promotionPrice;

            private String preferentialPrice;

            private String promotionId;

            private String promotionIconUrl;

            private String promotionIconUrls;

            private String promotionIconTexts;

            private String categoryTreeNodeId;

            private String merchantSeriesId;

            private String companyId;

            private String merchantId;

            private String merchantName;

            private String merchantType;

            private String freightAttribute;

            private String grossWeight;

            private String merchantProdVolume;

            private String shopId;

            private String shopName;

            private String shopType;

            private String freightTemplateId;

            private String warehouseNo;

            private String calculationUnit;

            private String standard;

            private String saleType;

            private String saleIconUrl;

            private String mpSalesVolume;

            private String mpSource;

            private String isBargain;

            private String isRent;

            private String status;

            private String managementState;

            private String remark;

            private String volume4sale;

            private String picUrl;

            private String promotionInfo;

            private String url60x60;

            private String url100x100;

            private String url120x120;

            private String url160x160;

            private String url220x220;

            private String url500x500;

            private String url800x800;

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductId() {
                return this.productId;
            }

            public void setMpId(String mpId) {
                this.mpId = mpId;
            }

            public String getMpId() {
                return this.mpId;
            }

            public void setMpsId(String mpsId) {
                this.mpsId = mpsId;
            }

            public String getMpsId() {
                return this.mpsId;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCode() {
                return this.code;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getSubTitle() {
                return this.subTitle;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType() {
                return this.type;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPrice() {
                return this.price;
            }

            public void setMarketPrice(String marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getMarketPrice() {
                return this.marketPrice;
            }

            public void setTax(String tax) {
                this.tax = tax;
            }

            public String getTax() {
                return this.tax;
            }

            public void setStockNum(String stockNum) {
                this.stockNum = stockNum;
            }

            public String getStockNum() {
                return this.stockNum;
            }

            public void setLackOfStock(String lackOfStock) {
                this.lackOfStock = lackOfStock;
            }

            public String getLackOfStock() {
                return this.lackOfStock;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getBrandId() {
                return this.brandId;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandName() {
                return this.brandName;
            }

            public void setBrandImgUrl(String brandImgUrl) {
                this.brandImgUrl = brandImgUrl;
            }

            public String getBrandImgUrl() {
                return this.brandImgUrl;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCategoryId() {
                return this.categoryId;
            }

            public void setPromotionType(String promotionType) {
                this.promotionType = promotionType;
            }

            public String getPromotionType() {
                return this.promotionType;
            }

            public void setPromotionPrice(String promotionPrice) {
                this.promotionPrice = promotionPrice;
            }

            public String getPromotionPrice() {
                return this.promotionPrice;
            }

            public void setPreferentialPrice(String preferentialPrice) {
                this.preferentialPrice = preferentialPrice;
            }

            public String getPreferentialPrice() {
                return this.preferentialPrice;
            }

            public void setPromotionId(String promotionId) {
                this.promotionId = promotionId;
            }

            public String getPromotionId() {
                return this.promotionId;
            }

            public void setPromotionIconUrl(String promotionIconUrl) {
                this.promotionIconUrl = promotionIconUrl;
            }

            public String getPromotionIconUrl() {
                return this.promotionIconUrl;
            }

            public void setPromotionIconUrls(String promotionIconUrls) {
                this.promotionIconUrls = promotionIconUrls;
            }

            public String getPromotionIconUrls() {
                return this.promotionIconUrls;
            }

            public void setPromotionIconTexts(String promotionIconTexts) {
                this.promotionIconTexts = promotionIconTexts;
            }

            public String getPromotionIconTexts() {
                return this.promotionIconTexts;
            }

            public void setCategoryTreeNodeId(String categoryTreeNodeId) {
                this.categoryTreeNodeId = categoryTreeNodeId;
            }

            public String getCategoryTreeNodeId() {
                return this.categoryTreeNodeId;
            }

            public void setMerchantSeriesId(String merchantSeriesId) {
                this.merchantSeriesId = merchantSeriesId;
            }

            public String getMerchantSeriesId() {
                return this.merchantSeriesId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getCompanyId() {
                return this.companyId;
            }

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

            public void setMerchantType(String merchantType) {
                this.merchantType = merchantType;
            }

            public String getMerchantType() {
                return this.merchantType;
            }

            public void setFreightAttribute(String freightAttribute) {
                this.freightAttribute = freightAttribute;
            }

            public String getFreightAttribute() {
                return this.freightAttribute;
            }

            public void setGrossWeight(String grossWeight) {
                this.grossWeight = grossWeight;
            }

            public String getGrossWeight() {
                return this.grossWeight;
            }

            public void setMerchantProdVolume(String merchantProdVolume) {
                this.merchantProdVolume = merchantProdVolume;
            }

            public String getMerchantProdVolume() {
                return this.merchantProdVolume;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getShopId() {
                return this.shopId;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopName() {
                return this.shopName;
            }

            public void setShopType(String shopType) {
                this.shopType = shopType;
            }

            public String getShopType() {
                return this.shopType;
            }

            public void setFreightTemplateId(String freightTemplateId) {
                this.freightTemplateId = freightTemplateId;
            }

            public String getFreightTemplateId() {
                return this.freightTemplateId;
            }

            public void setWarehouseNo(String warehouseNo) {
                this.warehouseNo = warehouseNo;
            }

            public String getWarehouseNo() {
                return this.warehouseNo;
            }

            public void setCalculationUnit(String calculationUnit) {
                this.calculationUnit = calculationUnit;
            }

            public String getCalculationUnit() {
                return this.calculationUnit;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public String getStandard() {
                return this.standard;
            }

            public void setSaleType(String saleType) {
                this.saleType = saleType;
            }

            public String getSaleType() {
                return this.saleType;
            }

            public void setSaleIconUrl(String saleIconUrl) {
                this.saleIconUrl = saleIconUrl;
            }

            public String getSaleIconUrl() {
                return this.saleIconUrl;
            }

            public void setMpSalesVolume(String mpSalesVolume) {
                this.mpSalesVolume = mpSalesVolume;
            }

            public String getMpSalesVolume() {
                return this.mpSalesVolume;
            }

            public void setMpSource(String mpSource) {
                this.mpSource = mpSource;
            }

            public String getMpSource() {
                return this.mpSource;
            }

            public void setIsBargain(String isBargain) {
                this.isBargain = isBargain;
            }

            public String getIsBargain() {
                return this.isBargain;
            }

            public void setIsRent(String isRent) {
                this.isRent = isRent;
            }

            public String getIsRent() {
                return this.isRent;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatus() {
                return this.status;
            }

            public void setManagementState(String managementState) {
                this.managementState = managementState;
            }

            public String getManagementState() {
                return this.managementState;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getRemark() {
                return this.remark;
            }

            public void setVolume4sale(String volume4sale) {
                this.volume4sale = volume4sale;
            }

            public String getVolume4sale() {
                return this.volume4sale;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getPicUrl() {
                return this.picUrl;
            }

            public void setPromotionInfo(String promotionInfo) {
                this.promotionInfo = promotionInfo;
            }

            public String getPromotionInfo() {
                return this.promotionInfo;
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

        public static class AdSource implements Serializable {
            public long id;
            public String name;
            public long startTime;
            public long endTime;
            public String sort;
            public long type;
            public String title;
            public String content;
            public int refType;
            public String refId;
            public String linkUrl;
            public String imageUrl;
            public String imageTitle;
            public boolean totalFlag;

            @Override
            public String toString() {
                return content;
            }
        }
    }
}
