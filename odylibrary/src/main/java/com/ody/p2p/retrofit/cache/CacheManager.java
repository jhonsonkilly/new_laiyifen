package com.ody.p2p.retrofit.cache;

import android.util.Log;

import com.ody.p2p.base.BaseRequestBean;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Samuel on 17/3/21下午4:35.
 */
public class CacheManager {

    private ICache mMemoryCache, mDiskCache;

    private CacheManager() {

        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
    }

    public static final CacheManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public <T extends BaseRequestBean> Observable<T> load(String key, Class<T> cls, NetworkCache<T> networkCache) {

        Observable observable = Observable.concat(
                loadFromNetwork(key, cls, networkCache),
                loadFromMemory(key, cls),
                loadFromDisk(key, cls))
                .takeFirst(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t != null && t.code.equals("0");
                    }
                });
        return observable;
    }

    private <T extends BaseRequestBean> Observable<T> loadFromMemory(String key, Class<T> cls) {

        Observable.Transformer<T, T> transformer = log("load from memory: " + key);

        return mMemoryCache
                .get(key, cls)
                .compose(transformer);
    }

    private <T extends BaseRequestBean> Observable<T> loadFromDisk(final String key, Class<T> cls) {

        Observable.Transformer<T, T> transformer = log("load from disk: " + key);

        return mDiskCache.get(key, cls)
                .compose(transformer)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (null != t) {
                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }

    private <T extends BaseRequestBean> Observable<T> loadFromNetwork(final String key, Class<T> cls
            , NetworkCache<T> networkCache) {

        Observable.Transformer<T, T> transformer = log("load from network: " + key);

        return networkCache.get(key, cls)
                .compose(transformer)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {

                        if (null != t) {
                            mDiskCache.put(key, t);
                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }

    private <T extends BaseRequestBean> Observable.Transformer<T, T> log(final String msg) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {

                        //MemoryCache、DiskCache中已经打印过log了，这里只是为了演示transformer、和compose的使用
                        Log.v("cache", msg);
                    }
                });
            }
        };
    }

    private static final class LazyHolder {
        public static final CacheManager INSTANCE = new CacheManager();
    }
}