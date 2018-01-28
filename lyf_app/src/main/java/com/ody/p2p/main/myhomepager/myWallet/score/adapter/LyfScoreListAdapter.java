package com.ody.p2p.main.myhomepager.myWallet.score.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseAdapter;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LyfScoreDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 来伊份 我的钱包 --> 积分页面 积分明细list的适配器
 * Created by caishengya on 2017/3/21.
 */

public class LyfScoreListAdapter extends BaseAdapter {

    private Context mContext;
    private List<LyfScoreDetailsBean.DataBeanX.DataBean> mArrayList;

    public LyfScoreListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mArrayList = new ArrayList<>();
    }

    public void addArrayList(List<LyfScoreDetailsBean.DataBeanX.DataBean> mArrayList) {
        this.mArrayList.addAll(mArrayList);
        notifyDataSetChanged();
    }

    public void setmArrayList(List<LyfScoreDetailsBean.DataBeanX.DataBean> mArrayList) {
        this.mArrayList.clear();
        this.mArrayList.addAll(mArrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lyf_cell_score_details_list, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextViewTypeName = (TextView) convertView.findViewById(R.id.lyf_cell_score_detail_list_type_name);
            viewHolder.mTextViewAddCount = (TextView) convertView.findViewById(R.id.lyf_cell_score_detail_list_add_count);
            viewHolder.mTextViewTime = (TextView) convertView.findViewById(R.id.lyf_cell_score_detail_list_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextViewTypeName.setText(mArrayList.get(position).actionTypeName);
        if (mArrayList.get(position).actionType == 1) {
            viewHolder.mTextViewAddCount.setText("+ " + String.valueOf(mArrayList.get(position).balance));
        } else if (mArrayList.get(position).actionType == 2) {
            viewHolder.mTextViewAddCount.setText("- " + String.valueOf(mArrayList.get(position).balance));
        }
        viewHolder.mTextViewTime.setText(mArrayList.get(position).createTimeStr);

        return convertView;
    }

    class ViewHolder {
        private TextView mTextViewTypeName;
        private TextView mTextViewAddCount;
        private TextView mTextViewTime;
    }

}
