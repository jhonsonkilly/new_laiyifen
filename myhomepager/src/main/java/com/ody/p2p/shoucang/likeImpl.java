package com.ody.p2p.shoucang;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/30.
 */
public class likeImpl implements LikePressenter {
    public static final String provinceId = "10";
    public static final String companyId = "1";
    likeview lview;
    boolean flage = false;

    public likeImpl(likeview lview) {
        this.lview = lview;
    }

    @Override
    public void selectlikeprodut(int pageNum) {
        if (pageNum > 0 && flage) {//下拉没有更多的数据
            return;
        } else {
            flage = false;
        }
//        if (pageNum == 1) {
//        }
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.USER_GOODS, params, new OkHttpManager.ResultCallback<MyShouCangListBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showShort("" + e.getMessage());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                lview.hideLoading();
            }

            @Override
            public void onResponse(MyShouCangListBean response) {
                lview.hideLoading();
                if (null != response && null != response.getData()) {
                    lview.likeproct(response);
                }
            }
        });
    }

    @Override
    public void del(String ids) {
        Map<String, String> params = new HashMap<>();
        params.put("ids", ids);
        lview.showLoading();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.USER_CLEAR, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                lview.hideLoading();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                lview.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                lview.hideLoading();
                if (response != null && "0".equals(response.code)) {
                    lview.refsh();

                }
            }
        }, params);

    }
}
