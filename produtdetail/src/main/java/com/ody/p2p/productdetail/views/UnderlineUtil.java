package com.ody.p2p.productdetail.views;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.produtdetail.R;

import java.util.List;

public class UnderlineUtil {

    ViewPager mPager;//页卡内容
    int bmpW = 0;// 保存移动位置
    int num;// 当前页卡
    Activity mContext;
    ImageView mCursor;//动画图片
    List<TextView> tvliList;// 页卡头标
    int color = R.color.theme_color;

    public UnderlineUtil(ViewPager mPager, Activity context, ImageView cursor,
                         List<TextView> tvliList) {
        super();
        this.mPager = mPager;
        this.mContext = context;
        this.mCursor = cursor;
        this.tvliList = tvliList;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            textColor();
            if (bmpW == 0) {
                bmpW = mCursor.getLeft();
            }
            int destCoordinate = 0;
            if (num > arg0) {
                destCoordinate = bmpW - mCursor.getWidth() * (num - arg0);
            } else {
                destCoordinate = bmpW + mCursor.getWidth() * (arg0 - num);
            }
            Animation animation = new TranslateAnimation(bmpW, destCoordinate, 0, 0);
            tvliList.get(arg0).setTextColor(mContext.getResources().getColor(color));
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            mCursor.startAnimation(animation);
            num = arg0;
            bmpW = destCoordinate;
            Log.d("onPageSelected", "......" + bmpW + "......" + destCoordinate);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void textColor() {
        for (int i = 0; i < tvliList.size(); i++) {
            tvliList.get(i).setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
        }
    }

}
