package com.ody.p2p.RefoundInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/10/11.
 */
public class LogisticCompanyAdapter extends BaseAdapter {

    private List<LogisticsCompanyBean.DataBean> mData;

    public LogisticCompanyAdapter(){
        mData=new ArrayList<>();
    }
    public void setData(List<LogisticsCompanyBean.DataBean> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addTop(LogisticsCompanyBean.DataBean bean,List<LogisticsCompanyBean.DataBean> data){
        mData.clear();
        mData.add(bean);
        if(data!=null&&data.size()>0){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public LogisticsCompanyBean.DataBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logistic_company,parent,false);
            holder=new Holder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.tv_name.setText(mData.get(position).name);
        return convertView;
    }

    private class Holder{
        private TextView tv_name;

    }
}
