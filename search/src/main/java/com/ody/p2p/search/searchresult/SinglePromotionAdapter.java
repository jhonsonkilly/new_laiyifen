package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by lxs on 2017/2/10.
 */
public class SinglePromotionAdapter extends BaseRecyclerViewAdapter{

    public SinglePromotionAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        PromotionDetailBean.SingleGiftInfoVO bean= (PromotionDetailBean.SingleGiftInfoVO) getDatas().get(position);
        GiftViewHolder holder1 = (GiftViewHolder) holder;
        GlideUtil.display(mContext, bean.picUrl).override(200, 200).into(holder1.img_changegif_item_pic);
        holder1.img_changegif_item_gifname.setText(bean.giftName);
        holder1.img_changegif_item_gifprice.setText(mContext.getResources().getString(R.string.money_symbol) + bean.price);
        holder1.img_changegif_item_num.setText("x" + bean.giftNum);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new GiftViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_promotion_item, parent, false)));
    }

    //
    public static class GiftViewHolder extends BaseRecyclerViewHolder {

        ImageView img_changegif_item_pic;
        TextView img_changegif_item_gifname, img_changegif_item_gifprice, img_changegif_item_num;

        public GiftViewHolder(View itemView) {
            super(itemView);
            img_changegif_item_pic = (ImageView) itemView.findViewById(R.id.img_changegif_item_pic);
            img_changegif_item_num = (TextView) itemView.findViewById(R.id.img_changegif_item_num);
            img_changegif_item_gifname = (TextView) itemView.findViewById(R.id.img_changegif_item_gifname);
            img_changegif_item_gifprice = (TextView) itemView.findViewById(R.id.img_changegif_item_gifprice);
        }
    }
}
