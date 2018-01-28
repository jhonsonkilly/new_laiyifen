package com.ody.p2p.main.myhomepager.bean;

/**
 * Created by caishengya on 2017/3/21.
 */

public class LyfRuleBean {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"beforeRate":1,"afterRate":10,"canExchangeAmount":880,"amount":88,"canExchange":1}
     * trace : 25!$6#@16%&172!$,null,62071228296788510540025
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * beforeRate : 1
         * afterRate : 10
         * canExchangeAmount : 880
         * amount : 88
         * canExchange : 1
         */

        public int beforeRate;
        public int afterRate;
        public int canExchangeAmount;
        public int amount;
        public int canExchange;
    }
}
