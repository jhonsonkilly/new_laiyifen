package com.ody.p2p.settings.accountSafe.modifyPsd1;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

public class ModifyPsdActivity extends BaseActivity implements View.OnClickListener,ModifyPsdView {

    private TextView tv_name;
    private RelativeLayout rl_big_back;
    private TextView tv_phone;
    private EditText et_input_validate_code;
    private ImageView iv_cha;
    private TextView tv_get_validate_code;
    private TextView tv_next;

    private ModifyPsdPresenter modifyPsdPresenter;
    private String phone;

    @Override
    public void initPresenter() {
        modifyPsdPresenter = new ModifyPsdPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_psd1;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout)view.findViewById(R.id.rl_big_back);
        tv_phone = (TextView)view.findViewById(R.id.tv_phone);
        iv_cha = (ImageView)view.findViewById(R.id.iv_cha);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        tv_get_validate_code = (TextView)view.findViewById(R.id.tv_get_validate_code);
        tv_next = (TextView)view.findViewById(R.id.tv_next);


        tv_name.setText(getString(R.string.phone_verify));

        //设置手机号
        phone = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null);
//        phone = "18516513113";//测试需要
        if (!StringUtils.isEmpty(phone)){
            String start = phone.substring(0,3);
            String end = phone.substring(7,11);
            StringBuffer sb = new StringBuffer();
            sb.append(start).append(getString(R.string.phone_not_show)).append(end);

            tv_phone.setText(sb);
        }

        rl_big_back.setOnClickListener(this);
        iv_cha.setOnClickListener(this);
        tv_get_validate_code.setOnClickListener(this);
        tv_next.setOnClickListener(this);

        StringUtils.operateCha(et_input_validate_code,iv_cha);

        //"下一步"置灰
        StringUtils.setTextviewGray(tv_next);
        //将textview动态置灰或置红
        StringUtils.setTextviewClickChange(et_input_validate_code,tv_next);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        modifyPsdPresenter.removeHd();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(iv_cha)){
            et_input_validate_code.setText("");
        }
        if (v.equals(tv_get_validate_code)){
            if (!StringUtils.isEmpty(phone)){
                modifyPsdPresenter.getValidateCode(phone);
            }
        }
        if (v.equals(tv_next)){
            modifyPsdPresenter.toNext(phone,et_input_validate_code.getText().toString());
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
        finish();
    }
}
