package com.ody.p2p.retrofit.store;

import java.util.List;

/**
 * Created by user on 2017/8/4.
 */

public class PreCommissionBean {


    /**
     * code : 0
     * message : null
     * data : [{"money":22,"level":1},{"money":33,"level":2},{"money":11,"level":3}]
     */

    private int code;
    private Object message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
         * money : 22.0
         * level : 1
         */

        private double money;
        private int level;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "money=" + money +
                    ", level=" + level +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PreCommissionBean{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
