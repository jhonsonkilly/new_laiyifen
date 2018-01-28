package com.ody.p2p.classesification;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.classesification.Bean.MultiCategory;
import com.ody.p2p.recycleviewutils.DecorationSpace;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.category.Category;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.webactivity.WebActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements CategoryView {

    private RecyclerView parentRv;//一级列表
    private RecyclerView subRv;//二三级列表
    private CategoryPresentImpl categoryPresent;
    private ParentCategoryAdapter adapter;
    private SubCategoryAdapter subAdapter;
    private RelativeLayout locationRl;
    private TextView locationTxt;
    protected ImageView scanImg;
    private ImageView messageImg;
    private TextView hintTxt;
    private LinearLayout searchLin;
    private TextView redFlag;
    private boolean move;
    private int mIndex;

    @Override
    public void initPresenter() {
        categoryPresent = new CategoryPresentImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void initView(View view) {
        redFlag = (TextView) view.findViewById(R.id.redFlag);
        parentRv = (RecyclerView) view.findViewById(R.id.parentRv);
        subRv = (RecyclerView) view.findViewById(R.id.subRv);
        scanImg = (ImageView) view.findViewById(R.id.scan);
        searchLin = (LinearLayout) view.findViewById(R.id.search);

        locationRl = (RelativeLayout) view.findViewById(R.id.locationRl);
        locationTxt = (TextView) view.findViewById(R.id.location);
        messageImg = (ImageView) view.findViewById(R.id.message);
        hintTxt = (TextView) view.findViewById(R.id.hint);
        adapter = new ParentCategoryAdapter();
        parentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        parentRv.setAdapter(adapter);

        subAdapter = new SubCategoryAdapter();
        subRv.setLayoutManager(RecycleUtils.getGridLayoutManager(getContext(), 3));
        subAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = subAdapter.getData().get(position).itemType;
                if (type == MultiCategory.TYPE_SELECTION || type == MultiCategory.TYPE_AD) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        subRv.addItemDecoration(new DecorationSpace(PxUtils.dipTopx(5)));
        subRv.setAdapter(subAdapter);

        addListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationTxt.setText(OdyApplication.getString(Constants.PROVINCE, ""));
        categoryPresent.getMsgSummary();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            categoryPresent.getMsgSummary();
            locationTxt.setText(OdyApplication.getString(Constants.PROVINCE, ""));
            if (!NetworkUtil.isNetworkAvailable(getContext())) {
                showFailed(true, 1, 55);
            }
        } else {
            showFailed(false, 1);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void doBusiness(Context mContext) {
        categoryPresent.getCategory(0, 1);
        categoryPresent.getSearchWord();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1, 55);
        }
    }

    @Override
    public void setParentCategory(List<Category> list) {
        if (list != null && list.size() > 0) {
            adapter.setNewData(list);
            showFailed(false, 1);
        } else {
            showFailed(true, 1, 55);
        }
    }

    @Override
    public void setSubCategory(List<MultiCategory> list) {
        subAdapter.setNewData(list);
    }

    @Override
    public void setCategoryAd(Ad ad) {
        MultiCategory mAd = new MultiCategory();
        mAd.itemType = MultiCategory.TYPE_AD;
        mAd.ad = ad;
        subAdapter.getData().add(0, mAd);
        subAdapter.setNewData(subAdapter.getData());
    }

    @Override
    public void refreshAdapter(int index) {
        adapter.notifyDataSetChanged();
        moveToPosition(index);
//        parentRv.smoothScrollToPosition(index);
//        ((LinearLayoutManager)parentRv.getLayoutManager()).scrollToPositionWithOffset(index, 0);
    }

    @Override
    public void setHintValue(String hintValue) {
        hintTxt.setText(hintValue);
    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void setUnReadCount(int count) {
//        if (count > 0) {
//            redFlag.setVisibility(View.VISIBLE);
//            redFlag.setText(String.valueOf(count));
//        } else {
//            redFlag.setVisibility(View.GONE);
//        }
        UiUtils.setCareNum(count, 1, redFlag);
    }

    protected void addListener() {
        parentRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                categoryPresent.click(baseQuickAdapter.getData(), i);
            }
        });

        subRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (subAdapter == null || subAdapter.getData() == null || subAdapter.getData().size() < i)
                    return;
                MultiCategory item = subAdapter.getData().get(i);
                if (item == null) return;

                //广告跳转
                if (subAdapter.getData().get(i).itemType == MultiCategory.TYPE_AD) {
                    JumpUtils.toActivity(subAdapter.getData().get(i).ad);
                    return;
                }
                if (subAdapter.getData().get(i).category.categoryId < 0) {
                    JumpUtils.toActivity(subAdapter.getData().get(i).ad);
                    return;
                }
                //普通类别跳转
                Bundle extra = new Bundle();
                extra.putString(Constants.NAVCATEGORY_NAME, item.category.categoryName);
                extra.putString(Constants.NAVCATEGORY_ID, String.valueOf(item.category.categoryId));
                JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, extra);
            }
        });
        parentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - ((LinearLayoutManager) parentRv.getLayoutManager()).findFirstVisibleItemPosition();
                    if (0 <= n && n < parentRv.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = parentRv.getChildAt(n).getTop();
                        //最后的移动
                        parentRv.smoothScrollBy(0, top);
                    }
                }
            }
        });

        locationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.ToActivity(JumpUtils.LOCATION);
            }
        });

        scanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(getContext());
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
                        });
            }
        });

        messageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/message/message-center.html", WebActivity.NO_TITLE, 0, "");
            }
        });

        searchLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.ToActivity(JumpUtils.SEARCH);
            }
        });
    }

    @Override
    protected void loadAgain() {
        super.loadAgain();
        categoryPresent.getCategory(0, 1);
        showFailed(false, 1);
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1, 55);
        }
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = ((LinearLayoutManager) parentRv.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) parentRv.getLayoutManager()).findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            parentRv.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = parentRv.getChildAt(n - firstItem).getTop();
            parentRv.smoothScrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            parentRv.smoothScrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }
}
