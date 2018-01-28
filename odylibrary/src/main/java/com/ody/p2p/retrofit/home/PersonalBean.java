package com.ody.p2p.retrofit.home;

import java.io.Serializable;

/**
 * Created by mac on 2017/11/21.
 */

public class PersonalBean implements Serializable{
    public String code;
    public String message;
    public String errMsg;
    public PersonalData data;

    public static class PersonalData implements Serializable {
        public String mobile;
        public String headPicUrl;
        public String sex;
        public String id;
        public String nickname;


    }
}
