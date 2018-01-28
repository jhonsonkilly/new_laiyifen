package com.ody.p2p.check.bill;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;

/**
 * Created by ${tang} on 2017/1/13.
 */

public class InvoiceProductsAdapter extends RecyclerView.Adapter {
    private ArrayList<String> mData;
    private Context context;
    public InvoiceProductsAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void setData(ArrayList<String> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageview,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewholder= (Holder) holder;
        if(!TextUtils.isEmpty(mData.get(position))){
            GlideUtil.display(context,mData.get(position)).into(viewholder.item_img);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    private class Holder extends RecyclerView.ViewHolder{

        private ImageView item_img;
        public Holder(View itemView) {
            super(itemView);
            this.item_img= (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
