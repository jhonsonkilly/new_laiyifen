package com.lyfen.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.ody.p2p.main.R;

import java.util.List;

/**
 * Created by mac on 2017/12/14.
 */

public class ProductDetailItemAdapter extends RecyclerView.Adapter<ProductDetailItemAdapter.ViewHolderOne> {

    Context context;

    List<OrderDetailEntity.DataBean.ChildOrderListBean.PackageListBean.ProductListBean> list;

    @Override
    public ViewHolderOne onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductDetailItemAdapter.ViewHolderOne(LayoutInflater.from(context).inflate(R.layout.item_orderdetail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderOne holder, int position) {
        ProductDetailItemAdapter.ViewHolderOne holderTwo = (ProductDetailItemAdapter.ViewHolderOne) holder;
        FrescoUtils.displayUrl(holderTwo.img, list.get(position).picUrl);
        holderTwo.text_price.setText("¥ " + list.get(position).price + "");
        holderTwo.text_title.setText(list.get(position).name + "");
        holderTwo.num.setText("x" + list.get(position).num + "");

    }

    public ProductDetailItemAdapter(Context context, List<OrderDetailEntity.DataBean.ChildOrderListBean.PackageListBean.ProductListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        } catch (Exception e) {
            return 0;
        }

    }


    class ViewHolderOne extends RecyclerView.ViewHolder {


        SimpleDraweeView img;

        TextView text_title;

        TextView text_price;

        TextView num;


        public ViewHolderOne(View convertView) {

            super(convertView);
            img = BaseViewHolder.get(convertView, R.id.img);
            text_title = BaseViewHolder.get(convertView, R.id.text_title);
            text_price = BaseViewHolder.get(convertView, R.id.text_price);
            num = BaseViewHolder.get(convertView, R.id.num);


        }
    }
}
