package com.ody.p2p.retrofit.cache;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.FileUtils;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Samuel on 17/3/21下午4:35.
 */
public class DiskCache implements ICache {

    private String CACHE_PATH;

    public DiskCache() {
        CACHE_PATH = FileUtils.getCacheDir();
    }

    @Override
    public <T extends BaseRequestBean> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                Log.v("cache", "load from disk: " + key);

                String filename = CACHE_PATH + key;
                String result = FileUtils.readTextFromSDcard(filename);
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                if (TextUtils.isEmpty(result)) {

                    subscriber.onNext(null);
                } else {
                    T t = new Gson().fromJson(result, cls);
                    subscriber.onNext(t);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T extends BaseRequestBean> void put(final String key, final T t) {

        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                Log.v("cache", "save to disk: " + key);

                if (t == null) return;
                String filename = CACHE_PATH + key;
                String result = new Gson().toJson(t);
                FileUtils.saveText2Sdcard(filename, result);
                if (!subscriber.isUnsubscribed()) {

                    subscriber.onNext(t);
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new ApiSubscriber<T>(new SubscriberListener() {
                    @Override
                    public void onNext(Object o) {

                    }
                }));
    }
}