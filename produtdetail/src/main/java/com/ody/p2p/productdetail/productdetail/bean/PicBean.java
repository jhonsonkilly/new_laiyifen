package com.ody.p2p.productdetail.productdetail.bean;

import java.io.Serializable;

/**
 * Created by pzy on 2016/12/8.
 */

public class PicBean implements Serializable{
    String picurl;
    ProductComment.Data.MpcList.ListObj listObj;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public ProductComment.Data.MpcList.ListObj getListObj() {
        return listObj;
    }

    public void setListObj(ProductComment.Data.MpcList.ListObj listObj) {
        this.listObj = listObj;
    }
}
