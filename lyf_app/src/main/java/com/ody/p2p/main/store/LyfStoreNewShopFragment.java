package com.ody.p2p.main.store;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.main.R;
import com.ody.p2p.main.search.LyfSearchResultAdapter;
import com.ody.p2p.productdetail.store.StoreHomeActivity;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.searchresult.CartExtBean;
import com.ody.p2p.search.searchresult.FilterSelectBean;
import com.ody.p2p.search.searchresult.PromotionDetailBean;
import com.ody.p2p.search.searchresult.SearchResultAdapter;
import com.ody.p2p.search.searchresult.SearchResultImpl;
import com.ody.p2p.search.searchresult.SearchResultView;
import com.ody.p2p.search.searchresult.popupwindow.PopUpWindowUtils;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.search.searchresult.popupwindow.SpaceItemDecoration;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LoadAnimationDrawable;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2017/7/20.
 */

public class LyfStoreNewShopFragment extends BaseFragment implements SearchResultView, View.OnClickListener {
    private StoreHomeActivity storeHomeActivity;
    private static int pageNo;
    private static SearchResultImpl mPresenter;
    private String key;
    private String shoppingGuideJson;
    private String brandIds;
    private String priceAnger;
    private String navCategoryIds;
    private String promotionIds;
    protected ImageView iv_loading;
    protected LinkedList<ResultBean.AttributeResult> attributeResult;
    protected List<ResultBean.SortBean> multipleList = new ArrayList<>();
    protected List<ResultBean.ProductBean> searchResultList = new ArrayList<>();
    private RadioButton rb_multiple;
    private TextView rb_sales;
    private TextView rb_filter;
    private View view_filter;
    private ImageView iv_showtype;
    private LinearLayout ll_footer;
    private LinearLayout ll_loading;
    private TextView tv_nomore;
    private RecyclerView recycler_seachreuslt;
    private LinearLayout ll_nosearch;
    private ImageView iv_nosearch;
    private TextView txt_nosearch;
    private View view_cover;
    public static final int TAB_TYPE = 1000;
    public static final int LIST_TYPE = 1001;

    protected int showType = TAB_TYPE;
    private PopUpWindowUtils utils;
    private View viewFooter;
    protected boolean isLoading;
    protected boolean loadCom;
    protected String sortType;
    protected boolean filterShowFlag = false;
    private OdySwipeRefreshLayout swipeRefreshLayout;
    private FilterSelectBean selectBean;
    protected int totalPage;
    private int multipleRes = -1;
    private ColorStateList multiplecolor = null;
    private LinearLayout rl_sort;
    private SearchResultAdapter adapter;
    protected LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    protected String productMpIds = "";
    private LoadAnimationDrawable loadAnimationDrawable;
    private static String merchantId;

    @Override
    public void initPresenter() {
        pageNo = 1;
        mPresenter = new SearchResultImpl(this);
        mPresenter.getList(key, null, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId, "IS_NEW");
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_lyf_store_all_shop;
    }

