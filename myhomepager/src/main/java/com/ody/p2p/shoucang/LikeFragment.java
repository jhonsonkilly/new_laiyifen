package com.ody.p2p.shoucang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.JumpUtils;

/**
 * Created by ody on 2016/8/17.
 */
public class LikeFragment extends BaseFragment implements likeview, View.OnClickListener, LikeProdutAdapter.Adapteroclik {
    public RecyclerView rv_like;
    public LikeProdutAdapter adapter;
    private likeImpl presenter;
    private LinearLayout lay_null;
    private TextView tv_goshop;
    private LinearLayoutManager manager;
    private TextView tv_delete;
    int pageNum = 1;
    private int btnResId = 0;
    private int btnTextColor = 0;

    @Override
    public void initPresenter() {

        presenter = new likeImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.myhomepager_like_fragment;
    }

    @Override
    public void initView(View view) {
        rv_like = (RecyclerView) view.findViewById(R.id.rv_homepage_like);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_like.setLayoutManager(manager);
        lay_null = (LinearLayout) view.findViewById(R.id.lay_null);
        tv_goshop = (TextView) view.findViewById(R.id.txt_goshopp);
        tv_delete = (TextView) view.findViewById(R.id.tv_like_delete);
        tv_delete.setVisibility(View.GONE);
        if (btnResId != 0) {
            tv_delete.setBackgroundResource(btnResId);
        }
        if (btnTextColor != 0) {
            tv_delete.setTextColor(btnTextColor);
        }

        initEvent();

    }

    private void initEvent() {
        tv_goshop.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.selectlikeprodut(pageNum);

    }

    @Override
    public void likeproct(MyShouCangListBean response) {
        if (null != response.getData().getData()) {
            lay_null.setVisibility(View.GONE);
            rv_like.setVisibility(View.VISIBLE);
            manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_like.setLayoutManager(manager);
            adapter = new LikeProdutAdapter(getContext(), response.getData().getData());
            adapter.setAdapteroclik(this);
            rv_like.setAdapter(adapter);
        } else {
            lay_null.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refsh() {
        presenter.selectlikeprodut(pageNum);
        if (mListener != null) {
            mListener.deleted();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.equals(tv_goshop)) {
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://main");
//            activityRoute.withParams(Constants.GO_MAIN, 0).open();

            Bundle bd = new Bundle();
            bd.putInt(Constants.GO_MAIN, 0);
            JumpUtils.ToActivity(JumpUtils.MAIN, bd);
        }
        if (v.equals(tv_delete)) {
            presenter.del(adapter.getSelectId());
        }

    }

    @Override
    public void delectItem(String id) {

    }

    @Override
    public void del(MyShouCangListBean.Data.ShouCangData data, int postion, String ids) {
//        String str = adapter.getSelectId();
//        if (StringUtils.isEmpty(ids)) {
//            presenter.del(ids);
//        }

    }

    @Override
    public void showDeleteBtn(int size) {
        tv_delete.setVisibility(View.VISIBLE);
        tv_delete.setText(getString(R.string.delete) + "(" + size + ")");
    }

    public void hideDeleteBtn() {
        tv_delete.setVisibility(View.GONE);
        tv_delete.setText(getString(R.string.delete));

    }


    public void showDelete() {
        if (rv_like.getVisibility() == View.VISIBLE) {
            adapter.setStatus(0);
        }
    }

    public void setBtnResId(int resId) {
        btnResId = resId;
    }

    public void setBtnTextColor(int color) {
        btnTextColor = color;
    }


    public interface onDeltedSucced {
        void deleted();
    }

    private onDeltedSucced mListener;

    public void setOnDeleteListener(onDeltedSucced listener) {
        this.mListener = listener;
    }
}
