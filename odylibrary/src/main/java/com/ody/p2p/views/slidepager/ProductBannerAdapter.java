package com.ody.p2p.views.slidepager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/6/8.
 */
public class ProductBannerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private ArrayList<BannerProductBean> bannerList;

    private int size;
    private boolean isInfiniteLoop;

    public ProductBannerAdapter(Context context, ArrayList<BannerProductBean> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
        this.size = bannerList.size();
        isInfiniteLoop = false;
    }

    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : bannerList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        BannerProductBean Bean = new BannerProductBean();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_product_banner, null);
        GridView gv_product = (GridView) convertView.findViewById(R.id.gv_product);
        Bean = bannerList.get(position % size);
        ProductAdapter adapter = new ProductAdapter(Bean.productList);
        gv_product.setAdapter(adapter);
        return convertView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        notifyDataSetChanged();
    }

    class ProductViewHolder {
        public TextView tv_productname;
        public TextView tv_productcost;
        public ImageView iv_product;
    }

    class ProductAdapter extends BaseAdapter {

        private List<ProductBean> productList;

        public ProductAdapter(List<ProductBean> productList) {
            super();
            this.productList = productList;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ProductViewHolder viewHolder;
            ProductBean Bean = new ProductBean();
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.item_gridview_product, null);
                viewHolder = new ProductViewHolder();
                viewHolder.tv_productname = (TextView) view.findViewById(R.id.tv_productname);
                viewHolder.tv_productcost = (TextView) view.findViewById(R.id.tv_productcost);
                viewHolder.iv_product = (ImageView) view.findViewById(R.id.iv_product);
                convertView = view;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ProductViewHolder) convertView.getTag();
            }
            Bean = productList.get(position);
            viewHolder.tv_productcost.setText(Bean.title);
//            viewHolder.tv_productname.setText(Bean.productName);
            GlideUtil.display(parent.getContext(), Bean.getRecommendUrl()).override(200, 200).into(viewHolder.iv_product);

            return convertView;
        }
    }


}
