package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/1.
 */
public class SearchHistoryBean extends BaseRequestBean {

    public SearchHistoryData data;

    public static class SearchHistoryData {
        /**
         * keyword : 出发
         * type : 2
         */

        public List<SearchHistoryList> searchHistoryList;

        public static class SearchHistoryList {
            public String keyword;
            public int type;

            @Override
            public String toString() {
                return keyword;
            }
        }


    }
}
