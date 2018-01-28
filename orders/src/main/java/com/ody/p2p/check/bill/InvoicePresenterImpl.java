package com.ody.p2p.check.bill;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangmeijuan on 16/6/24.
 */
public class InvoicePresenterImpl implements InvoicePresenter {

    private InvoiceView invoiceView;

    public InvoicePresenterImpl(InvoiceView invoiceView) {
        this.invoiceView = invoiceView;
    }

    @Override
    public void saveInvoice(final InvoiceDocument document) {
        Map<String, String> newParams = new HashMap<>();
        newParams.put("invoiceMode", document.getInvoiceMode() + "");
        newParams.put("invoiceType", document.getInvoiceType() + "");
        //发票类型
        if (document.isInvoiceTypeNull()){//不开发票
            doSaveInvoiceWork(document, newParams);
            return;
        }
        newParams.put("invoiceTitleType", document.getInvoiceTitleType() + "");
        if (document.isInvoiceModeElectronic()) {
            //电子发票
            saveElectronicInvoice(document, newParams);
        } else if (document.isInvoiceModePaper()) {
            //纸质发票
            if (document.isInvoiceTypeNormal()) {//普通发票
                //开票方式
                newParams.put("invoiceType", document.getInvoiceType() + "");
                savePaperInvoice(document, newParams);
            } else if (document.isInvoiceTypeVAT()) {//增值税发票
                newParams.put("invoiceType", document.getInvoiceType() + "");
                doSaveInvoiceWork(document, newParams);
            }
        } else {
            ToastUtils.showShort("请选择开票方式");
            return;
        }

    }

    private void savePaperInvoice(InvoiceDocument document, Map<String, String> newParams) {
        if (document.isInvoiceTitleTypePersonal()) {
            //发票抬头:个人
        } else if (document.isInvoiceTitleTypeCompany()) {
            //发票抬头:单位
            if (TextUtils.isEmpty(document.getInvoiceTitleContent())) {
                ToastUtils.showShort("请填写单位名称");
                return;
            }
            newParams.put("invoiceTitleContent", document.getInvoiceTitleContent());
        } else {
            ToastUtils.showShort("请选择发票抬头");
            return;
        }
//        if (TextUtils.isEmpty(document.getInvoiceContent())) {
//            ToastUtils.showShort("请选择发票内容");
//            return;
//        }
//        newParams.put("invoiceContentId", document.getInvoiceContentId() + "");
//        newParams.put("invoiceContent", document.getInvoiceContent());
        newParams.put("isNeedDetails", document.getIsNeedDetails() + "");
        doSaveInvoiceWork(document, newParams);
    }


    private void saveElectronicInvoice(InvoiceDocument document, Map<String, String> newParams) {
        if (document.isInvoiceTitleTypePersonal()) {
            //发票抬头:个人
            newParams.put("takerEmail", document.getTakerEmail());
        } else if (document.isInvoiceTitleTypeCompany()) {
            //发票抬头:单位
            if (TextUtils.isEmpty(document.getInvoiceTitleContent())) {
                ToastUtils.showShort("请填写单位名称");
                return;
            }
            newParams.put("invoiceTitleContent", document.getInvoiceTitleContent());
            if (TextUtils.isEmpty(document.getTaxpayerIdentificationCode())) {
                ToastUtils.showShort("请填写纳税人识别号");
                return;
            }
            newParams.put("taxpayerIdentificationCode", document.getTaxpayerIdentificationCode());
            newParams.put("takerEmail", document.getTakerEmail());
        } else {
            ToastUtils.showShort("请选择发票抬头");
            return;
        }
//        if (TextUtils.isEmpty(document.getInvoiceContent())) {
//            ToastUtils.showShort("请选择发票内容");
//            return;
//        }
//        newParams.put("invoiceContentId", document.getInvoiceContentId() + "");
//        newParams.put("invoiceContent", document.getInvoiceContent());
        newParams.put("isNeedDetails", document.getIsNeedDetails() + "");
        doSaveInvoiceWork(document, newParams);
    }

    private void doSaveInvoiceWork(final InvoiceDocument document, Map<String, String> newParams) {
        invoiceView.showLoading();
        newParams.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.SAVE_INVOICE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                invoiceView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                invoiceView.hideLoading();
                if (response == null) {
                    return;
                }
                if (response.code.equals("0") && !document.isInvoiceTypeNull()) {
                    invoiceView.finishActivity();
                }
            }
        }, newParams);
    }


    @Override
    public void addTaxInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        invoiceView.showLoading();
        OkHttpManager.postAsyn(Constants.INVOICE_INFO, new OkHttpManager.ResultCallback<InvoiceInfoBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                invoiceView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(InvoiceInfoBean response) {
                if (response != null) {
                    invoiceView.addInvoiceInfo(response);
                }
            }
        }, params);
    }
}
