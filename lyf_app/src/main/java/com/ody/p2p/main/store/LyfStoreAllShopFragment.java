package com.ody.p2p.main.store;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.dao.SearchRiCiEntityDao;
import com.ody.p2p.entity.SearchRiCiEntity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.search.LyfGiftWindow;
import com.ody.p2p.main.search.LyfSearchResultAdapter;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.searchkey.SearchHotWordAdapter;
import com.ody.p2p.search.searchresult.CartExtBean;
import com.ody.p2p.search.searchresult.CategoryAdapter;
import com.ody.p2p.search.searchresult.FlowAdapter;
import com.ody.p2p.search.searchresult.PromotionDetailBean;
import com.ody.p2p.search.searchresult.ResultCategoryBean;
import com.ody.p2p.search.searchresult.SearchResultAdapter;
import com.ody.p2p.search.searchresult.SearchResultFragment;
import com.ody.p2p.search.searchresult.SearchResultView;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.flowTagLayout.FlowTagLayout;
import com.ody.p2p.views.flowTagLayout.OnTagClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meijunqiang on 2017/6/29.
 * 描述:
 */

public class LyfStoreAllShopFragment extends SearchResultFragment implements SearchResultView, View.OnClickListener {

    protected TextView txt_brandname;
    protected LinearLayout ll_brand;
    protected ImageView img_brandImg;
    protected RelativeLayout rl_promotion, ll_shopcart;
    protected TextView tv_total_money, tv_de_money, tv_go_shopcart, tv_look_pro, tv_promotioncontent, tv_de_content;
    protected CategoryAdapter categoryAdapter;
    protected TextView tv_msg;

    protected SearchHotWordAdapter hotAdapter;

    protected List<PromotionDetailBean.PromotionGiftDetailVO> gifts = new ArrayList<>();

    protected String productMpIds = "";

    @Override
    public void initView(View view) {
        super.initView(view);
        view_filter.setVisibility(View.VISIBLE);
        rb_filter.setVisibility(View.VISIBLE);
        rl_search.setVisibility(View.GONE);
        txt_brandname = (TextView) view.findViewById(com.ody.p2p.search.R.id.txt_brandname);
        ll_brand = (LinearLayout) view.findViewById(R.id.ll_brand);
        img_brandImg = (ImageView) view.findViewById(R.id.iv_brand);
        rl_promotion = (RelativeLayout) view.findViewById(R.id.rl_promotion);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        tv_go_shopcart = (TextView) view.findViewById(R.id.tv_go_shopcart);
        tv_look_pro = (TextView) view.findViewById(R.id.tv_look_pro);
        tv_promotioncontent = (TextView) view.findViewById(R.id.tv_promotioncontent);
        tv_de_content = (TextView) view.findViewById(R.id.tv_de_content);
        ll_shopcart = (RelativeLayout) view.findViewById(R.id.ll_shopcart);

        tv_msg = (TextView) view.findViewById(R.id.tv_msg);

        rb_filter.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        Drawable drawable = getResources().getDrawable(R.drawable.selector_lyf_filter);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rb_multiple.setCompoundDrawables(null, null, drawable, null);
        rb_multiple.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        rb_sales.setTextColor(getResources().getColorStateList(R.color.lyf_text_selector));
        multipleRes = R.drawable.ic_lyf_selected;
        multiplecolor = getResources().getColorStateList(R.color.lyf_text_selector);
//        recycler_seachreuslt.addItemDecoration(new LyfSpaceItemDecoration(15));
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
        rl_serach.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_edit_bg));
        if (!StringUtils.isEmpty(brandIds) || !StringUtils.isEmpty(promotionIds) || !StringUtils.isEmpty(navCategoryIds)) {
            ll_brand.setVisibility(View.VISIBLE);
            rl_serach.setVisibility(View.GONE);
            rb_filter.setVisibility(View.GONE);
            rl_promotion.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(brandName)) {
                txt_brandname.setText(brandName);
                txt_brandname.setVisibility(View.VISIBLE);
            } else {
                txt_brandname.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(brand_icon)) {
                GlideUtil.display(mContext, brand_icon).into(img_brandImg);
                img_brandImg.setVisibility(View.VISIBLE);
            } else {
                img_brandImg.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(promotionIds)) {
                txt_brandname.setText(getResources().getString(R.string.hot_promotions));
                txt_brandname.setVisibility(View.VISIBLE);
                img_brandImg.setVisibility(View.GONE);
                rb_filter.setVisibility(View.VISIBLE);
//                initAnimation(67);
                iv_cartCar.setVisibility(View.INVISIBLE);
                mPresenter.getPromotionInfo(promotionIds);
                mPresenter.getPromotionInfoDetail(promotionIds);
            }
            if (!StringUtils.isEmpty(navCategoryIds)) {
                txt_brandname.setText(navCategoryNames);
                txt_brandname.setVisibility(View.VISIBLE);
                img_brandImg.setVisibility(View.GONE);
                rb_filter.setVisibility(View.VISIBLE);
            }
        } else {
            rb_filter.setVisibility(View.VISIBLE);
            rl_serach.setVisibility(View.VISIBLE);
            ll_brand.setVisibility(View.GONE);
        }
