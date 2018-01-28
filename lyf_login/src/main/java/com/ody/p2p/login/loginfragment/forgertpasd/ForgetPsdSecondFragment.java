package com.ody.p2p.login.loginfragment.forgertpasd;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.login.R;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * 测试而已
 */
public class ForgetPsdSecondFragment extends BaseFragment implements ForgetPsdSecondView, View.OnClickListener {
    View head;
    protected TextView tv_name;
    protected EditText et_input_psd;
    protected RelativeLayout rl_cha_psd;
    protected RelativeLayout rl_visiable_psd;
    protected EditText et_input_psd2;
    protected RelativeLayout rl_cha_psd2;
    protected RelativeLayout rl_visiable_psd2;
    protected TextView tv_finish;
    protected RelativeLayout rl_big_back;
    protected ImageView iv_visible_psd1, iv_visible_psd2;

    protected ForgetPsdSecondPresenter forgetPsdSecondPresenter;

    @Override
    public void initPresenter() {
        forgetPsdSecondPresenter = new ForgetPsdSecondPresenterImpl(this);
    }

    String mobile="";String smsCode="";
    public void setData(String mobile,String smsCode){
        this.mobile=mobile;
        this.smsCode=smsCode;
    }

    @Override
    public Context getClassContext() {
        return getContext();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_psd2;
    }

    @Override
    public void initView(View view) {
        head = view.findViewById(R.id.head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        et_input_psd = (EditText) view.findViewById(R.id.et_input_psd);
        rl_cha_psd = (RelativeLayout) view.findViewById(R.id.rl_cha_psd);
        rl_visiable_psd = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd);
        et_input_psd2 = (EditText) view.findViewById(R.id.et_input_psd2);
        rl_cha_psd2 = (RelativeLayout) view.findViewById(R.id.rl_cha_psd2);
        rl_visiable_psd2 = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd2);
        tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_visible_psd1 = (ImageView) view.findViewById(R.id.iv_visible_psd1);
        iv_visible_psd2 = (ImageView) view.findViewById(R.id.iv_visible_psd2);

        tv_name.setText(R.string.find_password);

        rl_big_back.setOnClickListener(this);
        rl_cha_psd.setOnClickListener(this);
        rl_cha_psd2.setOnClickListener(this);
        rl_visiable_psd.setOnClickListener(this);
        rl_visiable_psd2.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        tv_finish.setOnClickListener(this);

        StringUtils.operateCha(et_input_psd, rl_cha_psd);
        StringUtils.operateCha(et_input_psd2, rl_cha_psd2);

        //限制密码的输入符号,起码不能有中文
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
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void finishActivity() {
//        finish();
        if (null != forGetPsdCallBack) {
            forGetPsdCallBack.forgetPsdClose();
        }
    }

    private boolean psdIsVisiable = false;
    private boolean psdIsVisiable2 = false;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_big_back) {
//            finish();
            if (null != forGetPsdCallBack) {
                forGetPsdCallBack.forgetPsdClose();
            }
        } else if (v.getId() == R.id.rl_cha_psd) {
            et_input_psd.setText("");
        } else if (v.getId() == R.id.rl_cha_psd2) {
            et_input_psd2.setText("");
        } else if (v.getId() == R.id.rl_visiable_psd) {
            if (!psdIsVisiable) {
                psdIsVisiable = true;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_visible_psd1.setImageResource(R.drawable.user_pwd_on);
            } else {
                psdIsVisiable = false;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_visible_psd1.setImageResource(R.drawable.user_pwd_off);
            }
        } else if (v.getId() == R.id.rl_visiable_psd2) {
            if (!psdIsVisiable2) {
                psdIsVisiable2 = true;
                StringUtils.psdIsVisiable(et_input_psd2, psdIsVisiable2);
                iv_visible_psd2.setImageResource(R.drawable.user_pwd_on);
            } else {
                psdIsVisiable2 = false;
                StringUtils.psdIsVisiable(et_input_psd2, psdIsVisiable2);
                iv_visible_psd2.setImageResource(R.drawable.user_pwd_off);
            }
        } else if (v.getId() == R.id.tv_finish) {
            String phone = OdyApplication.getValueByKey(Constants.FORGET_MOBILE_PHONE, null);
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
            forgetPsdSecondPresenter.checkPsd(mobile,smsCode, et_input_psd.getText().toString(), et_input_psd2.getText().toString());
        }
    }

    ForGetPsdCallBack forGetPsdCallBack;

    public void setForGetPsdCallBack(ForGetPsdCallBack forGetPsdCallBack) {
        this.forGetPsdCallBack = forGetPsdCallBack;
    }

    public interface ForGetPsdCallBack {
        void forgetPsdClose();
    }
}
