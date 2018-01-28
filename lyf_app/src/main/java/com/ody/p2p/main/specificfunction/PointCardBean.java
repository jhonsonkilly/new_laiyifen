package com.ody.p2p.main.specificfunction;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by lxs on 2017/2/24.
 */
public class PointCardBean extends BaseRequestBean {

    public Data data;

    public static class Data {
        public double balance;
        public String effectiveDate;
        public String cardCode;
        public int status;
    }
}
