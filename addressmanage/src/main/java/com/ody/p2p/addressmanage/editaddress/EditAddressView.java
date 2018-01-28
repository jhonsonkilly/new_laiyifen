package com.ody.p2p.addressmanage.editaddress;


import com.ody.p2p.addressmanage.bean.BaseRequestBean;
import com.ody.p2p.addressmanage.bean.SaveAddressBackBean;
import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.ErrorBean;
import com.ody.p2p.receiver.ConfirmOrderBean;

/**
 * Created by ody on 2016/6/16.
 */
public interface EditAddressView extends BaseView{
    void delete(BaseRequestBean response);
    void save(SaveAddressBackBean response);
//    void edit(BaseRequestBean response);
    void showToast(String msg);
    void finishActivity(ConfirmOrderBean.DataEntity.Errors errorData);
}
