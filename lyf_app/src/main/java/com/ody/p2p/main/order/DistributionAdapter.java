package com.ody.p2p.main.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/12/5.
 */

public class DistributionAdapter extends BaseAdapter {

    private List<ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity.DeliveryModeListEntity> mData;

    public DistributionAdapter(){
        mData=new ArrayList<>();
    }

    public void addData(List<ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity.DeliveryModeListEntity> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity.DeliveryModeListEntity getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_distribution,parent,false);
            holder=new Holder();
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.textView= (TextView) convertView.findViewById(R.id.textView);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.check_icon=convertView.findViewById(R.id.check_icon);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.tv_name.setText(mData.get(position).name);
        if(mData.get(position).isTakeTheir==1){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText("目前仅支持上海");
        }else{
            holder.textView.setVisibility(View.GONE);
        }
        if(mData.get(position).isDefault==1){
            holder.checkBox.setChecked(true);
            holder.check_icon.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setChecked(false);
            holder.check_icon.setVisibility(View.GONE);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<mData.size();i++){
                    if(i==position){
                        mData.get(i).isDefault=1;
                    }else{
                        mData.get(i).isDefault=0;
                    }
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class Holder{
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView textView;
        private CheckBox checkBox;
        private View check_icon;
    }

}
