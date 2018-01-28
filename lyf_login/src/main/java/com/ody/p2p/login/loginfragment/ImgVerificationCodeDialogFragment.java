package com.ody.p2p.login.loginfragment;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.login.R;
import com.ody.p2p.utils.StringUtils;

/**
 * Created by hanzhifengyun on 2017/7/24.
 * 图形验证码验证Dialog
 */

public class ImgVerificationCodeDialogFragment extends DialogFragment {

    private ImageView img_getvercode;
    private EditText ed_login_verification_code;
    private RelativeLayout rl_cha_vercode;
    private String mCurrentImgVerificationCode;
    private TextView mTvConfirm;
    private TextView mTvCancel;
    private TextView mTvChangeImage;
    private TextView mTvErrorMessage;
    private LinearLayout mLlErrorMessage;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return inflater.inflate(R.layout.dialog_fragment_img_verification, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    private void initEvent() {
        StringUtils.operateCha(ed_login_verification_code, rl_cha_vercode);

        mTvChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImgVerificationCodClickListener != null) {
                    mOnImgVerificationCodClickListener.onClick();
                }
            }
        });

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnConfirmClickListener != null) {
                    String imgCode = getImgCode();
                    if (TextUtils.isEmpty(imgCode)) {
                        setErrorMessage(getString(R.string.hint_img_verification));
                        return;
                    }
                    mOnBtnConfirmClickListener.onClick(imgCode);
                }
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getImgCode() {
        return ed_login_verification_code.getText().toString().trim();
    }

    private void initView(View view) {
        ed_login_verification_code = (EditText) view.findViewById(R.id.ed_login_verification_code);
        img_getvercode = (ImageView) view.findViewById(R.id.img_getvercode);
        rl_cha_vercode = (RelativeLayout) view.findViewById(R.id.rl_cha_vercode);

        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        mTvChangeImage = (TextView) view.findViewById(R.id.tv_change_image);
        mTvChangeImage.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvChangeImage.getPaint().setAntiAlias(true);//抗锯齿


        mTvErrorMessage = (TextView) view.findViewById(R.id.tv_errorMessage);
        mLlErrorMessage = (LinearLayout) view.findViewById(R.id.ll_errorMessage);

        mLlErrorMessage.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(mCurrentImgVerificationCode)) {
            setImgVerification(mCurrentImgVerificationCode);
        }
    }

    /**
     * 图形验证码点击监听
     */
    public interface OnImgVerificationCodClickListener {
        void onClick();
    }

    private OnImgVerificationCodClickListener mOnImgVerificationCodClickListener;

    public ImgVerificationCodeDialogFragment setOnImgVerificationCodClickListener(OnImgVerificationCodClickListener onImgVerificationCodClickListener) {
        mOnImgVerificationCodClickListener = onImgVerificationCodClickListener;
        return this;
    }

    /**
     * 确认按钮点击监听
     */
    public interface OnBtnConfirmClickListener {
        void onClick(String code);
    }

    private OnBtnConfirmClickListener mOnBtnConfirmClickListener;


    public ImgVerificationCodeDialogFragment setOnBtnConfirmClickListener(OnBtnConfirmClickListener onBtnConfirmClickListener) {
        mOnBtnConfirmClickListener = onBtnConfirmClickListener;
        return this;
    }

    public ImgVerificationCodeDialogFragment setImgVerification(String data) {
        mCurrentImgVerificationCode = data;
        if (img_getvercode != null) {
            img_getvercode.setImageBitmap(StringUtils.stringtoBitmap(data));
        }
        clearImgVerificationCode();
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        clearImgVerificationCode();
    }

    public void clearImgVerificationCode() {
        if (ed_login_verification_code != null) {
            ed_login_verification_code.setText("");
        }
    }

    public void setErrorMessage(String errorMessage) {
        if (mLlErrorMessage != null) {
            mLlErrorMessage.setVisibility(View.VISIBLE);
        }
        if (mTvErrorMessage != null) {
            mTvErrorMessage.setText(errorMessage);
        }
    }

}
