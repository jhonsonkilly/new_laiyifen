package com.netease.nim.demo.redpacket.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class PacketDetailReturnModel {

    /**
     * code : 1
     * message : 执行成功
     * errMsg : null
     * data : {"packetInfo":{"redPacketId":"65000454","title":"大吉大利，晚上吃鸡！","amount":"153.33","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","sendMemberId":"37665226","sendUserName":"伊客55301289","sendTime":"2017-12-25 11:32:08","isReceived":"1","message":"","number":"50","receivedNum":"35"},"receiveList":[{"userName":"伊客97698729","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"1.25","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客85765724","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"13.35","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客91308424","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"14.86","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客55785922","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"15.52","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客1149296","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"19.22","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客70129364","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"6.89","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客92583615","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"19.50","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客84495164","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"10.38","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客54961381","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"2.85","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客17687655","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"16.43","dateTime":"2017-12-25 11:32:08"}]}
     * trace : 108!$3#@3%&10!$,1,63509007310738110570608
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
         * packetInfo : {"redPacketId":"65000454","title":"大吉大利，晚上吃鸡！","amount":"153.33","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","sendMemberId":"37665226","sendUserName":"伊客55301289","sendTime":"2017-12-25 11:32:08","isReceived":"1","message":"","number":"50","receivedNum":"35"}
         * receiveList : [{"userName":"伊客97698729","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"1.25","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客85765724","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"13.35","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客91308424","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"14.86","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客55785922","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"15.52","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客1149296","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"19.22","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客70129364","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"6.89","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客92583615","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"19.50","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客84495164","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"10.38","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客54961381","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"2.85","dateTime":"2017-12-25 11:32:08"},{"userName":"伊客17687655","headUrl":"http://m.laiyifen.com/images/logo-laiyifen.png","amount":"16.43","dateTime":"2017-12-25 11:32:08"}]
         */

        private PacketInfoBean packetInfo;
        private List<ReceiveListBean> receiveList;

        public PacketInfoBean getPacketInfo() {
            return packetInfo;
        }

        public void setPacketInfo(PacketInfoBean packetInfo) {
            this.packetInfo = packetInfo;
        }

        public List<ReceiveListBean> getReceiveList() {
            return receiveList;
        }

        public void setReceiveList(List<ReceiveListBean> receiveList) {
            this.receiveList = receiveList;
        }

        public static class PacketInfoBean {
            /**
             * redPacketId : 65000454
             * title : 大吉大利，晚上吃鸡！
             * amount : 153.33
             * headUrl : http://m.laiyifen.com/images/logo-laiyifen.png
             * sendMemberId : 37665226
             * sendUserName : 伊客55301289
             * sendTime : 2017-12-25 11:32:08
             * isReceived : 1
             * message :
             * number : 50
             * receivedNum : 35
             */

            private String redPacketId;
            private String title;
            private String amount;
            private String type;
            private String headUrl;
            private String sendMemberId;
            private String sendUserName;
            private String sendTime;
            private String isReceived;
            private String message;
            private String number;
            private String receivedNum;
            private String packetAmount;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPacketAmount() {
                return packetAmount;
            }

            public void setPacketAmount(String packetAmount) {
                this.packetAmount = packetAmount;
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

            public String getIsReceived() {
                return isReceived;
            }

            public void setIsReceived(String isReceived) {
                this.isReceived = isReceived;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
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
        }

        public static class ReceiveListBean {
            /**
             * userName : 伊客97698729
             * headUrl : http://m.laiyifen.com/images/logo-laiyifen.png
             * amount : 1.25
             * dateTime : 2017-12-25 11:32:08
             */

            private String userName;
            private String headUrl;
            private String amount;
            private String dateTime;
            private String isBigest;

            public String getIsBigest() {
                return isBigest;
            }

            public void setIsBigest(String isBigest) {
                this.isBigest = isBigest;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }
        }
    }
}
