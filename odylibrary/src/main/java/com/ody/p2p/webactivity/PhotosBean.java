package com.ody.p2p.webactivity;


import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/7/4.
 */
public class PhotosBean extends BaseRequestBean {

    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private String filePath;

        private String fileName;

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return this.filePath;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return this.fileName;
        }

    }
}
