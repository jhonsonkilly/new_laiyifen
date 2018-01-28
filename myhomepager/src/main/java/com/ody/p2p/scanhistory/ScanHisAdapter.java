package com.ody.p2p.scanhistory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;
import com.ody.p2p.data.ScanHistoryBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.List;


/**
 * Created by ody on 2016/6/16.
 */
public class ScanHisAdapter extends BaseAdapter {

    private Context mContext;
    private List<ScanHistoryBean> datas;

    public ScanHisAdapter(Context context, List<ScanHistoryBean> list){
        mContext = context;
        datas = list;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScanViewHolder holder ;
        if(convertView == null){
            holder = new ScanViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myhomepager_item_scan_history,null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_scan_his_item);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_scan_his_name);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_scan_his_price);
            holder.first = convertView.findViewById(R.id.view_first);
            convertView.setTag(holder);
        }else {
            holder = (ScanViewHolder) convertView.getTag();
        }
        ScanHistoryBean bean = datas.get(position);
        if(position == 0){
            holder.first.setVisibility(View.VISIBLE);
        }else {
            holder.first.setVisibility(View.GONE);
        }

        GlideUtil.display(mContext, bean.getUrl()).into(holder.iv);
        holder.tv_name.setText(bean.getSpName());
        holder.tv_price.setText(UiUtils.getMoney(mContext,bean.getCost()));

        return convertView;
    }

    class ScanViewHolder{
        ImageView iv;
        TextView tv_name;
        TextView tv_price;
        View first;
    }
    public void addDatas(List<ScanHistoryBean> list){
        datas.addAll(list);
        this.notifyDataSetChanged();

    }
}
