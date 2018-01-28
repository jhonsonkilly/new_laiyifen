package com.netease.nim.demo.session.extension;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.yidiancard.model.YKD001Model;
import com.netease.nim.demo.session.model.Type12Model;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class YiDianCardAttachment extends CustomAttachment {


    private YKD001Model.RowsBean bean;
    String content;
    String beanJson;

    public String getBeanJson() {
        return beanJson;
    }

    public void setBeanJson(String beanJson) {
        this.beanJson = beanJson;
    }

    public YiDianCardAttachment(YKD001Model.RowsBean  content) {
        super(CustomAttachmentType.YiDianCard);
        this.bean = content;
        this.content =new Gson().toJson(bean);
    }

    public YiDianCardAttachment(String  content) {
        super(CustomAttachmentType.YiDianCard);
        this.beanJson = content;
    }

    public YiDianCardAttachment() {
        super(CustomAttachmentType.YiDianCard);
    }

    @Override
    protected void parseData(JSONObject data) {
        content = data.toJSONString();
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        try {

//            Type12Model model = new Type12Model();

//            model.setYidou(bean);
//            String json = new Gson().toJson(model);
//            data = new JSONObject(). ;
            if (beanJson!=null) {
                data.put("yidou", beanJson);
            }else {
                data.put("yidou", new Gson().toJson(bean));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getContent() {
        return content;
    }

    public YKD001Model.RowsBean getBean() {
        return bean;
    }
}

