package com.lyfen.android.entity.network;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/12/13.
 */

public class OrderDetailEntity implements Serializable {


    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : {"userId":0,"orderCode":"171213835308887771","orderCreateTime":1513152419000,"orderStatus":1,"orderCanceOperateType":null,"orderCanceOperateTypeContext":null,"productList":null,"payMethod":"在线支付","amount":176.9,"paymentAmount":176.9,"totalCount":9,"canCancel":1,"isBankTransfer":0,"isWritedBankTransferInfo":0,"merchantId":101,"merchantName":"来伊份","merchantType":null,"merchantTelephone":"123456","shopId":null,"orderReturnStatus":1,"orderNeedCs":0,"orderBusinessType":0,"canComment":0,"commentStatus":0,"canDelete":0,"orderDeliveryFeeAccounting":0,"orderPaymentFlag":0,"installmentPayCount":null,"payPrice":null,"batchName":null,"batchNum":null,"soInstallmentId":null,"orderType":0,"groupBuyOrderCode":null,"groupBuyTitle":null,"packageCode":null,"packageList":[],"canAfterSale":0,"isDeleted":0,"installmentCode":null,"presell":{"status":null,"totalPrice":null,"downPrice":null,"offsetPrice":null,"finalPayment":null,"startTime":null,"endTime":null,"deliveryTime":null,"orderPaidByAccount":null},"orderFreeFlag":0,"canRefund":0,"canUndoRefund":0,"orderPromotionStatus":0,"versionNo":1,"orderPromotionType":0,"invoiceStatus":1,"canSupplementInvoice":2,"canRedrawInvoice":2,"receiver":{"receiverId":91865,"receiverName":"周佳伟","countryId":null,"countryName":null,"provinceId":10,"provinceName":"上海","cityId":110,"cityName":"上海市","areaId":1135,"areaName":"虹口区","detailAddress":"周家嘴路286号202室","receiverMobile":"15618090292","receiverPhone":null,"exactAddress":null,"sex":null},"payGateway":null,"deliveryModeName":"来伊份商城配送","promotionAmount":9.9,"taxAmount":0,"productAmount":216.8,"orderPaidByCard":0,"orderPaidByCoupon":30,"orderPaidByPoString":0,"orderPaidByCommission":0,"orderPaidByUcard":0,"orderVipDiscount":null,"cancelTime":41367,"deliverCode":null,"remark":null,"deliveryTimeStr":null,"batchPayList":null,"groupBuyLimitNum":null,"groupCreateTime":null,"standard":null,"invoice":{"id":1035050000036075,"invoiceType":1,"invoiceTitleType":2,"invoiceTitleContent":"生生世世","isNeedDetails":null,"invoiceContentId":1,"invoiceContent":"食品","unitName":null,"taxpayerIdentificationCode":null,"registerAddress":null,"registerPhone":null,"bankDeposit":null,"bankAccount":null,"receiverName":null,"receiverMobile":null,"receiverCountry":null,"receiverProvince":null,"receiverCity":null,"receiverArea":null,"receiverAddress":null,"auditStatus":null,"takerMobile":"15618090292","takerEmail":null,"invoiceMode":null,"invoiceList":null},"shipTimeStr":null,"deliveryExpressNbr":null,"childOrderList":[{"orderCode":"171213835308887771","orderCreateTime":null,"amount":null,"totalCount":0,"merchantId":null,"merchantName":"来伊份","merchantType":null,"merchantTelephone":null,"orderDeliveryFee":0,"packageList":[{"packageCode":null,"productList":[{"soItemId":1035050000036061,"merchantId":101,"productId":1008020801006842,"mpId":1008020801006843,"name":"精制猪肉脯128g","picUrl":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","price":19.8,"attrs":null,"num":2,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url100x100":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url120x120":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url160x160":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url220x220":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url300x300":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url400x400":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url500x500":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url800x800":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0"},{"soItemId":1035050000036062,"merchantId":101,"productId":1007020801001956,"mpId":1007020801001957,"name":"风味鸭脖118g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160.jpg","price":11.8,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg"},{"soItemId":1035050000036063,"merchantId":101,"productId":1006020801002703,"mpId":1006020801002704,"name":"照烧鸡翅中200g","picUrl":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg","price":31.9,"attrs":null,"num":5,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"},{"soItemId":1035050000036064,"merchantId":101,"productId":1008020801002501,"mpId":1008020801002502,"name":"天天坚果-萌萌派25g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180.jpg","price":5.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg"}]}],"remark":null,"remarkMerchant2user":null,"deliveryModeCode":"1937","deliveryModeName":"来伊份商城配送","isTakeTheir":0,"shopInfo":null,"orderDeliveryFeeAccounting":0,"pickMobile":null,"pickName":null,"pickAddress":null,"promiseDate":null}],"parentOrderCode":null,"paymentTimeStr":null,"receiveTimeStr":null,"orderRefund":null,"signDescribe":null,"confirmReceiveTime":null,"undoCancel":0,"remarkMerchant2user":null,"usedYidou":0,"orderStatusName":"待付款","orderNeedCsName":"待海关审核","orderCreateTimeStr":"2017-12-13 16:06:59"}
     * trace : 42!$1#@18%&10!$,197461,63466289351335980431475
     */

