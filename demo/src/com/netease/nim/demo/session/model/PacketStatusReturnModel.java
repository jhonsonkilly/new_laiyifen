package com.netease.nim.demo.session.model;

import java.io.Serializable;

/**
 * @author SevenCheng
 */

public class PacketStatusReturnModel implements Serializable {

    /**
     * code : 1
     * message : 执行成功
     * errMsg : null
     * data : {"message":"已领完","isMine":0,"status":1,"packetDetail":{"redPacketId":"36733151","title":"大吉大利，晚上吃鸡！","amount":"262.67","packetAmount":null,"receiveTotalAmount":null,"headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","sendMemberId":"55641127","sendUserName":"伊客99548747","sendTime":"2017-12-25 15:57:58","isReceived":null,"message":null,"number":"50","receivedNum":"41","kind":"1","kindStr":"普通红包"}}
     * trace : 108!$3#@3%&10!$,1,63509676320297455080037
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

    public static class DataBean implements Serializable {
        /**
         * message : 已领完
         * isMine : 0
         * status : 1
         * packetDetail : {"redPacketId":"36733151","title":"大吉大利，晚上吃鸡！","amount":"262.67","packetAmount":null,"receiveTotalAmount":null,"headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","sendMemberId":"55641127","sendUserName":"伊客99548747","sendTime":"2017-12-25 15:57:58","isReceived":null,"message":null,"number":"50","receivedNum":"41","kind":"1","kindStr":"普通红包"}
         */

        private String message;
        private int              isMine;
        private int              status;
        private PacketDetailBean packectDetail;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getIsMine() {
            return isMine;
        }

        public void setIsMine(int isMine) {
            this.isMine = isMine;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public PacketDetailBean getPacketDetail() {
            return packectDetail;
        }

        public void setPacketDetail(PacketDetailBean packectDetail) {
            this.packectDetail = packectDetail;
        }

        public static class PacketDetailBean {
            /**
             * redPacketId : 36733151
             * title : 大吉大利，晚上吃鸡！
             * amount : 262.67
             * packetAmount : null
             * receiveTotalAmount : null
             * headUrl : http://m.laiyifen.com/images/logo-laiyifen.png
             * sendMemberId : 55641127
             * sendUserName : 伊客99548747
             * sendTime : 2017-12-25 15:57:58
             * isReceived : null
             * message : null
             * number : 50
             * receivedNum : 41
             * kind : 1
             * kindStr : 普通红包
             */

            private String redPacketId;
            private String title;
            private String amount;
            private Object packetAmount;
            private Object receiveTotalAmount;
            private String headUrl;
            private String sendMemberId;
            private String sendUserName;
            private String sendTime;
            private Object isReceived;
            private Object message;
            private String number;
            private String receivedNum;
            private String type;
            private String kindStr;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRedPacketId() {
                return redPacketId;
            }

            public void setRedPacketId(String redPacketId) {
                this.redPacketId = redPacketId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public Object getPacketAmount() {
                return packetAmount;
            }

            public void setPacketAmount(Object packetAmount) {
                this.packetAmount = packetAmount;
            }

            public Object getReceiveTotalAmount() {
                return receiveTotalAmount;
            }

            public void setReceiveTotalAmount(Object receiveTotalAmount) {
                this.receiveTotalAmount = receiveTotalAmount;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getSendMemberId() {
                return sendMemberId;
            }

            public void setSendMemberId(String sendMemberId) {
                this.sendMemberId = sendMemberId;
            }

            public String getSendUserName() {
                return sendUserName;
            }

            public void setSendUserName(String sendUserName) {
                this.sendUserName = sendUserName;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public Object getIsReceived() {
                return isReceived;
            }

            public void setIsReceived(Object isReceived) {
                this.isReceived = isReceived;
            }

            public Object getMessage() {
                return message;
            }

            public void setMessage(Object message) {
                this.message = message;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getReceivedNum() {
                return receivedNum;
            }

            public void setReceivedNum(String receivedNum) {
                this.receivedNum = receivedNum;
            }



            public String getKindStr() {
                return kindStr;
            }

            public void setKindStr(String kindStr) {
                this.kindStr = kindStr;
            }
        }
    }
}
