package com.netease.nim.demo.session.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class PacketCheckReturnModel {


    /**
     * code : 1
     * message : 执行成功
     * errMsg : null
     * data : {"kind":"1","balance":null,"status":null,"message":null,"cardList":[{"cardCode":"68214358","cardName":"XXXU点卡","cardAmount":"26.99"},{"cardCode":"16838794","cardName":"XXXU点卡","cardAmount":"12.73"},{"cardCode":"15926595","cardName":"XXXU点卡","cardAmount":"23.57"}]}
     * trace : 108!$3#@3%&10!$,1,63495630271564926470185
     */

    private String code;
    private String message;
    private Object errMsg;
    private DataBean data;
    private String trace;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public static class DataBean {
        /**
         * kind : 1
         * balance : null
         * status : null
         * message : null
         * cardList : [{"cardCode":"68214358","cardName":"XXXU点卡","cardAmount":"26.99"},{"cardCode":"16838794","cardName":"XXXU点卡","cardAmount":"12.73"},{"cardCode":"15926595","cardName":"XXXU点卡","cardAmount":"23.57"}]
         */

        private String kind;
        private Object balance;
        private Object status;
        private Object message;
        private List<CardListBean> cardList;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public Object getBalance() {
            return balance;
        }

        public void setBalance(Object balance) {
            this.balance = balance;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public List<CardListBean> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardListBean> cardList) {
            this.cardList = cardList;
        }

        public static class CardListBean {
            /**
             * cardCode : 68214358
             * cardName : XXXU点卡
             * cardAmount : 26.99
             */

            private String cardCode;
            private String cardName;
            private String cardAmount;

            public String getCardCode() {
                return cardCode;
            }

            public void setCardCode(String cardCode) {
                this.cardCode = cardCode;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public String getCardAmount() {
                return cardAmount;
            }

            public void setCardAmount(String cardAmount) {
                this.cardAmount = cardAmount;
            }
        }
    }
}
