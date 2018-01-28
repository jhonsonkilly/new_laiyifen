package com.ody.p2p.check.myorder;


import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

/**
 * Created by tangmeijuan on 16/6/13.
 */
public interface ConfirmOrderView  extends BaseView {
    void result(ConfirmOrderBean confirmOrderBean, int isInitOrder);

    void toLogin();

    void dealOrder(SubmitOrderBean bean);

    void saveAddress();
    void showErrorDialog(ConfirmOrderBean.DataEntity.Errors error);

    void showOutNumberDialog(String title);

    void savePointsFail();

    void showMoneyExceedDialog();

    Context context();

    void announcementList(AdData response);

    void onErr();

    void onNetworkErr();
}
