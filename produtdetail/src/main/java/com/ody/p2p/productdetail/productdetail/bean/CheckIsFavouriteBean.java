package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ${tang} on 2017/4/27.
 */

public class CheckIsFavouriteBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : {"favoriteId":0,"isFavorite":0}
     */

    public Object errMsg;
    /**
     * favoriteId : 0
     * isFavorite : 0
     */

    public DataBean data;

    public static class DataBean {
        public String favoriteId;
        public int isFavorite;
    }
}
