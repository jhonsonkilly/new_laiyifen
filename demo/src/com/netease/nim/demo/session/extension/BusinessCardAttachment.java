package com.netease.nim.demo.session.extension;

import android.graphics.drawable.Drawable;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class BusinessCardAttachment extends CustomAttachment {



    private String bussinessCardUserId;

    public BusinessCardAttachment(String name) {
        super(CustomAttachmentType.BusinessCard);
        bussinessCardUserId = name;
    }



    @Override
    protected void parseData(JSONObject data) {
        bussinessCardUserId = data.getString("bussinessCardUserId");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("bussinessCardUserId", bussinessCardUserId);
        return data;
    }

    public String getBussinessCardUserId() {
        return bussinessCardUserId;
    }

    public void setBussinessCardUserId(String bussinessCardUserId) {
        this.bussinessCardUserId = bussinessCardUserId;
    }
}
