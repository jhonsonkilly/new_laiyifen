package com.ody.p2p.retrofit.store;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by meizivskai on 2017/7/13.
 */

public class StoreBaseInfo extends BaseRequestBean {


    /**
     * code : 0
     * msg : null
     * data : {"logo":null,"signboardPic":null,"signboardLink":null,"shopDesc":null,"shopId":1076015501000002,"merchantId":1076015501000001,"shopName":"testZcm","contactMobile":null,"contactAddr":null,"shopType":null,"skuNum":null,"merchantType":null,"provinceId":null,"cityId":null,"regionId":null,"detailAddressAll":null,"status":null,"openTime":1483427009000,"brands":[{"logoUrl":null,"brandName":"54"},{"logoUrl":null,"brandName":"334345454VR发VR"},{"logoUrl":null,"brandName":"十二星座周边店"},{"logoUrl":null,"brandName":"测试审核123"},{"logoUrl":null,"brandName":"445454"},{"logoUrl":null,"brandName":"新增品牌test10706"},{"logoUrl":null,"brandName":"34345454"},{"logoUrl":null,"brandName":"ceshi122333"},{"logoUrl":null,"brandName":"得到"},{"logoUrl":null,"brandName":"测试隔离"},{"logoUrl":null,"brandName":"7665656"},{"logoUrl":null,"brandName":"测试32"},{"logoUrl":null,"brandName":"侧认识3344"},{"logoUrl":null,"brandName":"测试3334"},{"logoUrl":null,"brandName":"1222测试"},{"logoUrl":null,"brandName":"测试321"},{"logoUrl":null,"brandName":"测试品牌123"},{"logoUrl":null,"brandName":"565656"},{"logoUrl":null,"brandName":"22323"},{"logoUrl":null,"brandName":"223333"},{"logoUrl":null,"brandName":"ygygyg"},{"logoUrl":null,"brandName":"自然之宝"},{"logoUrl":"http://cdn.oudianyun.com/1498732891502_34.01032910657621_839dd8e0-5cb7-11e7-8beb-00163e1a65a2.jpg","brandName":"金斯利安"},{"logoUrl":"http://cdn.oudianyun.com/1484192763170_25.830840960594127_a3c64020-d879-11e6-a85b-0050569e21c7.jpg","brandName":"水果"},{"logoUrl":null,"brandName":"来伊份 培训"}],"favoriteNum":0,"certificateFiles":null,"flags":[],"mpNums":203,"newMpNums":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * logo : null
         * signboardPic : null
         * signboardLink : null
         * shopDesc : null
         * shopId : 1076015501000002
         * merchantId : 1076015501000001
         * shopName : testZcm
         * contactMobile : null
         * contactAddr : null
         * shopType : null
         * skuNum : null
         * merchantType : null
         * provinceId : null
         * cityId : null
         * regionId : null
         * detailAddressAll : null
         * status : null
         * openTime : 1483427009000
         * brands : [{"logoUrl":null,"brandName":"54"},{"logoUrl":null,"brandName":"334345454VR发VR"},{"logoUrl":null,"brandName":"十二星座周边店"},{"logoUrl":null,"brandName":"测试审核123"},{"logoUrl":null,"brandName":"445454"},{"logoUrl":null,"brandName":"新增品牌test10706"},{"logoUrl":null,"brandName":"34345454"},{"logoUrl":null,"brandName":"ceshi122333"},{"logoUrl":null,"brandName":"得到"},{"logoUrl":null,"brandName":"测试隔离"},{"logoUrl":null,"brandName":"7665656"},{"logoUrl":null,"brandName":"测试32"},{"logoUrl":null,"brandName":"侧认识3344"},{"logoUrl":null,"brandName":"测试3334"},{"logoUrl":null,"brandName":"1222测试"},{"logoUrl":null,"brandName":"测试321"},{"logoUrl":null,"brandName":"测试品牌123"},{"logoUrl":null,"brandName":"565656"},{"logoUrl":null,"brandName":"22323"},{"logoUrl":null,"brandName":"223333"},{"logoUrl":null,"brandName":"ygygyg"},{"logoUrl":null,"brandName":"自然之宝"},{"logoUrl":"http://cdn.oudianyun.com/1498732891502_34.01032910657621_839dd8e0-5cb7-11e7-8beb-00163e1a65a2.jpg","brandName":"金斯利安"},{"logoUrl":"http://cdn.oudianyun.com/1484192763170_25.830840960594127_a3c64020-d879-11e6-a85b-0050569e21c7.jpg","brandName":"水果"},{"logoUrl":null,"brandName":"来伊份 培训"}]
         * favoriteNum : 0
         * certificateFiles : null
         * flags : []
         * mpNums : 203
         * newMpNums : 0
         */

