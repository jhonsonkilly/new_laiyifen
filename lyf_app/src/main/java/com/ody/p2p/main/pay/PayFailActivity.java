package com.ody.p2p.main.pay;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.payMode.payOnline.CancelTimeBean;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.JumpUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/12/1.
 */

public class PayFailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_payfail;
    private TextView tv_pay_again;
    private String userId;
    private String orderId;
    private String orderMoney;
    private String orderType;

    private ImageView iv_back;

    @Override
    public void initPresenter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_pay_fail;
    }

    @Override
    public void initView(View view) {
        userId = getIntent().getStringExtra(Constants.USER_ID);
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        orderMoney = getIntent().getStringExtra(Constants.ORDER_MONEY);
        orderType = getIntent().getStringExtra("orderType");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_pay_again = (TextView) findViewById(R.id.tv_pay_again);
        tv_payfail = (TextView) findViewById(R.id.tv_payfail);
        tv_pay_again.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (TextUtils.isEmpty(orderId)) {


            tv_payfail.setVisibility(View.GONE);
            tv_pay_again.setVisibility(View.GONE);
            return;
        }
        getPayTime();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void getPayTime() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("orderCode", orderId);
        params.put("sceneType", 1 + "");
        params.put("v", 2.0 + "");
        showLoading();
        OkHttpManager.getAsyn(Constants.ORDER_INFO, params, new OkHttpManager.ResultCallback<CancelTimeBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(CancelTimeBean response) {
                if (response.code.equals("0") && null != response && response.data != null && response.data.cancelTime > 0) {
                    tv_payfail.setText("支付未完成，请在" + DateTimeUtils.formatDateTime(response.data.cancelTime * 1000 + System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "前完成支付！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_pay_again) {
            Bundle bd = new Bundle();
            bd.putString(Constants.ORDER_ID, orderId);
            bd.putString(Constants.USER_ID, userId);
            bd.putString(Constants.ORDER_MONEY, orderMoney + "");
            if (!TextUtils.isEmpty(orderType) && orderType.equals("1")) {
                bd.putString("orderType", orderType);
                bd.putInt("isfromNative", 0);
            } else {
                bd.putInt("isfromNative", 1);
            }
            JumpUtils.ToActivity(JumpUtils.PAYONLINE, bd);
            finish();
        } else if (v.getId() == R.id.iv_back) {
            finish();
        }
    }
}
