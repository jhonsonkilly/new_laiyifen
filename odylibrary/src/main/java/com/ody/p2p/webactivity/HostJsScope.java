/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */

package com.ody.p2p.webactivity;

import android.webkit.WebView;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;

//HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
public class HostJsScope {


    public static void callActionGoodsDetail(WebView view, final String params) {
//        Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
//        JSONObject object = new JSONObject();
        try {
            JSONObject m = new JSONObject(params);
//            String productCode = "";
//            if (m.has("productCode")) {
//                productCode = m.optString("productCode");
//                object.put("productCode", productCode);
//            }
                int mpsId = 0;
            if (m.has("mapIds")) {
                mpsId = m.optInt("mapIds");
//                object.put("mpsId", String.valueOf(mpsId));
            }
//            intent.putExtra("params", object.toString());
//            view.getContext().startActivity(intent);
            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://shoppingdetail");
            activityRoute.withParams(Constants.SP_ID,mpsId + "").open();
        } catch (JSONException e) {
        }
    }

    public static void callActionGoodsDetailByProductId(WebView view, final String params) {
//        Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
//        JSONObject object = new JSONObject();
        try {
            JSONObject m = new JSONObject(params);
//            String productCode = "";
//            if (m.has("productCode")) {
//                productCode = m.optString("productCode");
//                object.put("productCode", productCode);
//            }
            int mpsId = 0;
            if (m.has("mapIds")) {
                mpsId = m.optInt("mapIds");
//                object.put("mpsId", String.valueOf(mpsId));
            }
//            intent.putExtra("params", object.toString());
//            view.getContext().startActivity(intent);
            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://shoppingdetail");
            activityRoute.withParams(Constants.SP_ID,mpsId + "").open();
        } catch (JSONException e) {
        }
    }

    public static String outPutCmsPlatform(WebView view) {
        Map<String, String> params = new HashMap<>();
        params.put("appName", OdyApplication.SCHEME);
        params.put("companyId", OdyApplication.COMPANYID);
        params.put("sessionId", OdySysEnv.getSessionId());
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("platfromId", "0");
        params.put("merchantId", OdyApplication.getValueByKey("merchantId", ""));
        params.put("areaCode", OdyApplication.getValueByKey("areaCode", ""));
        return new Gson().toJson(params);
    }

}