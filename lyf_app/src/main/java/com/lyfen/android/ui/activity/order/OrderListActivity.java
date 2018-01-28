package com.lyfen.android.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.views.action.Action;
import com.laiyifen.lyfframework.views.action.TextAction;
import com.lyfen.android.ui.fragment.MyOrderListFragment;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.BuildConfig;
import com.ody.p2p.main.R;
import com.ody.p2p.webactivity.NoTitleWebFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderListActivity extends ActionBarActivity {
    public String[] mTabName = UIUtils.getStringArray(R.array.ordertype_names);
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.vp_view)
    ViewPager vpView;

    public boolean refresh = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);


        initTag();
        int index = getIntent().getIntExtra("index", 0);

        Myadapter adapter = new Myadapter(getSupportFragmentManager());
        vpView.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpView);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器


        TextAction mReturnAction = new TextAction(this, "", R.drawable.icon_search, Action.LEFT_OF);
        mReturnAction.setHorizontalRule(Action.RIGHT_OF);
        mReturnAction.getView().setOnClickListener(v -> {

            startActivity(new Intent(OrderListActivity.this, OrderSearchActivity.class));
        });
        getActionTitleBar().addAction(mReturnAction);
        vpView.setCurrentItem(index, false);
    }

    private void initTag() {

        for (int j = 0; j < mTabName.length; j++) {
            tabLayout.setVisibility(View.VISIBLE);
            TabLayout.Tab tab = tabLayout.newTab();

            tab.setText(mTabName[j]);

            tabLayout.addTab(tab);//添加tab选项卡


        }
    }

    class Myadapter extends FragmentStatePagerAdapter {
        FragmentManager fm;

        public Myadapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 6) {
                NoTitleWebFragment fragment1 = new NoTitleWebFragment();
                fragment1.setLoadType(NoTitleWebFragment.JUMP_NORMAL);

                fragment1.setUrl(BuildConfig.H5URL + "/my/aftersale-list.html");
                fragment = fragment1;

            } else {
                fragment = MyOrderListFragment.newInstance(mTabName[position], position);
            }
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName[position].toUpperCase();
        }

        @Override
        public int getCount() {
            return mTabName.length;
        }


        @Override
        public int getItemPosition(Object object) {
            if (refresh) {
                return POSITION_NONE;
            } else {
                return POSITION_UNCHANGED;
            }
        }
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
