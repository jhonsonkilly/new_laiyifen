package com.ody.p2p.main.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑定伊点卡 页面
 * Created by caishengya on 2017/4/19.
 */

public class AddYCardPopupWindow extends PopupWindow implements View.OnClickListener {

    private View view;
    public EditText yCardNumber;
    public EditText yCardPassWord;

    private AddSuccessListener addSuccessListener;
    private TextWatcher textWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = s.length();
            if (length > 1 && length % 5 == 0) {
                if (!s.toString().endsWith(" ")) {
                    StringBuilder stringBuilder = new StringBuilder(s.toString());
                    StringBuilder insert1 = stringBuilder.insert(s.toString().length() - 1, " ");
                    yCardNumber.setText(insert1.toString());
                    yCardNumber.setSelection(insert1.length());
                }
            }
        }
    };

    public void setAddSuccessListener(AddSuccessListener addSuccessListener) {
        this.addSuccessListener = addSuccessListener;
    }

    public AddYCardPopupWindow(final Context context, String orderId, String orderType) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_add_ycard, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //卡号
        yCardNumber = (EditText) view.findViewById(R.id.popupwindow_add_ycard_number);
        yCardNumber.addTextChangedListener(textWatch);
        //密码
        yCardPassWord = (EditText) view.findViewById(R.id.popupwindow_add_ycard_password);
        TextView popupwindow_add_ycard_add = (TextView) view.findViewById(R.id.popupwindow_add_ycard_add);
        popupwindow_add_ycard_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindECard(context);
            }
        });
        ImageView mImageViewClose = (ImageView) view.findViewById(R.id.popupwindow_add_ycard_close);
        mImageViewClose.setOnClickListener(this);

        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
    }

    /**
     * 添加伊点卡
     */
    private void bindECard(final Context context) {
        String number = yCardNumber.getText().toString().trim();
        number = number.replace(" ", "");
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(context, "请输入卡号", Toast.LENGTH_SHORT).show();
            yCardNumber.requestFocus();
            return;
        }
        String pwd = yCardPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            yCardPassWord.requestFocus();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("cardCode", number);
        params.put("cardPwd", pwd);
        OkHttpManager.postAsyn(Constants.BindECard, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismiss();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
//                    if (TextUtils.isEmpty(response.message)) {
                    if ("0".equals(response.code)) {
                        response.message = "绑定成功";
                    } else {
                        response.message = "绑定失败";
                    }
//                    }
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show();
                    if ("0".equals(response.code) && addSuccessListener != null) {
                        addSuccessListener.addSuccess();
                    }
                } else {
                    Toast.makeText(context, "绑定失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, params);
    }

    public interface AddSuccessListener {
        public void addSuccess();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.popupwindow_add_ycard_close) {
            this.dismiss();
        }
    }
}
