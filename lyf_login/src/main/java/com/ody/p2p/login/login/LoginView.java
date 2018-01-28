package com.ody.p2p.login.login;

import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.login.Bean.ThirdLoginLogoBean;

import java.util.List;

/**
 * Created by ody on 2016/6/6.
 */
public interface LoginView extends BaseView {

    void setThirdLoginLogo(List<ThirdLoginLogoBean.LogoData.Logos> logos);

    //检查手机号是否为空,是否合法
    boolean checkPhone(String mobile);

    //检查密码是否为空
    boolean checkPsd(String psd);

    void finishActivity();

    //设置这个来区分不同项目跳转到哪个界面,比如p2p项目,德升项目...
    int getFlag();

    void setAlias(String alias);

    void loginResult(LoginBean response);

    void getImageCheck(String url);

    void toBindThirdPlatform(String token);

    Context getClassContext();

    void loginSuccess();

    void isNewUser(int flag);
}
