package com.ody.p2p.productdetail.photoamplification;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ody on 2016/5/31.
 */
public class ViewpagerAdapter extends PagerAdapter {
    ArrayList<View> viewContainter ;
    ArrayList<String> titleContainer ;

    public ViewpagerAdapter( ArrayList<View> lists, ArrayList<String> titleContainer)
    {
        this.titleContainer=titleContainer;
        this.viewContainter = (ArrayList<View>) lists;
    }
    //viewpager中的组件数量
    @Override
    public int getCount() {
        return viewContainter.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        ((ViewPager) container).removeView(viewContainter.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(viewContainter.get(position));
        return viewContainter.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleContainer.get(position);
    }
}

