package com.ody.p2p.check.coupon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;

/**
 * Created by ${tang} on 2016/8/15.
 */
public class UseCouponActivity extends BaseActivity implements View.OnClickListener, UseCouponView{

    protected ImageView iv_back;
    protected TextView tv_more;
    protected TextView tv_add;
    protected RelativeLayout rl_add;
    protected ListView lv_cp;
    protected TextView tv_ok;
    protected CouponAdapter couponAdapter;
    protected RelativeLayout rl_empty;
    protected RelativeLayout rl_content;
    private TextView tv_title;
    private UseCouponPresenter presenter;
    private TextView tv_empty;

    @Override
    public void initPresenter() {
        presenter = new UseCouponImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_use_coupon;
    }

    @Override
    public void initView(View view) {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_more = (TextView) findViewById(R.id.tv_more);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        rl_add = (RelativeLayout) findViewById(R.id.rl_add);
        lv_cp = (ListView) findViewById(R.id.lv_cp);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText(RUtils.getStringRes(this,RUtils.USE_COUPON));
        tv_empty= (TextView) findViewById(R.id.tv_empty);
        tv_empty.setText(RUtils.getStringRes(this,RUtils.NO_COUPON));
        iv_back.setOnClickListener(this);
        rl_add.setOnClickListener(this);
        setAdapter();
        couponAdapter.isUse(true);
        lv_cp.setAdapter(couponAdapter);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    public void setAdapter() {
        couponAdapter = new CouponAdapter(this);
    }

    @Override
    public void resume() {
        presenter.useCouponlist();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_ok) {
            String couponId="";
            if(couponAdapter.getAll()!=null&&couponAdapter.getAll().size()>0){
                for(int i=0;i<couponAdapter.getAll().size();i++){
                    if(couponAdapter.getItem(i).selected==1){
                        couponId=couponAdapter.getItem(i).couponId;
                    }
                }
            }
            presenter.saveUseCoupon(couponId);
        } else if (v.getId() == R.id.rl_add) {
            JumpUtils.ToActivity(JumpUtils.ADDCOUPON);
        }
    }

    @Override
    public void useCouponList(UseCouponBean useCouponBean) {
        if (useCouponBean.data == null) {
            return;
        }
        if (useCouponBean.data.coupons != null && useCouponBean.data.coupons.size() > 0) {
            couponAdapter.addData(useCouponBean.data.coupons);
            tv_ok.setOnClickListener(this);
        } else {
            onError();
        }
    }

    @Override
    public void finishActivity() {
        Intent intent=new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onError() {
        rl_content.setVisibility(View.GONE);
        rl_empty.setVisibility(View.VISIBLE);
    }


}
