package com.ody.p2p.entity;

import java.io.Serializable;

/**
 * Created by mac on 2017/11/30.
 */

public class YiModel implements Serializable {







    private int count = 0;

    private String date="";

    private String content;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
