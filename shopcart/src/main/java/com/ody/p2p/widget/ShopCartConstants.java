package com.ody.p2p.widget;

/**
 * Created by pzy on 2016/10/21.
 */
public class ShopCartConstants {
    //购物车版本
    public static String VERSION_1_4 = "1.4";
    public static String VERSION_1_3 = "1.3";
    public static String VERSION_1_2 = "1.2";

    //购物车itme
    /**
     * 商家
     */
    public static int ITEM_MERCHANT =-1;
    /**
     * 保税仓
     */
    public static int ITEM_OVERSEAS =0;
    /**
     * 促销
     */
    public static int ITEM_PROMOTION =1;
    /**
     * 购物车商品
     */
    public static int ITEM_PRODUCT =2;
    /**
     * 购物车赠品
     */
    public static int ITEM_GIF_PRODUCT =3;
    /**
     * 失效商品
     */
    public static int ITEM_FAILE_PRODUCT =4;
    /**
     * 操作失效商品（暂时只能清空）
     */
    public static int ITEM_CLEAN_FAILEPRODUCT =5;
    /**
     * 猜你喜欢title
     */
    public static int ITEM_TITLE_RECOMMEND =6;
    /**
     * 猜你喜欢列表
     */
    public static int ITEM_RECOMMEND =7;
    /**
     *购物车为空
     */
    public static int ITEM_NULDATA =99;
    /**
     * itme分割线（因为懒得计算item位置，所以加一个分割线的item）
     */
    public static int ITEM_NBSP =100;


}
