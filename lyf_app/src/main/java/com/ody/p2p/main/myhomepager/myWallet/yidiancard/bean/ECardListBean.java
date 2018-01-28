package com.ody.p2p.main.myhomepager.myWallet.yidiancard.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by meijunqiang on 2017/3/21.
 * 描述：查询伊点卡列表
 */

public class ECardListBean extends BaseRequestBean {

    /**
     * trace : 37!$6#@16%&172!$,null,62042163318638225210427
     * data : {"conditionList":[{"valueId":1,"valueName":"金额从低到高"},{"valueId":2,"valueName":"金额从高到低"}],"totalCount":1,"ecardList":[{"amount":100,"balance":88,"cardCode":"1234djhff","cardPwd":"cccccccxxxxxxx","isPresent":0,"effectiveDate":"2018-12-31 23:59:59","status":0}]}
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
         * conditionList : [{"valueId":1,"valueName":"金额从低到高"},{"valueId":2,"valueName":"金额从高到低"}]
         * totalCount : 1
         * ecardList : [{"amount":100,"balance":88,"cardCode":"1234djhff","cardPwd":"cccccccxxxxxxx","isPresent":0,"effectiveDate":"2018-12-31 23:59:59","status":0}]
         */
        private List<ConditionListEntity> conditionList;
        private int totalCount;
        private List<EcardListEntity> ecardList;

        public void setConditionList(List<ConditionListEntity> conditionList) {
            this.conditionList = conditionList;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public void setEcardList(List<EcardListEntity> ecardList) {
            this.ecardList = ecardList;
        }

        public List<ConditionListEntity> getConditionList() {
            return conditionList;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public List<EcardListEntity> getEcardList() {
            return ecardList;
        }

        public static class ConditionListEntity {
            /**
             * valueId : 1
             * valueName : 金额从低到高
             */
            private int valueId;
            private String valueName;

            public void setValueId(int valueId) {
                this.valueId = valueId;
            }

            public void setValueName(String valueName) {
                this.valueName = valueName;
            }

            public int getValueId() {
                return valueId;
            }

            public String getValueName() {
                return valueName;
            }
        }

        public static class EcardListEntity {
            /**
             * amount : 100
             * balance : 88
             * cardCode : 1234djhff
             * cardPwd : cccccccxxxxxxx
             * isPresent : 0
             * effectiveDate : 2018-12-31 23:59:59
             * status : 0
             */
            private double amount;
            private double balance;
            private String cardCode;
            private String cardPwd;
            private int isPresent;
            private String effectiveDate;
            private int status;

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public void setCardCode(String cardCode) {
                this.cardCode = cardCode;
            }

            public void setCardPwd(String cardPwd) {
                this.cardPwd = cardPwd;
            }

            public void setIsPresent(int isPresent) {
                this.isPresent = isPresent;
            }

            public void setEffectiveDate(String effectiveDate) {
                this.effectiveDate = effectiveDate;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getAmount() {
                return amount;
            }

            public double getBalance() {
                return balance;
            }

            public String getCardCode() {
                if (cardCode == null) {
                    cardCode = "";
                }
                return cardCode;
            }

            public String getCardPwd() {
                if (cardPwd == null) {
                    cardPwd = "";
                }
                return cardPwd;
            }

            public int getIsPresent() {
                return isPresent;
            }

            public String getEffectiveDate() {
                if (effectiveDate == null) {
                    effectiveDate = "";
                }
                return effectiveDate;
            }

            public int getStatus() {
                return status;
            }
        }
    }
}
