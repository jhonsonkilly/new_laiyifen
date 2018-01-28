package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ody.p2p.base.BaseFragment;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by qiujie on 2017/11/9.
 */

public class HomeTwoFloolFragment extends BaseFragment {

    private VerticalViewPager verticalViewPager;
    private boolean head;

    @Override
    public int bindLayout() {
        return R.layout.frag_two_flool_home;
    }

    @Override
    public void initView(View view) {
        verticalViewPager = (VerticalViewPager) view.findViewById(R.id.verticalviewpager);

    }

    @Override
    public void doBusiness(Context mContext) {
        Bundle arguments = getArguments();
        head = arguments.getBoolean("head", false);

        verticalViewPager.setAdapter(new DummyAdapter(getChildFragmentManager()));
//        verticalViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pagemargin));
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

        if (head) {
            verticalViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void initPresenter() {

    }

    public class DummyAdapter extends FragmentPagerAdapter {


        public DummyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (head) {
                if (position == 0) {

                    HomeTwoHeadFragment homeTwoHeadFragment = new HomeTwoHeadFragment();
                    return homeTwoHeadFragment;
                } else {
                    return new HomeCatergyFragment();
                }


            } else {
                return new HomeCatergyFragment();
            }


        }

        @Override
        public int getCount() {
            return head ? 2 : 1;
        }
    }
}
