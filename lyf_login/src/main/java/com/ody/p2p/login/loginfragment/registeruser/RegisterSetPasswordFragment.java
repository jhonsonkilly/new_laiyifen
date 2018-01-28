package com.ody.p2p.login.loginfragment.registeruser;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.eventbus.FinishActivityEvent;
import com.ody.p2p.login.R;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class RegisterSetPasswordFragment extends BaseFragment implements View.OnClickListener, RegisterSetPasswordView {

    protected TextView tv_psd, tv_psd_again;
    protected EditText et_input_psd, et_input_inviter;
    protected RelativeLayout rl_cha_psd;
    protected RelativeLayout rl_visiable_psd;
    protected EditText et_input_psd2;
    protected RelativeLayout rl_cha_psd2;
    protected RelativeLayout rl_visiable_psd2;
    protected TextView tv_finish;
    protected RelativeLayout rl_big_back, rl_ds_inviter, rl_cha_psd3;
    protected ImageView iv_register_visible_psd1, iv_register_visible_psd2;
    protected View v_divide_line4;
    protected View head;
    protected int inviterFlag;//德升 新增邀请人一栏,该值为1代表德升
    protected int addPsd;//德升  添加密码也是继承本类 值为1代表德升
    protected int registerFlag = -1;

    protected RegisterSetPasswordPresenter registerSecondPresenter;

    @Override
    public void initPresenter() {
        registerSecondPresenter = new RegisterSetPasswordPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register2;
    }

    @Override
    public void initView(View view) {
        head = view.findViewById(R.id.head);
        tv_psd = (TextView) view.findViewById(R.id.tv_psd);
        tv_psd_again = (TextView) view.findViewById(R.id.tv_psd_again);
        et_input_psd = (EditText) view.findViewById(R.id.et_input_psd);
        et_input_inviter = (EditText) view.findViewById(R.id.et_input_inviter);
        rl_cha_psd = (RelativeLayout) view.findViewById(R.id.rl_cha_psd);
        rl_visiable_psd = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd);
        et_input_psd2 = (EditText) view.findViewById(R.id.et_input_psd2);
        rl_cha_psd2 = (RelativeLayout) view.findViewById(R.id.rl_cha_psd2);
        rl_visiable_psd2 = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd2);
        tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        iv_register_visible_psd1 = (ImageView) view.findViewById(R.id.iv_register_visible_psd1);
        iv_register_visible_psd2 = (ImageView) view.findViewById(R.id.iv_register_visible_psd2);
        rl_ds_inviter = (RelativeLayout) view.findViewById(R.id.rl_ds_inviter);
        rl_cha_psd3 = (RelativeLayout) view.findViewById(R.id.rl_cha_psd3);
        v_divide_line4 = view.findViewById(R.id.v_divide_line4);

        rl_cha_psd.setOnClickListener(this);
        rl_cha_psd2.setOnClickListener(this);
        rl_cha_psd3.setOnClickListener(this);
        rl_visiable_psd.setOnClickListener(this);
        rl_visiable_psd2.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        rl_big_back.setOnClickListener(this);

        StringUtils.operateCha(et_input_psd, rl_cha_psd);
        StringUtils.operateCha(et_input_psd2, rl_cha_psd2);
        StringUtils.operateCha(et_input_inviter, rl_cha_psd3);

        StringUtils.limitInputType(et_input_psd);
        StringUtils.limitInputType(et_input_psd2);
        StringUtils.limitInputType(et_input_inviter);

        tv_finish.setTextColor(RUtils.getColorById(getContext(),RUtils.BTN_TEXT_COLOR));
        tv_finish.setBackgroundResource(RUtils.getDrawableRes(getContext(),RUtils.BTN_BACKGROUND_COLOR));
        registerFlag = getActivity().getIntent().getIntExtra("registerCoupon", -1);
    }

    @Override
    public void doBusiness(Context mContext) {
        head.setVisibility(View.GONE);
    }

    private boolean psdIsVisiable = false;
    private boolean psdIsVisiable2 = false;

    @Override
    public void onClick(View v) {
        if (v.equals(rl_cha_psd)) {
            et_input_psd.setText("");
        } else if (v.equals(rl_cha_psd2)) {
            et_input_psd2.setText("");
        } else if (v.equals(rl_visiable_psd)) {
            if (!psdIsVisiable) {
                psdIsVisiable = true;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_register_visible_psd1.setImageResource(R.drawable.user_pwd_on);
            } else {
                psdIsVisiable = false;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_register_visible_psd1.setImageResource(R.drawable.user_pwd_off);
            }
        } else if (v.equals(rl_visiable_psd2)) {
            if (!psdIsVisiable2) {
                psdIsVisiable2 = true;
                StringUtils.psdIsVisiable(et_input_psd2, psdIsVisiable2);
                iv_register_visible_psd2.setImageResource(R.drawable.user_pwd_on);
            } else {
                psdIsVisiable2 = false;
                StringUtils.psdIsVisiable(et_input_psd2, psdIsVisiable2);
                iv_register_visible_psd2.setImageResource(R.drawable.user_pwd_off);
            }
        } else if (v.equals(tv_finish)) {
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
            registerSecondPresenter.finishRegister(et_input_psd.getText().toString(), et_input_psd2.getText().toString(), registerFlag);
        } else if (v.equals(rl_big_back)) {
            if (null != registerCallBack) {
                registerCallBack.RegisterClose();
            }
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void finishActivity() {
        EventBus.getDefault().post(new FinishActivityEvent());
//        this.finish();
        if (null != registerCallBack) {
            registerCallBack.RegisterClose();
        }
    }

    @Override
    public boolean checkPsd(String et_psd_first, String et_psd_second) {
//        if (!StringUtils.checkPsdLength(et_psd_first)) {//首次输入的密码长度不合要求
        if (!StringUtils.checkPassword(et_psd_first)) {//首次输入的密码长度不合要求
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
    public String getInviterMobile() {
        return et_input_inviter.getText().toString();
    }

    @Override
    public int getAddPsd() {
        return addPsd;
    }

    @Override
    public void setAlias(String alias) {
        JPushInterface.setAlias(getContext(), alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
    }

    RegisterSetPasswordCallBack registerCallBack;

    public void setRegisterSetPasswordCallBack(RegisterSetPasswordCallBack registerCallBack) {
        this.registerCallBack = registerCallBack;
    }

    public interface RegisterSetPasswordCallBack {
        void RegisterClose();
    }

}
