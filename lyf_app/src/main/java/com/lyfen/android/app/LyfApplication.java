package com.lyfen.android.app;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.laiyifen.lyfframework.network.RestRetrofit;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.ody.p2p.main.App;
import com.ody.p2p.main.BuildConfig;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.lyfen.android</p>
 * <p>文件名:Lyfen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 *
 *
 */
public class LyfApplication extends App {

    private static LyfApplication mInstance;
    private static int mMainThreadId = -1;
    private static Thread mMainThread;
    private static Handler mMainThreadHandler;
    private static Looper mMainLooper;

    @Override
    public void onCreate() {
        super.onCreate();


        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;

        QbSdk.initX5Environment(this, null);

        RestRetrofit.init(this,BuildConfig.BASEURL);

//        Stetho.initializeWithDefaults(this);
//        Stetho.s





        //路由初始化
//        Router.initActivityRouter(getApplicationContext(), BuildConfig.SCHEME, router -> {
//
//            Observable.create((Observable.OnSubscribe<List<RouteEntity>>) subscriber -> {
//
//                try {
//                    List<RouteEntity> List = GsonUtils.getGson().fromJson(
//                            new InputStreamReader(mInstance.getAssets().open("router.json"))
//                            , new TypeToken<java.util.List<RouteEntity>>() {
//                            }.getType());
//                    subscriber.onNext(List);
//                    subscriber.onCompleted();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onError(e);
//                }
//
//            }).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<RouteEntity>>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    LogUtils.i("route", "路由跳转出现问题" + e.getMessage());
//                }
//
//                @Override
//                public void onNext(List<RouteEntity> activityRoute) {
//                    RouterHelper.getHelper().init(activityRoute);
//                    for (int i = 0; i < activityRoute.size(); i++) {
//                        try {
//                            Class<Activity> aClass = (Class<Activity>) Class.forName(activityRoute.get(i).routePramsActivity);
//                            router.put(activityRoute.get(i).routeSchema, aClass);
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//
//                }
//            });
//
//
//        });

        LoginHelper.init();
        if (!BuildConfig.DEBUG) {
           // setUncaughtExcept ();//全局捕获异常
        }

    }

    private void setUncaughtExcept() {
        Thread.currentThread ().setUncaughtExceptionHandler ((thread, ex) -> {
            try {
                StringWriter sw = new StringWriter ();
                PrintWriter pw = new PrintWriter (sw);

                Field[] fileds = Build.class.getDeclaredFields ();
                for (Field filed : fileds) {
                    sw.write (filed.getName () + "--" + filed.get (null) + "\n");
                }
                ex.printStackTrace (pw);
                File file = new File (Environment.getExternalStorageDirectory (), "log.txt");
                FileOutputStream fos = new FileOutputStream (file);
                fos.write (sw.toString ().getBytes ());
                fos.close ();
                pw.close ();
                sw.close ();
                android.os.Process.killProcess (android.os.Process.myPid ());
            } catch (Exception e) {
                android.os.Process.killProcess (android.os.Process.myPid ());
                e.printStackTrace ();
            }
        });

    }


    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }


    public static LyfApplication getApplication() {
        return mInstance;
    }

}
