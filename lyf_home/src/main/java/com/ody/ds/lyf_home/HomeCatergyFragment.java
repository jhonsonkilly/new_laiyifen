package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.ody.p2p.retrofit.home.QiangGouBean;
import com.ody.p2p.retrofit.home.ResultBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LoadAnimationDrawable;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2017/9/5.
 */

public class HomeCatergyFragment extends BaseFragment implements HomeCatergyView {

    private FrameLayout floatAdFm;

    private ImageView floatTailImg;

    private ImageView closeImg;

    private RecyclerView float_recycler_category;

    private RecyclerView recycler_home;

    private OdySwipeRefreshLayout swipe_refresh;

    protected ImageView iv_loading;


    protected TextView tv_nomore;

    protected LinearLayout ll_footer;
    protected LinearLayout ll_loading;

    private HomeCatergyPresenter mPresenter;

    private QuickHomeAdapter mAdapter;
    private AppHomePageBean mBean;
    protected int pageNo = 1;

    public static final int REFRESH_RECYCLER = 1000;

    protected String rankMpIds = "";

    protected long moduleId;

    protected boolean isLoading;

    protected String recommendMpIds = "";

    protected long categoryId = -1;

    protected boolean loadCom;

    public LoadAnimationDrawable loadAnimationDrawable;

    private TextView redFlag;

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
    public int bindLayout() {
        return R.layout.frag_catergy_home;
    }

    @Override
    public void initView(View view) {

        floatAdFm = (FrameLayout) view.findViewById(R.id.float_ad);
        floatTailImg = (ImageView) view.findViewById(R.id.float_tail);

        closeImg = (ImageView) view.findViewById(R.id.close);

        float_recycler_category = (RecyclerView) view.findViewById(R.id.home_float_recycler_category);

        recycler_home = (RecyclerView) view.findViewById(R.id.re_home);

        swipe_refresh = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);


        ll_footer = (LinearLayout) view.findViewById(R.id.ll_footer);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        tv_nomore = (TextView) view.findViewById(R.id.tv_nomore);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

