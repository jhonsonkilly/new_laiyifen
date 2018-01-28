package com.ody.ds.lyf_home;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ody.p2p.utils.PxUtils;

/**
 * Created by lxs on 2016/8/11.
 */
public class HotSpace extends RecyclerView.ItemDecoration{

    int mSpace ;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public HotSpace(int space) {
        this.mSpace = PxUtils.dipTopx(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.top = mSpace;
        outRect.bottom = mSpace;
    }
}
