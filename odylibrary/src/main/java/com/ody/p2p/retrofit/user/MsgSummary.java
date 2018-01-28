package com.ody.p2p.retrofit.user;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class MsgSummary extends BaseRequestBean {

    /**
     * code : 0
     * message : null
     * data : [{"messageType":1,"unReadMsgCount":0,"msgTitle":null,"msgContent":null,"coverUrl":null,"h5Url":null,"appUrl":null,"iconUrl":"http://cdn.oudianyun.com/lyf/branch/frontier-guide/1484728323583_4825_187.png","latestTime":null,"latestTimeStr":null,"labelName":"系统通知"},{"messageType":2,"unReadMsgCount":1,"msgTitle":"熏衣草123","msgContent":"测试123","coverUrl":null,"h5Url":null,"appUrl":null,"iconUrl":"http://cdn.oudianyun.com/lyf/branch/frontier-guide/1484728349426_5762_180.png","latestTime":1487041873000,"latestTimeStr":"昨天 11:11","labelName":"活动消息"}]
     */
    public List<DataBean> data;

    public static class DataBean {
        /**
         * messageType : 1
         * unReadMsgCount : 0
         * msgTitle : null
         * msgContent : null
         * coverUrl : null
         * h5Url : null
         * appUrl : null
         * iconUrl : http://cdn.oudianyun.com/lyf/branch/frontier-guide/1484728323583_4825_187.png
         * latestTime : null
         * latestTimeStr : null
         * labelName : 系统通知
         */

        public int unReadMsgCount;
        public String iconUrl;
        public String labelName;
    }
}