    @Override
    public void initView(View view) {
        storeHomeActivity = new StoreHomeActivity();
        merchantId = (String) getContext().getIntent().getExtras().get("merchantId");
        showType = TAB_TYPE;
//        key = "果汁";
        key = "";
        rl_sort = (LinearLayout) view.findViewById(R.id.rl_sort);
        rl_sort.setVisibility(View.GONE);
        rb_multiple = (RadioButton) view.findViewById(R.id.rb_multiple);
        rb_sales = (TextView) view.findViewById(R.id.rb_sales);
        rb_filter = (TextView) view.findViewById(R.id.rb_filter);
        view_filter = view.findViewById(R.id.view_filter);
        iv_showtype = (ImageView) view.findViewById(R.id.iv_showtype);
        ll_footer = (LinearLayout) view.findViewById(R.id.ll_footer);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading);
        tv_nomore = (TextView) view.findViewById(R.id.tv_nomore);
        recycler_seachreuslt = (RecyclerView) view.findViewById(R.id.recycler_seachreuslt);
        swipeRefreshLayout = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        ll_nosearch = (LinearLayout) view.findViewById(R.id.ll_nosearch);
        iv_nosearch = (ImageView) view.findViewById(R.id.iv_nosearch);
        txt_nosearch = (TextView) view.findViewById(R.id.txt_nosearch);
        view_cover = view.findViewById(R.id.view_cover);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        utils = new PopUpWindowUtils(context());
        LayoutInflater layoutInflater = getContext().getLayoutInflater();
        viewFooter = layoutInflater.inflate(R.layout.listview_footer, null);
        loadAnimationDrawable = new LoadAnimationDrawable();
        rb_multiple.setSelected(true);
        rb_filter.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        Drawable drawable = getResources().getDrawable(R.drawable.selector_lyf_filter);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rb_multiple.setCompoundDrawables(null, null, drawable, null);
        rb_multiple.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        rb_sales.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        multipleRes = R.drawable.ic_lyf_selected;
        multiplecolor = getResources().getColorStateList(R.color.lyf_text_selector);
//        recycler_seachreuslt.addItemDecoration(new LyfSpaceItemDecoration(15));

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
                                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds);
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
                                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds);
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

    @Override
    public void doBusiness(Context mContext) {
        initListener();
        linearLayoutManager = new LinearLayoutManager(context());
        gridLayoutManager = new GridLayoutManager(context(), 2);
        swipeRefreshLayout.setCanLoadMore(true);
        swipeRefreshLayout.setCanRefresh(false);
        recycler_seachreuslt.setLayoutManager(gridLayoutManager);
        recycler_seachreuslt.addItemDecoration(new SpaceItemDecoration(2));
    }


    private void initListener() {
        rb_multiple.setOnClickListener(this);
        iv_showtype.setOnClickListener(this);
        rb_filter.setOnClickListener(this);
        iv_nosearch.setOnClickListener(this);
        rb_sales.setOnClickListener(this);

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
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds);
            }
        });
        showTop(recycler_seachreuslt);
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        }
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
                        mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds);
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
                    utils.showFilterPop(rl_sort, attributeResult, priceAnger);
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
        if (v.equals(iv_showtype)) {
            if (showType == TAB_TYPE) {
                showType = LIST_TYPE;
                iv_showtype.setImageResource(com.ody.p2p.search.R.drawable.icon_list);
                recycler_seachreuslt.setLayoutManager(linearLayoutManager);
                adapter = new LyfSearchResultAdapter(getContext(), searchResultList, showType);
                recycler_seachreuslt.setAdapter(adapter);
                if (loadCom) {
                    adapter.addFootView(viewFooter);
                }
                adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                    @Override
                    public void addtoCart(String mpId, int position) {
                        mPresenter.addToCart(mpId, position);
                    }
                });
                adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                    @Override
                    public void toShop(String mpId) {
                        Bundle extra = new Bundle();
                        extra.putString(Constants.SP_ID, mpId);
                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                    }
                });
            } else if (showType == LIST_TYPE) {
                showType = TAB_TYPE;
                iv_showtype.setImageResource(com.ody.p2p.search.R.drawable.icon_grid);
                recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                adapter.setShowFlag(showType);
                adapter = new LyfSearchResultAdapter(getContext(), searchResultList, showType);
                recycler_seachreuslt.setAdapter(adapter);
                adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                    @Override
                    public void addtoCart(String mpId, int position) {
                        mPresenter.addToCart(mpId, position);
                    }
                });
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
                adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                    @Override
                    public void toShop(String mpId) {
                        Bundle extra = new Bundle();
                        extra.putString(Constants.SP_ID, mpId);
                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                    }
                });
            }
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
                mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds);
            }
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

    @Override
    public void initSearchList(ResultBean bean) {
        isLoading = false;
        ll_footer.setVisibility(View.GONE);
        recyclerLoading();
        if (bean != null && bean.data != null) {
            totalPage = (bean.data.totalCount % 20 == 0) ? bean.data.totalCount / 20 : bean.data.totalCount / 20 + 1;
            if (bean.data.productList != null && bean.data.productList.size() > 0) {
                if (bean.data.productList != null && bean.data.productList.size() > 0) {
                    for (int i = 0; i < bean.data.productList.size(); i++) {
                        if (StringUtils.isEmpty(productMpIds)) {
                            productMpIds += bean.data.productList.get(i).mpId;
                        } else {
                            productMpIds += ",";
                            productMpIds += bean.data.productList.get(i).mpId;
                        }
                    }
                    mPresenter.getCurrentPrice(productMpIds);
                }
                if (pageNo == 1) {
                    searchResultList = new ArrayList<>();
//                    if (attributeResult == null) {
                    attributeResult = bean.data.attributeResult;
                    for (int i = 0; i < attributeResult.size(); i++) {
                        ResultBean.AttributeValue value = new ResultBean.AttributeValue();
                        value.isChecked = true;
                        value.value = getString(R.string.all);
                        attributeResult.get(i).attributeValues.addFirst(value);
                    }
                    if (selectBean != null) {
                        for (int i = 0; i < selectBean.attributeJson.size(); i++) {
                            for (int j = 0; j < attributeResult.size(); j++) {
                                if (attributeResult.get(j).id.equals(selectBean.attributeJson.get(i).attributeId)) {
                                    attributeResult.get(j).attributeValues.get(0).isChecked = false;
                                    for (int k = 0; k < attributeResult.get(j).attributeValues.size(); k++) {
                                        for (int l = 0; l < selectBean.attributeJson.get(i).attrValueIds.size(); l++) {
                                            if (selectBean.attributeJson.get(i).attrValueIds.get(l).equals(attributeResult.get(j).attributeValues.get(k).id)) {
                                                attributeResult.get(j).attributeValues.get(k).isChecked = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    }
                    searchResultList.addAll(bean.data.productList);
                    if (multipleList == null || multipleList.size() == 0) {
                        multipleList = bean.data.sortByList;
                        if (multipleList != null && multipleList.size() > 0) {
                            multipleList.get(0).isSelected = true;
                        }
                    }
                    if (showType == TAB_TYPE) {
                        recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                        adapter = new LyfSearchResultAdapter(context(), searchResultList, TAB_TYPE);
                        recycler_seachreuslt.setAdapter(adapter);
                        adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                            @Override
                            public void addtoCart(String mpId, int position) {
                                mPresenter.addToCart(mpId, position);
                            }
                        });
                        adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                            @Override
                            public void toShop(String mpId) {
                                Bundle extra = new Bundle();
                                extra.putString(Constants.SP_ID, mpId);
                                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                            }
                        });
                    } else {
                        recycler_seachreuslt.setLayoutManager(linearLayoutManager);
                        adapter = new LyfSearchResultAdapter(context(), searchResultList, showType);
                        recycler_seachreuslt.setAdapter(adapter);
                        adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                            @Override
                            public void addtoCart(String mpId, int position) {
                                mPresenter.addToCart(mpId, position);
                            }
                        });
                        adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                            @Override
                            public void toShop(String mpId) {
                                Bundle extra = new Bundle();
                                extra.putString(Constants.SP_ID, mpId);
                                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                            }
                        });
                    }
                } else {
                    if (searchResultList != null) {
                        searchResultList.addAll(bean.data.productList);
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                ll_nosearch.setVisibility(View.GONE);
                recycler_seachreuslt.setVisibility(View.VISIBLE);
            } else {
                if (pageNo == 1) {
                    if (bean.data.zeroRecommendResult != null && bean.data.zeroRecommendResult.size() > 0) {
                        ll_nosearch.setVisibility(View.GONE);
                        recycler_seachreuslt.setVisibility(View.VISIBLE);
                        String words = bean.data.zeroRecommendWord;
                        searchResultList = bean.data.zeroRecommendResult;
                        if (searchResultList != null && searchResultList.size() > 0) {
                            if (showType == TAB_TYPE) {
                                recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                                adapter = new LyfSearchResultAdapter(context(), searchResultList, TAB_TYPE);
                                recycler_seachreuslt.setAdapter(adapter);
                                adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                                    @Override
                                    public void addtoCart(String mpId, int position) {
                                        mPresenter.addToCart(mpId, position);
                                    }
                                });
                                adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                                    @Override
                                    public void toShop(String mpId) {
                                        Bundle extra = new Bundle();
                                        extra.putString(Constants.SP_ID, mpId);
                                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                                    }
                                });
                            } else {
                                recycler_seachreuslt.setLayoutManager(linearLayoutManager);
                                adapter = new LyfSearchResultAdapter(context(), searchResultList, showType);
                                recycler_seachreuslt.setAdapter(adapter);
                                adapter.setAddBuyCart(new LyfSearchResultAdapter.AddBuyCart() {
                                    @Override
                                    public void addtoCart(String mpId, int position) {
                                        mPresenter.addToCart(mpId, position);
                                    }
                                });
                                adapter.setToSpInfo(new LyfSearchResultAdapter.ToSpInfo() {
                                    @Override
                                    public void toShop(String mpId) {
                                        Bundle extra = new Bundle();
                                        extra.putString(Constants.SP_ID, mpId);
                                        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                                    }
                                });
                            }
                            ll_nosearch.setVisibility(View.GONE);
                            recycler_seachreuslt.setVisibility(View.VISIBLE);
                        } else {
                            ll_nosearch.setVisibility(View.VISIBLE);
                            recycler_seachreuslt.setVisibility(View.GONE);
                        }

                    } else {
                        ll_nosearch.setVisibility(View.VISIBLE);
                        recycler_seachreuslt.setVisibility(View.GONE);
                    }
                }
            }
            if (searchResultList != null && searchResultList.size() < bean.data.totalCount) {
                loadCom = false;
            } else {
                loadCom = true;
                if (adapter != null) {
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

    @Override
    public void addSuccessful(int position) {
        ToastUtils.showToast(R.string.add_cart_success);
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

    static void loadMoreData(){
        ++pageNo;
        mPresenter.getList(null, null, pageNo, null, null, null, null, null, merchantId);
    }

    static class StoreAllShopHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadMoreData();
            }
        }
    }

}


