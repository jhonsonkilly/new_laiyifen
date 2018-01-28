package com.ody.p2p.utils;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 视图视图属性操作器
 *
 * @author solomon.wen 2015.05.26
 */
public class ViewUtils {
    private View mView;

    protected ViewUtils(View view) {
        mView = view;
    }

    /**
     * 初始化一个视图属性操作器
     */
    public static ViewUtils width(View view) {
        return new ViewUtils(view);
    }

    /**
     * 设置透明 (0-1)
     * <p/>
     * 0 means fully transparent, and 255 means fully opaque.
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ViewUtils setAlpha(float alpha) {
        if (alpha > 1) {
            alpha = 1;
        } else if (alpha < 0) {
            alpha = 0;
        }

        if (OsUtils.isHoneycombPlus()) {
            mView.setAlpha(alpha);
        } else {
            Drawable background = mView.getBackground();
            background.setAlpha((int) (255.0f * alpha));
            if (OsUtils.isJellyBeanPlus()) {
                mView.setBackground(background);
            } else {
                mView.setBackgroundDrawable(background);
            }
        }

        return this;
    }

    /**
     * 设置图片透明度 (0-1)
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ViewUtils setImageAlpha(float alpha) {
        if (mView instanceof ImageView) {
            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }
            if (OsUtils.isApi16Plus()) {
                ((ImageView) mView).setImageAlpha((int) (255.0f * alpha));
            } else {
                mView.setAlpha(alpha);
            }
        }

        return this;
    }

    /**
     * 设置背景视图
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ViewUtils setBackground(Drawable d) {
        if (OsUtils.isApi16Plus()) {
            mView.setBackground(d);
        } else {
            mView.setBackgroundDrawable(d);
        }
        return this;
    }

    /**
     * 判断视图是否为一个有内容的文本控件
     */
    public static boolean notEmptyTextView(View view){
        if (view instanceof TextView){
            if (!TextUtils.isEmpty(((TextView) view).getText())){
                return true;
            }
        }
        return false;
    }

    /**
     * 设置背景视图，参数为 Drawable 的资源ID号
     */
    public ViewUtils setBackground(int drawableResourceId) {
        setBackground(mView.getContext().getResources().getDrawable(drawableResourceId));
        return this;
    }

    /**
     * 设置视图的背景颜色
     */
    public ViewUtils setBackgroundColor(int color) {
        mView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置视图的字符串背景颜色
     */
    public ViewUtils setBackgroundColor(String color) {
        return setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 设置控件左边外边距
     */
    public ViewUtils setMarginLeft(int marginLeft) {
        return setMargin(marginLeft, -1, -1, -1);
    }

    /**
     * 设置控件顶部外边距
     */
    public ViewUtils setMarginTop(int marginTop) {
        return setMargin(-1, marginTop, -1, -1);
    }

    /**
     * 设置控件右边外边距
     */
    public ViewUtils setMarginRight(int marginRight) {
        return setMargin(-1, -1, marginRight, -1);
    }

    /**
     * 设置控件底部外边距
     */
    public ViewUtils setMarginBottom(int marginBottom) {
        return setMargin(-1, -1, -1, marginBottom);
    }

    /**
     * 设置控件外边距
     */
    public ViewUtils setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        if (null != lp) {
            boolean marginChanged = false;
            ViewGroup.MarginLayoutParams lpm;
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                lpm = (ViewGroup.MarginLayoutParams) lp;
            } else {
                lpm = new ViewGroup.MarginLayoutParams(lp);
            }

            if (marginLeft > -1) {
                marginChanged = true;
                lpm.leftMargin = marginLeft;
            }

            if (marginTop > -1) {
                marginChanged = true;
                lpm.topMargin = marginTop;
            }

            if (marginRight > -1) {
                marginChanged = true;
                lpm.rightMargin = marginRight;
            }

            if (marginBottom > -1) {
                marginChanged = true;
                lpm.bottomMargin = marginBottom;
            }

            if (marginChanged) {
                mView.setLayoutParams(lpm);
            }
        }

        return this;
    }

