package com.ody.p2p.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.ody.p2p.BuildConfig;
import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.action.PidUtils;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.okhttputils.UbtEvent;
import com.ody.p2p.okhttputils.UbtGsonutils;
import com.ody.p2p.okhttputils.UbtPageData;
import com.ody.p2p.okhttputils.UbtUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.ProgressDialog;
import com.ody.p2p.views.odyscorllviews.OdyScrollGridView;
import com.ody.p2p.views.odyscorllviews.OdyScrollListView;
import com.ody.p2p.views.odyscorllviews.OdyScrollView;
import com.ody.p2p.views.odyscorllviews.OdyScrollWebView;
import com.ody.p2p.views.odyscorllviews.OdySnapPageLayout;

import org.datatist.sdk.DatatistSDK;
import org.datatist.sdk.Track;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;


/**
 * android 系统中的四大组件之一Activity基类
 *
 * @author lxs
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    /***
     * 整个应用Applicaiton
     **/
    private OdyApplication mApplication = null;
    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    public View getmContextView() {
        return mContextView;
    }

    /**
     * 共通操作
     **/
    protected Operation mBaseOperation = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    protected RelativeLayout layout_title;
    protected TextView tv_title;
    protected ImageView back;
    private boolean isForeground = false;

    public ProgressDialog progressDialog;

    protected Context mContext;

    protected boolean bActive = false;

    public final static int MSG_CHAT_MIS = 0x1000;
    public final static int MSG_CHAT_FLAT_SHOW = 0x1001;

    protected int pageCode = 1;

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CHAT_MIS) {
                if (iv_chat != null) {
                    hideBtnChat();
                    mHandler.sendEmptyMessageDelayed(MSG_CHAT_FLAT_SHOW, 600);
                }
            } else if (msg.what == MSG_CHAT_FLAT_SHOW) {
                if (iv_flat != null) {
                    iv_flat.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initEventReport(this);
        if (isNeedShow()) {


            getWindow().setFormat(PixelFormat.TRANSLUCENT);

            super.onCreate(savedInstanceState);
            Log.d("samuel", TAG + "-->onCreate()");
            bActive = true;
            //初始化title
            //initTitle();
            //设置渲染视图View
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //获取应用Application
            mApplication = (OdyApplication) getApplicationContext();
            mContext = this;
            //将当前Activity压入栈
            context = new WeakReference<Activity>(this);
            mApplication.pushTask(context);
            //实例化共通操作
            mBaseOperation = new Operation(this);
            //初始化控件
            try {
                mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
                setContentView(mContextView);
                if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                    StatusBarCompat.setStatusBarColor(this, Color.parseColor("#B0B0B0"), true);
                } else {
                    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white), true);
                }
                initView(mContextView);
            } catch (Exception e) {
                finish();
                return;
            }
            getNetTAG();
            if (!OdyApplication.SCHEME.equals("saas") && !OdyApplication.SCHEME.equals("lyf")) {
                showChat();
                //mHandler.sendEmptyMessageDelayed(MSG_CHAT_MIS, 5000);
            }
            initFailed();
            //初始化控件的监听
            initListener();
            //初始化接口
            initPresenter();
            //业务操作
            doBusiness(this);
            setDarkStatusIcon(true);

            initPushEvent();
        } else {
            super.onCreate(savedInstanceState);
        }

    }

    protected boolean isNeedShow() {
        return true;
    }

    public void initPushEvent() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName();    //类名
        String className = info.topActivity.getClassName();
        Log.d(TAG, className);//完整类名
        String packageName = info.topActivity.getPackageName();

        final Map<String, String> eventMap = new HashMap<>();
        if (!className.contains("LyfWebActivity")) {

            Track.track().pageview().setTitle(shortClassName).setUrl(className).trackPageview(eventMap).submit(DatatistSDK.getTracker());
        }

    }

    public void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "BaseActivity-->onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "BaseActivity-->onStart()");
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);


        initUbdData("17", "enterPage");


        super.onResume();
        //Log.d(TAG, "BaseActivity-->onResume()");
        resume();
    }

    public void initUbdData(String ev, String en) {

        UbtEvent ubtEvent = new UbtEvent();
        ubtEvent.v = BuildConfig.VERSION_NAME;
        ubtEvent.ut = OdyApplication.getLoginUt();
        ubtEvent.did = OdyApplication.getDeviceId();
        ubtEvent.cha = "ANDROID";
        ubtEvent.of = "";
        ubtEvent.inf = "";


        ubtEvent.pid = PidUtils.getPid(TAG);
        ubtEvent.ct = System.currentTimeMillis() + "";
        ubtEvent.bt = "pv";
        ubtEvent.ev = ev;
        ubtEvent.tv = "1.0";

        Random rnd = new Random();
        int tempInt = rnd.nextInt(25);
        UbtPageData ubtPageData = new UbtPageData();
        ubtPageData.pn = tempInt + "";
        ubtPageData.pt = TAG;
        ubtPageData.pcd = "";
        ubtPageData.sk = "";
        ubtPageData.cid = "";
        ubtPageData.cms = "";
        ubtPageData.oc = "";
        ubtPageData.sn = "";
        ubtPageData.ocha = "";
        ubtPageData.ru = "";
        ubtPageData.rf = "";
        ubtPageData.rp = "";
        ubtPageData.ppid = tempInt + "";
        ubtPageData.cpu = TAG;
        ubtPageData.cost = "";
        ubtPageData.st = System.currentTimeMillis() + "";
        ubtPageData.en = en;

        ubtEvent.data = UbtGsonutils.toJson(ubtPageData);

        UbtUtils.init(ubtEvent);


    }

    private static boolean isAppInBackground = false;

    private static Object mLifecycleCallbacks;


    public void initEventReport(Context context) {
        if (mLifecycleCallbacks == null) {
            context.registerComponentCallbacks(new ComponentCallbacks2() {
                @Override
                public void onTrimMemory(int level) {
                    if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {

                    }
                }

                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                }

                @Override
                public void onLowMemory() {
                }
            });

            Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {

                    try {
                        initUbdData("14", "appResume");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {


                    try {

                        initUbdData("13", "appSleep");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }
            };
            mLifecycleCallbacks = callbacks;
            Context app = context.getApplicationContext();
            if (app instanceof Application) {
                ((Application) app).registerActivityLifecycleCallbacks(callbacks);
            }
        }
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
        Log.d(TAG, "BaseActivity-->onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "BaseActivity-->onStop()");
    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initUbdData("18", "endPage");

        if (isNeedShow()) {
            hideLoading();
            StringUtils.fixInputMethodManagerLeak(getContext());
            Log.d(TAG, "BaseActivity-->onDestroy()");
            bActive = false;
            mHandler.removeMessages(MSG_CHAT_MIS);
            mHandler.removeMessages(MSG_CHAT_FLAT_SHOW);
            hideLoading();
//        unbindDrawables(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));
            OkHttpManager.cancelRequest(getNetTAG());
            mApplication.removeTask(context);
            destroy();

        }
        try {
            if (Looper.getMainLooper() == Looper.myLooper() && !this.isFinishing()) {
                Glide.with(this).pauseRequests();
            }
        } catch (Exception e) {

        }


    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected Activity getContext() {
        if (null != context) return context.get();
        else return null;
    }

    /**
     * 获取共通操作机能
     */
    public Operation getOperation() {
        return this.mBaseOperation;
    }


    public void initTitle() {
        layout_title = UiUtils.getTitleBar(this, getResources().getString(R.string.ody_title));
        tv_title = (TextView) layout_title.findViewById(R.id.tv_title);
        //setTitle();
    }

    /**
     * 显示载入框
     *
     * @param msg
     */
    public void showLoading(String msg) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, msg);
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, getContext().getString(R.string.loading_waiting));
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    /**
     * 取消载入框
     */
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = mApplication.getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 整体路由的跳转
     *
     * @param toActivity
     */
    public void skipActivity(String toActivity) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(toActivity);
//        activityRoute.open();
        JumpUtils.ToActivity(toActivity);
    }


    //初始化控件监听
    public void initListener() {
    }

    private void unbindDrawables(View view) {
        if (view == null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            try {
                ((ViewGroup) view).removeAllViews();
            } catch (Exception e) {

            }
        }
    }

    protected ImageView iv_chat;//客服入口

    public void showChat() {
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            iv_chat = new ImageView(this);
            iv_chat.setImageResource(R.drawable.bg_bt_chat);
            //showChatFlat();
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
            lp.setMargins(PxUtils.dipTopx(15), 0, 0, PxUtils.dipTopx(75));
            iv_chat.setLayoutParams(lp);
            iv_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (OdyApplication.SCHEME.equals("lyf")) {
//                        final YWIMKit mIMKit = YWAPI.getIMKitInstance("testpro1", OdyApplication.APP_KEY);
//                        Intent intent = mIMKit.getChattingActivityIntent("testpro3", OdyApplication.APP_KEY);
//                        startActivity(intent);
                        //userid是客服帐号，第一个参数是客服帐号，第二个是组ID，如果没有，传0
                        String userId = OdyApplication.getValueByKey(Constants.BC_USER_ID, "");
                        if (StringUtils.isEmpty(userId)) {
                            return;
                        }
                        String password = OdyApplication.getValueByKey(Constants.BC_PASS, "");
                        final String receiverId = OdyApplication.getValueByKey(Constants.RECEIVER_ID, "");
                        String app_key = OdyApplication.getValueByKey(Constants.APP_KEY, "");
                        final YWIMKit mIMKit = YWAPI.getIMKitInstance(userId, app_key);
                        IYWLoginService loginService = mIMKit.getLoginService();
                        YWLoginParam loginParam = YWLoginParam.createLoginParam(userId, password);
                        loginService.login(loginParam, new IWxCallback() {
                            @Override
                            public void onSuccess(Object... arg0) {
                                final EServiceContact contact = new EServiceContact(receiverId, 0);
                                Intent intent = mIMKit.getChattingActivityIntent(contact);
                                startActivity(intent);
                            }

                            @Override
                            public void onProgress(int arg0) {

                            }

                            @Override
                            public void onError(int errCode, String description) {
                                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                                ToastUtils.showShort(description);
                            }
                        });

                    } else {
                        String settingId = OdyApplication.getValueByKey("settingId" + pageCode, "");
                        if (settingId != null && settingId.length() > 0) {
//                            ChatUtils.startChat(mContext, settingId);
//                            TODO 1
                        } else {
                            ToastUtils.showShort(getString(R.string.connection_service_fail));
                        }
                    }
                }
            });
            view.addView(iv_chat);
        }
    }


    protected ImageView iv_flat;

    public void showChatFlat() {
        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            iv_flat = new ImageView(this);
            iv_flat.setImageResource(R.drawable.bt_left);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            lp.setMargins(0, 0, 0, PxUtils.dipTopx(125));
            iv_flat.setLayoutParams(lp);
            iv_flat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iv_chat != null) {
                        showBtnChat();
                        iv_flat.setVisibility(View.GONE);
                        mHandler.removeMessages(MSG_CHAT_MIS);
                        mHandler.sendEmptyMessageDelayed(MSG_CHAT_MIS, 5000);
                    }
                }
            });
            view.addView(iv_flat);
            iv_flat.setVisibility(View.GONE);
        }
    }

    public void hideChat() {
        if (iv_chat != null) {
            iv_chat.setVisibility(View.GONE);
        }
    }

    public View viewFailed;

    public LinearLayout ll_notdataH5;

    public TextView tv_faild;

    //
    protected void showFailed(boolean showFlag, int type) {
        if (viewFailed != null) {
            viewFailed.setVisibility(showFlag ? View.VISIBLE : View.GONE);
            if (type == 0) {
                tv_faild.setText(R.string.a_o_load_faile);
            } else if (type == 1) {
                tv_faild.setText(R.string.Network_convulsions_please_checking);
            }
        }
    }

    public void initFailed() {
        View view = getContext().findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            viewFailed = LayoutInflater.from(this).inflate(R.layout.view_failed_load, null);
            viewFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAgain();
                }
            });
            tv_faild = (TextView) viewFailed.findViewById(R.id.tv_faild);
            ll_notdataH5 = (LinearLayout) viewFailed.findViewById(R.id.ll_notdataH5);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.TOP | Gravity.RIGHT;
            lp.setMargins(0, PxUtils.dipTopx(43), 0, 0);
            viewFailed.setLayoutParams(lp);
            ((FrameLayout) view).addView(viewFailed);
            viewFailed.setVisibility(View.GONE);
        }
    }

    public void setFailedMargins(int left, int top, int right, int bottom) {
        if (viewFailed == null) {
            return;
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.TOP | Gravity.RIGHT;
        lp.setMargins(PxUtils.dipTopx(left), PxUtils.dipTopx(top), PxUtils.dipTopx(right), PxUtils.dipTopx(bottom));
        viewFailed.setLayoutParams(lp);
    }

    protected ImageView iv_top;//回到顶部

    public void showTop(final View viewScrolled) {
        View view = getContext().findViewById(android.R.id.content);
        if (view instanceof FrameLayout) {
            iv_top = new ImageView(getContext());
            iv_top.setImageResource(R.drawable.bg_bt_top);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            lp.setMargins(0, 0, PxUtils.dipTopx(15), PxUtils.dipTopx(105));
            iv_top.setLayoutParams(lp);
            iv_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewScrolled instanceof RecyclerView) {
                        ((RecyclerView) viewScrolled).smoothScrollToPosition(0);
                    } else if (viewScrolled instanceof ScrollView) {
                        ((ScrollView) viewScrolled).smoothScrollTo(0, 0);
                    } else if (viewScrolled instanceof WebView) {
                        viewScrolled.scrollTo(0, 0);
                    } else if (viewScrolled instanceof ListView) {
                        ((ListView) viewScrolled).smoothScrollToPosition(0);
                    } else if (viewScrolled instanceof GridView) {
                        ((GridView) viewScrolled).smoothScrollToPosition(0);
                    }
                }
            });
            ((FrameLayout) view).addView(iv_top);
            iv_top.setVisibility(View.GONE);
            if (viewScrolled instanceof RecyclerView) {
                ((RecyclerView) viewScrolled).addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (recyclerView.getChildCount() > 0) {
//                            Log.d("tag", recyclerView.getChildAt(0).getY() + "****");
                            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                                int po = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                                if (po > 0 || po == -1) {
                                    showBtnTop();
                                } else {
                                    hideBtnTop();
                                }
                            }
                            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                                int po = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                                if (po > 0 || po == -1) {
                                    showBtnTop();
                                } else {
                                    hideBtnTop();
                                }
                            }
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollView) {
                ((OdyScrollView) viewScrolled).setScrollListener(new OdyScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollListView) {
                ((OdyScrollListView) viewScrolled).setScrollListener(new OdyScrollListView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollListView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollGridView) {
                ((OdyScrollGridView) viewScrolled).setScrollListener(new OdyScrollGridView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollGridView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdyScrollWebView) {
                ((OdyScrollWebView) viewScrolled).setScrollListener(new OdyScrollWebView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdyScrollWebView scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            } else if (viewScrolled instanceof OdySnapPageLayout) {
                ((OdySnapPageLayout) viewScrolled).setScrollListener(new OdySnapPageLayout.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(OdySnapPageLayout scrollView, int x, int y, int oldx, int oldy) {
                        if (y > 0) {
                            showBtnTop();
                        } else {
                            hideBtnTop();
                        }
                    }
                });
            }
        }
    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        if (iv_top.getVisibility() == View.GONE) {
            iv_top.setVisibility(View.VISIBLE);
//            iv_top.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_enter));
            Animator animator = AnimatorInflater.loadAnimator(getContext(), R.anim.btn_enter);
            animator.setTarget(iv_top);
            animator.start();
        }
    }

    /**
     * 隐藏置顶按钮
     */
    private void hideBtnTop() {
        // TODO Auto-generated method stub
        if (iv_top.getVisibility() == View.VISIBLE) {
            iv_top.setVisibility(View.GONE);
            iv_top.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_exit));
        }
    }

    /**
     * 隐藏客服按钮
     */
    private void hideBtnChat() {
        // TODO Auto-generated method stub
        if (iv_chat.getVisibility() == View.VISIBLE) {
            iv_chat.setVisibility(View.GONE);
            iv_chat.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_exit_hor));
        }
    }


    /**
     * 显示客服按钮
     */
    private void showBtnChat() {
        // TODO Auto-generated method stub
        if (iv_chat.getVisibility() == View.GONE) {
            iv_chat.setVisibility(View.VISIBLE);
            iv_chat.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_enter_hor));
        }
    }

    public String getNetTAG() {
        return this.getLocalClassName();
    }


    protected void loadAgain() {

    }

    public void setPopupWindowBackgroundBlack(PopupWindow popupWindow) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
}
