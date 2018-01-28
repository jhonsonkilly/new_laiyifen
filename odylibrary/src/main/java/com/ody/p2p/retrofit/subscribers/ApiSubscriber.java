package com.ody.p2p.retrofit.subscribers;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.ody.p2p.retrofit.JsonResultException;
import com.ody.p2p.retrofit.progress.ProgressCancelListener;
import com.ody.p2p.retrofit.progress.ProgressDialogHandler;

import org.json.JSONException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by 孙华辉 on 16/3/10.
 */
public class ApiSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberListener mSubscriberListener;
    private ProgressDialogHandler mProgressDialogHandler;

    public ApiSubscriber(SubscriberListener mSubscriberListener) {
        this.mSubscriberListener = mSubscriberListener;
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        //showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
//        dismissProgressDialog();
        if (mSubscriberListener != null) {
            mSubscriberListener.onCompleted();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (mSubscriberListener != null) {
            if (e instanceof JsonResultException) {
                mSubscriberListener.onJsonError(((JsonResultException) e).getJson());
            }
            mSubscriberListener.onError(e != null ? e.getMessage() : "矮油，出故障了!");
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberListener != null) {
            mSubscriberListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
//    @Override
//    public void onCancelProgress() {
//        if (!this.isUnsubscribed()) {
//            this.unsubscribe();
//        }
//    }
    @Override
    public void onCancelProgress() {

    }
}