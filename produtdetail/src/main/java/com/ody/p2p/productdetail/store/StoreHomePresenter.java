package com.ody.p2p.productdetail.store;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.store.bean.StoreBaseInfo;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.store.AttentionBean;
import com.ody.p2p.retrofit.store.AttentionNumberBean;
import com.ody.p2p.retrofit.store.DoAttentionBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.StringUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ody.p2p.base.OdyApplication.getValueByKey;

/**
 * Created by hanzhifengyun on 2017/7/7.
 * 店铺信息
 */

public class StoreHomePresenter implements StoreHomeContract.Presenter {

    private StoreHomeContract.View mView;

    public StoreHomePresenter(StoreHomeContract.View view) {
        mView = view;
    }

    @Override
    public void getStoreBaseInfo(String merchantId) {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
//        params.put("merchantId",  "1076015501000001");
        params.put("merchantId",  merchantId);
        OkHttpManager.getAsyn(Constants.GET_SHOP_BASE_INFO, params, mView.getNetTAG(), new OkHttpManager.ResultCallback<StoreBaseInfo>() {

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(StoreBaseInfo response) {
                if (response != null) {
                    mView.setStoreBaseInfo(response);
                }
            }
        });
    }

    @Override
    public void getLikeStatus(String entityId) {
        RetrofitFactory.getAttentionStatus(OdyApplication.getString(Constants.USER_LOGIN_UT,""),3,entityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AttentionBean>(new SubscriberListener<AttentionBean>() {
                    @Override
                    public void onNext(AttentionBean o) {
                        if(o.getCode().equals("0")){
                            mView.likeStatus(o);
                        }
                    }
                }));
    }

    @Override
    public void like(String entityId) {
        RetrofitFactory.doAttention(OdyApplication.getString(Constants.USER_LOGIN_UT,""),3,entityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<DoAttentionBean>(new SubscriberListener<DoAttentionBean>() {
                    @Override
                    public void onNext(DoAttentionBean o) {
                        if(o.getCode().equals("0")){
                            mView.likeOrCancel(o);
                        }
                    }
                }));
    }

    @Override
    public void cancelLike(String entityId) {
        RetrofitFactory.cancelAttention(OdyApplication.getString(Constants.USER_LOGIN_UT,""),3,entityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<DoAttentionBean>(new SubscriberListener<DoAttentionBean>() {
                    @Override
                    public void onNext(DoAttentionBean o) {
                        if(o.getCode().equals("0")){
                            mView.likeOrCancel(o);
                        }
                    }
                }));
    }

    @Override
    public void getAttentionNumber(String entityId) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", (String)getValueByKey(Constants.USER_LOGIN_UT));
        params.put("type", "3");
        params.put("entityId", entityId);
        OkHttpManager.postAsyn(Constants.COUNTFAVOTITE, new OkHttpManager.ResultCallback<AttentionNumberBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(AttentionNumberBean response) {
                if (response != null) {
                   mView.getAttentionNumber(response);
                }
            }
        }, params);
    }


}
