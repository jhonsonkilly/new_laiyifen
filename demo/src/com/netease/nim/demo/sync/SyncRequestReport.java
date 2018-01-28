package com.netease.nim.demo.sync;

import android.content.Context;

import com.google.gson.Gson;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.main.model.SortModel;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.sync.util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SevenCheng
 */

public class SyncRequestReport {
    private Context Mcontext;

    private static SyncRequestReport instance = null;

    public static SyncRequestReport getInstance(Context mcontext) {

        if (null == instance) {
            instance = new SyncRequestReport(mcontext);
        }
        return instance;
    }

    public SyncRequestReport(Context mcontext) {
        this.Mcontext = mcontext.getApplicationContext();
    }

    /**
     * 创建报文体
     *
     * @param body
     * @return
     */
    private String CreateRepot(Map<String, Object> body) {

//        String appid = Common.appid;
        String appkey = Common.appkey;
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();

        //        data.put("head", head);
        //        head.put("version", "2.0");
        //        head.put("appid", appid);
        //        data.put("body", body);
        /*try {
            head.put("sign", DataUtil.signRequest(appid, body, appkey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        Gson gson = new Gson();
        String str = gson.toJson(body);

        return str;
    }


    /**
     * 查看通讯录好友
     */
    public String ReportINTERFACE001(List<SortModel> model) {

        Map<String, Object> body = new HashMap<>();
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (SortModel sortModel : model) {
            Map<String, Object> body2 = new HashMap<>();
            body2.put("name", sortModel.getName());
            body2.put("mobile", sortModel.getMobile());
            maps.add(body2);
        }
        body.put("accid", DemoCache.getAccount());
        body.put("all_mail", maps);


        return CreateRepot(body);
    }

}
