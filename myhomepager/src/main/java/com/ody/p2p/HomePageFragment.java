package com.ody.p2p;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.CircleImageView;

public class HomePageFragment extends BaseFragment implements HomePageView, View.OnClickListener {
    public HomePagePressenter mPressent;
    public ImageView img_tosetting, img_message;
    public CircleImageView img_user_photos;
    public TextView tv_user_name, tv_sign_in, tv_nupay_conrner, tv_nushipments_conrner, tv_undelivery_conrner;
    public LinearLayout linearlayout_myproduct, linear_mywallet, linear_myshopaddress, linear_browsing_history, linear_collect_product, linear_property, liner_collect_store, linear_orderall, linear_islogin, my_integral, my_gold, my_huodong, linear_shipping_address, linear_vip, linear_browse_record, liner_collect_order, tv_unpay_order, my_giftcard, unshipments_order, undelivery_order, unevaluate_order, sale_order, my_wallet, my_coupon, my_balance;
    public LinearLayout ll_video;
    public boolean isLogin;//用户登录状态

    public int STANDARD = 0;//标准版
    public int SAAS = 1;//尊享版
    public int versions = -1;

    @Override
    public Activity getContext() {
        return super.getContext();
    }

    @Override
    public void initPresenter() {
        mPressent = new HomePageImrp(this);
        versions = STANDARD;//版本控制
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);//登录状态
        if (!hidden) {
            if (isLogin) {
                if (null != mPressent) {
                    initLoading();
                }
            } else {
                tv_user_name.setText(R.string.login_regeist);
                getSummary(null);
            }
        }
    }

    public void initLoading() {
        mPressent.getSummary();
        mPressent.getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);//登录状态
        if (null != mPressent) {
            if (isLogin) {
                initLoading();
            } else {
                tv_user_name.setText(R.string.login_regeist);
                getSummary(null);
            }
        }
    }

    @Override
    public int bindLayout() {
//        getSupportActionBar().hide();
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        return R.layout.activity_homepager;
    }

    @Override
    public void initView(View view) {
        ll_video = (LinearLayout) view.findViewById(R.id.ll_video);
        linearlayout_myproduct = (LinearLayout) view.findViewById(R.id.linearlayout_myproduct);
        linear_mywallet = (LinearLayout) view.findViewById(R.id.linearlayout_mywallet);
        img_tosetting = (ImageView) view.findViewById(R.id.img_tosetting);
        img_message = (ImageView) view.findViewById(R.id.img_message);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        linear_orderall = (LinearLayout) view.findViewById(R.id.linear_orderall);
        tv_unpay_order = (LinearLayout) view.findViewById(R.id.tv_unpay_order);
        unshipments_order = (LinearLayout) view.findViewById(R.id.unshipments_order);
        undelivery_order = (LinearLayout) view.findViewById(R.id.undelivery_order);
        unevaluate_order = (LinearLayout) view.findViewById(R.id.unevaluate_order);
        sale_order = (LinearLayout) view.findViewById(R.id.sale_order);
        my_wallet = (LinearLayout) view.findViewById(R.id.my_wallet);
        my_coupon = (LinearLayout) view.findViewById(R.id.my_coupon);
        my_balance = (LinearLayout) view.findViewById(R.id.my_balance);
        my_giftcard = (LinearLayout) view.findViewById(R.id.my_giftcard);
        liner_collect_order = (LinearLayout) view.findViewById(R.id.liner_collect_order);
        linear_browse_record = (LinearLayout) view.findViewById(R.id.linear_browse_record);
        linear_shipping_address = (LinearLayout) view.findViewById(R.id.linear_shipping_address);
        linear_vip = (LinearLayout) view.findViewById(R.id.linear_vip);
        my_huodong = (LinearLayout) view.findViewById(R.id.my_huodong);
        my_gold = (LinearLayout) view.findViewById(R.id.my_gold);
        my_integral = (LinearLayout) view.findViewById(R.id.my_integral);
        tv_sign_in = (TextView) view.findViewById(R.id.tv_sign_in);
        img_user_photos = (CircleImageView) view.findViewById(R.id.img_user_photos);
        tv_nupay_conrner = (TextView) view.findViewById(R.id.tv_nupay_conrner);
        tv_nushipments_conrner = (TextView) view.findViewById(R.id.tv_nushipments_conrner);
        linear_islogin = (LinearLayout) view.findViewById(R.id.linear_islogin);
        tv_undelivery_conrner = (TextView) view.findViewById(R.id.tv_undelivery_conrner);
        linear_property = (LinearLayout) view.findViewById(R.id.linear_property);
        liner_collect_store = (LinearLayout) view.findViewById(R.id.liner_collect_store);
        linear_collect_product = (LinearLayout) view.findViewById(R.id.linear_collect_product);
        linear_collect_product.setOnClickListener(this);
        linear_browsing_history = (LinearLayout) view.findViewById(R.id.linear_browsing_history);
        linear_myshopaddress = (LinearLayout) view.findViewById(R.id.linear_myshopaddress);

        img_tosetting.setOnClickListener(this);
        img_message.setOnClickListener(this);
        linear_orderall.setOnClickListener(this);
        unshipments_order.setOnClickListener(this);
        tv_unpay_order.setOnClickListener(this);
        undelivery_order.setOnClickListener(this);
        unevaluate_order.setOnClickListener(this);
        sale_order.setOnClickListener(this);
        my_wallet.setOnClickListener(this);
        my_coupon.setOnClickListener(this);
        my_balance.setOnClickListener(this);
        my_giftcard.setOnClickListener(this);
        liner_collect_order.setOnClickListener(this);
        linear_browse_record.setOnClickListener(this);
        linear_shipping_address.setOnClickListener(this);
        linear_vip.setOnClickListener(this);
        my_huodong.setOnClickListener(this);
        my_gold.setOnClickListener(this);
        my_integral.setOnClickListener(this);
        tv_sign_in.setOnClickListener(this);
        img_user_photos.setOnClickListener(this);
        liner_collect_store.setOnClickListener(this);
        linear_collect_product.setOnClickListener(this);
        linear_browsing_history.setOnClickListener(this);
        linear_myshopaddress.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        ll_video.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (versions == SAAS) {//版本区分
            linear_property.setVisibility(View.GONE);
            linear_islogin.setVisibility(View.VISIBLE);
        } else if (versions == STANDARD) {
            linear_property.setVisibility(View.VISIBLE);
            linear_mywallet.setVisibility(View.GONE);
            linearlayout_myproduct.setVisibility(View.GONE);
            linear_islogin.setVisibility(View.INVISIBLE);
        } else {
            linear_property.setVisibility(View.VISIBLE);
            linear_mywallet.setVisibility(View.VISIBLE);
            linearlayout_myproduct.setVisibility(View.VISIBLE);
            linear_islogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSummary(SummaryBean.Data repest) {
        if (null == repest) {
            tv_nupay_conrner.setVisibility(View.INVISIBLE);
            tv_nushipments_conrner.setVisibility(View.INVISIBLE);
            tv_undelivery_conrner.setVisibility(View.INVISIBLE);
            return;
        }
        if (repest.getUnpay() > 0) {//待付款
            tv_nupay_conrner.setVisibility(View.VISIBLE);
            tv_nupay_conrner.setText(repest.getUnpay() + "");
        } else {
            tv_nupay_conrner.setVisibility(View.INVISIBLE);
        }
        if (repest.getUndelivery() > 0) {//待送貨
            tv_nushipments_conrner.setVisibility(View.VISIBLE);
            tv_nushipments_conrner.setText(repest.getUndelivery() + "");
        } else {
            tv_nushipments_conrner.setVisibility(View.INVISIBLE);
        }
        if (repest.getUnerceive() > 0) {//待收貨
            tv_undelivery_conrner.setVisibility(View.VISIBLE);
            tv_undelivery_conrner.setText(repest.getUnerceive() + "");
        } else {
            tv_undelivery_conrner.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void getUserinfo(UserInfoBean.Data userInfo) {
        if (null != userInfo.getNickname() && userInfo.getNickname().length() > 0) {
            tv_user_name.setText(userInfo.getNickname());
        } else if (null != userInfo.getMobile() && userInfo.getMobile().length() > 0) {
            tv_user_name.setText(userInfo.getMobile());
        } else {
            tv_user_name.setText("");
        }
        if (null != userInfo.getHeadPicUrl()&&userInfo.getHeadPicUrl().length()>0) {
            Glide.with(getContext()).load(userInfo.getHeadPicUrl()).override(100, 100).into(img_user_photos);
//            GlideUtil.display(getContext(), userInfo.getHeadPicUrl()).override(100, 100).into(img_user_photos);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_user_photos) {
            skip(JumpUtils.SETTING);
        }
        if (v.getId() == R.id.tv_user_name) {
            skip(JumpUtils.SETTING);
        } else if (v.getId() == R.id.linear_myshopaddress) {//我的收货地址
            skip(JumpUtils.ADDRESS_MANAGER);
        } else if (v.getId() == R.id.sale_order) {//退货退款列表
            skip(JumpUtils.REFOUNDLIST);
        } else if (v.getId() == R.id.img_tosetting) {
            skip(JumpUtils.SETTING);
        } else if (v.getId() == R.id.linear_collect_product) {
            skip(JumpUtils.MYLIKE);
        } else if (v.equals(ll_video)) {//直播
            if (null != OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null) && !"".equals(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null))) {
                skip(JumpUtils.LIVELIST);
            } else {
                JumpUtils.ToActivity(JumpUtils.LOGIN);
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://login");
//                activityRoute.withParams(Constants.LOSINGTAP, "100").open();
            }
        }
        if (v.getId() == R.id.linear_browsing_history) {
            skip(JumpUtils.SCANHISTORY);
        } else if (v.getId() == R.id.linear_orderall) {
            skipActivity(JumpUtils.ORDERLIST, 0);
        } else if (v.getId() == R.id.tv_unpay_order) {
            skipActivity(JumpUtils.ORDERLIST, 1);
        } else if (v.getId() == R.id.unshipments_order) {
            skipActivity(JumpUtils.ORDERLIST, 2);
        } else if (v.getId() == R.id.undelivery_order) {
            skipActivity(JumpUtils.ORDERLIST, 3);
        } else if (v.getId() == R.id.unevaluate_order) {
            skipActivity(JumpUtils.ORDERLIST, 4);
        } else if (v.getId() == R.id.img_message) {
//            skip("message");
        }

//        switch (v.getId()) {
//            case R.id.img_tosetting:
//                //设置
//                break;
//            case R.id.img_message:
//                //消息
//                break;
//            case R.id.linear_orderall:
//                if (getLogin()) {
//                    //查看所有订单
//                }
//                break;
//            case R.id.tv_unpay_order:
//                if (getLogin()) {
//                    //查看待付款订单
//                }
//                break;
//            case R.id.unshipments_order:
//                if (getLogin()) {
//                    //查看待发货订单
//                }
//                break;
//            case R.id.unevaluate_order:
//                if (getLogin()) {
//                    //查看待评价
//                }
//                break;
//            case R.id.my_wallet:
//                if (getLogin()) {
//                    //我的钱包
//                }
//                break;
//            case R.id.my_coupon:
//                if (getLogin()) {
//                    //我的优惠券
//                }
//                break;
//            case R.id.my_giftcard:
//                if (getLogin()) {
//                    //我的礼金卡
//                }
//                break;
//            case R.id.liner_collect_order:
//                if (getLogin()) {
//                    //收藏商品
//                }
//                break;
//            case R.id.linear_browse_record:
//                if (getLogin()) {
//                    //浏览记录
//                }
//                break;
//            case R.id.linear_shipping_address:
//                if (getLogin()) {
//                    //收货地址
//                }
//                break;
//            case R.id.linear_vip:
//                if (getLogin()) {
//                    //会员
//                }
//                break;
//            case R.id.my_huodong:
//                if (getLogin()) {
//                    //活动中心
//                }
//                break;
//            case R.id.my_gold:
//                if (getLogin()) {
//                    //我的金币
//                }
//                break;
//            case R.id.my_integral:
//                if (getLogin()) {
//                    //我的积分
//                }
//                break;
//            case R.id.tv_sign_in:
//                if (getLogin()) {
//                    //签到
//                }
//                break;
//            case R.id.my_balance:
//                if (getLogin()) {
//                    //我的余额
//                }
//                break;
//            case R.id.liner_collect_store:
//                if (getLogin()) {
//                    //收藏的店铺
//                }
//                break;
//            case R.id.linear_collect_product:
//                if (getLogin()) {
//                    //收藏的商品
//                }
//                break;
//            case R.id.linear_browsing_history:
//                if (getLogin()) {
//                    //浏览记录
//                }
//                break;
//
//        }
    }


    /**
     * 获取登录状态
     *
     * @return
     */
    public void skip(String toName) {
        if (isLogin) {
            JumpUtils.ToActivity(toName);
        } else {
            //这里未登录，跳转至登录页面
            JumpUtils.ToActivity(JumpUtils.LOGIN);
        }
    }


    /**
     * 跳转
     *
     * @param toActivity
     * @param orderkey   订单状态(0 代表查询所有 1 待支付 2 待发货 3 待收货 10已取消)
     */
    public void skipActivity(String toActivity, int orderkey) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute(toActivity);
//        activityRoute.withParams("orderlist_Key", orderkey);
//        activityRoute.open();
        if (isLogin) {
            Bundle bundle = new Bundle();
            bundle.putInt("orderlist_Key", orderkey);
            JumpUtils.ToActivity(toActivity, bundle);
        } else {
            //这里未登录，跳转至登录页面
            JumpUtils.ToActivity(JumpUtils.LOGIN);
        }

    }
}
