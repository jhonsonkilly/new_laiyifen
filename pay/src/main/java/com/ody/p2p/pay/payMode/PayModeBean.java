package com.ody.p2p.pay.payMode;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/20.
 */
public class PayModeBean extends BaseRequestBean {

    /**
     * payIcon : http://img1.zgxcw.com/v1/tfs//T2NHbTBgEv1RCvBVdK
     * payTitle : 在线支付
     */

    public List<PayModeData> data;

    public static class PayModeData {
        public int paymentId;
        public String name;
        public boolean isChoose;
    }
}
