package com.ody.p2p.search.searchkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ody.p2p.search.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史不走接口时用的adapter
 */

public class SearchHistoryAdapter2 extends BaseAdapter {

    private final List<SearchRiCiBean> mDataList;

    public SearchHistoryAdapter2() {
        mDataList = new ArrayList<>();
    }

    public void setDatas(List<SearchRiCiBean> allSearchData) {
        mDataList.clear();
        mDataList.addAll(allSearchData);
        notifyDataSetChanged();
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

        SearchRiCiBean searchRiCiBean = mDataList.get(position);
        try {
            String[] str = searchRiCiBean.getRiCiName().split(",");
            if (str.length == 2) {
                holder.tv_tag.setText(str[0]);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public SearchRiCiBean getItem(int position) {
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
