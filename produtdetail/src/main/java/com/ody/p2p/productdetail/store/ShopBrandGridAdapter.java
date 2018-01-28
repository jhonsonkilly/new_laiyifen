package com.ody.p2p.productdetail.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.productdetail.store.bean.StoreBaseInfo;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2017/7/21.
 */

public class ShopBrandGridAdapter extends BaseAdapter {
    private List<StoreBaseInfo.DataBean.MerchantBrandVO> mData;
    private Context context;

    public ShopBrandGridAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void setData(List<StoreBaseInfo.DataBean.MerchantBrandVO> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void setNewData(List<StoreBaseInfo.DataBean.MerchantBrandVO> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BrandHolder brandHolder;
        if(convertView==null){
            brandHolder=new BrandHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_brand,parent,false);
            brandHolder.iv_brand=(ImageView) convertView.findViewById(R.id.iv_brand);
            brandHolder.tv_brand_name=(TextView) convertView.findViewById(R.id.tv_brand_name);
            convertView.setTag(brandHolder);
        }else{
            brandHolder= (BrandHolder) convertView.getTag();
        }

        brandHolder.tv_brand_name.setText(mData.get(position).brandName);
        GlideUtil.display(context,mData.get(position).logoUrl).into(brandHolder.iv_brand);
        return convertView;
    }

    private class BrandHolder{

        private ImageView iv_brand;
        private TextView tv_brand_name;

//        public BrandHolder() {
//            iv_brand= (ImageView) itemView.findViewById(R.id.iv_brand);
//            tv_brand_name= (TextView) itemView.findViewById(R.id.tv_brand_name);
//        }
    }
}
