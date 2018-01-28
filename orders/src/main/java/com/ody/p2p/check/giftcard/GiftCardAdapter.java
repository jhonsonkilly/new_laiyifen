package com.ody.p2p.check.giftcard;

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
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/15.
 */
public class GiftCardAdapter extends android.widget.BaseAdapter {

    private Context context;
    private boolean isUseing=false;
    private List<GiftCardBean.DataBean.GiftCardListBean> mData;
    public GiftCardAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void addData(List<GiftCardBean.DataBean.GiftCardListBean> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
    public void isUse(boolean isUseing){
        this.isUseing=isUseing;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public List<GiftCardBean.DataBean.GiftCardListBean> getAll(){
        return mData;
    }

    @Override
    public GiftCardBean.DataBean.GiftCardListBean getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_order_coupon,null);
            holder.iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
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
            holder.iv_coupon_tip= (ImageView) view.findViewById(R.id.iv_coupon_tip);
            holder.iv_status_icon= (ImageView) view.findViewById(R.id.iv_status_icon);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(i).cardAmount)){
            holder.tv_price.setText(UiUtils.getDoubleForDouble(mData.get(i).cardAmount));
        }
        if(mData.get(i).themeTitle!=null){
            holder.tv_name.setText(mData.get(i).themeTitle.trim());
        }
        holder.tv_time.setText(mData.get(i).effStartDateStr+context.getString(R.string.to)+mData.get(i).effEndDateStr);
        holder.tv_use_rule.setVisibility(View.GONE);
//        holder.tv_des.setText(mData.get(i).describe);
        holder.tv_coupon_tip.setText(R.string.money_sum);
        holder.iv_coupon_tip.setImageResource(R.drawable.mygift_card);

        if(isUseing){
            holder.iv_status_icon.setVisibility(View.GONE);
            holder.cb_select.setVisibility(View.VISIBLE);
            if(mData.get(i).isAvailable!=1){
                holder.cb_select.setEnabled(false);
            }else{
                holder.cb_select.setEnabled(true);
            }
        }else{
            holder.cb_select.setVisibility(View.INVISIBLE);
            if(mData.get(i).cardstatus==0){
                holder.iv_status_icon.setVisibility(View.GONE);
            }else if(mData.get(i).cardstatus==1){
                holder.iv_status_icon.setVisibility(View.VISIBLE);
                holder.iv_status_icon.setImageResource(R.drawable.giftcard_outdate);
            }else if(mData.get(i).cardstatus==2){
                holder.iv_status_icon.setVisibility(View.VISIBLE);
                holder.iv_status_icon.setImageResource(R.drawable.giftcard_uesdup);
            }
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
                        mData.get(i).selected=1;
                    }else{
                        mData.get(i).selected=0;
                    }
                    notifyDataSetChanged();
                }
            });
        }
        if(TextUtils.isEmpty(mData.get(i).describe)){
            holder.ll_bottom.setVisibility(View.GONE);
        }else{
            holder.tv_mark.setText(mData.get(i).describe);
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

    protected void showItem(final ViewHolder holder, final int i){
        if(mData.get(i).isAvailable==0){
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.iv_decoration.setImageResource(R.drawable.mycoupon_deraction_gray);
            holder.tv_use_rule.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.note_color));
        }else{
            holder.iv_decoration.setImageResource(RUtils.getDrawableRes(context,RUtils.COUPON_DECORATION));
            holder.tv_price.setTextColor(context.getResources().getColor(RUtils.getColorRes(context,RUtils.THEME_COLOR)));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.main_title_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(RUtils.getColorRes(context,RUtils.THEME_COLOR)));
        }
    }

    private class ViewHolder{
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
        public ImageView iv_coupon_tip;
        public ImageView iv_status_icon;
    }
}