    public String code;
    public String message;
    public String errMsg;
    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * userId : 0
         * orderCode : 171213835308887771
         * orderCreateTime : 1513152419000
         * orderStatus : 1
         * orderCanceOperateType : null
         * orderCanceOperateTypeContext : null
         * productList : null
         * payMethod : 在线支付
         * amount : 176.9
         * paymentAmount : 176.9
         * totalCount : 9
         * canCancel : 1
         * isBankTransfer : 0
         * isWritedBankTransferInfo : 0
         * merchantId : 101
         * merchantName : 来伊份
         * merchantType : null
         * merchantTelephone : 123456
         * shopId : null
         * orderReturnStatus : 1
         * orderNeedCs : 0
         * orderBusinessType : 0
         * canComment : 0
         * commentStatus : 0
         * canDelete : 0
         * orderDeliveryFeeAccounting : 0.0
         * orderPaymentFlag : 0
         * installmentPayCount : null
         * payPrice : null
         * batchName : null
         * batchNum : null
         * soInstallmentId : null
         * orderType : 0
         * groupBuyOrderCode : null
         * groupBuyTitle : null
         * packageCode : null
         * packageList : []
         * canAfterSale : 0
         * isDeleted : 0
         * installmentCode : null
         * presell : {"status":null,"totalPrice":null,"downPrice":null,"offsetPrice":null,"finalPayment":null,"startTime":null,"endTime":null,"deliveryTime":null,"orderPaidByAccount":null}
         * orderFreeFlag : 0
         * canRefund : 0
         * canUndoRefund : 0
         * orderPromotionStatus : 0
         * versionNo : 1
         * orderPromotionType : 0
         * invoiceStatus : 1
         * canSupplementInvoice : 2
         * canRedrawInvoice : 2
         * receiver : {"receiverId":91865,"receiverName":"周佳伟","countryId":null,"countryName":null,"provinceId":10,"provinceName":"上海","cityId":110,"cityName":"上海市","areaId":1135,"areaName":"虹口区","detailAddress":"周家嘴路286号202室","receiverMobile":"15618090292","receiverPhone":null,"exactAddress":null,"sex":null}
         * payGateway : null
         * deliveryModeName : 来伊份商城配送
         * promotionAmount : 9.9
         * taxAmount : 0.0
         * productAmount : 216.8
         * orderPaidByCard : 0.0
         * orderPaidByCoupon : 30.0
         * orderPaidByPoString : 0.0
         * orderPaidByCommission : 0.0
         * orderPaidByUcard : 0.0
         * orderVipDiscount : null
         * cancelTime : 41367
         * deliverCode : null
         * remark : null
         * deliveryTimeStr : null
         * batchPayList : null
         * groupBuyLimitNum : null
         * groupCreateTime : null
         * standard : null
         * invoice : {"id":1035050000036075,"invoiceType":1,"invoiceTitleType":2,"invoiceTitleContent":"生生世世","isNeedDetails":null,"invoiceContentId":1,"invoiceContent":"食品","unitName":null,"taxpayerIdentificationCode":null,"registerAddress":null,"registerPhone":null,"bankDeposit":null,"bankAccount":null,"receiverName":null,"receiverMobile":null,"receiverCountry":null,"receiverProvince":null,"receiverCity":null,"receiverArea":null,"receiverAddress":null,"auditStatus":null,"takerMobile":"15618090292","takerEmail":null,"invoiceMode":null,"invoiceList":null}
         * shipTimeStr : null
         * deliveryExpressNbr : null
         * childOrderList : [{"orderCode":"171213835308887771","orderCreateTime":null,"amount":null,"totalCount":0,"merchantId":null,"merchantName":"来伊份","merchantType":null,"merchantTelephone":null,"orderDeliveryFee":0,"packageList":[{"packageCode":null,"productList":[{"soItemId":1035050000036061,"merchantId":101,"productId":1008020801006842,"mpId":1008020801006843,"name":"精制猪肉脯128g","picUrl":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","price":19.8,"attrs":null,"num":2,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url100x100":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url120x120":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url160x160":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url220x220":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url300x300":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url400x400":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url500x500":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url800x800":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0"},{"soItemId":1035050000036062,"merchantId":101,"productId":1007020801001956,"mpId":1007020801001957,"name":"风味鸭脖118g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160.jpg","price":11.8,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg"},{"soItemId":1035050000036063,"merchantId":101,"productId":1006020801002703,"mpId":1006020801002704,"name":"照烧鸡翅中200g","picUrl":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg","price":31.9,"attrs":null,"num":5,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"},{"soItemId":1035050000036064,"merchantId":101,"productId":1008020801002501,"mpId":1008020801002502,"name":"天天坚果-萌萌派25g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180.jpg","price":5.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg"}]}],"remark":null,"remarkMerchant2user":null,"deliveryModeCode":"1937","deliveryModeName":"来伊份商城配送","isTakeTheir":0,"shopInfo":null,"orderDeliveryFeeAccounting":0,"pickMobile":null,"pickName":null,"pickAddress":null,"promiseDate":null}]
         * parentOrderCode : null
         * paymentTimeStr : null
         * receiveTimeStr : null
         * orderRefund : null
         * signDescribe : null
         * confirmReceiveTime : null
         * undoCancel : 0
         * remarkMerchant2user : null
         * usedYidou : 0.0
         * orderStatusName : 待付款
         * orderNeedCsName : 待海关审核
         * orderCreateTimeStr : 2017-12-13 16:06:59
         */

