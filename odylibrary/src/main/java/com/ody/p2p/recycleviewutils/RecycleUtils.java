package com.ody.p2p.recycleviewutils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by pzy on 2016/6/1 0001.
 */
public class RecycleUtils {

    /**
     * recycleview不展示时调用，撑起recycleview的高度
     *
     * @return
     */
    public static FullyLinearLayoutManager getLayoutManager(Context context) {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(context);
        manager.setOrientation(FullyLinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        return manager;
    }

    /**
     * recycleview不展示时调用，撑起recycleview的高度
     *
     * @return
     */
    public static FullyGridLayoutManager getGridLayoutManager(Context context, int itemNu) {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(context, itemNu);
        manager.setOrientation(FullyGridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        return manager;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
