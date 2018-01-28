package com.ody.p2p.check.orderlist;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by tangmeijuan on 16/6/20.
 */
public class OrderListBean extends BaseRequestBean {


    /**
     * totalCount : 2
     * userId : 940
     * orderList : [{"userId":0,"orderCode":"160702830131276940","orderCreateTime":1467435018000,"orderStatus":1,"orderCanceOperateType":null,"orderCanceOperateTypeContext":null,"productList":[{"soItemId":null,"merchantId":null,"productId":null,"mpId":1106,"name":"大蜥蜴","picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png","price":1111,"attrs":null,"num":2,"isGift":0,"url60x60":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=100&w=100&m=0","url500x500":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=220&w=220&m=0"}],"payMethod":null,"amount":2222,"totalCount":1,"canCancel":0,"isBankTransfer":0,"isWritedBankTransferInfo":0,"merchantId":203,"merchantName":"詹氏杂货店006","merchantType":"11","shopId":70,"orderReturnStatus":null,"orderNeedCs":null,"orderBusinessType":null,"orderPaymentFlag":0,"installmentPayCount":null,"payPrice":null,"batchName":null,"batchNum":null,"soInstallmentId":null,"orderType":null,"groupBuyOrderCode":null,"installmentCode":null,"childOrderList":null,"orderStatusName":"待付款","orderNeedCsName":null,"orderCreateTimeStr":"2016-07-02 12:50:18"},{"userId":0,"orderCode":"160702270812958940","orderCreateTime":1467432072000,"orderStatus":1,"orderCanceOperateType":null,"orderCanceOperateTypeContext":null,"productList":[{"soItemId":null,"merchantId":null,"productId":null,"mpId":1106,"name":"大蜥蜴","picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png","price":1111,"attrs":null,"num":2,"isGift":0,"url60x60":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=100&w=100&m=0","url500x500":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=220&w=220&m=0"}],"payMethod":null,"amount":2222,"totalCount":1,"canCancel":0,"isBankTransfer":0,"isWritedBankTransferInfo":0,"merchantId":203,"merchantName":"詹氏杂货店006","merchantType":"11","shopId":70,"orderReturnStatus":null,"orderNeedCs":null,"orderBusinessType":null,"orderPaymentFlag":0,"installmentPayCount":null,"payPrice":null,"batchName":null,"batchNum":null,"soInstallmentId":null,"orderType":null,"groupBuyOrderCode":null,"installmentCode":null,"childOrderList":null,"orderStatusName":"待付款","orderNeedCsName":null,"orderCreateTimeStr":"2016-07-02 12:01:12"}]
     */

    public DataBean data;

    public static class DataBean {
        public int totalCount;
        public String userId;
        /**
         * userId : 0
         * orderCode : 160702830131276940
         * orderCreateTime : 1467435018000
         * orderStatus : 1
         * orderCanceOperateType : null
         * orderCanceOperateTypeContext : null
         * productList : [{"soItemId":null,"merchantId":null,"productId":null,"mpId":1106,"name":"大蜥蜴","picUrl":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png","price":1111,"attrs":null,"num":2,"isGift":0,"url60x60":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=100&w=100&m=0","url500x500":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=220&w=220&m=0"}]
         * payMethod : null
         * amount : 2222.0
         * totalCount : 1
         * canCancel : 0
         * isBankTransfer : 0
         * isWritedBankTransferInfo : 0
         * merchantId : 203
         * merchantName : 詹氏杂货店006
         * merchantType : 11
         * shopId : 70
         * orderReturnStatus : null
         * orderNeedCs : null
         * orderBusinessType : null
         * orderPaymentFlag : 0
         * installmentPayCount : null
         * payPrice : null
         * batchName : null
         * batchNum : null
         * soInstallmentId : null
         * orderType : null
         * groupBuyOrderCode : null
         * installmentCode : null
         * childOrderList : null
         * orderStatusName : 待付款
         * orderNeedCsName : null
         * orderCreateTimeStr : 2016-07-02 12:50:18
         */

        public List<OrderListItemBean> orderList;
        public List<OrderListItemBean> data;
        public static class OrderListItemBean {
            public String userId;
            public String orderCode;
            public long orderCreateTime;
            public int orderStatus;
            public int orderCanceOperateType;
            public String orderCanceOperateTypeContext;
            public String payMethod;
            public String amount;
            public int totalCount;
            public String packageCode;
            public int isBankTransfer;
            public int isWritedBankTransferInfo;
            public String merchantId;
            public String merchantName;
            public String merchantType;
            public String shopId;
            public int orderReturnStatus;
            public String orderNeedCs;
            public int orderBusinessType;
            public int orderPaymentFlag;
            public String installmentPayCount;
            public String payPrice;
            public String batchName;
            public int batchNum;
            public int soInstallmentId;
            public int orderType;
            public String groupBuyOrderCode;
            public String installmentCode;

//            public String childOrderList;
            public String orderStatusName;
            public String orderNeedCsName;
            public String orderCreateTimeStr;
            public int canComment;
            public int canDelete;
            public int canCancel;
            public String paymentAmount;
            public List<PackageListEntity> packageList;

            public static class PackageListEntity {
                public String packageCode;
            }

            /**
             * soItemId : null
             * merchantId : null
             * productId : null
             * mpId : 1106
             * name : 大蜥蜴
             * picUrl : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png
             * price : 1111.0
             * attrs : null
             * num : 2
             * isGift : 0
             * url60x60 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=60&w=60&m=0
             * url100x100 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=100&w=100&m=0
             * url500x500 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=500&w=500&m=0
             * url800x800 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=800&w=800&m=0
             * url120x120 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=120&w=120&m=0
             * url160x160 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=160&w=160&m=0
             * url220x220 : http://zhuoshi.kssws.ks-cdn.com/back_product/1460277661053_30.06691606947487_1.png@base@tag=imgScale&h=220&w=220&m=0
             */

            public List<ProductListBean> productList;

            public static class ProductListBean {
                public String soItemId;
                public String merchantId;
                public String productId;
                public String mpId;
                public String name;
                public String picUrl;
                public double price;
                public Object attrs;
                public int num;
                public int isGift;
                public String url60x60;
                public String url100x100;
                public String url500x500;
                public String url800x800;
                public String url120x120;
                public String url160x160;
                public String url220x220;
                public List<PropertyTagsVO> propertyTags;
                public static class PropertyTagsVO{
                    public String name;
                    public String value;
                }
            }
        }
    }
}
