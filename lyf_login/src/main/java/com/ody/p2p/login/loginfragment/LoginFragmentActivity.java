package com.ody.p2p.login.loginfragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.login.Bean.LayRegusterBean;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.bindphone.BindPhoneFragment;
import com.ody.p2p.login.loginfragment.forgertpasd.ForgetPsdFragment;
import com.ody.p2p.login.loginfragment.forgertpasd.ForgetPsdSecondFragment;
import com.ody.p2p.login.loginfragment.registeruser.RegisterFirstFragment;
import com.ody.p2p.login.loginfragment.registeruser.RegisterSetPasswordFragment;
import com.ody.p2p.login.loginfragment.smslogin.SmsLoginFragment;
import com.ody.p2p.login.loginfragment.smslogin.SmsLoginSecondFragment;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by pzy on 2016/10/18.
 */
public class LoginFragmentActivity extends BaseActivity implements LoginFragmentView {
    protected int type;// 来伊份定义的发送验证码类型。。。1.注册  2.忘记密码 3.短信登陆 4.修改密码 7.绑定手机
    protected RelativeLayout rl_show_more;
    protected int isTp;

    public final static int REGISTER = 1; //// 1.注册
    public final static int FORGET_PASSWORD = 5;//2.忘记密码
    public final static int SMS_LOGIN = 3;//3.短信登陆
    public final static int BIND_PHONE = 7;//4.第三方登录绑定手机

    protected LoginFragmentPressenter mPressenter;
    FrameLayout loginCenter;
    View headView;
    protected TextView tv_right_text;
    protected RelativeLayout rl_big_back;
    protected TextView tv_titleName;

    protected Fragment newFragment;
    protected Fragment oldFragment;

    protected RegisterFirstFragment registerFirstFragment;
    protected RegisterSetPasswordFragment registerSetPasswordFragment;

    protected ForgetPsdFragment forgetPsdFragment;
    protected ForgetPsdSecondFragment forgetPsdSecondFragment;

    protected SmsLoginFragment smsLoginFragment;
    protected SmsLoginSecondFragment smsLoginSecondFragment;

    protected BindPhoneFragment bindPhoneFragment;

    @Override
    public void initPresenter() {
        mPressenter = new LoginFragmentPressenterImpr(this);
        type = getIntent().getIntExtra("loginType", SMS_LOGIN);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        headView = view.findViewById(R.id.head);
        rl_show_more = (RelativeLayout) view.findViewById(R.id.rl_show_more);
        tv_right_text = (TextView) headView.findViewById(R.id.tv_right_text);
        rl_big_back = (RelativeLayout) headView.findViewById(R.id.rl_big_back);
        tv_titleName = (TextView) headView.findViewById(R.id.tv_name);

        loginCenter = (FrameLayout) view.findViewById(R.id.loginCenter);

        registerFirstFragment = new RegisterFirstFragment();
        registerSetPasswordFragment = new RegisterSetPasswordFragment();
        forgetPsdFragment = new ForgetPsdFragment();
        forgetPsdSecondFragment = new ForgetPsdSecondFragment();
        smsLoginFragment = new SmsLoginFragment();
        smsLoginSecondFragment = new SmsLoginSecondFragment();
        bindPhoneFragment = new BindPhoneFragment();

        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        if (type == FORGET_PASSWORD) {// 2.忘记密码
            tv_titleName.setText(R.string.find_password);

            forgetPsdFragment.setForgetPsdCallBack(new ForgetPsdFragment.ForGetPsdCallBack() {
                @Override
                public void ForgetPsdNext(String mobile, String captchas) {
                    if (!StringUtils.isEmpty(captchas) && captchas.length() >= 4) {
                        forgetPsdSecondFragment.setData(mobile, captchas);
                        registerNext();
                    }
                }

                @Override
                public void close() {
                    finish();
                }

            });

            forgetPsdFragment.setVerificationCodeCallBack(new BaseVerificationCodeFragment.VerificationCodeCallBack() {
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

            forgetPsdSecondFragment.setForGetPsdCallBack(new ForgetPsdSecondFragment.ForGetPsdCallBack() {
                @Override
                public void forgetPsdClose() {
                    finish();
                }
            });

            newFragment = forgetPsdFragment;
            switchFragment(newFragment);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.loginCenter, newFragment).commitAllowingStateLoss();
        } else if (type == SMS_LOGIN) {//3.短信登陆
            tv_titleName.setText(R.string.login);

            smsLoginFragment.setSmsLoginCallBack(new SmsLoginFragment.SmsLoginCallBack() {
                @Override
                public void nextRegister(String mobile, String captchas) {
                    if (smsLoginFragment.isNewUser()) {//新用户快速登陆去设置密码
                        mPressenter.toNext(mobile, captchas);
                    } else {//老用户直接登陆
                        mPressenter.quickLogin(mobile, captchas);
                    }
                }

                @Override
                public void smsClose() {
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
            switchFragment(newFragment);
        } else if (type == BIND_PHONE) {// 4.联合登录绑定手机号
            tv_titleName.setText("绑定手机");
            final String token = getIntent().getStringExtra("token");
            bindPhoneFragment.setBindPhoneCallBack(new BindPhoneFragment.BindPhoneCallBack() {
                @Override
                public void nextRegister(String mobile, String captchas) {
                    mPressenter.bindPhone(mobile, captchas, token);
                }

            });
            bindPhoneFragment.setVerificationCodeCallBack(new BaseVerificationCodeFragment.VerificationCodeCallBack() {
                @Override
                public void checkPhoneIsRegistered(LoginDocument document) {
                    mPressenter.checkPhoneIsRegistered(document);//第三方登录绑定手机 type：7
                }

                @Override
                public void checkImgVerificationAvailable(LoginDocument document) {
                    mPressenter.checkImgVerificationAvailable(document);
                }

                @Override
                public void sendSmsVerificationCode(LoginDocument document) {
                    mPressenter.getValidateCode(document);//第三方登录绑定手机 type：7
                }
            });
            newFragment = bindPhoneFragment;
            switchFragment(newFragment);
        } else {//1.注册
            tv_titleName.setText(getString(R.string.register));

            registerFirstFragment.setRegisterCallBack(new RegisterFirstFragment.RegisterCallBack() {
                @Override
                public void nextRegister(String mobile, String captchas) {
                    mPressenter.Register(mobile, captchas);
                }

                @Override
                public void close() {
                    finish();
                }
            });

            registerFirstFragment.setVerificationCodeCallBack(new BaseVerificationCodeFragment.VerificationCodeCallBack() {
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

            registerSetPasswordFragment.setRegisterSetPasswordCallBack(new RegisterSetPasswordFragment.RegisterSetPasswordCallBack() {
                @Override
                public void RegisterClose() {
                    EventMessage msg = new EventMessage();
                    msg.flag = EventMessage.FIRST_LOGIN;
                    EventBus.getDefault().post(msg);
                    finish();


                }
            });

            newFragment = registerFirstFragment;
            switchFragment(newFragment);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onTelephoneAlreadyRegistered(LoginDocument document) {//手机号已注册
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).onTelephoneAlreadyRegistered(document);
        }
    }

    @Override
    public void onTelephoneUnregistered(LoginDocument document) {//手机号未注册
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).onTelephoneUnregistered(document);
        }
    }

    @Override
    public void loginBack(LayRegusterBean bean) {

    }

    @Override
    public void sendVerificationCodeSuccessful(LoginDocument document) {//发送成功
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).sendVerificationCodeSuccessful(document);
        }
    }


    @Override
    public void sendVerificationCodeFailed(LoginDocument document) {//发送验证码失败
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).sendVerificationCodeFailed(document);
        }
    }

