package com.ody.p2p.main.invitefriends;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.UserInfoBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.CurrDistributorBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.AdBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.cache.CacheManager;
import com.ody.p2p.retrofit.cache.NetworkCache;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by pzy on 2017/10/12.
 */
public class InviteFriendsPresenterImpl {
    private InviteFriendsView mView;

    public InviteFriendsPresenterImpl(InviteFriendsView mView) {
        this.mView = mView;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.USER_INFO, new OkHttpManager.ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(UserInfoBean response) {
                if (null != response && null != response.getData()) {
//                    GlideUtil.display(getContext(), response.getData().getHeadPicUrl()).into(mImgUerPic);
                    mView.backUser(response.getData());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        }, params);
    }

    /**
     * 获取当前店铺 sharecode
     */
    public void getCurrDistributor() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ut", OdyApplication.getLoginUt());
        OkHttpManager.getAsyn(Constants.SHOP_INFO, map, new OkHttpManager.ResultCallback<CurrDistributorBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CurrDistributorBean response) {
                if (response != null && response.data != null) {
                    mView.backCurrdistri(response.data);
                }
            }
        });
    }

    public void getAdData(final String adCode) {
        CacheManager.getInstance().load("cache_home_" + adCode, AdBean.class, new NetworkCache<AdBean>() {
            @Override
            public Observable<AdBean> get(String key, Class<AdBean> cls) {
                return RetrofitFactory.getCacheAd(AdPageCode.APP_INVITE_FRIEND_PAGE, adCode)
                        .subscribeOn(Schedulers.io());
            }
        })
                .map(new Func1<AdBean, AdData>() {
                    @Override
                    public AdData call(AdBean bean) {
                        if (bean != null && bean.code.equals("0") && bean.data != null) {
                            return bean.data;
                        } else {
                            return new AdData();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (adData != null) {
                            mView.initAdData(adData);
                        }
                    }
                }));
    }
}
