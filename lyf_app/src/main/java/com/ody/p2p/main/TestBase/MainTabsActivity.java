package com.ody.p2p.main.TestBase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.lyfen.android.DevelopActivity;
import com.lyfen.android.MemberFragment;
import com.netease.nim.demo.contact.helper.UserUpdateHelper;
import com.netease.nim.demo.login.LoginHelper;
import com.netease.nim.demo.main.fragment.YunXinFragment;
import com.netease.nim.demo.main.util.IMRedDotManager;
import com.netease.nim.demo.util.JurisdictionButton;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.ody.ds.lyf_home.HomeFragment;
import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.PolicyBean;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.UpGradeBean;
import com.ody.p2p.UserInfoBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.base.action.GrestCouponBean;
import com.ody.p2p.classesification.CategoryFragment;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.entity.YiModel;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.main.DisCoverPolicy;
import com.ody.p2p.main.IMPolicy;
import com.ody.p2p.main.InitDataBean;
import com.ody.p2p.main.MainActivity;
import com.ody.p2p.main.MainPresenterImpl;
import com.ody.p2p.main.R;
import com.ody.p2p.main.TabPolicy;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.shopcart.LyfShopCartFragment;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.AreacodeBean;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.home.AdBean;
import com.ody.p2p.retrofit.home.PersonalBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.retrofit.user.Alias;
import com.ody.p2p.shopcart.ShoppingCartFragment;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.TKUtil;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.TPDialog;
import com.ody.p2p.views.tablayout.TabChooser;
import com.ody.p2p.views.tablayout.TabChooserBean;
import com.ody.p2p.views.tablayout.TabSelectListener;
import com.ody.p2p.webactivity.NoTitleWebFragment;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.msg.UnicornMessage;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.lyfen.android.utils.UIUtils.getContext;

/**
 * Created by mac on 2018/1/30.
 */

public class MainTabsActivity extends BaseCommActivity<MainPresenter> implements MainView {

    protected boolean bActive = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAllWidget() {

        if (OdyApplication.getValueByKey(Constants.IM_TOGGLE, "0").equals("0")) {


            policy = new DisCoverPolicy(context());

        } else {
            policy = new IMPolicy(context());

        }
        //policy = new IMPolicy(context());
        // policy = new IMPolicy(context());
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        titleArr = policy.getTabText();

        imgArr = policy.getImg();


        tab_bar = (TabChooser) findViewById(R.id.tab_bar);
        img_select_two = (ImageView) findViewById(R.id.img_select_two);
        EventBus.getDefault().register(this);


        findViewById(R.id.tv_dev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainTabsActivity.this, DevelopActivity.class));
            }
        });


        fragmentHome = new HomeFragment();
        fragmentClass = new CategoryFragment();


        if (policy.getfragment() instanceof NoTitleWebFragment) {
            fragmentDiscover = (NoTitleWebFragment) policy.getfragment();
            fragmentDiscover.setLoadType(NoTitleWebFragment.JUMP_REPLACE);
            fragmentDiscover.setUrl(OdyApplication.H5URL + "/view/h5/30.html?appback=true");
            fragments.add(fragmentDiscover);
        }


       /* fragmentMy = new NoTitleWebFragment();
        fragmentMy.setLoadType(NoTitleWebFragment.JUMP_NORMAL);
        fragmentMy.setUrl(OdyApplication.H5URL + "/my/home.html");*/
        fragmentMy = new MemberFragment();


//        fragmentMy.setUrl(OdyApplication.H5URL + "/test.html");
        fragmentShoppingCart = new LyfShopCartFragment();
        fragments.add(fragmentHome);
        fragments.add(fragmentClass);

