package com.ody.p2p.main.TestBase;

import android.content.Context;

import com.ody.p2p.UpGradeBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.action.GrestCouponBean;
import com.ody.p2p.main.InitDataBean;
import com.ody.p2p.retrofit.home.AdBean;
import com.ody.p2p.retrofit.home.PersonalBean;

/**
 * Created by mac on 2018/1/30.
 */

public interface MainView extends IBaseCommView {

    void initData(InitDataBean bean);

    void initCartNum(int count);

    Context context();

    void Upgrade(UpGradeBean.Data data);

    void initTanPin(FuncBean bean);

    void initIsNewUser(int newUser, int flag);

    void initFirstLoginCoupon(GrestCouponBean firstLoginCouponBean);

    void load(String id);

    void setPersonalData(PersonalBean response, boolean isImlogin);

    void setGuangGaoData(AdBean bean);


    String getNetTAG();
}
