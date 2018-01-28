package com.ody.p2p.main.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.check.coupon.CouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/12/7.
 */

public class UseCouponAdapter extends BaseAdapter {

    private List<CouponBean.DataBean.CanUseCouponListBean> mData;

    public UseCouponAdapter(){
        mData=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CouponBean.DataBean.CanUseCouponListBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_use_coupon,parent,false);
            holder.cb_select= (CheckBox) convertView.findViewById(R.id.cb_select);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_des= (TextView) convertView.findViewById(R.id.tv_des);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.iv_status_icon= (ImageView) convertView.findViewById(R.id.iv_status_icon);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_price.setText(UiUtils.getDoubleForDouble(mData.get(position).couponValue));
        holder.tv_name.setText(mData.get(position).themeTitle);
        holder.tv_time.setText(DateTimeUtils.formatDateTime(mData.get(position).startTime,"yyyy-MM-dd")+"-"+DateTimeUtils.formatDateTime(mData.get(position).endTime,"yyyy-MM-dd"));
        if(mData.get(position).isAvailable!=1){
            holder.cb_select.setEnabled(false);
        }else{
            holder.cb_select.setEnabled(true);
        }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_price;
        private TextView tv_name;
        private TextView tv_des;
        private TextView tv_time;
        private ImageView iv_status_icon;
        private CheckBox cb_select;
    }
}
