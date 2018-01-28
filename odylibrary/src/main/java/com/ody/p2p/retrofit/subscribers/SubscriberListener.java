package com.ody.p2p.retrofit.subscribers;

/**
 * Created by sunhuahui on 16/3/10.
 */
public abstract class SubscriberListener<T> {
    public abstract void onNext(T t);

    public void onCompleted() {
    }

    public void onError(String msg) {
    }

    public void onJsonError(String json){}
}
