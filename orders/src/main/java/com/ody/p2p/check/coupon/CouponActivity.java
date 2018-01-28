package com.ody.p2p.check.coupon;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.basepopupwindow.SelectMenu;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://coupon")
public class CouponActivity extends BaseActivity implements View.OnClickListener, CouponView {

    protected ImageView iv_back;
    protected TextView tv_title;
    protected ImageView iv_more;
    protected TextView tv_unused;
    protected TextView tv_have_used;
    protected TextView tv_over_due;
    protected View line_unused;
    protected View line_have_used;
    protected View line_over_due;
    protected ListView rv_coupon;
    protected RelativeLayout rl_over_due;
    protected RelativeLayout rl_have_used;
    protected RelativeLayout rl_unused;
    protected CouponAdapter couponAdapter;
    protected RelativeLayout rl_add;
    protected TextView tv_add;
    protected RelativeLayout rl_empty;
    protected TextView tv_empty;
    private CouponPresenter presenter;
    private CouponBean couponBean;
    private String[] menuStr = {getString(R.string.message)};

    private int[] menuRes = {R.drawable.ic_message};

    @Override
    public void initPresenter() {
        presenter = new CouponPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText(RUtils.getStringRes(this,RUtils.MY_COUPON));
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        tv_unused = (TextView) view.findViewById(R.id.tv_unused);
        tv_have_used = (TextView) view.findViewById(R.id.tv_have_used);
        tv_over_due = (TextView) view.findViewById(R.id.tv_over_due);
        line_unused = view.findViewById(R.id.line_unused);
        line_have_used = view.findViewById(R.id.line_have_used);
        line_over_due = view.findViewById(R.id.line_over_due);
        rv_coupon = (ListView) view.findViewById(R.id.lv_coupon);
        rl_over_due = (RelativeLayout) view.findViewById(R.id.rl_over_due);
        rl_have_used = (RelativeLayout) view.findViewById(R.id.rl_have_used);
        rl_unused = (RelativeLayout) view.findViewById(R.id.rl_unused);
        rl_add = (RelativeLayout) view.findViewById(R.id.rl_add);
        tv_add = (TextView) view.findViewById(R.id.tv_add);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        tv_empty= (TextView) findViewById(R.id.tv_empty);
        tv_empty.setText(RUtils.getStringRes(this,RUtils.NO_COUPON));
        rl_have_used.setOnClickListener(this);
        rl_over_due.setOnClickListener(this);
        rl_unused.setOnClickListener(this);
        rl_add.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        couponAdapter = new CouponAdapter(this);
        rv_coupon.setAdapter(couponAdapter);
    }

