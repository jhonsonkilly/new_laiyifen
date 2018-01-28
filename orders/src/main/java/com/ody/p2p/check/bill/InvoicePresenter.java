package com.ody.p2p.check.bill;

/**
 * Created by tangmeijuan on 16/6/24.
 */
public interface InvoicePresenter {
    void saveInvoice(InvoiceDocument document);

    void addTaxInfo();
}
