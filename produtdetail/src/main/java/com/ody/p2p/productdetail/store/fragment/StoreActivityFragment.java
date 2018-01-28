package com.ody.p2p.productdetail.store.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.retrofit.store.StoreActivityBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

/**
 * Created by meijunqiang on 2017/6/23.
 * 描述:店铺主页--》店铺活动
 */

public class StoreActivityFragment extends BaseFragment implements StoreActivityView {

    private RecyclerView recyclerView;
    public static StoreActivityAdapter storeActivityAdapter;

    private StoreActivityPresenter storeActivityPresenter;
    protected static String merchantId;

    protected static LinearLayout ll_footer;
    protected static LinearLayout ll_loading;
    protected static TextView tv_no_more;

    protected static Integer currentPage = 1;
    protected static Integer countTotal = 0;


    @Override
    public int bindLayout() {
        return R.layout.fragment_store_activity;
    }

    @Override
    public void initView(View view) {
        merchantId = (String) getContext().getIntent().getExtras().get("merchantId");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_store_activity);
        ll_footer = (LinearLayout) view.findViewById(R.id.ll_footer);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        tv_no_more = (TextView) view.findViewById(R.id.tv_no_more);

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    @Override
    public void initPresenter() {
        storeActivityPresenter = new StoreActivityPresenterImpl(this, merchantId);
    }

    @Override
    public void onResume() {
        super.onResume();
        storeActivityPresenter.getAllStoreActivity(currentPage);
        storeActivityPresenter.getCount();
    }

    protected static void updateData(StoreActivityBean storeActivityBean) {
        storeActivityAdapter.addData(storeActivityBean.getData().getListObj());
        storeActivityAdapter.notifyDataSetChanged();
    }

    @Override
    public void initStoreActivity(StoreActivityBean storeActivityBean) {
        storeActivityAdapter = new StoreActivityAdapter(getContext(), storeActivityBean.getData().getListObj());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(storeActivityAdapter);

        storeActivityAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<StoreActivityBean.DataBean.ListObjBean>() {
            @Override
            public void onItemClick(View view, int position, StoreActivityBean.DataBean.ListObjBean model) {
                // 跳转搜索界面
//                Bundle bundle = new Bundle();
//                bundle.putString("keyword", model.getPromTitle());
//                JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bundle);

                Bundle bd = new Bundle();
                bd.putString(Constants.PRO_ID, model.getPromotionId() + "");
                JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bd);
            }

            @Override
            public void onItemLongClick(View view, int position, StoreActivityBean.DataBean.ListObjBean model) {

            }
        });
    }

    @Override
    public void getCount(StoreActivityCountBean storeActivityCountBean) {
        countTotal = storeActivityCountBean.getData().getTotal();
        if (storeActivityCountBean != null) {
            storeActivityCountListener.storeActivityCount(countTotal);
        }
    }

    static class StoreActivityAdapter extends BaseRecyclerViewAdapter<StoreActivityBean.DataBean.ListObjBean> {
        private Context context;
        private List<StoreActivityBean.DataBean.ListObjBean> datas;

        public StoreActivityAdapter(Context context, List<StoreActivityBean.DataBean.ListObjBean> datas) {
            super(context, datas);
            this.context = context;
            this.datas = datas;
        }

        public void addData(List<StoreActivityBean.DataBean.ListObjBean> data) {
            datas.addAll(data);
        }

        @Override
        protected void showViewHolder(BaseRecyclerViewHolder holders, int position) {
            viewHolder holder = (viewHolder) holders;
            StoreActivityBean.DataBean.ListObjBean listObjBean = datas.get(position);
            holder.tv_title.setText(listObjBean.getPromTitle());
            holder.tv_content.setText(listObjBean.getDescription().replaceAll("<[^>]*>", ""));

        }

        @Override
        protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
            return new viewHolder(mInflater.inflate(R.layout.item_list_store_activity, parent, false));
        }

        class viewHolder extends BaseRecyclerViewHolder {

            TextView tv_title;
            TextView tv_content;

            public viewHolder(View itemView) {
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }

    StoreActivityCountListener storeActivityCountListener;

    public interface StoreActivityCountListener {
        void storeActivityCount(int count);
    }

    public void setStoreActivityCountListener(StoreActivityCountListener storeActivityCountListener) {
        this.storeActivityCountListener = storeActivityCountListener;
    }

}
