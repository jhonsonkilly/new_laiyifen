package com.ody.p2p.check.coupon;

import com.ody.p2p.base.BaseView;

/**
 * Created by ${tang} on 2016/8/22.
 */
public interface UseCouponView extends BaseView{
    void useCouponList(UseCouponBean useCouponBean);

    void finishActivity();

    void onError();
}
