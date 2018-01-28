package com.ody.p2p.main.eventbus;

import com.tendcloud.appcpa.Order;
import com.tendcloud.appcpa.ShoppingCart;

//import org.datatist.sdk.data.CouponInfo;
//import org.datatist.sdk.data.OrderInfo;
//import org.datatist.sdk.data.ProductInfo;

import org.datatist.sdk.data.CouponInfo;
import org.datatist.sdk.data.OrderInfo;
import org.datatist.sdk.data.ProductInfo;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */
public class TaklingDataEventMessage {
    public static final String ONORDERPAYSUCC = "onOrderPaySucc";
    public static final String ONREGISTER = "onregister";
    public static final String ONLOGIN = "onLogin";
    public static final String ONVIEWITEM = "onViewItem";

    public static final String ONADDITEMTOSHOPPINGCART = "onAddItemToShoppingCart";

    public static final String ONPLACEORDER = "onPlaceOrder";
    public static final String ONRECEIVEDEEPLINK = "onReceiveDeepLink";
    public static final String ONVIEWSHOPPINGCART = "onViewShoppingCart";
    public static final String ONJPUSH = "onjpush";
    public static final String ONSEARCH = "onsearch";
    public static final String ONSPLASH = "onsplash";
    public static final String ONSPLASHCLICK = "onsplashclick";
    public static final String ONCHARGE = "oncharge";
    private String action = null;
    private Map<String, String> extra;
    private Order Order;

    public OrderInfo orderInfo;

    public CouponInfo couponInfo;

    public ProductInfo productInfo;


    private ShoppingCart ShoppingCart;

    public com.tendcloud.appcpa.ShoppingCart getShoppingCart() {
        return ShoppingCart;
    }

    public void setShoppingCart(com.tendcloud.appcpa.ShoppingCart shoppingCart) {
        ShoppingCart = shoppingCart;
    }

    public String getAction() {
        return action;
    }

    public TaklingDataEventMessage setAction(String action) {
        this.action = action;
        return this;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public TaklingDataEventMessage setExtra(Map extra) {
        this.extra = extra;
        return this;
    }

    //订单
    public com.tendcloud.appcpa.Order getOrder() {
        return Order;
    }

    public void setOrder(com.tendcloud.appcpa.Order order) {
        Order = order;
    }


    public void setNewOrder(OrderInfo orderInfo, CouponInfo couponInfo, ProductInfo productInfo) {
        this.orderInfo = orderInfo;
        this.couponInfo = couponInfo;
        this.productInfo = productInfo;

    }


}
