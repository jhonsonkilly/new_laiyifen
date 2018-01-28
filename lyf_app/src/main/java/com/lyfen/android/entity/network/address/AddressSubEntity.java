package com.lyfen.android.entity.network.address;

import java.util.List;

/**
 * Created by qj on 2017/6/22.
 */

public class AddressSubEntity {

    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : [{"code":340100,"name":"合肥市","id":136,"abbreviation":"H"},{"code":340200,"name":"芜湖市","id":137,"abbreviation":"W"},{"code":340300,"name":"蚌埠市","id":138,"abbreviation":"B"},{"code":340400,"name":"淮南市","id":139,"abbreviation":"H"},{"code":340500,"name":"马鞍山市","id":140,"abbreviation":"M"},{"code":340600,"name":"淮北市","id":141,"abbreviation":"H"},{"code":340700,"name":"铜陵市","id":142,"abbreviation":"T"},{"code":340800,"name":"安庆市","id":143,"abbreviation":"A"},{"code":341000,"name":"黄山市","id":144,"abbreviation":"H"},{"code":341100,"name":"滁州市","id":145,"abbreviation":"C"},{"code":341200,"name":"阜阳市","id":146,"abbreviation":"F"},{"code":341300,"name":"宿州市","id":147,"abbreviation":"S"},{"code":341500,"name":"六安市","id":148,"abbreviation":"L"},{"code":341600,"name":"亳州市","id":149,"abbreviation":"Z"},{"code":341700,"name":"池州市","id":150,"abbreviation":"C"},{"code":341800,"name":"宣城市","id":151,"abbreviation":"X"}]
     * trace : 82!$1#@18%&10!$,153537,62835691860735307421082
     */

    public String code;
    public String message;
    public String errMsg;
    public String trace;
    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * code : 340100
         * name : 合肥市
         * id : 136
         * abbreviation : H
         */

        public String code;
        public String name;
        public int id;
        public String abbreviation;
        public boolean expend;
    }
}
