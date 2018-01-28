package com.ody.p2p.check.orderoinfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/8/23.
 */
public class ProductAdapter extends BaseAdapter {

    private List<OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean.ProductListBean> mData;

    public ProductAdapter() {
       mData=new ArrayList<>();
    }

    public void setData(List<OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean.ProductListBean> mData){
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public OrderInfoBean.DataBean.ChildOrderListBean.PackageListBean.ProductListBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_two_item,parent,false);
            holder = new ViewHolder();
            holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_describe = (TextView) convertView.findViewById(R.id.tv_describe);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.iv_icon1 = (ImageView) convertView.findViewById(R.id.iv_icon1);
            holder.iv_icon2 = (ImageView) convertView.findViewById(R.id.iv_icon2);
            holder.iv_icon3 = (ImageView) convertView.findViewById(R.id.iv_icon3);
            holder.tv_service1 = (TextView) convertView.findViewById(R.id.tv_service1);
            holder.tv_service2 = (TextView) convertView.findViewById(R.id.tv_service2);
            holder.tv_service3 = (TextView) convertView.findViewById(R.id.tv_service3);
            holder.ll_security = (LinearLayout) convertView.findViewById(R.id.ll_security);
            holder.ll_sec1 = (LinearLayout) convertView.findViewById(R.id.ll_sec1);
            holder.ll_sec2 = (LinearLayout) convertView.findViewById(R.id.ll_sec2);
            holder.ll_sec3 = (LinearLayout) convertView.findViewById(R.id.ll_sec3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        showTtem(holder, position, parent);
        return convertView;
    }

    protected void showTtem(ViewHolder holder, final int position, ViewGroup parent) {
        if (!TextUtils.isEmpty(mData.get(position).picUrl)) {
            GlideUtil.display(parent.getContext(), mData.get(position).picUrl).into(holder.iv_product);
        }
        holder.tv_name.setText(mData.get(position).name);
        holder.tv_num.setText("X" + mData.get(position).num + "");
        holder.tv_price.setText(parent.getContext().getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(mData.get(position).price));
        if(mData.get(position).propertyTags!=null&&mData.get(position).propertyTags.size()>0){
            holder.tv_describe.setVisibility(View.VISIBLE);
            String attrs="";
            for(int i=0;i<mData.get(position).propertyTags.size();i++){
                if(mData.get(position).propertyTags.get(i) != null ){
                    attrs+=mData.get(position).propertyTags.get(i).name+":"+mData.get(position).propertyTags.get(i).value+",";
                }
            }
            attrs=attrs.substring(0,attrs.length()-1);
            if(!TextUtils.isEmpty(attrs)){
                holder.tv_describe.setText(attrs);
            }
        }else{
            holder.tv_describe.setVisibility(View.GONE);
        }
        if (mData.get(position).securityList != null && mData.get(position).securityList.size() > 0) {
            holder.ll_security.setVisibility(View.VISIBLE);
            for(int i=0;i<mData.get(position).securityList.size();i++){
                if(i==0){
                    holder.tv_service1.setText(mData.get(position).securityList.get(0).title);
                    if (!TextUtils.isEmpty(mData.get(position).securityList.get(0).url)) {
                        GlideUtil.display(parent.getContext(), mData.get(position).securityList.get(0).url).override(20,20).into(holder.iv_icon1);
                    }
                }
                if(i==1){
                    if (mData.get(position).securityList.get(1) != null) {
                        holder.ll_sec2.setVisibility(View.VISIBLE);
                        holder.tv_service2.setText(mData.get(position).securityList.get(1).title);
                        if (!TextUtils.isEmpty(mData.get(position).securityList.get(1).url)) {
                            GlideUtil.display(parent.getContext(), mData.get(position).securityList.get(1).url).override(20,20).into(holder.iv_icon2);
                        }
                    } else {
                        holder.ll_sec2.setVisibility(View.GONE);
                    }
                }
                if(i==2){
                    if (mData.get(position).securityList.get(2) != null) {
                        holder.ll_sec3.setVisibility(View.VISIBLE);
                        holder.tv_service3.setText(mData.get(position).securityList.get(2).title);
                        if (!TextUtils.isEmpty(mData.get(position).securityList.get(2).url)) {
                            GlideUtil.display(parent.getContext(), mData.get(position).securityList.get(2).url).override(20,20).into(holder.iv_icon3);
                        }
                    } else {
                        holder.ll_sec3.setVisibility(View.GONE);
                    }
                }
            }

        } else {
            holder.ll_security.setVisibility(View.GONE);
        }

        holder.iv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SP_ID,mData.get(position).mpId + "");
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL,bundle);
            }
        });
    }

    public static class ViewHolder {
        public ImageView iv_product;
        public TextView tv_name, tv_describe, tv_price, tv_num;
        public ImageView iv_icon1;
        public ImageView iv_icon2;
        public ImageView iv_icon3;
        public TextView tv_service1;
        public TextView tv_service2;
        public TextView tv_service3;
        public LinearLayout ll_security;
        public LinearLayout ll_sec3;
        public LinearLayout ll_sec2;
        public LinearLayout ll_sec1;
    }

}
