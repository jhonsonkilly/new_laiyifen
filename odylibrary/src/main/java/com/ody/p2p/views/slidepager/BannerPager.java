package com.ody.p2p.views.slidepager;


import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ody.p2p.R;
import com.ody.p2p.utils.PxUtils;

public class BannerPager extends FrameLayout {
	private ArrayList<BannerBean> bannerList = new ArrayList<BannerBean>();
	private ImageView[] imageViews;
	private Context context;
	private AutoScrollViewPager viewpa;
	private LinearLayout linear;
	private int current_index;
	private int unselect_id;
	private int selected_id;
	private int user_bottomMargin = 10;
	private int user_leftMargin = 20;
	private int user_rightMargin = 20;
	private int user_indicatorMargin = 10;
	private ImagePagerAdapter pagerAdapter;
	private int DURINT_TIME = 2000;
	private boolean IS_LOOPER = true;
	private boolean IS_AUTO = false;

	public static int GRAVITY_CENTER = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
	public static int GRAVITY_LEFT = Gravity.BOTTOM | Gravity.LEFT;
	public static int GRAVITY_RIGHT = Gravity.BOTTOM | Gravity.RIGHT;
	private int user_gravity;


	public BannerPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public BannerPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BannerPager(Context context) {
		super(context);
		init(context);
	}



	public void setCurrent_index(int current_index) {
		this.current_index = current_index;
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
		p.bottomMargin = 50;
		linear.setLayoutParams(p);
		if (unselect_id == 0){
			unselect_id = R.drawable.shape_banner_normal;
		}
		if(selected_id == 0){
			selected_id = R.drawable.shape_banner_selected;
		}
		addView(linear);
	}

	public void stop(){
		viewpa.stopAutoScroll();
	}

	public AutoScrollViewPager getViewpa() {
		return viewpa;
	}

	public void setIndicator(int unselect_id, int selected_id) {
		this.unselect_id = unselect_id;
		this.selected_id = selected_id;
		linear.invalidate();
	}

	public void setLooper(boolean looper){
		this.IS_LOOPER = looper;
		if (bannerList != null && bannerList.size() > 1){
			pagerAdapter.setInfiniteLoop(IS_LOOPER);
		}
	}
	public void setAuto(boolean auto){
		this.IS_AUTO = auto;
		if (IS_AUTO){
			viewpa.startAutoScroll();
		}else{
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

	public AutoScrollViewPager getViewPager() {
		return viewpa;
	}



	public void setslideBorderMode(int slideMode){
		viewpa.setSlideBorderMode(slideMode);
	}
	public void setslidetouchMode(int touchMode){
		viewpa.setTouchToParent(touchMode);
	}

	public void setEasyData(final ArrayList<BannerBean> bannerList) {
		if(this.bannerList != null &&this.bannerList.size() != 0)
			this.bannerList.clear();
		this.bannerList.addAll(bannerList);
		if(linear.getChildCount() != 0){
			linear.removeAllViews();
		}
		imageViews = null;
		imageViews = new ImageView[bannerList.size()];
		if (bannerList.size() > 1){
			setIndicator(bannerList.size());
		}
		pagerAdapter = new ImagePagerAdapter(getContext(), bannerList);
		if (bannerList.size() == 1){
			IS_LOOPER = false;
		}
		pagerAdapter.setInfiniteLoop(IS_LOOPER);
		pagerAdapter.setImageClickListener(new ImagePagerAdapter.ImageClickListener() {
			public void imageClick(int position) {
				if (listener != null){
					listener.click(position % bannerList.size());
				}
			}
		});
		if(bannerList.size() == 1){
			viewpa.setAdapter(pagerAdapter);
		}else {
			viewpa.setAdapter(pagerAdapter);
			viewpa.setInterval(DURINT_TIME);
			if (IS_AUTO){
				viewpa.startAutoScroll();
				viewpa.setCurrentItem(bannerList.size()*10000);
			}
		}
		viewpa.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	public void setIndicator(int size){
		if (size > 1) {
			for (int i = 0; i < size; i++) {
				imageViews[i] = new ImageView(context);
				imageViews[i].setLayoutParams(new LayoutParams(PxUtils.dipTopx(6) + 16, PxUtils.dipTopx(6)));
				imageViews[i].setPadding(8, 0, 8, 0);
				if (i == 0) {
					imageViews[i].setImageResource(selected_id);
				} else {
					imageViews[i].setImageResource(unselect_id);
				}
				linear.addView(imageViews[i]);
			}
		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        	position=position%bannerList.size();
        	current_index = position;
			if (bannerList.size() > 1){
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

	public void updateIndicator() {
		for (int i = 0; i < bannerList.size(); i++) {
			ImageView img = (ImageView) linear.getChildAt(i);
			if (current_index == i)
				img.setImageResource(selected_id);
			else
				img.setImageResource(unselect_id);
		}
	}


	public void OnDestroy(){
		viewpa.onDestroy();
		context = null;
	}

	/**
	 * 设置滑动时间
	 * @param during
	 */
	public void setDuring(int during){
		this.DURINT_TIME = during;
		viewpa.setInterval(DURINT_TIME);
	}


	public interface ImageClickLintener{
		void click(int position);
	}
	public ImageClickLintener listener;

	public void setImageClickListener(ImageClickLintener listener){
		this.listener = listener;
	}

}
