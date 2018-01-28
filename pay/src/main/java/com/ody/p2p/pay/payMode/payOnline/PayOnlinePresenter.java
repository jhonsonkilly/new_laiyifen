package com.ody.p2p.pay.payMode.payOnline;

/**
 * Created by ody on 2016/6/20.
 */
public interface PayOnlinePresenter {

    void getPayList(String orderCode);
    void getPayList(String orderCode,String orderType);

    void getPayInfoByNum(String orderId, String orderMoney, String userId, String paymentConfigId, String orderType);

    void getPayInfo(String paymentConfigId,String ordercode,String promotionId);

    void getPaytime(String orderCode);

    void getWalletSummary();
    void lyfSupportPayment();
}
