package com.ody.p2p.settings.accountSafe.modifyPhone1;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * 原手机号丢失这个功能暂时被隐藏了!!!!!!!
 */
public class ModifyPhoneActivity extends BaseActivity implements View.OnClickListener, ModifyPhoneView {

    protected TextView tv_name;
    protected TextView tv_phone;
    protected TextView tv_get_validate_code;
    protected TextView tv_change_phone;
    protected TextView tv_phone_is_lost;
    protected RelativeLayout rl_big_back;
    protected EditText et_input_validate_code;
    protected String phone;

    protected ModifyPhonePresenter modifyPhonePresenter;


    @Override
    public void initPresenter() {
        modifyPhonePresenter = new ModifyPhonePresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_phone1;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_get_validate_code = (TextView) view.findViewById(R.id.tv_get_validate_code);
        tv_change_phone = (TextView) view.findViewById(R.id.tv_change_phone);
        tv_phone_is_lost = (TextView) view.findViewById(R.id.tv_phone_is_lost);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);


        //设置手机号
        phone = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null);
        if (!StringUtils.isEmpty(phone)) {
            String start = phone.substring(0, 3);
            String end = phone.substring(7, 11);
            StringBuffer sb = new StringBuffer();
            sb.append(start).append(getString(R.string.phone_not_show)).append(end);
            tv_phone.setText(sb);
        }

        tv_name.setText(getString(R.string.text2));

        rl_big_back.setOnClickListener(this);
        tv_get_validate_code.setOnClickListener(this);
        tv_change_phone.setOnClickListener(this);
        tv_phone_is_lost.setOnClickListener(this);

        //"下一步"置灰
        StringUtils.setTextviewGray(tv_change_phone);
        //将textview动态置灰或置红
        StringUtils.setTextviewClickChange(et_input_validate_code,tv_change_phone);
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
        if (v.equals(tv_get_validate_code)){
            modifyPhonePresenter.getValidateCode();
        }
        if (v.equals(tv_change_phone)){
            modifyPhonePresenter.toNext(et_input_validate_code.getText().toString());
        }
        if (v.equals(tv_phone_is_lost)){//手机号丢失----暂时隐藏起来了
            ToastUtils.showShort(getString(R.string.ui_null));
        }
    }

    @Override
    public void setValidateCodeClickable(boolean flag) {
        tv_get_validate_code.setClickable(flag);
    }

    @Override
    public void getValidateFocus() {
        et_input_validate_code.requestFocus();
    }

    @Override
    public void setValidateText(String text) {
        tv_get_validate_code.setText(text);
    }

    @Override
    public boolean checkValidateCode(String captchas) {
        if (StringUtils.isEmpty(captchas)) {
            ToastUtils.showShort(getString(R.string.verifycode_null));
            return false;
        }
        return true;
    }

    @Override
    public void toActivity(Class clazz) {
        mBaseOperation.forward(clazz);
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
