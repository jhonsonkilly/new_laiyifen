package com.ody.p2p.search.searchresult.popupwindow;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ody.p2p.utils.PxUtils;

/**
 * Created by lxs on 2017/2/9.
 */
public class LyfSpaceItemDecoration extends RecyclerView.ItemDecoration{

    int mSpace ;

    public LyfSpaceItemDecoration(int space) {
        this.mSpace = PxUtils.dipTopx(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager){
            if(pos == 0 || pos == 1){
                outRect.top = PxUtils.dipTopx(0);
            }else{
                outRect.top = PxUtils.dipTopx(9);
            }
            outRect.left = mSpace;
            outRect.bottom = 0;
            if (pos % 2 != 0 ) {
                outRect.right = mSpace;
                outRect.left = PxUtils.dipTopx(9);
            }
        }
        else{
            if(pos == 0){
                outRect.top = PxUtils.dipTopx(0);
            }
            else{
                outRect.top = 0;
            }
            outRect.right = mSpace;
            outRect.left = mSpace;
        }
    }
}
