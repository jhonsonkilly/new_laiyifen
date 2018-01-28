package com.netease.nim.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.NimApplication;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.session.model.QuerySwitchModel;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SevenCheng
 *         权限开关
 */

public class JurisdictionButton {

    private static SharedPreferences sp;

    public static void setStuats(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences("JurisdictionButton", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value).commit();
    }

    public static void querySwitch(final Context context) {
        final SharedPreferences sp = context.getSharedPreferences("JurisdictionButton", Context.MODE_PRIVATE);
        String version = sp.getString("version", "");

        final SharedPreferences.Editor edit = sp.edit();

        Map body = new HashMap();
        body.put("token", Preferences.getUserMainToken());
        body.put("version", version);
        OKhttpHelper.send(context, new Gson().toJson(body), Common.AdapterPath + "querySwitch", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    QuerySwitchModel model = new Gson().fromJson(s, new TypeToken<QuerySwitchModel>() {
                    }.getType());
                    String version = model.getVersion();
                    edit.putString("version", version);

                    //1.成功 2.失败 3.无版本更新
                    switch (model.getStatus()) {
                        case "1":
                            List<QuerySwitchModel.SwitchDataBean> switchData = model.getSwitchData();
                            String dataCache = SaveListUtil.SceneList2String(switchData);
                            edit.putString("DATA", dataCache);
                            break;

                        case "2":

                            break;

                        case "3":

                            break;
                    }
                } catch (Exception e) {


                } finally {
                    edit.commit();
                    try {
                        List<QuerySwitchModel.SwitchDataBean> switchData = SaveListUtil.String2SceneList(sp.getString("DATA",""));
                        for (QuerySwitchModel.SwitchDataBean switchDataBean : switchData) {
                            String status = switchDataBean.getStatus() + "";
                            setStuats(context,switchDataBean.getCode(),status);
//                            switch (switchDataBean.getCode()) {
//                                case "QZ":
//                                    NimApplication.QZ = status;
//                                    break;
//
//                                case "QF":
//                                    setStuats(context,"QZ",status);
//                                    NimApplication.QF = status;
//
//                                    break;
//
//                                case "DDH":
//                                    NimApplication.DDH = status;
//
//                                    break;
//
//                                case "DSP":
//                                    NimApplication.DSP = status;
//
//                                    break;
//                                case "CZHY":
//                                    NimApplication.CZHY = status;
//
//                                    break;
//                                case "QR":
//                                    NimApplication.QR = status;
//
//                                    break;
//                                case "HB1":
//                                    NimApplication.HB1 = status;
//
//                                    break;
//                                case "HB2":
//                                    NimApplication.HB2 = status;
//
//                                    break;
//                                case "HB3":
//                                    NimApplication.HB3 = status;
//
//                                    break;
//                                case "YDK":
//                                    NimApplication.YDK = status;
//
//                                    break;
//                                case "YHQ":
//                                    NimApplication.YHQ = status;
//
//                                    break;
//                                case "YD":
//                                    NimApplication.YD = status;
//
//                                    break;
//                                case "SP1":
//                                    NimApplication.SP1 = status;
//
//                                    break;
//                                case "SP2":
//                                    NimApplication.SP2 = status;
//
//                                    break;
//                                case "SP3":
//                                    NimApplication.SP3 = status;
//
//                                    break;
//                                case "YY1":
//                                    NimApplication.YY1 = status;
//
//                                    break;
//                                case "YY2":
//                                    NimApplication.YY2 = status;
//
//                                    break;
//                                case "YY3":
//                                    NimApplication.YY3 = status;
//
//                                    break;
//                                case "MP":
//                                    NimApplication.MP = status;
//
//                                    break;
//                                case "SC":
//                                    NimApplication.SC = status;
//
//                                    break;
//                                case "XC1":
//                                    NimApplication.XC1 = status;
//
//                                    break;
//                                case "XC2":
//                                    NimApplication.XC2 = status;
//
//                                    break;
//                                case "XC3":
//                                    NimApplication.XC3 = status;
//
//                                    break;
//                                case "PS1":
//                                    NimApplication.PS1 = status;
//
//                                    break;
//                                case "PS2":
//                                    NimApplication.PS2 = status;
//
//                                    break;
//                                case "PS3":
//                                    NimApplication.PS3 = status;
//
//                                    break;
//                            }

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

}
