package com.ody.p2p.check.orderoinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.check.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/9/9.
 */
public class LogisticsAdapter extends RecyclerView.Adapter {
    private List<LogisticsBean.DataBean.OrderMessageListBean> mData;
    protected Context context;

    public void setData(List<LogisticsBean.DataBean.OrderMessageListBean> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public LogisticsAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_logisitcs, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        LogisticsBean.DataBean.OrderMessageListBean data = mData.get(position);
        viewHolder.tv_remark.setText(data.message);
        viewHolder.tv_updateTimeStr.setText(data.createTime);
        viewHolder.tv_remark.setTextColor(context.getResources().getColor(R.color.main_title_color));
        showItem(viewHolder,position,mData.size());
    }

    protected void showItem(ViewHolder viewHolder, int position,int size){
        if (position == 0) {
            viewHolder.tv_remark.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.img_icon_background.setBackgroundResource(R.drawable.orderinfo_lin_center);
            viewHolder.img_icon_back.setImageResource(R.drawable.my_order_o);
        } else if (position == size - 1) {
            viewHolder.img_icon_back.setImageResource(R.drawable.my_order_car);
            viewHolder.img_icon_background.setBackgroundResource(R.color.white);
        } else {
            viewHolder.img_icon_back.setImageResource(R.drawable.my_order_o);
            viewHolder.img_icon_background.setBackgroundResource(R.drawable.orderinfo_lin_center);
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_icon_background;
        public ImageView img_icon_back;
        public TextView tv_updateTimeStr;
        public TextView tv_remark;
        public ViewHolder(View itemView) {
            super(itemView);
            img_icon_background = (ImageView) itemView.findViewById(R.id.img_icon_background);
            img_icon_back = (ImageView) itemView.findViewById(R.id.img_icon_back);
            tv_updateTimeStr = (TextView) itemView.findViewById(R.id.tv_updateTimeStr);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
        }
    }
}
