package com.ody.p2p.check.myorder;


import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by lxs on 2016/6/29.
 */
public class SubmitOrderBean extends BaseRequestBean {

    public Data data;

    public class Data{
        public String amount;
        public String orderCode;
        public int orderWebStatus;
        public String userId;
        public int deliveryFee;
        public int isPaid;
    }
}
