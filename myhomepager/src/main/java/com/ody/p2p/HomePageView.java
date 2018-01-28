package com.ody.p2p;

import android.content.Context;

import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/6/16.
 */
public interface HomePageView extends BaseView{
    Context getContext();

    void getSummary(SummaryBean.Data repest);

    void getUserinfo(UserInfoBean.Data userInfo);

}
