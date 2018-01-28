package com.ody.p2p.views.recyclerviewlayoutmanager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by lxs on 2016/12/8.
 */
public class AtMostRecyclerView extends RecyclerView{
    public AtMostRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AtMostRecyclerView(Context context) {
        super(context);
    }

    public AtMostRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        try {
            super.onMeasure(widthMeasureSpec, expandSpec);
        } catch (Exception e) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
