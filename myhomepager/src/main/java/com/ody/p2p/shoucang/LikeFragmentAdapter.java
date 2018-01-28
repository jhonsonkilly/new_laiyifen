package com.ody.p2p.shoucang;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ody on 2016/8/17.
 */
public class LikeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;
    private Context mContext;

    public LikeFragmentAdapter(FragmentManager fm,Context context,List<Fragment> list) {
        super(fm);
        mData = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
