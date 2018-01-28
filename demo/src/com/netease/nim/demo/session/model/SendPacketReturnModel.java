package com.netease.nim.demo.session.model;

/**
 * @author SevenCheng
 */

public class SendPacketReturnModel {

    /**
     * code : 1
     * message : 执行成功
     * errMsg : null
     * data : {"redPacketId":78361795,"type":1,"typeStr":"普通红包","kind":1,"amount":50,"number":0,"title":"来伊份红包"}
     * trace : 108!$3#@3%&10!$,1,63495384626837882771877
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
         * redPacketId : 78361795
         * type : 1
         * typeStr : 普通红包
         * kind : 1
         * amount : 50
         * number : 0
         * title : 来伊份红包
         */

        private int redPacketId;
        private int    type;
        private String typeStr;
        private int    kind;
        private double    amount;
        private int    number;
        private String title;

        public int getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(int redPacketId) {
            this.redPacketId = redPacketId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
