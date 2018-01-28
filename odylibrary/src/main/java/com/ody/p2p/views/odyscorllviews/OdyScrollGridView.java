package com.ody.p2p.views.odyscorllviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by lxs on 2016/8/28.
 */
public class OdyScrollGridView extends GridView{

    public OdyScrollGridView(Context context) {
        super(context);
    }

    public OdyScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OdyScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (listener != null) {
            listener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }

    public interface ScrollViewListener {

        void onScrollChanged(OdyScrollGridView scrollView, int x, int y, int oldX, int oldY);

    }
    public ScrollViewListener listener;

    public void setScrollListener(ScrollViewListener scrollListener){
        this.listener = scrollListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
