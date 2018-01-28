package com.ody.p2p.check.aftersale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.basepopupwindow.PropertyWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/8/18.
 */
public class ChangeProductAdapter extends RecyclerView.Adapter {


    protected Context context;
    public List<AfterSaleChangeProductBean> mData;

    public ChangeProductAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_num;
        public TextView tv_sku;
        public RelativeLayout rl_sku;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            rl_sku = (RelativeLayout) itemView.findViewById(R.id.rl_sku);
            tv_sku = (TextView) itemView.findViewById(R.id.tv_sku);
        }
    }

    public List<AfterSaleChangeProductBean> getData() {
        return mData;
    }

    public void remove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_product, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        showItem(viewHolder, position);
    }

    public void addData(AfterSaleChangeProductBean dataBean) {
        mData.add(dataBean);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    private String getAttrs(List<ProductBean.Attrs> attrs){
        String attrstr="";
        if(attrs!=null&&attrs.size()>0){
            for(int i=0;i<attrs.size();i++){
                if(attrs.get(i).attrVal != null && !StringUtils.isEmpty(attrs.get(i).attrVal.value)){
                    attrstr+=attrs.get(i).attrVal.value+",";
                }
            }
            return attrstr.substring(0,attrstr.length()-1);
        }else{
            return "";
        }
    }

    protected void showItem(final ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(mData.get(position).productBean.picUrl)) {
            GlideUtil.display(context, mData.get(position).productBean.picUrl).override(200, 200).into(holder.iv_product);
        }
        holder.tv_name.setText(mData.get(position).productBean.name);
        holder.tv_price.setText(UiUtils.getMoneyDouble(mData.get(position).productBean.price )+ "");
        if (mData.get(position).isSerial == 1) {
            String sku=getAttrs(mData.get(position).productBean.attrs);

            //选择的子品属性描述，展示在底部,如果不是系列品则不展示
            if(!TextUtils.isEmpty(sku)){
                holder.rl_sku.setVisibility(View.VISIBLE);
                holder.tv_sku.setText(getAttrs(mData.get(position).productBean.attrs));
            }else{
                holder.rl_sku.setVisibility(View.GONE);
            }
            holder.rl_sku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.get(position).propertyWindow.showAtLocation(holder.rl_sku, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    mData.get(position).propertyWindow.setPropertyBack(new PropertyWindow.PropertyBack() {
                        @Override
                        public void PropertyCallBack(ProductBean product, int num) {
                            /**
                             * 如果是系列品选择更换的子品后，替换掉原来对应的商品信息，和商品对应的productBean.productBean.attrs
                             */
                            AfterSaleChangeProductBean productBean=new AfterSaleChangeProductBean();
                            productBean.productBean=product;
                            productBean.soItemId = mData.get(position).soItemId;
                            productBean.isSerial = mData.get(position).isSerial;
                            if (mData.get(position).isSerial == 1) {
                                productBean.isSerial=1;
                                productBean.propertyWindow = mData.get(position).propertyWindow;
                                productBean.map=mData.get(position).map;
                                if (mData.get(position).map!=null){
                                    productBean.productBean.attrs = new ArrayList<>();
                                    for (int i = 0; i < mData.get(position).map.getAttributes().size(); i++) {
                                        ProductBean.Attrs attr = new ProductBean.Attrs();
                                        attr.attrVal = new ProductBean.Attrs.AttrVal();
                                        if (mData.get(position).map.getAttributes().get(i) != null && mData.get(position).map.getAttributes().get(i).getValues() != null){
                                            for (int j = 0; j < mData.get(position).map.getAttributes().get(i).getValues().size(); j++) {
                                                if(mData.get(position).map.getAttributes().get(i).getValues().get(j).getChecked()){
                                                    attr.attrVal.value = mData.get(position).map.getAttributes().get(i).getValues().get(j).getValue();
                                                }
                                            }
                                        }
                                        productBean.productBean.attrs.add(attr);
                                    }
                                }
                            }
                            mData.remove(position);
                            mData.add(productBean);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void bayNow(ProductBean product, int productNumber) {

                        }

                        @Override
                        public void addToShopCart(ProductBean product, int productNumber) {

                        }


                    });
                }
            });
        }else{
            holder.rl_sku.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
