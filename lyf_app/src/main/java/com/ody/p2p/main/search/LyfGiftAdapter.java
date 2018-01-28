package com.ody.p2p.main.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.search.searchresult.CartExtBean;
import com.ody.p2p.utils.GlideUtil;


import java.util.List;

/**
 * Created by lxs on 2016/12/26.
 */
public class LyfGiftAdapter extends BaseRecyclerViewAdapter {

    public LyfGiftAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        CartExtBean.GiftProduct bean = (CartExtBean.GiftProduct) getDatas().get(position);
        GiftViewHolder holder1 = (GiftViewHolder) holder;
        holder1.relative_guige.setVisibility(View.GONE);
        holder1.img_changegif_item_checked.setVisibility(View.GONE);
        GlideUtil.display(mContext, bean.picUrl).override(200, 200).into(holder1.img_changegif_item_pic);
        holder1.img_changegif_item_gifname.setText(bean.giftName);
        holder1.img_changegif_item_gifprice.setText(mContext.getResources().getString(R.string.money_symbol) + bean.price);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new GiftViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_change_gif_item, parent, false)));
    }

    //
    public static class GiftViewHolder extends BaseRecyclerViewHolder {

        RelativeLayout relative_guige;
        RecyclerView img_changegif_item_reclycle;
        ImageView img_changegif_item_checked, img_changegif_item_pic, img_changegif_item_arrow;
        TextView img_changegif_item_gifname, img_changegif_item_gifprice, img_changegif_item_stands;

        public GiftViewHolder(View itemView) {
            super(itemView);
            img_changegif_item_reclycle = (RecyclerView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_reclycle);
            relative_guige = (RelativeLayout) itemView.findViewById(com.ody.p2p.shopcart.R.id.relative_guige);
            img_changegif_item_checked = (ImageView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_checked);
            img_changegif_item_pic = (ImageView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_pic);
            img_changegif_item_arrow = (ImageView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_arrow);

            img_changegif_item_gifname = (TextView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_gifname);
            img_changegif_item_gifprice = (TextView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_gifprice);
            img_changegif_item_stands = (TextView) itemView.findViewById(com.ody.p2p.shopcart.R.id.img_changegif_item_stands);
        }
    }

}
