package com.ody.p2p.check.orderlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.check.R;

/**
 * Created by ${tang} on 2016/8/15.
 */
public class RecommendAdapter extends RecyclerView.Adapter {

    protected class RecommendHolder extends RecyclerView.ViewHolder{
        public RecommendHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcart_item_recommed_girdl,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
