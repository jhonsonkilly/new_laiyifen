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

import com.ody.p2p.main.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单页面 适配器 子布局
 * Created by caishengya on 2017/07/03.
 */

public class LyfCouponUseAdapter extends BaseAdapter {

    private List<LyfCouponBean.CouponBean.Bean> mData;
    private Context context;

    public LyfCouponUseAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public LyfCouponUseAdapter(Context context, List<LyfCouponBean.CouponBean.Bean> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void addData(List<LyfCouponBean.CouponBean.Bean> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public List<LyfCouponBean.CouponBean.Bean> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public LyfCouponBean.CouponBean.Bean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lyf_use_coupon, parent, false);
            holder.cb_select = (CheckBox) convertView.findViewById(R.id.item_lyf_use_coupon_cb_select);
            holder.iv_status_icon = (ImageView) convertView.findViewById(R.id.item_lyf_use_coupon_iv_status_icon);
            holder.tv_des = (TextView) convertView.findViewById(R.id.item_lyf_use_coupon_tv_des);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_lyf_use_coupon_tv_name);
            holder.tv_price = (TextView) convertView.findViewById(R.id.item_lyf_use_coupon_tv_price);
            holder.tv_time = (TextView) convertView.findViewById(R.id.item_lyf_use_coupon_tv_time);
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.item_lyf_use_coupon_ll_root);
            holder.tv_icon = (TextView) convertView.findViewById(R.id.item_lyf_use_coupon_tv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(mData.get(position).couponValue)) {
            holder.tv_price.setText(UiUtils.getDoubleForDouble(mData.get(position).couponValue));
        }
        if (mData.get(position).themeTitle != null) {
            holder.tv_name.setText(mData.get(position).themeTitle.trim());
        }
        holder.tv_time.setText(DateTimeUtils.formatDateTime(mData.get(position).startTime, "yyyy.MM.dd") + "-" + DateTimeUtils.formatDateTime(mData.get(position).endTime, "yyyy.MM.dd"));
        holder.tv_des.setText(mData.get(position).moneyRule);

        if (mData.get(position).isAvailable != 1) {
            holder.cb_select.setEnabled(false);
            holder.cb_select.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cart_ic_invalidation));
            holder.ll_root.setBackgroundResource(R.drawable.coupon_orange_used);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.note_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.note_color));
        } else {
            holder.cb_select.setEnabled(true);
            holder.cb_select.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkbox_coupon));
            holder.ll_root.setBackgroundResource(R.drawable.coupon_orange);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.main_title_color));
            holder.tv_des.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.theme_color));
            holder.tv_icon.setTextColor(context.getResources().getColor(R.color.theme_color));
        }

        if (mData.get(position).selected == 1) {
            holder.cb_select.setChecked(true);
        } else {
            holder.cb_select.setChecked(false);
        }
        return convertView;
    }

    class ViewHolder {
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