    @Override
    public void resume() {
        tv_have_used.setTextColor(getResources().getColor(R.color.main_title_color));
        tv_unused.setTextColor(getResources().getColor(R.color.theme_color));
        tv_over_due.setTextColor(getResources().getColor(R.color.main_title_color));
        presenter.couponCount();
        presenter.couponlist();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_have_used) {
            tv_have_used.setTextColor(getResources().getColor(R.color.theme_color));
            tv_unused.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_over_due.setTextColor(getResources().getColor(R.color.main_title_color));
            line_have_used.setVisibility(View.VISIBLE);
            line_unused.setVisibility(View.GONE);
            line_over_due.setVisibility(View.GONE);
            if (couponBean != null && couponBean.data != null && couponBean.data.usedCouponList != null && couponBean.data.usedCouponList.size() > 0) {
                onNormal();
                couponAdapter.addData(couponBean.data.usedCouponList);
            } else {
                onError();
            }
        } else if (v.getId() == R.id.rl_over_due) {
            tv_have_used.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_unused.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_over_due.setTextColor(getResources().getColor(R.color.theme_color));
            line_have_used.setVisibility(View.GONE);
            line_unused.setVisibility(View.GONE);
            line_over_due.setVisibility(View.VISIBLE);
            if (couponBean != null && couponBean.data != null && couponBean.data.expiredCouponList != null && couponBean.data.expiredCouponList.size() > 0) {
                onNormal();
                couponAdapter.addData(couponBean.data.expiredCouponList);
            } else {
                onError();
            }
        } else if (v.getId() == R.id.rl_unused) {
            tv_have_used.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_unused.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_over_due.setTextColor(getResources().getColor(R.color.main_title_color));
            line_have_used.setVisibility(View.GONE);
            line_unused.setVisibility(View.VISIBLE);
            line_over_due.setVisibility(View.GONE);
            if (couponBean != null && couponBean.data != null && couponBean.data.canUseCouponList != null && couponBean.data.canUseCouponList.size() > 0) {
                onNormal();
                couponAdapter.addData(couponBean.data.canUseCouponList);
            } else {
                onError();
            }
        } else if (v.getId() == R.id.rl_add) {
            JumpUtils.ToActivity(JumpUtils.ADDCOUPON);
        } else if (v.getId() == R.id.iv_more) {
            final List<MenuPopBean> popList = new ArrayList<>();
            for (int i = 0; i < menuStr.length; i++) {
                MenuPopBean bean = new MenuPopBean();
                bean.content = menuStr[i];
                bean.picRes = menuRes[i];
                popList.add(bean);
            }
            SelectMenu menu = new SelectMenu(this, popList);
            menu.setClickSelectListener(new SelectMenu.clickSelectMenuListener() {
                @Override
                public void click(int position) {
                    if (position == 0) {
                        JumpUtils.ToActivity(JumpUtils.MESSAGE);
                    }


                }
            });
            menu.showAsDropDown(iv_more, PxUtils.dipTopx(-55), 0);
        }
    }

    @Override
    public void couponlist(CouponBean couponBean) {
        if (couponBean != null && couponBean.data != null && couponBean.data.canUseCouponList != null && couponBean.data.canUseCouponList.size() > 0) {
            for (int i = 0; i < couponBean.data.canUseCouponList.size(); i++) {
                couponBean.data.canUseCouponList.get(i).isAvailable = 1;
            }
        }
        if (couponBean != null && couponBean.data != null && couponBean.data.expiredCouponList != null && couponBean.data.expiredCouponList.size() > 0) {
            for (int i = 0; i < couponBean.data.expiredCouponList.size(); i++) {
                couponBean.data.expiredCouponList.get(i).isAvailable = 0;
            }
        }
        if (couponBean != null && couponBean.data != null && couponBean.data.usedCouponList != null && couponBean.data.usedCouponList.size() > 0) {
            for (int i = 0; i < couponBean.data.usedCouponList.size(); i++) {
                couponBean.data.usedCouponList.get(i).isAvailable = 0;
            }
        }
        this.couponBean = couponBean;
        if (couponBean != null && couponBean.data != null && couponBean.data.canUseCouponList != null && couponBean.data.canUseCouponList.size() > 0) {
            couponAdapter.addData(couponBean.data.canUseCouponList);
        } else {
            onError();
        }
    }

    @Override
    public void couponCount(CouponCountBean couponCountBean) {
        if (couponCountBean == null && couponBean.data == null) {
            return;
        }
        if (couponCountBean.data.canUserCount > 0) {
            tv_unused.setText(getString(R.string.unused)+"(" + couponCountBean.data.canUserCount + ")");
        } else {
            tv_unused.setText(R.string.unused);
        }
        if (couponCountBean.data.usedCount > 0) {
            tv_have_used.setText(getString(R.string.used)+"(" + couponCountBean.data.usedCount + ")");
        } else {
            tv_have_used.setText(R.string.used);
        }

        if (couponCountBean.data.expiredCount > 0) {
            tv_over_due.setText(getString(R.string.out_of_date)+"(" + couponCountBean.data.expiredCount + ")");
        } else {
            tv_over_due.setText(R.string.out_of_date);
        }

    }

    @Override
    public void onError() {
        rv_coupon.setVisibility(View.GONE);
        rl_empty.setVisibility(View.VISIBLE);
    }

    public void onNormal() {
        rv_coupon.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
    }
}
