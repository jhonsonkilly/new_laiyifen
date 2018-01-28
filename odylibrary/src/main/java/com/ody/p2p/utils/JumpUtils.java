package com.ody.p2p.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.webactivity.UrlBean;
import com.ody.p2p.webactivity.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;

/**
 * Created by lxs on 2016/8/26.
 */
public class JumpUtils {
    public static final String FFRAGMENT_LOGIN = "fragment_login";
    public static final String SCREEN_RECOMMEND = "screen_recommend";

    public static final String SELECT_ADDRESS = "selectAddress";
    public static final String ADDRESS_MANAGER = "addressManager";
    public static final String EDIT_ADDRESS = "editaddress";
    public static final String SEARCH = "search";
    public static final String SEARCH_RESULT = "searchresult";
    public static final String ARRIVAL_NOTIF = "arrivalnotif";
    public static final String MYPROFIT = "myprofit";
    public static final String SHOP_EVALUATE = "shopevaluate";
    public static final String PROFIT_DETAIL = "profitdetail";
    public static final String SUBSHOP_COMMISSION = "subshopcommission";
    public static final String SUBSHOP_COMMISSION_DETAIL = "subshopcommissiondetail";
    public static final String CASH_ACTIVITY = "cashactivity";
    public static final String CASH_RECORD = "cashrecord";
    public static final String FIRST_COMMISSION = "firstcommission";
    public static final String MYLIKE = "mylike";
    public static final String DESCOLLECT = "descollect";
    public static final String SETTING = "setting";
    public static final String MAIN = "main";
    public static final String LOGIN = "login";
    public static final String REGISTERFIRST = "registerFirst";
    public static final String FORGETPSD = "forgetPsd";
    public static final String SMSLOGIN = "smsLogin";
    public static final String PRODUCTDETAIL = "productdetail";
    public static final String CONFIRMORDER = "orderConfirm";
    public static final String ABOUTUS = "aboutus";
    public static final String PAYONLINE = "pay";
    public static final String ORDERLIST = "orderlist";
    public static final String ORDERDETAIL = "orderdetail";
    public static final String AFTERSALEINFO = "aftersaleInfo";
    public static final String SCANHISTORY = "scanhistory";
    public static final String AFTERSALE = "aftersale";
    public static final String LOGISTICS = "logistics";
    public static final String PAYFAIL = "payFail";
    public static final String PAYSUCCESS = "paySuccess";
    public static final String COUPON = "coupon";
    public static final String MESSAGE = "message";
    public static final String SYSMESSAGELIST = "sysmessagelist";
    public static final String FEEDBACK = "feedback";
    public static final String HELPCENTER = "helpcenter";
    public static final String FANSROLLE = "fansrolle";
    public static final String REPORTFORMS = "reportforms";
    public static final String MARKETING = "marketing";
    public static final String USECOUPON = "usecoupon";
    public static final String ADDCOUPON = "addCoupon";
    public static final String ARTICLEINFO = "articleinfo";
    public static final String RECHARGESCORE = "rechargescore";
    public static final String DSPERSONALINFO = "dspersonalinfo";
    public static final String DSMODIFYPSD = "dsmodifypsd";
    public static final String DSCHANGEPHONE = "dschangephone";
    public static final String DSCHANGEPHONESEC = "dschangephonesec";
    public static final String EDITBANKCARD = "editbankcard";
    public static final String REGISTER = "register";
    public static final String DSREGISTERFIRST = "dsregisterfirst";
    public static final String DSREGISTERSECOND = "dsregistersecond";
    public static final String DSFORGETPSDFIRST = "dsforgetpsdfirst";
    public static final String DSFORGETPSDSECOND = "dsforgetpsdsecond";
    public static final String DSADDPSD = "dsaddpsd";
    public static final String BANKLIST = "banklist";
    public static final String BANKCARDLIST = "bankcardlist";
    public static final String SELFSALESCOMMISSION = "selfsalescommission";
    public static final String PARTNER = "partner";
    public static final String GOODSMANAGE = "goodsmanage";
    public static final String H5 = "h5";
    public static final String UNIONBINDPHONE = "unionbindphone";
    public static final String USERPROTOCOL = "userprotocol";
    public static final String DSMYSCORE = "dsmyscore";
    public static final String SWEEP = "sweep";
    public static final String QR = "qr";
    public static final String SHOPORDER = "shoporder";
    public static final String SHOPORDERDETAIL = "shoporderdetail";
    public static final String ONLINESERVICE = "onlineservice";
    public static final String FUNC = "func";
    public static final String SHOPCART = "shoppingCart";
    public static final String DS_BILLES = "ds_Billes";
    public static final String DS_BILLESTOSERCH = "ds_BillesToserch";
    public static final String REFOUNDLIST = "refoundlist";
    public static final String ORDERSEARCH = "ordersearch";
    public static final String LIVELIST = "liveList";
    public static final String SHOWIMAGE = "showimage";
    public static final String CHOOSEPAYWAY = "choosepayway";
    public static final String INVOICE = "invoice";
    public static final String PICCHOOSE = "picchoose";
    public static final String FEEDBACKLIST = "feedbacklist";
    public static final String FEEDBACKDETAIL = "feedbackdetail";
    public static final String CHOOSE_LOGISTICS = "chooselogistics";
    public static final String BIND_GIFTCARD = "bindgiftcard";
    public static final String GIFTCARD = "giftcard";
    public static final String USE_GIFTCARD = "usegiftcard";
    public static final String GIFTCARD_CONSUMER = "giftcardconsumer";
    public static final java.lang.String CT_SEARCH_RESULT = "ct_searchresult";
    public static final String ACTIVITY_DETAILS = "activity_details";
    public static final java.lang.String ACTIVITY_GPS = "activity_gps";
    public static final java.lang.String ACTIVITY_AWARD = "activity_award";
    public static final java.lang.String ACTIVITY_JOIN = "activity_join";
    public static final java.lang.String LOCATION = "location";
    public static final String DISTRIBUTION = "distributionactivity";//配送方式界面
    public static final String CHOOSE_STORE = "choose_store";
    public static final String RED_PACKET = "red_packet";
    public static final String SCAN_LIST = "scan_list";

