package com.netease.nim.uikit.session.module;

/**
 * @author SevenCheng
 */

public class MsgModel {

    /**
     * path : /storage/emulated/0/com.netease.nim.demo/nim/image/6edc4073742be0a2e66da0e931d896b0.jpg
     * md5 : 6edc4073742be0a2e66da0e931d896b0
     * name : temp_image_46154c3c-10ec-4b01-94af-c083fb74723e.jpg
     * url : https://nos.netease.com/nim/NDQyOTE0Ng==/bmltYV8xMDQ2MDE4NjUyXzE1MTE5MTg1MTc5ODFfYjE0YTJjOTUtMTQ4MS00NjE3LTk5MWUtMGI1MjU4ZTJlYjg5
     * size : 25805
     * ext : jpg
     * w : 540
     * h : 960
     */

    private String path;
    private String md5;
    private String name;
    private String url;
    private int    size;
    private String ext;
    private int    w;
    private int    h;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
