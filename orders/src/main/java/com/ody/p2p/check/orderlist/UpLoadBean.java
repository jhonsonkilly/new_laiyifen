package com.ody.p2p.check.orderlist;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/8/29.
 */
public class UpLoadBean extends BaseRequestBean {
    private UpLoadData data;

    public UpLoadData getData() {
        return data;
    }

    public void setData(UpLoadData data) {
        this.data = data;
    }

    public class UpLoadData {
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
