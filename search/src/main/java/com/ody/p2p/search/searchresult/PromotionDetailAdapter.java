package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by lxs on 2017/2/10.
 */
public class PromotionDetailAdapter extends BaseRecyclerViewAdapter{

    public PromotionDetailAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        PromotionDetailBean.PromotionGiftDetailVO bean = (PromotionDetailBean.PromotionGiftDetailVO) getDatas().get(position);
        GiftViewHolder holder1 = (GiftViewHolder) holder;
        holder1.tv_promotion.setText(bean.description);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder1.recycler_promotion_single.setLayoutManager(manager);
        SinglePromotionAdapter adapter = new SinglePromotionAdapter(mContext,bean.singleGiftInfoList);
        holder1.recycler_promotion_single.setAdapter(adapter);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new GiftViewHolder(LayoutInflater.from(mContext).inflate(R.layout.promotion_item, parent, false)));
    }

    //
    public static class GiftViewHolder extends BaseRecyclerViewHolder {

        TextView tv_promotion;
        RecyclerView recycler_promotion_single;

        public GiftViewHolder(View itemView) {
            super(itemView);
            tv_promotion = (TextView) itemView.findViewById(R.id.tv_promotion);
            recycler_promotion_single = (RecyclerView) itemView.findViewById(R.id.recycler_promotion_single);
        }
    }
}
