package com.lyfen.android.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.laiyifen.lyfframework.utils.GsonUtils;
import com.laiyifen.lyfframework.utils.LogUtils;
import com.laiyifen.lyfframework.utils.TimeUtils;
import com.lyfen.android.entity.local.RouteEntity;
import com.lyfen.android.hybird.HybridEntity;
import com.lyfen.android.hybird.LyfWebViewActivity;
import com.lyfen.android.hybird.LyfWebViewFragment;
import com.lyfen.android.hybird.UrlEntity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.BuildConfig;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.util.List;

import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.laiyifen.lyfframework.com.lyfen.android.utils</p>
 * <p>文件名:new-laiyifen</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:zhoujiawei@laiyifen.com">vernal(周佳伟)</a>
 */
public class RouterHelper {

    private static List<RouteEntity> mList;


    private RouterHelper() {


    }

    private static RouterHelper sHelper = new RouterHelper();

    public static RouterHelper getHelper() {
        return sHelper;

    }

    public void init(List<RouteEntity> list) {
        mList = list;
    }

    public String Schema(String calssName) {
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).routePramsActivity.equals(calssName)) {
                    return mList.get(i).routeSchema;
                }
            }

        }
        return "";
    }


//    public static final String tag = "://";
//    //首页
//    public static final String MAIN = BuildConfig.SCHEME + tag + "main";
//    //web
//    public static final String H5 = BuildConfig.SCHEME + tag + "h5";
//    //商品详情
//    public static final String PRODUCTDETAIL = BuildConfig.SCHEME + tag + "productdetail";
//    //搜索
//    public static final String SEARCH = BuildConfig.SCHEME + tag + "search";
//    //商品列表
//    public static final String PRODUCTDELIST = BuildConfig.SCHEME + tag + "productlist";
//    //短信登录
//    public static final String SMSLOGIN = BuildConfig.SCHEME + tag + "smslogin";


//    public static void ToWebActivity(Context context, String loadUrl) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.SCHEME + "://" + H5);
//        activityRoute.withParams("loadUrl", loadUrl).open();
//    }
//
//    public static void ToWebActivity(String activityTag, String loadUrl, int showType, int Res, String title) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.SCHEME + "://" + activityTag);
//        if (activityRoute != null) {
//            activityRoute.withParams("loadUrl", loadUrl).withParams("showType", showType).withParams("titleRes", Res).withParams("titleContent", title).open();
//        }
//    }
//
//    public static void ToHelpWebActivity(String activityTag, String loadUrl, int showType, int Res, String title, int help) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.SCHEME + "://" + activityTag);
//        if (activityRoute != null) {
//            activityRoute.withParams("loadUrl", loadUrl).withParams("showType", showType).withParams("titleRes", Res).withParams("titleContent", title).withParams("help", help).open();
//        }
//    }

    /**
     * @param activityTag 符合 SCHEMA 的URL链接
     */
    public void Web2Activity( String activityTag) {

        if (TimeUtils.checkTimeDivCall(500)) {
            if (Uri.parse(activityTag).getScheme().equals(BuildConfig.SCHEME)) {
                Observable.create((Observable.OnSubscribe<ActivityRoute>) subscriber -> {

                    HybridEntity hybridModel = new HybridEntity();
                    hybridModel.callback = (Uri.parse(activityTag).getScheme() + "://" + Uri.parse(activityTag).getHost());
                    ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(activityTag);


                    if (!TextUtils.isEmpty(Uri.parse(activityTag).getQueryParameter("body"))) {
                        String body = Uri.parse(activityTag).getQueryParameter("body");
//                    try {
//                        JSONObject jsonObject = new JSONObject(body);
//                        String url = jsonObject.optString("url");
//                        UrlEntity urlEntity = new UrlEntity();
//                        urlEntity.url=url;
//                        hybridModel.param=urlEntity;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                        hybridModel.param = GsonUtils.fromJson(body, UrlEntity.class);
                        activityRoute.withParams("mode", hybridModel);




                    }
                    subscriber.onNext(activityRoute);
                    subscriber.onCompleted();
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ActivityRoute>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("jump-----Web2Activity", "解析出现问题" + e.getMessage());
                    }

                    @Override
                    public void onNext(ActivityRoute activityRoute) {
                        activityRoute.open();
                        LogUtils.i("Rxview", "excute context");

                    }
                });

            } else if (Uri.parse(activityTag).getScheme().equals("http") || Uri.parse(activityTag).getScheme().equals("https")) {
                LogUtils.i("Rxview", "excute context web");


                UrlEntity urlEntity = new UrlEntity();
                urlEntity.url = activityTag;

                HybridEntity hybridEntity = new HybridEntity();
                hybridEntity.param = urlEntity;
                Intent intent = new Intent(UIUtils.getContext(), LyfWebViewActivity.class);

                intent.putExtra("mode", hybridEntity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);


            }
        }
    }


    /**
     * 不符合 schema的链接
     *
     * @param jumtTag 跳转页面
     * @param entity  跳转参数
     */
    public void Native2Activity( String jumtTag, UrlEntity entity) {
        LogUtils.i("Rxview", " Native2Activity ---click");

        Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            String s = jumtTag + "?body=" + GsonUtils.toJson(entity);
            subscriber.onNext(s);
            subscriber.onCompleted();


        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("jump", "解析出现问题----Native2Activity" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                RouterHelper.getHelper().Web2Activity(s);
            }
        });


    }
//    public static void web2Activity(String activityTag, Bundle extras) {
//        ActivityRoute activityRoute = null;
//        if (activityTag.contains(LyfApplication.SCHEME + "://")) {
//            activityRoute = (ActivityRoute) Router.getRoute(activityTag);
//        } else {
//            activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.getRouterUrl(activityTag));
//        }
//        if (activityRoute != null) {
//            activityRoute.addExtras(extras);
//            activityRoute.open();
//        }
//    }
//
//
//    public static void ToActivityFoResult(String activityTag, Bundle extras, int code, Activity activity) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.getRouterUrl(activityTag));
//        if (activityRoute != null) {
//            activityRoute.addExtras(extras);
//            activityRoute.withOpenMethodStartForResult(activity, code).open();
//        }
//    }
//
//
//    public static void ToFragmenFoResult(String activityTag, Bundle extras, int code, Fragment fragment) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(LyfApplication.getRouterUrl(activityTag));
//        if (activityRoute != null) {
//            activityRoute.addExtras(extras);
//            activityRoute.withOpenMethodStartForResult(fragment, code).open();
//        }
//    }

    public LyfWebViewFragment getWebView(String url){
        LyfWebViewFragment lyfWebViewFragment = new LyfWebViewFragment();


        HybridEntity hybridEntity = new HybridEntity();
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.url=url;
        hybridEntity.param=urlEntity;
        Bundle bundle = new Bundle();
        bundle.putParcelable("mode", hybridEntity);
        lyfWebViewFragment.setArguments(bundle);
        return lyfWebViewFragment;
    }


    public void openWeb(String uri){
        JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + uri, WebActivity.NO_TITLE, 0, "");



//        UrlEntity urlEntity = new UrlEntity();
//        urlEntity.url = BuildConfig.H5URL + uri;
//
//        HybridEntity hybridEntity = new HybridEntity();
//        hybridEntity.param = urlEntity;
//        Intent intent = new Intent(UIUtils.getContext(), LyfWebViewActivity.class);
//
//        intent.putExtra("mode", hybridEntity);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        UIUtils.getContext().startActivity(intent);
    }
}
