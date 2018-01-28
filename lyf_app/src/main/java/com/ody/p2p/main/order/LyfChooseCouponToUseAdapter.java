package com.ody.p2p.main.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.views.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单页面  选择优惠券
 * Created by caishengya on 2017/07/03.
 */
public class LyfChooseCouponToUseAdapter extends BaseAdapter {

    private Context mContext;
    private List<LyfCouponBean.CouponBean> mData;
    private LyfChooseCouponOnClick mLyfChooseCouponOnClick;

    public LyfChooseCouponOnClick getmLyfChooseCouponOnClick() {
        return mLyfChooseCouponOnClick;
    }

    public void setmLyfChooseCouponOnClick(LyfChooseCouponOnClick mLyfChooseCouponOnClick) {
        this.mLyfChooseCouponOnClick = mLyfChooseCouponOnClick;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public LyfChooseCouponToUseAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    public void addData(List<LyfCouponBean.CouponBean> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public List<LyfCouponBean.CouponBean> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public LyfCouponBean.CouponBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int parentPos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lyf_choose_coupon_for_use, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTitle = (TextView) convertView.findViewById(R.id.item_lyf_choose_coupon_for_use_title);
        holder.mMyListView = (MyListView) convertView.findViewById(R.id.item_lyf_choose_coupon_for_use_mylistview);
        if (mData.get(parentPos).getCouponlist().get(0).getThemeType() == 0) {//平台券
            holder.mTitle.setText(mData.get(parentPos).merchantName + "平台券");
        } else if (mData.get(parentPos).getCouponlist().get(0).getThemeType() == 1) {//自营券
            holder.mTitle.setText(mData.get(parentPos).merchantName + "自营券");
        } else if (mData.get(parentPos).getCouponlist().get(0).getThemeType() == 11) {//商家券
            holder.mTitle.setText(mData.get(parentPos).merchantName);
        } else {
            holder.mTitle.setText(mData.get(parentPos).merchantName);
        }
        LyfCouponUseAdapter lyfCouponUseAdapter = new LyfCouponUseAdapter(mContext, mData.get(parentPos).couponlist);
        holder.mMyListView.setAdapter(lyfCouponUseAdapter);
        holder.mMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int childPos, long id) {
                if (mLyfChooseCouponOnClick != null) {
                    mLyfChooseCouponOnClick.ChooseCounpon(parentPos, childPos);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView mTitle;
        private MyListView mMyListView;
    }

    interface LyfChooseCouponOnClick {
        void ChooseCounpon(int parentPos, int childPos);
    }
}
