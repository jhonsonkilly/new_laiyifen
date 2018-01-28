package com.ody.p2p.productdetail.store.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.productdetail.store.bean.StoreActivityInfo;
import com.ody.p2p.produtdetail.R;

import java.util.List;

/**
 * Created by hanzhifengyun on 2017/7/10.
 */

public class StoreActivityAdapter extends RecyclerView.Adapter<StoreActivityAdapter.ViewHolder> {

    private Context mContext;
    private List<StoreActivityInfo.DataBean.ListObjBean> mStoreActivityInfoList;

    public StoreActivityAdapter(Context context, List<StoreActivityInfo.DataBean.ListObjBean> storeActivityInfoList) {
        mContext = context;
        mStoreActivityInfoList = storeActivityInfoList;
    }

    @Override
    public StoreActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(
                LayoutInflater.from(mContext).inflate(
                        R.layout.item_list_store_activity, null, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(StoreActivityAdapter.ViewHolder holder, int position) {
        StoreActivityInfo.DataBean.ListObjBean listObjBean = mStoreActivityInfoList.get(position);
        holder.mTvTitle.setText(listObjBean.getPromTitle());
        holder.mTvContent.setText(Html.fromHtml(listObjBean.getDescription()));
    }

    @Override
    public int getItemCount() {
        if (mStoreActivityInfoList == null) {
            return 0;
        }
        return mStoreActivityInfoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

