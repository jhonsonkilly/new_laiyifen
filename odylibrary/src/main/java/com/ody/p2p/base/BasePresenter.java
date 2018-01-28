package com.ody.p2p.base;

/**
 * Created by baixiaokang on 16/4/22.
 */
public abstract class BasePresenter<T> {
    public T mView;

    public void setV(T v) {
        this.mView = v;
        this.onStart();
    }

    public abstract void onStart();
}
