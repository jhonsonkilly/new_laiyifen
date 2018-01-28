package com.ody.p2p.recmmend;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class Recommedbean extends BaseRequestBean {
    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int totalNum;

        private List<DataList> dataList ;

        private int isHaveNext;

        public void setTotalNum(int totalNum){
            this.totalNum = totalNum;
        }
        public int getTotalNum(){
            return this.totalNum;
        }
        public void setDataList(List<DataList> dataList){
            this.dataList = dataList;
        }
        public List<DataList> getDataList(){
            return this.dataList;
        }
        public void setIsHaveNext(int isHaveNext){
            this.isHaveNext = isHaveNext;
        }
        public int getIsHaveNext(){
            return this.isHaveNext;
        }
    }
    public class TagList {
        private String mpId;

        private String promType;

        private String tagName;

        private String tagUrl;

        private String weight;

        public void setMpId(String mpId){
            this.mpId = mpId;
        }
        public String getMpId(){
            return this.mpId;
        }
        public void setPromType(String promType){
            this.promType = promType;
        }
        public String getPromType(){
            return this.promType;
        }
        public void setTagName(String tagName){
            this.tagName = tagName;
        }
        public String getTagName(){
            return this.tagName;
        }
        public void setTagUrl(String tagUrl){
            this.tagUrl = tagUrl;
        }
        public String getTagUrl(){
            return this.tagUrl;
        }
        public void setWeight(String weight){
            this.weight = weight;
        }
        public String getWeight(){
            return this.weight;
        }

    }
    public class DataList {
        private String mpId;

        private String productId;

        private String merchantId;

        private String shopId;

        private String categoryId;

        private String mpCode;

        private String mpName;

        private String srcImgUrl;

        private String tagetUrl;

        private double salePrice;

        private double sourcePrice;

        private CommentInfo commentInfo;

        private List<TagList> tagList ;

        private String saleInfo;

        private String url60x60;

        private String url100x100;

        private String url120x120;

        private String url160x160;

        private String url220x220;

        private String url300x300;

        private String url400x400;

        private String url500x500;

        private String url800x800;

        public void setMpId(String mpId){
            this.mpId = mpId;
        }
        public String getMpId(){
            return this.mpId;
        }
        public void setProductId(String productId){
            this.productId = productId;
        }
        public String getProductId(){
            return this.productId;
        }
        public void setMerchantId(String merchantId){
            this.merchantId = merchantId;
        }
        public String getMerchantId(){
            return this.merchantId;
        }
        public void setShopId(String shopId){
            this.shopId = shopId;
        }
        public String getShopId(){
            return this.shopId;
        }
        public void setCategoryId(String categoryId){
            this.categoryId = categoryId;
        }
        public String getCategoryId(){
            return this.categoryId;
        }
        public void setMpCode(String mpCode){
            this.mpCode = mpCode;
        }
        public String getMpCode(){
            return this.mpCode;
        }
        public void setMpName(String mpName){
            this.mpName = mpName;
        }
        public String getMpName(){
            return this.mpName;
        }
        public void setSrcImgUrl(String srcImgUrl){
            this.srcImgUrl = srcImgUrl;
        }
        public String getSrcImgUrl(){
            return this.srcImgUrl;
        }
        public void setTagetUrl(String tagetUrl){
            this.tagetUrl = tagetUrl;
        }
        public String getTagetUrl(){
            return this.tagetUrl;
        }
        public void setSalePrice(double salePrice){
            this.salePrice = salePrice;
        }
        public double getSalePrice(){
            return this.salePrice;
        }
        public void setSourcePrice(double sourcePrice){
            this.sourcePrice = sourcePrice;
        }
        public double getSourcePrice(){
            return this.sourcePrice;
        }
        public void setCommentInfo(CommentInfo commentInfo){
            this.commentInfo = commentInfo;
        }
        public CommentInfo getCommentInfo(){
            return this.commentInfo;
        }
        public void setTagList(List<TagList> tagList){
            this.tagList = tagList;
        }
        public List<TagList> getTagList(){
            return this.tagList;
        }
        public void setSaleInfo(String saleInfo){
            this.saleInfo = saleInfo;
        }
        public String getSaleInfo(){
            return this.saleInfo;
        }
        public void setUrl60x60(String url60x60){
            this.url60x60 = url60x60;
        }
        public String getUrl60x60(){
            return this.url60x60;
        }
        public void setUrl100x100(String url100x100){
            this.url100x100 = url100x100;
        }
        public String getUrl100x100(){
            return this.url100x100;
        }
        public void setUrl120x120(String url120x120){
            this.url120x120 = url120x120;
        }
        public String getUrl120x120(){
            return this.url120x120;
        }
        public void setUrl160x160(String url160x160){
            this.url160x160 = url160x160;
        }
        public String getUrl160x160(){
            return this.url160x160;
        }
        public void setUrl220x220(String url220x220){
            this.url220x220 = url220x220;
        }
        public String getUrl220x220(){
            return this.url220x220;
        }
        public void setUrl300x300(String url300x300){
            this.url300x300 = url300x300;
        }
        public String getUrl300x300(){
            return this.url300x300;
        }
        public void setUrl400x400(String url400x400){
            this.url400x400 = url400x400;
        }
        public String getUrl400x400(){
            return this.url400x400;
        }
        public void setUrl500x500(String url500x500){
            this.url500x500 = url500x500;
        }
        public String getUrl500x500(){
            return this.url500x500;
        }
        public void setUrl800x800(String url800x800){
            this.url800x800 = url800x800;
        }
        public String getUrl800x800(){
            return this.url800x800;
        }

    }
    public class CommentInfo {
        private String mpid;

        private int commentNum;

        private int goodRate;

        public void setMpid(String mpid){
            this.mpid = mpid;
        }
        public String getMpid(){
            return this.mpid;
        }
        public void setCommentNum(int commentNum){
            this.commentNum = commentNum;
        }
        public int getCommentNum(){
            return this.commentNum;
        }
        public void setGoodRate(int goodRate){
            this.goodRate = goodRate;
        }
        public int getGoodRate(){
            return this.goodRate;
        }

    }
}
