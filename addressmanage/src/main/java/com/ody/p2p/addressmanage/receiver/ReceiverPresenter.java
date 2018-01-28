package com.ody.p2p.addressmanage.receiver;

/**
 * Created by ody on 2016/6/16.
 */
public interface ReceiverPresenter {
    void getAddressListByNet();
    void deleteAddressByNet(int defaultIs, String id);
    void setDefaultAddressByNet(String id, String userName, String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault);

}
