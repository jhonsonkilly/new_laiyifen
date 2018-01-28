package com.ody.p2p.retrofit.category;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class CategoryBean extends BaseRequestBean {
    public Data data;

    public static class Data {
        public List<Category> categorys;
    }
}
