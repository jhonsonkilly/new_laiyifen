package com.ody.p2p.shoucang;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/5/10.
 */
public class MyShouCangListBean extends BaseRequestBean {

    /**
     * data : [{"id":10017,"mpId":1096,"merchantId":180,"productId":801,"chineseName":"拉布拉多犬","price":0.01,"originalPrice":0.01,"picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460106443994_71.56499772849702_1.png","status":4},{"id":10016,"mpId":1094,"merchantId":180,"productId":1010,"chineseName":"斗牛犬","price":1,"originalPrice":1,"picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460105656284_36.567066349820045_1.png","status":4},{"id":10015,"mpId":1095,"merchantId":180,"productId":1009,"chineseName":"边境牧羊犬","price":110,"originalPrice":110,"picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460105554323_41.04976518790993_1.png","status":4},{"id":10014,"mpId":1136,"merchantId":180,"productId":1032,"chineseName":"萨摩耶犬","price":400,"originalPrice":400,"picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1461898630533_8.51344188688936_1.png","status":4},{"id":10013,"mpId":1102,"merchantId":203,"productId":1011,"chineseName":"短毛猫","price":111,"originalPrice":111,"picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460275607131_33.99648515371485_1.png","status":4}]
     * totalCount : 5
     * pageSize : 10
     * totalPage : 1
     */

    public Data data;

    public static class Data {
        public int totalCount;
        public int pageSize;
        public int totalPage;
        /**
         * id : 10017
         * mpId : 1096
         * merchantId : 180
         * productId : 801
         * chineseName : 拉布拉多犬
         * price : 0.01
         * originalPrice : 0.01
         * picUrl : http://zhuoshi.kssws.ks-cdn.com/back_product/1460106443994_71.56499772849702_1.png
         * status : 4
         */

        public List<ShouCangData> data;

        public static class ShouCangData {
            private boolean checked = false;
            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }
            public String id;//收藏ID
            public String mpId;//商品ID
            public String merchantId;
            public String productId;
            public String chineseName;
            public double price;//售价(优惠价或原价)
            public double originalPrice;//原价
            public String picUrl;
            public int status;//商品状态，暂不使用
            public String mpSalesVolume;
            public int managementState ;
            public int stockNum ;

            public int getManagementState() {
                return managementState;
            }

            public void setManagementState(int managementState) {
                this.managementState = managementState;
            }

            public int getStockNum() {
                return stockNum;
            }

            public void setStockNum(int stockNum) {
                this.stockNum = stockNum;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMpId() {
                return mpId;
            }

            public void setMpId(String mpId) {
                this.mpId = mpId;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getChineseName() {
                return chineseName;
            }

            public void setChineseName(String chineseName) {
                this.chineseName = chineseName;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ShouCangData> getData() {
            return data;
        }

        public void setData(List<ShouCangData> data) {
            this.data = data;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
