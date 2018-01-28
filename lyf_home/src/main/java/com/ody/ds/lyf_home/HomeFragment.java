package com.ody.ds.lyf_home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.retrofit.home.PersonalBean;
import com.ody.p2p.retrofit.home.QIYuBean;
import com.ody.p2p.retrofit.home.ResultBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LoadAnimationDrawable;
import com.ody.p2p.utils.ScreenUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.FuncAdapter;
import com.ody.p2p.views.PagerSlidingTabStrip;
import com.ody.p2p.views.SuspendFrameLayout;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.ody.p2p.webactivity.WebActivity;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

/**
 * Created by lxs on 2016/12/2.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeView {

    private RecyclerView recycler_home;
    private TextView et_search;
    private RelativeLayout rl_search_content;
    private RelativeLayout rl_search;
    private View view_input_bg;
    private ImageView iv_sweep, iv_message;
    private TextView tv_address;
    private HomePresenter mPresenter;

    private AppHomePageBean mBean;
    private QuickHomeAdapter mAdapter;

    private OdySwipeRefreshLayout swipe_refresh;
    private ImageView floatTailImg;
    private FrameLayout floatAdFm;
    private ImageView closeImg;
    private TextView redFlag;

    protected LinearLayout ll_footer;
    protected LinearLayout ll_loading;
    protected ImageView iv_loading;
    protected TextView tv_nomore;
    protected boolean isLoading;
    protected boolean loadCom;
    protected int pageNo = 1;
    protected long categoryId = -1;
    protected long moduleId;

    protected RelativeLayout rl_home;

    protected String recommendMpIds = "";
    protected String rankMpIds = "";
    private RecyclerView float_recycler_category;
    public static final int REFRESH_RECYCLER = 1000;
    public LoadAnimationDrawable loadAnimationDrawable;

    private PagerSlidingTabStrip pagerslidingTab;
    private ViewPager slidViewpager;

    private List<AppHomePageBean.StaticData.Tabs> tabs = new ArrayList<>();

    private List<AppHomePageBean.Image> images = new ArrayList<>();

    HomeCategoryAdapter adapter;

    private SuspendFrameLayout suspendLayout;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_RECYCLER) {
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };


    @Override
    public void initPresenter() {
        mPresenter = new HomeImpl(this);
        mPresenter.getHomePage();
    }

    @Override
    public int bindLayout() {
        return R.layout.frag_home;
    }

    @Override
    public void initView(View view) {

        //float_recycler_category = (RecyclerView) view.findViewById(R.id.home_float_recycler_category);
        //floatAdFm = (FrameLayout) view.findViewById(R.id.float_ad);
        redFlag = (TextView) view.findViewById(R.id.redFlag);
        //closeImg = (ImageView) view.findViewById(R.id.close);
        //floatTailImg = (ImageView) view.findViewById(R.id.float_tail);
        //recycler_home = (RecyclerView) view.findViewById(R.id.re_home);
        et_search = (TextView) view.findViewById(R.id.et_search);
        rl_search_content = (RelativeLayout) view.findViewById(R.id.rl_search_content);
        rl_search = (RelativeLayout) view.findViewById(R.id.rl_search);
        view_input_bg = view.findViewById(R.id.view_input_bg);
        iv_sweep = (ImageView) view.findViewById(R.id.iv_sweep);
        iv_message = (ImageView) view.findViewById(R.id.iv_message);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        suspendLayout = (SuspendFrameLayout) view.findViewById(R.id.suspend_layout);
        //swipe_refresh = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        //rl_home = (RelativeLayout) view.findViewById(R.id.rl_home);

        //ll_footer = (LinearLayout) view.findViewById(R.id.ll_footer);
        //ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        //tv_nomore = (TextView) view.findViewById(R.id.tv_nomore);
        //iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

        rl_search_content.setOnClickListener(this);
        iv_message.setOnClickListener(this);
        iv_sweep.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        //swipe_refresh.setOdyDefaultView(true);
        //swipe_refresh.setCanLoadMore(false);
        /*swipe_refresh.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mPresenter.getHomePage();
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });*/
        /*recycler_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    GridLayoutManager lm = (GridLayoutManager) recyclerView.getLayoutManager();
                    if (lm.findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 10) {
                        if (loadCom) {
                            ll_footer.setVisibility(View.GONE);
                            recyclerLoading();
                        } else {
                            ll_footer.setVisibility(View.VISIBLE);
                            tv_nomore.setVisibility(View.GONE);
                            ll_loading.setVisibility(View.VISIBLE);
                            initLoading();
                            if (!isLoading) {
                                isLoading = true;
                                mAdapter.isLoading = isLoading;
                                pageNo++;
                               // mPresenter.getCategoryProduct(moduleId, categoryId, pageNo);
                            }
                        }
                    } else {
                        ll_footer.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    GridLayoutManager lm = (GridLayoutManager) recyclerView.getLayoutManager();
                    if (lm.findFirstVisibleItemPosition() < getGoodsIndex(mBean)) {
                        if (float_recycler_category.getVisibility() == View.VISIBLE) {
                            float_recycler_category.setVisibility(View.INVISIBLE);
                            if (mAdapter != null && float_recycler_category != null && float_recycler_category.getLayoutManager() != null) {
                                View topView = float_recycler_category.getLayoutManager().getChildAt(0);
                                if (topView == null) {
                                    return;
                                }
                                int leftOffset = topView.getLeft();
                                int leftPosition = float_recycler_category.getLayoutManager().getPosition(topView);
                                mAdapter.scrollFilter(leftPosition, leftOffset);
                            }
                        }
                    } else {
                        initFilterbar();
                    }
                }
            }
        });
        showTop(recycler_home);*/

        pagerslidingTab = (PagerSlidingTabStrip) view.findViewById(R.id.pagertab);
        slidViewpager = (ViewPager) view.findViewById(R.id.view_pager);
        slidViewpager.setOffscreenPageLimit(100);
        slidViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    suspendLayout.setVisibility(View.VISIBLE);
                } else {
                    suspendLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    suspendLayout.setVisibility(View.VISIBLE);
                } else {
                    suspendLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        EventBus.getDefault().register(this);
    }

    @Override
    public void doBusiness(Context mContext) {
       /* loadAnimationDrawable = new LoadAnimationDrawable();
        mAdapter = new QuickHomeAdapter(mPresenter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recycler_home.setLayoutManager(manager);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = mAdapter.getData().get(position).getItemType();
                if (type == AppHomePageBean.HomeData.TYPE_GOODS) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
        recycler_home.setAdapter(mAdapter);*/
        et_search.setText(OdyApplication.getValueByKey("searchword", ""));
        //mPresenter.getFloatTail();
    }

    public String getMobile() {
        Random rd = new Random();
        int i = rd.nextInt(100000);
        return "&mobile=" + i;
    }

    @Override
    public void onClick(View v) {
        //跳转到搜索\
        if (v.getId() == R.id.rl_search_content) {
            JumpUtils.ToActivity(JumpUtils.SEARCH);

            //JumpUtils.ToActivity(JumpUtils.QIANGGOU);


        }
        //跳转到扫码
        if (v.getId() == R.id.iv_sweep)

        {
           /* RxPermissions rxPermissions = new RxPermissions(getContext());
            rxPermissions.request(
                    //mTODO:meiyizhi
                    android.Manifest.permission.CAMERA//相机
            )
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                JumpUtils.ToActivity(JumpUtils.SWEEP);
                            } else {
                                ToastUtils.showShort("为了更好的使用体验，请开启相机使用权限!");
                            }
                        }
                    });*/

            String url = "http://180110fg0025.umaman.com/hongbao/game.html?nickname=&avatar=&ut=abc&activity_id=5a607604d3df9001a5343685";

            url = url + getMobile();
            JumpUtils.ToWebActivity(url, WebActivity.CONTENT_TITLE, -1, "");
           /* Intent intent = new Intent(getContext(), NewWebActivity.class);
            getActivity().startActivity(intent);*/
        }
        //跳转到消息中心
        if (v.getId() == R.id.iv_message)

        {
            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                JumpUtils.ToWebActivity(OdyApplication.H5URL + "/message/message-center.html", WebActivity.NO_TITLE, -1, "");
            } else {
                JumpUtils.ToActivity(JumpUtils.LOGIN);

            }

            //getContext().startActivity(new Intent(getContext(), LoginActivity.class));


        }
        //跳转到地址列表
        if (v.getId() == R.id.tv_address)

        {
            JumpUtils.ToActivity(JumpUtils.LOCATION);
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            /*if (!NetworkUtil.isNetworkAvailable(getContext())) {
                showFailed(true, 1, 55);
            }*/
            tv_address.setText(OdyApplication.getString(Constants.PROVINCE, ""));
            //mPresenter.getMsgSummary();
        } else {
            //showFailed(false, 1);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        //mPresenter.getMsgSummary();
        tv_address.setText(OdyApplication.getString(Constants.PROVINCE, ""));
        super.onResume();
    }


    public void initPager(final AppHomePageBean bean) {
        /*if (bean != null && bean.pageInfo != null) {
            if (!StringUtils.isEmpty(bean.pageInfo.bgImg)) {
                Glide.with(getContext()).load(bean.pageInfo.bgImg).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                        recycler_home.setBackgroundDrawable(new BitmapDrawable(arg0));
                    }
                });
            } else if (!StringUtils.isEmpty(bean.pageInfo.bgColor)) {
                recycler_home.setBackgroundColor(Color.parseColor(bean.pageInfo.bgColor));
            }
        }
        if (bean != null && bean.dataList != null && bean.dataList.size() > 0) {
            showFailed(false, 1);
            mBean = bean;
            for (AppHomePageBean.HomeData data : bean.dataList) {
                if (data.templateCode != null) {
                    if (data.staticData != null && data.staticData.ad_code != null) {
                        if (data.staticData.ad_code.contains(",")) {
                            String arr[] = data.staticData.ad_code.split(",");
                            for (int i = 0; i < arr.length; i++) {
                               // mPresenter.getAdData(arr[i]);
                            }
                        } else {
                           // mPresenter.getAdData(data.staticData.ad_code);
                        }
                    }
                }
                if (data.templateCode != null && data.templateCode.equals("news")) {
                   // mPresenter.getHeadlines();
                }
                if (data.templateCode != null && data.templateCode.equals("rank")) {
                  //  mPresenter.getRank(data.moduleId);
                }
                if (data.templateCode != null && data.templateCode.equals("goods")) {
                   // mPresenter.getCategory(data.moduleId);
                }
                if (data.templateCode != null && data.templateCode.equals("index_cube")) {
                    if (data.staticData != null && data.staticData.cdata != null && data.staticData.cdata.children != null) {
                        List<AppHomePageBean.Children> childList = getChildList(Double.parseDouble(data.staticData.cdata.width.replace("%", "")), data.staticData.cdata.children, null);
                        data.staticData.cdata.children = childList;
                    }
                }
            }
            mAdapter.setNewData(mBean.dataList);
        } else {
            showFailed(true, 1, 55);
        }*/



        /*AppHomePageBean.HomeData data1 = new AppHomePageBean.HomeData();
        data1.templateCode = "suspension_box";
        AppHomePageBean.StaticData data2 = new AppHomePageBean.StaticData();
        AppHomePageBean.StaticData.StyleObject data3 = new AppHomePageBean.StaticData.StyleObject();
        data3.height = "180";
        data3.left = "300";
        data3.top = "500";
        data3.width = "180";
        data2.styleObject = data3;


        AppHomePageBean.Image image = new AppHomePageBean.Image();
        image.desc = "悬浮简介";
        image.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504682433480_6342_32.png@base@tag=imgScale&q=80";
        images.add(image);
        data2.images = images;

        data1.staticData = data2;
        bean.dataList.add(3, data1);*/

        try {

            for (int i = 0; i < bean.dataList.size(); i++) {
                if (bean.dataList.get(i).getStaticData().tabs != null) {
                    tabs = bean.dataList.get(i).getStaticData().tabs;
                    break;
                }
            }

            //兼容版本
            if (tabs.size() == 0) {
                AppHomePageBean.StaticData.Tabs tabs1 = new AppHomePageBean.StaticData.Tabs();
                tabs1.title = "";
                tabs.add(tabs1);
                pagerslidingTab.setVisibility(View.GONE);
            }


            adapter = new HomeCategoryAdapter(tabs, getActivity().getSupportFragmentManager());

            slidViewpager.setAdapter(adapter);

            if (tabs.size() > 1) {

                pagerslidingTab.setVisibility(View.VISIBLE);

                pagerslidingTab.setViewPager(slidViewpager);

                pagerslidingTab.setTabsTitleStyle();
            }


            for (int i = 0; i < bean.dataList.size(); i++) {

                if (bean.dataList.get(i).getTemplateCode().equals("suspension_box")) {

                    final AppHomePageBean.StaticData staticData = bean.dataList.get(i).staticData;
                    suspendLayout.setVisibility(View.VISIBLE);
                    AppHomePageBean.StaticData.StyleObject data = bean.dataList.get(i).staticData.styleObject;

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) suspendLayout.getLayoutParams();
                    params.leftMargin = Integer.parseInt((ScreenUtils.getScreenWidth(getContext()) * Integer.parseInt(data.left) / 100) + "");
                    params.topMargin = Integer.parseInt((ScreenUtils.getScreenHeight(getContext()) * Integer.parseInt(data.top)) / 100 + "");
                    suspendLayout.setLayoutParams(params);

                    if (!TextUtils.isEmpty(data.height) && !TextUtils.isEmpty(data.width)) {
                        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) suspendLayout.imageView.getLayoutParams();
                        params1.height = Integer.parseInt(data.height);
                        params1.width = Integer.parseInt(data.width);
                        suspendLayout.imageView.setLayoutParams(params1);
                    }


                    //解决图片加载缓慢的情况
                    if (staticData.images.get(0).src.endsWith("gif")) {
                        Glide.with(getContext())
                                .load(staticData.images.get(0).src).asGif()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(suspendLayout.imageView);
                    } else {
                        GlideUtil.display(getContext(), staticData.images.get(0).src).into((suspendLayout.imageView));
                    }


                    suspendLayout.setOnSuspendClickListener(new SuspendFrameLayout.OnSuspendClickListener() {


                        @Override
                        public void performClick() {

                            JumpUtils.ToWebActivity(getContext(), staticData.images.get(0).link.appData);

                        }
                    });

                    break;
                }
            }
        } catch (Exception e) {

        }


    }


    public void initAdData(String adCode, String jsonData) {
        List<Ad> list = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray arr;
            arr = obj.getJSONArray(adCode);
            String json = arr.toString();
            list = gson.fromJson(json, new TypeToken<List<Ad>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            if (adCode.equals("searchword")) {
                et_search.setText(list.get(0).content);
                OdyApplication.putValueByKey("searchword", list.get(0).content);
            }
        }
        if (mBean != null && mBean.dataList != null && mBean.dataList.size() > 0) {
            for (AppHomePageBean.HomeData data : mBean.dataList) {
                if (data.templateCode != null) {
                    if (data.staticData != null && data.staticData.ad_code != null && data.templateCode.equals("ad_header") && data.staticData.ad_code.contains(adCode)) {
                        if (adCode.equals("ad_banner")) {
                            if (list != null && list.size() > 0) {
                                setBannerData(list);
                            }
                        }
                    } else if (data.staticData != null && data.staticData.ad_code != null && data.templateCode.equals("ad_channel")) {
                        if (adCode.equals("ad_channel")) {
                            if (list != null && list.size() > 0) {
                                setChannelData(list);
                            }
                        }
                    } else if (data.staticData != null && adCode.equals(data.staticData.ad_code) && !data.templateCode.equals("ad_channel")) {
                        data.staticData.adList = list;
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    public void initHeadlinesData(HeadLinesBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals("news")) {
                data.staticData.headLinesBean = bean;
                mAdapter.setNewData(mBean.dataList);
            }
        }
    }


    public void initRankData(ModuleDataBean moduleDataBean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals("rank")) {
                ModuleDataBean.CmsModuleDataVO productBean = new ModuleDataBean.CmsModuleDataVO();
                productBean.jumpType = ResultBean.JUMP_DETAIL;
                if (moduleDataBean.listObj == null || moduleDataBean.listObj.size() == 0) {
                    mBean.dataList.remove(data);
                    return;
                }
                if (moduleDataBean.listObj.size() < 11) {
                    moduleDataBean.listObj.add(productBean);
                }
                data.staticData.resultBean = moduleDataBean;
                break;
            }
        }
        if (moduleDataBean.listObj != null && moduleDataBean.listObj.size() > 0) {
            for (int i = 0; i < moduleDataBean.listObj.size() - 1; i++) {
                if (StringUtils.isEmpty(rankMpIds)) {
                    rankMpIds += moduleDataBean.listObj.get(i).mpId;
                } else {
                    rankMpIds += ",";
                    rankMpIds += moduleDataBean.listObj.get(i).mpId;
                }
            }
            //mPresenter.getStockPriceRank(rankMpIds);
        }
        mAdapter.setNewData(mBean.dataList);
    }


    public void initCategoryProduct(long moduleId, ModuleDataBean bean, long categoryId) {
        if (bean != null && bean.listObj != null && bean.listObj.size() > 0) {
            if (bean.listObj != null && bean.listObj.size() > 0) {
                for (int i = 0; i < bean.listObj.size(); i++) {
                    if (StringUtils.isEmpty(recommendMpIds)) {
                        recommendMpIds += bean.listObj.get(i).mpId;
                    } else {
                        recommendMpIds += ",";
                        recommendMpIds += bean.listObj.get(i).mpId;
                    }
                }
                // mPresenter.getStockPriceRecommend(recommendMpIds);
            }
            if (categoryId != this.categoryId) {
                cleanProduct(mBean);
                this.categoryId = categoryId;
                loadCom = false;
                pageNo = 1;
            }
            addProductList(bean.listObj);
            mAdapter.notifyDataSetChanged();
            if (pageNo >= bean.totalPage) {
                loadCom = true;
                if (mAdapter.getFooterLayoutCount() == 0) {
                    mAdapter.addFooterView(getView(R.layout.listview_footer));
                }
            } else {
                loadCom = false;
                if (mAdapter.getFooterLayoutCount() > 0) {
                    mAdapter.removeAllFooterView();
                }
            }
        } else {
            loadCom = true;
            if (mAdapter.getFooterLayoutCount() == 0) {
                mAdapter.addFooterView(getView(R.layout.listview_footer));
            }
        }
        ll_footer.setVisibility(View.GONE);
        recyclerLoading();
        isLoading = false;
        mAdapter.isLoading = isLoading;
    }


    public void onRefreshComplete() {
        swipe_refresh.setRefreshing(false);
    }


    public void initFloatTail(final Ad ad) {
        floatAdFm.setVisibility(View.VISIBLE);
        GlideUtil.display(getContext(), ad.imageUrl).into(floatTailImg);
        floatTailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.toActivity(ad);
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatAdFm.setVisibility(View.GONE);
            }
        });
    }


    public void setUnReadCount(int count) {
        UiUtils.setCareNum(count, 1, redFlag);
    }


    public void setRankPrice(StockPriceBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals("rank")) {
                for (int i = 0; i < data.staticData.resultBean.listObj.size(); i++) {
                    for (int j = 0; j < bean.data.plist.size(); j++) {
                        if (bean.data.plist.get(j).mpId.equals(data.staticData.resultBean.listObj.get(i).mpId)) {
                            data.staticData.resultBean.listObj.get(i).price = bean.data.plist.get(j).price;
                            data.staticData.resultBean.listObj.get(i).promotionPrice = bean.data.plist.get(j).promotionPrice;
                        }
                    }
                }
            }
        }
        mAdapter.setNewData(mBean.dataList);
    }


    public void setRecommendPrice(StockPriceBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals("goods")) {
                for (int i = 0; i < data.moduleData.moduleDataList.size(); i++) {
                    for (int j = 0; j < bean.data.plist.size(); j++) {
                        if (bean.data.plist.get(j).mpId.equals(data.moduleData.moduleDataList.get(i).mpId)) {
                            data.moduleData.moduleDataList.get(i).price = bean.data.plist.get(j).price;
                            data.moduleData.moduleDataList.get(i).promotionPrice = bean.data.plist.get(j).promotionPrice;
                        }
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 商品筛选栏的数据适配
     *
     * @param moduleId
     * @param categoryList
     */

    public void initCategory(long moduleId,
                             final List<ModuleDataCategoryBean.CategoryBean> categoryList) {
        //mTODO:meiyizhi 头部商品筛选栏悬浮
        this.moduleId = moduleId;

        if (categoryList != null && categoryList.size() > 0) {
            categoryList.get(0).choose = true;
            isLoading = true;
            mAdapter.isLoading = isLoading;
            //mPresenter.getCategoryProduct(moduleId, categoryList.get(0).categoryId, pageNo);
            int index = 0;
            for (AppHomePageBean.HomeData data : mBean.dataList) {
                if (data.templateCode != null && data.templateCode.equals("goods")) {
                    if (data.moduleData != null) {
                        data.moduleData.categoryList = categoryList;
                    } else {
                        data.moduleData = new AppHomePageBean.ModuleData();
                        data.moduleData.categoryList = categoryList;
                    }
                }
            }
        } else {
            isLoading = true;
            mAdapter.isLoading = isLoading;
            // mPresenter.getCategoryProduct(moduleId, -1, pageNo);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        handler.removeMessages(REFRESH_RECYCLER);
    }

    @Subscribe
    public void onEventMainThread(final EventMessage event) {
        if (event.flag == EventMessage.CHANGE_AREA) {
            tv_address.setText(OdyApplication.getString(Constants.PROVINCE, ""));
            //mPresenter.getHomePage();
        }

    }


    public void setBannerData(final List<Ad> adList) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals("ad_header")) {
                data.staticData.adList = adList;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setChannelData(final List<Ad> adList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FuncAdapter> listRe = null;
                FuncAdapter adapter = null;
                int page = 0;
                List<Ad> funcList;
                int pageSize = 10;
                if (adList != null && adList.size() > 0) {
                    if (adList.size() % pageSize == 0) {
                        listRe = new ArrayList<>();
                        page = adList.size() / pageSize;
                        for (int i = 0; i < page; i++) {
                            funcList = new ArrayList<>();
                            for (int j = 0; j < pageSize; j++) {
                                funcList.add(adList.get(pageSize * i + j));
                            }
                            adapter = new FuncAdapter(getContext(), funcList);
                            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, Object model) {
                                    Ad bean = (Ad) model;
                                    if (bean.linkUrl.contains("category")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constants.GO_MAIN, 1);
                                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                                    } else {
                                        if (bean.linkUrl != null && bean.linkUrl.contains("zhibo")) {
                                            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                                try {
                                                    JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
                                                } catch (UnsupportedEncodingException e) {
                                                    JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                JumpUtils.ToActivity(JumpUtils.LOGIN);
                                            }
                                        } else if (bean.linkUrl != null && bean.linkUrl.contains("my-order.html") && !OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                                        } /*else if (bean.linkUrl != null && bean.linkUrl.contains("seckill.html")) {
                                            JumpUtils.ToActivity(JumpUtils.QIANGGOU);

                                        } */ else {
                                            JumpUtils.toActivity(bean);
                                        }
                                    }
                                }

                                @Override
                                public void onItemLongClick(View view, int position, Object model) {

                                }
                            });
                            listRe.add(adapter);
                        }
                    } else {
                        listRe = new ArrayList<>();
                        page = adList.size() / pageSize;
                        for (int i = 0; i < page; i++) {
                            funcList = new ArrayList<>();
                            for (int j = 0; j < pageSize; j++) {
                                funcList.add(adList.get(pageSize * i + j));
                            }
                            adapter = new FuncAdapter(getContext(), funcList);
                            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, Object model) {
                                    Ad bean = (Ad) model;
                                    if (bean.linkUrl.contains("category")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constants.GO_MAIN, 1);
                                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                                    } else {
                                        if (bean.linkUrl != null && bean.linkUrl.contains("zhibo")) {
                                            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                                try {
                                                    JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8"));
                                                } catch (UnsupportedEncodingException e) {
                                                    JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, ""));
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                JumpUtils.ToActivity(JumpUtils.LOGIN);
                                            }
                                        } else {
                                            JumpUtils.toActivity(bean);
                                        }
                                    }
                                }

                                @Override
                                public void onItemLongClick(View view, int position, Object model) {

                                }
                            });
                            listRe.add(adapter);
                        }
                        funcList = new ArrayList<>();
                        for (int k = page * pageSize; k < adList.size(); k++) {
                            funcList.add(adList.get(k));
                        }
                        adapter = new FuncAdapter(getContext(), funcList);
                        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, Object model) {
                                Ad bean = (Ad) model;
                                if (bean.linkUrl.contains("category")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.GO_MAIN, 1);
                                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                                } else {
                                    if (bean.linkUrl != null && bean.linkUrl.contains("zhibo")) {
                                        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                            try {
                                                JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8"));
                                            } catch (UnsupportedEncodingException e) {
                                                JumpUtils.ToWebActivity(getContext(), bean.linkUrl + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, ""));
                                                e.printStackTrace();
                                            }
                                        } else {
                                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                                        }
                                    } else {
                                        JumpUtils.toActivity(bean);
                                    }
                                }
                            }

                            @Override
                            public void onItemLongClick(View view, int position, Object model) {

                            }
                        });
                        listRe.add(adapter);
                    }
                }
                if (listRe != null && listRe.size() > 0) {
                    for (int i = 0; i < mBean.dataList.size(); i++) {
                        if (mBean.dataList.get(i).templateCode.equals("ad_channel")) {
                            if (mBean.dataList.get(i).staticData != null) {
                                mAdapter.funcAdapters = listRe;
                                mBean.dataList.get(i).staticData.adList = null;
                            }
                        }
                    }
                    handler.sendEmptyMessage(REFRESH_RECYCLER);
                }
            }
        }).start();
    }

    @Override
    public void listener(int x, int y) {
        super.listener(x, y);
//        if (y > 400) {
//            float alpha = (y - 400) / 400.0f;
//            view_input_bg.setAlpha(alpha > 0.8f ? 0.8f : alpha);
//            view_input_bg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme_color));
//        } else {
//            view_input_bg.setAlpha(1);
//            view_input_bg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_search_bg));
//        }
    }

    /**
     * 筛选栏状态
     */
    private void initFilterbar() {
        if (null != mAdapter && mAdapter.recycler_category != null) {
            int[] position = new int[2];
            float_recycler_category.getLocationInWindow(position);
            int[] titlePosition = new int[2];
            mAdapter.recycler_category.getLocationInWindow(titlePosition);
            mAdapter.recycler_category.getHeight();
            if (position[1] < titlePosition[1]) {
                if (float_recycler_category.getVisibility() == View.VISIBLE) {
                    float_recycler_category.setVisibility(View.INVISIBLE);
                    if (mAdapter != null && float_recycler_category.getLayoutManager() != null) {
                        View topView = float_recycler_category.getLayoutManager().getChildAt(0);
                        if (topView == null) {
                            return;
                        }
                        int leftOffset = topView.getLeft();
                        int leftPosition = float_recycler_category.getLayoutManager().getPosition(topView);
                        mAdapter.scrollFilter(leftPosition, leftOffset);
                    }
                }
            } else {
                if (NetworkUtil.isNetworkAvailable(getContext())) {
                    if (float_recycler_category.getVisibility() == View.INVISIBLE) {
                        float_recycler_category.setVisibility(View.VISIBLE);
                        if (mAdapter.recycler_category != null) {
                            View topView = mAdapter.recycler_category.getLayoutManager().getChildAt(0);
                            if (topView == null) {
                                return;
                            }
                            int leftOffset = topView.getLeft();
                            int leftPosition = mAdapter.recycler_category.getLayoutManager().getPosition(topView);
                            ((LinearLayoutManager) float_recycler_category.getLayoutManager()).scrollToPositionWithOffset(leftPosition, leftOffset);
                        }
                    }
                }
            }
        }
    }

    private View getView(int viewId) {
        return LayoutInflater.from(getContext()).inflate(viewId, new RelativeLayout(getContext()));
    }

    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1, 55);
        }
        mPresenter.getHomePage();
        showFailed(false, 1);

    }


    public void startAnimation(ImageView iv_loading) {
        iv_loading.clearAnimation();
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getBackground();
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }


    public int getGoodsIndex(AppHomePageBean bean) {
        int index = -1;
        for (int i = 0; i < bean.dataList.size(); i++) {
            if (bean.dataList.get(i).templateCode.equals("goods")) {
                index = i;
            }
        }
        return index;
    }

    public int getProductIndex(AppHomePageBean bean) {
        int index = -1;
        for (int i = 0; i < bean.dataList.size(); i++) {
            if (bean.dataList.get(i).templateCode.equals("product")) {
                index = i;
            }
        }
        if (index == -1) {
            index = getGoodsIndex(bean);
        }
        return index;
    }


    public void cleanProduct(AppHomePageBean bean) {
        int size = bean.dataList.size();
        for (int i = 0; i < bean.dataList.size(); i++) {
            if (bean.dataList.get(i).templateCode.equals("product")) {
                bean.dataList.remove(i);
                i--;
            }
        }
    }

    public void addProductList(List<ModuleDataBean.CmsModuleDataVO> listObj) {
        for (int i = 0; i < listObj.size(); i++) {
            AppHomePageBean.HomeData data = new AppHomePageBean.HomeData();
            data.templateCode = "product";
            AppHomePageBean.ModuleData moduleData = new AppHomePageBean.ModuleData();
            moduleData.moduleDataList = new ArrayList<>();
            moduleData.moduleDataList.add(listObj.get(i));
            data.moduleData = moduleData;
            mBean.dataList.add(getProductIndex(mBean) + 1, data);
        }
    }

    public void initLoading() {
        loadAnimationDrawable.animateRawManuallyFromXML(R.drawable.footer_loading, iv_loading, new Runnable() {
            @Override
            public void run() {
            }
        }, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    public void recyclerLoading() {
        loadAnimationDrawable.recycler();
    }


    public List<AppHomePageBean.Children> getChildList(double width, List<AppHomePageBean.Children> children, List<AppHomePageBean.Children> newList) {
        if (newList == null) {
            newList = new ArrayList<>();
        }
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                AppHomePageBean.Children child = children.get(i);
                if (children.get(i).children != null && children.get(i).children.size() > 0) {
                    newList = getChildList(Double.parseDouble(child.width.replace("%", "")), child.children, newList);
                } else {
                    child.width = width * Double.parseDouble(child.width.replace("%", "")) / 100 + "%";
                    newList.add(children.get(i));
                }
            }
        }
        return newList;
    }

}
