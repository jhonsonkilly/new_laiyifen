package com.ody.p2p.addressmanage.selectaddressactivity;

/**
 * Created by ody on 2016/6/17.
 */
public interface SelectAddressPresenter {
    void getAddressList();
    void saveAddress(String addressId);
    void setDefault(String id, String userName, String provinceId, String cityId, String regionId, String detailAddress, String telNum, int isDefault);
    void deleteAddress(int defaultIs, String id);
}
