package com.ody.p2p.check.orderlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public class OrderViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public OrderViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList=fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
