package com.ody.p2p.views.basepopupwindow;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by ody on 2016/6/20.
 */
public class popupwinowUtitls {

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }
}
