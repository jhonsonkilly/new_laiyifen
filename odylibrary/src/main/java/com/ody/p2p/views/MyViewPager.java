package com.ody.p2p.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ${tang} on 2016/8/17.
 */
public class MyViewPager extends ViewPager {

    private boolean canScroll=true;

    public void setScrollState(boolean canScroll){
        this.canScroll=canScroll;
    }
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!canScroll) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

//    @Override
//    public void scrollTo(int x, int y) {
//        if (canScroll){
//            super.scrollTo(x, y);
//        }
//    }
}
