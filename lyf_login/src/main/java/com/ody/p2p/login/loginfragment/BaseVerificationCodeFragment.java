package com.ody.p2p.login.loginfragment;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.login.Bean.LoginDocument;
import com.ody.p2p.login.R;
import com.ody.p2p.utils.ToastUtils;

/**
 * Created by hanzhifengyun on 2017/7/25.
 * 关于验证码逻辑的封装类
 */

public abstract class BaseVerificationCodeFragment extends BaseFragment {

    protected ImgVerificationCodeDialogFragment mImgVerificationCodeDialogFragment;

    protected void checkImgVerificationAvailable(LoginDocument document) {
        if (mVerificationCodeCallBack != null) {
            mVerificationCodeCallBack.checkImgVerificationAvailable(document);
        }
    }

    protected void onBtnGetVerificationCodeClick() {
        LoginDocument document = getLoginDocument();
        if (document.isTelephoneEmpty()) {
            ToastUtils.showShort(getString(R.string.input_phone));
            return;
        }
        if (mVerificationCodeCallBack != null) {
            mVerificationCodeCallBack.checkPhoneIsRegistered(document);
        }
    }

    protected LoginDocument getLoginDocument() {
        LoginDocument document = new LoginDocument();
        document.setTelephone(getTelephone())
                .setCaptchasType(getCurrentType())
                .setVerificationCode(getVerificationCode());
        return document;
    }

    /**
     * @return 短信验证码类型
     */
    protected abstract int getCurrentType();

    /**
     * @return 验证码
     */
    protected abstract String getVerificationCode();

    /**
     * @return 手机号
     */
    protected abstract String getTelephone();

    /**
     * @return 倒计时TextView
     */
    protected abstract TextView getCaptchaText();

    private void showImgVerificationDialog(final LoginDocument document) {
        if (mImgVerificationCodeDialogFragment == null) {
            mImgVerificationCodeDialogFragment = new ImgVerificationCodeDialogFragment();
        }
        mImgVerificationCodeDialogFragment
                .setOnImgVerificationCodClickListener(new ImgVerificationCodeDialogFragment.OnImgVerificationCodClickListener() {
                    @Override
                    public void onClick() {
                        checkImgVerificationAvailable(document);
                    }
                })
                .setOnBtnConfirmClickListener(new ImgVerificationCodeDialogFragment.OnBtnConfirmClickListener() {
                    @Override
                    public void onClick(String code) {
                        document.setImgVerificationCode(code);
                        sendSmsVerificationCode(document);
                    }
                }).setCancelable(false);
        mImgVerificationCodeDialogFragment.setImgVerification(document.getImgVerificationCodeBytes());
        if (!mImgVerificationCodeDialogFragment.isAdded()) {
            mImgVerificationCodeDialogFragment.show(getFragmentManager(), "ImgVerificationCodeDialogFragment");
        }
    }

    private void sendSmsVerificationCode(LoginDocument document) {
        if (mVerificationCodeCallBack != null) {
            mVerificationCodeCallBack.sendSmsVerificationCode(document);
        }
    }


    public void sendVerificationCodeFailed(LoginDocument document) {
        if (document.isNeedImgVerificationCode()) {
            //重置图形验证码
            checkImgVerificationAvailable(document);
            showImgVerificationCodeDialogErrorMessage(document.getMessage());
        } else {
            ToastUtils.showShort(document.getMessage());
        }
    }

    private void showImgVerificationCodeDialogErrorMessage(String errorMessage) {
        if (mImgVerificationCodeDialogFragment != null && mImgVerificationCodeDialogFragment.isAdded()) {
            mImgVerificationCodeDialogFragment.setErrorMessage(errorMessage);
        }
    }

    public void sendVerificationCodeSuccessful(LoginDocument document) {
        ToastUtils.showShort(document.getMessage());
        if (document.isNeedImgVerificationCode()) {
            dismissImgVerificationCodeDialog();
        }
        //开始倒计时
        doCountDownTimeWork(getCaptchaText());
    }


    private void dismissImgVerificationCodeDialog() {
        if (mImgVerificationCodeDialogFragment != null && mImgVerificationCodeDialogFragment.isAdded()) {
            try {
                mImgVerificationCodeDialogFragment.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected VerificationCodeCallBack mVerificationCodeCallBack;

    public void setVerificationCodeCallBack(VerificationCodeCallBack verificationCodeCallBack) {
        mVerificationCodeCallBack = verificationCodeCallBack;
    }

    public void onTelephoneAlreadyRegistered(LoginDocument document) {
        checkImgVerificationAvailable(document);
    }

    public void onTelephoneUnregistered(LoginDocument document) {
        checkImgVerificationAvailable(document);
    }

    public void needCheckImgVerificationCode(LoginDocument document) {
        showImgVerificationDialog(document);
    }

    public void notNeedCheckImgVerificationCode(LoginDocument document) {
        sendSmsVerificationCode(document);
    }

    public interface VerificationCodeCallBack {
        void checkPhoneIsRegistered(LoginDocument document);

        void checkImgVerificationAvailable(LoginDocument document);

        void sendSmsVerificationCode(LoginDocument document);
    }


    private CountDownTimer mCountDownTimer;

    public void doCountDownTimeWork(final TextView tvView) {
        if (tvView == null) {
            return;
        }
        if (mCountDownTimer != null) {
            return;
        }
        tvView.setClickable(false);
        mCountDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvView.setText(R.string.get_verification_code);
                tvView.setClickable(true);
                mCountDownTimer = null;
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
