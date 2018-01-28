package com.lyfen.android.entity.network.login;

/**
 * Created by qiujie on 2017/6/29.
 */

public class UserInfoEntity {

    /**
     * message : 登录成功！
     * userAgent : Mozilla/5.0 (Linux; Android 7.0; Android SDK built for x86 Build/NYC; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.90 Mobile Safari/537.36--[{"sessionId":"de597bd1-518e-4b19-b612-e3b3e6420d9f","company":"ody","ut":"","appName":"ds"}]--
     * data : {"id":911971,"merchantCode":null,"username":"15900500705","mobile":"15900500705","originalId":null,"type":null,"companyId":30,"merchantId":null,"parentId":0,"memberId":null,"functionCodes":null,"functionPahts":null,"merchantIds":null,"manageMerchantDTOs":null,"identityType":null,"autoLogin":false,"nickname":"奋斗的小青春","headPicUrl":"http://cdn.oudianyun.com/lyf/prod/frontier-guide/1495607929131_7872_9695.jpg"}
     * isPwd : true
     * code : 0
     * ut : 72399d2e57cb4fe7033c14a51f6592f271
     * ip : 124.74.147.82, 10.18.2.198
     */

    public String message;
    public String userAgent;
    public DataEntity data;
    public boolean isPwd;
    public int code;
    public String ut;
    public String ip;

    public boolean checkImage;
    public static class DataEntity {
        /**
         * id : 911971
         * merchantCode : null
         * username : 15900500705
         * mobile : 15900500705
         * originalId : null
         * type : null
         * companyId : 30
         * merchantId : null
         * parentId : 0
         * memberId : null
         * functionCodes : null
         * functionPahts : null
         * merchantIds : null
         * manageMerchantDTOs : null
         * identityType : null
         * autoLogin : false
         * nickname : 奋斗的小青春
         * headPicUrl : http://cdn.oudianyun.com/lyf/prod/frontier-guide/1495607929131_7872_9695.jpg
         */

        public String id;
        public String merchantCode;
        public String username;
        public String mobile;
        public String originalId;
        public String type;
        public String companyId;
        public String merchantId;
        public String parentId;
        public String memberId;
        public String functionCodes;
        public String functionPahts;
        public String merchantIds;
        public String manageMerchantDTOs;
        public String identityType;
        public boolean autoLogin;
        public String nickname;
        public String headPicUrl;
    }
}
