package com.ody.p2p.search.searchresult;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.wxlib.util.NetworkUtil;
import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.R;
import com.ody.p2p.search.searchresult.popupwindow.PopUpWindowUtils;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.search.searchresult.popupwindow.SpaceItemDecoration;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LoadAnimationDrawable;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.flowTagLayout.FlowTagLayout;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.ody.p2p.webactivity.UrlBean;
import com.ody.p2p.webactivity.WebActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.campusapp.router.router.ActivityRouter;
import dsshare.SharePopupWindow;

/**
 * Created by lxs on 2016/6/7.
 */

public class SearchResultFragment extends BaseFragment implements SearchResultView, View.OnClickListener {

    protected TextView et_search;
    protected PopUpWindowUtils utils;

    protected List<ResultBean.SortBean> multipleList = new ArrayList<>();
    protected List<ResultBean.ProductBean> searchResultList = new ArrayList<>();
    protected LinkedList<ResultBean.AttributeResult> attributeResult;
    protected List<String> searchWordList;
    protected String searchWord;

    protected View viewFooter;
    protected RelativeLayout rl_window;

    protected RelativeLayout rl_search;
    protected LinearLayout rl_sort;
    public RelativeLayout rl_serach;
    protected LinearLayout ll_nosearch;
    protected ImageView iv_nosearch;
    protected TextView txt_nosearch;
    protected RadioButton rb_multiple;
    protected TextView rb_filter;
    protected TextView rb_sales;
    protected ImageView iv_showtype;
    protected ImageView iv_cartCar;
    protected String[] menuStr;//右角标
    protected int[] menuRes;

    protected GridView grid_result;

    protected boolean loadCom;

    protected View view_cover;
    protected View view_filter;
    protected ImageView iv_back;
    protected RecyclerView recycler_seachreuslt;
    protected OdySwipeRefreshLayout swipeRefreshLayout;

    protected ImageView img_totop_button;

    protected SearchResultAdapter adapter;

    public static final int TAB_TYPE = 1000;
    public static final int LIST_TYPE = 1001;

    protected int showType = TAB_TYPE;

    public static final int HIDE_TITLE = 0x01;
    public static final int SHOW_TITLE = 0x02;

    protected int title_top = -1;
    protected int topmargin = -1;

    protected int shopCount;
    protected SearchResultPresenter mPresenter;
    public String brandIds;
    public String brand_icon;
    public String brandName;
    protected String key;
    protected String navCategoryNames;

    protected String navCategoryIds;
    protected String sortType;
    protected int pageNo;
    protected int totalPage;
    protected boolean filterShowFlag = false;
    protected FilterSelectBean selectBean;
    protected String shoppingGuideJson;
    protected String priceAnger;
    protected boolean defaultShowTop = true;

    protected int multipleRes = -1;
    protected ColorStateList multiplecolor = null;
    protected RelativeLayout rl_search_head;

    protected TranslateAnimation hideAnim;
    protected TranslateAnimation showAnim;
    protected int titleHeight;
    protected boolean hideFlag = false;
    protected boolean showFlag = true;
    protected FlowTagLayout flow_re;
    protected TextView tv_recommend;
    protected RelativeLayout rl_recommend;
    protected LinearLayout ll_brand;

    protected RecyclerView recycler_category;

    protected LinearLayoutManager linearLayoutManager;
    protected GridLayoutManager gridLayoutManager;
    protected LinearLayoutManager categoryManager;

    protected String promotionIds;

    protected List<ResultCategoryBean> categoryList;

    protected LinearLayout ll_footer;
    protected LinearLayout ll_loading;
    protected ImageView iv_loading;
    protected TextView tv_nomore;
    protected boolean isLoading;
    protected LinearLayout ll_hot;
    protected FlowTagLayout fl_hot;

    protected boolean pressDown = false;
    private boolean showPassivity = false;//是否需要被动调用显示动画
    private boolean hidePassivity = false;//是否需要被动调用隐藏动画

    public LoadAnimationDrawable loadAnimationDrawable;

    protected String merchantId;
    protected String store;