        public String userId;
        public String orderCode;
        public String orderCreateTime;
        public int orderStatus;
        public String orderCanceOperateType;
        public String orderCanceOperateTypeContext;
        public String productList;
        public String payMethod;
        public String amount;
        public String paymentAmount;
        public String totalCount;
        public int canCancel;
        public String isBankTransfer;
        public String isWritedBankTransferInfo;
        public String merchantId;
        public String merchantName;
        public String merchantType;
        public String merchantTelephone;
        public String shopId;
        public String orderReturnStatus;
        public String orderNeedCs;
        public String orderBusinessType;
        public int canComment;
        public int commentStatus;
        public int canDelete;
        public String orderDeliveryFeeAccounting;
        public String orderPaymentFlag;
        public String installmentPayCount;
        public String payPrice;
        public String batchName;
        public String batchNum;
        public String soInstallmentId;
        public String orderType;
        public String groupBuyOrderCode;
        public String groupBuyTitle;
        public String packageCode;
        public int canAfterSale;
        public String isDeleted;
        public String installmentCode;
        public PresellBean presell;
        public String orderFreeFlag;
        public String canRefund;
        public String canUndoRefund;
        public String orderPromotionStatus;
        public String versionNo;
        public int orderPromotionType;
        public String invoiceStatus;
        public String canSupplementInvoice;
        public String canRedrawInvoice;
        public ReceiverBean receiver;
        public String payGateway;
        public String deliveryModeName;
        public double promotionAmount;
        public double taxAmount;
        public String productAmount;
        public String orderPaidByCard;
        public String orderPaidByCoupon;
        public String orderPaidByPoString;
        public String orderPaidByCommission;
        public String orderPaidByUcard;
        public String orderVipDiscount;
        public int cancelTime;
        public String deliverCode;
        public String remark;
        public String deliveryTimeStr;
        public String batchPayList;
        public String groupBuyLimitNum;
        public String groupCreateTime;
        public String standard;
        public InvoiceBean invoice;
        public String shipTimeStr;
        public String deliveryExpressNbr;
        public String parentOrderCode;
        public String paymentTimeStr;
        public String receiveTimeStr;
        public String orderRefund;
        public String signDescribe;
        public String confirmReceiveTime;
        public String undoCancel;
        public String remarkMerchant2user;
        public String usedYidou;
        public String orderStatusName;
        public String orderNeedCsName;
        public String orderCreateTimeStr;
        public List<?> packageList;
        public List<ChildOrderListBean> childOrderList;

