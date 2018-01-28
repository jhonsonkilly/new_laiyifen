package com.lyfen.android;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.Stetho;
import com.google.gson.reflect.TypeToken;
import com.laiyifen.lyfframework.utils.GsonUtils;
import com.laiyifen.lyfframework.utils.LogUtils;
import com.lyfen.android.entity.local.RouteEntity;
import com.lyfen.android.utils.RouterHelper;
import com.ody.p2p.main.App;
import com.ody.p2p.main.BuildConfig;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;

import java.io.InputStreamReader;
import java.util.List;

import cn.campusapp.router.Router;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.lyfen.android</p>
 * <p>文件名:Lyfen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
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


        //Stetho.initializeWithDefaults(this);
        //路由初始化
        Router.initActivityRouter(getApplicationContext(), BuildConfig.SCHEME, router -> {

            Observable.create((Observable.OnSubscribe<List<RouteEntity>>) subscriber -> {

                try {
                    List<RouteEntity> List = GsonUtils.getGson().fromJson(
                            new InputStreamReader(mInstance.getAssets().open("router.json"))
                            , new TypeToken<java.util.List<RouteEntity>>() {
                            }.getType());
                    subscriber.onNext(List);
                    subscriber.onCompleted();

                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<RouteEntity>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.i("route", "路由跳转出现问题" + e.getMessage());
                }

                @Override
                public void onNext(List<RouteEntity> activityRoute) {
                    RouterHelper.getHelper().init(activityRoute);
                    for (int i = 0; i < activityRoute.size(); i++) {
                        try {
                            Class<Activity> aClass = (Class<Activity>) Class.forName(activityRoute.get(i).routePramsActivity);
                            router.put(activityRoute.get(i).routeSchema, aClass);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });


        });

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);



        MobclickAgent.UMAnalyticsConfig umAnalyticsConfig =
                new MobclickAgent.UMAnalyticsConfig(this, "5a42218cf43e485046000027", BuildConfig.FLAVOR);

        MobclickAgent.startWithConfigure(umAnalyticsConfig);


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
