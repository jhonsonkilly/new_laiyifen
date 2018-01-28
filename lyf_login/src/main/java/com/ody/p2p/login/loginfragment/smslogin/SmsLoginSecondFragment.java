package com.ody.p2p.login.loginfragment.smslogin;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.login.R;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 *
 */
public class SmsLoginSecondFragment extends BaseFragment implements SmsLoginSecondView, View.OnClickListener {
    View head;
    RelativeLayout rl_big_back;
    TextView tv_name;
    EditText et_input_psd;
    RelativeLayout rl_cha_psd;
    RelativeLayout rl_visiable_psd;
    EditText et_input_psd2;
    RelativeLayout rl_cha_psd2;
    RelativeLayout rl_visiable_psd2;
    TextView tv_finish;
    ImageView iv_visible_confirm_psd1, iv_visible_confirm_psd2;

    private SmsLoginSecondPresenter smsLoginSecondPresenter;

    @Override
    public void initPresenter() {
        smsLoginSecondPresenter = new SmsLoginSecondPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sms_login2;
    }

    @Override
    public void initView(View view) {
        head=view.findViewById(R.id.head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        et_input_psd = (EditText) view.findViewById(R.id.et_input_psd);
        rl_cha_psd = (RelativeLayout) view.findViewById(R.id.rl_cha_psd);
        rl_visiable_psd = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd);
        et_input_psd2 = (EditText) view.findViewById(R.id.et_input_psd2);
        rl_cha_psd2 = (RelativeLayout) view.findViewById(R.id.rl_cha_psd2);
        rl_visiable_psd2 = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd2);
        tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        iv_visible_confirm_psd1 = (ImageView) view.findViewById(R.id.iv_visible_confirm_psd1);
        iv_visible_confirm_psd2 = (ImageView) view.findViewById(R.id.iv_visible_confirm_psd2);

        tv_name.setText(R.string.set_password);

        rl_big_back.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        rl_visiable_psd.setOnClickListener(this);
        rl_visiable_psd2.setOnClickListener(this);
        rl_cha_psd.setOnClickListener(this);
        rl_cha_psd2.setOnClickListener(this);

        StringUtils.operateCha(et_input_psd, rl_cha_psd);
        StringUtils.operateCha(et_input_psd2, rl_cha_psd2);

        StringUtils.limitInputType(et_input_psd);
        StringUtils.limitInputType(et_input_psd2);

        tv_finish.setTextColor(RUtils.getColorById(getContext(),RUtils.BTN_TEXT_COLOR));
        tv_finish.setBackgroundResource(RUtils.getDrawableRes(getContext(),RUtils.BTN_BACKGROUND_COLOR));
    }

    @Override
    public void doBusiness(Context mContext) {
        head.setVisibility(View.GONE);
    }

    @Override
    public boolean checkPsd(String et_psd_first, String et_psd_second) {
        if (!StringUtils.checkPsdLength(et_psd_first)) {//首次输入的密码长度不合要求
            showToast(getString(R.string.password_lenght_6for18));
            return false;
        }
        if (!et_psd_first.equals(et_psd_second)) {
            showToast(getString(R.string.Password_please_enter_again));
            return false;
        }
        return true;
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void finishActivity() {
//        finish();
        if (null != smsLoginCallBack) {
            smsLoginCallBack.smsLoginClose();
        }
    }


    private boolean isPsdVisiable = false;
    private boolean isPsdVisiable2 = false;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_big_back) {
//            finish();
            if (null != smsLoginCallBack) {
                smsLoginCallBack.smsLoginClose();
            }
        }
        if (v.getId() == R.id.tv_finish) {
            if (et_input_psd == null) {
                return;
            }
            if (et_input_psd.getText() == null) {
                return;
            }
            if (et_input_psd2 == null) {
                return;
            }
            if (et_input_psd2.getText() == null) {
                return;
            }
            smsLoginSecondPresenter.finishRegister(et_input_psd.getText().toString(), et_input_psd2.getText().toString());
        }
        if (v.getId() == R.id.rl_visiable_psd) {
            if (!isPsdVisiable) {
                isPsdVisiable = true;
                StringUtils.psdIsVisiable(et_input_psd, isPsdVisiable);
                iv_visible_confirm_psd1.setImageResource(R.drawable.user_pwd_on);
            } else {
                isPsdVisiable = false;
                StringUtils.psdIsVisiable(et_input_psd, isPsdVisiable);
                iv_visible_confirm_psd1.setImageResource(R.drawable.user_pwd_off);
            }
        }
        if (v.getId() == R.id.rl_visiable_psd2) {
            if (!isPsdVisiable2) {
                isPsdVisiable2 = true;
                StringUtils.psdIsVisiable(et_input_psd2, isPsdVisiable2);
                iv_visible_confirm_psd2.setImageResource(R.drawable.user_pwd_on);
            } else {
                isPsdVisiable2 = false;
                StringUtils.psdIsVisiable(et_input_psd2, isPsdVisiable2);
                iv_visible_confirm_psd2.setImageResource(R.drawable.user_pwd_off);
            }
        }
        if (v.getId() == R.id.rl_cha_psd) {
            et_input_psd.setText("");
        }
        if (v.getId() == R.id.rl_cha_psd2) {
            et_input_psd2.setText("");
        }
    }

    SmsLoginCallBack smsLoginCallBack;

    public void setSmsLoginCallBack(SmsLoginCallBack smsLoginCallBack) {
        this.smsLoginCallBack = smsLoginCallBack;
    }

    public interface SmsLoginCallBack {
        void smsLoginClose();
    }
}
