package com.ody.p2p.addressmanage.selectaddressactivity;


import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.base.BaseView;
import com.ody.p2p.receiver.ConfirmOrderBean;

/**
 * Created by ody on 2016/6/17.
 */
public interface SelectAddressView extends BaseView {
    void refreshAddresList(AddressBean list);
    void showToast(String msg);
    void finishActivity(ConfirmOrderBean.DataEntity.Errors errorData);
}
