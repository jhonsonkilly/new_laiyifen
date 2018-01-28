package com.ody.p2p.shoucang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MylikeActivity extends BaseActivity implements View.OnClickListener,TabTitleAdapter.onItemSelectedListener,likeview,LikeProdutAdapter.Adapteroclik{
    private ImageView img_back;
    public TextView tv_right;
    public TextView tv_title;
    public View view_top_line;
    private TextView tv_goshop;
    public TabTitleAdapter titleAdapter;
    public LikeProdutAdapter adapter;
    public List<String> mTitles ;
    public RecyclerView rv_title;
    public RecyclerView rv_like;
    private LinearLayoutManager manager;
    private LinearLayoutManager manager2;
    private List<MyShouCangListBean.Data.ShouCangData> mData;

    private likeImpl presenter;
    private LinearLayout lay_null;
    private FrameLayout lay_content;
    public TextView tv_delete;
    int pageNum = 1;
    private OdySwipeRefreshLayout lay_refresh;

    @Override
    public int bindLayout() {
        return R.layout.activity_mylike;
    }

    @Override
    public void initView(View view) {
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tv_title = (TextView) view.findViewById(R.id.txt_title);
        lay_null = (LinearLayout) view.findViewById(R.id.lay_null);
        tv_goshop = (TextView) view.findViewById(R.id.txt_goshopp);
        lay_content = (FrameLayout) view.findViewById(R.id.lay_like_content);
        tv_delete = (TextView) view.findViewById(R.id.tv_like_delete);

        rv_title = (RecyclerView) view.findViewById(R.id.rv_like_title);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_title.setLayoutManager(manager);
        tv_right = (TextView) view.findViewById(R.id.tv_like_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(R.string.editor);
        mTitles = new ArrayList<>();
        titleAdapter = new TabTitleAdapter(this,mTitles);
        titleAdapter.setOnIemSelectedListener(this);

        rv_title.setAdapter(titleAdapter);
        view_top_line = view.findViewById(R.id.view_top_line);
        view_top_line.setVisibility(View.GONE);

        rv_like = (RecyclerView) view.findViewById(R.id.rv_like_content);
        manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        rv_like.setLayoutManager(manager2);
        mData = new ArrayList<MyShouCangListBean.Data.ShouCangData>();
        adapter = new LikeProdutAdapter(this,mData);
        adapter.setAdapteroclik(this);
        rv_like.setAdapter(adapter);

        lay_refresh = (OdySwipeRefreshLayout) view.findViewById(R.id.lay_like_refresh);
        lay_refresh.setHeaderViewBackgroundColor(0x00ffffff);
        lay_refresh.setTargetScrollWithLayout(true);
        lay_refresh.setOdyDefaultView(true);


        initEvent();

        setAdapter();
    }

    private void initEvent() {
        tv_right.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_goshop.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        lay_refresh.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                presenter.selectlikeprodut(pageNum);
                resetPage();
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });

        lay_refresh.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum ++;
                presenter.selectlikeprodut(pageNum);
                resetPage();
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });

    }

    public void setAdapter() {


    }

    @Override
    public void initPresenter() {
        presenter = new likeImpl(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.img_back){
            finish();
        }
        if(v.equals(tv_right)){
            if(tv_right.getText().toString().equals(getString(R.string.editor))){
                showDeleteAdapter();
                tv_right.setText(R.string.complete);
            }else if(tv_right.getText().toString().equals(getString(R.string.complete))){
                resetPage();
            }

        }
        if(v.equals(tv_goshop)){
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://main");
//            activityRoute.withParams(Constants.GO_MAIN, 0).open();

            Bundle bd=new Bundle();
            bd.putInt(Constants.GO_MAIN,0);
            JumpUtils.ToActivity(JumpUtils.MAIN,bd);
        }
        if(v.equals(tv_delete)){
            presenter.del(adapter.getSelectId());
        }
    }

    public void showDeleteAdapter(){
        adapter.setStatus(0);
    }
    @Override
    public void doBusiness(Context mContext) {
        presenter.selectlikeprodut(pageNum);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onItemSelected(int position) {
        titleAdapter.setChecked(position);

    }


    @Override
    public void likeproct(MyShouCangListBean response) {
        if (null != response.getData().getData()) {
            lay_null.setVisibility(View.GONE);
            lay_content.setVisibility(View.VISIBLE);
            if(pageNum == 1) {
                mData = response.getData().getData();
            }else {
                mData.addAll(response.getData().getData());
            }
            adapter.setDatas(mData);
        } else {
            if(pageNum == 1) {
                lay_null.setVisibility(View.VISIBLE);
                lay_content.setVisibility(View.GONE);
            }else {
                ToastUtils.showStr(getString(R.string.no_more_product));
            }
        }
    }

    @Override
    public void refsh() {
        presenter.selectlikeprodut(pageNum);
        resetPage();

    }

    @Override
    public void del(MyShouCangListBean.Data.ShouCangData data, int postion, String ids) {

    }

    @Override
    public void showDeleteBtn(int size) {
        tv_delete.setVisibility(View.VISIBLE);
        tv_delete.setText(getString(R.string.delete) + "(" + size + ")");
    }

    public void resetPage(){
        adapter.setStatus(1);
        tv_delete.setVisibility(View.GONE);
        tv_right.setText(R.string.editor);
    }

    @Override
    public void delectItem(String id) {

    }
}
