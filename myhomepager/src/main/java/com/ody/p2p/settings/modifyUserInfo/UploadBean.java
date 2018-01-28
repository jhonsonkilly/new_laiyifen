package com.ody.p2p.settings.modifyUserInfo;

import com.ody.p2p.base.BaseRequestBean;

/**
 * 上传单张照片
 */
public class UploadBean extends BaseRequestBean {

    public Data data;

    public class Data {
        public String fileName;
        public String filePath;
    }
}
