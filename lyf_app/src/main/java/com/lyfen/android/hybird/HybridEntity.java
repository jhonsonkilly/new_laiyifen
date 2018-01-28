package com.lyfen.android.hybird;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qj on 2017/5/24.
 */

public class HybridEntity implements Parcelable {

    public static final String FUNCTION_CLEAR_CACHE = "clearCache";
    public static final String FUNCTION_GET_CACHE = "getCache";
    public static final String FUNCTION_SHARE = "share";
    public static final String ADDRESS_MANAGER = "addressManager";
    public static final String FUNCTION_GET_CAMERA = "getCamera";
    public static final String FUNCTION_GET_LOCATION = "getLocation";
    public static final String FUNCTION_GET_VERSION_NAME = "getVersionName";
    public static final String FUNCTION_PAGE_REFRESH = "pageRefresh";
    public static final String FUNCTION_UPLOAD_PHOTO = "uploadPhoto";
    public static final String FUNCTION_LOGIN = "login";
    public static final String FUNCTION_LOGOUT = "logout";
    public static final String FUNCTION_HIDE_TITLE = "hiddenHead";
    public static final String FUNCTION_BACK = "webViewBack";
    public static final String FUNCTION_NAVIGATE = "navigate";
    public static final String FUNCTION_GET_MESSAGE_INFO = "getMessageInfo";
    public static final String FUNCTION_CALL_CUSTOMS_SERVICE = "callcustomservice";

    public static final String FUNCTION_CALL_CMS_SHARE = "cmsShare";

    public static final String FUNCTION_BINDTHIRD_QQ = "bindThirdPlatformQQ";
    public static final String FUNCTION_BINDTHIRD_WX = "bindThirdPlatformWX";
    public static final String FUNCTION_SET_ALARM = "setAlarm";
    public static final String FUNCTION_CANCEL_ALARM = "cancelAlarm";
    public static final String FUNCTION_STATUS_ALARM = "alarmState";
    public static final String FUNCTION_RED_PACKAGE = "goredPod";
    public static final String FUNCTION_STATUS_AREA_CODE = "getAreaCode";

    public static final String FUNCTION_GO_BACK_HOME = "goBackHome";
    public static final String GET_WEB_COLOEL = "GET_WEB_COLOEL";

    public static final String TO_SHOPCART = "shoppingCar";//确定没有T，不要怀疑

    public static final String ADD_CART_SUCCESS = "addCartSuccess";//确定没有T，不要怀疑



    @Override
    public String toString() {
        return "HybridEntity{" +
                "callback='" + callback + '\'' +
                ", function='" + function + '\'' +
                ", param=" + param +
                '}';
    }

    /**
     * callback : a
     * function : getLocation
     * param : test
     */

    public String callback;
    public String function;
    public String params;
    public UrlEntity param;//js 的参数
    public int tag;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.callback);
        dest.writeString(this.function);
        dest.writeString(this.params);
        dest.writeParcelable(this.param, flags);
    }

    public HybridEntity() {
    }


    protected HybridEntity(Parcel in) {
        this.callback = in.readString();
        this.function = in.readString();
        this.params = in.readString();
        this.param = in.readParcelable(UrlEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<HybridEntity> CREATOR = new Parcelable.Creator<HybridEntity>() {
        @Override
        public HybridEntity createFromParcel(Parcel source) {
            return new HybridEntity(source);
        }

        @Override
        public HybridEntity[] newArray(int size) {
            return new HybridEntity[size];
        }
    };
}
