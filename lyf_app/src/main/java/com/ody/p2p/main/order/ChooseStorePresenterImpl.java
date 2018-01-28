package com.ody.p2p.main.order;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/12/13.
 */

public class ChooseStorePresenterImpl implements ChooseStrorePresenter{

    private ChooseStoreView chooseStoreView;

    public ChooseStorePresenterImpl(ChooseStoreView chooseStoreView){
        this.chooseStoreView=chooseStoreView;
    }

    @Override
    public void getStoreList(int pageNum,String keyword,String lat,String longt) {
        Map<String,String> params=new HashMap<>();
        params.put("pageNum",pageNum+"");
        params.put("pageSize",10+"");
        params.put("longitude",longt);// lat--31.198857--long121.602961
        params.put("latitude",lat);
        params.put("keyword",keyword);
        params.put("isPickUp",1+"");//筛选支持自提的门店
        OkHttpManager.getAsyn(Constants.STORE_LIST, params, new OkHttpManager.ResultCallback<ChooseStoreBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);

            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ChooseStoreBean response) {
                if(response!=null&&response.code.equals("0")){
                    chooseStoreView.storeList(response);
                }
            }
        });
    }

    @Override
    public void saveShop(String merchantId, String deliveryCode, String pickSiteId, String phoneNum) {
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("merchantId",merchantId);
        params.put("deliveryModeCodeChecked",deliveryCode);
        params.put("pickSiteId",pickSiteId);
        params.put("pickMobile",phoneNum);
        OkHttpManager.postAsyn(Constants.SAVE_DELIVERY_MODE, new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    chooseStoreView.finishActivity();
                }
            }
        },params);
    }
}
