package com.ody.p2p.views.odyscorllviews;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.ody.p2p.views.scrollwebview.ScrollWebView;

/**
 * Created by lxs on 2016/8/28.
 */
public class OdyScrollWebView extends WebView{

    public OdyScrollWebView(Context context) {
        super(context);
    }

    public OdyScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OdyScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (listener != null) {
            listener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface ScrollViewListener {

        void onScrollChanged(OdyScrollWebView scrollView, int x, int y, int oldx, int oldy);

    }
    public ScrollViewListener listener;

    public void setScrollListener(ScrollViewListener scrollListener){
        this.listener = scrollListener;
    }

}
