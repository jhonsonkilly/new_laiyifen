package com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by meijunqiang on 2017/3/17.
 * 描述：
 */

public class YRecordBean extends BaseRequestBean {

    /**
     * trace : 25!$6#@16%&172!$,null,62067579455187402413479
     * data : {"data":[{"balance":88.88,"currentAmount":0,"transactionCode":"ccedhoahdshfownsl","payName":"微信支付","recordTimeStr":"2016-11-16 09:23:34"}],"totalPage":0,"pageSize":0,"totalCount":0}
     * errMsg : null
     */
    private String trace;
    private DataEntity data;
    private String errMsg;

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTrace() {
        return trace;
    }

    public DataEntity getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public static class DataEntity {
        /**
         * data : [{"balance":88.88,"currentAmount":0,"transactionCode":"ccedhoahdshfownsl","payName":"微信支付","recordTimeStr":"2016-11-16 09:23:34"}]
         * totalPage : 0
         * pageSize : 0
         * totalCount : 0
         */
        private List<DataEntityBean> data;
        private int totalPage;
        private int pageSize;
        private int totalCount;

        public void setData(List<DataEntityBean> data) {
            this.data = data;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<DataEntityBean> getData() {
            return data;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public static class DataEntityBean {
            /**
             * balance : 88.88
             * currentAmount : 0
             * transactionCode : ccedhoahdshfownsl
             * payName : 微信支付
             * recordTimeStr : 2016-11-16 09:23:34
             */
            private double balance;
            private int currentAmount;
            private String transactionCode;
            private String payName;
            private String recordTimeStr;

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public void setCurrentAmount(int currentAmount) {
                this.currentAmount = currentAmount;
            }

            public void setTransactionCode(String transactionCode) {
                this.transactionCode = transactionCode;
            }

            public void setPayName(String payName) {
                this.payName = payName;
            }

            public void setRecordTimeStr(String recordTimeStr) {
                this.recordTimeStr = recordTimeStr;
            }

            public double getBalance() {
                return balance;
            }

            public int getCurrentAmount() {
                return currentAmount;
            }

            public String getTransactionCode() {
                return transactionCode;
            }

            public String getPayName() {
                return payName;
            }

            public String getRecordTimeStr() {
                return recordTimeStr;
            }
        }
    }
}
