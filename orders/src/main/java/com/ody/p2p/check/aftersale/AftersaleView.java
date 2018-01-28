package com.ody.p2p.check.aftersale;


import com.ody.p2p.base.BaseView;
import com.ody.p2p.check.aftersale.Bean.AfterSaleTypeBean;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;
import com.ody.p2p.check.aftersale.Bean.ChangeProductBean;
import com.ody.p2p.check.aftersale.Bean.InitApplyRefundBean;
import com.ody.p2p.check.aftersale.Bean.SalesReturn;

import java.util.List;

/**
 * Created by ody on 2016/6/29.
 */
public interface AftersaleView extends BaseView {
    void getaftersale(List<ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs> list);

    void finishActivity();
    void getPhotos(String url);

    void aftersaletype(AfterSaleTypeBean afterSaleTypeBean);

    void initReturnProduct(InitApplyRefundBean initApplyRefundBean);

    void changeProduct(ChangeProductBean changeProductBean);

    void showDialog(String msg);

}
