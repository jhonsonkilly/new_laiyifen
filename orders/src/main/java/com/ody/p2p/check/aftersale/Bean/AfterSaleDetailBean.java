package com.ody.p2p.check.aftersale.Bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

public class AfterSaleDetailBean extends BaseRequestBean {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class MerchantProductVOs {
        private String id;

        private String mpId;

        private String productPicPath;

        private int productItemNum;

        private String chineseName;

        private int returnProductItemNum;

        private int mayReturnProductItemNum;

        private double productPayPrice;

        private String productPriceFinal;

        private String returnId;

        private String productItemAmount;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setMpId(String mpId) {
            this.mpId = mpId;
        }

        public String getMpId() {
            return this.mpId;
        }

        public void setProductPicPath(String productPicPath) {
            this.productPicPath = productPicPath;
        }

        public String getProductPicPath() {
            return this.productPicPath;
        }

        public void setProductItemNum(int productItemNum) {
            this.productItemNum = productItemNum;
        }

        public int getProductItemNum() {
            return this.productItemNum;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getChineseName() {
            return this.chineseName;
        }

        public void setReturnProductItemNum(int returnProductItemNum) {
            this.returnProductItemNum = returnProductItemNum;
        }

        public int getReturnProductItemNum() {
            return this.returnProductItemNum;
        }

        public void setMayReturnProductItemNum(int mayReturnProductItemNum) {
            this.mayReturnProductItemNum = mayReturnProductItemNum;
        }

        public int getMayReturnProductItemNum() {
            return this.mayReturnProductItemNum;
        }

        public void setProductPayPrice(double productPayPrice) {
            this.productPayPrice = productPayPrice;
        }

        public double getProductPayPrice() {
            return this.productPayPrice;
        }

        public void setProductPriceFinal(String productPriceFinal) {
            this.productPriceFinal = productPriceFinal;
        }

        public String getProductPriceFinal() {
            return this.productPriceFinal;
        }

        public void setReturnId(String returnId) {
            this.returnId = returnId;
        }

        public String getReturnId() {
            return this.returnId;
        }

        public void setProductItemAmount(String productItemAmount) {
            this.productItemAmount = productItemAmount;
        }

        public String getProductItemAmount() {
            return this.productItemAmount;
        }

    }

    public class Data {
        private String returnRemark;

        private String returnReason;

        private int type;
        private List<String> picUrlList;
        private String orderCreateTime;

        private String orderCreateTimeStr;

        private String applyTime;

        private String applyTimeStr;

        private String id;

        private String orderCode;

        private double applyReturnAmount;

        private int returnStatus;

        private int cancelStatus;

        private String actualReturnAmount;

        private String courierNumber;

        private String returnReasonId;

        private List<MerchantProductVOs> merchantProductVOs;

        private String serviceDesc;

        private String returnCode;

        public String consigneeAddress;

        public String consigneeName;

        public String consigneeMobile;

        public String logisticsCompany;

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }

        public void setReturnRemark(String returnRemark) {
            this.returnRemark = returnRemark;
        }

        public String getReturnRemark() {
            return this.returnRemark;
        }

        public void setReturnReason(String returnReason) {
            this.returnReason = returnReason;
        }

        public String getReturnReason() {
            return this.returnReason;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
        }

        public String getOrderCreateTime() {
            return this.orderCreateTime;
        }

        public void setOrderCreateTimeStr(String orderCreateTimeStr) {
            this.orderCreateTimeStr = orderCreateTimeStr;
        }

        public String getOrderCreateTimeStr() {
            return this.orderCreateTimeStr;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getApplyTime() {
            return this.applyTime;
        }

        public void setApplyTimeStr(String applyTimeStr) {
            this.applyTimeStr = applyTimeStr;
        }

        public String getApplyTimeStr() {
            return this.applyTimeStr;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderCode() {
            return this.orderCode;
        }

        public void setApplyReturnAmount(double applyReturnAmount) {
            this.applyReturnAmount = applyReturnAmount;
        }

        public double getApplyReturnAmount() {
            return this.applyReturnAmount;
        }

        public void setReturnStatus(int returnStatus) {
            this.returnStatus = returnStatus;
        }

        public int getReturnStatus() {
            return this.returnStatus;
        }

        public void setCancelStatus(int cancelStatus) {
            this.cancelStatus = cancelStatus;
        }

        public int getCancelStatus() {
            return this.cancelStatus;
        }

        public void setActualReturnAmount(String actualReturnAmount) {
            this.actualReturnAmount = actualReturnAmount;
        }

        public String getActualReturnAmount() {
            return this.actualReturnAmount;
        }

        public void setCourierNumber(String courierNumber) {
            this.courierNumber = courierNumber;
        }

        public String getCourierNumber() {
            return this.courierNumber;
        }

        public void setReturnReasonId(String returnReasonId) {
            this.returnReasonId = returnReasonId;
        }

        public String getReturnReasonId() {
            return this.returnReasonId;
        }

        public void setMerchantProductVOs(List<MerchantProductVOs> merchantProductVOs) {
            this.merchantProductVOs = merchantProductVOs;
        }

        public List<MerchantProductVOs> getMerchantProductVOs() {
            return this.merchantProductVOs;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceDesc() {
            return this.serviceDesc;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnCode() {
            return this.returnCode;
        }

    }


}
