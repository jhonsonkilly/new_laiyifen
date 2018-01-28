package com.ody.p2p.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.utility.UIUtils;
import com.ody.p2p.R;
import com.ody.p2p.utils.ScreenUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;

/**
 * Created by mac on 2017/9/7.
 */

public class SuspendFrameLayout extends FrameLayout {

    public ImageView imageView;



    private int lastX;
    private int lastY;

    private boolean isDrag = true;

    private int screenWidth;
    private int screenHeight;

    private int statusHeight;
    private int virtualHeight;
    private float x;
    private float y;
    private int initx;
    private int inity;

    OnSuspendClickListener onSuspendClickListener;

    public SuspendFrameLayout(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public SuspendFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SuspendFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        View view = View.inflate(context, R.layout.layout_suspend_layout, null);
        imageView = (ImageView) view.findViewById(R.id.suspend_img);


        screenWidth = ScreenUtils.getScreenWidth(getContext());

        screenHeight = ScreenUtils.getScreenHeight(getContext());
        statusHeight = ScreenUtils.getStatusHeight(getContext());
        virtualHeight = ScreenUtils.getVirtualBarHeigh(getContext());
        addView(view);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                //getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                initx = (int) getX();
                inity = (int) getY();
                break;
            case MotionEvent.ACTION_MOVE:

                //计算手指移动了多少
                int dx = rawX - lastX;
                int dy = rawY - lastY;


                x = getX() + dx;
                y = getY() + dy;

                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                // y = y < statusHeight ? statusHeight : (y + getHeight() >= screenHeight ? screenHeight - getHeight() : y);
                if (y < 0) {
                    y = 0;
                }
                if (y > screenHeight - statusHeight - getHeight() - UiUtils.dip2px(getContext(), 50)) {
                    y = screenHeight - statusHeight - getHeight() - UiUtils.dip2px(getContext(), 50);
                }
                setX(x);
                setY(y);

                lastX = rawX;
                lastY = rawY;

                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x - initx) < 5 || Math.abs(y - inity) < 5) {
                    if (onSuspendClickListener != null) {
                        onSuspendClickListener.performClick();
                    }
                }

                break;
        }
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag || super.onTouchEvent(event);

    }


   public interface OnSuspendClickListener {

        void performClick();
    }


    public void setOnSuspendClickListener(OnSuspendClickListener onSuspendClickListener) {
        this.onSuspendClickListener = onSuspendClickListener;
    }
}

