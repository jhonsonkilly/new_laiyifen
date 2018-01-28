package com.ody.p2p.main.myhomepager.bean;

/**
 * Created by caishengya on 2017/3/20.
 */

public class LyfScoreBean {


    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : {"amountBalance":0,"amountFreezed":0,"amountExpiring":0}
     * trace : 34!$9#@2%&10!$,143236,62495269581780754230267
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * amountBalance : 0
         * amountFreezed : 0
         * amountExpiring : 0
         */

        public int amountBalance;
        public int amountFreezed;
        public int amountExpiring;
    }
}
