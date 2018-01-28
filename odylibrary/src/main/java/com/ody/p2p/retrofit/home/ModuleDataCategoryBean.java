package com.ody.p2p.retrofit.home;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/12/2.
 */
public class ModuleDataCategoryBean extends BaseRequestBean {

    public List<CategoryBean> data;

    public static class CategoryBean {
        public long categoryId;
        public String categoryName;
        public boolean choose;
        public String pictureUrl;
    }

}
