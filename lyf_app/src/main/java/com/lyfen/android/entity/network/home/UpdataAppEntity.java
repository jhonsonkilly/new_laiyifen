package com.lyfen.android.entity.network.home;

/**
 * <p> Created by qiujie on 2017/12/5/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class UpdataAppEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"appName":"com.umaman.laiyifen","versionCode":"5.1.0","describe":null,"versionName":null,"updateType":1,"packageSize":"23MB","obtainUrl":"http://cdn.oudianyun.com/lyf/prod/social/1507717103775_2476_26177.apk","updateFlag":1,"platformType":0}
     * trace : 82!$1#@18%&10!$,199378,63437555311088319023723
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * appName : com.umaman.laiyifen
         * versionCode : 5.1.0
         * describe : null
         * versionName : null
         * updateType : 1
         * packageSize : 23MB
         * obtainUrl : http://cdn.oudianyun.com/lyf/prod/social/1507717103775_2476_26177.apk
         * updateFlag : 1
         * platformType : 0
         */

        public String appName;
        public String versionCode;
        public String describe;
        public String versionName;
        public int updateType;
        public String packageSize;
        public String obtainUrl;
        public int updateFlag;
        public int platformType;
    }
}
