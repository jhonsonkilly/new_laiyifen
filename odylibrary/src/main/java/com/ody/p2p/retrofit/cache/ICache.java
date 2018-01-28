package com.ody.p2p.retrofit.cache;

import com.ody.p2p.base.BaseRequestBean;

import rx.Observable;

/**
 * Created by Samuel on 17/3/21下午4:35.
 */
public interface ICache {

    <T extends BaseRequestBean> Observable<T> get(String key, Class<T> cls);

    <T extends BaseRequestBean> void put(String key, T t);
}
