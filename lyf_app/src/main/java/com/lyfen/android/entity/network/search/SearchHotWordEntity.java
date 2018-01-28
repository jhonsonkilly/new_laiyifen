package com.lyfen.android.entity.network.search;

import java.util.List;

/**
 * Created by qj on 2017/5/18.
 */

public class SearchHotWordEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"hotword":[{"id":1014021400000020,"name":"热搜词1","startTime":1488453389000,"endTime":1522494989000,"sort":0,"type":0,"title":"鸭肫","content":"鸭肫","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E9%B8%AD%E8%82%AB%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021400000022,"name":"核桃","startTime":1488453406000,"endTime":1522495006000,"sort":0,"type":0,"title":"核桃","content":"核桃","refType":11,"refId":1007022000000035,"linkUrl":"lyf://searchresult?body=%7B%22navCategoryIds%22%3A%221007022000000035%22%2C%22navCategoryNames%22%3A%22%E6%A0%B8%E6%A1%83%E7%B1%BB%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":true},{"id":1014021800000009,"name":"天天坚果","startTime":1488792751000,"endTime":1522488751000,"sort":0,"type":0,"title":"天天坚果","content":"天天坚果","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E5%A4%A9%E5%A4%A9%E5%9D%9A%E6%9E%9C%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021800000011,"name":"猪肉脯","startTime":1488792811000,"endTime":1526722411000,"sort":0,"type":0,"title":"猪肉脯","content":"猪肉脯","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E7%8C%AA%E8%82%89%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021800000013,"name":"迪士尼","startTime":1488792971000,"endTime":1522488971000,"sort":0,"type":0,"title":"迪士尼","content":"迪士尼","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E8%BF%AA%E5%A3%AB%E5%B0%BC%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021800000017,"name":"鸭舌","startTime":1488793143000,"endTime":1522489143000,"sort":0,"type":0,"title":"鸭舌","content":"鸭舌","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E9%B8%AD%E8%88%8C%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021800000026,"name":"蛋糕","startTime":1488793543000,"endTime":1522489543000,"sort":0,"type":0,"title":"蛋糕","content":"蛋糕","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E8%9B%8B%E7%B3%95%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false},{"id":1014021800000019,"name":"1号金芒芒果肉","startTime":1488793190000,"endTime":1522489190000,"sort":0,"type":0,"title":"芒果","content":"芒果","refType":6,"refId":null,"linkUrl":"lyf://searchresult?body=%7B%22keyword%22%3A%22%E8%8A%92%E6%9E%9C%22%7D","imageUrl":null,"imageTitle":null,"refString":null,"goods":false}]}
     * trace : 41!$1#@18%&10!$,153537,62708926937310785711729
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<HotwordEntity> hotword;

        public static class HotwordEntity {
            /**
             * id : 1014021400000020
             * name : 热搜词1
             * startTime : 1488453389000
             * endTime : 1522494989000
             * sort : 0
             * type : 0
             * title : 鸭肫
             * content : 鸭肫
             * refType : 6
             * refId : null
             * linkUrl : lyf://searchresult?body=%7B%22keyword%22%3A%22%E9%B8%AD%E8%82%AB%22%7D
             * imageUrl : null
             * imageTitle : null
             * refString : null
             * goods : false
             */

            public long id;
            public String name;
            public long startTime;
            public long endTime;
            public int sort;
            public int type;
            public String title;
            public String content;
            public int refType;
            public String refId;
            public String linkUrl;
            public String imageUrl;
            public String imageTitle;
            public String refString;
            public boolean goods;
        }
    }
}
