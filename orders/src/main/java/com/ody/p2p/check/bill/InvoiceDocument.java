package com.ody.p2p.check.bill;

/**
 * 发票单据
 */

public class InvoiceDocument {

    //region 发票类型
    private static final int INVOICE_TYPE_NULL = 0; //不开发票
    private static final int INVOICE_TYPE_NORMAL = 1; //普通发票
    private static final int INVOICE_TYPE_VAT = 2; //增值税发票

    /**
     * @return 当选择不开发票时返回true
     */
    public boolean isInvoiceTypeNull() {
        return invoiceType == INVOICE_TYPE_NULL;
    }

    /**
     * 设置不开发票
     */
    public void setInvoiceTypeNull() {
        this.invoiceType = INVOICE_TYPE_NULL;
    }

    /**
     * @return 当选择开普通发票时返回true
     */
    public boolean isInvoiceTypeNormal() {
        return invoiceType == INVOICE_TYPE_NORMAL;
    }

    /**
     * 设置发票类型为：普通发票
     */
    public void setInvoiceTypeNormal() {
        this.invoiceType = INVOICE_TYPE_NORMAL;
    }

    /**
     * @return 当选择开增值税发票时返回true
     */
    public boolean isInvoiceTypeVAT() {
        return invoiceType == INVOICE_TYPE_VAT;
    }

    /**
     * 设置发票类型为：增值税发票
     */
    public void setInvoiceTypeVAT() {
        this.invoiceType = INVOICE_TYPE_VAT;
    }

    //endregion

    //region 开票方式
    private static final int INVOICE_MODE_ELECTRONIC = 1; //电子发票
    private static final int INVOICE_MODE_PAPER = 2; //纸质发票

    /**
     * @return 当选择开电子发票时返回true
     */
    public boolean isInvoiceModeElectronic() {
        return invoiceMode == INVOICE_MODE_ELECTRONIC;
    }

    /**
     * 设置开票方式为：电子发票
     */
    public void setInvoiceModeElectronic() {
        this.invoiceMode = INVOICE_MODE_ELECTRONIC;
    }

    /**
     * @return 当选择开纸质发票时返回true
     */
    public boolean isInvoiceModePaper() {
        return invoiceMode == INVOICE_MODE_PAPER;
    }

    /**
     * 设置开票方式为：纸质发票
     */
    public void setInvoiceModePaper() {
        this.invoiceMode = INVOICE_MODE_PAPER;
    }

    //endregion

    //region 发票抬头类型
    private static final int INVOICE_TITLE_TYPE_PERSONAL = 1; //个人
    private static final int INVOICE_TITLE_TYPE_COMPANY = 2; //单位


    /**
     * @return 当选择开个人发票时返回true
     */
    public boolean isInvoiceTitleTypePersonal() {
        return invoiceTitleType == INVOICE_TITLE_TYPE_PERSONAL;
    }

    /**
     * 设置发票抬头类型为：个人
     */
    public void setInvoiceTitleTypePersonal() {
        this.invoiceTitleType = INVOICE_TITLE_TYPE_PERSONAL;
    }

    /**
     * @return 当选择开单位发票时返回true
     */
    public boolean isInvoiceTitleTypeCompany() {
        return invoiceTitleType == INVOICE_TITLE_TYPE_COMPANY;
    }


    /**
     * 设置发票抬头类型为：单位
     */
    public void setInvoiceTitleTypeCompany() {
        this.invoiceTitleType = INVOICE_TITLE_TYPE_COMPANY;
    }


    //endregion


    private static final int INVOICE_IS_NEED_DETAIL_FALSE = 0; //汇总
    private static final int INVOICE_IS_NEED_DETAIL_TRUE = 1; //明细

    /**
     * @return 发票内容为明细时返回true
     */
    public boolean isNeedDetails() {
        return isNeedDetails == INVOICE_IS_NEED_DETAIL_TRUE;
    }

    /**
     * 设置发票内容为明细
     */
    public void setNeedDetailsTrue() {
        this.isNeedDetails = INVOICE_IS_NEED_DETAIL_TRUE;
    }

    /**
     * 设置发票内容为汇总
     */
    public void setNeedDetailsFalse() {
        this.isNeedDetails = INVOICE_IS_NEED_DETAIL_FALSE;
    }

    /**
     * 是否开发票
     */
    private boolean isOpened;

    /**
     * 发票类型
     */
    private int invoiceType;
    /**
     * 开票方式
     */
    private int invoiceMode;
    /**
     * 发票抬头类型
     */
    private int invoiceTitleType;
    /**
     * 发票抬头内容（这里指单位名称）
     */
    private String invoiceTitleContent;
    /**
     * 是否需要明细（默认不需要）
     */
    private int isNeedDetails;
    /**
     * 发票内容id
     */
    private long invoiceContentId;
    /**
     * 发票内容
     */
    private String invoiceContent;
    /**
     * 收票人手机
     */
    private String takerMobile;
    /**
     * 收票人邮箱
     */
    private String takerEmail;
    /**
     * 纳税人识别码
     */
    private String taxpayerIdentificationCode;
    /**
     * 订单类型0：普通订单 1：团购订单 2：询价订单 3：租赁订单
     * 4：砍价订单 5：预售订单，第一阶段支付定金
     * 6：预售订单：第二阶段支付尾款 默认普通订单，其他业务类型订单时，必须字段
     */
    private int businessType;


    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        this.isOpened = opened;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(int invoiceMode) {
        this.invoiceMode = invoiceMode;
    }

    public int getInvoiceTitleType() {
        return invoiceTitleType;
    }

    public void setInvoiceTitleType(int invoiceTitleType) {
        this.invoiceTitleType = invoiceTitleType;
    }

    public String getInvoiceTitleContent() {
        return invoiceTitleContent;
    }

    public void setInvoiceTitleContent(String invoiceTitleContent) {
        this.invoiceTitleContent = invoiceTitleContent;
    }

    public int getIsNeedDetails() {
        return isNeedDetails;
    }

    public void setIsNeedDetails(int isNeedDetails) {
        this.isNeedDetails = isNeedDetails;
    }

    public long getInvoiceContentId() {
        return invoiceContentId;
    }

    public void setInvoiceContentId(long invoiceContentId) {
        this.invoiceContentId = invoiceContentId;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getTakerMobile() {
        return takerMobile;
    }

    public void setTakerMobile(String takerMobile) {
        this.takerMobile = takerMobile;
    }

    public String getTakerEmail() {
        return takerEmail;
    }

    public void setTakerEmail(String takerEmail) {
        this.takerEmail = takerEmail;
    }

    public String getTaxpayerIdentificationCode() {
        return taxpayerIdentificationCode;
    }

    public void setTaxpayerIdentificationCode(String taxpayerIdentificationCode) {
        this.taxpayerIdentificationCode = taxpayerIdentificationCode;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

}
