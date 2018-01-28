package com.ody.p2p.retrofit.store;

import java.util.List;

/**
 * Created by user on 2017/7/25.
 */

public class StoreHotKeyBean {

    /**
     * code : 0
     * msg : null
     * data : [{"id":1012035800000007,"merchantId":1076015501000001,"keywords":"测试7","priority":7,"isDeleted":null},{"id":1012035800000006,"merchantId":1076015501000001,"keywords":"测试6","priority":6,"isDeleted":null},{"id":1012035800000005,"merchantId":1076015501000001,"keywords":"测试5","priority":5,"isDeleted":null},{"id":1012035800000004,"merchantId":1076015501000001,"keywords":"测试4","priority":4,"isDeleted":null},{"id":1012035800000003,"merchantId":1076015501000001,"keywords":"测试3","priority":3,"isDeleted":null},{"id":1012035800000002,"merchantId":1076015501000001,"keywords":"测试2","priority":2,"isDeleted":null},{"id":1012035800000001,"merchantId":1076015501000001,"keywords":"测试1","priority":1,"isDeleted":null}]
     */

    private int code;
    private Object msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1012035800000007
         * merchantId : 1076015501000001
         * keywords : 测试7
         * priority : 7
         * isDeleted : null
         */

        private long id;
        private long merchantId;
        private String keywords;
        private int priority;
        private Object isDeleted;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(long merchantId) {
            this.merchantId = merchantId;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Object getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Object isDeleted) {
            this.isDeleted = isDeleted;
        }

        @Override
        public String toString() {
            return keywords;
        }
    }
}
