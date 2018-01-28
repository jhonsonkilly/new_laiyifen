package com.ody.p2p.pay.payMode.payOnline;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ${tang} on 2016/12/12.
 */

public class CancelTimeBean extends BaseRequestBean {

    public OrderCasncelTime data;

    public class OrderCasncelTime{
        public long cancelTime;
        public String versionNo;
        public String paymentAmount;
    }
}