    @Override
    public void initPresenter() {
        if (utils != null && attributeResult != null && attributeResult.size() > 0) {
            utils.resetSelectListConfirm(attributeResult);
        }
        rb_sales.setSelected(false);
        rb_multiple.setSelected(true);
        pageNo = 1;
        mPresenter = new SearchResultImpl(this);
        filterShowFlag = false;
        priceAnger = "";
        shoppingGuideJson = "";
        rb_filter.setSelected(false);
        mPresenter.getList(key, null, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_searchresult;
    }

    @Override
    public void initView(View view) {
        showType = TAB_TYPE;
        key = getContext().getIntent().getStringExtra(Constants.SEARCH_KEY);
        merchantId = getContext().getIntent().getStringExtra("merchantId");
        store = getContext().getIntent().getStringExtra("store");
        navCategoryNames = getContext().getIntent().getStringExtra(Constants.NAVCATEGORY_NAME);
        navCategoryIds = getContext().getIntent().getStringExtra(Constants.NAVCATEGORY_ID);
        promotionIds = getContext().getIntent().getStringExtra(Constants.PRO_ID);
        if (getContext().getIntent().getData() != null) {
            String distributeId = getContext().getIntent().getData().getQueryParameter("distributeId");
            String shareCode = getContext().getIntent().getData().getQueryParameter("shareCode");
            brandIds = getContext().getIntent().getData().getQueryParameter("brandId");
            OdyApplication.putValueByKey(Constants.DISTRIBUTOR_ID, distributeId);
            OdyApplication.putValueByKey(Constants.SHARE_CODE, shareCode);
        }
        if (StringUtils.isEmpty(brandIds)) {
            brandIds = getContext().getIntent().getStringExtra("brandIds");
        }
        brand_icon = getContext().getIntent().getStringExtra("brandImgUrl");
        brandName = getContext().getIntent().getStringExtra("brandName");
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(navCategoryIds) && StringUtils.isEmpty(brandIds)) {
            String keyUrl = getContext().getIntent().getStringExtra(ActivityRouter.KEY_URL);
            if (keyUrl != null && keyUrl.length() > 0) {
                UrlBean bean = JumpUtils.getUrlBean(keyUrl);
                if (null != bean) {
                    key = bean.keyword;
                    navCategoryNames = bean.navCategoryNames;
                    navCategoryIds = bean.navCategoryIds;
                    brandIds = bean.brandIds;
                } else {
                    key = getString(R.string.canont_get_keyword);
                }
            }
        }
        getContext().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        utils = new PopUpWindowUtils(getContext());
        et_search = (TextView) view.findViewById(R.id.et_search);
        if (null != navCategoryIds && !"".equals(navCategoryIds)) {
            et_search.setText(navCategoryNames);
        } else {
            et_search.setText(key);
        }
        LayoutInflater layoutInflater = getContext().getLayoutInflater();
        viewFooter = layoutInflater.inflate(R.layout.listview_footer, null);
        rl_window = (RelativeLayout) view.findViewById(R.id.rl_window);
        rl_sort = (LinearLayout) view.findViewById(R.id.rl_sort);
        rl_search = (RelativeLayout) view.findViewById(R.id.rl_search);
        rb_multiple = (RadioButton) view.findViewById(R.id.rb_multiple);
        recycler_seachreuslt = (RecyclerView) view.findViewById(R.id.recycler_seachreuslt);
        iv_showtype = (ImageView) view.findViewById(R.id.iv_showtype);
        rb_filter = (TextView) view.findViewById(R.id.rb_filter);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        rl_serach = (RelativeLayout) view.findViewById(R.id.rl_serach);
        ll_nosearch = (LinearLayout) view.findViewById(R.id.ll_nosearch);
        iv_nosearch = (ImageView) view.findViewById(R.id.iv_nosearch);
        txt_nosearch = (TextView) view.findViewById(R.id.txt_nosearch);
        iv_cartCar = (ImageView) view.findViewById(R.id.iv_cartcar);
        rb_sales = (TextView) view.findViewById(R.id.rb_sales);
        img_totop_button = (ImageView) view.findViewById(R.id.img_totop_button);
        view_cover = view.findViewById(R.id.view_cover);
        swipeRefreshLayout = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        view_filter = view.findViewById(R.id.view_filter);
        rl_search_head = (RelativeLayout) view.findViewById(R.id.rL_search_head);
        flow_re = (FlowTagLayout) view.findViewById(R.id.flow_re);
        tv_recommend = (TextView) view.findViewById(R.id.tv_recommend);
        rl_recommend = (RelativeLayout) view.findViewById(R.id.rl_recommend);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        ll_footer = (LinearLayout) view.findViewById(R.id.ll_footer);
        tv_nomore = (TextView) view.findViewById(R.id.tv_nomore);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading);