        public static class PresellBean {
            /**
             * status : null
             * totalPrice : null
             * downPrice : null
             * offsetPrice : null
             * finalPayment : null
             * startTime : null
             * endTime : null
             * deliveryTime : null
             * orderPaidByAccount : null
             */

            public String status;
            public String totalPrice;
            public String downPrice;
            public String offsetPrice;
            public String finalPayment;
            public String startTime;
            public String endTime;
            public String deliveryTime;
            public String orderPaidByAccount;
        }

        public static class ReceiverBean {
            /**
             * receiverId : 91865
             * receiverName : 周佳伟
             * countryId : null
             * countryName : null
             * provinceId : 10
             * provinceName : 上海
             * cityId : 110
             * cityName : 上海市
             * areaId : 1135
             * areaName : 虹口区
             * detailAddress : 周家嘴路286号202室
             * receiverMobile : 15618090292
             * receiverPhone : null
             * exactAddress : null
             * sex : null
             */

            public String receiverId;
            public String receiverName;
            public String countryId;
            public String countryName;
            public String provinceId;
            public String provinceName;
            public String cityId;
            public String cityName;
            public String areaId;
            public String areaName;
            public String detailAddress;
            public String receiverMobile;
            public String receiverPhone;
            public String exactAddress;
            public String sex;

            public String getLocation() {
                return provinceName + cityName + areaName + detailAddress;
            }
        }


        public static class InvoiceBean {
            /**
             * id : 1035050000036075
             * invoiceType : 1
             * invoiceTitleType : 2
             * invoiceTitleContent : 生生世世
             * isNeedDetails : null
             * invoiceContentId : 1
             * invoiceContent : 食品
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
             * takerMobile : 15618090292
             * takerEmail : null
             * invoiceMode : null
             * invoiceList : null
             */

            public String id;
            public String invoiceType;
            public String invoiceTitleType;
            public String invoiceTitleContent;
            public String isNeedDetails;
            public String invoiceContentId;
            public String invoiceContent;
            public String unitName;
            public String taxpayerIdentificationCode;
            public String registerAddress;
            public String registerPhone;
            public String bankDeposit;
            public String bankAccount;
            public String receiverName;
            public String receiverMobile;
            public String receiverCountry;
            public String receiverProvince;
            public String receiverCity;
            public String receiverArea;
            public String receiverAddress;
            public String auditStatus;
            public String takerMobile;
            public String takerEmail;
            public String invoiceMode;
            public String invoiceList;
        }

        public static class ChildOrderListBean {
            /**
             * orderCode : 171213835308887771
             * orderCreateTime : null
             * amount : null
             * totalCount : 0
             * merchantId : null
             * merchantName : 来伊份
             * merchantType : null
             * merchantTelephone : null
             * orderDeliveryFee : 0.0
             * packageList : [{"packageCode":null,"productList":[{"soItemId":1035050000036061,"merchantId":101,"productId":1008020801006842,"mpId":1008020801006843,"name":"精制猪肉脯128g","picUrl":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","price":19.8,"attrs":null,"num":2,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url100x100":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url120x120":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url160x160":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url220x220":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url300x300":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url400x400":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url500x500":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url800x800":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0"},{"soItemId":1035050000036062,"merchantId":101,"productId":1007020801001956,"mpId":1007020801001957,"name":"风味鸭脖118g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160.jpg","price":11.8,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg"},{"soItemId":1035050000036063,"merchantId":101,"productId":1006020801002703,"mpId":1006020801002704,"name":"照烧鸡翅中200g","picUrl":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg","price":31.9,"attrs":null,"num":5,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"},{"soItemId":1035050000036064,"merchantId":101,"productId":1008020801002501,"mpId":1008020801002502,"name":"天天坚果-萌萌派25g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180.jpg","price":5.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg"}]}]
             * remark : null
             * remarkMerchant2user : null
             * deliveryModeCode : 1937
             * deliveryModeName : 来伊份商城配送
             * isTakeTheir : 0
             * shopInfo : null
             * orderDeliveryFeeAccounting : 0.0
             * pickMobile : null
             * pickName : null
             * pickAddress : null
             * promiseDate : null
             */

