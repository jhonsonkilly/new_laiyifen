package com.ody.p2p.RefoundList;

/**
 * Created by ody on 2016/7/19.
 */
public class RefoundBaseBean {
    int type;
    AfterSaleBean.OrderRefundVOs  orderRefundVOs;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AfterSaleBean.OrderRefundVOs getOrderRefundVOs() {
        return orderRefundVOs;
    }

    public void setOrderRefundVOs(AfterSaleBean.OrderRefundVOs orderRefundVOs) {
        this.orderRefundVOs = orderRefundVOs;
    }
}
