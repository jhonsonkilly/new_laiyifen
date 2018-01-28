package com.ody.p2p.login.loginfragment.bindphone;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.BaseVerificationCodeFragment;

/**
 * Created by pzy on 2017/2/15.
 */
public class BindPhoneFragment extends BaseVerificationCodeFragment implements View.OnClickListener {
    TextView tv_get_captcha;
    TextView tv_login;
    EditText et_input_phone;
    EditText et_input_validate_code;


    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sms_login1;
    }

    @Override
    public TextView getCaptchaText() {
        return tv_get_captcha;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.head).setVisibility(View.GONE);
        view.findViewById(R.id.tv_psd_login).setVisibility(View.GONE);

        tv_get_captcha = (TextView) view.findViewById(R.id.tv_get_captcha);
        et_input_phone = (EditText) view.findViewById(R.id.et_input_phone);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        tv_login = (TextView) view.findViewById(R.id.tv_login);

        tv_get_captcha.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_get_captcha) {
            onBtnGetVerificationCodeClick();
        } else {
            if (v.getId() == R.id.tv_login) {
                if (et_input_phone == null) {
                    return;
                }
                if (et_input_phone.getText() == null) {
                    return;
                }
                if (et_input_validate_code == null) {
                    return;
                }
                if (et_input_validate_code.getText() == null) {
                    return;
                }
                if (null != bindPhoneCallBack) {
                    bindPhoneCallBack.nextRegister(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
                }
            }
        }
    }


    @Override
    protected int getCurrentType() {
        return LoginDocument.BIND_PHONE;
    }

    @Override
    public String getVerificationCode() {
        return et_input_validate_code.getText().toString().trim();
    }

    @Override
    public String getTelephone() {
        return et_input_phone.getText().toString().trim();
    }



    BindPhoneFragment.BindPhoneCallBack bindPhoneCallBack;

    public void setBindPhoneCallBack(BindPhoneFragment.BindPhoneCallBack bindPhoneCallBack) {
        this.bindPhoneCallBack = bindPhoneCallBack;
    }

    public interface BindPhoneCallBack {
        void nextRegister(String mobile, String captchas);
    }
}
