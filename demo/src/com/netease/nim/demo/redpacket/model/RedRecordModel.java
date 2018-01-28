package com.netease.nim.demo.redpacket.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class RedRecordModel {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"headUrl":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1512437766591_5738_3986.jpg","nickName":"伊客11094809","amount":5,"quantity":1,"luckiest":1,"sendList":[{"nickName":"郑文青","date":"2017-12-27","amount":5,"redType":1,"sendCount":null,"receiveCount":null,"headUrl":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1511333424290_2527_2000.jpg","redPackectId":134,"isBest":0}]}
     * trace : 108!$3#@3%&10!$,1,63517599122748170733031
     */

    private String code;
    private String message;
    private Object errMsg;
    private DataBean data;
    private String trace;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public static class DataBean {
        /**
         * headUrl : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1512437766591_5738_3986.jpg
         * nickName : 伊客11094809
         * amount : 5
         * quantity : 1
         * luckiest : 1
         * sendList : [{"nickName":"郑文青","date":"2017-12-27","amount":5,"redType":1,"sendCount":null,"receiveCount":null,"headUrl":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1511333424290_2527_2000.jpg","redPackectId":134,"isBest":0}]
         */

        private String headUrl;
        private String nickName;
        private double             amount;
        private int                quantity;
        private int                luckiest;
        private List<SendListBean> sendList;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getLuckiest() {
            return luckiest;
        }

        public void setLuckiest(int luckiest) {
            this.luckiest = luckiest;
        }

        public List<SendListBean> getSendList() {
            return sendList;
        }

        public void setSendList(List<SendListBean> sendList) {
            this.sendList = sendList;
        }

        public static class SendListBean {
            /**
             * nickName : 郑文青
             * date : 2017-12-27
             * amount : 5
             * redType : 1
             * sendCount : null
             * receiveCount : null
             * headUrl : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1511333424290_2527_2000.jpg
             * redPackectId : 134
             * isBest : 0
             */

            private String nickName;
            private String date;
            private double amount;
            private int    redType;
            private Object sendCount;
            private Object receiveCount;
            private String headUrl;
            private int    redPackectId;
            private int    isBest;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getRedType() {
                return redType;
            }

            public void setRedType(int redType) {
                this.redType = redType;
            }

            public Object getSendCount() {
                return sendCount;
            }

            public void setSendCount(Object sendCount) {
                this.sendCount = sendCount;
            }

            public Object getReceiveCount() {
                return receiveCount;
            }

            public void setReceiveCount(Object receiveCount) {
                this.receiveCount = receiveCount;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getRedPackectId() {
                return redPackectId;
            }

            public void setRedPackectId(int redPackectId) {
                this.redPackectId = redPackectId;
            }

            public int getIsBest() {
                return isBest;
            }

            public void setIsBest(int isBest) {
                this.isBest = isBest;
            }
        }
    }
}
