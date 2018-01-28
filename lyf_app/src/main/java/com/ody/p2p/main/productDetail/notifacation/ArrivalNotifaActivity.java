package com.ody.p2p.main.productDetail.notifacation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.login.Bean.IsRepeatPhoneBean;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;


/**
 * 到货通知
 */
public class ArrivalNotifaActivity extends BaseActivity {
    String CheckApp="check_app_push";//本地保存推送方式
    String CheckMessage="check_message";//本地保存推送方式
    String phoneNumber;//记录电话
    String HintPhoneNumber;//记录电话
    TextView tv_productname;
    EditText ed_mobile;
    CheckBox check_app_push,check_message;
    Button btn_submit;

    ProductBean product;

    @Override
    public void initPresenter() {
        ProductInfoBean productInfoBean=(ProductInfoBean)getIntent().getSerializableExtra("product");
        if (null!=productInfoBean&&null!=productInfoBean.getData()&&productInfoBean.getData().size()>0){
            product=productInfoBean.getData().get(0);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_arrival_notifa;
    }

    @Override
    public void initView(View view) {
        ((TextView)view.findViewById(R.id.tv_name)).setText(R.string.arrival_notif);
        ed_mobile=(EditText)view.findViewById(R.id.ed_mobile);
        tv_productname=(TextView)view.findViewById(R.id.tv_productname);
        check_app_push=(CheckBox)view.findViewById(R.id.check_app_push);
        check_message=(CheckBox)view.findViewById(R.id.check_message);
        btn_submit=(Button)view.findViewById(R.id.btn_submit);

        view.findViewById(R.id.rl_big_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isMobileNumber(ed_mobile.getText().toString())){
                    phoneNumber=ed_mobile.getText().toString();
                    ed_mobile.setText(ed_mobile.getText().toString().substring(0,3)+"****"+ed_mobile.getText().toString().substring(ed_mobile.getText().toString().length()-4,ed_mobile.getText().toString().length()));
                    ed_mobile.setSelection(ed_mobile.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_DEL){
            if (StringUtils.isMobileNumber(phoneNumber)){
                ed_mobile.setText(phoneNumber.substring(0,phoneNumber.length()-1));
                ed_mobile.setSelection(ed_mobile.getText().toString().length());
                phoneNumber="";
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void doBusiness(Context mContext) {
        check_app_push.setChecked(OdyApplication.getBoolean(CheckApp,true));
        check_message.setChecked(OdyApplication.getBoolean(CheckMessage,true));
        String moblie=OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,"");
        if (null!=moblie&&moblie.length()>10){
            HintPhoneNumber=moblie;
//            ed_mobile.setText(moblie.substring(0,3)+"****"+moblie.substring(moblie.length()-4,moblie.length()));
            ed_mobile.setHint(moblie.substring(0,3)+"****"+moblie.substring(moblie.length()-4,moblie.length()));
        }
        if (null!=product){
            tv_productname.setText(getString(R.string.product_name)+"："+product.name);
        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_app_push.isChecked()||check_message.isChecked()){
                    String pushType="";
                    if (check_app_push.isChecked()){
                        pushType+="1,";
                    }
                    if (check_message.isChecked()){
                        pushType+="2,";
                    }
                    if(null!=pushType&&pushType.length()>0){
                        if (null!=phoneNumber&&phoneNumber.toString().length()>0){
                            if (!StringUtils.isMobileNumber(phoneNumber)){
                                ToastUtils.showShort("手机号格式不正确");
                            }else{
                                getAttentionMerchantProduct(phoneNumber,pushType.substring(0,pushType.length()-1));
                            }
                        }else if(StringUtils.isMobileNumber(HintPhoneNumber)){
                            getAttentionMerchantProduct(HintPhoneNumber,pushType.substring(0,pushType.length()-1));
                        }else{
                            //手机号格式有问题
                        }
                    }
                }else{
                    ToastUtils.showShort("请选择一个通知方式");
                }
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void getAttentionMerchantProduct(String mobile,String pushType){
        if (null == product) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("mpId", product.mpId+"");
        params.put("pushType", pushType);
        params.put("mobile", mobile);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        showLoading();
        OkHttpManager.postAsyn(Constants.ATTENTION_NOTIFACTION, new OkHttpManager.ResultCallback<IsRepeatPhoneBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showStr(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onResponse(IsRepeatPhoneBean response) {
                if (response.code.equals("0")){
                    OdyApplication.putBoolean(CheckApp,check_app_push.isChecked());
                    OdyApplication.putBoolean(CheckMessage,check_message.isChecked());
                    finish();
                }
                ToastUtils.showShort(response.message);
            }
        }, params);
    }

}