    public static final String POINT_CARD = "point_card";
    public static final String CHOOSE_CONTACTS = "contacts";//选择联系人
    public static final String PAY_FOR_LEISURELY = "PAY_FOR_LEISURELY";//悠点卡充值页面
    public static final String ADD_YCARD = "ADD_YCARD";//绑定伊点卡页面
    public static final String UPLEVEL = "uplevel";
    public static final String HOME = "lyf://home";
    public static final String MY = "lyf://myhome";
    public static final String YWTPAY = "ywtpay";
    public static final String MY_WALLET = "my_wallet";
    public static final String LEISURELY = "leisurely";

    public static final String PERSONAL_DATA = "personal_data";//个人资料
    public static final String PERSONAL_SETDATA = "personal_setdata";//设置个人资料
    public static final String QUERY_YIDIAN_CARD = "query_yidian_card";//查询伊点卡
    public static final String QUERY_YIDIAN_CARD_RESULT = "query_yidian_card_result";//查询伊点卡结果页
    public static final String MESSAGE_CENTER = "message_center";//消息中心
    public static final String SEARCH_STORE = "search_store";//店铺查询
    public static final String STORE_DETAIL = "store_detail";//店铺详情
    public static final String STORE_NAVIGATION = "store_navi";//店铺导航
    public static final String YIDIAN_CARD = "yidian_card";//伊点卡
    public static final String YIDOU = "yidou";//伊豆
    public static final String LAIYIFEN_COUPON = "laiyifen_coupon";//优惠券

    public static final String LYFSCORE = "lyfscore";//积分
    public static final String LYF_SCORE_EXCHANGE = "lyf_score_exchange";//兑换积分
    public static final String PAY_RECORD = "pay_record";//充值记录;
    public static final String PAY_DETAILS = "pay_details";//充值记录;
    public static final String YIDOU_RULE = "yidou_rule";
    public static final String COMMISSION_CODE = "commission_code";
    public static final String SHOP_HOME = "shop_home";
    public static final String SHOP_INFO = "shop_info";
    public static final String SHOP_CATEGORT = "shop_category";//店铺分类
    public static final String STORE = "shop_home";

    public static final String INVITE_FRIENDS = "inviteFriend";//邀请好友

    public static final String QIANGGOU = "qianggou";
    public static final String FLASHSALE = "flashSales";

