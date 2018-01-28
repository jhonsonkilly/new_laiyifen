package com.ody.p2p.main.pay;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by caishengya on 2017/5/11.
 */

public class CardPayBean extends BaseRequestBean {
    public DataBean data;

    public static class DataBean {
        /**
         * isPaid : 1
         */
        public int isPaid;
    }
}
