package com.ody.p2p.main;

import android.support.v4.app.Fragment;



/**
 * Created by mac on 2017/12/4.
 */

public interface TabPolicy  {

    //数组
    String[] getTabText();

    //返回的fragment
    Fragment getfragment();

    int[] getImg();








}
