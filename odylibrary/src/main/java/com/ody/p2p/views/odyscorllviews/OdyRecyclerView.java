package com.ody.p2p.views.odyscorllviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ody.p2p.base.BaseRecyclerViewAdapter;

/**
 * Created by lxs on 2016/9/13.
 */
public class OdyRecyclerView extends RecyclerView{

    private OnScrolledLinstener onScrolledLinstener;
    private BaseRecyclerViewAdapter adapter;
    private LayoutManager layout;

    public OdyRecyclerView(Context context) {
        this(context, null);
    }

    public OdyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OdyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.layout = layout;
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof BaseRecyclerViewAdapter) {
            this.adapter = (BaseRecyclerViewAdapter) adapter;
        }
        super.setAdapter(adapter);
    }

    public class ImageAutoLoadScrollListener extends OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(onScrolledLinstener != null){
                onScrolledLinstener.onScrolled(recyclerView, dx, dy);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState){
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                    //对于滚动不加载图片的尝试
                    if (adapter != null){
                        adapter.setScroll(false);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                    if (adapter != null){
                        adapter.setScroll(false);
                    }
                    break;
                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
                    if (adapter != null){
                        adapter.setScroll(true);
                    }
                    break;
            }
        }
    }


    public void setOnScrollLinstener(OnScrolledLinstener onScrolledLinstener){
        this.onScrolledLinstener = onScrolledLinstener;
    }

    public interface OnScrolledLinstener{
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

}
