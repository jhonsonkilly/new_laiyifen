package com.ody.p2p.shopcart;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface ShopCartPressenter {

    void shopCartData();

    void recommedData(String mpids);

    void selectAll(String checkedStr);

    void editShopcartNum(ShopCartBean.ProductList mpid, int number,int flag);

    void deleteProduct(ShopCartBean.ProductList product, int postion);

    void attentionProduct(ShopCartBean.ProductList product);

    void clearFailProduct();

    void checkedItem(ShopCartBean.ProductList product);

    void addFavorite(String entityIds);

    void getProperty(String productID);

    void getCouponBean(String overseaId);

    void toConfirmorder();

    void deleteSelected(String mpIds);

    void UpdatepProduct(String oldMpId, String newMpId, int num);

    void UpdateGift(long promotionId, String mpIds);

    void prepareCheck();

    void getAdvertisement();

    void getToPassAble(String profomtionId);

}
