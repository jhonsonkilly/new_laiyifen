package com.ody.p2p.login.loginfragment.forgertpasd;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.BaseVerificationCodeFragment;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://forgetPsd")
public class ForgetPsdFragment extends BaseVerificationCodeFragment implements View.OnClickListener, ForgetPsdSecondView {

    protected TextView tv_name;
    protected EditText et_input_phone;
    protected ImageView iv_cha;
    protected EditText et_input_validate_code;
    protected ImageView iv_cha_validate_code;
    protected TextView tv_get_captcha;
    protected TextView tv_next;
    protected RelativeLayout rl_big_back;
    protected View head;
    private ForgetPsdSecondPresenter presenter;

    @Override
    public TextView getCaptchaText() {
        return tv_get_captcha;
    }

    @Override
    public void initPresenter() {
        presenter = new ForgetPsdSecondPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_psd1;
    }

    @Override
    public void initView(View view) {
        head = view.findViewById(R.id.head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        et_input_phone = (EditText) view.findViewById(R.id.et_input_phone);
        iv_cha = (ImageView) view.findViewById(R.id.iv_cha);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        iv_cha_validate_code = (ImageView) view.findViewById(R.id.iv_cha_validate_code);
        tv_get_captcha = (TextView) view.findViewById(R.id.tv_get_captcha);
        tv_next = (TextView) view.findViewById(R.id.tv_next);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);

        tv_name.setText(R.string.find_password);

        rl_big_back.setOnClickListener(this);
        tv_get_captcha.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_cha.setOnClickListener(this);
        iv_cha_validate_code.setOnClickListener(this);

        StringUtils.operateCha(et_input_phone, iv_cha);
        StringUtils.operateCha(et_input_validate_code, iv_cha_validate_code);
        et_input_phone.setText(OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, ""));
        et_input_phone.setSelection(et_input_phone.getText().toString().length());

        tv_get_captcha.setTextColor(RUtils.getColorById(getContext(), RUtils.SYS_THEME_COLOR));
        tv_next.setTextColor(RUtils.getColorById(getContext(), RUtils.BTN_TEXT_COLOR));
        tv_next.setBackgroundResource(RUtils.getDrawableRes(getContext(), RUtils.BTN_BACKGROUND_COLOR));
    }

    @Override
    public void doBusiness(Context mContext) {
        head.setVisibility(View.GONE);
    }

    public void getValidateFocus() {
        et_input_validate_code.requestFocus();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)) {
            if (null != forGetPsdCallBack) {
                forGetPsdCallBack.close();
            }
//            finish();
        } else if (v.equals(tv_get_captcha)) {
            onBtnGetVerificationCodeClick();
        } else if (v.equals(tv_next)) {
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
            presenter.checkCaptchas(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
        } else if (v.equals(iv_cha)) {
            et_input_phone.setText("");
        } else if (v.equals(iv_cha_validate_code)) {
            et_input_validate_code.setText("");
        }
    }

    @Override
    protected int getCurrentType() {
        return LoginDocument.FORGET_PASSWORD;
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
    public void onTelephoneUnregistered(LoginDocument document) {
        //未注册不能找回密码
        ToastUtils.showShort(document.getMessage());
    }

    ForGetPsdCallBack forGetPsdCallBack;

    public void setForgetPsdCallBack(ForGetPsdCallBack forGetPsdCallBack) {
        this.forGetPsdCallBack = forGetPsdCallBack;
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void finishActivity() {
        if (null != forGetPsdCallBack) {
            forGetPsdCallBack.ForgetPsdNext(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
        }
    }

    @Override
    public Context getClassContext() {
        return null;
    }

    public interface ForGetPsdCallBack {
        void ForgetPsdNext(String mobile, String captchas);

        void close();
    }
}
