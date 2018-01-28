package com.netease.nim.uikit.session.module;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author SevenCheng
 *         收藏的Bean
 */

public class CollectModel implements Serializable {


    /**
     * height : 1147
     * width : 828
     * displayName : 图片发送于2017-11-16 16:06
     * extension : jpg
     * md5 : d3faff86f9acf02a2011ca47039c402e
     * size : 150359
     * url : https://nos.netease.com/nim/NDQyOTE0Ng==/bmltYV8xMDYyNjg2MzIwXzE1MTA3NTIwODY1NTZfYjk1YTFjOTUtNTQ0Yy00NjA4LTgzNjMtYzYyMDZkNjFhM2Ex
     */

    private int       height;
    private int       width;
    private String    displayName;
    private String    fromNick;
    private String    fromID;
    private String    extension;
    private String    md5;
    private int       size;
    private long      fromTime;
    private String    url;
    private String    formIcon;
    private String    path;
    private long      collect_time;
    private Bitmap mBitmap;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getFormIcon() {
        return formIcon;
    }

    public void setFormIcon(String formIcon) {
        this.formIcon = formIcon;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(long collect_time) {
        this.collect_time = collect_time;
    }

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public String getFromNick() {
        return fromNick;
    }

    public void setFromNick(String fromNick) {
        this.fromNick = fromNick;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
