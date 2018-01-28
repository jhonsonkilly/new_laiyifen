package com.ody.p2p.receiver;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/14.
 */
public class ConfirmOrderBean implements Serializable{



    public String code;
    public String message;

    public DataEntity data;


    public static class DataEntity implements Serializable{

        public Errors error;
        public static class Errors implements Serializable{
            public int type;
            public String message;
            public List<ChangeProduct> data;
        }

        public static class ChangeProduct implements Serializable{
            public String id;
            public String name;
            public String imgUrl;
        }
        public String userId;
        public String orderCode;
        /**
         * receiverId : 367
         * name : tgggg
         * mobile : 13256235242
         * provinceId : 10
         * provinceName : 上海
         * cityId : 110
         * cityName : 上海市
         * areaId : 1129
         * areaName : 黄浦区
         * detailAddress : ghhuhgff
         * postCode : null
         * exactAddress : null
         * sex : null
         * identityCardNumber : 61032**********2321
         */

        public ReceiverEntity receiver;
        public int totalNum;
        public String amount;
        public String totalWeight;
        public String totalDeliveryFee;
        public String promotionSavedAmount;
        public String productAmount;
        public String giftCardAmount;
        public String couponAmount;
        public String totalTax;
        public int overseaPurchase;//是否海沟
        public int isIdCardVerifyRequired;//是否实名认证

        public long countdown;//秒杀商品倒计时
        public String countdownTheme;

        public OrderUCard orderUCard;
        public OrderECard orderECard;
        public int receiverStatus;
        public class OrderUCard{
            public int isAvailable;
            public int selected;
            public String availableAmount;
            public String usageAmount;
        }

        public class OrderECard{
            public int isAvailable;
            public int selected;
            public String availableAmount;
            public String usageAmount;
        }

        /**
         * certificationId : 27
         * name : jonna
         */

        public UserCertificationEntity userCertification;
        public boolean oversea;

        public List<ExpenseEntity> expenseList;
        public static class ExpenseEntity implements Serializable{
            public String label;
            public String value;
            public String operator;
            public String iconUrl;
        }
        /**
         * merchantSupportInvoiceType : 1
         * invoice : null
         * invoiceContentList : [{"invoiceContentId":1,"invoiceContentValue":"食品"},{"invoiceContentId":2,"invoiceContentValue":"耗材"},{"invoiceContentId":3,"invoiceContentValue":"办公用品"},{"invoiceContentId":4,"invoiceContentValue":"服饰"}]
         */

        public OrderInvoiceEntity orderInvoice;
        public String remark;
        public Object deliveryTime;
        /**
         * orderCoupons : []
         * selected : 1
         */

        public Giftcard giftCard;

        public static class Giftcard implements Serializable{
            public int isAvailable;
            public String balance;
            public String selectedBalance;
            public int selected;
            public String unavailableReason;
            public int availableQuantity;
        }
        public AllCouponEntity allCoupon;
        public int businessType;
        public Point points;
        public static class Point implements Serializable{
            public int minUseUnit;
            public double discount;
            public double rate;
            public int canUseCount;
            public int totalCount;
            public int isAvailable;
        }

        public Brokerage brokerage;

        public static class Brokerage implements Serializable{
            public String usageAmount;
            public String residualAmount;
            public int isAvailable;
        }
        /**
         * paymentId : 1
         * name : 在线支付
         * selected : 1
         * isAvailable : 1
         * unavailableReason : null
         * availableCouponNum : 0
         */

        public List<PaymentsEntity> payments;


        public List<MerchantListEntity> merchantList;
        /**
         * merchantId : 100
         * merchantName : 优品可视
         * deliveryModeList : [{"isDefault":1,"code":"1006","name":"普通快递"}]
         * deliveryFee : 0.0
         */

        public List<MerchantDeliveryModeListEntity> merchantDeliveryModeList;
        public List<?> coupons;

        public static class ReceiverEntity implements Serializable {
            public String receiverId;
            public String name;
            public String mobile;
            public long provinceId;
            public String provinceName;
            public int cityId;
            public String cityName;
            public int areaId;
            public String areaName;
            public String detailAddress;
            public String postCode;
            public String exactAddress;
            public String sex;
            public String identityCardNumber;
            public int isDefault;//1默认
        }

