package com.ody.p2p.views.slidepager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.utils.PxUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.gravity;

/**
 * Created by lxs on 2016/8/16.
 */
public class RecommendBannerPager<T extends BaseRecyclerViewAdapter> extends FrameLayout {

    private ImageView[] imageViews;
    private Context context;
    private AutoScrollViewPager viewpa;
    public LinearLayout linear;
    private int current_index;
    private int unselect_id;
    private int selected_id;
    private int user_bottomMargin = 10;
    private int user_leftMargin = 20;
    private int user_rightMargin = 20;
    private int user_indicatorMargin = 10;
    private RecommendBannerAdapter pagerAdapter;
    private int DURINT_TIME = 2000;
    private boolean IS_LOOPER = true;
    public static int GRAVITY_CENTER = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    public static int GRAVITY_LEFT = Gravity.BOTTOM | Gravity.LEFT;
    public static int GRAVITY_RIGHT = Gravity.BOTTOM | Gravity.RIGHT;
    private int user_gravity;
    private int count;
    public List<T> adapterses;
    private int colorId = -1;

    public RecommendBannerPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecommendBannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecommendBannerPager(Context context) {
        super(context);
        init(context);
    }

    public void stop() {
        viewpa.stopAutoScroll();
    }

    public void setslideBorderMode(int slideMode) {
        viewpa.setSlideBorderMode(slideMode);
    }

    public void setslidetouchMode(int touchMode) {
        viewpa.setTouchToParent(touchMode);
    }

    public void setLayoutCount(int count) {
        this.count = count;
    }


    private void init(Context context) {
        this.context = context;
        viewpa = new AutoScrollViewPager(context);
        addView(viewpa, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        linear = new LinearLayout(context);
        LayoutParams p = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        user_gravity = GRAVITY_CENTER;
        p.gravity = user_gravity;
        p.bottomMargin = PxUtils.pxTodip(20);
        linear.setLayoutParams(p);
        if (unselect_id == 0) {
            unselect_id = R.drawable.shape_banner_normal;
        }
        if (selected_id == 0) {
            selected_id = R.drawable.shape_banner_selected;
        }
        addView(linear);
    }


    public AutoScrollViewPager getViewpa() {
        return viewpa;
    }

    public void setIndicator(int unselect_id, int selected_id) {
        this.unselect_id = unselect_id;
        this.selected_id = selected_id;
        linear.invalidate();
    }

    public void setLooper(boolean looper) {
        this.IS_LOOPER = looper;
        if (adapterses != null && adapterses.size() > 1) {
            pagerAdapter.setInfiniteLoop(IS_LOOPER);
        }
    }

    public void setAuto(boolean auto) {
        if (auto) {
            viewpa.startAutoScroll();
        } else {
            viewpa.stopAutoScroll();
        }
    }

    public void setIndicatorPosition(int gravity, int bottomMargin,
                                     int leftMargin, int rightMargin) {
        LayoutParams p = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        p.gravity = gravity;
        p.bottomMargin = bottomMargin;
        p.leftMargin = leftMargin;
        p.rightMargin = rightMargin;
        linear.setLayoutParams(p);
        linear.invalidate();
    }

    public void setIndicatorPosition(int height) {
        LayoutParams p = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        p.gravity = BannerPager.GRAVITY_CENTER;
        p.height = height;
        linear.setLayoutParams(p);
        linear.invalidate();
    }

    public AutoScrollViewPager getViewPager() {
        return viewpa;
    }

    public void setEasyData(List<T> adapters) {
        if (adapters == null || adapters.size() == 0) {
            return;
        }
        if (null != adapterses && adapterses.size() > 0) {
            adapterses.clear();
        }
        this.adapterses = adapters;
        if (linear.getChildCount() != 0) {
            linear.removeAllViews();
        }
        imageViews = null;
        imageViews = new ImageView[adapterses.size()];
        if (adapterses.size() < 1) {
            setIndicator(0);
        } else {
            if (adapterses.size() > 1) {
                setIndicator(adapters.size());
            }
        }
        pagerAdapter = new RecommendBannerAdapter(getContext(), adapterses, count);
        pagerAdapter.setBackgroundColorId(colorId);
        if (adapterses.size() == 1) {
            IS_LOOPER = false;
        }
        pagerAdapter.setInfiniteLoop(IS_LOOPER);
        if (adapters.size() == 1) {
            viewpa.setAdapter(pagerAdapter);
        } else {
            viewpa.setAdapter(pagerAdapter);
            viewpa.setInterval(DURINT_TIME);
            viewpa.startAutoScroll();
            viewpa.setCurrentItem(adapters.size() * 10000);
        }
        viewpa.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    int ImageIndicatorWidth = PxUtils.dipTopx(8);
    int ImageIndicatorHeight = PxUtils.dipTopx(8);

    public void setIndicatorSize(int width, int height) {
        this.ImageIndicatorWidth = width;
        this.ImageIndicatorHeight = height;
    }

    public void setIndicator(int size) {
        for (int i = 0; i < size; i++) {
            imageViews[i] = new ImageView(context);
//            FrameLayout.LayoutParams params = new LayoutParams(PxUtils.dipTopx(10) + 20, PxUtils.dipTopx(10));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ImageIndicatorWidth, ImageIndicatorHeight);
            params.setMargins(PxUtils.dipTopx(4), 0, PxUtils.dipTopx(4), 0);
            imageViews[i].setLayoutParams(params);
            if (i == 0) {
                imageViews[i].setBackgroundResource(selected_id);
            } else {
                imageViews[i].setBackgroundResource(unselect_id);
            }
            linear.addView(imageViews[i]);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            position = position % adapterses.size();
            current_index = position;
            if (adapterses.size() > 1) {
                updateIndicator();
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }

    public void notifyDataChanged() {
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataChanged();
        }
    }

    private void updateIndicator() {
        for (int i = 0; i < adapterses.size(); i++) {
            ImageView img = (ImageView) linear.getChildAt(i);
            if (current_index == i) {
                img.setBackgroundResource(selected_id);
            } else {
                img.setBackgroundResource(unselect_id);
            }

        }
    }


    public void OnDestroy() {
        viewpa.onDestroy();
        context = null;
    }

    /**
     * 设置滑动时间
     *
     * @param during
     */
    public void setDuring(int during) {
        this.DURINT_TIME = during;
        viewpa.setInterval(DURINT_TIME);
    }


    public interface ImageClickLintener {
        void click(int position);
    }

    public ImageClickLintener listener;

    public void setImageClickListener(ImageClickLintener listener) {
        this.listener = listener;
    }

    public void setBackgroundColorId(int colorId) {
        this.colorId = colorId;
    }

}
