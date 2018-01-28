package com.ody.p2p.addressmanage.editaddress;

/**
 * Created by ody on 2016/6/16.
 */
public interface EditAddressPresenter {
    void saveAddress(String userName, String provinceId, String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault,String certificationCard);
    void editAddress(String id, String userName, String provinceId, String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault,String certificationCard);
    void deleteAddress(int defaultIs, String id);
    void confirmordersave(String addressId);
}
