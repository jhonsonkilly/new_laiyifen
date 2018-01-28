package com.ody.p2p.productdetail.productdetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.productdetail.ProductDetailActivity;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * Created by ody on 2016/8/17.
 */
public class ProdutPresenAdapter extends BaseRecyclerViewAdapter {
    int giftType;

    public ProdutPresenAdapter(Context context, List datas) {
        super(context, datas);
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        ProductDetailActivity activity = (ProductDetailActivity) mContext;

        if (1 == giftType) {
            viewHodler.rl_coupon.setVisibility(View.GONE);
            viewHodler.ll_giftinfo.setVisibility(View.VISIBLE);
            List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList.SingleGiftInfoVOList> list = getDatas();
            if (!StringUtils.isEmpty(list.get(position).getPicUrl())) {
                GlideUtil.display(mContext, list.get(position).getPicUrl()).into(viewHodler.img);
            } else {
                viewHodler.img.setImageResource(activity.getDefaultImg());
            }
            if (list.get(position).soldOut == 1) {
                viewHodler.txt_gifsoldout.setVisibility(View.VISIBLE);
            } else {
                viewHodler.txt_gifsoldout.setVisibility(View.GONE);
            }
            if (Integer.parseInt(list.get(position).getGiftNum()) == 0) {
                viewHodler.textNum.setVisibility(View.GONE);
            } else {
                viewHodler.textNum.setText("×" + list.get(position).getGiftNum());
            }
            viewHodler.textState.setText(list.get(position).getGiftName());

        } else if (2 == giftType) {
            List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList.singleCouponInfoList> list = getDatas();

            viewHodler.rl_coupon.setVisibility(View.VISIBLE);
            viewHodler.ll_giftinfo.setVisibility(View.GONE);
            viewHodler.txt_conditionValue.setText(list.get(position).couponAmount + "");
            viewHodler.tx_themeTitle.setText(list.get(position).themeTitle);
            if (Integer.parseInt(list.get(position).soldOut) == 1) {
                viewHodler.rl_coupon.setBackgroundResource(R.drawable.item_couponbg_gray);
                viewHodler.txt_gifsoldout.setVisibility(View.VISIBLE);
                viewHodler.img_couponsoldout.setVisibility(View.VISIBLE);
            } else {
                viewHodler.txt_gifsoldout.setVisibility(View.GONE);
            }
        }


    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new ProdutPresenAdapter.ViewHodler(LayoutInflater.from(mContext).inflate(R.layout.product_itme_present, parent, false));

    }

    class ViewHodler extends BaseRecyclerViewHolder {
        LinearLayout ll_giftinfo;
        View layout_adress;
        ImageView img;
        TextView textNum;
        TextView textState;
        TextView txt_gifsoldout;

        RelativeLayout rl_coupon;
        TextView txt_conditionValue;
        TextView tx_themeTitle;
        ImageView img_couponsoldout;


        public ViewHodler(View itemView) {
            super(itemView);
            ll_giftinfo = (LinearLayout) itemView.findViewById(R.id.ll_giftinfo);
            img = (ImageView) itemView.findViewById(R.id.img_imge);
            textNum = (TextView) itemView.findViewById(R.id.txt_num);
            textState = (TextView) itemView.findViewById(R.id.txt_state);
            txt_gifsoldout = (TextView) itemView.findViewById(R.id.txt_gifsoldout);
            rl_coupon = (RelativeLayout) itemView.findViewById(R.id.rl_coupon);
            txt_conditionValue = (TextView) itemView.findViewById(R.id.txt_conditionValue);
            tx_themeTitle = (TextView) itemView.findViewById(R.id.tx_themeTitle);
            img_couponsoldout = (ImageView) itemView.findViewById(R.id.img_couponsoldout);
        }
    }
}
