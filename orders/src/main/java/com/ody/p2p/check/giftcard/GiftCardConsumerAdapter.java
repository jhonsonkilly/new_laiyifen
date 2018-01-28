package com.ody.p2p.check.giftcard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/10/18.
 */
public class GiftCardConsumerAdapter extends RecyclerView.Adapter {

    private List<GiftCardConsumerBean.DataBean> mData;
    private Context context;
    private ItemClickListener listener;
    public GiftCardConsumerAdapter(Context context){
        mData=new ArrayList<>();
        this.context=context;
    }

    public void addData(List<GiftCardConsumerBean.DataBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    public void setClicklistener(ItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giftcard_consumer_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.tv_date.setText(mData.get(position).userTimeStr);
        viewHolder.tv_money.setText("-"+context.getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(mData.get(position).monetary));
        viewHolder.tv_product_num.setText(context.getString(R.string.common) + mData.get(position).num + context.getString(R.string.piece)+context.getString(R.string.product));
        if(mData.get(position).productList!=null&&mData.get(position).productList.size()>0){
            for(int i=0;i<mData.get(position).productList.size();i++){
                    if(i==0&& !TextUtils.isEmpty(mData.get(position).productList.get(i).picUrl)){
                        GlideUtil.display(context, mData.get(position).productList.get(i).picUrl).into(viewHolder.img1);
                    }
                    if(i==1&& !TextUtils.isEmpty(mData.get(position).productList.get(i).picUrl)){
                        GlideUtil.display(context, mData.get(position).productList.get(i).picUrl).into(viewHolder.img2);
                    }
                    if(i==2&& !TextUtils.isEmpty(mData.get(position).productList.get(i).picUrl)){
                        GlideUtil.display(context, mData.get(position).productList.get(i).picUrl).into(viewHolder.img3);
                    }
            }
        }
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public interface ItemClickListener{
        void onItemClick(GiftCardConsumerBean.DataBean bean);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_money;
        private TextView tv_date;
        private TextView tv_product_num;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date= (TextView) itemView.findViewById(R.id.tv_date);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            tv_product_num= (TextView) itemView.findViewById(R.id.tv_product_num);
            img1= (ImageView) itemView.findViewById(R.id.img1);
            img2= (ImageView) itemView.findViewById(R.id.img2);
            img3= (ImageView) itemView.findViewById(R.id.img3);
            rl_item= (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }
}
