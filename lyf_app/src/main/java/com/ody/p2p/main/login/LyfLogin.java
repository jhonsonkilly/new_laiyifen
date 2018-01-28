package com.ody.p2p.main.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.login.Bean.LoginBean;
import com.ody.p2p.login.login.LoginActivity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.utils.CodeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by pzy on 2016/12/1.
 */

public class LyfLogin extends LoginActivity {
    EditText ed_login_verification_code;
    RelativeLayout rl_cha_vercode, rl_validate_code_title;
    LinearLayout verification_code;
    ImageView img_getvercode;
    TextView login_for_quike;
    CodeUtils codeUtils;

    @Override
    public int bindLayout() {
        return R.layout.activity_lyf_login;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        CanLogin = false;
        ed_login_verification_code = (EditText) view.findViewById(R.id.ed_login_verification_code);
        verification_code = (LinearLayout) view.findViewById(R.id.verification_code);
        rl_cha_vercode = (RelativeLayout) view.findViewById(R.id.rl_cha_vercode);
        rl_validate_code_title = (RelativeLayout) view.findViewById(R.id.rl_validate_code_title);
        img_getvercode = (ImageView) view.findViewById(R.id.img_getvercode);
        login_for_quike = (TextView) view.findViewById(R.id.login_for_quike);

        img_getvercode.setOnClickListener(this);
        login_for_quike.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
        StringUtils.operateCha(ed_login_verification_code, rl_cha_vercode);
        tv_right_text.setTextColor(getResources().getColor(R.color.theme_color));

        codeUtils = CodeUtils.getInstance();
        codeUtils.setCodeLength(4);
        codeUtils.setImageWidth(200);

//        thirdLogin.setVisibility(View.VISIBLE);

//        img_getvercode.setImageBitmap(codeUtils.createBitmap());//本地随机生成验证码
//        mPresenter.getIgraphicCode();
//        getVerCode();
    }

    /**
     * 获取图形验证码
     */
    public void getVerCode() {
        String url = "http://jingkelong.oudianyun.com/ouser-web/mobileLogin/checkImageForm.do" +
                "?width=110&" +
                "height=50&" +
                "codeNmInSession=vicode&" +
                "codeCount=4&" +
                new Random().nextInt();

        GlideUtil.display(getContext(), url).override(200, 200).into(img_getvercode);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_getvercode:
//                img_getvercode.setImageBitmap(codeUtils.createBitmap());+
//                getVerCode();
                mPresenter.getIgraphicCode();
                ed_login_verification_code.setText("");
                break;
            case R.id.tv_login:
                if (et_input_phone == null || et_input_phone.getText() == null) {
                    return;
                }
                if (et_input_psd == null || et_input_psd.getText() == null) {
                    return;
                }
                if (!checkPhone(et_input_phone.getText().toString())) {
                    return;
                }
                if (checkImage && StringUtils.isEmpty(ed_login_verification_code.getText().toString())) {
                    ToastUtils.failToast("验证码有误");
                    return;
                }
                mPresenter.checkPhoneIsRegistered(et_input_phone.getText().toString(), et_input_psd.getText().toString(), ed_login_verification_code.getText().toString());
                break;
            case R.id.login_for_quike:
                Bundle bd = new Bundle();
                bd.putInt("loginType", 3);
                JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
                break;
        }
    }

    boolean checkImage = false;


    @Override
    public void getImageCheck(final String url) {
        super.getImageCheck(url);
        if (!StringUtils.isEmpty(url)) {
            checkImage = true;
            verification_code.setVisibility(View.VISIBLE);
//            "data:image/jpg;base64,"
//            GlideUtil.display(getContext(),url).into(img_getvercode);
            img_getvercode.setImageBitmap(stringtoBitmap(url));

        }
    }

    /**
     * 获取base64图片
     *
     * @param string
     * @return
     */
    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void loginResult(LoginBean response) {
        super.loginResult(response);
        if (Integer.valueOf(response.code) == 0) {
            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONLOGIN);
            Map<String, String> map = new HashMap<>();
            map.put("userId", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名"));
            map.put("id", OdyApplication.getValueByKey(Constants.LOGIN_USER_ID, ""));

            msg.setExtra(map);
            EventBus.getDefault().post(msg);
        }
    }
}
