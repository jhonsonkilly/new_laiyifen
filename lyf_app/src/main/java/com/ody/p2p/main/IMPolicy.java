package com.ody.p2p.main;

import android.content.Context;
import android.support.v4.app.Fragment;


/**
 * Created by mac on 2017/12/4.
 */

public class IMPolicy implements TabPolicy {
    @Override
    public int[] getImg() {
        return new int[]{R.drawable.select_home, R.drawable.select_classification, R.drawable.select_im, R.drawable.select_buycart, R.drawable.select_my};
    }

    Context context;


    public IMPolicy(Context context) {
        this.context = context;
    }


    @Override
    public String[] getTabText() {


        return context.getResources().getStringArray(R.array.home_tabs2);
    }

    @Override
    public Fragment getfragment() {

      /*  if (fragment == null) {
            fragment = YunXinFragment.newInstance(""
                    , ""
                    , ""
                    , ""
                    , ""
                    , "");
            return fragment;
        } else {
            return fragment;
        }*/
        return null;
    }
}
