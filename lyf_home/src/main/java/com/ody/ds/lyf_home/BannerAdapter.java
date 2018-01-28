package com.ody.ds.lyf_home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.views.slidepager.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2017/2/16.
 */
public class BannerAdapter extends StaticPagerAdapter {

    public List <BannerBean> bannerList;

    public BannerAdapter(List <BannerBean> bannerList){
        this.bannerList = bannerList != null ? bannerList : new ArrayList<BannerBean>();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setImageResource(R.drawable.icon_replace);
        GlideUtil.display(container.getContext(),bannerList.get(position).getImage()).into(view);
        return view;
    }


    @Override
    public int getCount() {
        return bannerList.size();
    }
}
