package com.lyfen.android.entity.network;

/**
 * <p> Created by qiujie on 2017/12/16/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class QrcodeEntity {

    /**
     * code : 0
     * message : 成功
     * data : {"st":"712161435388216671"}
     */

    public String code;
    public String message;
    public DataEntity data;

    public static class DataEntity {
        /**
         * st : 712161435388216671
         */
        public int status;
        public String st;
    }
}
