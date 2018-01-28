package com.ody.p2p.check.orderoinfo;

/**
 * Created by ody on 2016/6/20.
 */
public interface OrderInfoPressenter {

    void getOrderInfo(String orderCode);

    void cancleOrder(String orderCode);

    void confrimReceive(String orderCode);

    void afterSaleReasonlist();

    void deleteOrder(String orderCode);

    void applyRefund(String orderCode,String refundReasonId);
}
