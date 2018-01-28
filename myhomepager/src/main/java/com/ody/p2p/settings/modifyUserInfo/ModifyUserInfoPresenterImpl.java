package com.ody.p2p.settings.modifyUserInfo;

import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/23.
 */
public class ModifyUserInfoPresenterImpl implements ModifyUserInfoPresenter {

    private ModifyUserInfoView modifyUserInfoView;

    public ModifyUserInfoPresenterImpl(ModifyUserInfoView modifyUserInfoView){
        this.modifyUserInfoView = modifyUserInfoView;
    }

    /**
     * 试过了,目前没有这个接口,所以相关的设置数据什么的都还没做,且上传照片,更新用户信息的接口也还没写hardcode
     */
    @Override
    public void getUserInfo() {
        OkHttpManager.getAsyn(Constants.GET_USER_INFO, new OkHttpManager.ResultCallback<GetUserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(GetUserInfoBean response) {

            }
        });

//        ODYHttpClient.getInstance().get(OdyApplication.getUrl(Constants.GET_USER_INFO), GetUserInfoBean.class, new RequestBackListener() {
//            @Override
//            public void onSuccess(BaseRequestBean data) {
//                super.onSuccess(data);
//                GetUserInfoBean bean = (GetUserInfoBean) data;
//                if (bean != null && bean.data != null) {
//                    mModifyUserInfoView.getBirthdayUpdateCount(bean.data.birthdayUpdateCount);
//
//                    //头像
//                    if (!OdyUtil.isEmpty(bean.data.headPicUrl)) {
//                        mModifyUserInfoView.setUri(Uri.parse(bean.data.headPicUrl));
//                    }
////                    else {
////                        muiActivity.sdv_user_photo.setBackgroundResource(R.drawable.android_template);//默认
////                    }
//
//                    //用户名
//                    if (!OdyUtil.isEmpty(bean.data.username)) {
//                        mModifyUserInfoView.setNameText(bean.data.username);
//                    }
////                    else {
////                        muiActivity.tv_name_hint.setText("");
////                    }
//
//                    //生日
//                    if (!OdyUtil.isEmpty(bean.data.birthDay)) {
//                        mModifyUserInfoView.setBirthText(bean.data.birthDay);
//                    } else {
//                        mModifyUserInfoView.setBirthText("");
//                    }
//
//                    mModifyUserInfoView.setSexPhoto(bean.data.sex);
//                }
//            }
//        });
    }

    /**
     * 接口响应码为500
     * @param path
     */
    String filePath = null;
    @Override
    public void uploadFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.showShort("文件不存在");
            return;
        }
        try {
            OkHttpManager.postAsyn(Constants.UPLOAD_SINGLE_PHOTO, new OkHttpManager.ResultCallback<UploadBean>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(UploadBean response) {
                    if (response!=null && response.data!=null && !StringUtils.isEmpty(response.data.filePath)){
                        filePath = response.data.filePath;
                    }
                }
            }, file, "file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 诸葛是有统一的"提交"按钮来执行这个方法,但是月白说p2p的这个没有这样一个统一的按钮,是一个个分别提交的????????
     * @param userName
     * @param sex
     * @param birthDay
     */
    @Override
    public void updateUserInfo(String userName,String sex ,String birthDay) {
        Map<String,String> params = new HashMap<>();
        //这些参数是否要做非空判断再上传????????????
        params.put("headPicUrl",filePath);
        params.put("username", userName);
        params.put("sex", sex);
        params.put("birthDay", birthDay);

//        ODYHttpClient.getInstance().postByUrlencoded(OdyApplication.getUrl(Constants.UPDATE_USER_INFO), params, BaseRequestBean.class, new RequestBackListener() {
//            @Override
//            public void onSuccess(BaseRequestBean data) {
//                super.onSuccess(data);
//                OdyApplication.putValueByKey("isChange", true);
////                OdyUtil.showToast(muiActivity, params.toString());
//                mModifyUserInfoView.showToast(data.message);
//                mModifyUserInfoView.finishActivity();
//            }
//
//            @Override
//            public void onError(String msg) {
//                super.onError(msg);
//                mModifyUserInfoView.showToast(msg);
//            }
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                mModifyUserInfoView.showProgressDialog();
//            }
//
//            @Override
//            public void finish() {
//                super.finish();
//                mModifyUserInfoView.hideProgressDialog();
//            }
//        });
    }


}
