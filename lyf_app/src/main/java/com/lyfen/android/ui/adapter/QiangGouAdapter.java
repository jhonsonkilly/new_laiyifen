package com.lyfen.android.ui.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;

import com.lyfen.android.entity.network.activity.QiangGouFrgBean;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.activity.qianggou.QiangGouActivity;
import com.ody.p2p.Constants;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;


/**
 * Created by mac on 2017/7/21.
 */

public class QiangGouAdapter extends RecyclerView.Adapter<QiangGouAdapter.MyViewHolder> {


    Context context;


    QiangGouFrgBean.QiangGouData.QiangGouList currentShop;


    public QiangGouAdapter(Context context, QiangGouFrgBean.QiangGouData.QiangGouList currentShop) {
        this.context = context;


        this.currentShop = currentShop;

    }

    public void setData(QiangGouFrgBean.QiangGouData.QiangGouList currentShop) {
        this.currentShop = currentShop;
    }

    @Override
    public QiangGouAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_qianggou, parent,
                false));
        return holder;

    }

    public void updateState(TextView textView, boolean isNotify) {
        if (isNotify) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.rectangle_bead));
            textView.setText("取消提醒");
        } else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.CFF6900));
            textView.setText("提醒我");
        }


    }

    @Override
    public void onBindViewHolder(final QiangGouAdapter.MyViewHolder holder, int position) {
        final QiangGouFrgBean.QiangGouData.QiangGouList.MerchantProducts bean = currentShop.merchantProducts.get(position);

        FrescoUtils.displayUrl(holder.mCommonImgHorizontalNumber1, bean.picUrl);

        holder.mCommonTvHorizontalNumber1.setText(bean.name);
        holder.mCommonTvHorizontalNumber2.setText(bean.originalPrice);
        holder.mCommonTvHorizontalNumber3.setText("¥" + bean.promotionPrice);
        holder.mProgressBar.setMax(Integer.parseInt(bean.allStock));
        holder.mrelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putString(Constants.SP_ID, bean.mpId);
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
            }
        });

        doValueAnimator(holder.mProgressBar, (Integer.parseInt(bean.saleStock)));


        switch (bean.status) {
            case "1":
                holder.mCommonTvHorizontalNumber6.setText(bean.noticeStatusStr);
                if (bean.noticeStatusStr.equals("提醒我")) {
                    holder.mCommonTvHorizontalNumber6.setBackgroundColor(context.getResources().getColor(R.color.CFF6900));
                } else {
                    holder.mCommonTvHorizontalNumber6.setBackgroundColor(context.getResources().getColor(R.color.rectangle_bead));
                }

                holder.mCommonTvHorizontalNumber5.setVisibility(View.VISIBLE);
                holder.mCommonTvHorizontalNumber5.setText(bean.noticeCount + "人已关注");
                holder.mCommonTvHorizontalNumber4.setVisibility(View.GONE);

                break;

            case "2":

                holder.mCommonTvHorizontalNumber6.setText(bean.statusStr);
                holder.mCommonTvHorizontalNumber6.setBackgroundColor(context.getResources().getColor(R.color.CFF6900));
                holder.mCommonTvHorizontalNumber4.setVisibility(View.VISIBLE);
                holder.mCommonTvHorizontalNumber4.setText(bean.getSaleProgress(bean.allStock, bean.saleStock));
                holder.mCommonTvHorizontalNumber5.setVisibility(View.GONE);
                break;

            default:

                holder.mCommonTvHorizontalNumber6.setText(bean.statusStr);
                holder.mCommonTvHorizontalNumber6.setBackgroundColor(context.getResources().getColor(R.color.rectangle_bead));
                holder.mCommonTvHorizontalNumber4.setVisibility(View.VISIBLE);
                holder.mCommonTvHorizontalNumber4.setText(bean.getSaleProgress(bean.allStock, bean.saleStock));
                holder.mCommonTvHorizontalNumber5.setVisibility(View.GONE);
                break;
        }
        holder.mCommonTvHorizontalNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分三种一种提醒，一种不提醒,一种加入购物车

                if (LoginHelper.isLogin()) {
                    if (bean.status.equals("2")) {
                        QiangGouActivity act = (QiangGouActivity) context;
                        act.addToCart(bean.mpId);
                    } else if (bean.status.equals("1")) {
                        if (holder.mCommonTvHorizontalNumber6.getText().equals("提醒我")) {
                            //要提醒的
                            QiangGouActivity act = (QiangGouActivity) context;

                            act.notifyMes(bean.promotionId, bean.mpId, Long.parseLong(currentShop.startTime) / 1000, holder.mCommonTvHorizontalNumber6);
                        } else {
                            //不要提醒的
                            QiangGouActivity act = (QiangGouActivity) context;
                            act.cancelNotifyMes(bean.promotionId, bean.mpId, currentShop.startTime, holder.mCommonTvHorizontalNumber6);
                        }

                    }
                } else {
                    JumpUtils.ToActivity(JumpUtils.LOGIN);
                }


            }
        });

        holder.mrelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Bundle bd = new Bundle();
                bd.putString(Constants.SP_ID, bean.mpId);
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
            }
        });


    }

    @Override
    public int getItemCount() {
        return currentShop.merchantProducts.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mrelDetail;
        ImageView mCommonImgHorizontalNumber2;
        SimpleDraweeView mCommonImgHorizontalNumber1;
        TextView mCommonTvHorizontalNumber1;
        TextView mCommonTvHorizontalNumber2;
        TextView mCommonTvHorizontalNumber3;
        TextView mCommonTvHorizontalNumber4;
        TextView mCommonTvHorizontalNumber5;
        TextView mCommonTvHorizontalNumber6;
        ProgressBar mProgressBar;

        public MyViewHolder(View view) {
            super(view);
            mrelDetail = (RelativeLayout) view.findViewById(R.id.rel_detail);
            mCommonImgHorizontalNumber2 = (ImageView) view.findViewById(R.id.common_img_horizontal_number_2);
            mCommonImgHorizontalNumber1 = (SimpleDraweeView) view.findViewById(R.id.common_img_horizontal_number_1);
            mCommonTvHorizontalNumber1 = (TextView) view.findViewById(R.id.common_tv_horizontal_number_1);
            mCommonTvHorizontalNumber2 = (TextView) view.findViewById(R.id.common_btn_horizontal_number_2);
            mCommonTvHorizontalNumber2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            mCommonTvHorizontalNumber3 = (TextView) view.findViewById(R.id.common_btn_horizontal_number_3);
            mCommonTvHorizontalNumber4 = (TextView) view.findViewById(R.id.common_btn_horizontal_number_4);
            mCommonTvHorizontalNumber5 = (TextView) view.findViewById(R.id.common_btn_horizontal_number_5);
            mCommonTvHorizontalNumber6 = (TextView) view.findViewById(R.id.common_btn_horizontal_number_6);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progress);


        }
    }

    public void doValueAnimator(final ProgressBar progressBar, int a) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, a);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });
        valueAnimator.start();
    }


}
