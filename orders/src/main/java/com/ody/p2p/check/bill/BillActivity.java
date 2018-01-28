package com.ody.p2p.check.bill;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.util.ArrayList;


/**
 * Created by tangmeijuan on 16/6/15.
 */
public class BillActivity extends BaseActivity implements InvoiceView, View.OnClickListener {

    private CheckBox cb_switch;
    private ImageView backIcon;
    private RadioButton rg_invoive_paper;//纸质发票
    private RadioButton rg_invoive_electronic;//电子发票
    private RadioButton rb_person;
    private RadioButton rb_company;
    private RadioButton rb_invoice_content_detail_false;
    private RadioButton rb_invoice_content_detail_true;
    private LinearLayout ll_content;
    private LinearLayout ll_invoice;
    private Button btn_yes;
    private InvoicePresenter presenter;
    private LinearLayout ll_taitou;
    private EditText et_company_name, et_phone, et_email;
    private EditText et_taxpayer_code;
    private EditText et_receiver_mail;
    private RadioGroup rg_invoice_type;
    private RadioGroup rg_taitou;
    private RadioGroup rg_invoice_way;
    private RadioGroup rg_invoice_content;
    private RadioButton rb_invoice_type1, rb_invoice_type2;
    private ConfirmOrderBean.DataEntity.OrderInvoiceEntity orderInvoice;

    //普通发票
    private LinearLayout ll_common;
    //增值税发票
    private LinearLayout ll_added_tax;
    private LinearLayout ll_added_pros;
    private RecyclerView rv_pics;//不支持增值税发票的商品列表
    private LinearLayout ll_addtax_unauth;//增值未认证
    private LinearLayout ll_addtax_auth;//增值已认证
    private TextView tv_company_name;
    private TextView tv_code;
    private TextView tv_register_address;
    private TextView tv_register_phone;
    private TextView tv_bank;
    private TextView tv_bank_account;
    private TextView tv_electronic_info2;
    private ArrayList<String> pros;
    private TextView tv_invoice_rule;
    private TextView tv_invoive_electronic;

    private InvoiceDocument mDocument;

    @Override
    public void initPresenter() {
        presenter = new InvoicePresenterImpl(this);
        String str = getIntent().getStringExtra("invoice_content");
        Gson gson = new Gson();
        orderInvoice = gson.fromJson(str, ConfirmOrderBean.DataEntity.OrderInvoiceEntity.class);
        pros = getIntent().getStringArrayListExtra("proUrl_list");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_bill;
    }

