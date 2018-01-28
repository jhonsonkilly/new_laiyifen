package com.ody.p2p.settings.accountSafe.modifyPhone2;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.settings.accountSafe.AccountSafeActivity;
import com.ody.p2p.utils.StringUtils;

public class ModifyPhoneSecondActivity extends BaseActivity implements View.OnClickListener,ModifyPhoneSecondView {

    protected TextView tv_name;
    protected TextView tv_get_validate_code;
    protected TextView tv_bind_phone;
    protected RelativeLayout rl_big_back;
    protected EditText et_phone;
    protected EditText et_input_validate_code;
    protected ImageView iv_phone_cha;
    protected ImageView iv_validate_code_cha;
    protected String ut;//德升项目联合登录成功后绑定手机需要这个参数
    protected String bindPhoneUrl;//方便不同项目设置绑定手机号的请求路径
    protected int canClick;
    protected String OrgianlMobile;

    protected ModifyPhoneSecondPresenter modifyPhoneSecondPresenter;

    @Override
    public void initPresenter() {
        modifyPhoneSecondPresenter = new ModifyPhoneSecondPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_phone2;
    }

    @Override
    public void initView(View view) {
        OrgianlMobile = getIntent().getStringExtra("orgianlMobile");
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_get_validate_code = (TextView)view.findViewById(R.id.tv_get_validate_code);
        tv_bind_phone = (TextView)view.findViewById(R.id.tv_bind_phone);
        rl_big_back = (RelativeLayout)view.findViewById(R.id.rl_big_back);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        iv_phone_cha = (ImageView) view.findViewById(R.id.iv_phone_cha);
        iv_validate_code_cha = (ImageView) view.findViewById(R.id.iv_validate_code_cha);

        tv_name.setText(getString(R.string.modify_phone2));

        rl_big_back.setOnClickListener(this);
        iv_phone_cha.setOnClickListener(this);
        iv_validate_code_cha.setOnClickListener(this);
        tv_get_validate_code.setOnClickListener(this);
        tv_bind_phone.setOnClickListener(this);

        StringUtils.operateCha(et_phone,iv_phone_cha);
        StringUtils.operateCha(et_input_validate_code,iv_validate_code_cha);

        StringUtils.setTextviewGray(tv_bind_phone);

        if (canClick!=1){
            et_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!StringUtils.isEmpty(et_phone.getText().toString()) && !StringUtils.isEmpty(et_input_validate_code.getText().toString())){
                        tv_bind_phone.setClickable(true);
                        tv_bind_phone.setBackgroundResource(R.drawable.shape_can_click);
                    }else {
                        tv_bind_phone.setClickable(false);
                        tv_bind_phone.setBackgroundResource(R.drawable.shape_cannot_click);
                    }
                }
            });

            et_input_validate_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!StringUtils.isEmpty(et_phone.getText().toString()) && !StringUtils.isEmpty(et_input_validate_code.getText().toString())){
                        tv_bind_phone.setClickable(true);
                        tv_bind_phone.setBackgroundResource(R.drawable.shape_can_click);
                    }else {
                        tv_bind_phone.setClickable(false);
                        tv_bind_phone.setBackgroundResource(R.drawable.shape_cannot_click);
                    }
                }
            });
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        modifyPhoneSecondPresenter.removeHd();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(iv_phone_cha)){
            et_phone.setText("");
        }
        if (v.equals(iv_validate_code_cha)){
            et_input_validate_code.setText("");
        }
        if (v.equals(tv_get_validate_code)){
            modifyPhoneSecondPresenter.checkPhoneIsRegistered(et_phone.getText().toString());
        }
        if (v.equals(tv_bind_phone)){
            modifyPhoneSecondPresenter.bindPhone(OrgianlMobile, et_phone.getText().toString(),et_input_validate_code.getText().toString());
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
    public void toAccountSafeActivity() {
        Intent intent = new Intent(this, AccountSafeActivity.class);
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public String getUnionUt() {
        return ut;
    }
}
