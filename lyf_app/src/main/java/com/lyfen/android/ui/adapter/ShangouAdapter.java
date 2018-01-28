package com.lyfen.android.ui.adapter;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.activity.ShangouEntity;
import com.lyfen.android.event.ShanGouEvent;
import com.lyfen.android.hybird.UrlEntity;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.Constants;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class ShangouAdapter extends RecyclerView.Adapter<ShangouAdapter.LvListHolder> {

    List<ShangouEntity.DataEntity> mErchantProducts;
    int mpostion;

    public ShangouAdapter(int mPosition) {
        this.mpostion = mPosition;
    }


    @Override
    public LvListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = View.inflate(parent.getContext(), R.layout.item_fragment_todayflash, null);

        return new LvListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(LvListHolder holder, int position) {
        holder.setData(mErchantProducts.get(position));


    }

    @Override
    public int getItemCount() {
        return mErchantProducts.size();
    }

    public void setData(List<ShangouEntity.DataEntity> merchantProducts) {
        mErchantProducts = merchantProducts;

    }

    public class LvListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.common_tv_vertical_number_1)
        TextView commonTvVerticalNumber1;
        @Bind(R.id.common_img_horizontal_number_1)
        SimpleDraweeView commonImgHorizontalNumber1;
        @Bind(R.id.common_img_horizontal_number_3)
        ImageView commonImgHorizontalNumber3;
        @Bind(R.id.common_rllayout_horizontal_number_3)
        RelativeLayout commonRllayoutHorizontalNumber3;
        @Bind(R.id.common_tv_horizontal_number_1)
        TextView commonTvHorizontalNumber1;
        @Bind(R.id.common_tv_horizontal_number_2)
        TextView commonTvHorizontalNumber2;
        @Bind(R.id.common_lllayout_horizontal_number_1)
        LinearLayout commonLllayoutHorizontalNumber1;
        @Bind(R.id.common_rllayout_horizontal_number_1)
        RelativeLayout commonRllayoutHorizontalNumber1;
        @Bind(R.id.common_tv_horizontal_number_4)
        TextView commonTvHorizontalNumber4;
        @Bind(R.id.ll_name)
        LinearLayout llName;
        @Bind(R.id.common_tv_horizontal_number_5)
        TextView commonTvHorizontalNumber5;
        @Bind(R.id.common_tv_horizontal_number_6)
        TextView commonTvHorizontalNumber6;
        @Bind(R.id.ll_price)
        LinearLayout llPrice;
        @Bind(R.id.common_tv_horizontal_number_7)
        TextView commonTvHorizontalNumber7;
        @Bind(R.id.common_pb_horizontal_number_1)
        ProgressBar commonPbHorizontalNumber1;
        @Bind(R.id.common_tv_horizontal_number_8)
        TextView commonTvHorizontalNumber8;
        @Bind(R.id.ll_size)
        LinearLayout llSize;
        @Bind(R.id.common_tv_horizontal_number_10)
        TextView commonTvHorizontalNumber10;
        //        @Bind(R.id.common_img_horizontal_number_2)
