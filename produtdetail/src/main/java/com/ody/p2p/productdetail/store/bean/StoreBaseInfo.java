package com.ody.p2p.productdetail.store.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * 店铺基本信息
 */

public class StoreBaseInfo extends BaseRequestBean {

    /**
     * code : 0
     * msg : null
     * data : {"logo":"https://gw.alicdn.com/simba/img/TB10IICKFXXXXXaXpXXSutbFXXX.jpg_q50.jpg","signboardPic":"https://gw.alicdn.com/simba/img/TB10IICKFXXXXXaXpXXSutbFXXX.jpg_q50.jpg","signboardLink":null,"shopDesc":"<p>商品描述测试数据<\/p>","shopId":8,"merchantId":100,"shopName":"店铺","contactMobile":"15800351874","contactAddr":"安徽省芜湖市无为县洪巷乡马场村","shopType":null,"skuNum":null,"merchantType":null,"provinceId":340000,"cityId":340200,"regionId":340225,"detailAddressAll":null,"status":null,"openTime":null,"brands":null,"favoriteNum":0,"certificateFiles":null,"flags":[],"mpNums":0,"newMpNums":0}
     */

    private Object msg;
    private DataBean data;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * logo : https://gw.alicdn.com/simba/img/TB10IICKFXXXXXaXpXXSutbFXXX.jpg_q50.jpg
         * signboardPic : https://gw.alicdn.com/simba/img/TB10IICKFXXXXXaXpXXSutbFXXX.jpg_q50.jpg
         * signboardLink : null
         * shopDesc : <p>商品描述测试数据</p>
         * shopId : 8
         * merchantId : 100
         * shopName : 店铺
         * contactMobile : 15800351874
         * contactAddr : 安徽省芜湖市无为县洪巷乡马场村
         * shopType : null
         * skuNum : null
         * merchantType : null
         * provinceId : 340000
         * cityId : 340200
         * regionId : 340225
         * detailAddressAll : null
         * status : null
         * openTime : null
         * brands : null
         * favoriteNum : 0
         * certificateFiles : null
         * flags : []
         * mpNums : 0
         * newMpNums : 0
         */

        private String logo;//店铺logo url
        private String signboardPic;//店招图片url
        private Object signboardLink;//店招图片链接
        private String shopDesc;//店铺简介
        private long shopId;//店铺id
        private long merchantId;//店铺对应商家id
        private String shopName;//店铺名称
        private String contactMobile;//联系号码
        private String contactAddr;//联系地址
        private long shopType;
        private int skuNum;
        private String merchantType;
        private long provinceId;
        private long cityId;
        private long regionId;
        private String detailAddressAll;
        private int status;//店铺状态 1正常 0停用 2清退中 -1新引进
        private long openTime;//开店时间
        public List<MerchantBrandVO> brands;//商家品牌
        private int favoriteNum;//商家店铺收藏人数
        private List<String> certificateFiles;//资质证书
        private int mpNums;//店铺商品数量
        private int newMpNums;   //新上商品数量
        private List<String> flags;//店铺标签
        private String dsrScore;
        private String flagName;

        public class MerchantBrandVO{
            public String logoUrl;
            public String brandName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSignboardPic() {
            return signboardPic;
        }

        public void setSignboardPic(String signboardPic) {
            this.signboardPic = signboardPic;
        }

        public Object getSignboardLink() {
            return signboardLink;
        }

        public void setSignboardLink(Object signboardLink) {
            this.signboardLink = signboardLink;
        }

        public String getShopDesc() {
            return shopDesc;
        }

        public void setShopDesc(String shopDesc) {
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

        public String getContactMobile() {
            return contactMobile;
        }

        public void setContactMobile(String contactMobile) {
            this.contactMobile = contactMobile;
        }

        public String getContactAddr() {
            return contactAddr;
        }

        public void setContactAddr(String contactAddr) {
            this.contactAddr = contactAddr;
        }

        public long getShopType() {
            return shopType;
        }

        public void setShopType(long shopType) {
            this.shopType = shopType;
        }

        public int getSkuNum() {
            return skuNum;
        }

        public void setSkuNum(int skuNum) {
            this.skuNum = skuNum;
        }

        public String getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(String merchantType) {
            this.merchantType = merchantType;
        }

        public long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(long provinceId) {
            this.provinceId = provinceId;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public long getRegionId() {
            return regionId;
        }

        public void setRegionId(long regionId) {
            this.regionId = regionId;
        }

        public String getDetailAddressAll() {
            return detailAddressAll;
        }

        public void setDetailAddressAll(String detailAddressAll) {
            this.detailAddressAll = detailAddressAll;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public List<String> getCertificateFiles() {
            return certificateFiles;
        }

        public void setCertificateFiles(List<String> certificateFiles) {
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

        public List<String> getFlags() {
            return flags;
        }

        public void setFlags(List<String> flags) {
            this.flags = flags;
        }

        public String getDsrScore() {
            return dsrScore;
        }

        public void setDsrScore(String dsrScore) {
            this.dsrScore = dsrScore;
        }

        public String getFlagName() {
            return flagName;
        }

        public void setFlagName(String flagName) {
            this.flagName = flagName;
        }
    }
}
