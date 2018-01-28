package com.ody.p2p.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;
import com.ody.p2p.R;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.webactivity.UrlBean;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.alibaba.mobileim.YWChannel.getResources;
import static com.odysaxx.photograph.Application.getContext;

/**
 * Created by lxs on 2016/9/12.
 */
public class PermissionUtils {


    public static boolean hasPermission(Context context) {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        boolean permission = camera == PackageManager.PERMISSION_GRANTED;
        return permission;
    }

    public static void getPermission(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pack = pm.getPackageInfo("packageName", PackageManager.GET_PERMISSIONS);
            String[] permissionStrings = pack.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查需要的权限
     *
     * @param context
     * @param str
     */
    public static Observable<Boolean> checkPermission(final Activity context, final String... str) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                RxPermissions rxPermissions = new RxPermissions(context);
                rxPermissions.request(
                        str
                ).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(granted);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(throwable);
                        }
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        });
    }

}
