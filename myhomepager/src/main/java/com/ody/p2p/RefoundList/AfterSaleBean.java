package com.ody.p2p.RefoundList;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

public class AfterSaleBean extends BaseRequestBean {

    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private List<OrderRefundVOs> orderRefundVOs;

        private int total;

        public void setOrderRefundVOs(List<OrderRefundVOs> orderRefundVOs) {
            this.orderRefundVOs = orderRefundVOs;
        }

        public List<OrderRefundVOs> getOrderRefundVOs() {
            return this.orderRefundVOs;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return this.total;
        }

    }

    public class OrderRefundVOs {
        private long applyTime;

        private String id;

        private String orderCode;

        private double applyReturnAmount;

        private int refundStatus;

        private int returnStatus;

        private int cancelStatus;

        private double actualReturnAmount;

        private List<AfterSalesProductVOs> afterSalesProductVOs;

        public int type;

        private int productAmount;


        /**
         * @return the returnStatus
         */
        public int getReturnStatus() {
            return returnStatus;
        }

        /**
         * @param returnStatus the returnStatus to set
         */
        public void setReturnStatus(int returnStatus) {
            this.returnStatus = returnStatus;
        }

        public void setApplyTime(long applyTime) {
            this.applyTime = applyTime;
        }

        public long getApplyTime() {
            return this.applyTime;
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

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public int getRefundStatus() {
            return this.refundStatus;
        }

        public void setCancelStatus(int cancelStatus) {
            this.cancelStatus = cancelStatus;
        }

        public int getCancelStatus() {
            return this.cancelStatus;
        }

        public void setActualReturnAmount(double actualReturnAmount) {
            this.actualReturnAmount = actualReturnAmount;
        }

        public double getActualReturnAmount() {
            return this.actualReturnAmount;
        }

        public void setAfterSalesProductVOs(
                List<AfterSalesProductVOs> afterSalesProductVOs) {
            this.afterSalesProductVOs = afterSalesProductVOs;
        }

        public List<AfterSalesProductVOs> getAfterSalesProductVOs() {
            return this.afterSalesProductVOs;
        }

        public void setProductAmount(int productAmount) {
            this.productAmount = productAmount;
        }

        public int getProductAmount() {
            return this.productAmount;
        }

    }

    public class AfterSalesProductVOs {
        private String mpId;
        private String chineseName;
        private String productPicPath;

        public String productPayPrice;

        private int productItemNum;

        private int returnProductItemNum;

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public int getReturnProductItemNum() {
            return returnProductItemNum;
        }

        public void setReturnProductItemNum(int returnProductItemNum) {
            this.returnProductItemNum = returnProductItemNum;
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

    }

}
