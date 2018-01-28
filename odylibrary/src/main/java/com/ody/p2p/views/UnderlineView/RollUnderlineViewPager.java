package com.ody.p2p.views.UnderlineView;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.R;

import java.util.List;

public class RollUnderlineViewPager extends FragmentPagerAdapter implements OnPageChangeListener {
    private List<Fragment> mFragmentList;
    ViewPager mPager;//页卡内容
    int bmpW;// 保存移动位置
    int thisNumber;// 当前页卡
    Activity mContext;
    ImageView mCursor;//动画图片
    List<TextView> tvliList;// 页卡头标

    int mSelectColor = R.color.theme_color;
    int mUnSelectColor = R.color.main_title_color;

    /**
     * 滑动的viewpager
     *
     * @param fm
     * @param context
     * @param mPager        滑动的viewpager
     * @param mFragmentList viewpager中的内容（Fragment）
     * @param cursor        滑动的下划线
     * @param tvliList      头
     */
    public RollUnderlineViewPager(FragmentManager fm, Activity context, ViewPager mPager, List<Fragment> mFragmentList, ImageView cursor,
                                  List<TextView> tvliList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mPager = mPager;
        this.mContext = context;
        this.mCursor = cursor;
        this.tvliList = tvliList;
        initView();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    void initView() {
        if (null == mPager || null == tvliList || null == mCursor)
            return;
        bmpW = mCursor.getLeft();
        mPager.addOnPageChangeListener(this);
        if (tvliList.size() > 0) {
            for (int i = 0; i < tvliList.size(); i++) {
                if (i == 0) {
                    tvliList.get(i).setTextColor(mContext.getResources().getColor(mSelectColor));
                }
                tvliList.get(i).setTag(i);
                tvliList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thisNumber = Integer.parseInt(v.getTag().toString());
                        mPager.setCurrentItem(thisNumber);
                    }
                });
            }
        }
    }


    /**
     * 设置选中色、非选中色
     *
     * @param mSelectColor
     * @param mUnSelectColor
     */
    public void setColor(int mSelectColor, int mUnSelectColor) {
        this.mSelectColor = mSelectColor;
        this.mUnSelectColor = mUnSelectColor;
        if (null != tvliList && tvliList.size() > 0) {
            tvliList.get(0).setTextColor(mContext.getResources().getColor(mSelectColor));
        }
        mCursor.setImageResource(mSelectColor);
    }

    @Override
    public void onPageSelected(int arg0) {
        int destCoordinate = tvliList.get(arg0).getLeft();
        Animation animation = new TranslateAnimation(bmpW, destCoordinate, 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        mCursor.startAnimation(animation);
        thisNumber = arg0;
        bmpW = destCoordinate;
        //设置文字颜色
        for (int i = 0; i < tvliList.size(); i++) {
            if (i == thisNumber) {
                tvliList.get(i).setTextColor(mContext.getResources().getColor(mSelectColor));
            } else {
                tvliList.get(i).setTextColor(mContext.getResources().getColor(mUnSelectColor));
            }
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

}
