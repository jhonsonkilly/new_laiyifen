package com.ody.p2p.pay.fail;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.pay.R;
import com.ody.p2p.pay.payMode.payOnline.PayFailBean;
import com.ody.p2p.pay.payMode.payOnline.PayOnlineActivity;
import com.ody.p2p.utils.DateTimeUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://payFail")
public class PayFailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_name;
    protected TextView tv_pay_again;
    private TextView tv_time;
    private RelativeLayout rl_big_back;
    private String userId;
    private String orderId;
    private String orderMoney;
    private CountDownTimer timer;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_fail;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_pay_again = (TextView) view.findViewById(R.id.tv_pay_again);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        tv_name.setText(getString(R.string.pay_status_title));
        tv_time = (TextView) findViewById(R.id.tv_time);
        rl_big_back.setOnClickListener(this);
        tv_pay_again.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        userId = getIntent().getStringExtra(Constants.USER_ID);
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        orderMoney = getIntent().getStringExtra(Constants.ORDER_MONEY);
    }

    @Override
    public void resume() {

        getCancelTime();

    }

    private void getCancelTime() {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderId);
        params.put("v", 2.0 + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("sceneType", 1 + "");
        showLoading();
        OkHttpManager.getAsyn(Constants.ORDER_INFO, params, new OkHttpManager.ResultCallback<PayFailBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(PayFailBean response) {
                hideLoading();
                if (null != response && null != response.data) {
                    if (response.data.cancelTime > 0) {
                        runTime(response.data.cancelTime);
                    }
                }
            }
        });
    }

    public void runTime(long time) {
        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_time.setText(getString(R.string.unpay_please) + DateTimeUtils.formatTime(millisUntilFinished) + getString(R.string.complete_the_payment));
            }

            @Override
            public void onFinish() {
                tv_time.setText(R.string.order_automatic_cancel);
            }
        };
        timer.start();
    }

    @Override
    public void destroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)) {
            finish();
        }
        if (v.equals(tv_pay_again)) {
            mBaseOperation.addParameter(Constants.USER_ID, userId);
            mBaseOperation.addParameter(Constants.ORDER_ID, orderId);
            mBaseOperation.addParameter(Constants.ORDER_MONEY, orderMoney);
            mBaseOperation.forward(PayOnlineActivity.class);
            finish();
        }
    }
}
