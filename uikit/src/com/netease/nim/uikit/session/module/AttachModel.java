package com.netease.nim.uikit.session.module;

/**
 * @author SevenCheng
 */

public class AttachModel {


    /**
     * data : {"bussinessCardUserId":"15921513808"}
     * type : 7
     */

    private DataBean data;
    private int type;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class DataBean {
        /**
         * bussinessCardUserId : 15921513808
         */

        private String bussinessCardUserId;

        public String getBussinessCardUserId() {
            return bussinessCardUserId;
        }

        public void setBussinessCardUserId(String bussinessCardUserId) {
            this.bussinessCardUserId = bussinessCardUserId;
        }
    }
}