    @Override
    public void initView(View view) {
        mDocument = new InvoiceDocument();
        backIcon = (ImageView) findViewById(R.id.backIcon);
        cb_switch = (CheckBox) findViewById(R.id.cb_switch);
        rb_invoice_type1 = (RadioButton) findViewById(R.id.rb_invoice_type1);
        rb_invoice_type2 = (RadioButton) findViewById(R.id.rb_invoice_type2);
        ll_invoice = (LinearLayout) findViewById(R.id.ll_invoice);
        rg_invoive_paper = (RadioButton) findViewById(R.id.rg_invoive_paper);
        rg_invoive_electronic = (RadioButton) findViewById(R.id.rg_invoive_electronic);
        rb_person = (RadioButton) findViewById(R.id.rb_person);
        rb_company = (RadioButton) findViewById(R.id.rb_company);
        rb_invoice_content_detail_false = (RadioButton) findViewById(R.id.rb_invoice_content_detail_false);
        rb_invoice_content_detail_true = (RadioButton) findViewById(R.id.rb_invoice_content_detail_true);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        btn_yes = (Button) findViewById(R.id.btn_yes);
        ll_taitou = (LinearLayout) findViewById(R.id.ll_taitou);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_taxpayer_code = (EditText) findViewById(R.id.et_taxpayer_code);
        et_receiver_mail = (EditText) findViewById(R.id.et_receiver_mail);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
        rg_invoice_way = (RadioGroup) findViewById(R.id.rg_invoice_way);
        rg_taitou = (RadioGroup) findViewById(R.id.rg_taitou);
        rg_invoice_type = (RadioGroup) findViewById(R.id.rg_invoice_type);
        rg_invoice_content = (RadioGroup) findViewById(R.id.rg_invoice_content);

        ll_added_pros = (LinearLayout) findViewById(R.id.ll_added_pros);
        ll_common = (LinearLayout) findViewById(R.id.ll_common);
        ll_added_tax = (LinearLayout) findViewById(R.id.ll_added_tax);
        rv_pics = (RecyclerView) findViewById(R.id.rv_pics);
        ll_addtax_unauth = (LinearLayout) findViewById(R.id.ll_addtax_unauth);
        ll_addtax_auth = (LinearLayout) findViewById(R.id.ll_addtax_auth);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_code = (TextView) findViewById(R.id.tv_code);
        tv_register_address = (TextView) findViewById(R.id.tv_register_address);
        tv_register_phone = (TextView) findViewById(R.id.tv_register_phone);
        tv_bank = (TextView) findViewById(R.id.tv_bank);
        tv_bank_account = (TextView) findViewById(R.id.tv_bank_account);
        tv_invoice_rule = (TextView) findViewById(R.id.tv_invoice_rule);
        tv_electronic_info2 = (TextView) findViewById(R.id.tv_electronic_info2);
        tv_invoive_electronic = (TextView) findViewById(R.id.tv_invoive_electronic);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv_pics.setLayoutManager(gridLayoutManager);
        backIcon.setOnClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {
        if (orderInvoice == null) {
            return;
        }
        presenter.addTaxInfo();


        initEvent();

        initData();

    }

    private void initEvent() {
        //是否需要发票
        cb_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_content.setVisibility(View.VISIBLE);
                    if (mDocument.isInvoiceTypeNull()) {
                        mDocument.setInvoiceTypeNormal();
                    }
                } else {
                    ll_content.setVisibility(View.GONE);
                    mDocument.setInvoiceTypeNull();
                    presenter.saveInvoice(mDocument);
                }
            }
        });

        //开票方式
        rg_invoice_way.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rg_invoive_electronic) {
                    //电子发票
                    mDocument.setInvoiceModeElectronic();
                    //隐藏发票类型
                    ll_invoice.setVisibility(View.GONE);
                    int taitouCheckId = rg_taitou.getCheckedRadioButtonId();
                    if (taitouCheckId == R.id.rb_person) {
                        //发票抬头内容 个人
                        showElectronicAndPersonView();
                    } else if (taitouCheckId == R.id.rb_company) {
                        //发票抬头内容 单位
                        showElectronicAndCompanyView();
                    } else {
                        hideAllTaitouView();
                    }

                } else if (checkedId == R.id.rg_invoive_paper) {
                    //纸质发票
                    mDocument.setInvoiceModePaper();
                    //显示发票类型
                    ll_invoice.setVisibility(View.VISIBLE);
                    int taitouCheckId = rg_taitou.getCheckedRadioButtonId();
                    if (taitouCheckId == R.id.rb_person) {
                        //发票抬头内容 个人
                        showPaperAndPersonView();
                    } else if (taitouCheckId == R.id.rb_company) {
                        //发票抬头内容 单位
                        showPaperAndCompanyView();
                    } else {
                        hideAllTaitouView();
                    }
                }
            }
        });

        rg_invoice_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_invoice_type1) {
                    //普通发票
                    mDocument.setInvoiceTypeNormal();
                    ll_common.setVisibility(View.VISIBLE);
                    ll_added_tax.setVisibility(View.GONE);
                    btn_yes.setClickable(true);
                    btn_yes.setBackgroundResource(R.drawable.theme_btn_normal);
                } else if (checkedId == R.id.rb_invoice_type2) {
                    //增值税发票
                    mDocument.setInvoiceTypeVAT();
                    ll_common.setVisibility(View.GONE);
                    ll_added_tax.setVisibility(View.VISIBLE);
                    btn_yes.setClickable(true);
                    btn_yes.setBackgroundResource(R.drawable.theme_btn_normal);
                    if (pros != null && pros.size() > 0) {
                        //存在不支持增值税发票的商品
                        ll_added_pros.setVisibility(View.VISIBLE);
                        InvoiceProductsAdapter adapter = new InvoiceProductsAdapter(BillActivity.this);
                        adapter.setData(pros);
                        rv_pics.setAdapter(adapter);
                        btn_yes.setClickable(false);
                        btn_yes.setBackgroundResource(R.drawable.shape_cannot_click);
                    } else {
                        ll_added_pros.setVisibility(View.GONE);
                    }

                    if (ll_addtax_unauth.getVisibility() == View.VISIBLE) {
//                        ToastUtils.showToast("用户没有增值税发票信息");
                        btn_yes.setClickable(false);
                        btn_yes.setBackgroundResource(R.drawable.shape_cannot_click);
                    }
                } else {
                    //不开发票
                    mDocument.setInvoiceTypeNull();
                }
            }
        });


        //发票抬头
        rg_taitou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.rb_person == i) {
                    //发票抬头内容 个人
                    mDocument.setInvoiceTitleTypePersonal();
                    int wayCheckId = rg_invoice_way.getCheckedRadioButtonId();
                    if (wayCheckId == R.id.rg_invoive_electronic) {
                        //电子发票
                        showElectronicAndPersonView();
                    } else if (wayCheckId == R.id.rg_invoive_paper) {
                        //纸质发票
                        showPaperAndPersonView();
                    } else {
                        hideAllTaitouView();
                    }

                } else if (R.id.rb_company == i) {
                    //发票抬头内容 单位
                    mDocument.setInvoiceTitleTypeCompany();
                    int wayCheckId = rg_invoice_way.getCheckedRadioButtonId();
                    if (wayCheckId == R.id.rg_invoive_electronic) {
                        //电子发票
                        showElectronicAndCompanyView();
                    } else if (wayCheckId == R.id.rg_invoive_paper) {
                        //纸质发票
                        showPaperAndCompanyView();
                    } else {
                        hideAllTaitouView();
                    }
                }
            }
        });
        //发票内容
        rg_invoice_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.rb_invoice_content_detail_false == i) {
                    //发票内容 汇总
                    mDocument.setNeedDetailsFalse();
                } else if (R.id.rb_invoice_content_detail_true == i) {
                    //发票内容 明细
                    mDocument.setNeedDetailsTrue();
                } else {
                    //发票内容 默认汇总
                    mDocument.setNeedDetailsFalse();
                }
            }
        });


        tv_invoice_rule.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
    }

    /**
     * 隐藏所有抬头需要输入的信息
     */
    private void hideAllTaitouView() {
        ll_taitou.setVisibility(View.GONE);
    }

    /**
     * 显示（电子发票 - 个人）需要输入的信息
     */
    private void showElectronicAndPersonView() {
        ll_taitou.setVisibility(View.VISIBLE);
        et_company_name.setVisibility(View.GONE);
        et_taxpayer_code.setVisibility(View.GONE);
        et_receiver_mail.setVisibility(View.VISIBLE);
    }

    /**
     * 显示（纸质发票 - 个人）需要输入的信息
     */
    private void showPaperAndPersonView() {
        hideAllTaitouView();
    }

    /**
     * 显示（纸质发票 - 单位）需要输入的信息
     */
    private void showPaperAndCompanyView() {
        ll_taitou.setVisibility(View.VISIBLE);
        et_company_name.setVisibility(View.VISIBLE);
        et_taxpayer_code.setVisibility(View.GONE);
        et_receiver_mail.setVisibility(View.GONE);
    }

    /**
     * 显示（电子发票 - 单位）需要输入的信息
     */
    private void showElectronicAndCompanyView() {
        ll_taitou.setVisibility(View.VISIBLE);
        et_company_name.setVisibility(View.VISIBLE);
        et_taxpayer_code.setVisibility(View.VISIBLE);
        et_receiver_mail.setVisibility(View.VISIBLE);
    }

    private void initData() {
        ConfirmOrderBean.DataEntity.OrderInvoiceEntity.InvoiceEntity invoice = orderInvoice.invoice;
        if (invoice == null) {
            mDocument.setInvoiceTypeNull();
            return;
        }
        //发票类型
        mDocument.setInvoiceType(invoice.invoiceType);
        //开票方式
        mDocument.setInvoiceMode(invoice.invoiceMode);
        //发票抬头类型
        mDocument.setInvoiceTitleType(invoice.invoiceTitleType);
        //发票抬头内容
        mDocument.setInvoiceTitleContent(invoice.invoiceTitleContent);
        et_company_name.setText(invoice.invoiceTitleContent);
        //纳税人识别码
        mDocument.setTaxpayerIdentificationCode(invoice.taxpayerIdentificationCode);
        et_taxpayer_code.setText(invoice.taxpayerIdentificationCode);
        //收票人邮箱
        mDocument.setTakerEmail(invoice.takerEmail);
        et_receiver_mail.setText(invoice.takerEmail);
        //发票内容
        mDocument.setIsNeedDetails(invoice.isNeedDetails);
        //是否开发票
        if (mDocument.isInvoiceTypeNull()) {
            //不需要发票
            cb_switch.setChecked(false);
            ll_content.setVisibility(View.GONE);
        } else {
            //不需要发票
            cb_switch.setChecked(true);
            ll_content.setVisibility(View.VISIBLE);
        }
        //开票方式
//        if (orderInvoice.isSupportEInvoice()) {
//            rg_invoive_electronic.setEnabled(true);
//            rg_invoive_electronic.setTextColor(getResources().getColor(R.color.main_title_color));
//            tv_electronic_info2.setVisibility(View.GONE);
//        } else {
//            rg_invoive_electronic.setEnabled(false);
//            rg_invoive_electronic.setTextColor(getResources().getColor(R.color.sub_title_color));
//            tv_electronic_info2.setVisibility(View.VISIBLE);
//        }
        if(orderInvoice.isSupportEInvoice == 0){
            rg_invoive_electronic.setEnabled(true);
            rg_invoive_electronic.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_electronic_info2.setVisibility(View.GONE);
        }else if(orderInvoice.isSupportEInvoice == 1){
            rg_invoive_paper.setEnabled(true);
            rg_invoive_electronic.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_electronic_info2.setVisibility(View.VISIBLE);
        }else if(orderInvoice.isSupportEInvoice == 2){
            rg_invoive_electronic.setVisibility(View.GONE);
            tv_invoive_electronic.setVisibility(View.GONE);
            rg_invoive_paper.setEnabled(true);
            rg_invoive_electronic.setTextColor(getResources().getColor(R.color.main_title_color));
        }
        if (mDocument.isInvoiceModeElectronic()) {
            //电子发票
            rg_invoive_electronic.setChecked(true);
            //隐藏发票类型
            ll_invoice.setVisibility(View.GONE);
        } else if (mDocument.isInvoiceModePaper()) {
            //纸质发票
            rg_invoive_paper.setChecked(true);
            //显示发票类型
            ll_invoice.setVisibility(View.VISIBLE);
        } else {
            //默认纸质发票
//            rg_invoive_paper.setChecked(true);
            //默认电子发票
            if(rg_invoive_electronic.getVisibility() == View.VISIBLE){
                rg_invoive_electronic.setChecked(true);
            }else{
                rg_invoive_paper.setChecked(true);
            }
            //显示发票类型
            ll_invoice.setVisibility(View.VISIBLE);
        }
        //发票抬头
        if (mDocument.isInvoiceTitleTypePersonal()) {
            //发票抬头内容 个人
            rb_person.setChecked(true);
        } else if (mDocument.isInvoiceTitleTypeCompany()) {
            //发票抬头内容 单位
            rb_company.setChecked(true);
        } else {
            //发票抬头内容 默认个人
            rb_person.setChecked(true);
        }
        //发票类型
        if (mDocument.isInvoiceTypeNormal()) {
            //普通发票
            rb_invoice_type1.setChecked(true);
        } else if (mDocument.isInvoiceTypeVAT()) {
            //增值税发票
            rb_invoice_type2.setChecked(true);
        } else {
            //默认普通发票
            rb_invoice_type1.setChecked(true);
        }
        //发票内容 写死 汇总 + 明细
        if (mDocument.isNeedDetails()) {
            rb_invoice_content_detail_true.setChecked(true);
        } else {
            rb_invoice_content_detail_false.setChecked(true);
        }
        //动态展示
//        if (orderInvoice.invoiceContentList != null && orderInvoice.invoiceContentList.size() > 0) {
//            for (int i = 0; i < orderInvoice.invoiceContentList.size(); i++) {
//                ConfirmOrderBean.DataEntity.OrderInvoiceEntity.InvoiceContentListEntity invoiceContentListEntity = orderInvoice.invoiceContentList.get(i);
//                RadioButton radioButton = new RadioButton(this);
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//                params.setMargins(0, 24, 24, 0);
//                radioButton.setLayoutParams(params);
//                radioButton.setButtonDrawable(R.drawable.checkbox_coupon);
//                radioButton.setTextColor(getResources().getColor(R.color.main_title_color));
//                radioButton.setText(invoiceContentListEntity.invoiceContentValue);
//                radioButton.setPadding(PxUtils.dipTopx(5), 0, 0, 0);
//                if (invoiceContentListEntity.invoiceContentId == invoice.invoiceContentId) {
//                    radioButton.setChecked(true);
//                } else {
//                    radioButton.setChecked(false);
//                }
//                rg_invoice_content.addView(radioButton);
//            }
//        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void finishActivity() {
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
        finish();
    }

    @Override
    public void addInvoiceInfo(final InvoiceInfoBean invoiceInfoBean) {
        if (invoiceInfoBean.data != null) {
            if (!TextUtils.isEmpty(invoiceInfoBean.data.id)) {
                ll_addtax_unauth.setVisibility(View.GONE);
                ll_addtax_auth.setVisibility(View.VISIBLE);
                tv_company_name.setText(invoiceInfoBean.data.unitName);
                tv_code.setText(invoiceInfoBean.data.taxpayerIdentificationCode);
                tv_register_address.setText(invoiceInfoBean.data.registerAddress);
                tv_register_phone.setText(invoiceInfoBean.data.registerPhone);
                tv_bank.setText(invoiceInfoBean.data.bankDeposit);
                tv_bank_account.setText(invoiceInfoBean.data.bankAccount);
            } else {
                ll_addtax_unauth.setVisibility(View.VISIBLE);
                ll_addtax_auth.setVisibility(View.GONE);
            }
        } else {
            ll_addtax_unauth.setVisibility(View.VISIBLE);
            ll_addtax_auth.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_yes) {
            mDocument.setInvoiceTitleContent(getCompanyName());
            mDocument.setTakerEmail(getTakerEmail());
            mDocument.setTaxpayerIdentificationCode(getTaxpayerIdentificationCode());
            //动态发票内容
//            if (orderInvoice.invoiceContentList != null && orderInvoice.invoiceContentList.size() > 0) {
//                int checkItem = getCheckItem(rg_invoice_content);
//                if (checkItem == -1) {
//                    mDocument.setInvoiceContentId(0);
//                    mDocument.setInvoiceContent("");
//                } else {
//                    ConfirmOrderBean.DataEntity.OrderInvoiceEntity.InvoiceContentListEntity entity = orderInvoice.invoiceContentList.get(checkItem);
//                    mDocument.setInvoiceContentId(entity.invoiceContentId);
//                    mDocument.setInvoiceContent(entity.invoiceContentValue);
//                }
//            }
            presenter.saveInvoice(mDocument);
        } else if (view.getId() == R.id.backIcon) {
            JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
            finish();
        } else if (view.getId() == R.id.tv_invoice_rule) {
            JumpUtils.ToWebActivity(OdyApplication.H5URL + "/pay/billRule.html", WebActivity.NO_TITLE, -1, null);
        }
    }

    @Override
    public void onBackPressed() {
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
        finish();
    }

    @NonNull
    private String getTaxpayerIdentificationCode() {
        return et_taxpayer_code.getText().toString().trim();
    }

    @NonNull
    private String getTakerEmail() {
        return et_receiver_mail.getText().toString().trim();
    }

    @NonNull
    private String getCompanyName() {
        return et_company_name.getText().toString().trim();
    }


    public int getCheckItem(RadioGroup group) {
        int item = 0;
        for (int i = 0; i < group.getChildCount(); i++) {
            if (((RadioButton) group.getChildAt(i)).isChecked()) {
                item = i;
                break;
            }
        }
        return item;
    }
}
