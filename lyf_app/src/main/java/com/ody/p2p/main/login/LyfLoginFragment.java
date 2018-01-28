package com.ody.p2p.main.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.loginfragment.BaseVerificationCodeFragment;
import com.ody.p2p.login.loginfragment.LoginFragmentActivity;
import com.ody.p2p.login.loginfragment.registeruser.RegisterFirstFragment;
import com.ody.p2p.login.loginfragment.registeruser.RegisterSetPasswordFragment;
import com.ody.p2p.login.loginfragment.smslogin.SmsLoginFragment;
import com.ody.p2p.login.loginfragment.smslogin.SmsLoginSecondFragment;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.login.loginfragment.LyfBindPhoneFragment;
import com.ody.p2p.main.login.loginfragment.LyfForgetPsdFragment;
import com.ody.p2p.main.login.loginfragment.LyfForgetPsdSecondFragment;
import com.ody.p2p.main.login.loginfragment.LyfRegisterFirstFragment;
import com.ody.p2p.main.login.loginfragment.LyfSetPasswordFragment;
import com.ody.p2p.main.login.loginfragment.LyfSmsLoginFragment;
import com.ody.p2p.main.login.loginfragment.LyfSmsLoginSecondFragment;
import com.ody.p2p.utils.JumpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzy on 2016/12/2.
 */
public class LyfLoginFragment extends LoginFragmentActivity {
    private int registerFlag = -1;

    @Override
    public void initView(View view) {
        super.initView(view);

        registerFirstFragment = new LyfRegisterFirstFragment();
        registerSetPasswordFragment = new LyfSetPasswordFragment();

        forgetPsdFragment = new LyfForgetPsdFragment();
        forgetPsdSecondFragment = new LyfForgetPsdSecondFragment();
//
        smsLoginFragment = new LyfSmsLoginFragment();
        smsLoginSecondFragment = new LyfSmsLoginSecondFragment();

        bindPhoneFragment = new LyfBindPhoneFragment();

    }


    @Override
    public void doBusiness(Context mContext) {
        isTp = getIntent().getIntExtra("isTp", -1);
        registerFlag = getIntent().getIntExtra("registerCoupon", 1);
        if (type == SMS_LOGIN) {//快速登录
            boolean isBind = getIntent().getBooleanExtra("bind_phone", false);//第三方登录绑定手机
            tv_titleName.setText(R.string.login);
            if (!isBind) {
                rl_show_more.setVisibility(View.VISIBLE);
                tv_right_text.setText(R.string.register);
                tv_right_text.setTextColor(getResources().getColor(R.color.theme_color));
                tv_right_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getString(R.string.jump_over).equals(tv_right_text.getText().toString())) {  //跳过 按钮
                            if (registerFlag == 1) {
                                EventMessage msg = new EventMessage();
                                msg.flag = EventMessage.FIRST_LOGIN;
                                EventBus.getDefault().post(msg);
                            }
                            JumpUtils.ToActivity(JumpUtils.MAIN);
                        } else {

                          /*  newFragment = registerFirstFragment;
                            switchFragment(newFragment);*/

                            /*Bundle bd = new Bundle();
                            bd.putInt("loginType", LoginFragmentActivity.REGISTER);
                            JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);*/


                            /*Bundle bd = new Bundle();
                            bd.putInt("loginType", LoginFragmentActivity.REGISTER);
                            JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);*/
                            //finish();
                            tv_right_text.setText("");
                            tv_titleName.setText(getString(com.ody.p2p.login.R.string.register));
                            type = 1;
                            doBusiness(getContext());

                        }
                    }
                });
            }
            smsLoginFragment.setSmsLoginCallBack(new SmsLoginFragment.SmsLoginCallBack() {
                @Override
                public void nextRegister(String mobile, String captchas) {
//                    if (!smsLoginFragment.isNewUser){//新用户快速登陆去设置密码
//                        mPressenter.toNext(mobile,captchas);
//                    }else{//老用户直接登陆
                    mPressenter.quickLogin(mobile, captchas);
//                    }
                }

                @Override
                public void smsClose() {
                    Bundle bundle = new Bundle();
                    bundle.putInt("isTp", isTp);
                    JumpUtils.ToActivity(JumpUtils.SMSLOGIN, bundle);
                    finish();
                }
            });

            smsLoginFragment.setVerificationCodeCallBack(new BaseVerificationCodeFragment.VerificationCodeCallBack() {
                @Override
                public void checkPhoneIsRegistered(LoginDocument document) {
                    mPressenter.checkPhoneIsRegistered(document);
                }

                @Override
                public void checkImgVerificationAvailable(LoginDocument document) {
                    mPressenter.checkImgVerificationAvailable(document);
                }

                @Override
                public void sendSmsVerificationCode(LoginDocument document) {
                    mPressenter.getValidateCode(document);
                }
            });
            smsLoginSecondFragment.setSmsLoginCallBack(new SmsLoginSecondFragment.SmsLoginCallBack() {
                @Override
                public void smsLoginClose() {
                    finish();
                }
            });
            newFragment = smsLoginFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(com.ody.p2p.login.R.id.loginCenter, newFragment).commitAllowingStateLoss();
        } else {
            super.doBusiness(mContext);
        }
    }

    @Override
    public void registerNext() {
        super.registerNext();
        if (type != FORGET_PASSWORD) {
            rl_big_back.setVisibility(View.INVISIBLE);
            tv_right_text.setText(R.string.jump_over);
            tv_right_text.setTextColor(getResources().getColor(R.color.theme_color));
            tv_right_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventMessage msg = new EventMessage();
                    msg.flag = EventMessage.FIRST_LOGIN;
                    EventBus.getDefault().post(msg);
                    finish();
                    JumpUtils.ToActivity(JumpUtils.MAIN);

                }
            });
        } else {
            rl_big_back.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void registerSuccess() {
        super.registerSuccess();
        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONREGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("userId", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名"));
        map.put("id", OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));
        msg.setExtra(map);
        EventBus.getDefault().post(msg);


    }


    @Override
    public void loginSuccess() {
        super.loginSuccess();
        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONLOGIN);
        Map<String, String> map = new HashMap<>();
        map.put("userId", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名"));
        map.put("id", OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));
        msg.setExtra(map);
        EventBus.getDefault().post(msg);
    }

}