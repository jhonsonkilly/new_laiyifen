package com.ody.p2p.eventbus;

/**
 * Created by Administrator on 2016/12/5.
 */

public class JsEventMessage {
    public static final String FUNCTION_CLEAR_CACHE = "clearCache";
    public static final String FUNCTION_GET_CACHE = "getCache";
    public static final String FUNCTION_SHARE = "share";
    public static final String FUNCTION_PURCHASE = "purchase";
    public static final String ADDRESS_MANAGER = "addressManager";
    public static final String FUNCTION_GET_CAMERA = "getCamera";
    public static final String FUNCTION_GET_LOCATION = "getLocation";
    public static final String FUNCTION_GET_VERSION_NAME = "getVersionName";
    public static final String FUNCTION_PAGE_REFRESH = "pageRefresh";
    public static final String FUNCTION_UPLOAD_PHOTO = "uploadPhoto";
    public static final String FUNCTION_LOGIN = "login";
    public static final String FUNCTION_LOGOUT = "logout";
    public static final String FUNCTION_HIDE_TITLE = "hiddenHead";
    public static final String FUNCTION_BACK = "webViewBack";
    public static final String FUNCTION_PAGEVIEW = "webPageView";
    public static final String FUNCTION_NAVIGATE = "navigate";
    public static final String FUNCTION_GET_MESSAGE_INFO = "getMessageInfo";
    public static final String FUNCTION_CALL_CUSTOMS_SERVICE = "callcustomservice";
    public static final String FUNCTION_ADDTOCART = "addShoppingCart";
    public static final String FUNCTION_CALL_CMS_SHARE = "cmsShare";

    public static final String FUNCTION_BINDTHIRD_QQ = "bindThirdPlatformQQ";
    public static final String FUNCTION_BINDTHIRD_WX = "bindThirdPlatformWX";
    public static final String FUNCTION_SET_ALARM = "setAlarm";
    public static final String FUNCTION_CANCEL_ALARM = "cancelAlarm";
    public static final String FUNCTION_STATUS_ALARM = "alarmState";
    public static final String FUNCTION_RED_PACKAGE = "goredPod";
    public static final String FUNCTION_STATUS_AREA_CODE = "getAreaCode";

    public static final String FUNCTION_GO_BACK_HOME = "goBackHome";

    public static final String TO_SHOPCART = "shoppingCar";//确定没有T，不要怀疑

    public static final String ADD_CART_SUCCESS = "addCartSuccess";//确定没有T，不要怀疑


    public static final String SET_STATUS_BAR_COLOR = "setStatusBarColor";//设置状态栏颜色

    public static final String POSTER = "poster";//保存海报

    public String function;
    public String params;
    public String callback;
    public int msgTag;
}
