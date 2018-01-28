package com.ody.p2p.check.myorder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ${tang} on 2017/4/21.
 */

public class KeyboardLayout extends RelativeLayout{
    public static final byte KEYBOARD_STATE_SHOW = -3;
    public static final byte KEYBOARD_STATE_HIDE = -2;
    private onKybdsChangeListener mListener;
    public KeyboardLayout(Context context) {
        super(context);
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnkbdStateListener(onKybdsChangeListener listener){
        mListener = listener;
    }
    public interface onKybdsChangeListener{
        void onKeyBoardStateChange(int state);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null != mListener && 0 != oldw && 0 != oldh) {
            if (h < oldh) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
            } else {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
            }
        }
    }
}
