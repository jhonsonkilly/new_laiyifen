package com.ody.p2p.retrofit.cache;

import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import com.google.gson.Gson;
import com.ody.p2p.base.BaseRequestBean;

import java.io.UnsupportedEncodingException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Samuel on 17/3/21下午4:35.
 */
public class MemoryCache implements ICache {

    private LruCache<String, String> mCache;

    public MemoryCache() {
        //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        //给LruCache分配1/8 4M
        mCache = new LruCache<String, String>(mCacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                try {
                    return value.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return value.getBytes().length;
                }
            }
        };
    }

    @Override
    public <T extends BaseRequestBean> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                Log.v("cache", "load from memory: " + key);

                String result = mCache.get(key);
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
        });
    }

    @Override
    public <T extends BaseRequestBean> void put(String key, T t) {

        if (null != t) {
            Log.v("cache", "save to memory: " + key);
            mCache.put(key, new Gson().toJson(t));
        }
    }
}