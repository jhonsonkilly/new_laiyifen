package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * 促销标签
 */
public class PromotionIconAdapter extends BaseRecyclerViewAdapter<String>{

    public PromotionIconAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        PromotionViewHolder viewHodler = (PromotionViewHolder) holder;
        if (null!=mDatas.get(position)&&!"".equals(mDatas.get(position))){
            GlideUtil.display(mContext,mDatas.get(position)).into(viewHodler.iv_promotion);
        }else {
            viewHodler.iv_promotion.setImageResource(R.drawable.item_activity);
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new PromotionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_promotion, parent, false));
    }

    public static class PromotionViewHolder extends BaseRecyclerViewHolder {

        public ImageView iv_promotion;

        public PromotionViewHolder(View view){
            super(view);
            iv_promotion = (ImageView) view.findViewById(R.id.iv_promotion);
        }
    }

}