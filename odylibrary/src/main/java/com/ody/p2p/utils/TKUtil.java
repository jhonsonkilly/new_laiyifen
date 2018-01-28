package com.ody.p2p.utils;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sun on 2016/9/1.
 */
public class TKUtil {

    public static void upload(Context context, String pageCode, int trackType) {
        upload(context, pageCode, "", "", "", trackType);
    }
    /**
     *
     * @param context 上下文
     * @param pageCode 页面类型()
     * @param merchantid 分销商id
     * @param mpid 商品id
     * @param isvip 是否为会员
     * @param trackType 1页面加载（PV、UV） 2点击事件
     */
    public static void upload(Context context, String pageCode, String merchantid, String mpid, String isvip, int trackType) {
//        {
//            "accountId": "1", //德升传1
//                "appversion": "", //App版本
//                "cityId": "", //城市ID
//                "companyId": "10", //德生传10
//                "countryId": "", //国家ID
//                "currentTime": "2016-8-16 16:44:11", //发送时间，请格式化为yyyy-mm-dd HH24:mi:ss
//                "currentUrl": "http://cms.yhd.com/sale/NXeqtztMqNJ", //当前URL
//                "extMap": { //扩展信息 德升需要额外增加分销商ID merchantid，商品详细页增加mpid，若当前登录的用户为该商家的vip用户，增加isvip固定1
//            "isvip": "1",
//                    "merchantid": "5135",
//                    "mpid": "111211"
//        },
//            "guid": "8BT7XSXC8C9HS633CA9GR2ASHNGAQM32EYJV", //用来唯一标识一个设备，H5从cookie取，APP从取设备号
//                "ip": "", //ip
//                "lat": "", //经度
//                "lng": "", //纬度
//                "nettype": "", //网络类型
//                "pageCode": "index", //pagecode 见后面的说明
//                "phoneBrand": "", //手机品牌
//                "phoneModel": "", //手机型号
//                "phoneNum": "", //手机号码
//                "platform": "Win32", //操作系统版本
//                "provider": "", //网络服务运营商
//                "provinceId": "", //省份ID
//                "refUrl": "http://www.yhd.com/", //上一个URL
//                "screen": "1920x1080", //分辨率 用小x隔开
//                "sessionId": "G1RBWDDFUZHV1V83GWVQZVSR3UHFM8B1", //会话标识
//                "systemId": "1", //德升传1
//                "trackType": "1", //track类型	1页面加载（PV、UV） 2点击事件
//                "userAgent": "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0", //浏览器信息
//                "userId": "112121121", //用户ID
//                "utmCampaign": "", //外站跳入用
//                "utmContent": "", //外站跳入用
//                "utmMedium": "", //外站跳入用
//                "utmSource": "", //外站跳入用
//                "utmTerm": "" //外站跳入用
//        }



        Map<String, Object> contents = new HashMap<>();
        contents.put("accountId", "1");
        contents.put("appversion", OdyApplication.VERSION_NAME);
        contents.put("companyId", "30");
        contents.put("currentTime", DateUtils.getTodayTime(System.currentTimeMillis()));
        contents.put("cityId", "10");
        Map<String, Object> extMap = new HashMap<>();
        extMap.put("isvip", isvip);
        extMap.put("merchantid", merchantid);
        extMap.put("mpid", mpid);

        contents.put("extMap", extMap);
        contents.put("guid", OdyApplication.getGUID());
        contents.put("nettype", NetworkUtils.getInstance().getNetworkType());
        contents.put("pageCode", pageCode);
        contents.put("phoneBrand", Build.MANUFACTURER);
        contents.put("phoneModel", Build.MODEL);
        contents.put("platform", Build.VERSION.RELEASE);
        contents.put("provider", NetworkUtils.getInstance().getSimOperatorName());
        contents.put("screen", ScreenUtils.getScreenWidth(context) + "x" + ScreenUtils.getScreenHeight(context));
        contents.put("sessionId", OdyApplication.getSessionId());
        contents.put("systemId", "1");
        contents.put("trackType", String.valueOf(trackType));
        Map<String, String> params = new HashMap<>();
        params.put("content", new Gson().toJson(contents));
        OkHttpManager.postAsyn(Constants.TRACK_UPLOAD, new OkHttpManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        }, params);

    }
}
