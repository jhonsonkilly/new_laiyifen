package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ody on 2016/8/28.
 */
public class ShowImageAdapter extends PagerAdapter {

    private List<View> mViews;
    private List<PhotoFileBean> mDatas;
    private Context mContext;

    public ShowImageAdapter(Context context , List<View> list,List<PhotoFileBean> datas){
        mViews = list;
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        ImageView iv = (ImageView) mViews.get(position).findViewById(R.id.iv_show_image_item);
        iv.setTag(R.id.image_tag, mDatas.get(position));
//        GlideUtil.display(mContext,mDatas.get(position)).into(iv);
//        Glide.with(mContext).load(mDatas.get(position).getFilePath()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(iv);
        GlideUtil.display(mContext,mDatas.get(position).getFilePath()).into(iv);
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int size = mViews.size();
        if(position < size) {
            container.removeView(mViews.get(position));
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
