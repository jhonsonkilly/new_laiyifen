package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.PromotionInfo;

import java.util.List;

/**
 * Created by ody on 2016/8/17.
 */
public class RecommendAdapterBean {
    List<RecommendData> datas;

    public static class RecommendData {
        String produtImgUrl;
        String produtName;
        List<PromotionInfo> promotionInfo;
        double produtPrice;
        double promotionPrice;
        int produtSales;
        public long mpId;

        public static class promotions {
            public long id;
            public String description;
            public long promotionId;
            public int promotionType;
            public String url;

        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public void setProdutPrice(double produtPrice) {
            this.produtPrice = produtPrice;
        }

        public long getMpId() {
            return mpId;
        }

        public void setMpId(long mpId) {
            this.mpId = mpId;
        }

        public Double getProdutPrice() {
            return produtPrice;
        }

        public void setProdutPrice(Double produtPrice) {
            this.produtPrice = produtPrice;
        }

        public String getProdutImgUrl() {
            return produtImgUrl;
        }

        public void setProdutImgUrl(String produtImgUrl) {
            this.produtImgUrl = produtImgUrl;
        }

        public String getProdutName() {
            return produtName;
        }

        public void setProdutName(String produtName) {
            this.produtName = produtName;
        }

        public List<PromotionInfo> getPromotionInfo() {
            return promotionInfo;
        }

        public void setPromotionInfo(List<PromotionInfo> promotionInfo) {
            this.promotionInfo = promotionInfo;
        }

        public int getProdutSales() {
            return produtSales;
        }

        public void setProdutSales(int produtSales) {
            this.produtSales = produtSales;
        }

    }

    public List<RecommendData> getDatas() {
        return datas;
    }

    public void setDatas(List<RecommendData> datas) {
        this.datas = datas;
    }
}
