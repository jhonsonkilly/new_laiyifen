package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/1.
 */
public class SearchHotWordBean extends BaseRequestBean {

    public HotWord data;

    public static class HotWord {
        public List<String> searchHotWordList;
    }
}
