package com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord.bean.YRecordBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meijunqiang on 2017/3/20.
 * 描述：充值记录适配器
 */

public class PayRecordAdapter extends RecyclerView.Adapter<PayRecordAdapter.PayRecordHolder> {
    private Context mContext;
    private ArrayList<YRecordBean.DataEntity.DataEntityBean> mData;

    public PayRecordAdapter(Context context, ArrayList<YRecordBean.DataEntity.DataEntityBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setData(List<YRecordBean.DataEntity.DataEntityBean> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(List<YRecordBean.DataEntity.DataEntityBean> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(YRecordBean.DataEntity.DataEntityBean... datas) {
        mData.addAll(Arrays.asList(datas));
        notifyDataSetChanged();
    }

    @Override
    public PayRecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PayRecordHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pay_record, parent, false));
    }

    @Override
    public void onBindViewHolder(PayRecordHolder holder, int position) {
        YRecordBean.DataEntity.DataEntityBean recordBean = mData.get(position);
        holder.itemRecordTypeName.setText(recordBean.getPayName());
        holder.itemRecordNumber.setText("+" + recordBean.getBalance());
        holder.itemRecordDate.setText(recordBean.getRecordTimeStr());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PayRecordHolder extends RecyclerView.ViewHolder {
        public TextView itemRecordTypeName;
        public TextView itemRecordDate;
        public TextView itemRecordNumber;

        public PayRecordHolder(View itemView) {
            super(itemView);
            itemRecordTypeName = (TextView) itemView.findViewById(R.id.item_record_type_name);
            itemRecordDate = (TextView) itemView.findViewById(R.id.item_record_date);
            itemRecordNumber = (TextView) itemView.findViewById(R.id.item_record_number);
        }
    }
}
