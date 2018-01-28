package com.lyfen.android;

/**
 * 作者：qiujie on 16/6/10
 * 邮箱：qiujie@laiyifen.com
 */
public class PrefrenceKey {
    public static boolean ONLINE = false;//环境切换  false  支持切换
    public static final int JUMP_DETAIL = 1;

    public static String SHOW_NEWUSER_VERSION="SHOW_NEWUSER_VERSION" ;
    public static String EVE_CHANGE_READ_CACHE="EVE_CHANGE_READ_CACHE" ;
    public static String JAVA_HOST="JAVA_HOST";
    public static String H5_HOST="H5_HOST";
    public static final String SHOW_GUIDE_VERSION = "show_guide_version";// 轮播图版本
    public static final String SP_ID = "sp_id";
    public static final String SEARCHWORD = "searchword";


    //登录时的ut
    public static final String USER_LOGIN_UT = "token";
    //用户头像
    public static final String HEAD_PIC_URL = "headPicUrl";
    //昵称
    public static final String NICK_NAME = "nickname";
    public static final String DISTRIBUTOR_ID = "distributorId";
    //是否登录的状态值
    public static final String LOGIN_STATE = "loginState";
    public static final String USER_INFO= "user_info";
    // 用户相关
    public static final String LOGIN_MOBILE_PHONE = "loginPhone";




    //阿里百川
    public static final String APP_KEY = "app_key";
    public static final String BC_USER_ID = "bc_userId";
    public static final String RECEIVER_ID = "bc_receiveId";
    public static final String BC_PASS = "bc_password";




    public static final String AREA_CODE = "areaCode";
    public static final String AREA_CODE_ADDRESS = "areaCode_address";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String AREA_NAME = "area_name";


    //分类跳转
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ID = "category_id";


    public static int CODETIME=60000;
}
