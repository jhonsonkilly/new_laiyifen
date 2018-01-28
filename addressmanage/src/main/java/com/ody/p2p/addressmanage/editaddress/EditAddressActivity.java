package com.ody.p2p.addressmanage.editaddress;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.Contact.ContactsActivity;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.bean.BaseRequestBean;
import com.ody.p2p.addressmanage.bean.SaveAddressBackBean;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.selectaddress.RequestAddressBean;
import com.ody.p2p.views.selectaddress.SeclectAddressPopupWindow;
import com.ody.p2p.views.selectaddress.selectAddressListener;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

import static com.ody.p2p.Constants.AREA_CODE_ADDRESS;
import static com.ody.p2p.addressmanage.R.id.et_address_detail;


/**
 * Created by ody on 2016/6/13.
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener, selectAddressListener, EditAddressView {

    private ImageView iv_back;
    private TextView tv_title, tv_right;
    protected EditText et_contact_name, et_contact_tel, et_detail_location;
    private ImageView iv_contact_name_clear, iv_contact_tel_clear, iv_detail_location_clear;
    private LinearLayout lay_at_location;
    private TextView tv_at_location;
    private LinearLayout lay_select_contatc;
    public Button btn_address_save;
    protected CheckBox toggle_btn;
    private AddressBean.Address mBean;
    private Bundle bundle;
    protected SeclectAddressPopupWindow popwnd;
    private RequestAddressBean.Data mProvince, mCity, mRegion;
    private int isDefault = 0;// 0-非默认 1-默认
    private int isEdit = 0;// 0-添加 1-编辑
    private EditAddressPresenterImpl presenter;
    private Toast toast;
    private View mToastView;
    private boolean isFromOrder = false;
    public ImageView iv_edit_address_contact;
    protected CustomDialog dialog;
    private EditText et_certification_card;
    private RelativeLayout ll_certification;
    private ImageView iv_card_number_clear;


    @Override
    public void initPresenter() {
        isFromOrder = getIntent().getBooleanExtra("isFromOrder", false);
        if (isFromOrder) {
            btn_address_save.setText("保存并使用");
        } else {
            btn_address_save.setText("保存");
        }
        presenter = new EditAddressPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.addressmanage_activity_new_address;
    }

    @Override
    public void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_head_back);
        tv_title = (TextView) view.findViewById(R.id.tv_head_title);
        tv_right = (TextView) view.findViewById(R.id.tv_head_right);

        et_contact_name = (EditText) view.findViewById(R.id.et_address_contact_name);
        et_contact_tel = (EditText) view.findViewById(R.id.et_address_contact_tel);
        et_detail_location = (EditText) view.findViewById(et_address_detail);

        iv_contact_name_clear = (ImageView) view.findViewById(R.id.iv_address_contact_name_clear);
        iv_contact_tel_clear = (ImageView) view.findViewById(R.id.iv_address_contact_tel_clear);
        iv_detail_location_clear = (ImageView) view.findViewById(R.id.iv_address_detail_clear);

        btn_address_save = (Button) view.findViewById(R.id.btn_address_save);
        btn_address_save.setOnClickListener(this);

        lay_at_location = (LinearLayout) view.findViewById(R.id.lay_address_at_location);
        tv_at_location = (TextView) view.findViewById(R.id.tv_address_at_location);
        lay_select_contatc = (LinearLayout) view.findViewById(R.id.lay_select_contact);

        toggle_btn = (CheckBox) view.findViewById(R.id.toggleBtn);
        iv_edit_address_contact = (ImageView) findViewById(R.id.iv_edit_address_contact);

        et_certification_card = (EditText) findViewById(R.id.et_certification_card);
        ll_certification = (RelativeLayout) findViewById(R.id.ll_certification);
        iv_card_number_clear = (ImageView) findViewById(R.id.iv_card_number_clear);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            mBean = (AddressBean.Address) bundle.getSerializable("bean");
            isEdit = bundle.getInt("isEdit", 0);
        }
        if (OdyApplication.OVERSEA == 1) {
            ll_certification.setVisibility(View.VISIBLE);
        } else {
            ll_certification.setVisibility(View.GONE);
        }
        //编辑收货地址页面
        if (mBean != null) {
            tv_title.setText(getString(R.string.addressmanage_edite_address));
            tv_right.setText(getString(R.string.delete));
            tv_right.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_right.setVisibility(View.VISIBLE);

            et_certification_card.setText(mBean.getIdentityCardNumber());
            et_contact_name.setText(mBean.getUserName());
            et_contact_tel.setText(mBean.getMobile());
            tv_at_location.setText(mBean.getProvinceName() + " " + mBean.getCityName() + " " + mBean.getRegionName());
            et_detail_location.setText(mBean.getDetailAddress());
            if (mBean.getDefaultIs() == 1) {
                toggle_btn.setChecked(true);
            } else {
                toggle_btn.setChecked(false);
            }
            if (OdyApplication.OVERSEA == 1) {
                et_certification_card.setText(mBean.getIdentityCardNumber());
            }

            tv_right.setOnClickListener(this);

            RequestAddressBean addressBean = new RequestAddressBean();
            //这里的provinceCode其实是provinceID
            mProvince = addressBean.new Data(mBean.getProvinceCode() + "", mBean.getProvinceName(), mBean.getProvinceCode() + "");
            mCity = addressBean.new Data("", mBean.getCityName(), mBean.getCityId() + "");
            mRegion = addressBean.new Data("", mBean.getRegionName(), mBean.getRegionId() + "");
        }
        //新建收货人地址页面
        else {
            tv_title.setText(getResources().getText(R.string.new_address_title));
            tv_right.setVisibility(View.GONE);

        }

        iv_back.setOnClickListener(this);

        lay_select_contatc.setOnClickListener(this);

        et_contact_name.setOnFocusChangeListener(new TextFocusChange(et_contact_name, iv_contact_name_clear));

        et_contact_tel.setOnFocusChangeListener(new TextFocusChange(et_contact_tel, iv_contact_tel_clear));

        et_detail_location.setOnFocusChangeListener(new TextFocusChange(et_detail_location, iv_detail_location_clear));

        et_certification_card.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString()) && et_certification_card.hasFocus()) {
                    iv_card_number_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_card_number_clear.setVisibility(View.GONE);
                }
                iv_card_number_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_certification_card.setText("");
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        popwnd = new SeclectAddressPopupWindow(EditAddressActivity.this);
        popwnd.setSelectAddressListener(this);

        lay_at_location.setOnClickListener(this);


        toast = new Toast(getApplicationContext());
        mToastView = LayoutInflater.from(EditAddressActivity.this).inflate(R.layout.addressmanage_tost, null);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(mToastView);
        toast.setDuration(Toast.LENGTH_SHORT);


        dialog = new CustomDialog(EditAddressActivity.this, getString(R.string.addressmanage_delete_address));
        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                presenter.deleteAddress(isDefault, mBean.getId() + "");
            }
        });


    }

    @Override
    public void initListener() {

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
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_head_back) {
            finish();
        } else if (i == R.id.lay_select_contact) {
            //这种调用已经处理了那种一个联系人对应于多个号码的情况
//            Intent it = new Intent(Intent.ACTION_GET_CONTENT);
//            it.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//            startActivityForResult(it, 0);
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(
                    //mTODO:meiyizhi 获取联系人需要的权限
                    Manifest.permission.READ_CONTACTS
            )
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                JumpUtils.ToActivityFoResult(JumpUtils.CHOOSE_CONTACTS, null, 100, EditAddressActivity.this);
                            } else {
                                ToastUtils.showShort("为了更好的使用体验，请开启获取联系人权限!");
                            }
                        }
                    });
        } else if (i == R.id.tv_head_right) {//圆角的dialog的显示

            dialog.show();

        } else if (i == R.id.lay_address_at_location) {
            closeInput(v);
            popwnd.showAtLocation(findViewById(R.id.lay_edit_top), Gravity.BOTTOM, 0, 0);

        } else if (i == R.id.popup_click) {
            popwnd.dismiss();

        } else if (i == R.id.btn_address_save) {
            upLoadData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                et_contact_name.setText(data.getStringExtra(ContactsActivity.CONTACTS_NAME));
                et_contact_tel.setText(data.getStringExtra(ContactsActivity.CONTACTS_PHONE).replaceAll(" ", "").replaceAll("\\+86", ""));
            } else {
                //选取联系人的正确形式
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        Cursor c = null;
                        try {
                            c = getContentResolver().query(uri, new String[]{
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    null, null, null);

                            if (c != null && c.moveToFirst()) {
                                String number = c.getString(0);
                                String name = c.getString(1);
                                et_contact_name.setText(name);
                                et_contact_tel.setText(number.replaceAll(" ", "").replaceAll("\\+86", ""));
                            }
                        } finally {
                            if (c != null) {
                                c.close();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getAddress(RequestAddressBean.Data first, RequestAddressBean.Data second, RequestAddressBean.Data third) {
        tv_at_location.setText(first.name + " " + second.name + " " + third.name);
        mProvince = first;
        mCity = second;
        mRegion = third;
    }

    @Override
    public void delete(BaseRequestBean response) {
        finish();
    }

    @Override
    public void save(SaveAddressBackBean response) {
        if (isFromOrder) {
//            Bundle bd = new Bundle();
//            bd.putString("addressId", response.id);
//            JumpUtils.ToActivity(JumpUtils.CONFIRMORDER, bd);
            presenter.confirmordersave(response.id);
        } else {
            OdyApplication.putString(AREA_CODE_ADDRESS, tv_at_location.getText().toString() + " " + et_detail_location.getText().toString());
            OdyApplication.putString(Constants.AREA_CODE, mProvince.code);
            OdyApplication.putString(Constants.AREA_NAME, mRegion.getName());
            OdyApplication.putString(Constants.PROVINCE, mProvince.getName());
            OdyApplication.putString(Constants.CITY, mCity.getName());
            OdyApplication.putString(Constants.ADDRESS_ID, response.id);
            finish();
        }

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showStr(msg);
    }

    @Override
    public void finishActivity(ConfirmOrderBean.DataEntity.Errors errors) {
        Bundle db = new Bundle();
        if (null != errors) {
            db.putSerializable("error", errors);
        }
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER, db);
        finish();
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
        }
    }

    private void upLoadData() {
        if (TextUtils.isEmpty(et_contact_name.getText().toString())) {
            ToastUtils.showToast("请输入收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(et_contact_tel.getText().toString())) {
            ToastUtils.showToast("请输入联系方式");
            return;
        }
//        if (et_contact_tel.getText().toString().length() >= 13) {
//            ToastUtils.showToast("输入的手机号有误");
//            return;
//        }
        if (!StringUtils.checkMobile(et_contact_tel.getText().toString())) {
            ToastUtils.showStr(getString(R.string.addressmanage_phone_number_format));
            return;
        }

        if (TextUtils.isEmpty(tv_at_location.getText().toString())) {
            ToastUtils.showToast("请选择所在地区");
            return;
        }

        if (mProvince == null || mCity == null || mRegion == null) {
            ToastUtils.showStr(getString(R.string.addressmanage_address_error));
            return;
        }
        if (TextUtils.isEmpty(et_detail_location.getText().toString())) {
            ToastUtils.showToast("请输入详细地址");
            return;
        }
        isDefault = 0;
        if (toggle_btn.isChecked()) {
            isDefault = 1;
        }
        if (!TextUtils.isEmpty(et_certification_card.getText().toString()) && !UiUtils.isIdentityCard(et_certification_card.getText().toString())) {
            ToastUtils.showToast(getString(R.string.please_init_odcard_right));
            return;
        }
        if (isEdit == 1) {
            presenter.editAddress(mBean.getId() + "", et_contact_name.getText().toString(), mProvince.id, mProvince.id, mCity.id, mRegion.id, et_detail_location.getText().toString(), et_contact_tel.getText().toString(), isDefault, et_certification_card.getText().toString());
        } else {
            presenter.saveAddress(et_contact_name.getText().toString(), mProvince.id, mProvince.id, mCity.id, mRegion.id, et_detail_location.getText().toString(), et_contact_tel.getText().toString(), isDefault, et_certification_card.getText().toString());
        }
    }

    private void closeInput(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
