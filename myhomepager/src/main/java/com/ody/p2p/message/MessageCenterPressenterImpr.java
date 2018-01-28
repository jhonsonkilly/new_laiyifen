package com.ody.p2p.message;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/8/24.
 */
public class MessageCenterPressenterImpr implements MessageCenterPressenter {
    MessageCenterView mView;

    public MessageCenterPressenterImpr(MessageCenterView mView) {
        this.mView = mView;
    }

    @Override
    public void getMsgSummary() {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.MESSAGE_MSGSUMMARY, params,new OkHttpManager.ResultCallback<MessageCenterBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(MessageCenterBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData()) {
                    mView.getHelpCenterBean(response.getData());
                }

            }
        });
    }
}
