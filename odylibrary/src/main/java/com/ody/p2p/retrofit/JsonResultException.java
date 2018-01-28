package com.ody.p2p.retrofit;

/**
 * Created by pzy on 2017/7/20.
 */
public class JsonResultException extends RuntimeException {

    private String json = "";

    public JsonResultException(String json) {
        super(json);
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}