package com.ody.p2p.main.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/12/9.
 */

public class LYFPayListAdapter extends BaseAdapter {

    private Context context;
    private List<PayTypeListBean.PayType> mData;

    public LYFPayListAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    public void addData(List<PayTypeListBean.PayType> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public PayTypeListBean.PayType getItem(int position) {
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
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_pay,parent,false);
            holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_des= (TextView) convertView.findViewById(R.id.tv_des);
            holder.tv_pay_name= (TextView) convertView.findViewById(R.id.tv_pay_name);
            holder.tv_promotion= (TextView) convertView.findViewById(R.id.tv_promotion);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(position).paymentLogoUrl)){
            GlideUtil.display(context,mData.get(position).paymentLogoUrl).into(holder.iv_icon);
        }
        holder.tv_pay_name.setText(mData.get(position).paymentThirdparty);
        if(!TextUtils.isEmpty(mData.get(position).promLabel)){
            holder.tv_promotion.setVisibility(View.VISIBLE);
            holder.tv_promotion.setText(mData.get(position).promLabel);
        }else{
            holder.tv_promotion.setVisibility(View.INVISIBLE);
        }
        holder.tv_des.setText(mData.get(position).paymentThirdparty+"快捷支付");
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_icon;
        private TextView tv_pay_name;
        private TextView tv_des;
        private TextView tv_promotion;
    }

}
