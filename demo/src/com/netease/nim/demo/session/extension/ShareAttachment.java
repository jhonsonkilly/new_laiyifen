package com.netease.nim.demo.session.extension;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class ShareAttachment extends CustomAttachment {


    private String value;

    public ShareAttachment(String value) {
        super(CustomAttachmentType.Share);
       this.value = value;
    }
    public ShareAttachment() {
        super(CustomAttachmentType.Share);
    }

    @Override
    protected void parseData(JSONObject data) {
        value = data.getString("Share");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("Share", value);
        return data;
    }


    public String getValue() {
        return value;
    }
}