    public static void ToWebActivity(Context context, String loadUrl) {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.SCHEME + "://" + H5);
        activityRoute.withParams("loadUrl", loadUrl).open();
    }

    public static void ToWebActivity(String activityTag, String loadUrl, int showType, int Res, String title) {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.SCHEME + "://" + activityTag);
        if (activityRoute != null) {
            activityRoute.withParams("loadUrl", loadUrl).withParams("showType", showType).withParams("titleRes", Res).withParams("titleContent", title).open();
        }
    }

    public static void ToHelpWebActivity(String activityTag, String loadUrl, int showType, int Res, String title, int help) {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.SCHEME + "://" + activityTag);
        if (activityRoute != null) {
            activityRoute.withParams("loadUrl", loadUrl).withParams("showType", showType).withParams("titleRes", Res).withParams("titleContent", title).withParams("help", help).open();
        }
    }

    public static void ToActivity(String activityTag) {
        ActivityRoute activityRoute = null;
        if(!StringUtils.isEmpty(activityTag)){
            if (activityTag.contains(OdyApplication.SCHEME + "://")) {
                activityRoute = (ActivityRoute) Router.getRoute(activityTag);
            } else if (activityTag.startsWith("http")) {
                ToWebActivity(activityTag, WebActivity.NO_TITLE, 0, "");
            } else {
                activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.getRouterUrl(activityTag));
            }
            if (activityRoute != null) {
                activityRoute.open();
            }
        }
    }

    public static void ToActivity(String activityTag, Bundle extras) {
        ActivityRoute activityRoute = null;
        if (activityTag.contains(OdyApplication.SCHEME + "://")) {
            activityRoute = (ActivityRoute) Router.getRoute(activityTag);
        } else {
            activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.getRouterUrl(activityTag));
        }
        if (activityRoute != null) {
            activityRoute.addExtras(extras);
            activityRoute.open();
        }
    }

    public static void ToWebActivity(String url, int showType, int Res, String title) {
        if (url.startsWith("http")) {
            if (url.startsWith("http://laiyifen.umaman.com/zhibo")) {
                if (StringUtils.isEmpty(OdyApplication.getString(Constants.USER_LOGIN_UT, ""))) {
                    JumpUtils.ToActivity(JumpUtils.LOGIN);
                    return;
                } else {
                    try {
                        url += "&nickname=" + URLEncoder.encode(OdyApplication.getString(Constants.NICK_NAME, ""), "UTF-8")
                                + "&mobile=" + URLEncoder.encode(OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, ""), "UTF-8")
                                + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8");
                    } catch (Exception ex) {

                    }
                }
            }
            ToWebActivity("h5", url, showType, Res, title);
        } else if (url.startsWith(OdyApplication.SCHEME)) {
            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(url);
            if (activityRoute != null) {
                activityRoute.withParams("showType", showType).withParams("titleRes", Res).withParams("titleContent", title).open();
            }
        } else {
//            ToastUtils.showShort("地址不合法");
        }
    }

    public static void ToActivityFoResult(String activityTag, Bundle extras, int code, Activity activity) {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.getRouterUrl(activityTag));
        if (activityRoute != null) {
            if (null != extras) {
                activityRoute.addExtras(extras);
            }
            activityRoute.withOpenMethodStartForResult(activity, code).open();
        }
    }


    public static void ToActivityFoResult(String activityTag, Bundle extras, int code, Fragment fragment) {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(OdyApplication.getRouterUrl(activityTag));
        if (activityRoute != null) {
            activityRoute.addExtras(extras);
            activityRoute.withOpenMethodStartForResult(fragment, code).open();
        }
    }

    public static UrlBean getUrlBean(String url) {
        try {
            url = URLDecoder.decode(url, "utf-8");
            String urlBody = "";
            if (url.contains("body=")) {
                urlBody = url.split("body=")[1];
            } else {
                urlBody = url;
            }
            Gson gson = new Gson();
            UrlBean bean = null;
            if (urlBody != null && urlBody.length() > 0 && urlBody.contains("{") && urlBody.contains("}")) {
                bean = gson.fromJson(urlBody, UrlBean.class);
                return bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UrlBean();
    }

    public static String getValueFromUrl(String url, String key) {
        try {
            url = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String urlBody = Uri.parse(url).getQueryParameter("body");
        try {
            if (!StringUtils.isEmpty(urlBody)) {
                JSONObject jsonObject = new JSONObject(urlBody);
                return jsonObject.optString(key);
            }
        } catch (JSONException e) {
            Log.d("samuel", e.getMessage());
        }
        return "";
    }

    public static void toActivity(FuncBean.Data.AdSource bean) {
        if (null == bean || StringUtils.isEmpty(bean.linkUrl)) {
            return;
        }
        if (bean.linkUrl.contains("http")) {
            ToWebActivity(bean.linkUrl, WebActivity.NO_TITLE, -1, bean.name);
        } else {
            ToActivity(bean.linkUrl);
        }
    }


    public static void toActivity(Ad bean) {
        if (null == bean || StringUtils.isEmpty(bean.linkUrl)) {
            return;
        }
        if (bean.linkUrl.contains("http")) {
            ToWebActivity(bean.linkUrl, WebActivity.NO_TITLE, -1, bean.name);
        } else {
            ToActivity(bean.linkUrl);
        }
    }
}