        private Object logo;
        private Object signboardPic;
        private Object signboardLink;
        private Object shopDesc;
        private long shopId;
        private long merchantId;
        private String shopName;
        private Object contactMobile;
        private Object contactAddr;
        private Object shopType;
        private Object skuNum;
        private Object merchantType;
        private Object provinceId;
        private Object cityId;
        private Object regionId;
        private Object detailAddressAll;
        private Object status;
        private long openTime;
        private int favoriteNum;
        private Object certificateFiles;
        private int mpNums;
        private int newMpNums;
        private List<BrandsBean> brands;
        private List<?> flags;
        private String flagName;
        private String dsrScore;

        public Object getLogo() {
            return logo;
        }

        public void setLogo(Object logo) {
            this.logo = logo;
        }

        public Object getSignboardPic() {
            return signboardPic;
        }

        public void setSignboardPic(Object signboardPic) {
            this.signboardPic = signboardPic;
        }

        public Object getSignboardLink() {
            return signboardLink;
        }

        public void setSignboardLink(Object signboardLink) {
            this.signboardLink = signboardLink;
        }

        public Object getShopDesc() {
            return shopDesc;
        }

        public void setShopDesc(Object shopDesc) {
            this.shopDesc = shopDesc;
        }

        public long getShopId() {
            return shopId;
        }

        public void setShopId(long shopId) {
            this.shopId = shopId;
        }

        public long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(long merchantId) {
            this.merchantId = merchantId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getContactMobile() {
            return contactMobile;
        }

        public void setContactMobile(Object contactMobile) {
            this.contactMobile = contactMobile;
        }

        public Object getContactAddr() {
            return contactAddr;
        }

        public void setContactAddr(Object contactAddr) {
            this.contactAddr = contactAddr;
        }

        public Object getShopType() {
            return shopType;
        }

        public void setShopType(Object shopType) {
            this.shopType = shopType;
        }

        public Object getSkuNum() {
            return skuNum;
        }

        public void setSkuNum(Object skuNum) {
            this.skuNum = skuNum;
        }

        public Object getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(Object merchantType) {
            this.merchantType = merchantType;
        }

        public Object getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Object provinceId) {
            this.provinceId = provinceId;
        }

        public Object getCityId() {
            return cityId;
        }

        public void setCityId(Object cityId) {
            this.cityId = cityId;
        }

        public Object getRegionId() {
            return regionId;
        }

        public void setRegionId(Object regionId) {
            this.regionId = regionId;
        }

        public Object getDetailAddressAll() {
            return detailAddressAll;
        }

        public void setDetailAddressAll(Object detailAddressAll) {
            this.detailAddressAll = detailAddressAll;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public long getOpenTime() {
            return openTime;
        }

        public void setOpenTime(long openTime) {
            this.openTime = openTime;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public Object getCertificateFiles() {
            return certificateFiles;
        }

        public void setCertificateFiles(Object certificateFiles) {
            this.certificateFiles = certificateFiles;
        }

        public int getMpNums() {
            return mpNums;
        }

        public void setMpNums(int mpNums) {
            this.mpNums = mpNums;
        }

        public int getNewMpNums() {
            return newMpNums;
        }

        public void setNewMpNums(int newMpNums) {
            this.newMpNums = newMpNums;
        }

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public List<?> getFlags() {
            return flags;
        }

        public void setFlags(List<?> flags) {
            this.flags = flags;
        }

        public String getFlagName() {
            return flagName;
        }

        public void setFlagName(String flagName) {
            this.flagName = flagName;
        }

        public String getDsrScore() {
            return dsrScore;
        }

        public void setDsrScore(String dsrScore) {
            this.dsrScore = dsrScore;
        }

        public static class BrandsBean {
            /**
             * logoUrl : null
             * brandName : 54
             */

            private Object logoUrl;
            private String brandName;

            public Object getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(Object logoUrl) {
                this.logoUrl = logoUrl;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }
        }
    }
}