//        ImageView commonImgHorizontalNumber2;
        @Bind(R.id.common_lllayout_horizontal_number_2)
        LinearLayout commonLllayoutHorizontalNumber2;
        @Bind(R.id.common_rllayout_horizontal_number_2)
        RelativeLayout commonRllayoutHorizontalNumber2;
        @Bind(R.id.common_tv_horizontal_number_11)
        TextView commonTvHorizontalNumber11;
        @Bind(R.id.common_cv_horizontal_number_1)
        CardView commonCvHorizontalNumber1;
        @Bind(R.id.rl_root)
        RelativeLayout relativeLayout;

        CountDownTimer timer;


        public LvListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ShangouEntity.DataEntity merchantProductsEntity) {
            relativeLayout.setOnClickListener(view -> {

                UrlEntity urlEntity = new UrlEntity();
                urlEntity.mpId = merchantProductsEntity.merchantProducts.get(0).mpId + "";

                //RouterHelper.getHelper().Native2Activity(RouterHelper.getHelper().Schema(ProductDectilActivity.class.getName()), urlEntity);
            });
            FrescoUtils.displayUrl(commonImgHorizontalNumber1, merchantProductsEntity.merchantProducts.get(0).picUrl);


            long time = Long.parseLong(merchantProductsEntity.merchantProducts.get(0).startTime) + 86400000 - System.currentTimeMillis();

            timer = new CountDownTimer(time, 1000) {

                public void onTick(final long millisUntilFinished) {


                    commonTvHorizontalNumber2.setText(getTimeShort(millisUntilFinished / 1000));


                }

                public void onFinish() {


                    timer.cancel();


                }
            };
            timer.start();

            commonTvHorizontalNumber4.setText(merchantProductsEntity.merchantProducts.get(0).name);
            commonTvHorizontalNumber6.setText("¥" + merchantProductsEntity.merchantProducts.get(0).originalPrice);
            commonTvHorizontalNumber6.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            commonTvHorizontalNumber5.setText("¥" + merchantProductsEntity.merchantProducts.get(0).promotionPrice);
            commonTvHorizontalNumber7.setText("限量" + sub(merchantProductsEntity.merchantProducts.get(0).stockNum
                    , merchantProductsEntity.merchantProducts.get(0).saleStock) + "份");


            commonTvHorizontalNumber8.setText("剩余" + pre(merchantProductsEntity.merchantProducts.get(0).stockNum,
                    sub(merchantProductsEntity.merchantProducts.get(0).stockNum
                            , merchantProductsEntity.merchantProducts.get(0).saleStock)) + "%");


            if (mpostion == 0) {

                commonTvHorizontalNumber1.setText("距离结束");
                commonTvHorizontalNumber10.setText("马上抢");

                commonTvHorizontalNumber2.setVisibility(View.VISIBLE);
                commonTvHorizontalNumber11.setVisibility(View.GONE);
            } else {
                commonTvHorizontalNumber2.setVisibility(View.GONE);
                commonTvHorizontalNumber1.setText("每日 00:00:00 准时开抢");
                commonTvHorizontalNumber10.setText("开启提醒");
                commonTvHorizontalNumber10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commonTvHorizontalNumber10.getText().equals("开启提醒")) {
                            ShanGouEvent event = new ShanGouEvent();
                            event.promotionId = merchantProductsEntity.merchantProducts.get(0).promotionId;
                            event.mpId = merchantProductsEntity.merchantProducts.get(0).mpId;
                            event.time = Long.parseLong(merchantProductsEntity.merchantProducts.get(0).startTime) / 1000;
                            event.textView = commonTvHorizontalNumber10;
                            event.isNotify = true;
                            event.type = 1;
                            EventBus.getDefault().post(event);
                        } else {
                            ShanGouEvent event = new ShanGouEvent();
                            event.promotionId = merchantProductsEntity.merchantProducts.get(0).promotionId;
                            event.mpId = merchantProductsEntity.merchantProducts.get(0).mpId;
                            event.time = Long.parseLong(merchantProductsEntity.merchantProducts.get(0).startTime) / 1000;
                            event.textView = commonTvHorizontalNumber10;
                            event.isNotify = false;
                            event.type = 1;
                            EventBus.getDefault().post(event);
                        }
                    }
                });
                //commonRllayoutHorizontalNumber2.setBackgroundColor(ContextCompat.getColor(UIUtils.getContext(), R.color.text_med_grey));
                commonTvHorizontalNumber11.setVisibility(View.VISIBLE);

                commonTvHorizontalNumber11.setText(merchantProductsEntity.merchantProducts.get(0).noticeCount + "人已关注");

            }
            commonPbHorizontalNumber1.setMax(Integer.parseInt("100"));

            commonCvHorizontalNumber1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bd = new Bundle();
                    bd.putString(Constants.SP_ID, merchantProductsEntity.merchantProducts.get(0).mpId);
                    JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                }
            });



            doValueAnimator(commonPbHorizontalNumber1, Double.parseDouble(pre(merchantProductsEntity.merchantProducts.get(0).stockNum,
                    sub(merchantProductsEntity.merchantProducts.get(0).stockNum
                            , merchantProductsEntity.merchantProducts.get(0).saleStock))));

        }
    }

    public int sub(int a, int b) {
        return a + b;
    }

    public String pre(int a, int b) {
        NumberFormat numberFormat = NumberFormat.getInstance();


        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format((float) a / (float) b * 100);


    }

    public void updateState(TextView textView, boolean isNotify) {
        if (isNotify) {

            textView.setBackgroundColor(ContextCompat.getColor(UIUtils.getContext(), R.color.text_med_grey));
            textView.setText("取消提醒");
        } else {

            textView.setBackgroundColor(ContextCompat.getColor(UIUtils.getContext(), R.color.colorAccent));
            textView.setText("开启提醒");
        }


    }

    public void doValueAnimator(ProgressBar progressBar, double a) {

        try {
            double v = a * 100;
            String s = String.valueOf(v);

            String substring = s.substring(0, s.length() - 4);

            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, Integer.parseInt(substring));
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(animation -> {
                Integer value = (Integer) animation.getAnimatedValue();
                progressBar.setProgress(value);
            });
            valueAnimator.start();
        } catch (Exception e) {

        }

    }

    public String getTimeShort(long i) {

        long h = i / 3600;
        long m = (i % 3600) / 60;
        long s = (i % 3600) % 60;


        String dateString = +h + ":" + m + ":" + s;

        if (m < 10) {
            dateString = +h + ":" + "0" + m + ":" + s;
        }
        if (s < 10) {
            dateString = +h + ":" + m + ":" + "0" + s;
        }
        if (m < 10 && s < 10) {

            dateString = +h + ":" + "0" + m + ":" + "0" + s;
        }

        return dateString;
    }
}
