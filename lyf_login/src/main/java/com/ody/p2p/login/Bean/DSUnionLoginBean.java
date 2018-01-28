package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/8/23.
 * 德升 联合登录请求成功返回的bean
 */
public class DSUnionLoginBean extends BaseRequestBean {

    /**
     * id : 1228
     * merchantCode : null
     * username : 851795
     * mobile : null
     * originalId : null
     * type : 4
     * companyId : 1001
     * merchantId : null
     * parentId : 0
     * memberId : null
     * functionCodes : null
     * functionPahts : null
     * merchantIds : null
     * manageMerchantDTOs : null
     * identityType : null
     * autoLogin : false
     * nickname : null
     * headPicUrl : null
     */

    public Data data;
    /**
     * data : {"id":1228,"merchantCode":null,"username":"851795","mobile":null,"originalId":null,"type":4,"companyId":1001,"merchantId":null,"parentId":0,"memberId":null,"functionCodes":null,"functionPahts":null,"merchantIds":null,"manageMerchantDTOs":null,"identityType":null,"autoLogin":false,"nickname":null,"headPicUrl":null}
     * ut : 00eab44f69384ac7b1f669739b531672
     * isExists : 1
     */

    public String ut;
    public String isExists;

    public static class Data {
        public long id;
        public String merchantCode;
        public String username;
        public String mobile;
        public String originalId;
        public int type;
        public boolean autoLogin;
        public String nickname;
        public String headPicUrl;
    }
}
