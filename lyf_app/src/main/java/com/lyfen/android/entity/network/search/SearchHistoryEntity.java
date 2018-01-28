package com.lyfen.android.entity.network.search;

import java.util.List;

/**
 * Created by qj on 2017/5/18.
 */

public class SearchHistoryEntity {

    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : {"searchHistoryList":[{"keyword":"啊哈哈","type":0}]}
     * trace : 83!$1#@18%&10!$,153537,62708950656478872850954
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<SearchHistoryListEntity> searchHistoryList;

        public static class SearchHistoryListEntity {
            /**
             * keyword : 啊哈哈
             * type : 0
             */

            public String keyword;
            public int type;
        }
    }
}
