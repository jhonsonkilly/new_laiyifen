package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ody.p2p.search.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/8/29.
 */
public class FlowAdapter extends BaseAdapter{

    private final List<String> mDataList;

    public FlowAdapter() {
        mDataList = new ArrayList<>();
    }

    public void setDatas(List<String> datas) {
        if (datas != null && datas.size() > 0){
            mDataList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<String> datas) {
        mDataList.clear();
        setDatas(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history_tag, null);
            holder.tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_tag.setText(mDataList.get(position).toString());
        return convertView;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        private TextView tv_tag;
    }
}
