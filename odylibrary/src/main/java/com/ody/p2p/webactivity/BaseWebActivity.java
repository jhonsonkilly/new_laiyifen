package com.ody.p2p.webactivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseActivity;

/**
 * Created by lxs on 2016/10/17.
 */
public class BaseWebActivity extends BaseActivity{

    protected BaseWebFragment fragment;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activty_baseweb;
    }

    @Override
    public void initView(View view) {
        if (fragment == null){
            fragment = new BaseWebFragment();
        }
        addFragment(fragment);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }


    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.contain, fragment).commitAllowingStateLoss();
    }

    public void setWebFragment(BaseWebFragment fragment){
        this.fragment = fragment;
    }
}
