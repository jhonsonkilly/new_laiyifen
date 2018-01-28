package com.lyfen.android.event;


public class EventbusMessage {
    public final static int REFRESH_UT = 999;          //刷新ut
    public final static int REFRESH_H5 = 1000;          //刷新ut
    public final static int FINISH_ACTIVITY = 1001;     //关闭activity
    public final static int PAY_STATUS = 1002;          //支付状态
    public final static int JUMP_TO_LOGIN = 1003;       //跳转到登录界面
    public final static int MSG_REFRESH = 1004;         //刷新消息
    public final static int CHANGE_AREA = 1005;    //刷新区域；
    public final static int FRUSH_FILTER = 1006;
    public static final int FRUSH_FILTER_STATUS = 1007;
    public static final int IS_SP = 1008;
    public static final int FRUSH_FILTER_SCROLLX = 1009;
    public static final int FIRST_LOGIN = 1010;
    public static final int GET_CART_COUNT = 10;  //获取购物车数量
    public static final int GET_COUPON = 7;//首次登录券

    @Override
    public String toString() {
        return "EventbusMessage{" +
                "flag=" + flag +
                '}';
    }

    public int flag;
}
