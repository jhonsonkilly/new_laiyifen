package com.ody.p2p.views.scrollwebview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.ody.p2p.views.webview.BaseWebView;

/**
 * Created by lxs on 2016/6/2.
 */
public class ScrollWebView extends BaseWebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    private double alpha;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        alpha = (double) (300.0 / (double) oldt);
        if (alpha > 0.9) {
            alpha = 0.9;
        }
        Log.d("a", "onScrollChanged: " + oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt, 1.0 - alpha);
        }

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        Log.i("TAG1", "webview.getScrollY()====>>" + getScrollY());
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
            if (mOnScrollChangedCallback != null) {
                mOnScrollChangedCallback.onPageEnd(l, t, oldl, oldt);
            }
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
            if (mOnScrollChangedCallback != null) {
                mOnScrollChangedCallback.onPageTop(l, t, oldl, oldt);
            }
        } else {
            if (mOnScrollChangedCallback != null) {

                mOnScrollChangedCallback.onScrollChanged(l, t, oldl, oldt);
            }
        }

    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy, double alpha);

        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
