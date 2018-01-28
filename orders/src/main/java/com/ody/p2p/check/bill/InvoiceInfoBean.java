package com.ody.p2p.check.bill;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ${tang} on 2017/1/13.
 */

public class InvoiceInfoBean extends BaseRequestBean {

    /**
     * id : 12
     * userId : 551
     * unitName : 份饭
     * taxpayerIdentificationCode : 111
     * registerAddress : 发发
     * registerPhone : 33342123
     * bankDeposit : 招商
     * bankAccount : 1234
     * goodReceiverAddress : 淡定
     * goodReceiverName : DDDD
     * goodReceiverMobile : 13034214321
     * goodReceiverProvinceId : 5
     * goodReceiverProvince : 黑龙江
     * goodReceiverCityId : 44
     * goodReceiverCity : 大兴安岭地区
     * goodReceiverAreaId : 1122
     * goodReceiverArea : 塔河县
     * registrationCertificatePath : http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1448847770402_107_Jellyfish.jpg
     * generalTaxpayerCertificatePath : http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1448847769578_654_Desert.jpg
     * increaseTicketAuthorizePath : http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1448847769046_781_Jellyfish.jpg
     * auditStatus : 0
     * bankLicensePath : http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1448847769046_781_Jellyfish.jpg
     */

    public DataBean data;

    public static class DataBean {
        public String id;
        public String userId;
        public String unitName;
        public String taxpayerIdentificationCode;
        public String registerAddress;
        public String registerPhone;
        public String bankDeposit;
        public String bankAccount;
        public String goodReceiverAddress;
        public String goodReceiverName;
        public String goodReceiverMobile;
        public String goodReceiverProvinceId;
        public String goodReceiverProvince;
        public String goodReceiverCityId;
        public String goodReceiverCity;
        public String goodReceiverAreaId;
        public String goodReceiverArea;
        public String registrationCertificatePath;
        public String generalTaxpayerCertificatePath;
        public String increaseTicketAuthorizePath;
        public String auditStatus;
        public String bankLicensePath;
    }
}
