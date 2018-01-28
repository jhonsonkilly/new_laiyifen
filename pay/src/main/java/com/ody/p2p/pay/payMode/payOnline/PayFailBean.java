package com.ody.p2p.pay.payMode.payOnline;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/9/12.
 */
public class PayFailBean extends BaseRequestBean {
    public String errMsg;

    public DataBean data;
    public String trace;

    public static class DataBean {
        public long cancelTime;
    }
}
