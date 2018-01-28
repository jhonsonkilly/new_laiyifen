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
public class PackageListAdapter extends BaseAdapter {
    protected List<OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean> mData;

    protected ProductAdapter productAdapter;

    public PackageListAdapter(){
        mData=new ArrayList<>();
    }

    public void setData(List<OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean> data){
        for(int i=0;i<data.size();i++){
            if(data.get(i).productList!=null&&data.get(i).productList.size()>0){
                this.mData.add(data.get(i));
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean getItem(int position) {
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package_info,parent,false);
            holder=new ViewHolder();
            holder.lv_product= (MyListView) convertView.findViewById(R.id.lv_product);
            holder.tv_package= (TextView) convertView.findViewById(R.id.tv_package);
            holder.view_line=convertView.findViewById(R.id.view_line);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(mData.size()<2){
            holder.tv_package.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.GONE);
        }else{
            holder.tv_package.setVisibility(View.VISIBLE);
            holder.view_line.setVisibility(View.VISIBLE);
            holder.tv_package.setText(parent.getContext().getString(R.string.parcel)+(position+1));
        }
        if(mData.get(position).productList!=null&&mData.get(position).productList.size()>0){
            ProductAdapter productAdapter=new ProductAdapter();
            productAdapter.setData(mData.get(position).productList);
            holder.lv_product.setAdapter(productAdapter);
        }
        return convertView;
    }

//    protected ProductAdapter setAdapter(){
//        return new ProductAdapter();
//    }

    class ViewHolder{
        private TextView tv_package;
        private MyListView lv_product;
        private View view_line;
    }
}
