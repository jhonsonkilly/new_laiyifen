package com.ody.p2p.message.sysmessagelist;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/8/25.
 */
public class SysMessageListPressenterImpr implements SysMessageListPressenter {
    SysMessageListView mView;

    public SysMessageListPressenterImpr(SysMessageListView mView) {
        this.mView = mView;
    }

    @Override
    public void getMsgList(int pageNo) {
        Map<String, String> params = new HashMap<>();
//        ut	String	是	用户UT
//        isUpdate	Boolean	是	是否将消息未读状态改为已读状态
//        pageNo	Integer	是	当前页数
//        pageSize	Integer	是	每页查询个数
        params.put("isUpdate", true + "");
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
//        mView.showLoading();
        OkHttpManager.postAsyn(Constants.MESSAGE_MSGLIST, new OkHttpManager.ResultCallback<SysMessageListBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(SysMessageListBean response) {
                if (null != response && null != response.getData() && null != response.getData().getDatas() && response.getData().getDatas().size() > 0) {
                    mView.getSysMessageBean(response.getData().getDatas());
                }
            }
        }, params);
    }

}
