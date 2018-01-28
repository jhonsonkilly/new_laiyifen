package com.ody.p2p;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/6/17.
 */
public class UserInfoBean extends BaseRequestBean {
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String id;

        private int type;

        private String username;

        private String nickname;

        private String companyName;

        private String contactPerson;

        private String mobile;

        private String telephone;

        private String email;

        private String legalPersonName;

        private String legalPersonnoNumber;

        private String organizationCode;

        private String businessLicenseNumber;

        private String businessLicenseUrl;

        private String companyType;

        private String companyTypeStr;

        private String businessScope;

        private String businessStartStr;

        private String businessStart;

        private String businessEndStr;

        private String businessEnd;

        private String registeredAddress;

        private String registeredCapital;

        private String population;

        private String populcationStr;

        private int companyId;

        private int isCompany;

        private String identityCardName;

        private int bindQQStatus;

        private int bindWechatStatus;

        private String remarks;

        private String sexName;

        private String sex;

        private String birthdayStr;

        private String birthday;

        private String userProvince;

        private String userCity;

        private String userRegion;

        private String addressDetail;

        private String userAddress;

        private String createTimeStr;

        private long createTime;

        private String headPicUrl;

        private String distributorId;

        private int isDistributor;//是否是分销商

        /**
         * @return 是否是分销商
         */
        public boolean isDistributor() {
            return isDistributor == 1;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return this.nickname;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyName() {
            return this.companyName;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getContactPerson() {
            return this.contactPerson;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getTelephone() {
            return this.telephone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }

        public String getLegalPersonName() {
            return this.legalPersonName;
        }

        public void setLegalPersonnoNumber(String legalPersonnoNumber) {
            this.legalPersonnoNumber = legalPersonnoNumber;
        }

        public String getLegalPersonnoNumber() {
            return this.legalPersonnoNumber;
        }

        public void setOrganizationCode(String organizationCode) {
            this.organizationCode = organizationCode;
        }

        public String getOrganizationCode() {
            return this.organizationCode;
        }

        public void setBusinessLicenseNumber(String businessLicenseNumber) {
            this.businessLicenseNumber = businessLicenseNumber;
        }

        public String getBusinessLicenseNumber() {
            return this.businessLicenseNumber;
        }

        public void setBusinessLicenseUrl(String businessLicenseUrl) {
            this.businessLicenseUrl = businessLicenseUrl;
        }

        public String getBusinessLicenseUrl() {
            return this.businessLicenseUrl;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getCompanyType() {
            return this.companyType;
        }

        public void setCompanyTypeStr(String companyTypeStr) {
            this.companyTypeStr = companyTypeStr;
        }

        public String getCompanyTypeStr() {
            return this.companyTypeStr;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getBusinessScope() {
            return this.businessScope;
        }

        public void setBusinessStartStr(String businessStartStr) {
            this.businessStartStr = businessStartStr;
        }

        public String getBusinessStartStr() {
            return this.businessStartStr;
        }

        public void setBusinessStart(String businessStart) {
            this.businessStart = businessStart;
        }

        public String getBusinessStart() {
            return this.businessStart;
        }

        public void setBusinessEndStr(String businessEndStr) {
            this.businessEndStr = businessEndStr;
        }

        public String getBusinessEndStr() {
            return this.businessEndStr;
        }

        public void setBusinessEnd(String businessEnd) {
            this.businessEnd = businessEnd;
        }

        public String getBusinessEnd() {
            return this.businessEnd;
        }

        public void setRegisteredAddress(String registeredAddress) {
            this.registeredAddress = registeredAddress;
        }

        public String getRegisteredAddress() {
            return this.registeredAddress;
        }

        public void setRegisteredCapital(String registeredCapital) {
            this.registeredCapital = registeredCapital;
        }

        public String getRegisteredCapital() {
            return this.registeredCapital;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        public String getPopulation() {
            return this.population;
        }

        public void setPopulcationStr(String populcationStr) {
            this.populcationStr = populcationStr;
        }

        public String getPopulcationStr() {
            return this.populcationStr;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getCompanyId() {
            return this.companyId;
        }

        public void setIsCompany(int isCompany) {
            this.isCompany = isCompany;
        }

        public int getIsCompany() {
            return this.isCompany;
        }

        public void setIdentityCardName(String identityCardName) {
            this.identityCardName = identityCardName;
        }

        public String getIdentityCardName() {
            return this.identityCardName;
        }

        public void setBindQQStatus(int bindQQStatus) {
            this.bindQQStatus = bindQQStatus;
        }

        public int getBindQQStatus() {
            return this.bindQQStatus;
        }

        public void setBindWechatStatus(int bindWechatStatus) {
            this.bindWechatStatus = bindWechatStatus;
        }

        public int getBindWechatStatus() {
            return this.bindWechatStatus;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getRemarks() {
            return this.remarks;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName;
        }

        public String getSexName() {
            return this.sexName;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return this.sex;
        }

        public void setBirthdayStr(String birthdayStr) {
            this.birthdayStr = birthdayStr;
        }

        public String getBirthdayStr() {
            return this.birthdayStr;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday() {
            return this.birthday;
        }

        public void setUserProvince(String userProvince) {
            this.userProvince = userProvince;
        }

        public String getUserProvince() {
            return this.userProvince;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserCity() {
            return this.userCity;
        }

        public void setUserRegion(String userRegion) {
            this.userRegion = userRegion;
        }

        public String getUserRegion() {
            return this.userRegion;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getAddressDetail() {
            return this.addressDetail;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserAddress() {
            return this.userAddress;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getCreateTimeStr() {
            return this.createTimeStr;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getCreateTime() {
            return this.createTime;
        }

        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }

        public String getHeadPicUrl() {
            return this.headPicUrl;
        }

        public void setDistributorId(String distributorId) {
            this.distributorId = distributorId;
        }

        public String getDistributorId() {
            return this.distributorId;
        }

        public void setIsDistributor(int isDistributor) {
            this.isDistributor = isDistributor;
        }

        public int getIsDistributor() {
            return this.isDistributor;
        }

    }
}
