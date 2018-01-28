package com.ody.p2p.check.giftcard;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;


public class BindGiftCardActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_bind_card;
    private ImageView iv_back;
    private EditText et_cardnum;
    private EditText et_cardpsw;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_bind_gift_card;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_cardnum= (EditText) findViewById(R.id.et_cardnum);
        et_cardpsw= (EditText) findViewById(R.id.et_cardpsw);
        btn_bind_card= (Button) findViewById(R.id.btn_bind_card);
        btn_bind_card.setBackgroundResource(RUtils.getDrawableRes(this,RUtils.THEME_BUTTON));
        btn_bind_card.setTextColor(RUtils.getColorRes(this,RUtils.THEME_BTN_TEXTCOLOR));
        iv_back.setOnClickListener(this);
        btn_bind_card.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }else if(v.getId()==R.id.btn_bind_card){
            submit();
        }
    }

    private void submit(){
        if(TextUtils.isEmpty(et_cardnum.getText().toString())){
            ToastUtils.showToast(getString(R.string.into_gift_card));
            return;
        }
        if(TextUtils.isEmpty(et_cardpsw.getText().toString())){
            ToastUtils.showToast(getString(R.string.into_gift_password));
            return;
        }
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("cardCode",et_cardnum.getText().toString());
        params.put("cardPasswd",et_cardpsw.getText().toString());
        OkHttpManager.getAsyn(Constants.BIND_GIFTCARD,params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    finish();
                }else{
                    if(!TextUtils.isEmpty(response.message)){
                        ToastUtils.showToast(response.message);
                    }
                }
            }

        });
    }
}