//      来伊份

        menuStr = new String[]{getString(com.ody.p2p.search.R.string.home)};
        menuRes = new int[]{com.ody.p2p.search.R.drawable.ic_homepage};
        ll_shopcart.setVisibility(View.VISIBLE);
        hotAdapter = new SearchHotWordAdapter();

//        if(StringUtils.isEmpty(store)){
        fl_hot.setAdapter(hotAdapter);
//        }

        iv_showtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        defaultShowTop = false;
        tv_go_shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GO_MAIN, 3);
                JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                getContext().finish();
            }
        });
        tv_look_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gifts != null && gifts.size() > 0) {
                    LyfGiftWindow window = new LyfGiftWindow(getContext(), gifts);
                    window.showAtLocation(tv_look_pro, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
        ll_shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GO_MAIN, 3);
                JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                getContext().finish();
            }
        });
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        }
    }

    @Override
    public void initSearchList(ResultBean bean) {
        isLoading = false;
        ll_footer.setVisibility(View.GONE);
        recyclerLoading();
        if (bean != null && bean.data != null) {
            if (bean.data.navCategoryTreeResult != null && categoryList == null) {
                categoryList = new ArrayList<>();
                categoryList = getCategoryList(categoryList, bean.data.navCategoryTreeResult);
            }
            if (categoryList != null && categoryList.size() > 0) {
                recycler_category.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(navCategoryIds)) {
                    for (ResultCategoryBean result : categoryList) {
                        result.choose = false;
                        if (result.id != null && result.id.equals(navCategoryIds)) {
                            result.choose = true;
                        }
                    }
                }
//                else {
//                    categoryList.get(0).choose = true;
//                }
                if (categoryAdapter == null) {
                    categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                    categoryAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Object model) {
                            pageNo = 1;
                            categoryAdapter.refreshCateList(categoryList, position);
                            categoryAdapter.notifyDataSetChanged();
                            navCategoryIds = categoryList.get(position).id;
                            mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
                            adapter.setChangeGridLayoutManager(new SearchResultAdapter.ChangeGridLayoutManagerSpance() {
                                @Override
                                public void change(final int size, final boolean isAddHead, final boolean isAddFoot) {
                                    if (showType == TAB_TYPE) {
                                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                            @Override
                                            public int getSpanSize(int position) {
                                                int spanSize = 1;
                                                return spanSize;
                                            }
                                        });
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onItemLongClick(View view, int position, Object model) {

                        }
                    });
                    recycler_category.setAdapter(categoryAdapter);
                }
            } else {
                recycler_category.setVisibility(View.GONE);
            }
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
                    rl_recommend.setVisibility(View.GONE);
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
                        adapter = new LyfSearchResultAdapter(getContext(), searchResultList, TAB_TYPE);
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
                        adapter = new LyfSearchResultAdapter(getContext(), searchResultList, showType);
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
                        rl_recommend.setVisibility(View.VISIBLE);
                        recycler_seachreuslt.setVisibility(View.VISIBLE);
                        String words = bean.data.zeroRecommendWord;
                        if (words != null && words.length() > 0) {
                            String content = getString(R.string.noshear_product_recommend) + words + getString(R.string.please_change_key);
                            tv_recommend.setText(getStick(getContext(), content));
                        }
                        if (bean.data.maybeInterestedKeywords != null && bean.data.maybeInterestedKeywords.size() > 0) {
                            searchWordList = bean.data.maybeInterestedKeywords;
                            flow_re.setVisibility(View.VISIBLE);
                        } else {
                            flow_re.setVisibility(View.GONE);
                        }
                        if (searchWordList != null && searchWordList.size() > 0) {
                            FlowAdapter adapter = new FlowAdapter();
                            flow_re.setAdapter(adapter);
                            adapter.clearAndAddAll(searchWordList);
                            flow_re.setOnTagClickListener(new OnTagClickListener() {
                                @Override
                                public void onItemClick(FlowTagLayout parent, View view, int position) {
                                    et_search.setText(searchWordList.get(position));
                                    mPresenter.getList(searchWordList.get(position), sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
                                }
                            });
                        }
                        searchResultList = bean.data.zeroRecommendResult;
                        if (searchResultList != null && searchResultList.size() > 0) {
                            if (showType == TAB_TYPE) {
                                recycler_seachreuslt.setLayoutManager(gridLayoutManager);
                                adapter = new LyfSearchResultAdapter(getContext(), searchResultList, TAB_TYPE);
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
                                adapter = new LyfSearchResultAdapter(getContext(), searchResultList, showType);
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
                if (isNullList(bean.data.productList) && isNullList(bean.data.maybeInterestedKeywords) && isNullList(bean.data.zeroRecommendResult)) {
                    ll_hot.setVisibility(View.VISIBLE);
                    if (StringUtils.isEmpty(merchantId)) {
                        mPresenter.getHotWord();
                    } else {
                        mPresenter.getStoreHotKey(merchantId);
                    }

                }
            }
        }
    }


    @Override
    public void initPromotion(CartExtBean bean) {
        if (bean != null && bean.data != null) {
            tv_total_money.setText(UiUtils.getMoneyDouble(bean.data.totalPrice));
            rl_promotion.setVisibility(View.VISIBLE);
            tv_promotioncontent.setVisibility(View.VISIBLE);
            tv_de_content.setText(bean.data.message);
        }
    }

    @Override
    public void initPromotionDetail(PromotionDetailBean bean) {
        if (bean != null && bean.data != null) {
            tv_promotioncontent.setText(getResources().getString(R.string.result_promotion_description) + bean.data.description);
            tv_look_pro.setVisibility(View.GONE);
            if (bean.data.contentType == 13 || bean.data.contentType == 14) {
                tv_look_pro.setVisibility(View.VISIBLE);
                tv_look_pro.setText("查看换购");
            }
            if (bean.data.contentType == 4) {
                tv_look_pro.setVisibility(View.INVISIBLE);
                tv_look_pro.setText("查看赠品");
            }
            if (bean.data.promotionGiftDetailList != null && bean.data.promotionGiftDetailList.size() > 0) {
                gifts = bean.data.promotionGiftDetailList;
            } else {
                tv_look_pro.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initCartNum(int num) {
        super.initCartNum(num);
        UiUtils.setCareNum(num, 0, tv_msg);
    }

    @Override
    public void setStoreHotKey(StoreHotKeyBean storeHotKeyBean) {
        final List<StoreHotKeyBean.DataBean> list = storeHotKeyBean.getData();

//        if (!StringUtils.isEmpty(store) && store.equals("store")) {
        if (null != list && list.size() > 0) {
            ll_hot.setVisibility(View.VISIBLE);
            hotAdapter.clearAndAddAll(list);
            fl_hot.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onItemClick(FlowTagLayout parent, View view, int position) {
                    key = list.get(position).toString();
                    et_search.setText(key);
                    saveSearchData(key, 1);
                    mPresenter.getList(key, sortType, pageNo, shoppingGuideJson, brandIds, priceAnger, navCategoryIds, promotionIds, merchantId);
                }
            });
        } else {
            ll_hot.setVisibility(View.GONE);
        }
//        }

    }

    @Override
    public void setHotWord(final List<FuncBean.Data.AdSource> hotWord) {
        if (StringUtils.isEmpty(store)) {
            if (null != hotWord && hotWord.size() > 0) {
                ll_hot.setVisibility(View.VISIBLE);
                hotAdapter.clearAndAddAll(hotWord);
                fl_hot.setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onItemClick(FlowTagLayout parent, View view, int position) {
                        JumpUtils.toActivity(hotWord.get(position));
                    }
                });
            } else {
                ll_hot.setVisibility(View.GONE);
            }
        }
    }

    /*保存搜索纪录**/
    public void saveSearchData(String strSearch, int type) {
        SearchRiCiEntityDao mDao = OdyApplication.daoSession.getSearchRiCiEntityDao();
        if (StringUtils.isEmpty(strSearch)) {
            return;
        }
        SearchRiCiEntity entity = mDao.queryBuilder().where(SearchRiCiEntityDao.Properties.RiCiName.eq(strSearch)).build().unique();
        if (entity != null) {//有同名的riCiName
            // delete from 表名 where name ='****'
            mDao.deleteByKey(entity.get_id());
        }
        SearchRiCiEntity newEntity = new SearchRiCiEntity();
        newEntity.setRiCiName(strSearch);
        newEntity.set_id(null);
        mDao.insert(newEntity);
    }

    @Override
    public void addSuccessful(int position) {
        super.addSuccessful(position);
        shopCount++;
        initCartNum(shopCount);
        mPresenter.getPromotionInfo(promotionIds);
    }


    public boolean isNullList(List list) {
        boolean isNull = true;
        if (list != null && list.size() > 0) {
            isNull = false;
        }
        return isNull;
    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {
        super.setCurrentPrice(bean);
        if (bean.data != null && bean.data.plist != null && bean.data.plist.size() > 0) {
            for (int i = 0; i < searchResultList.size(); i++) {
                for (int j = 0; j < bean.data.plist.size(); j++) {
                    if (bean.data.plist.get(j).mpId.equals(searchResultList.get(i).mpId)) {
                        searchResultList.get(i).price = bean.data.plist.get(j).price;
                        searchResultList.get(i).promotionPrice = bean.data.plist.get(j).promotionPrice;
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
