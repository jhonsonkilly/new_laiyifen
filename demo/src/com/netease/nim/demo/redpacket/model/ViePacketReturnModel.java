package com.netease.nim.demo.redpacket.model;

/**
 * @author SevenCheng
 */

public class ViePacketReturnModel {


    /**
     * code : 1
     * message : 执行成功
     * errMsg : null
     * data : {"subPacketId":57217311,"amount":15.52,"status":1,"message":"已领取"}
     * trace : 108!$3#@3%&10!$,1,63508960748278378350167
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
         * subPacketId : 57217311
         * amount : 15.52
         * status : 1
         * message : 已领取
         */

        private int subPacketId;
        private double amount;
        private int    status;
        private String message;

        public int getSubPacketId() {
            return subPacketId;
        }

        public void setSubPacketId(int subPacketId) {
            this.subPacketId = subPacketId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