        public static class UserCertificationEntity implements Serializable {
            public long certificationId;
            public String name;
        }

        public static class OrderInvoiceEntity implements Serializable {
            public int merchantSupportInvoiceType;//0 不支持发票 1 仅支持普通发票 2 仅支持增值税发票 3 支持普通发票和增值税发票
            public int isSupportEInvoice;//0 不支持电子发票 1 支持电子发票 2.隐藏电子发票

            public boolean isSupportEInvoice() {
                return isSupportEInvoice == 1;
            }

            public InvoiceEntity invoice;
            /**
             * invoiceContentId : 1
             * invoiceContentValue : 食品
             */

            public List<InvoiceContentListEntity> invoiceContentList;

            public static class InvoiceContentListEntity implements Serializable {
                public long invoiceContentId;
                public String invoiceContentValue;
            }
            public static class InvoiceEntity implements Serializable{
//                "invoiceType": 1,
//                        "invoiceTitleType": 1,
//                        "invoiceTitleContent": "个人",
//                        "isNeedDetails": 1,
//                        "invoiceContentId": null,
//                        "invoiceContent": null,
//                        "unitName": null,
//                        "taxpayerIdentificationCode": null,
//                        "registerAddress": null,
//                        "registerPhone": null,
//                        "bankDeposit": null,
//                        "bankAccount": null,
//                        "receiverName": null,
//                        "receiverMobile": null,
//                        "receiverCountry": null,
//                        "receiverProvince": null,
//                        "receiverCity": null,
//                        "receiverArea": null,
//                        "receiverAddress": null,
//                        "auditStatus": 1

                public int invoiceType;//发票类型 0是不开发票，1普通发票，2增值税发票
                public int invoiceTitleType;//发票抬头内容 1个人／2单位
                public int invoiceMode;//发票种类 1电子发票，2纸质发票
                public String invoiceTitleContent;//普通发票的发票抬头内容
                public String taxpayerIdentificationCode;//纳税人识别码
                public String takerEmail;//收票人邮箱
                public int isNeedDetails;
                public long invoiceContentId;
                public String unitName;
                public int auditStatus;

            }
        }



        public static class AllCouponEntity implements Serializable {
            public int selected;
            public List<OrderCoupons> orderCoupons;
            public int availableQuantity;
            public String preferentialPrice;

            public static class OrderCoupons implements Serializable{
               public String themeTitle;
               public String couponValue;
            }
        }

        public static class PaymentsEntity implements Serializable {
            public long paymentId;
            public String name;
            public int selected;
            public int isAvailable;
            public String unavailableReason;
            public int availableCouponNum;
        }

        public static class MerchantListEntity implements Serializable {
            public String id;
            public String merchantId;
            public String merchantName;
            public int totalNum;
            public double totalWeight;
            public double amount;
            public double tax;
            public String deliveryFee;
            public String overseaModeName;
            public long overseaModeId;
            public String remark;

            public List<ProductListEntity> productList;

            public static class ProductListEntity implements Serializable {
                public String mpId;
                public String name;
                public String picUrl;
                public int num;
                public String weight;
                public String price;
                public String tax;
                public int stock;
                public int mpType;
                public String productAmount;
                public String grossWeight;
                public String netWeight;
                public int freightAttribute;
                public String merchantProdVolume;
                public int isGift;
                public String promotionType;
                public String exciseTaxAmount;
                public String incrementTaxAmount;
                public String customsDutiesAmount;
                public String url60x60;
                public String url100x100;
                public String url120x120;
                public String url160x160;
                public String url220x220;
                public String url500x500;
                public String url800x800;
                public int isVatInvoice;
            }


        }

        public static class MerchantDeliveryModeListEntity implements Serializable{
            public String id;
            public long merchantId;
            public String merchantName;
            public double deliveryFee;
            /**
             * isDefault : 1
             * code : 1006
             * name : 普通快递
             */

            public List<DeliveryModeListEntity> deliveryModeList;

            public static class DeliveryModeListEntity implements Serializable{
                public int isDefault;
                public String code;
                public String name;
                public String freight;
                public int isTakeTheir;
                public String pickSiteId;
                public String pickMobile;
                public String pickName;
                public String pickAddress;
                public String promiseDate;
            }
        }
    }
}
