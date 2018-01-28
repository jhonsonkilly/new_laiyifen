package com.ody.p2p.main.myhomepager.bean;

/**
 * 我的钱包页面 bean
 * Created by caishengya on 2017/2/27.
 */

public class MyWalletBean {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"eCardBalance":0,"yCardBalance":0,"yBean":1000,"coupon":0,"point":0}
     * trace : 34!$9#@2%&10!$,137412,62418981125510884592475
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * eCardBalance : 0.0
         * yCardBalance : 0
         * yBean : 1000
         * coupon : 0
         * point : 0
         */
        public String eCardBalance;
        public String yCardBalance;
        public String yBean;
        public String coupon;
        public String point;
    }
}
