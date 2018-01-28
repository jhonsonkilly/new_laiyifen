package com.ody.p2p.addressmanage.receiver;


import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.bean.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/16.
 */
public interface ReceiverView {
    void refreshAddresList(AddressBean list);
    void showToast(String msg);
    void deleteAddress(BaseRequestBean bean);
    void setDefaultAddress(BaseRequestBean bean);
}
