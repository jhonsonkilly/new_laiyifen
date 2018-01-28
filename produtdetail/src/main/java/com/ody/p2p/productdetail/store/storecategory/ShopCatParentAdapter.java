package com.ody.p2p.productdetail.store.storecategory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${tang} on 2017/7/18.
 */

public class ShopCatParentAdapter extends RecyclerView.Adapter {


    private List<ShopCateSubBean.DataEntity.ChildListBean> mData;
    private ItemClickListener itemClickListener;
    private int selectedPosition=0;
    private Context context;

    public ShopCatParentAdapter(Context context){
        mData=new ArrayList<>();
        this.context=context;
    }

    public void setData(List<ShopCateSubBean.DataEntity.ChildListBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_category_parent,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ParentHolder parentHolder= (ParentHolder) holder;
        parentHolder.tv_category_name.setText(mData.get(position).name);
        if(position==selectedPosition){
            parentHolder.tv_category_name.setTextColor(context.getResources().getColor(R.color.theme_color));
        }else {
            parentHolder.tv_category_name.setTextColor(context.getResources().getColor(R.color.black));
        }

        GlideUtil.display(context, mData.get(position).pictureUrl).into(parentHolder.iv_category);

        parentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(mData.get(position));
                int pos=selectedPosition;
                selectedPosition=position;
                notifyItemChanged(position);
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ParentHolder extends RecyclerView.ViewHolder{

        private TextView tv_category_name;
        private ImageView iv_category;

        public ParentHolder(View itemView) {
            super(itemView);
            tv_category_name= (TextView) itemView.findViewById(R.id.tv_category_name);
            iv_category= (ImageView) itemView.findViewById(R.id.iv_category);
        }
    }

    public void setItemClickListener(ItemClickListener clickListener){
        this.itemClickListener=clickListener;
    }

    public interface ItemClickListener{
        void onItemClick(ShopCateSubBean.DataEntity.ChildListBean childListBean);
    }


}
