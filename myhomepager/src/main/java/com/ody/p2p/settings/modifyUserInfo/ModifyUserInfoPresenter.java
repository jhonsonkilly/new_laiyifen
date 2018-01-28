package com.ody.p2p.settings.modifyUserInfo;

/**
 * Created by ody on 2016/6/23.
 */
public interface ModifyUserInfoPresenter {

    //获取用户信息
    void getUserInfo();

    //上传照片---单张
    void uploadFile(String path);

    //更新个人资料
    void updateUserInfo(String userName, String sex, String birthDay);
}
