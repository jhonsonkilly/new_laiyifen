package com.ody.ds.lyf_home;

import android.content.Context;
import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.cache.CacheManager;
import com.ody.p2p.retrofit.cache.NetworkCache;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.HomeBean;
import com.ody.p2p.retrofit.home.PersonalBean;
import com.ody.p2p.retrofit.home.QIYuBean;
import com.ody.p2p.retrofit.home.QiangGouBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.AutoFitUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 2017/9/5.
 */

public class HomeImpl implements HomePresenter {

    public HomeView mView;
    private int count = 0;

    public HomeImpl(HomeView mView) {
        this.mView = mView;
    }

    @Override
    public void getHomePage() {
        CacheManager.getInstance()
                .load("cache_home_page", HomeBean.class, new NetworkCache<HomeBean>() {
                    @Override
                    public Observable<HomeBean> get(String key, Class<HomeBean> cls) {
                        return RetrofitFactory.getCacheHomePage()
                                .subscribeOn(Schedulers.io());
                    }
                })
                .map(new Func1<HomeBean, AppHomePageBean>() {
                    @Override
                    public AppHomePageBean call(HomeBean result) {
                        if (result != null && result.code.equals("0") && result.data != null) {
                            return result.data;
                        } else {
                            return new AppHomePageBean();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AppHomePageBean>(new SubscriberListener<AppHomePageBean>() {


                    @Override
                    public void onNext(AppHomePageBean bean) {
                        mView.initPager(bean);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        Log.d("tag", "onError: msg = " + msg);
                    }

                    @Override
                    public void onCompleted() {
                        // mView.onRefreshComplete();
                        super.onCompleted();
                    }
                }));
    }


}
