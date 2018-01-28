package com.lyfen.android.entity.network.mine;

/**
 * <p> Created by qiujie on 2017/12/14/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class MyOrderEntity {

    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : {"unpay":0,"undelivery":0,"unreceive":3,"unEvaluate":0,"unType":0,"isAfter":0}
     * trace : 85!$1#@18%&10!$,197461,63468932766751990321844
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * unpay : 0
         * undelivery : 0
         * unreceive : 3
         * unEvaluate : 0
         * unType : 0
         * isAfter : 0
         */

        public int unpay;
        public int undelivery;
        public int unreceive;
        public int unEvaluate;
        public int unType;
        public int isAfter;
    }
}
