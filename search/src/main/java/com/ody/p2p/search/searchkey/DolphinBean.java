package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * 初始化搜索界面  搜索框内默认搜索词  主要就用到searchword这个list...
 */
public class DolphinBean extends BaseRequestBean {
    public Data data;

    public class Data {
        public List<SearchWord> searchword;
    }

    public static class SearchWord{

        /**
         * id : 32
         * name : 草莓
         * startTime : null
         * endTime : null
         * sort : 1
         * type : 0
         * title : 草莓
         * content : 草莓
         * refType : 1
         * refId : 392
         * linkUrl : blbl://h5?body=%7B%22url%22%3A%22http%3A%2F%2Ftestfront.cms.odianyun.com%2Fview%2Fh5%2F392.html%22%7D
         * imageUrl : null
         * imageTitle : null
         * refObject : null
         */

        public int id;
        public String name;
        public Object startTime;
        public Object endTime;
        public int sort;
        public int type;
        public String title;
        public String content;
        public int refType;
        public int refId;
        public String linkUrl;
        public Object imageUrl;
        public Object imageTitle;
        public Object refObject;
    }

}
