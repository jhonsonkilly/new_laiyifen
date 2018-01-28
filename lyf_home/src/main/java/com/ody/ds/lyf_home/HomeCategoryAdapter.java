package com.ody.ds.lyf_home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;


import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.webactivity.NoTitleWebFragment;

import java.util.List;


public class HomeCategoryAdapter extends FragmentStatePagerAdapter {
    private BaseFragment mCurrentFragment; // 当前显示的Fragment

    private List<AppHomePageBean.StaticData.Tabs> mCategoryList;

    public HomeCategoryAdapter(List<AppHomePageBean.StaticData.Tabs> mList, FragmentManager fm) {
        super(fm);
        mCategoryList = mList;
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (BaseFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public CharSequence getPageTitle(int position) {

        return mCategoryList.get(position).title;

    }

    @Override
    public BaseFragment getItem(int position) {
        if (position == 0) {

            HomeCatergyFragment newRecomendFragment = new HomeCatergyFragment();

            return newRecomendFragment;
        } else {
            try {
                NoTitleWebFragment fragment = new NoTitleWebFragment();
                fragment.setLoadType(NoTitleWebFragment.JUMP_NORMAL);

                fragment.setUrl(mCategoryList.get(position).link.appData);


                return fragment;
            } catch (Exception e) {
                NoTitleWebFragment fragment = new NoTitleWebFragment();
                fragment.setLoadType(NoTitleWebFragment.JUMP_NORMAL);
                return fragment;
            }





        }




       /* else if("全部".equals(getPageTitle(position)))
        {

            LiveFragment liveFragment = new LiveFragment();
            liveFragment.isShowTopBar = false;

            return liveFragment;
        }
        else
        {
            CategoryListFragment categoryListFragment = new CategoryListFragment();// TODO  建议使用 构造函数传递 slug 和screen字段
            categoryListFragment.slug = mCategoryList.get(position).slug;
            categoryListFragment.screen = mCategoryList.get(position).screen;

            return categoryListFragment;
        }*/
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {

        return mCategoryList == null ? 0 : mCategoryList.size();
    }






    /*@Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }*/
}
