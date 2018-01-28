package com.ody.p2p.RefoundList;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;

import java.util.List;

public class RefoundListActivity extends BaseActivity implements RefoundListView, View.OnClickListener {
    RecyclerView recycle_refound;
    SwipeRefreshLayout swip_refresh;
    RefoundListPressenter mPressenter;
    LinearLayoutManager mLayoutManager;
    int lastVisibleItem = 0;
    RefoundListAdapter adapter;
    ImageView img_finish;
    ImageView more;
    int pageNum = 1;

    @Override
    public void initPresenter() {
        mPressenter = new RefoundListImrp(this);
        mPressenter.initRefoundList(pageNum);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_refound_list;
    }

    @Override
    public void initView(View view) {
        recycle_refound = (RecyclerView) view.findViewById(R.id.recycle_refound);
        swip_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swip_refresh);
        more = (ImageView) view.findViewById(R.id.more);
        img_finish = (ImageView) view.findViewById(R.id.img_finish);

        more.setOnClickListener(this);
        img_finish.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mPressenter.initRefoundList(pageNum);
            }
        });

        recycle_refound.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    mPressenter.initRefoundList(++pageNum);
                    // 下拉加载更多，sendRequest ....
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    @Override
    public void getListData(List<RefoundBaseBean> mData) {
        swip_refresh.setRefreshing(false);
        if (null == adapter) {
            adapter = new RefoundListAdapter(getContext(), mData);
            mLayoutManager = new LinearLayoutManager(getContext());
            recycle_refound.setLayoutManager(mLayoutManager);
            recycle_refound.setAdapter(adapter);
        }
        if (pageNum == 1) {
            adapter.setListData(mData);
        } else {
            adapter.addListData(mData);
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.more) {
//            Intent intent = new Intent(getContext(), RefoundInfoActivity.class);
////        intent.putExtra("orderCoder", data.getOrderCode());
//            startActivity(intent);
        } else if (v.getId() == R.id.img_finish) {
            finish();
        }

    }
}
