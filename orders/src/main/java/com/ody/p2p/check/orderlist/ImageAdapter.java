package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/19.
 */
public class ImageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> pics;

    public ImageAdapter(Context context,List<String> pics){
        this.context=context;
        this.pics=pics;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_imageview,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImgViewHolder viewHolder= (ImgViewHolder) holder;
        if(!TextUtils.isEmpty(pics.get(position))){
            GlideUtil.display(context, pics.get(position)).into(viewHolder.item_img);
        }
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }
    private class ImgViewHolder extends RecyclerView.ViewHolder{
        private ImageView item_img;
        public ImgViewHolder(View itemView) {
            super(itemView);
            item_img= (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
