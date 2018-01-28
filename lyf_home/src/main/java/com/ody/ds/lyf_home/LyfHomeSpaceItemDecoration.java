package com.ody.ds.lyf_home;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ody.p2p.utils.PxUtils;

/**
 * Created by lxs on 2017/2/9.
 */
public class LyfHomeSpaceItemDecoration extends RecyclerView.ItemDecoration {

    int mSpace;

    public LyfHomeSpaceItemDecoration(int space) {
        this.mSpace = PxUtils.dipTopx(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            if (manager.getSpanCount() == 2) {
                outRect.left = mSpace;
                outRect.bottom = 0;
                if (pos % 2 != 0) {
                    outRect.right = mSpace;
                    outRect.left = PxUtils.dipTopx(9);
                }
            }
        } else {
            if (pos == 0) {
                outRect.top = PxUtils.dipTopx(0);
            } else {
                outRect.top = 0;
            }
            outRect.right = mSpace;
            outRect.left = mSpace;
        }
    }
}