    /**
     * 切换fragment
     *
     * @param from
     * @param to
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (!bActive) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            fragmentManager.beginTransaction().hide(from).add(R.id.loginCenter, to).commitAllowingStateLoss();
        } else {
            // 隐藏当前的fragment，显示下一个
            fragmentManager.beginTransaction().hide(from).show(to).commitAllowingStateLoss();
        }
    }

    /**
     * 切换fragment,防止用户快速双击调用导致每个fragment没有独自生成
     *
     * @param current
     */
    public void switchFragment(Fragment current) {
        if (!bActive) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!current.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.loginCenter, current).commitAllowingStateLoss();
        }
    }

    @Override
    public void registerNext() {
        if (type == REGISTER || type == SMS_LOGIN || type == BIND_PHONE) {
            if (registerFirstFragment != null) {
                if (type == REGISTER) {

                    registerSuccess();
                }

            }
            tv_titleName.setText(getString(R.string.set_password));
            oldFragment = newFragment;
            switchFragment(oldFragment, registerSetPasswordFragment);
            newFragment = registerFirstFragment;
        } else if (type == FORGET_PASSWORD) {
            tv_titleName.setText(getString(R.string.find_password));
            oldFragment = newFragment;
            switchFragment(oldFragment, forgetPsdSecondFragment);
            newFragment = forgetPsdSecondFragment;
        } else {
            //登录成功
            loginSuccess();
//           finish();
        }
    }

    @Override
    public void loginSuccess() {
        if (isTp == 1) {
            mPressenter.isNewUser();
        } else {
            JumpUtils.ToActivity(JumpUtils.MAIN);
        }
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void isNewUser(int flag) {
        if (flag == 1) {
            EventMessage message = new EventMessage();
            message.flag = EventMessage.IS_SP;
            EventBus.getDefault().post(message);
            JumpUtils.ToActivity(JumpUtils.MAIN);
        } else {
            CustomDialog dialog = new CustomDialog(getContext(), "这是新用户专享活动，您是老用户不能享受新用户优惠，谢谢关注~", 6);
            dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                @Override
                public void Confirm() {
                    JumpUtils.ToActivity(JumpUtils.MAIN);
                }
            });
            dialog.show();
        }
    }

    @Override
    public void needCheckImgVerificationCode(LoginDocument document) {
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).needCheckImgVerificationCode(document);
        }
    }

    @Override
    public void notNeedCheckImgVerificationCode(LoginDocument document) {
        if (newFragment != null && newFragment instanceof BaseVerificationCodeFragment) {
            ((BaseVerificationCodeFragment) newFragment).notNeedCheckImgVerificationCode(document);
        }
    }
}