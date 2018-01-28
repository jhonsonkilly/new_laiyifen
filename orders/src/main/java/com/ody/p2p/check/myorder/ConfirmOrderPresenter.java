package com.ody.p2p.check.myorder;

import com.ody.p2p.views.scrollbanner.HeadLinesBean;

/**
 * Created by tangmeijuan on 16/6/13.
 */
public interface ConfirmOrderPresenter {
    void initOrder(int ignoreChange,String delMpIds);
    void submitorder();
    void quickpurchase(String mpid, String num, String merchantId,int ignoreChange);
    void showOrder();
    void initOldOrder(int ignoreChange,String items);
    void saveAddress(String addressId);
    void saveBrokerage(String usageAmount);
    void savePoints(int points);
    void savePayment(long paymentId);
    void saveCoupon(String couponId);
    void getAdvertisement();

    void saveEdian(int selected);
    void saveUdian(int selected);
}
