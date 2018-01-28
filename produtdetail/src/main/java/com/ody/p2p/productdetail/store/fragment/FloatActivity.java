package com.ody.p2p.productdetail.store.fragment;

import android.content.Context;
import android.view.View;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.produtdetail.R;

/**
 * Created by meijunqiang on 2017/6/27.
 * 描述:
 */
public class FloatActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_float_activity2;
    }

    @Override
    public void initView(View view) {
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBar);
//        mToolbar.setLogo(R.drawable.ic_launcher);
//        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_delete);
//        mToolbar.setTitle("zhangphil");
//        mToolbar.setSubtitle("zhangphil副标题");
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        for (int i = 0; i < 10; i++)
//            tabLayout.addTab(tabLayout.newTab().setText("选项卡槽" + i));
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
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

    @Override
    public void initPresenter() {

    }
}
