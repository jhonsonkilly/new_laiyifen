package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.search.R;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/9/13.
 */
public class DsSearchResultAdapter extends BaseAdapter{

    public Context context;
    public List<ResultBean.ProductBean> list;

    public DsSearchResultAdapter(Context context, List<ResultBean.ProductBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_gridview_searchresult, parent, false);
            holder = new ViewHolder();
            holder.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            holder.tv_product_cost = (TextView) view.findViewById(R.id.tv_product_cost);
            holder.tv_product_praise = (TextView) view.findViewById(R.id.tv_product_praise);
            holder.tv_product_praise_good = (TextView) view.findViewById(R.id.tv_product_praise_good);
            holder.iv_product_pic = (ImageView) view.findViewById(R.id.iv_product_pic);
            holder.iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
            //holder.recycler_tag = (RecyclerView) view.findViewById(R.id.recycler_tag);
            holder.rl_search_item = (RelativeLayout) view.findViewById(R.id.rl_search_item);
            holder.recycler_promotion = (RecyclerView) view.findViewById(R.id.recycler_promotion);
            holder.tv_product_sold = (TextView) view.findViewById(R.id.tv_product_sold);
            holder.tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);
            convertView = view;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ResultBean.ProductBean bean = list.get(position);
        if (null!=bean.promotionIconList){
            bean.promotionIconList=null;
        }
        bean.promotionIconList=new ArrayList<>();
        if (null != bean.promotionIconUrls && bean.promotionIconUrls.size() > 0) {//如果有特价就选择特价标签
            for (int i = 0; i < bean.promotionIconUrls.size(); i++) {
                bean.promotionIconList.add(bean.promotionIconUrls.get(i));
            }
        }
        if (null != bean.promotionInfo&&bean.promotionInfo.size()>0&&null != bean.promotionInfo.get(0).promotions &&  bean.promotionInfo.get(0).promotions.size() > 0) {
            for (int k = 0; k < bean.promotionInfo.get(0).promotions.size(); k++) {
                bean.promotionIconList.add( bean.promotionInfo.get(0).promotions.get(k).iconUrl);
            }
        }
        holder.tv_product_name.setText(bean.name);
        holder.tv_product_sold.setText(context.getString(R.string.already_sell)+"："+bean.mpSalesVolume);

        GlideUtil.display(context, bean.picUrl).into(holder.iv_product_pic);
        if (!StringUtils.isEmpty(bean.promotionPrice)){
            holder.tv_product_cost.setText(UiUtils.getMoney(context, bean.promotionPrice));
            holder.tv_productcost_old.setVisibility(View.VISIBLE);
            holder.tv_productcost_old.setText(context.getString(R.string.money_symbol) + bean.price);
        }else{
            holder.tv_product_cost.setText(UiUtils.getMoney(context, bean.price));
            if (bean.marketPrice != null){
                holder.tv_productcost_old.setVisibility(View.VISIBLE);
                holder.tv_productcost_old.setText(context.getString(R.string.money_symbol)+ bean.marketPrice);
            }else{
                holder.tv_productcost_old.setVisibility(View.GONE);
            }
        }
//        if (bean.tagList != null && getDatas().size() > 0) {
//            if (showFlag == SearchResultActivity.TAB_TYPE) {
//                viewHolder.recycler_tag.setLayoutManager(new FullyGridLayoutManager(mContext, 2));
//            } else if (showFlag == SearchResultActivity.LIST_TYPE) {
//                viewHolder.recycler_tag.setLayoutManager(new FullyGridLayoutManager(mContext, 4));
//            }
//            TagAdapter adapter = new TagAdapter(bean.tagList, mContext);
//            viewHolder.recycler_tag.setAdapter(adapter);
//            viewHolder.recycler_tag.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.recycler_tag.setVisibility(View.GONE);
//        }
        if (bean.promotionIconList != null && bean.promotionIconList.size() > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recycler_promotion.setLayoutManager(manager);
            PromotionIconAdapter adapter = new PromotionIconAdapter(context, bean.promotionIconList);
            holder.recycler_promotion.setAdapter(adapter);
        }
        holder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.stockNum > 0) {
                    addBuyCart.addtoCart(bean.mpId, position);
                } else {
                    ToastUtils.showShort(context.getResources().getString(R.string.inventory_insufficient));
                }
            }
        });
        holder.rl_search_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSpInfo.toShop(bean.mpId);
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public ImageView iv_product_pic;
        public TextView tv_product_name;
        public TextView tv_product_cost;
        public TextView tv_product_praise;
        public TextView tv_product_praise_good;
        public ImageView iv_addtocart;
        public RecyclerView recycler_tag;
        public RelativeLayout rl_search_item;
        public TextView tv_productcost_old;
        public TextView tv_product_sold;
        public RecyclerView recycler_promotion;
    }

    public interface AddBuyCart {
        void addtoCart(long mpId, int position);
    }

    public AddBuyCart addBuyCart;

    public void setAddBuyCart(AddBuyCart addBuyCart) {
        this.addBuyCart = addBuyCart;
    }

    public interface ToSpInfo {
        void toShop(long mpId);
    }

    public ToSpInfo toSpInfo;

    public void setToSpInfo(ToSpInfo toSpInfo) {
        this.toSpInfo = toSpInfo;
    }


}