            public String orderCode;
            public String orderCreateTime;
            public String amount;
            public String totalCount;
            public String merchantId;
            public String merchantName;
            public String merchantType;
            public String merchantTelephone;
            public String orderDeliveryFee;
            public String remark;
            public String remarkMerchant2user;
            public String deliveryModeCode;
            public String deliveryModeName;
            public String isTakeTheir;
            public String shopInfo;
            public String orderDeliveryFeeAccounting;
            public String pickMobile;
            public String pickName;
            public String pickAddress;
            public String promiseDate;
            public List<PackageListBean> packageList;

            public static class PackageListBean {
                /**
                 * packageCode : null
                 * productList : [{"soItemId":1035050000036061,"merchantId":101,"productId":1008020801006842,"mpId":1008020801006843,"name":"精制猪肉脯128g","picUrl":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","price":19.8,"attrs":null,"num":2,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url100x100":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url120x120":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url160x160":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0","url220x220":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url300x300":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0","url400x400":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url500x500":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0","url800x800":"http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0"},{"soItemId":1035050000036062,"merchantId":101,"productId":1007020801001956,"mpId":1007020801001957,"name":"风味鸭脖118g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160.jpg","price":11.8,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20170217/a1548778-df22-4abf-852b-95d80eefe160_l.jpg"},{"soItemId":1035050000036063,"merchantId":101,"productId":1006020801002703,"mpId":1006020801002704,"name":"照烧鸡翅中200g","picUrl":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg","price":31.9,"attrs":null,"num":5,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1510563159531_74.29174395125452_13564a69-0d7f-451e-b1b6-3e10bf6e1704.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"},{"soItemId":1035050000036064,"merchantId":101,"productId":1008020801002501,"mpId":1008020801002502,"name":"天天坚果-萌萌派25g","picUrl":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180.jpg","price":5.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":0,"url60x60":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url100x100":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url120x120":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url160x160":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_s.jpg","url220x220":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url300x300":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_m.jpg","url400x400":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url500x500":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg","url800x800":"http://images.laiyifen.com/trailbreaker/product/20160810/8740b6c0-b76d-4068-a24b-91443d9f5180_l.jpg"}]
                 */

                public String packageCode;
                public List<ProductListBean> productList;

                public static class ProductListBean {
                    /**
                     * soItemId : 1035050000036061
                     * merchantId : 101
                     * productId : 1008020801006842
                     * mpId : 1008020801006843
                     * name : 精制猪肉脯128g
                     * picUrl : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0
                     * price : 19.8
                     * attrs : null
                     * num : 2
                     * isGift : 0
                     * standard : null
                     * managementState : 1
                     * securityList : null
                     * propertyTags : null
                     * commentStatus : 0
                     * combinedProductMpId : null
                     * seriesParentId : null
                     * usedYidou : 0.0
                     * url60x60 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0
                     * url100x100 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0
                     * url120x120 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0
                     * url160x160 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_s.jpg?v=1.0
                     * url220x220 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0
                     * url300x300 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_m.jpg?v=1.0
                     * url400x400 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0
                     * url500x500 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0
                     * url800x800 : http://images.laiyifen.com/laiyifen/2012/71218/71218_01_l.jpg?v=1.0
                     */

                    public String soItemId;
                    public String merchantId;
                    public String productId;
                    public String mpId;
                    public String name;
                    public String picUrl;
                    public String price;
                    public String attrs;
                    public String num;
                    public String isGift;
                    public String standard;
                    public String managementState;
                    public String securityList;
                    public String propertyTags;
                    public String commentStatus;
                    public String combinedProductMpId;
                    public String seriesParentId;
                    public String usedYidou;
                    public String url60x60;
                    public String url100x100;
                    public String url120x120;
                    public String url160x160;
                    public String url220x220;
                    public String url300x300;
                    public String url400x400;
                    public String url500x500;
                    public String url800x800;
                }
            }
        }
    }
}
