package com.ody.p2p.main.myhomepager.myWallet.coupon;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.coupon.adapter.LyfCouponAdapter;
import com.ody.p2p.main.myhomepager.myWallet.coupon.bean.CouponBean;
import com.ody.p2p.main.views.ImToolBar;
import com.ody.p2p.main.views.SimpleCellLinearLayout;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meijunqiang on 2017/3/14.
 * 描述：我的优惠券
 */

public class LyfCouponActivity extends BaseActivity {
    private ImToolBar couponTb;
    private SimpleCellLinearLayout couponNumber;
    private SimpleCellLinearLayout couponPwd;
    private Button couponAdd;
    private RadioGroup couponRgpFilter;
    private RadioButton couponGrp0;
    private RadioButton couponGrp1;
    private RadioButton couponGrp2;
    private RadioButton couponGrp3;
    private RecyclerView couponRecycler;
    private RelativeLayout couponNoList;
    private LyfCouponAdapter mAdapter;
    private CouponBean.DataEntity mData;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.lyf_coupon_activity;
    }

    @Override
    public void initView(View view) {
        couponTb = (ImToolBar) findViewById(R.id.coupon_tb);
        couponNumber = (SimpleCellLinearLayout) findViewById(R.id.coupon_number);
        couponPwd = (SimpleCellLinearLayout) findViewById(R.id.coupon_pwd);
        couponAdd = (Button) findViewById(R.id.coupon_add);
        couponRgpFilter = (RadioGroup) findViewById(R.id.coupon_rgp_filter);
        couponGrp0 = (RadioButton) findViewById(R.id.coupon_grp_0);
        couponGrp1 = (RadioButton) findViewById(R.id.coupon_grp_1);
        couponGrp2 = (RadioButton) findViewById(R.id.coupon_grp_2);
        couponGrp3 = (RadioButton) findViewById(R.id.coupon_grp_3);
        couponRecycler = (RecyclerView) findViewById(R.id.coupon_recycler);
        couponNoList = (RelativeLayout) findViewById(R.id.coupon_no_list);
        //绑定优惠券
        couponAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindCoupon();
            }
        });
    }

    /**
     * 绑定优惠券
     */
    private void bindCoupon() {
        if (TextUtils.isEmpty(couponNumber.getEtText())) {
            Toast.makeText(LyfCouponActivity.this, "请输入卡号", Toast.LENGTH_SHORT).show();
            couponNumber.etRequestFocus();
            return;
        }
        if (TextUtils.isEmpty(couponPwd.getEtText())) {
            Toast.makeText(LyfCouponActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            couponPwd.etRequestFocus();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("couponCode", couponNumber.getEtText());
        params.put("pwd", couponPwd.getEtText());
        params.put("companyId", OdyApplication.COMPANYID);
        showLoading();
        OkHttpManager.postAsyn(Constants.BIND_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                hideLoading();
                Toast.makeText(LyfCouponActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                hideLoading();
                if (response != null) {
                    if (TextUtils.isEmpty(response.message)) {
                        if ("0".equals(response.code)) {
                            response.message = "添加成功";
                        } else {
                            response.message = "添加失败";
                        }
                    }
                    Toast.makeText(LyfCouponActivity.this, response.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LyfCouponActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, params);
    }

    @Override
    public void doBusiness(Context mContext) {
        //优惠券列表适配
        mAdapter = new LyfCouponAdapter(this, new ArrayList<CouponBean.DataEntity.CouponListEntity>(), 0);
        couponRecycler.setLayoutManager(new LinearLayoutManager(this));
        couponRecycler.setAdapter(mAdapter);
        //选择伊点卡状态
        couponRgpFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                couponNoList.setVisibility(View.GONE);
                switch (checkedId) {
                    case R.id.coupon_grp_0:
                        if (mData != null) {
                            mAdapter.setData(mData.canUseCouponList, 0);
                        }
                        break;
                    case R.id.coupon_grp_1:
                        if (mData != null) {
                            mAdapter.setData(mData.usedCouponList, 1);
                        }
                        break;
                    case R.id.coupon_grp_2:
                        if (mData != null) {
                            mAdapter.setData(mData.shareCouponList, 2);
                        }
                        break;
                    case R.id.coupon_grp_3:
                        if (mData != null) {
                            mAdapter.setData(mData.expiredCouponList, 3);
                        }
                        break;
                }
                if (null != mAdapter && mAdapter.getItemCount() <= 0) {
                    couponNoList.setVisibility(View.VISIBLE);
                }
            }
        });
        getCouponList();
    }

    /**
     * 获取优惠券列表
     */
    private void getCouponList() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("companyId", OdyApplication.COMPANYID);
        showLoading();
        OkHttpManager.postAsyn(Constants.LyfCouponList, new OkHttpManager.ResultCallback<CouponBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                hideLoading();
            }

            @Override
            public void onResponse(CouponBean response) {
                hideLoading();
                couponNoList.setVisibility(View.GONE);
                if (response != null && response.getData() != null) {
                    mData = response.getData();
                    if (response.getData().canUseCouponList != null && response.getData().canUseCouponList.size() > 0) {
                        mAdapter.setData(response.getData().canUseCouponList, 0);
                    }
                }
                if (null != mAdapter && mAdapter.getItemCount() <= 0) {
                    couponNoList.setVisibility(View.VISIBLE);
                }
            }
        }, params);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
