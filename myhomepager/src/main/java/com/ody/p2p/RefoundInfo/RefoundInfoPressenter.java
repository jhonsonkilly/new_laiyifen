package com.ody.p2p.RefoundInfo;

/**
 * Created by ody on 2016/7/1.
 */
public interface RefoundInfoPressenter {

    void getOrderInfo(String orderAfterSalesId,String returnCode);

    void cancelReturnProduct(String returnCode);

    void courierNumber(String courierNumber,String logisticsCompany,String orderAfterSalesId);

    void confirmReceivePro(String returnId);

    void isAftersale(String orderCode);
}