        ll_hot = (LinearLayout) view.findViewById(R.id.ll_hot);
        fl_hot = (FlowTagLayout) view.findViewById(R.id.fl_hot);
//        iv_loading.setBackgroundResource(R.drawable.footer_loading);
        recycler_category = (RecyclerView) view.findViewById(R.id.recycler_category);
        //grid_result = (GridView) view.findViewById(R.id.grid_result);
        Drawable drawable = getResources().getDrawable(R.drawable.selector_drawer_open);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rb_multiple.setCompoundDrawables(null, null, drawable, null);
        rb_multiple.setSelected(true);
        loadAnimationDrawable = new LoadAnimationDrawable();
        recycler_seachreuslt.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (recyclerView.getAdapter() == null || lm == null) {
                        return;
                    }
                    if (lm.findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 10) {
                        if (loadCom) {
                            ll_footer.setVisibility(View.GONE);
                            recyclerLoading();
                        } else {
                            ll_footer.setVisibility(View.VISIBLE);
                            tv_nomore.setVisibility(View.GONE);
                            iv_loading.setVisibility(View.VISIBLE);
                            ll_loading.setVisibility(View.VISIBLE);
                            initLoading();
                            if (!isLoading) {
                                isLoading = true;
                                pageNo++;
                                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
                            }
                        }
                    } else {
                        ll_footer.setVisibility(View.GONE);
                    }
                } else {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    if (recyclerView.getAdapter() == null || layoutManager == null) {
                        return;
                    }
                    if (layoutManager.findLastVisibleItemPosition() >= recyclerView.getAdapter().getItemCount() - 10) {
                        if (loadCom) {
                            ll_footer.setVisibility(View.GONE);
                            tv_nomore.setVisibility(View.VISIBLE);
                            iv_loading.setVisibility(View.GONE);
                            ll_loading.setVisibility(View.GONE);
                            recyclerLoading();
                        } else {
                            ll_footer.setVisibility(View.VISIBLE);
                            tv_nomore.setVisibility(View.GONE);
                            iv_loading.setVisibility(View.VISIBLE);
                            ll_loading.setVisibility(View.VISIBLE);

//                            startAnimation(iv_loading);
                            initLoading();
                            if (!isLoading) {
                                isLoading = true;
                                pageNo++;
                                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
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
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void doBusiness(Context mContext) {
        categoryManager = new LinearLayoutManager(getContext());
        categoryManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_category.setLayoutManager(categoryManager);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        swipeRefreshLayout.setHeaderViewBackgroundColor(0x00f0f0f0);
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout.setOdyDefaultView(true);
        swipeRefreshLayout.setCanLoadMore(false);
        recycler_seachreuslt.setLayoutManager(gridLayoutManager);
        recycler_seachreuslt.addItemDecoration(new SpaceItemDecoration(2));
        initAnimation(45);

        rb_multiple.setOnClickListener(this);
        iv_showtype.setOnClickListener(this);
        rb_filter.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        et_search.setOnClickListener(this);
        iv_nosearch.setOnClickListener(this);
        iv_cartCar.setOnClickListener(this);
        rb_sales.setOnClickListener(this);
        if (null != brandIds && !"".equals(brandIds)) {//品牌分享
            menuStr = new String[]{getString(R.string.home), getString(R.string.message), getString(R.string.share)};
            menuRes = new int[]{R.drawable.ic_homepage, R.drawable.ic_message, R.drawable.ic_share};
        } else {
            menuStr = new String[]{getString(R.string.home), getString(R.string.message)};
            menuRes = new int[]{R.drawable.ic_homepage, R.drawable.ic_message};
        }

        img_totop_button.setOnClickListener(this);

        recycler_seachreuslt.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    if (totalPage > 1) {
                        ToastUtils.showPage((((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() / 20 + 1) + "/" + totalPage);
                    }
                }
            }
        });
        recycler_seachreuslt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeRefreshLayout.setRefreshing(false);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressDown = true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressDown = false;
                }
                return false;
            }
        });

        recycler_seachreuslt.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    int scrollState = recycler_seachreuslt.getScrollState();
                    Log.d("onScrollStateChanged", "state=" + scrollState);
                    if (scrollState == 0) {
                        Log.d("onScrollStateChanged", "state=" + scrollState + "被动滚动操作");
                    } else {
                        Log.d("animal", "到达底部");
                        hidePassivity = true;
                    }
                } else {
                    hidePassivity = false;
                }
                Log.d("onScrolle", "onScrolled " + dy);
                if (recycler_seachreuslt.getAdapter().getItemCount() > 6) {
                    if (dy > 5) {
                        if (!pressDown && showFlag && !hideFlag) {
                            rl_window.startAnimation(hideAnim);
                        }
                    }
                    if (dy < -5) {
                        if (!pressDown && !showFlag && hideFlag) {
                            rl_window.startAnimation(showAnim);
                        }
                    }
                }
            }
        });

        swipeRefreshLayout.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
            }

            @Override
            public void onPullDistance(int distance) {
                if (!(ViewCompat.canScrollVertically(recycler_seachreuslt, -1)) && !showFlag && hideFlag) {
                    if (null == rl_window || null == showAnim) {
                        return;
                    }
                    rl_window.startAnimation(showAnim);
                }
            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });

        swipeRefreshLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNo++;
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
            }

            @Override
            public void onPushEnable(boolean enable) {
            }

            @Override
            public void onPushDistance(int distance) {
                // TODO Auto-generated method stub
            }

        });

        utils.setFilterDismissListener(new PopUpWindowUtils.FilterDismissListener() {
            @Override
            public void dismiss() {
                view_cover.setVisibility(View.GONE);
                filterShowFlag = false;
            }
        });

        utils.setFilterSureListener(new PopUpWindowUtils.FilterSureListener() {
            @Override
            public void setFilter(String price) {
                utils.dismissPop();
                priceAnger = price;
                shoppingGuideJson = getSelectListJson();
                pageNo = 1;
                if ((priceAnger != null && priceAnger.contains(",")) || (selectBean.attributeJson != null && selectBean.attributeJson.size() > 0)) {
                    rb_filter.setSelected(true);
                } else {
                    rb_filter.setSelected(false);
                }
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
            }
        });
        showTop(recycler_seachreuslt);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.initCartNum();
    }

    @Override
    public void onClick(View v) {
        utils.dismissPop();
        if (v.equals(rb_multiple)) {
            if (multipleList != null && multipleList.size() > 0) {
                rb_multiple.setChecked(true);
                utils.showMultiplePop(rb_multiple, multipleList, multipleRes, multiplecolor);
                utils.setMutipleListener(new PopUpWindowUtils.MutipleListener() {
                    @Override
                    public void setMutiple(String mutiple) {
                        rb_sales.setSelected(false);
                        rb_multiple.setSelected(true);
                        sortType = mutiple;
                        pageNo = 1;
                        rb_multiple.setChecked(false);
                        mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
                        utils.dismissPop();
                    }
                });
            } else {
                rb_multiple.setChecked(false);
            }
        }
        if (v.equals(rb_filter)) {
            if (!filterShowFlag) {
                if (attributeResult != null) {
                    utils.showFilterPop(rl_search, attributeResult, priceAnger);
                    filterShowFlag = !filterShowFlag;
                }
            } else {
                utils.dismissPop();
                filterShowFlag = !filterShowFlag;
            }
            if (attributeResult != null) {
                view_cover.setVisibility(!filterShowFlag ? View.VISIBLE : View.GONE);
            }
        }
        if (v.equals(img_totop_button)) {
            recycler_seachreuslt.smoothScrollToPosition(0);
        }
        if (v.equals(iv_showtype)) {
            if (showType == TAB_TYPE) {
                showType = LIST_TYPE;
                iv_showtype.setImageResource(R.drawable.icon_grid);
                recycler_seachreuslt.setLayoutManager(linearLayoutManager);
                adapter = new SearchResultAdapter(getContext(), searchResultList, showType);
                if (loadCom) {
                    adapter.addFootView(viewFooter);
                    adapter.notifyDataSetChanged();
                }
                recycler_seachreuslt.setAdapter(adapter);
                adapter.setAddBuyCart(new SearchResultAdapter.AddBuyCart() {
                    @Override
                    public void addtoCart(String mpId, int position) {
                        mPresenter.addToCart(mpId, position);
                    }
                });
                adapter.setToSpInfo(new SearchResultAdapter.ToSpInfo() {
                    @Override
                    public void toShop(String mpId) {
                        Bundle bd = new Bundle();
                        bd.putString(Constants.SP_ID, mpId);
                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                    }
                });
            } else if (showType == LIST_TYPE) {
                showType = TAB_TYPE;
                iv_showtype.setImageResource(R.drawable.icon_list);
                recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                adapter.setShowFlag(showType);
                adapter = new SearchResultAdapter(getContext(), searchResultList, showType);
                recycler_seachreuslt.setAdapter(adapter);
                if (loadCom) {
                    adapter.addFootView(viewFooter);
                    adapter.setChangeGridLayoutManager(new SearchResultAdapter.ChangeGridLayoutManagerSpance() {
                        @Override
                        public void change(final int size, final boolean isAddHead, final boolean isAddFoot) {
                            if (showType == TAB_TYPE) {
                                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        int spanSize = 1;
                                        if (adapter.isAddHead) {
                                            if (position == 0) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        if (adapter.isAddFoot) {
                                            if (position == size) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        return spanSize;
                                    }
                                });
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                adapter.setAddBuyCart(new SearchResultAdapter.AddBuyCart() {
                    @Override
                    public void addtoCart(String mpId, int position) {
                        mPresenter.addToCart(mpId, position);
                    }
                });
                adapter.setToSpInfo(new SearchResultAdapter.ToSpInfo() {
                    @Override
                    public void toShop(String mpId) {
                        Bundle bd = new Bundle();
                        bd.putString(Constants.SP_ID, mpId);
                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                    }
                });
            }
        }
        if (v.equals(iv_back)) {
            getContext().finish();
        }
        if (v.equals(et_search)) {
            JumpUtils.ToActivity(JumpUtils.SEARCH);
            getContext().finish();
        }
        if (v.equals(iv_nosearch)) {
            JumpUtils.ToActivity(JumpUtils.SEARCH);
            getContext().finish();
        }
        if (v.equals(iv_cartCar)) {
            final List<MenuPopBean> popList = new ArrayList<>();
            for (int i = 0; i < menuStr.length; i++) {
                MenuPopBean bean = new MenuPopBean();
                bean.content = menuStr[i];
                bean.picRes = menuRes[i];
                popList.add(bean);
            }
            final com.ody.p2p.views.basepopupwindow.SelectMenu menu = new com.ody.p2p.views.basepopupwindow.SelectMenu(getContext(), popList);
            menu.setClickSelectListener(new com.ody.p2p.views.basepopupwindow.SelectMenu.clickSelectMenuListener() {
                @Override
                public void click(int position) {
                    if (position == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 0);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    }
                    if (position == 1) {
                        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                            JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/my/my-message.html", WebActivity.NO_TITLE, -1, "");
                        } else {
                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                        }
                    }
                    if (position == 2) {
                        if (null != brandIds && !"".equals(brandIds)) {//品牌分享  有Id的时候肯定是在品牌页面的
                            goToShare();
                            menu.dismiss();
                        }
                    }

                }
            });
            menu.showAsDropDown(iv_cartCar, PxUtils.dipTopx(-60), 15);
        }
        if (v.equals(rb_sales)) {
            pageNo = 1;
            if (rb_sales.isSelected()) {
//                rb_sales.setSelected(false);
//                sortType = "volume4sale_asc";
//                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds);
            } else {
                rb_multiple.setSelected(false);
                reFreshSortList(multipleList);
                rb_multiple.setText("综合");
                sortType = "volume4sale_desc";
                rb_sales.setSelected(true);
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
            }
        }
    }

    @Override
    public void initSearchList(ResultBean bean) {
        if (bean != null && bean.data != null) {
            totalPage = (bean.data.totalCount % 10 == 0) ? bean.data.totalCount / 10 : bean.data.totalCount / 10 + 1;
            if (bean.data.productList != null && bean.data.productList.size() > 0) {
                if (pageNo == 1) {
                    if (attributeResult == null || attributeResult.size() == 0) {
                        attributeResult = bean.data.attributeResult;
                    }
                    searchResultList = new ArrayList<>();
                    searchResultList.addAll(bean.data.productList);
                    if (multipleList == null || multipleList.size() == 0) {
                        multipleList = bean.data.sortByList;
                        if (multipleList != null && multipleList.size() > 0) {
                            multipleList.get(0).isSelected = true;
                        }
                    }
                    if (showType == TAB_TYPE) {
                        recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                        adapter = new SearchResultAdapter(getContext(), searchResultList, TAB_TYPE);
                        recycler_seachreuslt.setAdapter(adapter);
                        adapter.setAddBuyCart(new SearchResultAdapter.AddBuyCart() {
                            @Override
                            public void addtoCart(String mpId, int position) {
                                mPresenter.addToCart(mpId, position);
                            }
                        });
                        adapter.setToSpInfo(new SearchResultAdapter.ToSpInfo() {
                            @Override
                            public void toShop(String mpId) {
                                Bundle bd = new Bundle();
                                bd.putString(Constants.SP_ID, mpId);
                                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                            }
                        });
                    } else {
                        recycler_seachreuslt.setLayoutManager(linearLayoutManager);
                        adapter = new SearchResultAdapter(getContext(), searchResultList, showType);
                        recycler_seachreuslt.setAdapter(adapter);
                        adapter.setAddBuyCart(new SearchResultAdapter.AddBuyCart() {
                            @Override
                            public void addtoCart(String mpId, int position) {
                                mPresenter.addToCart(mpId, position);
                            }
                        });
                        adapter.setToSpInfo(new SearchResultAdapter.ToSpInfo() {
                            @Override
                            public void toShop(String mpId) {
                                Bundle bd = new Bundle();
                                bd.putString(Constants.SP_ID, mpId);
                                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                            }
                        });
                    }
                } else {
                    if (searchResultList != null) {
                        searchResultList.addAll(bean.data.productList);
                    }
                }
                ll_nosearch.setVisibility(View.GONE);
                recycler_seachreuslt.setVisibility(View.VISIBLE);
                if (bean.data.productList.size() < 10) {
                    loadCom = true;
                    swipeRefreshLayout.setCanLoadMore(false);
                    adapter.addFootView(viewFooter);
                    adapter.setChangeGridLayoutManager(new SearchResultAdapter.ChangeGridLayoutManagerSpance() {
                        @Override
                        public void change(final int size, final boolean isAddHead, final boolean isAddFoot) {
                            if (showType == TAB_TYPE) {
                                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        int spanSize = 1;
                                        if (adapter.isAddHead) {
                                            if (position == 0) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        if (adapter.isAddFoot) {
                                            if (position == size) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        return spanSize;
                                    }
                                });
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                } else {
                    loadCom = false;
                    swipeRefreshLayout.setCanLoadMore(true);
                }
            } else {
                if (pageNo == 1) {
                    ll_nosearch.setVisibility(View.VISIBLE);
                    recycler_seachreuslt.setVisibility(View.GONE);
                } else {
                    loadCom = true;
                    swipeRefreshLayout.setCanLoadMore(false);
                    adapter.addFootView(viewFooter);
                    adapter.setChangeGridLayoutManager(new SearchResultAdapter.ChangeGridLayoutManagerSpance() {
                        @Override
                        public void change(final int size, final boolean isAddHead, final boolean isAddFoot) {
                            if (showType == TAB_TYPE) {
                                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        int spanSize = 1;
                                        if (adapter.isAddHead) {
                                            if (position == 0) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        if (adapter.isAddFoot) {
                                            if (position == size) {
                                                spanSize = gridLayoutManager.getSpanCount();
                                            }
                                        }
                                        return spanSize;
                                    }
                                });
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }
    }

    int count = 0;

    @Override
    public void addSuccessful(int position) {
//        ToastUtils.showToast(getString(R.string.add_succeed));
        Toast mToast = Toast.makeText(getContext(), getString(R.string.add_succeed), Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();
        count++;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void initPromotion(CartExtBean bean) {

    }

    @Override
    public void initPromotionDetail(PromotionDetailBean bean) {

    }

    @Override
    public void initCartNum(int num) {
        shopCount = num;
    }

    @Override
    public void setHotWord(List<FuncBean.Data.AdSource> hotWord) {

    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {

    }

    @Override
    public void loadList(ResultBean bean) {

    }

    @Override
    public void setStoreHotKey(StoreHotKeyBean storeHotKeyBean) {

    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.GONE) {
            img_totop_button.setVisibility(View.VISIBLE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_enter));
        }
    }

    /**
     * 隐藏置顶按钮
     */
    protected void hideBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.VISIBLE) {
            img_totop_button.setVisibility(View.GONE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_exit));
        }
    }

    public List<ResultBean.SortBean> reFreshSortList(List<ResultBean.SortBean> multipleList) {
        if (multipleList != null && multipleList.size() > 0) {
            for (int i = 0; i < multipleList.size(); i++) {
                multipleList.get(i).isSelected = false;
            }
        }
        return multipleList;
    }


    public void resetAdapter(BaseRecyclerViewAdapter adapter) {
        this.adapter = (SearchResultAdapter) adapter;
    }


    public String getSelectListJson() {
        selectBean = new FilterSelectBean();
        if (attributeResult == null) {
            return null;
        }
        for (int i = 0; i < attributeResult.size(); i++) {
            FilterSelectBean.AttrValueId attrValueId = new FilterSelectBean.AttrValueId();
            boolean hasFlag = false;
            for (int j = 1; j < attributeResult.get(i).attributeValues.size(); j++) {
                if (attributeResult.get(i).attributeValues.get(j).isChecked == true) {
                    hasFlag = true;
                    attrValueId.attributeId = attributeResult.get(i).id;
                    attrValueId.attrValueIds.add(attributeResult.get(i).attributeValues.get(j).id);
                }
            }
            if (hasFlag) {
                selectBean.attributeJson.add(attrValueId);
            }
        }
        return new Gson().toJson(selectBean, FilterSelectBean.class).replaceAll("\\[", "%5b").replaceAll("\\]", "%5d").replaceAll("\\{", "%7b").replaceAll("\\}", "%7d");
    }

    private void goToShare() {
        SharePopupWindow shareindow = new SharePopupWindow(getContext(), SharePopupWindow.SHARE_BRAND, brandIds);
        shareindow.showAtLocation(iv_cartCar, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    /**
     * 搜索词
     *
     * @param context
     * @param text
     * @return
     */
    public static SpannableString getStick(Context context, String text) {
        SpannableString styledText = new SpannableString(text);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_stick_title), text.indexOf("\""), text.lastIndexOf("\""), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_stick_head), text.lastIndexOf("\"") + 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_stick_head), 0, text.indexOf("\""), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }

    /**
     * 头动画
     *
     * @param height
     */
    public void initAnimation(int height) {
        titleHeight = PxUtils.dipTopx(height);
        hideAnim = new TranslateAnimation(0, 0, 0, -titleHeight);
        hideAnim.setInterpolator(new DecelerateInterpolator());
        hideAnim.setDuration(500);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                pressDown =
                        hideFlag = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_window.clearAnimation();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_window
                        .getLayoutParams();
                params.topMargin = -titleHeight;
                params.bottomMargin = 0;
                rl_window.setLayoutParams(params);
                showFlag = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        showAnim = new TranslateAnimation(0, 0, 0, titleHeight);
        showAnim.setInterpolator(new DecelerateInterpolator());
        showAnim.setDuration(500);
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                showFlag = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_window.clearAnimation();
                hideFlag = false;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_window
                        .getLayoutParams();
                params.topMargin = 0;
                params.bottomMargin = -titleHeight;
                rl_window.setLayoutParams(params);
                if (hidePassivity) {
                    rl_window.startAnimation(hideAnim);
                    hidePassivity = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * 取类目树中的类目列表
     *
     * @param categoryList
     * @param navCategoryTreeResult
     * @return
     */
    public List<ResultCategoryBean> getCategoryList(List<ResultCategoryBean> categoryList, List<ResultBean.Child> navCategoryTreeResult) {
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        for (ResultBean.Child child : navCategoryTreeResult) {//取二级类目
            if (child.children != null && child.children.size() > 0) {
                for (ResultBean.Child childBean : child.children) {
                    ResultCategoryBean bean = new ResultCategoryBean();
                    bean.count = childBean.count;
                    bean.id = childBean.id;
                    bean.name = childBean.name;
                    categoryList.add(bean);
                }
            }
        }
        return categoryList;
    }

    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        } else {
            showFailed(false, 1);
            mPresenter.getList(key, null, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
        }
    }


    public void startAnimation(ImageView iv_loading) {
        iv_loading.clearAnimation();
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getBackground();
        if (animationDrawable != null) {
            animationDrawable.start();
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
        System.gc();
    }


}
