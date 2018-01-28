package com.ody.p2p.check.myorder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/9.
 */
public class ExpenseListAdapter extends BaseAdapter {

    private List<ConfirmOrderBean.DataEntity.ExpenseEntity> mData;

    public ExpenseListAdapter(List<ConfirmOrderBean.DataEntity.ExpenseEntity> mData){
        this.mData=mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_list,parent,false);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_value= (TextView) convertView.findViewById(R.id.tv_value);
            viewHolder.iv_prom= (ImageView) convertView.findViewById(R.id.iv_prom);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(mData.get(position).label);
        viewHolder.tv_value.setText(mData.get(position).operator+parent.getContext().getString(R.string.money_symbol)+ UiUtils.getDoubleForDouble(mData.get(position).value));
        if(TextUtils.isEmpty(mData.get(position).iconUrl)){
            viewHolder.iv_prom.setVisibility(View.GONE);
        }else{
            viewHolder.iv_prom.setVisibility(View.VISIBLE);
            GlideUtil.display(parent.getContext(),mData.get(position).iconUrl).into(viewHolder.iv_prom);
        }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_name;
        private TextView tv_value;
        private ImageView iv_prom;
    }
}
