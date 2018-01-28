package com.ody.p2p.main;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.webactivity.NoTitleWebFragment;

/**
 * Created by mac on 2017/12/4.
 */

public class DisCoverPolicy implements TabPolicy {


    Context context;


    public DisCoverPolicy(Context context) {
        this.context = context;
    }

    @Override
    public String[] getTabText() {
        return context.getResources().getStringArray(R.array.home_tabs);
    }

    @Override
    public Fragment getfragment() {

        NoTitleWebFragment fragmentMy = new NoTitleWebFragment();

        return fragmentMy;
    }

    @Override
    public int[] getImg() {
        return new int[]{R.drawable.select_home, R.drawable.select_classification, R.drawable.select_discover, R.drawable.select_buycart, R.drawable.select_my};
    }
}
