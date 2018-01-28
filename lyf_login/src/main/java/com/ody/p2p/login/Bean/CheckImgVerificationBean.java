package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * 检验获取短信验证码之前是否需要验证图形码
 */
public class CheckImgVerificationBean extends BaseRequestBean {




    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String image; //图形验证码 base64
        private String imageKey; //图形验证码key
        private boolean needImgCaptcha; //是否需要验证图形验证码
        private String imageCode; //用户输入的图形验证码

        public String getImageCode() {
            return imageCode;
        }

        public DataBean setImageCode(String imageCode) {
            this.imageCode = imageCode;
            return this;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageKey() {
            return imageKey;
        }

        public void setImageKey(String imageKey) {
            this.imageKey = imageKey;
        }

        public boolean isNeedImgCaptcha() {
            return needImgCaptcha;
        }

        public void setNeedImgCaptcha(boolean needImgCaptcha) {
            this.needImgCaptcha = needImgCaptcha;
        }
    }
}
