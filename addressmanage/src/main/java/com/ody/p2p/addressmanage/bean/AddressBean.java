package com.ody.p2p.addressmanage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class AddressBean implements Serializable {
    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }

    /**
     * 收货地址bean
     */
    private static final long serialVersionUID = 1L;
    private List<Address> data;

    public class Address implements Serializable {

        private static final long serialVersionUID = 1L;
        private String id;
        private String userId;
        private String userName;
        private String provinceCode;

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        private String identityCardNumber;
        private String cityId;
        private String regionId;
        private String detailAddress;
        private String mobile;
        private int defaultIs;// 1--默认
        private long createTime;
        private long updateTime;
        private long deleteTime;
        private String createUserId;
        private String updateUserId;
        private String deleteUserId;
        private int deletes;
        private String provinceName;
        private String cityName;
        private String regionName;
        private String regionCode;


        public String getIdentityCardNumber() {
            return identityCardNumber;
        }

        public void setIdentityCardNumber(String identityCardNumber) {
            this.identityCardNumber = identityCardNumber;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getDefaultIs() {
            return defaultIs;
        }

        public void setDefaultIs(int defaultIs) {
            this.defaultIs = defaultIs;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(long deleteTime) {
            this.deleteTime = deleteTime;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getDeleteUserId() {
            return deleteUserId;
        }

        public void setDeleteUserId(String deleteUserId) {
            this.deleteUserId = deleteUserId;
        }

        public int getDeletes() {
            return deletes;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }
    }
}
