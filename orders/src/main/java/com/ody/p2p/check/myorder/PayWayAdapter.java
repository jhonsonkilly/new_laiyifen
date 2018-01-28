package com.ody.p2p.check.myorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tangmeijuan on 16/6/16.
 */
public class PayWayAdapter extends BaseAdapter {

    private Context context;
    private int checkedpos = 0;
    private List<ConfirmOrderBean.DataEntity.PaymentsEntity> mData;

    public PayWayAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setChecked(int pos) {
        this.checkedpos = pos;
        notifyDataSetChanged();
    }

    public void addData(List<ConfirmOrderBean.DataEntity.PaymentsEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ConfirmOrderBean.DataEntity.PaymentsEntity getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder=new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_order_pay_way, null);
            holder.iconChoose= (ImageView) view.findViewById(R.id.icon_choose);
            holder.tvName= (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        showItem(holder,i);
        return view;
    }

    protected void showItem(ViewHolder holder,int i){
        if(checkedpos==i){
            holder.iconChoose.setVisibility(View.VISIBLE);
        }else{
            holder.iconChoose.setVisibility(View.GONE);
        }
        holder.tvName.setText(mData.get(i).name);
    }

    public static class ViewHolder {
       public TextView tvName;
       public ImageView iconChoose;

    }
}
