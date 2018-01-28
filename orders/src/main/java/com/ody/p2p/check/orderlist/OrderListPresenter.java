package com.ody.p2p.check.orderlist;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public interface OrderListPresenter {
    void orderlist(int pageNo, int status);

    //加上后面的两个参数是为了埋点获取参数
    void cancelOrder(String orderCode, String orderMoney, String deliveryFee);

    void confrimReceive(String orderCode);

    void setOrderListUrl(String orderListUrl);
}
