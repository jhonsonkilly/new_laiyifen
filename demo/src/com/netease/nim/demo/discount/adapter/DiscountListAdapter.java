package com.netease.nim.demo.discount.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.main.util.TimeUtil;

import java.util.List;


/**
 * @author SevenCheng
 */

public class DiscountListAdapter extends RecyclerView.Adapter {

    private List<YHQModel.DataBean.CanUseCouponListBean> discountModel;
    private OnItemclickListener                          mOnItemclickListener;

    public DiscountListAdapter(List<YHQModel.DataBean.CanUseCouponListBean> discountModel, OnItemclickListener onItemclickListener) {
        this.mOnItemclickListener = onItemclickListener;
        this.discountModel = discountModel;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discountcard_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final YHQModel.DataBean.CanUseCouponListBean discountModel = this.discountModel.get(position);
            if (discountModel.isShow()) {
                viewHolder.mLlPwdInfo.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mLlPwdInfo.setVisibility(View.GONE);
            }
            viewHolder.rl_arrows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (discountModel.isShow()) {
                        viewHolder.mIvArrows.setImageResource(R.drawable.arrows_down);
                        discountModel.setShow(false);
                        notifyDataSetChanged();
                    } else {
                        viewHolder.mIvArrows.setImageResource(R.drawable.arrows_up);
                        discountModel.setShow(true);
                        notifyDataSetChanged();
                    }
                }
            });

            viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemclickListener != null) {
                        mOnItemclickListener.onClick(discountModel);
                    }
                }
            });

            viewHolder.mAmount.setText("¥" + discountModel.getCouponValue());
            viewHolder.mName.setText(discountModel.getThemeTitle());
            String endTiem = discountModel.getEndTime() + "";
            if (endTiem.length() < 13) {
                endTiem = endTiem + "000";
            }
            viewHolder.mTime.setText("有效期 " + TimeUtil.getStrTime(endTiem));
            viewHolder.mPwd.setText(discountModel.getPwd() + "");
            viewHolder.mCardno.setText(discountModel.getCouponCode());
            //            viewHolder.mMsg.setText(discountModel.getUseRange());

            int useRange = discountModel.getUseRange();
            if (useRange == 1) { //线上
                viewHolder.bac.setBackgroundResource(R.drawable.yhq_xianshang);
            } else if (useRange == 2) { //线下
                viewHolder.bac.setBackgroundResource(R.drawable.item_discount_background);

            } else if (useRange == 3) { //通用
                viewHolder.bac.setBackgroundResource(R.drawable.yhq_ty);

            }

        }

    }

    @Override
    public int getItemCount() {
        return discountModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mItemView;
        RelativeLayout rl_arrows;
        TextView       mAmount;
        TextView       mName;
        TextView       mMsg;
        TextView       mTime;
        ImageView      mIvArrows;
        TextView       mCardno;
        TextView       mPwd;
        LinearLayout   mLlPwdInfo;
        LinearLayout   bac;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            rl_arrows = (RelativeLayout) itemView.findViewById(R.id.rl_arrows);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMsg = (TextView) itemView.findViewById(R.id.msg);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mIvArrows = (ImageView) itemView.findViewById(R.id.iv_arrows);
            mCardno = (TextView) itemView.findViewById(R.id.cardno);
            mPwd = (TextView) itemView.findViewById(R.id.pwd);
            mLlPwdInfo = (LinearLayout) itemView.findViewById(R.id.ll_pwd_info);
            bac = (LinearLayout) itemView.findViewById(R.id.ll_bac);
        }
    }

    public interface OnItemclickListener {
        void onClick(YHQModel.DataBean.CanUseCouponListBean bean);
    }


}
