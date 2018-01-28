package com.netease.nim.demo.yidiancard.adapter;

import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.yidiancard.model.YKD001Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * @author SevenCheng
 */

public class YDCardListAdapter extends RecyclerView.Adapter {

    private List<YKD001Model.RowsBean> discountModel;
    private OnItemclickListener        mOnItemclickListener;

    public YDCardListAdapter(List<YKD001Model.RowsBean> discountModel, OnItemclickListener onItemclickListener) {
        this.mOnItemclickListener = onItemclickListener;
        this.discountModel = discountModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ydcard_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final YKD001Model.RowsBean discountModel = this.discountModel.get(position);
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


            viewHolder.mAmount.setText("¥" + discountModel.getAmount());
            viewHolder.mTitle.setText(discountModel.getIntro());
            //            viewHolder.msg.setText(discountModel.getType());


            viewHolder.mTime.setText("有效期:" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Long(discountModel.getEndTime() + "000")))));
            viewHolder.mCardNo.setText(discountModel.getCardNo() + "");
            viewHolder.mPwd.setText(discountModel.getPwd() + "");


        }

    }

    @Override
    public int getItemCount() {
        return discountModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mItemView;
        RelativeLayout rl_arrows;
        TextView       mTitle;
        TextView       mAmount;
        TextView       mTime;
        ImageView      mIvArrows;

        TextView     mCardNo;
        TextView     mPwd;
        LinearLayout mLlPwdInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            rl_arrows = (RelativeLayout) itemView.findViewById(R.id.rl_arrows);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mIvArrows = (ImageView) itemView.findViewById(R.id.iv_arrows);
            mCardNo = (TextView) itemView.findViewById(R.id.cardNo);
            mPwd = (TextView) itemView.findViewById(R.id.pwd);
            mLlPwdInfo = (LinearLayout) itemView.findViewById(R.id.ll_pwd_info);
        }
    }

    public interface OnItemclickListener {
        void onClick(YKD001Model.RowsBean discountModel);
    }


}
