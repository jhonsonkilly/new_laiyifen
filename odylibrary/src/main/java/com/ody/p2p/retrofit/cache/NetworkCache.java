package com.ody.p2p.retrofit.cache;

import com.ody.p2p.base.BaseRequestBean;

import rx.Observable;

/**
 * Created by Samuel on 17/3/21下午4:35.
 */
public abstract class NetworkCache<T extends BaseRequestBean> {

    public abstract Observable<T> get(String key, final Class<T> cls);
}