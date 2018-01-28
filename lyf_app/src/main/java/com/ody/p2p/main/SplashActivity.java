package com.ody.p2p.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.ody.p2p.AliBCBean;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.AreacodeBean;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.home.AdBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.webactivity.WebActivity;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends Activity {

    private ImageView iv_loading;
    private ViewPager vp;
    //    private int[] imgs = {R.drawable.one, R.drawable.two, R.drawable.three};
    private int[] imgs = {R.drawable.pager1, R.drawable.pager2, R.drawable.pager3};
    private List<View> views = new ArrayList<View>();
    private boolean isFirst = true;
    public static final int LOADING_MSG = 0x01;
    public static final int COUNT_DOWN = 0x02;


    private LinearLayout linear;
    private FrameLayout rl_main;
    private TextView tv_jump;
    private int countDown = 3;

    private List<ImageView> viewList = new ArrayList<>();

    private LocationManager locationManager;
    private boolean isJumping;

    private List<AdBean.Data.AdBanner> list;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isJumping) {
                return;
            }
            if (msg.what == LOADING_MSG) {

                    if (list != null && list.size() > 0) {
                        try {
                        FrescoUtils.displayUrl(guangGao, list.get(0).imageUrl);
                        tv_jump.setVisibility(View.VISIBLE);
                        tv_jump.setText("跳过(" + countDown + ")");
                        mHandler.sendEmptyMessage(COUNT_DOWN);
                        guangGao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHandler.removeMessages(COUNT_DOWN);
                                JumpUtils.ToActivity(JumpUtils.MAIN);
                                finish();
                                JumpUtils.ToWebActivity(list.get(0).linkUrl, WebActivity.CONTENT_TITLE, -1, "");

                            }
                        });
                        } catch (Exception e) {

                        }


                    } else {
                        JumpUtils.ToActivity(JumpUtils.MAIN);
                        finish();
                    }


            }

            if (msg.what == COUNT_DOWN) {
                if (countDown <= 0) {
                    jumpToMainPage();
                } else {
                    tv_jump.setText("跳过(" + countDown + ")");
                    countDown--;
                    mHandler.sendEmptyMessageDelayed(COUNT_DOWN, 1000);
                }
            }
        }
    };
    private ImageView imageView;
    private ImageView img_logo_word;
    private SimpleDraweeView guangGao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        MobclickAgent.setDebugMode(false);
        MobclickAgent.openActivityDurationTrack(false);
        initView();
        initListener();
        doBusiness(this);
        if (!NetworkUtil.isNetworkAvailable(this)) {
            JumpUtils.ToActivity(JumpUtils.MAIN);
            finish();
        }
    }

    public void initView() {
        isJumping = false;
        vp = (ViewPager) findViewById(R.id.vp);

        vp.setOffscreenPageLimit(5);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);
        rl_main = (FrameLayout) findViewById(R.id.rl_main);
        tv_jump = (TextView) findViewById(R.id.tv_jump);
        imageView = (ImageView) findViewById(R.id.img_logo);
        img_logo_word = (ImageView) findViewById(R.id.img_logo_word);
        guangGao = (SimpleDraweeView) findViewById(R.id.guangGao_view);
        tv_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMainPage();
