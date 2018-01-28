package com.lyfen.android.entity.network;

/**
 * Created by qj on 2017/4/27.
 */

public class AlibcEntity {

    /**
     * message : 成功
     * data : {"userId":"15900500705","appKey":"23310446","receiveId":"来伊份小伊","password":"111111"}
     * code : 0
     */

    public String message;
    public DataBean data;
    public String code;

    @Override
    public String toString() {
        return "AlibcEntity{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * userId : 15900500705
         * appKey : 23310446
         * receiveId : 来伊份小伊
         * password : 111111
         */

        public String userId;
        public String appKey;
        public String receiveId;
        public String password;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userId='" + userId + '\'' +
                    ", appKey='" + appKey + '\'' +
                    ", receiveId='" + receiveId + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
