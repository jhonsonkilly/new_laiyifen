package com.netease.nim.demo.session.extension;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class DistinguishAttachment extends CustomAttachment {


    private String value;

    public DistinguishAttachment(String value) {
        super(CustomAttachmentType.Distinguish);
       this.value = value;
    }
    public DistinguishAttachment() {
        super(CustomAttachmentType.Distinguish);
    }

    @Override
    protected void parseData(JSONObject data) {
        value = data.getString("recommendGoods");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("recommendGoods", value);
        return data;
    }


    public String getValue() {
        return value;
    }
}
