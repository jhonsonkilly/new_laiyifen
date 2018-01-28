package com.ody.p2p.settings.modifyUserInfo;

import com.ody.p2p.base.BaseRequestBean;

/**
 * 获取用户信息
 */
public class GetUserInfoBean extends BaseRequestBean {


    /**
     * headPicUrl : http://logo
     * username : 诸葛修车户
     * mobile : 13121033021
     * birthDay : 1992-01-01
     * sex : 1
     */

    public Data data;

    public class Data {
        public String headPicUrl;
        public String username;
        public String mobile;
        public String birthDay;
        public int sex;
        public int birthdayUpdateCount;
    }
}