//                countDown = 0;
//                mHandler.sendEmptyMessage(COUNT_DOWN);
            }
        });

        if (BuildConfig.FLAVOR.equals("Lenovo")) {
            setLogo(R.mipmap.img_logo_lenove);
        } else if (BuildConfig.FLAVOR.equals("_360")) {
            setLogo(R.mipmap.img_logo_360);
        } else if (BuildConfig.FLAVOR.equals("Sougou")) {
            setLogo(R.mipmap.img_logo_sougou);
        } else if (BuildConfig.FLAVOR.equals("Samsung")) {
//            setLogo(R.mipmap.img_logo_sumsung);
        } else if (BuildConfig.FLAVOR.equals("_1688")) {
            setLogo(R.mipmap.img_logo_1688_2);
        } else if (BuildConfig.FLAVOR.equals("Letv")) {
            setLogo(R.mipmap.img_logo_letv);
        } else if (BuildConfig.FLAVOR.equals("Huawei")) {
            setLogo(R.mipmap.img_logo_huawei);
        }

    }

    public void setLogo(int imgid) {
        imageView.setBackgroundDrawable(this.getResources().getDrawable(imgid));
        img_logo_word.setVisibility(View.GONE);
    }

    private void jumpToMainPage() {
        isJumping = true;
        OdyApplication.putValueByKey("isFirstSplash", false);
        JumpUtils.ToActivity(JumpUtils.MAIN);
        finish();
    }

    public void initListener() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < viewList.size(); i++) {
                    viewList.get(i).setSelected(false);
                }
                viewList.get(position).setSelected(true);
                if (position == (viewList.size() - 1)) {
                    tv_jump.setVisibility(View.VISIBLE);
                    tv_jump.setText("跳过(" + countDown + ")");
                    mHandler.sendEmptyMessage(COUNT_DOWN);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void doBusiness(final Context mContext) {
        locationManager = new LocationManager(this);
        iv_loading.setVisibility(View.VISIBLE);
        vp.setVisibility(View.GONE);
        isFirst = OdyApplication.getValueByKey("isFirstSplash", true);//第一次打开状态
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                //TODO:meiyizhi 项目运行需要的权限
                Manifest.permission.ACCESS_FINE_LOCATION,//位置
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.RECORD_AUDIO,
//
                //               Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
                //             Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
//                Manifest.permission.CAMERA,//相机
//
//                Manifest.permission.CALL_PHONE,//电话、短信
//                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.READ_SMS
        )
                .subscribe(new Action1<Boolean>() {


                    @Override
                    public void call(Boolean granted) {
                        if (isFirst) {
//            adSplash();
                            iv_loading.setVisibility(View.GONE);
                            guangGao.setVisibility(View.GONE);
                            vp.setVisibility(View.VISIBLE);
                            linear = new LinearLayout(mContext);
                            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                    FrameLayout.LayoutParams.WRAP_CONTENT);
                            p.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                            p.bottomMargin = 80;
                            linear.setLayoutParams(p);
                            linear.setVisibility(View.GONE);
                            rl_main.addView(linear);
                            setSplashPages();
                        } else {
                            guangGao.setVisibility(View.VISIBLE);
                            String json = OdyApplication.getValueByKey("AdParam", "");
                            if (!TextUtils.isEmpty(json)) {
                                try {
                                    Gson gson = new Gson();

                                    AdBean bean = gson.fromJson(json, AdBean.class);
                                    list = bean.data.APP_boot_ad;
                                    if (list != null && list.size() > 0) {
                                        FrescoUtils.fetchToBitmapCache(list.get(0).imageUrl);

                                    }
                                } catch (Exception e) {

                                }

                            }


                            mHandler.sendEmptyMessageDelayed(LOADING_MSG, 1000);
                        }
                    }
                });


        if (StringUtils.isEmpty(OdyApplication.getString(Constants.AREA_CODE, ""))) {
            locationManager.setLocationListener(new LocationManager.LocationListener() {
                @Override
                public void onLocationChanged(LocationManager.MapLocation location) {
                    if (location != null) {
                        RetrofitFactory.getArea(location.province, location.city, location.district)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriber<AreacodeBean>(new SubscriberListener<AreacodeBean>() {
                                    @Override
                                    public void onNext(AreacodeBean o) {
                                        OdyApplication.putString(Constants.AREA_CODE, o.getData().getFouCode() + "");
                                        OdyApplication.putString(Constants.PROVINCE, o.getData().getTwoAddress());
                                        OdyApplication.putString(Constants.CITY, o.getData().getThrAddress());
                                        OdyApplication.putString(Constants.AREA_NAME, o.getData().getFouAddress());
                                        OdyApplication.putString(Constants.AREA_CODE_ADDRESS, o.getData().getTwoAddress() + "  " + o.getData().getThrAddress() + "  " + o.getData().getFouAddress());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        if (!isFirst) {
                                            //  mHandler.sendEmptyMessageDelayed(LOADING_MSG, 1000);
                                        }
                                    }
                                }));
                    } else {
                        // mHandler.sendEmptyMessageDelayed(LOADING_MSG, 1000);
                    }
                }
            })
                    .setOnceLocation(true)
                    .startLocation(SplashActivity.this);

        }
        if (!StringUtils.isEmpty(OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, ""))) {
            getAppKey(OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, ""));
        }

        RetrofitFactory.activateApp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<BaseRequestBean>(new SubscriberListener() {
                    @Override
                    public void onNext(Object o) {

                    }
                }));
    }

    private void setSplashPages() {
        for (int i = 0; i < imgs.length; i++) {
            View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.item_splash, null);
            ImageView img = (ImageView) view.findViewById(R.id.item_img);
            TextView txt = (TextView) view.findViewById(R.id.item_txt);
            ImageView imageView = new ImageView(SplashActivity.this);
            img.setImageResource(imgs[i]);
          /*  Glide.with(this)
                    .load(imgs[i])
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(img);*/
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.dipTopx(10) + 20, PxUtils.dipTopx(10)));
            imageView.setPadding(10, 0, 10, 0);
            if (i == 0) {
                imageView.setSelected(true);
            } else {

            }
            imageView.setImageResource(R.drawable.banner_choose_selector);
            viewList.add(imageView);
            linear.addView(imageView);
            if (i == imgs.length - 1) {
                txt.setVisibility(View.VISIBLE);
                txt.setTextColor(getResources().getColor(R.color.white));
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToMainPage();
                    }
                });
            } else {
                txt.setVisibility(View.GONE);
            }
            views.add(view);
        }
        SplashPagerAdapter adapter = new SplashPagerAdapter();
        vp.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(LOADING_MSG);
        mHandler.removeMessages(COUNT_DOWN);
        if (locationManager != null) {
            locationManager.stopLocation();
        }
        super.onDestroy();
    }

    class SplashPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return views.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));// 删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
            container.addView(views.get(position), 0);// 添加页卡
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;// 官方提示这样写
        }

    }


    public void getAppKey(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", mobile);
        params.put("companyId", "30");
        OkHttpManager.getAsyn(Constants.GET_ALIBC, params, new OkHttpManager.ResultCallback<AliBCBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(AliBCBean response) {
                if (response != null && response.data != null) {
                    OdyApplication.putValueByKey(Constants.APP_KEY, response.data.appKey);
                    OdyApplication.putValueByKey(Constants.BC_PASS, response.data.password);
                    OdyApplication.putValueByKey(Constants.BC_USER_ID, response.data.userId);
                    OdyApplication.putValueByKey(Constants.RECEIVER_ID, response.data.receiveId);
                }
            }
        });
    }

    private void adSplash() {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", "guide_pages");
        params.put("platform", "3");
        params.put("pageCode", "APP_GUIDE_PAGE");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
                JumpUtils.ToActivity(JumpUtils.MAIN);
                finish();
            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null) {
                    if (response.data != null && response.data.guide_pages != null && response.data.guide_pages.size() > 0) {
                        for (int i = 0; i < response.data.guide_pages.size(); i++) {
                            View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.item_splash, null);
                            ImageView img = (ImageView) view.findViewById(R.id.item_img);
                            TextView txt = (TextView) view.findViewById(R.id.item_txt);
                            GlideUtil.display(SplashActivity.this, response.data.guide_pages.get(i).imageUrl).placeholder(R.mipmap.guidance_pager1).into(img);
                            ImageView imageView = new ImageView(SplashActivity.this);
                            imageView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.dipTopx(10) + 20, PxUtils.dipTopx(10)));
                            imageView.setPadding(10, 0, 10, 0);
                            if (i == 0) {
                                imageView.setSelected(true);
                            } else {

                            }
                            imageView.setImageResource(R.drawable.banner_choose_selector);
                            viewList.add(imageView);
                            linear.addView(imageView);
                            if (i == response.data.guide_pages.size() - 1) {
                                txt.setVisibility(View.VISIBLE);
                                txt.setTextColor(getResources().getColor(R.color.white));
                                txt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        jumpToMainPage();
                                    }
                                });
                            } else {
                                txt.setVisibility(View.GONE);
                            }
                            views.add(view);
                        }
                    } else {
                        for (int i = 0; i < imgs.length; i++) {
                            View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.item_splash, null);
                            ImageView img = (ImageView) view.findViewById(R.id.item_img);
                            TextView txt = (TextView) view.findViewById(R.id.item_txt);
                            ImageView imageView = new ImageView(SplashActivity.this);
                            img.setImageResource(imgs[i]);
                            imageView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.dipTopx(10) + 20, PxUtils.dipTopx(10)));
                            imageView.setPadding(10, 0, 10, 0);
                            if (i == 0) {
                                imageView.setSelected(true);
                            } else {

                            }
                            imageView.setImageResource(R.drawable.banner_choose_selector);
                            viewList.add(imageView);
                            linear.addView(imageView);
                            if (i == imgs.length - 1) {
                                txt.setVisibility(View.VISIBLE);
                                txt.setTextColor(getResources().getColor(R.color.white));
                                txt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        jumpToMainPage();
                                    }
                                });
                            } else {
                                txt.setVisibility(View.GONE);
                            }
                            views.add(view);
                        }
                    }
                    SplashPagerAdapter adapter = new SplashPagerAdapter();
                    vp.setAdapter(adapter);
                }
            }
        });
    }


    /**
     * 获取广告推荐
     */
    private void getAdvertising(String adCode) {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", adCode);
        params.put("platform", "3");
        params.put("pageCode", "APP_HOME");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, SplashActivity.this.toString(), new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null) {
                    if (null != response.getData() && null != response.getData().tan_ping && response.getData().tan_ping.size() > 0) {
                        OdyApplication.putValueByKey("tang_ping_result", response.getData().tan_ping);
                    }
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
