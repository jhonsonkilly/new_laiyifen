package com.ody.p2p.login.loginfragment.forgertpasd;

import android.content.Context;

import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/6/7.
 */
public interface ForgetPsdSecondView extends BaseView{

    void showToast(String msg);

    void finishActivity();

    Context getClassContext();

}
