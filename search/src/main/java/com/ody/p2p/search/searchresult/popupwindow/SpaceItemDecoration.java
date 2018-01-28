package com.ody.p2p.search.searchresult.popupwindow;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ody.p2p.utils.PxUtils;

/**
 * Created by lxs on 2016/7/20.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    int mSpace ;
    public final int LYF_TYPE = 1001;
    public int type;
    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(int space) {
        this.mSpace = PxUtils.dipTopx(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager){
            if(pos == 0 || pos == 1){
                outRect.top = PxUtils.dipTopx(12);;
            }else{
                outRect.top = mSpace;
            }
            outRect.left = 0;
            outRect.bottom = 0;
            if (pos % 2 != 0 ) {
                outRect.right = 0;
                outRect.left = mSpace;
            }
        }
        else{
            if(pos == 0){
                outRect.top = PxUtils.dipTopx(12);
            }
            else{
                outRect.top = mSpace;
            }
            outRect.right = 0;
            outRect.left = 0;
        }
    }

}
