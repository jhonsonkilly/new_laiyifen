package com.ody.p2p.pay.success;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.pay.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.webactivity.WebActivity;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://paySuccess")
public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_name;
    protected TextView tv_check_order;
    protected TextView tv_continue_shopping;
    private RelativeLayout rl_big_back;
    private String orderId;
    protected ImageView iv_status_icon;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_check_order = (TextView) view.findViewById(R.id.tv_check_order);
        tv_continue_shopping = (TextView) view.findViewById(R.id.tv_continue_shopping);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_status_icon = (ImageView) findViewById(R.id.iv_status_icon);

        tv_name.setText(getString(R.string.pay_status_title));

        rl_big_back.setOnClickListener(this);
        tv_check_order.setOnClickListener(this);
        tv_continue_shopping.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)) {
            finish();
        }
        if (v.equals(tv_check_order)) {
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://orderdetail");
//            activityRoute.withParams(Constants.ORDER_ID,orderId).open();
            if (OdyApplication.SCHEME.equals("ds")) {
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, orderId);
                JumpUtils.ToActivity(JumpUtils.ORDERDETAIL, bd);
            } else {
                String url = OdyApplication.H5URL + "/my/order-detail.html?orderCode=" + orderId;
                JumpUtils.ToWebActivity(url, WebActivity.NO_TITLE, -1, "");
            }
            finish();
        }
        if (v.equals(tv_continue_shopping)) {
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://main");
//            activityRoute.open();
            Bundle db=new Bundle();
            db.putInt(Constants.GO_MAIN,0);
            JumpUtils.ToActivity(JumpUtils.MAIN,db);
            finish();
        }
    }
}
