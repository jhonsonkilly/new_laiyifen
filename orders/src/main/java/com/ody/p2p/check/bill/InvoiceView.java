package com.ody.p2p.check.bill;


import android.content.Context;

import com.ody.p2p.base.BaseView;

/**
 * Created by tangmeijuan on 16/6/24.
 */
public interface InvoiceView extends BaseView {

    void finishActivity();

    void addInvoiceInfo(InvoiceInfoBean invoiceInfoBean);
}
