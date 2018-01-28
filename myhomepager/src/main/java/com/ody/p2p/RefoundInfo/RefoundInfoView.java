package com.ody.p2p.RefoundInfo;

import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/7/1.
 */
public interface RefoundInfoView extends BaseView {

    void getData(AfterSaleDetailBean.Data data);

    void cancel();

    void saveOK();

    void isAftersale(String json);
}
