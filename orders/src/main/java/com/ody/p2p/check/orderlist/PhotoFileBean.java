package com.ody.p2p.check.orderlist;

import java.io.Serializable;

/**
 * Created by ody on 2016/8/29.
 */
public class PhotoFileBean implements Serializable {
    private static final long serialVersionUID = 2L;
    private String filePath;
    private int state;//0 未上传，1，上传中，2，上传成功，3，上传失败

    private String internetPath ;

    public String getInternetPath() {
        return internetPath;
    }

    public void setInternetPath(String internetPath) {
        this.internetPath = internetPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