        swipe_refresh.setOdyDefaultView(true);
        swipe_refresh.setCanLoadMore(false);
        swipe_refresh.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mPresenter.getHomePage(true);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });

        recycler_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getContext()).resumeRequests();
                    Log.d(TAG, "onScrollStateChanged:          resumeRequests");
                } else /*if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING)*/ {
                    Glide.with(getContext()).pauseRequests();
                    Log.d(TAG, "onScrollStateChanged:          pauseRequests");
                }
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
                                mPresenter.getCategoryProduct(moduleId, categoryId, pageNo);
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
                    if (lm.findFirstVisibleItemPosition() < mAdapter.getTypeIndex(AppHomePageBean.GOODS)) {
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
        showTop(recycler_home);
        EventBus.getDefault().register(this);
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

    @Override
    public void doBusiness(Context mContext) {
        loadAnimationDrawable = new LoadAnimationDrawable();
        mAdapter = new QuickHomeAdapter(mPresenter);
        mAdapter.setOnRetryListener(new QuickHomeAdapter.OnRetryListener() {
            @Override
            public void retryCountTime() {
                //局部刷新
                mPresenter.getHomePage(false);
            }
        });
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
        recycler_home.setAdapter(mAdapter);
        // et_search.setText(OdyApplication.getValueByKey("searchword", ""));
        mPresenter.getFloatTail();
    }

    @Override
    public void initPresenter() {
        mPresenter = new HomeCatergyImpl(this);
        mPresenter.getHomePage(true);
    }

    @Override
    public void initPager(AppHomePageBean bean, boolean isFreshAll) {
        if (!isFreshAll) {
            if (bean != null && bean.dataList != null && bean.dataList.size() > 0) {
                for (AppHomePageBean.HomeData data : bean.dataList) {
                    if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.INDEX_CUBE)) {
                        if (data.staticData != null && data.staticData.cdata != null && data.staticData.cdata.children != null) {
                            List<AppHomePageBean.Children> childList = getChildList(Double.parseDouble(data.staticData.cdata.width.replace("%", "")), data.staticData.cdata.children, null);
                            data.staticData.cdata.children = childList;
                            for (AppHomePageBean.Children children : data.staticData.cdata.children) {
                                if (children.purchase == 1) {
                                    mPresenter.getGiangGouTime(children);
                                }
                            }

                        }
                    }
                }
                return;
            }
        }
        if (bean != null && bean.pageInfo != null) {
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
                                mPresenter.getAdData(arr[i]);
                            }
                        } else {
                            mPresenter.getAdData(data.staticData.ad_code);
                        }
                    }
                }
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.NEWS)) {
                    mPresenter.getHeadlines();
                }
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.RANK)) {
                    mPresenter.getRank(data.moduleId);
                }
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.GOODS)) {
                    mPresenter.getCategory(data.moduleId);
                    //mPresenter.getSpecCategoryProduct(data.moduleId, -1, 1, 9);
                }
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.INDEX_CUBE)) {
                    if (data.staticData != null && data.staticData.cdata != null && data.staticData.cdata.children != null) {
                        List<AppHomePageBean.Children> childList = getChildList(Double.parseDouble(data.staticData.cdata.width.replace("%", "")), data.staticData.cdata.children, null);
                        data.staticData.cdata.children = childList;
                        for (AppHomePageBean.Children children : data.staticData.cdata.children) {
                            if (children.purchase == 1) {
                                mPresenter.getGiangGouTime(children);
                            }
                        }

                    }
                }
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.GOODS_R1C3)) {
                    //固定数据
                    mPresenter.getSpecCategoryProduct(data.moduleId, -1, 1, 3);
                }
            }
            mAdapter.setNewData(mBean.dataList);
            setSuspendImage(mBean.dataList);

        } else {
            showFailed(true, 1, 55);
        }
    }

    public void setSuspendImage(List<AppHomePageBean.HomeData> data) {

    }

    @Override
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
                //et_search.setText(list.get(0).content);
                OdyApplication.putValueByKey("searchword", list.get(0).content);
            }
        }
        if (mBean != null && mBean.dataList != null && mBean.dataList.size() > 0) {
            for (AppHomePageBean.HomeData data : mBean.dataList) {
                if (data.templateCode != null) {
                    if (data.staticData != null && data.staticData.ad_code
                            != null && data.templateCode.equals(AppHomePageBean.AD_HEADER)
                            && data.staticData.ad_code.contains(adCode)) {
                        if (adCode.equals(AppHomePageBean.AD_BANNER)) {
                            if (list != null && list.size() > 0) {
                                setBannerData(list);
                            }
                        }
                    } else if (data.staticData != null && data.staticData.ad_code != null
                            && data.templateCode.equals(AppHomePageBean.AD_CHANNEL)) {
                        if (adCode.equals(AppHomePageBean.AD_CHANNEL)) {
                            if (list != null && list.size() > 0) {
                                setChannelData(list);
                            }
                        }
                    } else if (data.staticData != null && adCode.equals(data.staticData.ad_code) && !data.templateCode.equals(AppHomePageBean.AD_CHANNEL)) {
                        data.staticData.adList = list;
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initHeadlinesData(HeadLinesBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.NEWS)) {
                data.staticData.headLinesBean = bean;
                mAdapter.setNewData(mBean.dataList);
            }
        }
    }

    @Override
    public void initRankData(ModuleDataBean moduleDataBean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.RANK)) {
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
            mPresenter.getStockPriceRank(rankMpIds);
        }
        mAdapter.setNewData(mBean.dataList);
    }

    @Override
    public void initCategory(long moduleId, List<ModuleDataCategoryBean.CategoryBean> categoryList) {
        this.moduleId = moduleId;

        if (categoryList != null && categoryList.size() > 0) {
            categoryList.get(0).choose = true;
            isLoading = true;
            mAdapter.isLoading = isLoading;
            mPresenter.getCategoryProduct(moduleId, categoryList.get(0).categoryId, pageNo);
            int index = 0;
            for (AppHomePageBean.HomeData data : mBean.dataList) {
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.GOODS)) {
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
            mPresenter.getCategoryProduct(moduleId, -1, pageNo);
        }
    }

    @Override
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
                mPresenter.getStockPriceRecommend(recommendMpIds);
            }
            if (categoryId != this.categoryId) {
                mAdapter.cleanProduct();
                this.categoryId = categoryId;
                loadCom = false;
                pageNo = 1;
            }
            addProductList(bean.listObj);
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

    @Override
    public void onRefreshComplete() {
        swipe_refresh.setRefreshing(false);
    }

    @Override
    public void initFloatTail(Ad ad) {

    }

    @Override
    public void setUnReadCount(int count) {
        // UiUtils.setCareNum(count, 1, redFlag);
    }

    @Override
    public void setRankPrice(StockPriceBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.RANK)) {
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

    @Override
    public void setRecommendPrice(StockPriceBean bean) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.GOODS)) {
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

    @Override
    public void initSpecCategoryProduct(long moduleId, ModuleDataBean bean, long categoryId) {
        addProductList2(bean.listObj);
    }


    private View getView(int viewId) {
        return LayoutInflater.from(getContext()).inflate(viewId, new RelativeLayout(getContext()));
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

    public void setBannerData(final List<Ad> adList) {
        for (AppHomePageBean.HomeData data : mBean.dataList) {
            if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.AD_HEADER)) {
                data.staticData.adList = adList;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setChannelData(final List<Ad> adList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<com.ody.p2p.views.FuncAdapter> listRe = null;
                com.ody.p2p.views.FuncAdapter adapter = null;
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
                            adapter = new com.ody.p2p.views.FuncAdapter(getContext(), funcList);
                            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, Object model) {
                                    Ad bean = (Ad) model;
                                    if (bean.linkUrl.contains("category")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constants.GO_MAIN, 1);
                                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                                    } else {
                                        /*if (bean.linkUrl != null && bean.linkUrl.contains("zhibo")) {
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
                                        } else */
                                        if (bean.linkUrl != null && bean.linkUrl.contains("my-order.html") && !OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
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
                            adapter = new com.ody.p2p.views.FuncAdapter(getContext(), funcList);
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
                        adapter = new com.ody.p2p.views.FuncAdapter(getContext(), funcList);
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
                        if (mBean.dataList.get(i).templateCode.equals(AppHomePageBean.AD_CHANNEL)) {
                            if (mBean.dataList.get(i).staticData != null) {
                                mAdapter.funcAdapters = listRe;
                                mBean.dataList.get(i).staticData.adList = null;
                                mBean.dataList.get(i).staticData.adList = null;
                            }
                        }
                    }
                    handler.sendEmptyMessage(REFRESH_RECYCLER);
                }
            }
        }).start();
    }

    public void addProductList(List<ModuleDataBean.CmsModuleDataVO> listObj) {
        for (int i = 0; i < listObj.size(); i++) {
            AppHomePageBean.HomeData data = new AppHomePageBean.HomeData();
            data.templateCode = AppHomePageBean.PRODUCT;
            AppHomePageBean.ModuleData moduleData = new AppHomePageBean.ModuleData();
            moduleData.moduleDataList = new ArrayList<>();
            moduleData.moduleDataList.add(listObj.get(i));
            data.moduleData = moduleData;

            int index = mAdapter.getTypeIndex(AppHomePageBean.PRODUCT);
            if (index == -1) {
                index = mAdapter.getTypeIndex(AppHomePageBean.GOODS);
            }
            mBean.dataList.add(index + 1, data);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void addProductList2(List<ModuleDataBean.CmsModuleDataVO> listObj) {
        String recommendMpIds="";
        try {
            AppHomePageBean.ModuleData moduleData = new AppHomePageBean.ModuleData();
            moduleData.spModuleDataList = new ArrayList<>();
            for (int i = 0; i < listObj.size(); i++) {
                moduleData.spModuleDataList.add(listObj.get(i));
            }
            int index = mAdapter.getTypeIndex(AppHomePageBean.GOODS_R1C3);
            mAdapter.getData().get(index).moduleData = moduleData;
            mAdapter.notifyItemChanged(index);


            if (listObj != null && listObj.size() > 0) {
                for (int i = 0; i < listObj.size(); i++) {
                    if (StringUtils.isEmpty(recommendMpIds)) {
                        recommendMpIds += listObj.get(i).mpId;
                    } else {
                        recommendMpIds += ",";
                        recommendMpIds += listObj.get(i).mpId;
                    }
                }
                mPresenter.getPriceCmsModuleDataVO(recommendMpIds);
            }



        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!NetworkUtil.isNetworkAvailable(getContext())) {
                showFailed(true, 1, 55);
            }

            mPresenter.getMsgSummary();
        } else {
            showFailed(false, 1);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        mPresenter.getMsgSummary();

        super.onResume();
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
            //tv_address.setText(OdyApplication.getString(Constants.PROVINCE, ""));
            mPresenter.getHomePage(true);
        }
        if (event.flag == EventMessage.FRUSH_FILTER) {
            try {

                if (event.homeData.moduleData.categoryList.size() > 1) {
                    float_recycler_category.setBackgroundColor(getResources().getColor(R.color.white));
                    LinearLayoutManager categoryManger = new LinearLayoutManager(getContext());
                    categoryManger.setOrientation(LinearLayoutManager.HORIZONTAL);
                    float_recycler_category.setLayoutManager(categoryManger);
                    final CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());

                    categoryAdapter.setDatas(event.homeData.moduleData.categoryList);
                    categoryAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Object model) {
                            if (!event.homeData.moduleData.categoryList.get(position).choose && !isLoading) {
                                mAdapter.refreshCateList(event.homeData.moduleData.categoryList, position);
                                View topView = float_recycler_category.getLayoutManager().getChildAt(0);
                                event.homeData.moduleData.leftPosition = float_recycler_category.getLayoutManager().getPosition(topView);
                                event.homeData.moduleData.leftOffset = topView.getLeft();
                                isLoading = true;
                                mAdapter.isLoading = isLoading;
                                mPresenter.getCategoryProduct(event.homeData.moduleId, event.homeData.moduleData.categoryList.get(position).categoryId, 1);
                                mAdapter.refreshCategoryAdapter(position);
                                categoryAdapter.notifyDataSetChanged();
                                recycler_home.scrollToPosition(mAdapter.getTypeIndex(AppHomePageBean.GOODS));
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position, Object model) {

                        }
                    });

                    float_recycler_category.setAdapter(categoryAdapter);
                    ((LinearLayoutManager) float_recycler_category.getLayoutManager()).scrollToPositionWithOffset(event.homeData.moduleData.leftPosition, event.homeData.moduleData.leftOffset);
                } else {
                    float_recycler_category.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }

        }
    }

    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1, 55);
        }
        mPresenter.getHomePage(true);
        showFailed(false, 1);

    }


    @Override
    public void initTimeList(QiangGouBean response, AppHomePageBean.Children children) {
        try {
            //获取现在手机的
            String time = response.data.timeList.get(0).times.get(1).startTime;
            String sysTime = response.data.timeList.get(0).times.get(1).sysTime;
            children.startTime = response.data.timeList.get(0).times.get(1).timeStr;
            children.countTime = Long.parseLong(time) - Long.parseLong(sysTime) + "";
            mAdapter.notifyDataSetChanged();


        } catch (Exception e) {

        }

    }

    @Override
    public void setPriceCmsModuleDataVO(StockPriceBean bean) {
        try {
            for (AppHomePageBean.HomeData data : mBean.dataList) {
                if (data.templateCode != null && data.templateCode.equals(AppHomePageBean.GOODS_R1C3)) {
                    for (int i = 0; i < data.staticData.resultBean.listObj.size(); i++) {
                        for (int j = 0; j < bean.data.plist.size(); j++) {
                            if (bean.data.plist.get(j).mpId.equals(data.moduleData.spModuleDataList.get(i).mpId)) {
                                data.moduleData.spModuleDataList.get(i).price = bean.data.plist.get(j).price;
                                data.moduleData.spModuleDataList.get(i).promotionPrice = bean.data.plist.get(j).promotionPrice;
                            }
                        }
                    }
                }
            }
            mAdapter.setNewData(mBean.dataList);
        } catch (Exception e) {
            Log.i(TAG, "setPriceCmsModuleDataVO erro");

        }

    }


}
