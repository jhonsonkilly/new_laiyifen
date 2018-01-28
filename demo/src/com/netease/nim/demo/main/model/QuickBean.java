package com.netease.nim.demo.main.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by jasmin on 2017/9/1.
 */

public class QuickBean implements MultiItemEntity {

    String title;
    String user;
    String type;
    String date;
    String content;
    int imgID;

    public QuickBean(String title, String content ,String date, String type) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public QuickBean(String title, String user, String date, int imgID) {
        this.title = title;
        this.user = user;
        this.date = date;
        this.imgID = imgID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public QuickBean(String title, int layoutID){
        this.title = title;
        imgID = layoutID;
    }
    public QuickBean(String title, String type, int layoutID){
        this.title = title;
        this.type = type;
        imgID = layoutID;
    }

    public QuickBean(String title, String type){
        this.title = title;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
