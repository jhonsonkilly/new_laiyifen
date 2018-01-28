package com.ody.p2p.main.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.check.coupon.CouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/12/9.
 */

public class CouponUseAdapter extends BaseAdapter {

    private List<CouponBean.DataBean.CanUseCouponListBean> mData;
    private Context context;
    private int able=0;//0--可选  1--不可选

    public CouponUseAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void addData(List<CouponBean.DataBean.CanUseCouponListBean> mData){
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public List<CouponBean.DataBean.CanUseCouponListBean> getData(){
        return mData;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_use_coupon,parent,false);
            holder.cb_select= (CheckBox) convertView.findViewById(R.id.cb_select);
            holder.iv_status_icon= (ImageView) convertView.findViewById(R.id.iv_status_icon);
            holder.tv_des= (TextView) convertView.findViewById(R.id.tv_des);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.ll_root= (LinearLayout) convertView.findViewById(R.id.ll_root);
            holder.tv_icon= (TextView) convertView.findViewById(R.id.tv_icon);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(position).couponValue)){
            holder.tv_price.setText(UiUtils.getDoubleForDouble(mData.get(position).couponValue));
        }
        if(mData.get(position).themeTitle!=null){
            holder.tv_name.setText(mData.get(position).themeTitle.trim());
        }
        holder.tv_time.setText(DateTimeUtils.formatDateTime(mData.get(position).startTime,"yyyy.MM.dd")+"-"+DateTimeUtils.formatDateTime(mData.get(position).endTime,"yyyy.MM.dd"));
        holder.tv_des.setText(mData.get(position).moneyRule);
        if(mData.get(position).isAvailable!=1){
            holder.cb_select.setVisibility(View.INVISIBLE);
            holder.ll_root.setBackgroundResource(R.drawable.coupon_orange_used);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.note_color));
        }else{
            holder.cb_select.setVisibility(View.VISIBLE);
            holder.cb_select.setEnabled(true);
            holder.ll_root.setBackgroundResource(R.drawable.coupon_orange);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.main_title_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.theme_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.theme_color));
        }
        if(mData.get(position).selected==1){
            holder.cb_select.setChecked(true);
        }else{
            holder.cb_select.setChecked(false);
        }
//        if(able==0){
//            holder.cb_select.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for(int j=0;j<mData.size();j++){
//                        if(position==j){
//                            if(mData.get(j).selected==1){
//                                mData.get(j).selected=0;
//                            }else{
//                                mData.get(j).selected=1;
//                            }
//                        }else{
//                            mData.get(j).selected=0;
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//        }else{
//            holder.cb_select.setChecked(false);
//            holder.cb_select.setEnabled(false);
//        }
        return convertView;
    }

//    public void isEnable(int able){
//        this.able=able;
//        notifyDataSetChanged();
//    }

    class ViewHolder{
        private TextView tv_price;
        private TextView tv_name;
        private TextView tv_des;
        private TextView tv_time;
        private CheckBox cb_select;
        private ImageView iv_status_icon;
        private LinearLayout ll_root;
        private TextView tv_icon;
    }
}
