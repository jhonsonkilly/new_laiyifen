package com.ody.p2p.search.searchresult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/6/12.
 */
public class SearchResultProductListBean {

    public String code;
    public String message;
    public List<SearchResultProductBean> wareInfo;

    public class SearchResultProductBean{
        public String spuId;
        public String imageurl;
        public String totalCount;
        public String good;
        public String jdPrice;
        public boolean self;
        public String wname;
        public List<String> tagList = new ArrayList<>();
    }

}
