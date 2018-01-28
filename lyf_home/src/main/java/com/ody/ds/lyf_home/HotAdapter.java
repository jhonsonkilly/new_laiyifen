package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ResultBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.util.List;

/**
 * Created by lxs on 2016/12/6.
 */
public class HotAdapter extends BaseRecyclerViewAdapter {

    public AppHomePageBean.StaticData staticData;

    public HotAdapter(Context context, List datas,AppHomePageBean.StaticData staticData) {
        super(context, datas);
        this.staticData = staticData;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ModuleDataBean.CmsModuleDataVO bean = (ModuleDataBean.CmsModuleDataVO) getDatas().get(position);
        HotViewHolder holder1 = (HotViewHolder) holder;
        if (bean.jumpType == ResultBean.JUMP_DETAIL){
            holder1.iv_home_rank_details.setVisibility(View.VISIBLE);
            holder1.rl_content.setVisibility(View.GONE);
            holder1.iv_home_rank_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (staticData != null && staticData.link != null){
                        JumpUtils.ToActivity(staticData.link.appData);
                    }
                }
            });
        }else {
            holder1.rl_content.setVisibility(View.VISIBLE);
            holder1.iv_home_rank_details.setVisibility(View.GONE);
            holder1.tv_rank.setText((position + 1) + "");
            holder1.name.setText(bean.mpName);
            holder1.oldPrice.getPaint().setAntiAlias(true);//抗锯齿
            holder1.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (!StringUtils.isEmpty(bean.promotionPrice)){
                holder1.price.setText("¥" + UiUtils.getDoubleForDouble(bean.promotionPrice));
                holder1.oldPrice.setText("¥" + UiUtils.getDoubleForDouble(bean.price));
                holder1.oldPrice.setVisibility(View.VISIBLE);
            }else {
                holder1.price.setText("¥" + UiUtils.getDoubleForDouble(bean.price));
                holder1.oldPrice.setVisibility(View.GONE);
            }
            GlideUtil.display(mContext, bean.picUrl).into(holder1.iv_rank);
            ((HotViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(Constants.SP_ID, bean.mpId);
                    JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
                }
            });
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new HotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hot_item, parent, false)));
    }

    //特色主题
    public static class HotViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_rank;
        public ImageView iv_rank;
        private TextView name;
        private TextView price;
        private ImageView iv_home_rank_details;
        private RelativeLayout rl_content;
        private TextView oldPrice;


        public HotViewHolder(View view) {
            super(view);
            tv_rank = (TextView) view.findViewById(R.id.tv_rank);
            iv_rank = (ImageView) view.findViewById(R.id.iv_rank);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            oldPrice = (TextView) view.findViewById(R.id.price_old);
            rl_content = (RelativeLayout) view.findViewById(R.id.rl_content);
            iv_home_rank_details = (ImageView) view.findViewById(R.id.iv_home_rank_details);
        }
    }


}
