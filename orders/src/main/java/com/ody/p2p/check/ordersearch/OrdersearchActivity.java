package com.ody.p2p.check.ordersearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.check.orderlist.OrderListBean;
import com.ody.p2p.check.orderlist.OrderListPresenterImpl;
import com.ody.p2p.check.orderlist.OrderListView;
import com.ody.p2p.check.orderlist.OrderRecycleViewNewAdapter;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.MessageUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.campusapp.router.annotation.RouterMap;


/**
 * 搜索界面
 */
@RouterMap("activity://ordersearch")
public class OrdersearchActivity extends BaseActivity implements View.OnClickListener, OrderListView, OrderRecycleViewNewAdapter.OnOrderItemClick {
    public RecyclerView rv_search_list;
    public EditText et_search_keywords;
    public RelativeLayout search_keywords_bg;
    public ImageView iv_left_icon;
    public TextView tv_search;
    public ImageView iv_more;
    private int pageNo = 1;
    protected OrderListPresenterImpl presenter;
    protected OrderRecycleViewNewAdapter mAdapter;

    protected List<OrderListBean.DataBean.OrderListItemBean> mData = new ArrayList<>();
    private int TOTAL_SIZE = 0;
    private OdySwipeRefreshLayout odySwipeRefreshLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_order_search;
    }

    @Override
    public void initView(View view) {
        rv_search_list = (RecyclerView) view.findViewById(R.id.rlv_order_list);
        et_search_keywords = (EditText) view.findViewById(R.id.et_search_keywords);
        search_keywords_bg = (RelativeLayout) view.findViewById(R.id.search_keywords_bg);
        iv_left_icon = (ImageView) view.findViewById(R.id.iv_left_icon);
        iv_left_icon.setOnClickListener(this);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        LinearLayoutManager mZDLayoutManager = new LinearLayoutManager(this);
        mZDLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search_list.setLayoutManager(mZDLayoutManager);
        odySwipeRefreshLayout = (OdySwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        odySwipeRefreshLayout.setHeaderViewBackgroundColor(0x00ffffff);
        odySwipeRefreshLayout.setTargetScrollWithLayout(true);
        odySwipeRefreshLayout.setOdyDefaultView(true);
    }

    protected void setAdapter(){
        mAdapter = new OrderRecycleViewNewAdapter(mData, this, presenter);
    }

    protected String getSearchurl(){
        return Constants.SEARCH_ORDER_LIST;
    }
    @Override
    public void initListener() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(et_search_keywords.getText().toString())) {
            String keyword = et_search_keywords.getText().toString();
            pageNo=1;
            initOrderList(keyword);
        }
    }

    @Override
    public void initPresenter() {
        presenter = new OrderListPresenterImpl(this);

    }

    @Override
    public void doBusiness(Context mContext) {
        odySwipeRefreshLayout.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mAdapter.addFooter(false);
                initOrderList(et_search_keywords.getText().toString());
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean b) {

            }
        });

        odySwipeRefreshLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mAdapter.getItemCount() < TOTAL_SIZE) {
                    pageNo++;
                    initOrderList(et_search_keywords.getText().toString());
                } else {
                    mAdapter.addFooter(true);
                    odySwipeRefreshLayout.setLoadMore(false);
                }
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean b) {

            }
        });

        et_search_keywords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    if (!StringUtils.isEmpty(et_search_keywords.getText().toString())) {
                        String keyword = et_search_keywords.getText().toString();
                        pageNo=1;
                        initOrderList(keyword);
                    }
                }
                return false;
            }
        });
        setAdapter();
        mAdapter.setListener(this);
        rv_search_list.setAdapter(mAdapter);
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(iv_left_icon)) {//搜索框左侧向左箭头
            hideSoftInput(et_search_keywords);
            finish();
        }
        if (v.equals(tv_search)) {//搜索框右侧"搜索"
            if (!StringUtils.isEmpty(et_search_keywords.getText().toString())) {
                String keyword = et_search_keywords.getText().toString();
                pageNo=1;
                initOrderList(keyword);
            }
        }
        if (v.equals(iv_more)) {//搜索框右侧三个点
            MessageUtil.setMegScan(getContext(), iv_more);
        }
    }

    /**
     * 获取订单搜索结果列表
     *
     * @param keyword
     */
    private void initOrderList(String keyword) {
        if(TextUtils.isEmpty(keyword)){
            ToastUtils.showToast(getString(R.string.please_into_keyword));
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("pageNo", pageNo + "");
        params.put("pageSize", 10 + "");
        params.put("keyword", keyword);
        showLoading();
        OkHttpManager.getAsyn(getSearchurl(), params, new OkHttpManager.ResultCallback<OrderListBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(OrderListBean response) {
                if (response!=null&&response.code.equals("0")) {
                    if (response != null && response.data != null && response.data.data != null && response.data.data.size() > 0) {
                        if (pageNo == 1) {
                            TOTAL_SIZE = response.data.totalCount;
                            mAdapter.removeAll();
                            mAdapter.addItemTop(response.data.data);
                        } else {
                            mAdapter.addItemLast(response.data.data);
                        }
                    } else {
                        if (pageNo == 1) {
                            TOTAL_SIZE = response.data.totalCount;
                            mAdapter.removeAll();
                            ToastUtils.showToast(getString(R.string.you_no_correlation_order_));
                        }
                    }
                }
            }
        });
    }


    //隐藏edittext弹出的软键盘
    public void hideSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @Override
    public void orderlist(OrderListBean orderListBean) {

    }

    @Override
    public void refreshlist() {
        if (!StringUtils.isEmpty(et_search_keywords.getText().toString())) {
            String keyword = et_search_keywords.getText().toString();
            pageNo=1;
            initOrderList(keyword);
        }
    }

    @Override
    public void onOrderItemClickListener(OrderListBean.DataBean.OrderListItemBean orderListItemBean) {
        Bundle bundle=new Bundle();
        bundle.putString(Constants.ORDER_ID,orderListItemBean.orderCode);
        JumpUtils.ToActivity(JumpUtils.ORDERDETAIL,bundle);
    }
}
