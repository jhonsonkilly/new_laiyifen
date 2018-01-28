package com.lyfen.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laiyifen.lyfframework.base.BaseFragment;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.listener.OnBothRefreshListener;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.laiyifen.lyfframework.views.LoadingPage;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.entity.network.activity.OrderListEntity;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.activity.order.OrderDetailActivity;
import com.lyfen.android.ui.adapter.activity.OrderAdapter;
import com.ody.p2p.main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observer;


/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class MyOrderListFragment extends BaseFragment {
    int mPosition;
    LoadingPage mLoappager;
    RefreshRecyclerView mRefreshRecyclerView;
    private OrderApi beanOfClass;
    List<OrderListEntity.DataEntity.OrderListEntity2> orderListEntity2s;

    public static Fragment newInstance(String s, int position) {
        MyOrderListFragment likeFragment = new MyOrderListFragment();
        likeFragment.mPosition = position;


        return likeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View inflate = View.inflate(getActivity(), R.layout.refresh_recyclerview, null);
        mRefreshRecyclerView = (RefreshRecyclerView) inflate.findViewById(R.id.id_recyclerview);

        beanOfClass = RestRetrofit.getBeanOfClass(OrderApi.class);

        orderListEntity2s = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.layout_loading_pager, null);
        return inflate;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoappager = (LoadingPage) view.findViewById(R.id.common_loadpager);


    }

    int paget = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData(paget);
    }

    @Override
    protected void onFragmentShowed() {
        super.onFragmentShowed();

//        initData(paget);

    }

    public void initData(int pageNo) {
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("ut", LoginHelper.getUt());
        stringStringMap.put("companyId", 30 + "");
        if (mPosition == 5) {
            stringStringMap.put("orderStatus", 8 + "");
        } else if (mPosition == 6) {
//            stringStringMap.put("orderStatus", 20 + "");

        } else if (mPosition == 7) {

        } else {
            stringStringMap.put("orderStatus", mPosition + "");
        }

        stringStringMap.put("pageNo", pageNo + "");
        stringStringMap.put("nocache", System.currentTimeMillis() + "");
        stringStringMap.put("pageSize", 10 + "");
        stringStringMap.put("sysSource", "ody");


        beanOfClass.getOrderList(stringStringMap).subscribe(this::success, this::erro);


    }

    private void erro(Throwable throwable) {
        mLoappager.showPage(LoadingPage.LoadResult.EMPTY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(1);
            }
        });
    }

    OrderAdapter shangouAdapter;

    private void success(OrderListEntity orderListEntity) {


        if (null == shangouAdapter) {
            if (null != orderListEntity && null != orderListEntity.data && null != orderListEntity.data.orderList && orderListEntity.data.orderList.size() > 0) {
                shangouAdapter = new OrderAdapter(mPosition, this);
                orderListEntity2s.clear();
                orderListEntity2s.addAll(orderListEntity.data.orderList);
                shangouAdapter.setData(orderListEntity2s);

                RecyclerViewManager.with(shangouAdapter, new LinearLayoutManager(getActivity()))
                        .setMode(RecyclerMode.BOTH)
                        .setOnItemClickListener((holder, position) -> {
                            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                            intent.putExtra("OrderId", orderListEntity2s.get(position).orderCode);
                            getActivity().startActivity(intent);
                        })

                        .setOnBothRefreshListener(new OnBothRefreshListener() {
                            @Override
                            public void onPullDown() {
                                orderListEntity2s.clear();
                                paget = 1;
                                initData(paget);
                            }

                            @Override
                            public void onLoadMore() {
                                paget++;
                                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                                stringStringMap.put("ut", LoginHelper.getUt());
                                stringStringMap.put("companyId", 30 + "");
                                stringStringMap.put("orderStatus", mPosition + "");
                                stringStringMap.put("pageNo", paget + "");
                                stringStringMap.put("nocache", System.currentTimeMillis() + "");
                                stringStringMap.put("pageSize", 10 + "");
                                stringStringMap.put("sysSource", "ody");
                                beanOfClass.getOrderList(stringStringMap).subscribe(new Observer<OrderListEntity>() {
                                    @Override
                                    public void onCompleted() {
                                        mRefreshRecyclerView.onRefreshCompleted();
                                        shangouAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mRefreshRecyclerView.onRefreshCompleted();
                                        shangouAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onNext(OrderListEntity orderListEntity) {
                                        orderListEntity2s.addAll(orderListEntity.data.orderList);
                                        shangouAdapter.setData(orderListEntity2s);
                                        shangouAdapter.notifyDataSetChanged();

                                    }
                                });


                            }
                        }).into(mRefreshRecyclerView, getActivity());

                mLoappager.showPage(LoadingPage.LoadResult.SUCCEED, mRefreshRecyclerView);
            } else {
                mLoappager.showPage(LoadingPage.LoadResult.EMPTY).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initData(1);

                    }
                });
            }

        } else {
            orderListEntity2s.addAll(orderListEntity.data.orderList);
            shangouAdapter.setData(orderListEntity2s);
            mRefreshRecyclerView.onRefreshCompleted();
            shangouAdapter.notifyDataSetChanged();


        }

    }

    public void removeIndex(OrderListEntity.DataEntity.OrderListEntity2 orderCode) {
        try {
            for (int i = 0; i < orderListEntity2s.size(); i++) {
                if (orderListEntity2s.contains(orderCode)) {
                    orderListEntity2s.remove(orderCode);

                }

            }
            shangouAdapter.setData(orderListEntity2s);
            mRefreshRecyclerView.onRefreshCompleted();
            shangouAdapter.notifyDataSetChanged();
        } catch (Exception w) {

        }


    }

}
