package com.ody.p2p.main.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/12/5.
 */

public class ChooseStroreAdapter extends BaseAdapter {

    private List<ChooseStoreBean.DataBean.DataListBean> mData;

    public ChooseStroreAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ChooseStoreBean.DataBean.DataListBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<ChooseStoreBean.DataBean.DataListBean> getAllData() {
        return mData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
            holder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_check);
            holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tv_store_address = (TextView) convertView.findViewById(R.id.tv_store_address);
            holder.tv_store_name = (TextView) convertView.findViewById(R.id.tv_store_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_store_name.setText(mData.get(position).merchantShopName);
        holder.tv_store_address.setText(mData.get(position).address);
        holder.tv_distance.setText(mData.get(position).awayFrom);
        if (mData.get(position).isSelected == 1) {
            holder.cb_check.setChecked(true);
        } else if (mData.get(position).isSelected == 0) {
            holder.cb_check.setChecked(false);
        }
        return convertView;
    }

    public void addData(List<ChooseStoreBean.DataBean.DataListBean> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<ChooseStoreBean.DataBean.DataListBean> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    class Holder {
        private TextView tv_store_name;
        private TextView tv_store_address;
        private TextView tv_phone;
        private TextView tv_distance;
        private CheckBox cb_check;
        private RelativeLayout root;
    }

}