    /**
     * 设置控件宽度(pixel)
     */
    public ViewUtils setWidth(int pixelWidth) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        if (null != lp) {
            lp.width = pixelWidth;
            mView.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置控件高度(pixel)
     */
    public ViewUtils setHeight(int pixelHeight) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        if (null != lp) {
            lp.height = pixelHeight;
            mView.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置控件尺寸(pixel)
     */
    public ViewUtils setSize(int pixelWidth, int pixelHeight) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        if (null != lp) {
            lp.width = pixelWidth;
            lp.height = pixelHeight;
            mView.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置 GridView/ListView 单元格点击时不变色
     */
    public ViewUtils setNoneSelector() {
        if (mView instanceof AbsListView) {
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[0], new ColorDrawable(Color.TRANSPARENT));
            ((AbsListView) mView).setSelector(drawable);
        }

        return this;
    }

    /**
     * 设置 GridView/ListView 单元格点击时背景色为指定颜色
     *
     * @param pressedBgColor 点击时的字符串颜色值，如 #ff6600
     */
    public ViewUtils setColorSelector(String pressedBgColor) {
        if (mView instanceof AbsListView) {
            ((AbsListView) mView).setSelector(buildPressedListDrawable(Color.parseColor(pressedBgColor)));
        }

        return this;
    }

    /**
     * 设置 GridView/ListView 单元格点击时背景色为指定颜色
     *
     * @param pressedBgColor 点击时的背景颜色值
     */
    public ViewUtils setColorSelector(int pressedBgColor) {
        if (mView instanceof AbsListView) {
            ((AbsListView) mView).setSelector(buildPressedListDrawable(pressedBgColor));
        }

        return this;
    }

    /**
     * 设置点击时的背景颜色，默认情况下为透明色
     *
     * @param pressedBgColor 点击时的字符串背景颜色值，如 #ff6600
     */
    public ViewUtils setPressedBgColor(String pressedBgColor) {
        return setPressedBgColor(Color.parseColor(pressedBgColor));
    }

    /**
     * 设置点击时的背景颜色，默认情况下为透明色
     *
     * @param pressedBgColor 点击时的背景颜色值
     */
    public ViewUtils setPressedBgColor(int pressedBgColor) {
        return setBackground(buildPressedListDrawable(pressedBgColor));
    }

    /**
     * 分别设置点击时和默认时的字符串背景颜色
     *
     * @param pressedBgColor 点击时的字符串背景颜色值
     * @param defaultColor   默认情况下的字符串颜色值
     */
    public ViewUtils setPressedBgColor(String pressedBgColor, String defaultColor) {
        return setPressedBgColor(Color.parseColor(pressedBgColor), Color.parseColor(defaultColor));
    }

    /**
     * 分别设置点击时和默认时的背景颜色
     *
     * @param pressedBgColor 点击时的背景颜色值
     * @param defaultColor   默认情况下的颜色值
     */
    public ViewUtils setPressedBgColor(int pressedBgColor, int defaultColor) {
        return setBackground(buildPressedListDrawable(pressedBgColor, defaultColor));
    }

    /**
     * 创建一个点击时可变色的Drawable，默认情况下为透明色
     *
     * @param pressedColor 点击时变化的颜色值
     */
    public static StateListDrawable buildPressedListDrawable(int pressedColor) {
        return buildPressedListDrawable(pressedColor, Color.TRANSPARENT);
    }

    /**
     * 创建一个点击时可变色的Drawable
     *
     * @param pressedColor 点击时变化的颜色值
     * @param defaultColor 默认情况下的颜色值
     */
    public static StateListDrawable buildPressedListDrawable(int pressedColor, int defaultColor) {
        StateListDrawable drawable = new StateListDrawable();
        ColorDrawable focusDrawable = new ColorDrawable(pressedColor);
        drawable.addState(new int[]{android.R.attr.state_pressed}, focusDrawable);
        drawable.addState(new int[0], new ColorDrawable(defaultColor));
        return drawable;
    }

    /**
     * 创建一个点击时可变色的ColorStateList，默认情况下为黑色
     *
     * @param pressedColor 点击时变化的颜色值
     */
    public static ColorStateList buildPressedListColor(int pressedColor) {
        return buildPressedListColor(pressedColor, Color.BLACK);
    }

    /**
     * 创建一个点击时可变色的ColorStateList (String)
     *
     * @param pressedColorStr 点击时变化的颜色值
     * @param defaultColorStr 默认情况下的颜色值
     */
    public static ColorStateList buildPressedListColor(String pressedColorStr, String defaultColorStr) {
        return buildPressedListColor(Color.parseColor(pressedColorStr), Color.parseColor(defaultColorStr));
    }

    /**
     * 创建一个点击时可变色的ColorStateList (String)
     *
     * @param pressedColor 点击时变化的颜色值
     * @param defaultColor 默认情况下的颜色值
     */
    public static ColorStateList buildPressedListColor(int pressedColor, int defaultColor) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[0]}, new int[]{pressedColor, defaultColor});
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 随机产生一个不重复的 ViewId
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * 设置listview的高度
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for(int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setListHeightAllClum(GridView listView ,int count){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            if (i % count == 0){
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }


}
