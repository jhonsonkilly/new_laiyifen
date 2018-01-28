package com.ody.p2p.pay.payMode.payOnline;


import com.google.gson.annotations.SerializedName;
import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by Sun on 2016/1/27.
 */
public class PrePayInfoBean extends BaseRequestBean {

    /**
     * sign : C4F7C14251F70BA2C88B311E65838F75
     * timestamp : 1453890502
     * noncestr : 7cfd5df443b4eb0d69886a583b33de4c
     * partnerid : 1245636402
     * prepayid : wx20160127182833816c0cffe20672679358
     * package : Sign=WXPay
     * appid : wxde8907939c78dc5c
     */

    public PrePayInfor data;

    public static class PaymentMessage {
        public String od;
        public String appid;
        public String noncestr;
        public String out_trade_no;
        @SerializedName("package")
        public String packageX;
        public String partnerid;
        public String prepayid;
        public String sign;
        public String timestamp;
        public String tn;

        //招行的
        public String PayUrl;
        public PayData p_data;
        //建行支付
        public String orderUrl;

        public static class PayData {
            public String Amount;
            public String BillNo;
            public String BranchID;
            public String Cono;
            public String Date;
            public String ExpireTimeSpan;
            public String MerchantCode;
            public String MerchantRetUrl;
            public String MerchantUrl;
        }
    }

    public static class PrePayInfor {
        public String sign;
        public String timestamp;
        public String noncestr;
        public String partnerid;
        public String prepayid;
        @SerializedName("package")
        public String packageX;
        public String appid;
        public String od;
        public String signMethod;
        public String tn;
        public String paymentConfigId;
        public PaymentMessage paymentMessage;
    }

}
