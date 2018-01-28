package com.ody.p2p.login.loginfragment.registeruser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.Bean.UserPortocolBean;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.BaseVerificationCodeFragment;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzy on 2016/10/18.
 */
public class RegisterFirstFragment extends BaseVerificationCodeFragment implements View.OnClickListener,TextWatcher {
    protected TextView tv_name;
    protected EditText et_input_phone;


    protected RelativeLayout rl_cha;//叉电话号码
    protected EditText et_input_validate_code;
    protected RelativeLayout rl_cha_validate_code;//叉验证码
    protected TextView tv_get_captcha;//获取验证码
    protected TextView tv_next;
    protected TextView tv_agreement;
    protected RelativeLayout rl_big_back;
    protected View head;

    @Override
    public TextView getCaptchaText() {
        return tv_get_captcha;
    }

    public String linkUrl="";//用户协议

    @Override
    public void initPresenter() {



    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register1;
    }

    @Override
    public void initView(View view) {
        head = view.findViewById(R.id.head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        et_input_phone = (EditText) view.findViewById(R.id.et_input_phone);
        rl_cha = (RelativeLayout) view.findViewById(R.id.rl_cha);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        rl_cha_validate_code = (RelativeLayout) view.findViewById(R.id.rl_cha_validate_code);
        tv_next = (TextView) view.findViewById(R.id.tv_next);
        tv_agreement = (TextView) view.findViewById(R.id.tv_agreement);
        tv_get_captcha = (TextView) view.findViewById(R.id.tv_get_captcha);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);

        tv_name.setText(R.string.register);

        rl_cha.setOnClickListener(this);
        rl_cha_validate_code.setOnClickListener(this);
        tv_get_captcha.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_next.setEnabled(false);
        rl_big_back.setOnClickListener(this);
        StringUtils.operateCha(et_input_phone, rl_cha);
        StringUtils.operateCha(et_input_validate_code, rl_cha_validate_code);
        tv_get_captcha.setTextColor(RUtils.getColorById(getContext(),RUtils.SYS_THEME_COLOR));
        tv_next.setTextColor(RUtils.getColorById(getContext(),RUtils.BTN_TEXT_COLOR));
        tv_next.setBackgroundResource(RUtils.getDrawableRes(getContext(),RUtils.BTN_BACKGROUND_COLOR));


        et_input_phone.addTextChangedListener(this);
        et_input_validate_code.addTextChangedListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        head.setVisibility(View.GONE);
        getUserProtocol();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_cha)) {
            et_input_phone.setText("");
        } else if (v.equals(rl_cha_validate_code)) {
            et_input_validate_code.setText("");
        } else if (v.equals(tv_get_captcha)) {
            onBtnGetVerificationCodeClick();
        } else if (v.equals(tv_agreement)) {
            if (!StringUtils.isEmpty(linkUrl)){
                JumpUtils.ToHelpWebActivity(JumpUtils.H5, linkUrl, 2, -0, getResources().getString(R.string.user_agreement),2);
//                JumpUtils.ToWebActivity(getContext(),linkUrl);
//                JumpUtils.ToWebActivity(JumpUtils.H5, linkUrl, WebActivity.PIC_TITLE, R.drawable.icon_headlines, null);
            }
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
            if (null != registerCallBack) {
                registerCallBack.nextRegister(et_input_phone.getText().toString(), et_input_validate_code.getText().toString());
            }
        } else if (v.equals(rl_big_back)) {
            if (null != registerCallBack) {
                registerCallBack.close();
            }
        }
    }

    @Override
    protected int getCurrentType() {
        return LoginDocument.REGISTER;
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
        //该手机号已经注册过
        ToastUtils.showShort(document.getMessage());
    }

    public int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    RegisterCallBack registerCallBack;

    public void setRegisterCallBack(RegisterCallBack registerCallBack) {
        this.registerCallBack = registerCallBack;
    }

    public interface RegisterCallBack {
        void nextRegister(String mobile, String captchas);

        void close();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(et_input_validate_code.getText().toString()) && !StringUtils.isEmpty(et_input_phone.getText().toString())) {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
    }


    //获取用户协议
    public void getUserProtocol() {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", "reg_protocol");
        params.put("pageCode", "APP_HOME");
        params.put("platform", "3");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<UserPortocolBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);

            }

            @Override
            public void onResponse(UserPortocolBean response) {
                if (response != null && response.data != null && response.data.reg_protocol != null && response.data.reg_protocol.size() > 0) {
                    linkUrl = response.data.reg_protocol.get(0).linkUrl;
                }
            }
        });
    }


}
