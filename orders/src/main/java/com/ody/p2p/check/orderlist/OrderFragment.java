package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public class OrderFragment extends BaseFragment implements OrderListView,OrderRecycleViewNewAdapter.OnOrderItemClick {

    protected OrderListPresenter presenter;
    protected OdySwipeRefreshLayout odySwipeRefreshLayout;
    private RecyclerView rlv_order_list;
    protected OrderRecycleViewNewAdapter mAdapter;
    private int TOTAL_SIZE=0;
    private LinearLayoutManager linearLayoutManager;
    private int pageNo=1;
    private int status;
    protected RecyclerView rv_recommend;
    protected RelativeLayout rl_bottom;
    protected List<OrderListBean.DataBean.OrderListItemBean> mData=new ArrayList<>();


    public static Fragment getInstance(int orderStatus,OrderFragment fragment){
        Bundle bundle = new Bundle();
        bundle.putInt("orderStatus",orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new OrderListPresenterImpl(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        status=getArguments().getInt("orderStatus");
        mAdapter.setEmptyView(setOrderEmptyView());
        pageNo = 1;
        setOrderListUrl();
        presenter.orderlist(pageNo,status);
    }


    @Override
    public void initPresenter() {
        presenter=new OrderListPresenterImpl(this);
    }

    @Override
    public void orderlist(OrderListBean orderListBean) {
        if(orderListBean==null||orderListBean.data==null){
            return;
        }
        if(mAdapter==null){
            return;
        }
        if(orderListBean.data.orderList!=null&&orderListBean.data.orderList.size()>0){
            if(pageNo==1){
                TOTAL_SIZE=orderListBean.data.totalCount;
                mAdapter.removeAll();
                mAdapter.addItemTop(orderListBean.data.orderList);
            }else{
                mAdapter.addItemLast(orderListBean.data.orderList);
            }
        }else{
            if(pageNo==1){
                TOTAL_SIZE=orderListBean.data.totalCount;
                mAdapter.removeAll();
            }
        }
    }

    @Override
    public void refreshlist() {
        pageNo = 1;
        presenter.orderlist(pageNo,status);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_order_fragment;
    }

    @Override
    public void initView(View view) {
        rlv_order_list= (RecyclerView) view.findViewById(R.id.rlv_order_list);
        rv_recommend= (RecyclerView) view.findViewById(R.id.rv_recommend);
        rl_bottom= (RelativeLayout) view.findViewById(R.id.rl_bottom);
        odySwipeRefreshLayout= (OdySwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        odySwipeRefreshLayout.setHeaderViewBackgroundColor(0x00ffffff);
        odySwipeRefreshLayout.setTargetScrollWithLayout(true);
        odySwipeRefreshLayout.setOdyDefaultView(true);
    }

    @Override
    public void doBusiness(Context mContext) {
        odySwipeRefreshLayout.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mAdapter.addFooter(false);
                presenter.orderlist(pageNo, status);
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
                    presenter.orderlist(pageNo, status);
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
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv_order_list.setLayoutManager(linearLayoutManager);
        setAdapter();
        mAdapter.setListener(this);
        rlv_order_list.setAdapter(mAdapter);
    }

    /**
     * 设置空界面
     * @return
     */
    protected View setOrderEmptyView(){
        View view;
        if (status == 0) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.order_layout_go_buy, odySwipeRefreshLayout, false);
            TextView goHome = (TextView) view.findViewById(R.id.tv_gomain);
            goHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putInt(Constants.GO_MAIN,0);
                    JumpUtils.ToActivity(JumpUtils.MAIN,bundle);
                }
            });
        } else {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_order_view, odySwipeRefreshLayout, false);
        }
        return view;
    }

    protected void setAdapter(){
        mAdapter=new OrderRecycleViewNewAdapter(mData,getActivity(),presenter);
    }

    /**
     * 用户订单和店铺订单分别用不同的URL
     */
    protected void setOrderListUrl() {
        presenter.setOrderListUrl(Constants.OEDER_LIST);
    }

    /**
     * 跳转至订单详情页
     * @param orderListItemBean
     */
    @Override
    public void onOrderItemClickListener(OrderListBean.DataBean.OrderListItemBean orderListItemBean) {
        Bundle bundle=new Bundle();
        bundle.putString(Constants.ORDER_ID,orderListItemBean.orderCode);
        JumpUtils.ToActivity(JumpUtils.ORDERDETAIL,bundle);
    }
}
