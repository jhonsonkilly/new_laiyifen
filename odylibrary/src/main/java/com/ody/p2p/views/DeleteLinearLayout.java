package com.ody.p2p.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class DeleteLinearLayout extends LinearLayout {
    private LinearLayout lay_first;
    private TextView tv_delete;
    private  int MAXWIDTH ;
    private int mLastX;
    private int mTouchSlop;
    private Scroller mScroller;
    private boolean canScroll = true;

    public DeleteLinearLayout(Context context) {
        this(context,null,0);
    }

    public DeleteLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeleteLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        lay_first = (LinearLayout) getChildAt(0);
        tv_delete = (TextView) getChildAt(1);
        MAXWIDTH = dipToPx(context,70);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean intercepted = false;
        int x = (int) ev.getX();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                intercepted = false;
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(mLastX - x ) > mTouchSlop){
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    return true;
                }
                intercepted = false;
                break;
        }
        mLastX = x;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        getParent().requestDisallowInterceptTouchEvent(false);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                scrollBy(-dx,0);
                if(mListner != null){
                    mListner.onScroll();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int scrollX1 = getScrollX();
                if(scrollX1 > MAXWIDTH / 3){
                    mScroller.startScroll(getScrollX(),0,MAXWIDTH - getScrollX(),0,200);
                    invalidate();
                }else {
                    mScroller.startScroll(getScrollX(),0,0 - getScrollX(),0,200);
                    invalidate();
                }
                if(mListner != null){
                    mListner.onScrollFinished();
                }
                break;
        }
        mLastX = x;

        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(canScroll) {

            if (x < 0) {
                x = 0;
            }
            if (x > MAXWIDTH) {
                x = MAXWIDTH;
            }

            super.scrollTo(x, y);
        }
    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
    public void openLeft(){
        scrollTo(MAXWIDTH,0);
    }
    public void closeLeft(){
        scrollTo(0,0);
        invalidate();
    }

    public interface onMyScrollListener{
        void onScroll();
        void onScrollFinished();
    }
    private onMyScrollListener mListner;

    public void setOnMyScrollListner(onMyScrollListener listner) {
        this.mListner = listner;
    }

    public void setCanScroll(boolean canScroll){
        this.canScroll = canScroll;
    }
}
