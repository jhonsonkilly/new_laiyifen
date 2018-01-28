package com.ody.p2p.settings.accountSafe.modifyPsd2;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

public class ModifyPsdSecondActivity extends BaseActivity implements View.OnClickListener,ModifyPsdSecondView {

    protected TextView tv_name;
    protected TextView tv_confirm;
    protected RelativeLayout rl_big_back;
    protected EditText et_original_psd;
    protected EditText et_new_psd;
    protected EditText et_confirm_psd;
    protected ImageView iv_psd_cha1;
    protected ImageView iv_psd_cha2;
    protected ImageView iv_psd_cha3;
    protected ImageView iv_psd_visiable1;
    protected ImageView iv_psd_visiable2;
    protected ImageView iv_psd_visiable3;
    protected String dsPsdModify;//德升,个人设置,密码修改界面的接口路径

    protected ModifyPsdSecondPresenter modifyPsdSecondPresenter;

    @Override
    public void initPresenter() {
        modifyPsdSecondPresenter = new ModifyPsdSecondPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_psd2;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_confirm = (TextView)view.findViewById(R.id.tv_confirm);
        rl_big_back = (RelativeLayout)view.findViewById(R.id.rl_big_back);
        et_original_psd = (EditText)view.findViewById(R.id.et_original_psd);
        et_new_psd = (EditText)view.findViewById(R.id.et_new_psd);
        et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
        iv_psd_cha1 = (ImageView)view.findViewById(R.id.iv_psd_cha1);
        iv_psd_cha2 = (ImageView)view.findViewById(R.id.iv_psd_cha2);
        iv_psd_cha3 = (ImageView)view.findViewById(R.id.iv_psd_cha3);
        iv_psd_visiable1 = (ImageView)view.findViewById(R.id.iv_psd_visiable1);
        iv_psd_visiable2 = (ImageView)view.findViewById(R.id.iv_psd_visiable2);
        iv_psd_visiable3 = (ImageView)view.findViewById(R.id.iv_psd_visiable3);

        StringUtils.operateCha(et_original_psd,iv_psd_cha1);
        StringUtils.operateCha(et_new_psd,iv_psd_cha2);
        StringUtils.operateCha(et_confirm_psd, iv_psd_cha3);

        StringUtils.limitInputType(et_original_psd);
        StringUtils.limitInputType(et_new_psd);
        StringUtils.limitInputType(et_confirm_psd);

        tv_name.setText(getString(R.string.modify_psd));

        rl_big_back.setOnClickListener(this);
        iv_psd_cha1.setOnClickListener(this);
        iv_psd_cha2.setOnClickListener(this);
        iv_psd_cha3.setOnClickListener(this);
        iv_psd_visiable1.setOnClickListener(this);
        iv_psd_visiable2.setOnClickListener(this);
        iv_psd_visiable3.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        StringUtils.setTextviewGray(tv_confirm);
        et_original_psd.addTextChangedListener(new TextChange(et_original_psd, iv_psd_cha1));
        et_new_psd.addTextChangedListener(new TextChange(et_new_psd,iv_psd_cha2));
        et_confirm_psd.addTextChangedListener(new TextChange(et_confirm_psd,iv_psd_cha3) );
        et_original_psd.setOnFocusChangeListener(new TextFocusChange(et_original_psd, iv_psd_cha1));
        et_new_psd.setOnFocusChangeListener(new TextFocusChange(et_new_psd,iv_psd_cha2));
        et_confirm_psd.setOnFocusChangeListener(new TextFocusChange(et_confirm_psd,iv_psd_cha3));
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean checkPsd(String et_psd1,String et_psd2, String et_psd3) {
        if (StringUtils.isEmpty(et_psd1)){
            ToastUtils.showShort(getString(R.string.psd_not_null));
            return false;
        }
        if (!StringUtils.checkPsdLength(et_psd2)) {//首次输入的密码长度不合要求
            ToastUtils.showShort(getString(R.string.psd_format_error));
            return false;
        }
        if (!et_psd2.equals(et_psd3)) {
            ToastUtils.showShort(getString(R.string.psd_notconfirm));
            return false;
        }
        if (et_psd1.equals(et_psd2)){
            ToastUtils.showShort(getString(R.string.psd_repeat));
            return false;
        }
        return true;
    }

    @Override
    public void toAccountSafeActivity() {
        finish();
    }

    @Override
    public String getDsPsdModify() {
        return dsPsdModify;
    }

    protected boolean psd1IsVisiable = false;
    protected boolean psd2IsVisiable = false;
    protected boolean psd3IsVisiable = false;

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(iv_psd_cha1)){
            et_original_psd.setText("");
        }
        if (v.equals(iv_psd_cha2)){
            et_new_psd.setText("");
        }
        if (v.equals(iv_psd_cha3)){
            et_confirm_psd.setText("");
        }
        if (v.equals(iv_psd_visiable1)){

            if (!psd1IsVisiable){
                psd1IsVisiable = true;
                StringUtils.psdIsVisiable(et_original_psd,psd1IsVisiable);
                iv_psd_visiable1.setImageResource(R.drawable.user_pwd_on);
            }else {
                psd1IsVisiable = false;
                StringUtils.psdIsVisiable(et_original_psd,psd1IsVisiable);
                iv_psd_visiable1.setImageResource(R.drawable.user_pwd_off);
            }
//            et_original_psd.clearFocus();
//            et_new_psd.clearFocus();
//            et_confirm_psd.clearFocus();
            iv_psd_cha1.setVisibility(View.GONE);
            iv_psd_cha2.setVisibility(View.GONE);
            iv_psd_cha3.setVisibility(View.GONE);
        }
        if (v.equals(iv_psd_visiable2)){
            if (!psd2IsVisiable){
                psd2IsVisiable = true;
                StringUtils.psdIsVisiable(et_new_psd,psd2IsVisiable);
                iv_psd_visiable2.setImageResource(R.drawable.user_pwd_on);
            }else {
                psd2IsVisiable = false;
                StringUtils.psdIsVisiable(et_new_psd,psd2IsVisiable);
                iv_psd_visiable2.setImageResource(R.drawable.user_pwd_off);
            }
//            et_original_psd.clearFocus();
//            et_new_psd.clearFocus();
//            et_confirm_psd.clearFocus();
            iv_psd_cha1.setVisibility(View.GONE);
            iv_psd_cha2.setVisibility(View.GONE);
            iv_psd_cha3.setVisibility(View.GONE);
        }
        if (v.equals(iv_psd_visiable3)){

            if (!psd3IsVisiable){
                psd3IsVisiable = true;
                StringUtils.psdIsVisiable(et_confirm_psd,psd3IsVisiable);
                iv_psd_visiable3.setImageResource(R.drawable.user_pwd_on);
            }else {
                psd3IsVisiable = false;
                StringUtils.psdIsVisiable(et_confirm_psd,psd3IsVisiable);
                iv_psd_visiable3.setImageResource(R.drawable.user_pwd_off);
            }
//            et_original_psd.clearFocus();
//            et_new_psd.clearFocus();
//            et_confirm_psd.clearFocus();
            iv_psd_cha1.setVisibility(View.GONE);
            iv_psd_cha2.setVisibility(View.GONE);
            iv_psd_cha3.setVisibility(View.GONE);
        }
        if (v.equals(tv_confirm)){
            modifyPsdSecondPresenter.confirmModifyPsd(et_original_psd.getText().toString(),et_new_psd.getText().toString(),et_confirm_psd.getText().toString());
        }
    }

    //改变"确定"按钮可点击状态时的颜色
    protected void setConfirmButtonColor(int color){

    }

    //设置flag是方便项目修改button是否可点击时textview的字体颜色的 默认为0,即一直是xml布局里的白色  德升为1,不可点击为白色,可点击时为黑色
    protected void showButton(int canClickColor,int cannotClickColor,int flag,int canClickTextviewColor,int cannotClickTextviewColor) {
        if (!StringUtils.isEmpty(et_original_psd.getText().toString()) && !StringUtils.isEmpty(et_new_psd.getText().toString()) &&
                !StringUtils.isEmpty(et_confirm_psd.getText().toString())){
            tv_confirm.setClickable(true);
            tv_confirm.setBackgroundResource(canClickColor);
            if (flag==1){
                tv_confirm.setTextColor(canClickTextviewColor);
            }
        }else {
            tv_confirm.setClickable(false);
            tv_confirm.setBackgroundResource(cannotClickColor);
            if (flag==1){
                tv_confirm.setTextColor(cannotClickTextviewColor);
            }
        }
    }
    class TextChange implements TextWatcher {
        private EditText et;
        private ImageView iv;

        public TextChange(EditText et, ImageView iv) {
            this.et = et;
            this.iv = iv;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (et.getText().length() > 0 && et.hasFocus()) {
                iv.setVisibility(View.VISIBLE);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et.setText("");
                    }
                });
            } else {
                iv.setVisibility(View.INVISIBLE);
            }
            showButton(R.drawable.shape_can_click,R.drawable.shape_cannot_click,0,0,0);
        }



        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class TextFocusChange implements View.OnFocusChangeListener {

        private EditText et;
        private ImageView iv;

        public TextFocusChange(EditText et, ImageView iv) {
            this.et = et;
            this.iv = iv;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (et.getText().length() > 0 && hasFocus) {
                iv.setVisibility(View.VISIBLE);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et.setText("");
                    }
                });
            } else {
                iv.setVisibility(View.INVISIBLE);
            }
            showButton(R.drawable.shape_can_click,R.drawable.shape_cannot_click,0,0,0);
        }
    }
}
