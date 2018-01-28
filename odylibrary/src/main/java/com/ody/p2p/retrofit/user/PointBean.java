package com.ody.p2p.retrofit.user;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by Administrator on 2017/2/17.
 */

public class PointBean extends BaseRequestBean {


    /**
     * status : 0
     * success : true
     * data : {"message":"操作成功","code":"0","amount":20,"deadline":null}
     * desc : null
     * trace : 12!$9#@2%&10!$,0,62382033446409220900923
     */

    public Point data;

    public static class Point {
        /**
         * message : 操作成功
         * code : 0
         * amount : 20
         * deadline : null
         */

        public String message;
        public String code;
        public int amount;
        public Object deadline;
    }
}
