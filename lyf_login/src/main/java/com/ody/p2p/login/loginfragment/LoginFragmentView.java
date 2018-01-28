package com.ody.p2p.login.loginfragment;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.login.Bean.LayRegusterBean;
import com.ody.p2p.login.Bean.LoginDocument;

/**
 * Created by pzy on 2016/10/18.
 */
public interface LoginFragmentView extends BaseView {

    void loginBack(LayRegusterBean bean);

    void registerNext();

    void onTelephoneAlreadyRegistered(LoginDocument document);

    void onTelephoneUnregistered(LoginDocument document);

    void sendVerificationCodeSuccessful(LoginDocument document);

    void sendVerificationCodeFailed(LoginDocument document);

    public void loginSuccess();

    public void registerSuccess();

    void isNewUser(int flag);

    void needCheckImgVerificationCode(LoginDocument document);

    void notNeedCheckImgVerificationCode(LoginDocument document);
}
