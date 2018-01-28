package com.ody.p2p.pay.payMode.payOnline;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/6/29.
 */
public class PayTypeListBean extends BaseRequestBean {

    public Data data;

    public class Data{
        public List<PayType> payGatewayList;
        public List<PayType> commonPayGatewayList;
    }
    public class PayType{
        public boolean isChoose;
        public String paymentConfigId;
        public int paymentGateway;
        public String paymentThirdparty;
        public String paymentLogoUrl;
        public String promLabel;
        public String promotionId;
    }

}
