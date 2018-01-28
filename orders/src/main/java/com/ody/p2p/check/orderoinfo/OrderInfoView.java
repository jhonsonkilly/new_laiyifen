package com.ody.p2p.check.orderoinfo;

import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;

import java.util.List;


/**
 * Created by ody on 2016/6/20.
 */
public interface OrderInfoView extends BaseView {


    void initOrderInfo(OrderInfoBean.DataBean bean);

    void cancleOrder();

//    void isAftersale(String json);

    void aftersaleReasonList(List<ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs> list);

    void deleteOrder();

}
