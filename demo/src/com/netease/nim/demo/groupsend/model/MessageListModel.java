package com.netease.nim.demo.groupsend.model;



import java.io.Serializable;
import java.util.List;

/**
 * Created by jasmin on 2017/11/8.
 */

public class MessageListModel implements Serializable{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
//        /**
//         * content : xhxh
//         * name : 收件人： 讨论组，这是一个高级群，高级群，非常高级的群，高级群1，高级群2，高级群，这是一个牛逼的群，吃鸡群，吧，高级群，hd，aaaaa，接口，，我是谁，，房益明，jinlei，Desmart7，讨论组，这是一个高级群，高级群，非常高级的群，高级群1，高级群2，高级群，这是一个牛逼的群，吃鸡群，吧，高级群，hd，aaaaa，接口，，我是谁，，房益明，jinlei，Desmart7，讨论组，这是一个高级群，高级群，非常高级的群，高级群1，高级群2，高级群，这是一个牛逼的群，吃鸡群，吧，高级群，hd，aaaaa，接口，，我是谁，，房益明，jinlei，Desmart7
//         */
//
//        private String content;
//        private String name;
//        private String sendDate;
//        private List<String> sessionId;
//
//        public List<String> getSessionId() {
//            return sessionId;
//        }
//
//        public void setSessionId(List<String> sessionId) {
//            this.sessionId = sessionId;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getSendDate() {
//            return sendDate;
//        }
//
//        public void setSendDate(String sendDate) {
//            this.sendDate = sendDate;
//        }

        private List<GroupSendModel> sendList;
        private String content;
        private String sendDate;

        public List<GroupSendModel> getSendList() {
            return sendList;
        }

        public void setSendList(List<GroupSendModel> sendList) {
            this.sendList = sendList;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String name) {
            this.content = name;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }
    }
}
