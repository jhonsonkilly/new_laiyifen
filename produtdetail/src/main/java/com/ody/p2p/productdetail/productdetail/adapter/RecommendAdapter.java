package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.PromotionAdapter;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.productdetail.ProductDetailActivity;
import com.ody.p2p.productdetail.productdetail.bean.RecommendAdapterBean;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by lxs on 2016/8/16.
 */
public class RecommendAdapter extends BaseRecyclerViewAdapter<RecommendAdapterBean.RecommendData> {
    int themeColor = R.color.theme_color;

    public RecommendAdapter(Context context, List datas, int themeColor) {
        super(context, datas);
        this.themeColor = themeColor;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {

        LoveGuessViewHolder viewHolder = (LoveGuessViewHolder) holder;
        viewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
        viewHolder.tv_productcost_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tv_product_cost.setTextColor(mContext.getResources().getColor(themeColor));


        viewHolder.tv_product_name.setText(mDatas.get(position).getProdutName());
        if (0 != mDatas.get(position).getPromotionPrice()) {//有促销价的时候
            viewHolder.tv_product_cost.setText(UiUtils.getMoney(mContext, mDatas.get(position).getPromotionPrice()));
            viewHolder.tv_productcost_old.setText(UiUtils.getMoney(mContext, mDatas.get(position).getProdutPrice()));
        } else {
            viewHolder.tv_product_cost.setText(UiUtils.getMoney(mContext, mDatas.get(position).getProdutPrice()));
            viewHolder.tv_productcost_old.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(mDatas.get(position).getProdutImgUrl())) {
            GlideUtil.display(mContext, mDatas.get(position).getProdutImgUrl() + "").into(viewHolder.iv_product_pic);
        } else {
            ProductDetailActivity activity = (ProductDetailActivity) mContext;
            viewHolder.iv_product_pic.setImageResource(activity.getDefaultImg());
        }
        viewHolder.tv_product_sold.setText(mContext.getString(R.string.already_sell) + mDatas.get(position).getProdutSales() + mContext.getString(R.string.pen));

         /*List<Integer> proUrlList = new ArrayList<>();
        proUrlList.add(R.drawable.icon_reduce);
        proUrlList.add(R.drawable.icon_reduce);
        proUrlList.add(R.drawable.icon_reduce);*/
        if (null != mDatas.get(position).getPromotionInfo() && mDatas.get(position).getPromotionInfo().size() > 0) {
            viewHolder.recycler_promotion.setVisibility(View.VISIBLE);
            PromotionAdapter promotionAdapter = new PromotionAdapter(mContext, mDatas.get(position).getPromotionInfo());
            LinearLayoutManager rankLayoutManager = new LinearLayoutManager(mContext);
            rankLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.recycler_promotion.setLayoutManager(rankLayoutManager);
            viewHolder.recycler_promotion.setAdapter(promotionAdapter);
        }

        viewHolder.iv_addtocart.setVisibility(View.GONE);
       /* loveHolder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendAdapterCallBack.addCart(bean.get(position).getMpId()+"",1);
            }
        });*/
        viewHolder.rl_search_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendAdapterCallBack.itmeOnclik(mDatas.get(position).getMpId() + "");
            }
        });

    }

    RecommendAdapterCallBack recommendAdapterCallBack;

    public void setRecommendAdapterCallBack(RecommendAdapterCallBack recommendAdapterCallBack) {
        this.recommendAdapterCallBack = recommendAdapterCallBack;
    }

    public interface RecommendAdapterCallBack {
        void itmeOnclik(String s);

    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        LoveGuessViewHolder holder = new LoveGuessViewHolder(LayoutInflater.from(mContext).inflate(R.layout.produt_item_recycler_recommend, parent, false));
        return holder;
    }


    public static class LoveGuessViewHolder extends BaseRecyclerViewHolder {

        public ImageView iv_product_pic;
        public TextView tv_product_name;
        public TextView tv_product_cost;
        public ImageView iv_addtocart;
        public RecyclerView recycler_promotion;
        public RelativeLayout rl_search_item;
        public TextView tv_product_sold;
        public TextView tv_productcost_old;


        public LoveGuessViewHolder(View view) {
            super(view);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_cost = (TextView) view.findViewById(R.id.tv_product_cost);
            iv_product_pic = (ImageView) view.findViewById(R.id.iv_product_pic);
            iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
            recycler_promotion = (RecyclerView) view.findViewById(R.id.recycler_promotion);
            rl_search_item = (RelativeLayout) view.findViewById(R.id.rl_search_item);
            tv_product_sold = (TextView) view.findViewById(R.id.tv_product_sold);
            tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);

        }
    }
}
