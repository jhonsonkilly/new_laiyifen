package com.ody.p2p;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.QIYuBean;
import com.ody.p2p.utils.AutoFitUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 2017/12/4.
 */

public class QiYuServicePolicy implements ServicePolicy {
    @Override
    public void doServicePolicy(final int type,  final Context context) {
        String userId = OdyApplication.getValueByKey(Constants.BC_USER_ID, "");
        if (StringUtils.isEmpty(userId)) {
            JumpUtils.ToActivity(JumpUtils.LOGIN);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("appVersion", AutoFitUtils.getVersionCode(OdyApplication.gainContext()) + "");
        params.put("platform", "0");

        OkHttpManager.noSessionIdArea(Constants.QIYUMES, new OkHttpManager.ResultCallback<QIYuBean>() {
            @Override
            public void onError(Request request, Exception e) {


            }

            @Override
            public void onResponse(QIYuBean response) {


                if (response != null) {
                    try {
                        ConsultSource source = new ConsultSource("", "", "首页");

                        YSFUserInfo userInfo = new YSFUserInfo();

                        String data = new Gson().toJson(response.data.data);

                        userInfo.userId = response.data.uid;
                        userInfo.data = data;

                        if (type == 0) {
                            source.groupId = response.data.groupIdList.get(0).type1;

                        }
                        if (type == 1) {
                            source.groupId = response.data.groupIdList.get(1).type2;

                        }
                        if (type == 2) {
                            source.groupId = response.data.groupIdList.get(2).type3;

                        }
                        if (type == 3) {
                            source.groupId = response.data.groupIdList.get(3).type4;

                        }


                        Unicorn.setUserInfo(userInfo);

                        Unicorn.openServiceActivity(context, "来伊份客服", source);


                    } catch (Exception e) {

                    }

                }
            }
        }, params);
    }




}
