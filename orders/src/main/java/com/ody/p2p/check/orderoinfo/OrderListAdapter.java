package com.ody.p2p.check.orderoinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.views.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/8/23.
 */
public class OrderListAdapter extends BaseAdapter {

    private List<OrderInfoBean.DataBean.ChildOrderListBean> mData;
    private ProductAdapter productAdapter;
    public OrderListAdapter(){
        mData=new ArrayList<>();
    }

    public void setData(List<OrderInfoBean.DataBean.ChildOrderListBean> data){
        this.mData=data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public OrderInfoBean.DataBean.ChildOrderListBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_info,parent,false);
            holder=new ViewHolder();
            holder.lv_package= (MyListView) convertView.findViewById(R.id.lv_package);
            holder.tv_merchant_name= (TextView) convertView.findViewById(R.id.tv_merchant_name);
            holder.tv_transport_fee= (TextView) convertView.findViewById(R.id.tv_transport_fee);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_merchant_name.setText(parent.getContext().getString(R.string.order)+(position+1));
        if(mData.get(position).orderDeliveryFee.equals("0")){
            holder.tv_transport_fee.setText(parent.getContext().getString(R.string.no_mail));
        }else{
            holder.tv_transport_fee.setText(parent.getContext().getString(R.string.feright)+":"+parent.getContext().getString(R.string.money_symbol)+mData.get(position).orderDeliveryFee);
        }
        if(mData.get(position).packageList!=null&&mData.get(position).packageList.size()>0){
            PackageListAdapter packageListAdapter=new PackageListAdapter();
            packageListAdapter.setData(mData.get(position).packageList);
            holder.lv_package.setAdapter(packageListAdapter);
        }
        return convertView;
    }

//    public PackageListAdapter getPackageAdapter(){
//        return new PackageListAdapter();
//    }

    class ViewHolder{
        private TextView tv_merchant_name;
        private MyListView lv_package;
        private TextView tv_transport_fee;
    }
}
