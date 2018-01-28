package com.lyfen.android.entity.network.address;

import java.util.List;

/**
 * Created by qiujie on 2017/6/29.
 */

public class AddressUserEntity {

    /**
     * data : [{"currentPage":0,"itemsPerPage":0,"id":1034622,"userName":"邱先生","provinceId":14,"provinceCode":350000,"provinceName":"福建省","cityId":152,"cityCode":350100,"cityName":"福州市","regionId":1450,"regionCode":350102,"regionName":"鼓楼区","detailAddress":"华大街道华屏路屏东城3座1602","mobile":"15306973901","defaultIs":0,"userId":911971,"sex":null,"longitude":null,"latitude":null,"exactAddress":null,"companyId":null,"createTime":null,"identityCardNumber":null,"realName":"邱先生","certification":0,"ut":null,"startItem":0},{"currentPage":0,"itemsPerPage":0,"id":1027606,"userName":"邱洁","provinceId":10,"provinceCode":310000,"provinceName":"上海","cityId":110,"cityCode":310100,"cityName":"上海市","regionId":1142,"regionCode":310117,"regionName":"松江区","detailAddress":"来伊份青年大厦3楼","mobile":"15900500705","defaultIs":1,"userId":911971,"sex":null,"longitude":null,"latitude":null,"exactAddress":null,"companyId":null,"createTime":null,"identityCardNumber":null,"realName":"邱洁","certification":0,"ut":null,"startItem":0}]
     * code : 0
     */

    public String code;
    public List<DataEntity> data;

    public static class DataEntity  {
        /**
         * currentPage : 0
         * itemsPerPage : 0
         * id : 1034622
         * userName : 邱先生
         * provinceId : 14
         * provinceCode : 350000
         * provinceName : 福建省
         * cityId : 152
         * cityCode : 350100
         * cityName : 福州市
         * regionId : 1450
         * regionCode : 350102
         * regionName : 鼓楼区
         * detailAddress : 华大街道华屏路屏东城3座1602
         * mobile : 15306973901
         * defaultIs : 0
         * userId : 911971
         * sex : null
         * longitude : null
         * latitude : null
         * exactAddress : null
         * companyId : null
         * createTime : null
         * identityCardNumber : null
         * realName : 邱先生
         * certification : 0
         * ut : null
         * startItem : 0
         */

        public int currentPage;
        public int itemsPerPage;
        public int id;
        public String userName;
        public int provinceId;
        public int provinceCode;
        public String provinceName;
        public int cityId;
        public int cityCode;
        public String cityName;
        public int regionId;
        public int regionCode;
        public String regionName;
        public String detailAddress;
        public String mobile;
        public int defaultIs;
        public int userId;
        public String sex;
        public String longitude;
        public String latitude;
        public String exactAddress;
        public String companyId;
        public String createTime;
        public String identityCardNumber;
        public String realName;
        public int certification;
        public String ut;
        public int startItem;
    }
}
