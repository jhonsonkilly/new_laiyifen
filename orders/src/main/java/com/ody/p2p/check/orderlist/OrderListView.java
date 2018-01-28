package com.ody.p2p.check.orderlist;


import com.ody.p2p.base.BaseView;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public interface OrderListView extends BaseView {
    void orderlist(OrderListBean orderListBean);

    void refreshlist();
}
