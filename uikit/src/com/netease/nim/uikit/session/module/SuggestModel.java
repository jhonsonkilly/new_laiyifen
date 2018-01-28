package com.netease.nim.uikit.session.module;

import java.util.List;

/**
 * Created by jasmin on 2018/1/10.
 */

public class SuggestModel {


    /**
     * status : 200
     * message : null
     * data : [{"name":"CY甘栗仁","picUrl":"http://cdn.oudianyun.com/1504250807607_19.790973437516378_6c349c20-4e5d-4e54-ab90-24887e061f26.png@base@tag=imgScale&q=80&m=0&h=220&w=220","availablePrice":"11.00","mpsId":"1004039701000190"},{"name":"小核桃仁138g","picUrl":"http://images.laiyifen.com/laiyifen/2013/72576/72576_02_m.jpg?v=1.0","availablePrice":"43.90","mpsId":"1004016601020765"},{"name":"核桃夹心枣170g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20160918/0c57558a-2352-4638-b8ae-f61398c36e04_m.jpg","availablePrice":"1.00","mpsId":"1004016601029139"}]
     */

    private int status;
    private Object message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : CY甘栗仁
         * picUrl : http://cdn.oudianyun.com/1504250807607_19.790973437516378_6c349c20-4e5d-4e54-ab90-24887e061f26.png@base@tag=imgScale&q=80&m=0&h=220&w=220
         * availablePrice : 11.00
         * mpsId : 1004039701000190
         */

        private String name;
        private String picUrl;
        private String availablePrice;
        private String mpsId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getAvailablePrice() {
            return availablePrice;
        }

        public void setAvailablePrice(String availablePrice) {
            this.availablePrice = availablePrice;
        }

        public String getMpsId() {
            return mpsId;
        }

        public void setMpsId(String mpsId) {
            this.mpsId = mpsId;
        }
    }
}
