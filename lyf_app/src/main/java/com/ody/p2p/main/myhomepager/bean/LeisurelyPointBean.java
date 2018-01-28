package com.ody.p2p.main.myhomepager.bean;

/**
 * 悠点卡 界面
 * Created by caishengya on 2017/2/28.
 */

public class LeisurelyPointBean {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"yBean":0,"eCardBalance":0,"yCardBalance":68.11}
     * trace : 37!$6#@16%&172!$,null,62041807524528703883032
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * yBean : 0
         * eCardBalance : 0
         * yCardBalance : 68.11
         */

        public int yBean;
        public int eCardBalance;
        public double yCardBalance;
    }
}
