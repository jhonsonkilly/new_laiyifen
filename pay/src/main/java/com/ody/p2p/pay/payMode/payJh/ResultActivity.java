package com.ody.p2p.pay.payMode.payJh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.pay.R;
import com.ody.p2p.utils.JumpUtils;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>包名:com.ody.p2p.main.pay</p>
 * <p>文件名:未命名文件夹</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:zhoujiawei@laiyifen.com">vernal(周佳伟)</a>
 */
public class ResultActivity extends BaseActivity {

    String orderId, orderMoney, orderType;

    @Override
    public int bindLayout() {
        return R.layout.activity_res_layout;
    }

    @Override
    public void initView(View view) {


        Intent intent = getIntent();
        String rs = intent.getStringExtra("CCBPARAM");
        Log.i("ResultActivity", rs);


        if (rs.contains("SUCCESS=Y")) {
            //tv_name.setText("支付成功");

            Bundle bd = new Bundle();
            bd.putString(Constants.ORDER_ID, OdyApplication.getString(Constants.ORDER_ID, ""));
            JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            finish();


        } else {
            Bundle bd = new Bundle();
            bd.putString(Constants.ORDER_ID, OdyApplication.getString(Constants.ORDER_ID, ""));
            JumpUtils.ToActivity(JumpUtils.PAYFAIL, bd);
            finish();
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {

    }


}