//        fragments.add(liveFragment);
        fragments.add(fragmentShoppingCart);
        fragments.add(fragmentMy);
        for (int i = 0; i < titleArr.length; i++) {
            TabChooserBean bean = new TabChooserBean();
            bean.imagesrc = imgArr[i];
            bean.tabcontent = titleArr[i];
            list.add(bean);
        }
        newFragment = fragmentHome;
        addFragment(newFragment);
        tab_bar.setTabList(list);

        locationManager = new LocationManager(this);
        locationManager
                .setOnceLocation(true)
                .setLocationListener(new LocationManager.LocationListener() {
                    @Override
                    public void onLocationChanged(final LocationManager.MapLocation location) {
                        if (location == null) return;
                        RetrofitFactory.getArea(location.province, location.city, location.district)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriber<AreacodeBean>(new SubscriberListener<AreacodeBean>() {
                                    @Override
                                    public void onNext(final AreacodeBean o) {
                                        if (StringUtils.isEmpty(o.getData().getTwoAddress())) {
                                            OdyApplication.putString(Constants.PROVINCE, "");
                                            EventMessage msg = new EventMessage();
                                            msg.flag = EventMessage.CHANGE_AREA;
                                            EventBus.getDefault().post(msg);
                                            return;
                                        }
                                        if (!OdyApplication.getString(Constants.PROVINCE, "").equals(o.getData().getTwoAddress())) {
                                            final CustomDialog dialog = new CustomDialog(MainTabsActivity.this, "系统定位您现在在" + location.province + "," + "\n是否要切换到上海", 2);
                                            dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                                @Override
                                                public void Confirm() {
                                                    OdyApplication.putString(Constants.AREA_CODE, o.getData().getFouCode() + "");
                                                    OdyApplication.putString(Constants.PROVINCE, o.getData().getTwoAddress());
                                                    OdyApplication.putString(Constants.CITY, o.getData().getThrAddress());
                                                    OdyApplication.putString(Constants.AREA_NAME, o.getData().getFouAddress());
                                                    OdyApplication.putString(Constants.AREA_CODE_ADDRESS, o.getData().getTwoAddress() + "  " + o.getData().getThrAddress() + "  " + o.getData().getFouAddress());
                                                    EventMessage msg = new EventMessage();
                                                    msg.flag = EventMessage.CHANGE_AREA;
                                                    EventBus.getDefault().post(msg);
                                                }
                                            });
                                            dialog.show();
                                        }
                                    }
                                }));
                    }
                }).startLocation(this);
        tab_bar.setTabSelectListener(new TabSelectListener() {
            @Override
            public void select(int position) {
                switch (position) {
                    case 0:
                        isClicktalk = false;
                        oldFragment = newFragment;
                        switchFragment(oldFragment, fragmentHome);
                        newFragment = fragmentHome;
                        if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                        } else {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                        }
                        img_select_two.setSelected(false);
                        break;
                    case 1:
                        isClicktalk = false;
                        oldFragment = newFragment;
                        switchFragment(oldFragment, fragmentClass);
                        newFragment = fragmentClass;
                        if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                        } else {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                        }
                        img_select_two.setSelected(false);
                        break;

                    case 2:
                        isClicktalk = false;
                        if (policy.getfragment() instanceof NoTitleWebFragment) {
                            if (fragmentDiscover == null) {
                                fragmentDiscover = (NoTitleWebFragment) policy.getfragment();
                                fragmentDiscover.setLoadType(NoTitleWebFragment.JUMP_REPLACE);
                                fragmentDiscover.setUrl(OdyApplication.H5URL + "/view/h5/30.html?appback=true");
                            }
                            oldFragment = newFragment;
                            switchFragment(oldFragment, fragmentDiscover);
                            newFragment = fragmentDiscover;
                            if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                                StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                            } else {
                                StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                            }
                            img_select_two.setSelected(true);
                        } else {
                            if (null != OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null) && !"".equals(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null))) {
                                presenter.getPersonalMes(false);
                            } else {
                                isClicktalk = true;
                                if (fragmentIM == null) {

                                    fragmentIM = YunXinFragment.newInstance(""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , "");
                                } else {
                                    fragmentIM.setData(""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , "");
                                }


                                oldFragment = newFragment;
                                switchFragment(oldFragment, fragmentIM);
                                newFragment = fragmentIM;
                                if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                                    StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                                } else {
                                    StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                                }
                                img_select_two.setSelected(true);


                            }
                        }


                        break;
                    case 3:
                        isClicktalk = false;
                        oldFragment = newFragment;
                        switchFragment(oldFragment, fragmentShoppingCart);
                        newFragment = fragmentShoppingCart;
                        if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                        } else {
                            StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                        }
                        img_select_two.setSelected(false);
                        break;
                    case 4:
                        isClicktalk = false;
                        oldFragment = newFragment;
                        switchFragment(oldFragment, fragmentMy);
                        newFragment = fragmentMy;
                        StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#FFB300"), false);
                        img_select_two.setSelected(false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void clickView(View v) {

    }

    @Override
    public void setTopTitle(String title) {

    }

    @Override
    public void showProgressBar() {

    }


    public static final int HOME_FRAGMENT = 0;
    public static final int CLASS_FRAGMENT = 1;
    public static final int LIVE_FRAGMETN = 2;
    public static final int SHOPCART_FRAGMENT = 3;
    public static final int MY_FRAGMENT = 4;

    private TabChooser tab_bar;
    private List<TabChooserBean> list = new ArrayList<>();
    private Fragment newFragment;
    private Fragment oldFragment;
    private List<Fragment> fragments = new ArrayList<>();

    //    private String [] titleArr = new String[]{"首页","分类","直播","购物车","我的"};
    private String[] titleArr;
    //    private int [] imgArr = new int[]{R.drawable.select_home,R.drawable.select_classification,R.drawable.select_live,R.drawable.select_buycart,R.drawable.select_my};
    private int[] imgArr;
    private HomeFragment fragmentHome;
    private CategoryFragment fragmentClass;
    private YunXinFragment fragmentIM;

    private NoTitleWebFragment fragmentDiscover;
    //    private LiveListFragment liveFragment;
    private MemberFragment fragmentMy;
    private ShoppingCartFragment fragmentShoppingCart;
    private long mExitTime;

    private com.ody.p2p.main.MainPresenter presenter;
    private String appChannel;

    protected LocationManager locationManager;
    private FuncBean.Data.AdSource actAd;
    private List<FuncBean.Data.AdSource> actSours;
    private ImageView img_select_two;

    private boolean isClicktalk = false;


    YiModel model = new YiModel();

    TabPolicy policy;


    private void addUnreadCountChangeListener(boolean add) {
        Unicorn.addUnreadCountChangeListener(listener, add);
    }

    private UnreadCountChangeListener listener = new UnreadCountChangeListener() { // 声明一个成员变量
        @Override
        public void onUnreadCountChange(int count) {
            model.setCount(count);

            UnicornMessage message = Unicorn.queryLastMessage();
            if (message != null) {
                model.setContent(message.getContent());
                model.setDate(message.getTime() + "");
            }


            EventMessage msg = new EventMessage();
            msg.flag = EventMessage.YUNXUN;
            msg.yiModel = model;
            EventBus.getDefault().post(msg);

        }
    };


    public void switchFragment(Fragment from, Fragment to) {
        if (!bActive) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (to.isAdded() || fragmentManager.getFragments().contains(to)) {
            // 隐藏当前的fragment，显示下一个
            fragmentManager.beginTransaction().hide(from).show(to).commitAllowingStateLoss();
        } else {
            // 隐藏当前的fragment，add下一个到Activity中
            fragmentManager.beginTransaction().hide(from).add(R.id.centerlayout, to).commitAllowingStateLoss();
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.centerlayout, fragment).commitAllowingStateLoss();
        }
    }

    public void setFragment(int code) {
        switch (code) {
            case HOME_FRAGMENT:
                tab_bar.setCurrentItem(0);
                oldFragment = newFragment;
                switchFragment(oldFragment, fragmentHome);
                newFragment = fragmentHome;
                break;
            case CLASS_FRAGMENT:
                tab_bar.setCurrentItem(1);
                oldFragment = newFragment;
                switchFragment(oldFragment, fragmentClass);

                newFragment = fragmentClass;
                break;
            case LIVE_FRAGMETN:
                tab_bar.setCurrentItem(2);
                oldFragment = newFragment;
                switchFragment(oldFragment, fragmentDiscover);
                newFragment = fragmentDiscover;
                break;
            case SHOPCART_FRAGMENT:
                tab_bar.setCurrentItem(3);
                oldFragment = newFragment;
                switchFragment(oldFragment, fragmentShoppingCart);
                newFragment = fragmentShoppingCart;
                break;
            case MY_FRAGMENT:
                tab_bar.setCurrentItem(4);
                oldFragment = newFragment;
                switchFragment(oldFragment, fragmentMy);
                newFragment = fragmentMy;
                break;
            default:
                break;
        }
    }

    @Override
    public void initData(InitDataBean bean) {
        if (bean.data != null && bean.data.upGrade != null) {
            if (bean.data.upGrade.updateFlag = true) {
            }
        }
    }


    @Override
    public void initCartNum(int count) {
        tab_bar.showDot(3, true, count);
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void Upgrade(UpGradeBean.Data data) {
        if (data.updateFlag == 1) {//需要更新版本
            if (!TextUtils.isEmpty(data.getObtainUrl()) && data.getUpdateType() != 0) {


                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE

                )
                        .subscribe(granted -> {
                            if (granted) {
                                Bundle bd = new Bundle();
                                bd.putString("appName", data.getAppName());//文件名称
                                bd.putString("describe", data.getDescribe());//描述
                                bd.putString("obtainUrl", data.getObtainUrl());//下载路径
                                bd.putString("versionName", data.getVersionName());//版本名称
                                bd.putString("versionCode", data.getVersionCode() + "");//版本code
                                bd.putString("packageSize", data.getPackageSize());//文件大小
                                bd.putInt("type", data.getUpdateType());// 0 不更新  1 选择更新  2 强制更新
                                JumpUtils.ToActivity(JumpUtils.UPLEVEL, bd);


                            } else {
                                Toast.makeText(MainTabsActivity.this, "为了更好的体验，开启存储权限", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }

    @Override
    public void initTanPin(FuncBean bean) {
        if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
            if (bean.data != null && bean.data.registered_coupon != null && bean.data.registered_coupon.size() > 0) {
                TPDialog dialog = new TPDialog(getContext(), bean.data.registered_coupon, R.style.NobackDialog, 1);
                dialog.show();
            } else {
                if (bean.data != null && bean.data.newcomers_popup != null && bean.data.newcomers_popup.size() > 0) {
                    actAd = bean.data.newcomers_popup.get(0);
                    actSours = bean.data.newcomers_popup;
                    if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                        TPDialog dialog = new TPDialog(getContext(), bean.data.newcomers_popup, R.style.NobackDialog, 0);
                        dialog.show();
                    } else {
                        presenter.isNewUser(0);
                    }
                }
            }
        } else if (bean.data != null && bean.data.newcomers_popup != null && bean.data.newcomers_popup.size() > 0) {
            actAd = bean.data.newcomers_popup.get(0);
            actSours = bean.data.newcomers_popup;
            presenter.isNewUser(0);
        }
    }

    @Override
    public void initIsNewUser(int newUser, int flag) {
        if (newUser == 1) {
            if (flag == 0) {
                if (actSours != null) {
                    TPDialog dialog = new TPDialog(getContext(), actSours, R.style.NobackDialog, 0);
                    dialog.show();
                }
            } else if (flag == 1) {
                if (actAd != null) {
                    JumpUtils.toActivity(actAd);
                }
            }
        }
    }

    @Override
    public void initFirstLoginCoupon(GrestCouponBean firstLoginCouponBean) {


        try {
            TPDialog tpDialog = new TPDialog(this, firstLoginCouponBean.data.register_coupon_guide, R.style.NobackDialog);
            if (!tpDialog.isShowing()) {
                tpDialog.show();
            }

        } catch (Exception e) {

        }

//        if (firstLoginCouponBean != null && firstLoginCouponBean.data != null && firstLoginCouponBean.data.canUseCouponList != null && firstLoginCouponBean.data.canUseCouponList.size() > 0) {
////            FirstLoginCouponDialog firstLoginCouponDialog = new FirstLoginCouponDialog(getContext(), firstLoginCouponBean.data.canUseCouponList, R.style.NobackDialog);
////            firstLoginCouponDialog.show();
//        }
    }

    @Override
    public void load(String id) {
        TKUtil.upload(this, com.ody.p2p.main.constant.Constants.HOME, id, "", "", 1);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_the_program, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void onEventMainThread(EventbusMessage event) {
        if (event.flag == EventbusMessage.GET_CART_COUNT) {
            presenter.initCartNum();
        }
    }

    @Subscribe
    public void onEventMainThread(JsEventMessage event) {
        if (event.function == JsEventMessage.FUNCTION_PAGE_REFRESH) {
            if (fragmentMy != null) {
//                fragmentMy.reload();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        if (event.flag == EventMessage.IS_SP) {
            presenter.isNewUser(1);
        } else if (event.flag == EventMessage.FIRST_LOGIN) {
            presenter.firstLoginCoupon();
        } else if (event.flag == EventMessage.REFRESH_UT) {

            refreshUserInfo();

            if (TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
                //注销,替换成原来的
                ServiceManager.getInstance().doPolicy(new AliServicePolicy(), this);
                // NIMClient.getService(AuthService.class).logout();
                Unicorn.logout();
//                LogoutHelper.logout();
                tab_bar.showDot(2, false, 0);
                OdyApplication.putValueByKey(Constants.IM_TOGGLE, "0");
                OdyApplication.putValueByKey(Constants.SERVICE_TOGGLE, "0");
                //恢复原来的
                list.clear();
                policy = new DisCoverPolicy(context());

                titleArr = policy.getTabText();

                imgArr = policy.getImg();

                for (int j = 0; j < titleArr.length; j++) {
                    TabChooserBean bean = new TabChooserBean();
                    bean.imagesrc = imgArr[j];
                    bean.tabcontent = titleArr[j];
                    list.add(bean);
                }
                tab_bar.setTabList(list);

            } else {
                getpolicy();
                //解决密码登录返回的问题


                if (isClicktalk == true) {
                    tab_bar.setCurrentItem(0);
                    //presenter.getPersonalMes();
                    isClicktalk = false;
                    oldFragment = newFragment;
                    switchFragment(oldFragment, fragmentHome);
                    newFragment = fragmentHome;
                    if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                        StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
                    } else {
                        StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
                    }
                    img_select_two.setSelected(false);
                }
            }


        } else if (event.flag == EventMessage.REDNUM) {

            tab_bar.showDot(2, true, event.redNumber);

        } else if (event.flag == EventMessage.JUMPIM) {
            tab_bar.setCurrentItem(2);

            isClicktalk = false;
            oldFragment = newFragment;
            switchFragment(oldFragment, fragmentIM);
            newFragment = fragmentIM;
            if (Build.MODEL.toUpperCase().contains("OPPO") || Build.MODEL.toUpperCase().contains("LENOVO")) {
                StatusBarCompat.setStatusBarColor(MainTabsActivity.this, Color.parseColor("#B0B0B0"), true);
            } else {
                StatusBarCompat.setStatusBarColor(MainTabsActivity.this, ContextCompat.getColor(MainTabsActivity.this, R.color.white), true);
            }
            img_select_two.setSelected(false);
        }
    }

    private void refreshUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.USER_INFO, new OkHttpManager.ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(UserInfoBean response) {
                if (null != response && null != response.getData()) {
                    saveUserInfo(response.getData());
                }
            }
        }, params);

        UserUpdateHelper.update(UserInfoFieldEnum.AVATAR, OdyApplication.getValueByKey(Constants.HEAD_PIC_URL, ""), null);

    }


    private void saveUserInfo(UserInfoBean.Data data) {
        OdyApplication.putValueByKey(Constants.USER_IS_DISTRIBUTOR, data.isDistributor());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
            IMRedDotManager.newInstent().destoryEvent();
            if (locationManager != null) {
                locationManager.stopLocation();
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void setPersonalData(PersonalBean response, boolean isImlogin) {


        PersonalBean.PersonalData data = response.data;
        //修改IM个人资料
        RequestCallbackWrapper callback = new RequestCallbackWrapper() {
            @Override
            public void onResult(int code, Object result, Throwable exception) {
            }
        };

        OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, data.mobile);

        UserUpdateHelper.update(UserInfoFieldEnum.Name, data.nickname == null ? data.mobile : data.nickname, callback);
        UserUpdateHelper.update(UserInfoFieldEnum.GENDER, (Integer.parseInt(data.sex) + 1) + "", callback);
        UserUpdateHelper.update(UserInfoFieldEnum.MOBILE, data.mobile, callback);
        UserUpdateHelper.update(UserInfoFieldEnum.AVATAR, OdyApplication.getValueByKey(Constants.HEAD_PIC_URL, data.headPicUrl), callback);
        JurisdictionButton.querySwitch(this);

        if (isImlogin) {
            PersonalBean.PersonalData person = response.data;
            LoginHelper.register(MainTabsActivity.this, person.id, person.mobile, person.nickname, OdyApplication.getValueByKey(Constants.USER_LOGIN_UT) + "", person.sex, person.headPicUrl);
            IMRedDotManager.newInstent().init(this);
            return;
        }
        if (fragmentIM == null) {

            fragmentIM = YunXinFragment.newInstance(data.id
                    , data.mobile
                    , data.headPicUrl
                    , data.nickname == null ? data.mobile : data.nickname
                    , OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "")
                    , (Integer.parseInt(data.sex) + 1) + "");


        } else {
            fragmentIM.setData(data.id
                    , data.mobile
                    , data.headPicUrl
                    , data.nickname == null ? data.mobile : data.nickname
                    , OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "")
                    , (Integer.parseInt(data.sex) + 1) + "");

        }


        oldFragment = newFragment;
        switchFragment(oldFragment, fragmentIM);
        newFragment = fragmentIM;

        img_select_two.setSelected(true);


    }


    public void getpolicy() {
        Map<String, String> params = new HashMap<>();
        params.put("token", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("terminal", "Andriod");
        OkHttpManager.getAsyn(Constants.POLICY, params, new OkHttpManager.ResultCallback<PolicyBean>() {
            @Override
            public void onError(Request request, Exception e) {


            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(PolicyBean response) {
                if (null != response && response.data.size() != 0) {
                    for (int i = 0; i < response.data.size(); i++) {
                        if (response.data.get(i).type.equals("1")) {
                            OdyApplication.putValueByKey(Constants.IM_TOGGLE, response.data.get(i).status);


                            if (OdyApplication.getValueByKey(Constants.IM_TOGGLE, "0").equals("0")) {

                                list.clear();
                                policy = new DisCoverPolicy(context());

                                titleArr = policy.getTabText();

                                imgArr = policy.getImg();

                                for (int j = 0; j < titleArr.length; j++) {
                                    TabChooserBean bean = new TabChooserBean();
                                    bean.imagesrc = imgArr[j];
                                    bean.tabcontent = titleArr[j];
                                    list.add(bean);
                                }
                                tab_bar.setTabList(list);


                            } else {
                                //多访问下个人信息
                                presenter.getPersonalMes(true);


                                list.clear();
                                policy = new IMPolicy(context());

                                titleArr = policy.getTabText();

                                imgArr = policy.getImg();

                                for (int j = 0; j < titleArr.length; j++) {
                                    TabChooserBean bean = new TabChooserBean();
                                    bean.imagesrc = imgArr[j];
                                    bean.tabcontent = titleArr[j];
                                    list.add(bean);
                                }
                                tab_bar.setTabList(list);


                            }
                        }
                        if (response.data.get(i).type.equals("2")) {

                            OdyApplication.putValueByKey(Constants.SERVICE_TOGGLE, response.data.get(i).status);
                        }
                    }
                    //发送eventbus
                    //saveUserInfo(response.getData());
                }
            }
        });
    }

    @Override
    public void setGuangGaoData(AdBean bean) {
        Gson gson = new Gson();
        String json = gson.toJson(bean);
        OdyApplication.putValueByKey("AdParam", json);
    }


}
