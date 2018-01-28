package com.ody.p2p.settings.accountSafe;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.settings.accountSafe.modifyPhone1.ModifyPhoneActivity;
import com.ody.p2p.settings.accountSafe.modifyPsd2.ModifyPsdSecondActivity;
import com.ody.p2p.utils.StringUtils;

public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_phone;
    private RelativeLayout rl_big_back;
    private RelativeLayout rl_modify_psd;
    private RelativeLayout rl_modify_phone;
    private String phone;


    @Override
    public void initPresenter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_account_safe;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_phone = (TextView)view.findViewById(R.id.tv_phone);
        rl_big_back = (RelativeLayout)view.findViewById(R.id.rl_big_back);
        rl_modify_psd = (RelativeLayout)view.findViewById(R.id.rl_modify_psd);
        rl_modify_phone = (RelativeLayout)view.findViewById(R.id.rl_modify_phone);


        //设置手机号
        phone = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null);
        if (!StringUtils.isEmpty(phone)){
            String start = phone.substring(0,3);
            String end = phone.substring(7,11);
            StringBuffer sb = new StringBuffer();
            sb.append(start).append(getString(R.string.phone_not_show)).append(end);
            tv_phone.setText(sb);
        }

        tv_name.setText(getString(R.string.account_safe));

        rl_big_back.setOnClickListener(this);
        rl_modify_psd.setOnClickListener(this);
        rl_modify_phone.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

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
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(rl_modify_psd)){
            mBaseOperation.forward(ModifyPsdSecondActivity.class);
        }
        if (v.equals(rl_modify_phone)){
            mBaseOperation.forward(ModifyPhoneActivity.class);
        }
    }
}
