package com.ody.p2p.check.coupon;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public class CouponAdapter extends android.widget.BaseAdapter {

    protected Context context;
    protected List<CouponBean.DataBean.CanUseCouponListBean> mData;
    private boolean isUseing=false;

    public CouponAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void addData(List<CouponBean.DataBean.CanUseCouponListBean> mData){
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public void isUse(boolean isUseing){
        this.isUseing=isUseing;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    public List<CouponBean.DataBean.CanUseCouponListBean> getAll(){
        return mData;
    }

    @Override
    public CouponBean.DataBean.CanUseCouponListBean getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       final  Holder holder;
        if(view==null){
            holder=new Holder();
            view= LayoutInflater.from(context).inflate(R.layout.item_order_coupon,null);
            holder.iv_arrow= (ImageView) view.findViewById(R.id.iv_arrow);
            holder.iv_decoration= (ImageView) view.findViewById(R.id.iv_decoration);
            holder.ll_bottom= (LinearLayout) view.findViewById(R.id.ll_bottom);
            holder.tv_des= (TextView) view.findViewById(R.id.tv_des);
            holder.tv_mark= (TextView) view.findViewById(R.id.tv_mark);
            holder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            holder.tv_price= (TextView) view.findViewById(R.id.tv_price);
            holder.tv_time= (TextView) view.findViewById(R.id.tv_time);
            holder.tv_icon= (TextView) view.findViewById(R.id.tv_icon);
            holder.tv_use_rule= (TextView) view.findViewById(R.id.tv_use_rule);
            holder.cb_select= (CheckBox) view.findViewById(R.id.cb_select);
            holder.tv_coupon_tip= (TextView) view.findViewById(R.id.tv_coupon_tip);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }
        holder.tv_coupon_tip.setText(RUtils.getStringRes(context,RUtils.COUPON));
        if(!TextUtils.isEmpty(mData.get(i).couponValue)){
            holder.tv_price.setText(UiUtils.getDoubleForDouble(mData.get(i).couponValue));
        }
        if(mData.get(i).themeTitle!=null){
            holder.tv_name.setText(mData.get(i).themeTitle.trim());
        }
        holder.tv_time.setText(DateTimeUtils.formatDateTime(mData.get(i).startTime,"yyyy-MM-dd")+context.getString(R.string.to)+DateTimeUtils.formatDateTime(mData.get(i).endTime,"yyyy-MM-dd"));
        if(isUseing){
            holder.tv_des.setText(mData.get(i).moneyRule);
            holder.tv_use_rule.setVisibility(View.GONE);
            holder.cb_select.setVisibility(View.VISIBLE);
            if(mData.get(i).isAvailable!=1){
                holder.cb_select.setButtonDrawable(R.drawable.ic_forbidden);
                holder.cb_select.setEnabled(false);
            }else{
                holder.cb_select.setButtonDrawable(R.drawable.checkbox_coupon);
                holder.cb_select.setEnabled(true);
            }
        }else{
            holder.tv_use_rule.setVisibility(View.VISIBLE);
            holder.tv_use_rule.setText(mData.get(i).moneyRule);
            holder.tv_des.setText(mData.get(i).couponCode);
            holder.cb_select.setVisibility(View.INVISIBLE);
        }
        if(holder.cb_select.getVisibility()==holder.cb_select.VISIBLE){
            if(mData.get(i).selected==1){
                holder.cb_select.setChecked(true);
            }else{
                holder.cb_select.setChecked(false);
            }
            holder.cb_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.cb_select.isChecked()){
                        for(int j=0;j<mData.size();j++){
                            if(i==j){
                                mData.get(j).selected=1;
                            }else{
                                mData.get(j).selected=0;
                            }
                        }
                        notifyDataSetChanged();
                    }else{
                        mData.get(i).selected=0;
                    }
                }
            });
        }
        if(TextUtils.isEmpty(mData.get(i).refDescription)){
            holder.ll_bottom.setVisibility(View.GONE);
        }else{
            holder.tv_mark.setText(mData.get(i).refDescription);
            holder.ll_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.tv_mark.getVisibility()==holder.tv_mark.VISIBLE){
                        holder.iv_arrow.setImageResource(R.drawable.ic_arrowdown_gray);
                        holder.tv_mark.setVisibility(View.GONE);
                    }else{
                        holder.tv_mark.setVisibility(View.VISIBLE);
                        holder.iv_arrow.setImageResource(R.drawable.ic_arrowup_gray);
                    }
                }
            });
        }
        showItem(holder,i);
        return view;
    }

    protected void showItem(final Holder holder, final int i){
        if(mData.get(i).isAvailable==0){
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.iv_decoration.setImageResource(R.drawable.mycoupon_deraction_gray);
            holder.tv_use_rule.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.note_color));
        }else{
            holder.iv_decoration.setImageResource(R.drawable.coupon_decoration);
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.theme_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.main_title_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.theme_color));
        }
    }

    protected class Holder{
        public TextView tv_price;
        public TextView tv_name;
        public TextView tv_des;
        public TextView tv_time;
        public LinearLayout ll_bottom;
        public ImageView iv_arrow;
        public TextView tv_mark;
        public ImageView iv_decoration;
        public TextView tv_icon;
        public TextView tv_use_rule;
        public CheckBox cb_select;
        public TextView tv_coupon_tip;
    }
}
