/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.ody.p2p.views.slidepager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;

/**
 * ImagePagerAdapter
 *
 * @author lxs 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private ArrayList<BannerBean> bannerList;

    private int size;
    private boolean isInfiniteLoop;

    public ImagePagerAdapter(Context context, ArrayList<BannerBean> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
        this.size = bannerList.size();
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : bannerList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        if (size != 0) {
            return isInfiniteLoop ? position % size : position;
        }
        return -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        view = new ImageView(context);
        view.setBackgroundColor(Color.parseColor("#F0F0F0"));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
        if (null != bannerList && bannerList.size() > 0) {
//            GlideUtil.display(context, bannerList.get(getPosition(position)).image).into(((ImageView) view));
            if (bannerList.size() > getPosition(position) - 1) {
//                GlideUtil.display(context, bannerList.get(getPosition(position)).image).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(((ImageView) view));
                GlideUtil.displayGif(context, ((ImageView) view), bannerList.get(getPosition(position)).image);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.imageClick(position);
                }
            });
        }
        return view;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        notifyDataSetChanged();
    }


    public interface ImageClickListener {
        void imageClick(int position);
    }

    public void setImageClickListener(ImageClickListener listener) {
        this.listener = listener;
    }

    public ImageClickListener listener;

}
