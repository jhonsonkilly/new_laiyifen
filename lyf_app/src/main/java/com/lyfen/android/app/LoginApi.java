package com.lyfen.android.app;

import com.lyfen.android.entity.network.CommonEntity;
import com.lyfen.android.entity.network.login.UserInfoEntity;
import com.lyfen.android.entity.network.login.UserProEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qiujie on 2017/6/29.
 */

public interface LoginApi {

    //校验手机号是否已经注册
    @FormUrlEncoded
    @POST("/ouser-web/mobileRegister/isRepeatPhoneForm.do")
    Observable<CommonEntity> isRepeatPhoneForm(@FieldMap Map<String, String> map);


    //获取验证码
    @FormUrlEncoded
    @POST("ouser-web/mobileRegister/sendCaptchasCodeForm.do")
    Observable<CommonEntity> sendCaptchasCodeForm(@FieldMap Map<String, String> map);


    //登陆
    @FormUrlEncoded
    @POST("/ouser-web/mobileLogin/loginForm.do")
    Observable<UserInfoEntity> mobileLogin(@FieldMap Map<String, String> map);


    // 图形验证码
    @FormUrlEncoded
    @POST("/ouser-web/mobileRegister/checkImageForm.do")
    Observable<CommonEntity> checkImageForm(@FieldMap Map<String, String> map);


    //用户协议
    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Observable<UserProEntity> getArgment(@Query("pageCode") String pageeCode,
                                         @Query("adCode") String adCode,
                                         @Query("areaCode") String areaCode,
                                         @Query("sessionId") String sessionId);

    //    注册下一步
    @FormUrlEncoded
    @POST("/ouser-web/memberRegisterForm.do")
    Observable<UserProEntity> memberRegister(@FieldMap Map<String, String> map);


//    Map<String, String> params = new HashMap<>();
//        params.put("adCode", adCode);
//        params.put("platform", "3");
//        params.put("pageCode", "APP_HOME");
//        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, view.getNetTAG(), new OkHttpManager.ResultCallback<FuncBean>() {
//        @Override
//        public void onError(Request request, Exception e) {
//
//        }
//
//        @Override
//        public void onFailed(String code, String msg) {
//            super.onFailed(code, msg);
//        }
//
//        @Override
//        public void onResponse(FuncBean response) {
//            if (response != null) {
//                view.initTanPin(response);
//            }
//        }
//    });




}
