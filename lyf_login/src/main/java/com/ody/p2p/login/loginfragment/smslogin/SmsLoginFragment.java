package com.ody.p2p.login.loginfragment.smslogin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.BaseVerificationCodeFragment;
import com.ody.p2p.login.loginfragment.LoginFragmentActivity;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://smsLogin")
public class SmsLoginFragment extends BaseVerificationCodeFragment implements View.OnClickListener {
    View head;
    public TextView tv_name;
    public TextView tv_right_text;
    RelativeLayout rl_big_back;
    TextView tv_psd_login;
    EditText et_input_phone;
    RelativeLayout rl_cha;
    EditText et_input_validate_code;
    RelativeLayout rl_cha_validate_code;
    RelativeLayout rl_get_validate_code;
    TextView tv_login;
    TextView tv_get_captcha;

    @Override
    public TextView getCaptchaText() {
        return tv_get_captcha;
    }

    private boolean isNewUser = true;//是否是新用户

    public boolean isNewUser() {
        return isNewUser;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sms_login1;
    }

    @Override
    public void initView(View view) {
        head = view.findViewById(R.id.head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        tv_psd_login = (TextView) view.findViewById(R.id.tv_psd_login);
        et_input_phone = (EditText) view.findViewById(R.id.et_input_phone);
        rl_cha = (RelativeLayout) view.findViewById(R.id.rl_cha);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        rl_cha_validate_code = (RelativeLayout) view.findViewById(R.id.rl_cha_validate_code);
        rl_get_validate_code = (RelativeLayout) view.findViewById(R.id.rl_get_validate_code);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_get_captcha = (TextView) view.findViewById(R.id.tv_get_captcha);

        tv_name.setText(R.string.login);
        tv_right_text.setText(R.string.register);

        rl_big_back.setOnClickListener(this);
        tv_psd_login.setOnClickListener(this);
        tv_get_captcha.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_right_text.setOnClickListener(this);
        rl_cha.setOnClickListener(this);
        rl_cha_validate_code.setOnClickListener(this);

        tv_get_captcha.setTextColor(RUtils.getColorById(getContext(), RUtils.SYS_THEME_COLOR));
        tv_login.setTextColor(RUtils.getColorById(getContext(), RUtils.BTN_TEXT_COLOR));
        tv_login.setBackgroundResource(RUtils.getDrawableRes(getContext(), RUtils.BTN_BACKGROUND_COLOR));
    }

    @Override
    public void doBusiness(Context mContext) {
        head.setVisibility(View.GONE);
        StringUtils.operateCha(et_input_phone, rl_cha);
        StringUtils.operateCha(et_input_validate_code, rl_cha_validate_code);

        et_input_phone.setText(OdyApplication.getString(com.ody.p2p.Constants.LOGIN_MOBILE_PHONE, ""));
        et_input_phone.setSelection(et_input_phone.getText().toString().length());
//        bind_phone

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_big_back || v.getId() == R.id.tv_psd_login) {
//            finish();
            if (null != smsLoginCallBack) {
                smsLoginCallBack.smsClose();
            }
        }
        if (v.getId() == R.id.tv_get_captcha) {
            onBtnGetVerificationCodeClick();
            return;
        }
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
            if (null != smsLoginCallBack) {
                smsLoginCallBack.nextRegister(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
            }
//            smsLoginPresenter.setPsdOrLogin(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
        }
        if (v.getId() == R.id.tv_right_text) {
//            mBaseOperation.forward(RegisterFirstActivity.class);
            Bundle bd = new Bundle();
            bd.putInt("loginType", LoginFragmentActivity.SMS_LOGIN);
            JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);
        }
        if (v.getId() == R.id.rl_cha) {
            et_input_phone.setText("");
        }
        if (v.getId() == R.id.rl_cha_validate_code) {
            et_input_validate_code.setText("");
        }
    }


    @Override
    protected int getCurrentType() {
        return LoginDocument.SMS_LOGIN;
    }

    @Override
    public String getVerificationCode() {
        return et_input_validate_code.getText().toString().trim();
    }

    @Override
    public String getTelephone() {
        return et_input_phone.getText().toString().trim();
    }

    @Override
    public void onTelephoneAlreadyRegistered(LoginDocument document) {
        isNewUser = false;
        super.onTelephoneAlreadyRegistered(document);
    }

    @Override
    public void onTelephoneUnregistered(LoginDocument document) {
        isNewUser = true;
        super.onTelephoneUnregistered(document);
    }

    SmsLoginCallBack smsLoginCallBack;

    public void setSmsLoginCallBack(SmsLoginCallBack smsLoginCallBack) {
        this.smsLoginCallBack = smsLoginCallBack;
    }


    public interface SmsLoginCallBack {
        void nextRegister(String mobile, String captchas);

        void smsClose();
    }
}
