package com.netease.nim.demo.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


/**
 * @author SevenCheng
 */

public abstract class SyncClient implements Runnable {
    protected SyncClientListener clientListener;
    protected Handler mMainHandler = new Handler(Looper.getMainLooper());
    protected String url;


    public boolean getIsDebug() {
        return IsDebug;
    }

    public void setIsDebug(boolean isDebug) {
        IsDebug = isDebug;
    }

    private  boolean IsDebug =false;

    public SyncClient(String url, SyncClientListener clientListener) {
        this.clientListener = clientListener;
        this.url = url;


    }

    protected ProgressDialog dialog = null;

    public void ShowProgressDialog(Context montext) {
        dialog = ProgressDialog
                .show(montext, "提示", "正在加载...", false);
    }

    public void ShowProgressDialog(Context montext, String title, String showcontent) {
        dialog = ProgressDialog
                .show(montext, title, showcontent, false);
    }

    public ProgressDialog getProgressDialog() {
        return this.dialog;
    }

    protected void StartSync() {

        if (clientListener != null) {

            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null) {
                        dialog.show();
                    }

                }
            });

        }


    }

    protected void SyncSuccess(final String resurt) {

        System.out.println(" 下载内容 = "+resurt);
        if (clientListener != null) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    clientListener.onDownloadSyncSuccess(resurt);
                }
            });
        }


    }

    protected void SyncFail(final String exception) {
        System.out.println(" 下载失败 = "+exception);
        if (clientListener != null) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    clientListener.onDownloadSyncFail(exception);
                }
            });
        }
    }
}

