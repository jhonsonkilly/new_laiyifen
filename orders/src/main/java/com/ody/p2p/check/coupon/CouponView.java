package com.ody.p2p.check.coupon;

import com.ody.p2p.base.BaseView;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public interface CouponView extends BaseView{

    void couponlist(CouponBean couponBean);

    void couponCount(CouponCountBean couponCountBean);

    void onError();
}
