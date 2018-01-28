package com.ody.p2p.productdetail.store.fragment;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by meijunqiang on 2017/6/28.
 * 描述:
 */

public class MyNestedScrollView extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public MyNestedScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
//    private OnScrollChangedListener mOnScrollChangedListener;
//
//    public MyNestedScrollView(Context context) {
//        super(context);
//    }
//
//    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    private GestureDetector mGestureDetector;
//    View.OnTouchListener mGestureListener;
//
//    public MyNestedScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mGestureDetector = new GestureDetector(context, new YScrollDetector());
//        setFadingEdgeLength(0);
//    }
//
//    public interface OnScrollChangedListener {
//        void onScrollChanged(int l, int t, int oldl, int oldt);
//    }
//
//    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
//        mOnScrollChangedListener = onScrollChangedListener;
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        if (mOnScrollChangedListener != null) {
//            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
//        }
//        super.onScrollChanged(l, t, oldl, oldt);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
//    }
//
//    // Return false if we're scrolling in the x direction
//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (Math.abs(distanceY) > Math.abs(distanceX)) {
//                return true;
//            }
//            return false;
//        }
//    }
}
