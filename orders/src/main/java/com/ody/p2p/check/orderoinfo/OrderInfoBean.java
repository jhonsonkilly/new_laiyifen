package com.ody.p2p.check.orderoinfo;



import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/20.
 */
public class OrderInfoBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : {"userId":0,"orderCode":"160712305633990296","orderCreateTime":1468286995000,"orderStatus":8,"orderCanceOperateType":null,"orderCanceOperateTypeContext":null,"payMethod":"在线支付","amount":0.01,"totalCount":1,"canCancel":0,"isBankTransfer":0,"isWritedBankTransferInfo":0,"merchantId":101,"merchantName":"大唐有限公司","merchantType":null,"merchantTelephone":null,"shopId":null,"orderReturnStatus":1,"orderNeedCs":0,"orderBusinessType":0,"orderPaymentFlag":0,"installmentPayCount":null,"payPrice":null,"batchName":null,"batchNum":null,"soInstallmentId":null,"orderType":1,"groupBuyOrderCode":"12440","groupBuyTitle":"拼团测试008","packageCode":"P1607123056339902961","installmentCode":null,"receiver":{"receiverId":132528,"receiverName":"张三","countryId":null,"countryName":null,"provinceId":10,"provinceName":"上海","cityId":110,"cityName":"上海市","areaId":1140,"areaName":"浦东新区","detailAddress":"123号","receiverMobile":"13130303030","receiverPhone":null,"exactAddress":null,"sex":null},"payGateway":"支付宝","orderDeliveryFeeAccounting":0,"deliveryModeName":"申通物流","promotionAmount":0,"taxAmount":0,"productAmount":0.01,"orderPaidByCard":0,"orderPaidByCoupon":0,"cancelTime":null,"deliverCode":null,"remark":null,"deliveryTimeStr":null,"batchPayList":null,"groupBuyLimitNum":3,"groupCreateTime":"2016-07-09 09:51:57","standard":null,"invoice":{"id":null,"invoiceType":null,"invoiceTitleType":null,"invoiceTitleContent":null,"isNeedDetails":null,"invoiceContentId":null,"invoiceContent":null,"unitName":null,"taxpayerIdentificationCode":null,"registerAddress":null,"registerPhone":null,"bankDeposit":null,"bankAccount":null,"receiverName":null,"receiverMobile":null,"receiverCountry":null,"receiverProvince":null,"receiverCity":null,"receiverArea":null,"receiverAddress":null,"auditStatus":null},"shipTimeStr":"2016-07-12 15:53","deliveryExpressNbr":"asfasf","orderStatusName":"已完成","orderNeedCsName":"待海关审核","orderCreateTimeStr":"2016-07-12 09:29:55","orderPaidByPoint":10,"orderPaidByCommission":20,"parentOrderCode":"160712305633990296","paymentTimeStr":"2016-07-1209: 29: 55","receiveTimeStr":"2016-07-1209: 29: 55","canComment":1,"canDelete":0,"childOrderList":[{"orderCode":"160712305633990296","orderDeliveryFee":12,"packageList":[{"packageCode":"160712305633990296","productList":[{"soItemId":1828346,"merchantId":101,"productId":282,"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","price":0.01,"attrs":null,"num":1,"isGift":0,"standard":null,"securityList":[{"title":"七天包退","url":null}],"url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]}]}]}
     * trace : 165!$1#@168%&192!$,55579,61585688808183769600037
     */

    public String errMsg;
    /**
     * userId : 0
     * orderCode : 160712305633990296
     * orderCreateTime : 1468286995000
     * orderStatus : 8
     * orderCanceOperateType : null
     * orderCanceOperateTypeContext : null
     * payMethod : 在线支付
     * amount : 0.01
     * totalCount : 1
     * canCancel : 0
     * isBankTransfer : 0
     * isWritedBankTransferInfo : 0
     * merchantId : 101
     * merchantName : 大唐有限公司
     * merchantType : null
     * merchantTelephone : null
     * shopId : null
     * orderReturnStatus : 1
     * orderNeedCs : 0
     * orderBusinessType : 0
     * orderPaymentFlag : 0
     * installmentPayCount : null
     * payPrice : null
     * batchName : null
     * batchNum : null
     * soInstallmentId : null
     * orderType : 1
     * groupBuyOrderCode : 12440
     * groupBuyTitle : 拼团测试008
     * packageCode : P1607123056339902961
     * installmentCode : null
     * receiver : {"receiverId":132528,"receiverName":"张三","countryId":null,"countryName":null,"provinceId":10,"provinceName":"上海","cityId":110,"cityName":"上海市","areaId":1140,"areaName":"浦东新区","detailAddress":"123号","receiverMobile":"13130303030","receiverPhone":null,"exactAddress":null,"sex":null}
     * payGateway : 支付宝
     * orderDeliveryFeeAccounting : 0
     * deliveryModeName : 申通物流
     * promotionAmount : 0
     * taxAmount : 0
     * productAmount : 0.01
     * orderPaidByCard : 0
     * orderPaidByCoupon : 0
     * cancelTime : null
     * deliverCode : null
     * remark : null
     * deliveryTimeStr : null
     * batchPayList : null
     * groupBuyLimitNum : 3
     * groupCreateTime : 2016-07-09 09:51:57
     * standard : null
     * invoice : {"id":null,"invoiceType":null,"invoiceTitleType":null,"invoiceTitleContent":null,"isNeedDetails":null,"invoiceContentId":null,"invoiceContent":null,"unitName":null,"taxpayerIdentificationCode":null,"registerAddress":null,"registerPhone":null,"bankDeposit":null,"bankAccount":null,"receiverName":null,"receiverMobile":null,"receiverCountry":null,"receiverProvince":null,"receiverCity":null,"receiverArea":null,"receiverAddress":null,"auditStatus":null}
     * shipTimeStr : 2016-07-12 15:53
     * deliveryExpressNbr : asfasf
     * orderStatusName : 已完成
     * orderNeedCsName : 待海关审核
     * orderCreateTimeStr : 2016-07-12 09:29:55
     * orderPaidByPoint : 10
     * orderPaidByCommission : 20
     * parentOrderCode : 160712305633990296
     * paymentTimeStr : 2016-07-1209: 29: 55
     * receiveTimeStr : 2016-07-1209: 29: 55
     * canComment : 1
     * canDelete : 0
     * childOrderList : [{"orderCode":"160712305633990296","orderDeliveryFee":12,"packageList":[{"packageCode":"160712305633990296","productList":[{"soItemId":1828346,"merchantId":101,"productId":282,"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","price":0.01,"attrs":null,"num":1,"isGift":0,"standard":null,"securityList":[{"title":"七天包退","url":null}],"url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]}]}]
     */

    public DataBean data;
    public String trace;

    public static class DataBean {
        public String userId;
        public String orderCode;
        public long orderCreateTime;
        public int orderStatus;
        public int orderCanceOperateType;
        public String orderCanceOperateTypeContext;
        public String payMethod;
        public String amount;
        public int totalCount;
        public int canCancel;
        public int isBankTransfer;
        public int isWritedBankTransferInfo;
        public String merchantId;
        public String merchantName;
        public int merchantType;
        public String merchantTelephone;
        public String shopId;
        public int orderReturnStatus;
        public int orderNeedCs;
        public int orderBusinessType;
        public int orderPaymentFlag;
        public Object installmentPayCount;
        public String payPrice;
        public String batchName;
        public String batchNum;
        public String soInstallmentId;
        public String orderType;
        public String groupBuyOrderCode;
        public String groupBuyTitle;
        public String packageCode;
        public String installmentCode;
        public String paymentAmount;
        /**
         * receiverId : 132528
         * receiverName : 张三
         * countryId : null
         * countryName : null
         * provinceId : 10
         * provinceName : 上海
         * cityId : 110
         * cityName : 上海市
         * areaId : 1140
         * areaName : 浦东新区
         * detailAddress : 123号
         * receiverMobile : 13130303030
         * receiverPhone : null
         * exactAddress : null
         * sex : null
         */

        public ReceiverBean receiver;
        public String payGateway;
        public String orderDeliveryFeeAccounting;
        public String deliveryModeName;
        public String promotionAmount;
        public String taxAmount;
        public String productAmount;
        public String orderPaidByCard;
        public String orderPaidByCoupon;
        public long cancelTime;
        public String deliverCode;
        public String remark;
        public String deliveryTimeStr;
        public Object batchPayList;
        public int groupBuyLimitNum;
        public String groupCreateTime;
        public String standard;
        /**
         * id : null
         * invoiceType : null
         * invoiceTitleType : null
         * invoiceTitleContent : null
         * isNeedDetails : null
         * invoiceContentId : null
         * invoiceContent : null
         * unitName : null
         * taxpayerIdentificationCode : null
         * registerAddress : null
         * registerPhone : null
         * bankDeposit : null
         * bankAccount : null
         * receiverName : null
         * receiverMobile : null
         * receiverCountry : null
         * receiverProvince : null
         * receiverCity : null
         * receiverArea : null
         * receiverAddress : null
         * auditStatus : null
         */

        public InvoiceBean invoice;
        public String shipTimeStr;
        public String deliveryExpressNbr;
        public String orderStatusName;
        public String orderNeedCsName;
        public String orderCreateTimeStr;
        public String orderPaidByPoint;
        public String orderPaidByCommission;
        public String parentOrderCode;
        public String paymentTimeStr;
        public String receiveTimeStr;
        public int canComment;
        public int canDelete;
        public int canAfterSale;
        public OrderRefundVO orderRefund;
        public static class OrderRefundVO{
            public int refundStatus;
            public String refundStatusName;
            public String refundAmount;
        }
        /**
         * orderCode : 160712305633990296
         * orderDeliveryFee : 12
         * packageList : [{"packageCode":"160712305633990296","productList":[{"soItemId":1828346,"merchantId":101,"productId":282,"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","price":0.01,"attrs":null,"num":1,"isGift":0,"standard":null,"securityList":[{"title":"七天包退","url":null}],"url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]}]
         */

        public List<ChildOrderListBean> childOrderList;

        public static class ReceiverBean {
            public String receiverId;
            public String receiverName;
            public String countryId;
            public String countryName;
            public int provinceId;
            public String provinceName;
            public int cityId;
            public String cityName;
            public int areaId;
            public String areaName;
            public String detailAddress;
            public String receiverMobile;
            public String receiverPhone;
            public String exactAddress;
            public int sex;
        }

        public static class InvoiceBean {
            public String id;
            public String invoiceType;
            public String invoiceTitleType;
            public String invoiceTitleContent;
            public String isNeedDetails;
            public String invoiceContentId;
            public String invoiceContent;
            public Object unitName;
            public Object taxpayerIdentificationCode;
            public Object registerAddress;
            public Object registerPhone;
            public Object bankDeposit;
            public Object bankAccount;
            public Object receiverName;
            public Object receiverMobile;
            public Object receiverCountry;
            public Object receiverProvince;
            public Object receiverCity;
            public Object receiverArea;
            public Object receiverAddress;
            public Object auditStatus;
        }

        public static class ChildOrderListBean {
            public String orderCode;
            public String orderDeliveryFee;
            /**
             * packageCode : 160712305633990296
             * productList : [{"soItemId":1828346,"merchantId":101,"productId":282,"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","price":0.01,"attrs":null,"num":1,"isGift":0,"standard":null,"securityList":[{"title":"七天包退","url":null}],"url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]
             */

            public List<PackageListBean> packageList;

            public static class PackageListBean {
                public String packageCode;
                /**
                 * soItemId : 1828346
                 * merchantId : 101
                 * productId : 282
                 * mpId : 292
                 * name : 王老吉
                 * picUrl : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg
                 * price : 0.01
                 * attrs : null
                 * num : 1
                 * isGift : 0
                 * standard : null
                 * securityList : [{"title":"七天包退","url":null}]
                 * url120x120 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0
                 * url160x160 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0
                 * url220x220 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0
                 * url500x500 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0
                 * url800x800 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0
                 * url60x60 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0
                 * url100x100 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0
                 */

                public List<ProductListBean> productList;

                public static class ProductListBean {
                    public String soItemId;
                    public String merchantId;
                    public String productId;
                    public String mpId;
                    public String name;
                    public String picUrl;
                    public String price;
                    public String attrs;
                    public int num;
                    public int isGift;
                    public String standard;
                    public String url120x120;
                    public String url160x160;
                    public String url220x220;
                    public String url500x500;
                    public String url800x800;
                    public String url60x60;
                    public String url100x100;
                    public List<PropertyTagsVO> propertyTags;
                    /**
                     * title : 七天包退
                     * url : null
                     */

                    public List<SecurityListBean> securityList;

                    public static class SecurityListBean {
                        public String title;
                        public String url;
                    }

                    public static class PropertyTagsVO{
                        public String name;
                        public String value;
                    }
                }
            }
        }
    }
}
