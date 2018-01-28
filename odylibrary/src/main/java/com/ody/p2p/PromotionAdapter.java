package com.ody.p2p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * 公共的促销标签
 */
public class PromotionAdapter extends BaseRecyclerViewAdapter<PromotionInfo> {

    public PromotionAdapter(Context context, List datas) {
        super(context, datas);
    }

    int gapDistance=0;
    int width=0;
    int height=0;

    /**
     * 设置间距、宽、高
     * @param gapDistance   间距
     * @param width         宽
     * @param height        高
     */
    public void setGapDistance(int gapDistance,int width,int height) {
        this.gapDistance = gapDistance;
        this.width = width;
        this.height = height;
        notifyDataSetChanged();
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        PromotionViewHolder viewHodler = (PromotionViewHolder) holder;
        if (position < showCount) {//目前是只展示前三个
            if (!StringUtils.isEmpty(mDatas.get(position).getIconUrl())) {
                GlideUtil.display(mContext, mDatas.get(position).getIconUrl()).into(viewHodler.iv_promotion);
                if (gapDistance!=0||width!=0||height!=0){
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    if (gapDistance!=0){
                        lp.setMargins(0, 0,gapDistance, 0);
                    }
                    if (width!=0){
                        lp.width=width;
                    }
                    if(height!=0){
                        lp.height=height;
                    }
                    viewHodler.iv_promotion.setLayoutParams(lp);
                }
            } else {
                viewHodler.iv_promotion.setImageResource(R.drawable.item_activity);
            }
        }else {
            viewHodler.iv_promotion.setVisibility(View.GONE);
        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new PromotionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.produt_item_promotion, parent, false));
    }

    public static class PromotionViewHolder extends BaseRecyclerViewHolder {

        public ImageView iv_promotion;

        public PromotionViewHolder(View view) {
            super(view);
            iv_promotion = (ImageView) view.findViewById(R.id.iv_promotion);
        }
    }


    private int showCount = 3;

    public void setPromotionCount(int showCount){
        this.showCount = showCount;
    }

}
