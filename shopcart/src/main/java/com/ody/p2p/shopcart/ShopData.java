package com.ody.p2p.shopcart;

import com.ody.p2p.recmmend.Recommedbean;

import java.util.List;

/**
 * Created by ody on 2016/6/14.
 */
public class ShopData {

    int itemType;//-1商家 0保税仓  1促销  2普通商品 3赠品 4失效商品 5清空失效商品 6猜你喜欢标签 7推荐列表 99购物车为空 100分割

    ShopCartBean.MerchantList merchantList;//商家

    ShopCartBean.Overseas overseas;//保税仓

    ShopCartBean.Promotion promotion;//促销信息

    ShopCartBean.ProductList productList;//普通商品

    ShopCartBean.GiftProducts giftProducts;//赠品

    List<ShopCartBean.GiftProductList> giftProductList;//赠品组

    ShopCartBean.ProductList failureProducts;//失效商品

    List<Recommedbean.DataList> recommed;//猜你喜欢

    public ShopCartBean.GiftProducts getGiftProducts() {
        return giftProducts;
    }

    public void setGiftProducts(ShopCartBean.GiftProducts giftProducts) {
        this.giftProducts = giftProducts;
    }

    public List<ShopCartBean.GiftProductList> getGiftProductList() {
        return giftProductList;
    }

    public void setGiftProductList(List<ShopCartBean.GiftProductList> giftProductList) {
        this.giftProductList = giftProductList;
    }

    public ShopCartBean.MerchantList getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(ShopCartBean.MerchantList merchantList) {
        this.merchantList = merchantList;
    }

    public List<Recommedbean.DataList> getRecommed() {
        return recommed;
    }

    public void setRecommed(List<Recommedbean.DataList> recommed) {
        this.recommed = recommed;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public ShopCartBean.Overseas getOverseas() {
        return overseas;
    }

    public void setOverseas(ShopCartBean.Overseas overseas) {
        this.overseas = overseas;
    }

    public ShopCartBean.Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(ShopCartBean.Promotion promotion) {
        this.promotion = promotion;
    }

    public ShopCartBean.ProductList getProductList() {
        return productList;
    }

    public void setProductList(ShopCartBean.ProductList productList) {
        this.productList = productList;
    }


    public ShopCartBean.ProductList getFailureProducts() {
        return failureProducts;
    }

    public void setFailureProducts(ShopCartBean.ProductList failureProducts) {
        this.failureProducts = failureProducts;
    }
}
