package com.ody.p2p.pay.payMode.payOnline;


import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/6/20.
 */
public interface PayOnlineView extends BaseView {

    void setPayList(PayTypeListBean bean);

    void startPay(PrePayInfoBean bean);

    void countdowntime(CancelTimeBean cancelTimeBean);

    void setWalletMessage(WalletBean walletBean);
    void lyfSupportPayment(SupportBean walletBean);
}

