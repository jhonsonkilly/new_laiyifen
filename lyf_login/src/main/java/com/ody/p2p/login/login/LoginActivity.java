package com.ody.p2p.login.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.eventbus.FinishActivityEvent;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.login.Bean.ThirdLoginLogoBean;
import com.ody.p2p.login.R;
import com.ody.p2p.login.loginfragment.LoginFragmentActivity;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.qiyukf.unicorn.api.Unicorn;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.campusapp.router.annotation.RouterMap;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

@RouterMap("activity://login")
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView, PlatformActionListener {
    protected boolean CanLogin = true;
    protected TextView tv_name, tv_third_logos_title;
    protected TextView tv_right_text;
    protected EditText et_input_phone;
    protected RelativeLayout rl_cha;//输入账号时的叉
    protected EditText et_input_psd;
    protected RelativeLayout rl_visiable_psd;//控制密码可见性的眼睛
    protected RelativeLayout rl_cha_psd;//输入密码的叉
    protected EditText et_input_validate_code;
    protected RelativeLayout rl_validate_code;//验证码图片
    protected RelativeLayout rl_cha_validate_code;//输入验证码的叉
    protected TextView tv_sms_login;
    protected TextView tv_forget_psd;
    protected RelativeLayout rl_big_back;
    protected TextView tv_login;
    protected ImageView iv_visible_psd;
    protected ImageView iv_back, iv_qq, iv_wechat, iv_sina_weibo, iv_zhifubao;
    protected LinearLayout ll_third_logos_title, ll_third_logos, ll_qq, ll_wechat, ll_sina_weibo, ll_zhifubao, thirdLogin;//合作账号登录的title,第三方登录的四个图标
    String TAP = "";
    protected LoginPresenter mPresenter;

    private int isTp;

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenterImpl(this);
        mPresenter.getThirdLogo();
        TAP = getIntent().getStringExtra(Constants.LOSINGTAP);

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        ShareSDK.initSDK(this);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
        et_input_phone = (EditText) view.findViewById(R.id.et_input_phone);
        rl_cha = (RelativeLayout) view.findViewById(R.id.rl_cha);
        et_input_psd = (EditText) view.findViewById(R.id.et_input_psd);
        thirdLogin = (LinearLayout) findViewById(R.id.third_login);
        rl_visiable_psd = (RelativeLayout) view.findViewById(R.id.rl_visiable_psd);
        rl_cha_psd = (RelativeLayout) view.findViewById(R.id.rl_cha_psd);
        et_input_validate_code = (EditText) view.findViewById(R.id.et_input_validate_code);
        rl_validate_code = (RelativeLayout) view.findViewById(R.id.rl_validate_code);
        rl_cha_validate_code = (RelativeLayout) view.findViewById(R.id.rl_cha_validate_code);
        tv_sms_login = (TextView) view.findViewById(R.id.tv_sms_login);
        tv_forget_psd = (TextView) view.findViewById(R.id.tv_forget_psd);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        iv_visible_psd = (ImageView) view.findViewById(R.id.iv_visible_psd);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
        iv_wechat = (ImageView) view.findViewById(R.id.iv_wechat);
        iv_sina_weibo = (ImageView) view.findViewById(R.id.iv_sina_weibo);
        iv_zhifubao = (ImageView) view.findViewById(R.id.iv_zhifubao);
        tv_third_logos_title = (TextView) view.findViewById(R.id.tv_third_logos_title);

        ll_third_logos_title = (LinearLayout) view.findViewById(R.id.ll_third_logos_title);
        ll_third_logos = (LinearLayout) view.findViewById(R.id.ll_third_logos);
        ll_qq = (LinearLayout) view.findViewById(R.id.ll_qq);
        ll_wechat = (LinearLayout) view.findViewById(R.id.ll_wechat);
        ll_sina_weibo = (LinearLayout) view.findViewById(R.id.ll_sina_weibo);
        ll_zhifubao = (LinearLayout) view.findViewById(R.id.ll_zhifubao);

        EventBus.getDefault().register(this);
        tv_name.setText(R.string.login);
        tv_right_text.setText(R.string.register);

        rl_cha.setOnClickListener(this);
        rl_cha_psd.setOnClickListener(this);
        rl_cha_validate_code.setOnClickListener(this);
        rl_visiable_psd.setOnClickListener(this);
        tv_right_text.setOnClickListener(this);
        rl_big_back.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_sms_login.setOnClickListener(this);
        tv_forget_psd.setOnClickListener(this);

        ll_qq.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_sina_weibo.setOnClickListener(this);
        ll_zhifubao.setOnClickListener(this);
        StringUtils.operateCha(et_input_phone, rl_cha);
        StringUtils.operateCha(et_input_psd, rl_cha_psd);
        StringUtils.operateCha(et_input_validate_code, rl_cha_validate_code);

        //限制密码的输入符号,起码不能有中文
        StringUtils.limitInputType(et_input_psd);

    }

    @Subscribe
    public void onEventMainThread(FinishActivityEvent event) {
        finish();
    }

    @Override
    public void doBusiness(Context mContext) {
        isTp = getIntent().getIntExtra("isTp", -1);
        if (OdyApplication.SCHEME.equals("ds")) {
            thirdLogin.setVisibility(View.VISIBLE);
        } else {
            thirdLogin.setVisibility(View.GONE);
        }
        et_input_phone.setText(OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, ""));
        et_input_phone.setSelection(et_input_phone.getText().toString().length());
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        ShareSDK.stopSDK();
    }

    private boolean psdIsVisiable = false;
    protected Platform qq;

    @Override
    public void onClick(View v) {
        if (v.equals(rl_cha)) {
            et_input_phone.setText("");
        } else if (v.equals(rl_cha_psd)) {
            et_input_psd.setText("");
        } else if (v.equals(rl_cha_validate_code)) {
            et_input_validate_code.setText("");
        } else if (v.equals(rl_visiable_psd)) {
            if (!psdIsVisiable) {
                psdIsVisiable = true;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_visible_psd.setImageResource(R.drawable.user_pwd_on);
            } else {
                psdIsVisiable = false;
                StringUtils.psdIsVisiable(et_input_psd, psdIsVisiable);
                iv_visible_psd.setImageResource(R.drawable.user_pwd_off);
            }
        } else if (v.equals(tv_right_text)) {
            if (flag == 1) {//德升 注册界面1
                JumpUtils.ToActivity(JumpUtils.DSREGISTERFIRST);
            } else {
//                toActivity(RegisterFirstActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("loginType", LoginFragmentActivity.REGISTER);
                JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);
            }
        } else if (v.equals(rl_big_back)) {
            finish();
        } else if (v.equals(tv_login)) {
            if (et_input_phone == null) {
                return;
            }
            if (et_input_phone.getText() == null) {
                return;
            }
            if (et_input_psd == null) {
                return;
            }
            if (et_input_psd.getText() == null) {
                return;
            }
            if (CanLogin) {
                mPresenter.checkPhoneIsRegistered(et_input_phone.getText().toString(), et_input_psd.getText().toString());
            }
        } else if (v.equals(tv_sms_login)) {
//            toActivity(SmsLoginActivity.class);
            Bundle bd = new Bundle();
            bd.putInt("loginType", LoginFragmentActivity.SMS_LOGIN);
            JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);
        } else if (v.equals(tv_forget_psd)) {
            if (flag == 1) {//德升 忘记密码界面1
//                ActivityRoute route = (ActivityRoute) Router.getRoute("activity://dsforgetpsdfirst");
//                route.open();

                JumpUtils.ToActivity(JumpUtils.DSFORGETPSDFIRST);
            } else {
//                toActivity(ForgetPsdActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("loginType", LoginFragmentActivity.FORGET_PASSWORD);
                JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);
            }
        } else if (v.equals(ll_qq)) {//qq登录
            qq = ShareSDK.getPlatform(QQ.NAME);
            qq.SSOSetting(false); // 设置false表示使用SSO授权方式
            qq.setPlatformActionListener(this);
            if (qq.isAuthValid()) {
                qq.removeAccount(true);
            }
            if (qq.isClientValid()) {
                qq.showUser(null);// 获取到用户信息
            } else {
                ToastUtils.showShort(getString(R.string.not_to_install_qq));
            }
        } else if (v.equals(ll_wechat)) {//微信登录

            Platform wx = ShareSDK.getPlatform(Wechat.NAME);
            wx.SSOSetting(false); // 设置false表示使用SSO授权方式
            wx.setPlatformActionListener(this);
            if (wx.isAuthValid()) {
                wx.removeAccount(true);
            }
            if (wx.isClientValid()) {
                wx.showUser(null);// 获取到用户信息
            } else {
                ToastUtils.showShort(getString(R.string.not_to_install_wechat));
            }
        } else if (v.equals(ll_sina_weibo)) {//新浪微博登录
            Platform sinaWb = ShareSDK.getPlatform(SinaWeibo.NAME);
            sinaWb.SSOSetting(false);
            sinaWb.setPlatformActionListener(this);
            if (sinaWb.isAuthValid()) {
                sinaWb.removeAccount(true);
            }
            if (sinaWb.isClientValid()) {
                sinaWb.showUser(null);
            } else {
                ToastUtils.showShort(getString(R.string.not_to_install_sinawb));
            }
        } else if (v.equals(ll_zhifubao)) {//支付宝登录
            ToastUtils.showShort(getString(R.string.Pay_treasure_to_login));
        }
    }

    public int flag;

    //flag默认是p2p项目  1为德升项目
    public void setFlag(int flag) {
        this.flag = flag;
    }

    //
    @Override
    public int getFlag() {
        return flag;
    }

    @Override
    public void setAlias(String alias) {
        JPushInterface.setAlias(this, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
    }

    @Override
    public Context getClassContext() {
        return getContext();
    }

    @Override
    public void loginSuccess() {

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

    public void toActivity(Class clazz) {
        mBaseOperation.forward(clazz);
    }

    @Override
    public void setThirdLoginLogo(List<ThirdLoginLogoBean.LogoData.Logos> logos) {
        //目前在xml布局里写死的是四个logo位置...
        GlideUtil.display(this, logos.get(0).logoPic).into(iv_qq);
        GlideUtil.display(this, logos.get(1).logoPic).into(iv_wechat);
        GlideUtil.display(this, logos.get(2).logoPic).into(iv_sina_weibo);
        GlideUtil.display(this, logos.get(3).logoPic).into(iv_zhifubao);
    }

    @Override
    public boolean checkPhone(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            ToastUtils.showShort(getString(R.string.phone_number_can_t_empty));
            et_input_phone.requestFocus();
            return false;
        }
        boolean isMobile = StringUtils.checkMobile(mobile);
        if (!isMobile) {
            ToastUtils.showShort(getString(R.string.phone_number_wrong_please_chcked));
            et_input_phone.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPsd(String psd) {
        if (psd.length() < 6) {
            ToastUtils.showShort(getString(R.string.password_lenght_6for18));
            et_input_psd.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void finishActivity() {
        if (!"".equals(TAP)) {
            if (isTp == 1) {
                mPresenter.isNewUser();
            } else {
                this.finish();
            }
        } else {
            if (isTp == 1) {
                mPresenter.isNewUser();
            } else {
                JumpUtils.ToActivity(JumpUtils.MAIN);
                this.finish();
            }
        }
    }

    /**
     * 第三方登录的回调
     *
     * @param platform
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String token = platform.getDb().getToken();
        String openId = platform.getDb().getUserId();
        String platformNname = platform.getDb().getPlatformNname();
        String unionid = (String) hashMap.get("unionid");
        if ("QQ".equals(platformNname)) {
            mPresenter.unionLogin("1150085386", openId, unionid, token, 1);
        } else if ("Wechat".equals(platformNname)) {
            mPresenter.unionLogin("wxfac2b15cf7763717", openId, unionid, token, 2);
        }
        hideLoading();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("test", "onError+++" + throwable.toString());
        hideLoading();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort(getString(R.string.cancel_login));
        hideLoading();
    }

    @Override
    public void toBindThirdPlatform(String token) {
        Bundle bd = new Bundle();
        bd.putString("token", token);
        bd.putInt("loginType", LoginFragmentActivity.BIND_PHONE);
        JumpUtils.ToActivity(JumpUtils.FFRAGMENT_LOGIN, bd);
    }

    @Override
    public void loginResult(LoginBean response) {
        if (Integer.valueOf(response.code) == 0) {
            OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
            OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
            OdyApplication.putValueByKey(Constants.LOGIN_USER_ID, response.data.id);
            OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, et_input_phone.getText().toString());
            String info = OdyApplication.getValueByKey("history", null);
            if (info != null && info.length() > 0) {
                mPresenter.synHistory(info);
            }
            mPresenter.bindGuid();
            EventMessage message = new EventMessage();
            message.flag = EventMessage.REFRESH_UT;
            EventBus.getDefault().post(message);


        }
    }

    @Override
    public void getImageCheck(String url) {

    }
}
