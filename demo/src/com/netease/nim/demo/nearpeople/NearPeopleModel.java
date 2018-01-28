package com.netease.nim.demo.nearpeople;

import java.util.List;

/**
 * @author SevenCheng
 */

public class NearPeopleModel {


    /**
     * status : 200
     * message : null
     * data : [{"userId":"10007798","avatar":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1512031843385_3525_36.jpg","userName":"伊客11082835","distance":"0米"}]
     */

    private int            status;
    private Object         message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 10007798
         * avatar : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1512031843385_3525_36.jpg
         * userName : 伊客11082835
         * distance : 0米
         */

        private String userId;
        private String avatar;
        private String userName;
        private String distance;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
