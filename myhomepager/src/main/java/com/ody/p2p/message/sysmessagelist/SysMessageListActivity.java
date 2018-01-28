package com.ody.p2p.message.sysmessagelist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.util.List;

public class SysMessageListActivity extends BaseActivity implements SysMessageListView {
    public RecyclerView recycle_system_inform;
    ImageView img_finish_icon;
    OdySwipeRefreshLayout swipe_re;
    SysMessageListPressenter mPressenter;
    int pagNo = 1;
    SysMessageListAdapter adapter;

    @Override
    public void initPresenter() {
        mPressenter = new SysMessageListPressenterImpr(this);
        mPressenter.getMsgList(pagNo);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initView(View view) {
        swipe_re = (OdySwipeRefreshLayout) view.findViewById(R.id.swipe_re);
        recycle_system_inform = (RecyclerView) view.findViewById(R.id.recycle_system_inform);
        img_finish_icon = (ImageView) view.findViewById(R.id.img_finish_icon);
        swipe_re.setOdyDefaultView(true);
        img_finish_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipe_re.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pagNo++;
                mPressenter.getMsgList(pagNo);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {
            }
        });
        swipe_re.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pagNo = 1;
                mPressenter.getMsgList(pagNo);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        recycle_system_inform.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getSysMessageBean(List<SysMessageListBean.Dataes> mdata) {
        if (null == adapter) {
            adapter = new SysMessageListAdapter(getContext(), mdata);
            recycle_system_inform.setAdapter(adapter);
            return;
        }
        if (pagNo == 1) {
            adapter.setDatas(mdata);
        } else {
            adapter.addItemLast(mdata);
        }

    }
}
