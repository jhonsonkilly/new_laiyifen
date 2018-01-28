package com.ody.p2p.main.order;

/**
 * Created by ${tang} on 2016/12/13.
 */

public interface ChooseStrorePresenter {
    void getStoreList(int pageNum,String keyword,String lat,String longt);

    void saveShop(String merchantId,String deliveryCode,String pickSiteId,String phoneNum);
}
