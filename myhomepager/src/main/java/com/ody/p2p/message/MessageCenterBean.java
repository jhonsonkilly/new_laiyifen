package com.ody.p2p.message;

/**
 * Created by ody on 2016/8/24.
 */
public class MessageCenterBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String labelName;

        private long latestTime;

        private String msgContent;

        private String msgTitle;

        private int unReadMsgCount;

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getLabelName() {
            return this.labelName;
        }

        public void setLatestTime(long latestTime) {
            this.latestTime = latestTime;
        }

        public long getLatestTime() {
            return this.latestTime;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public String getMsgContent() {
            return this.msgContent;
        }

        public void setMsgTitle(String msgTitle) {
            this.msgTitle = msgTitle;
        }

        public String getMsgTitle() {
            return this.msgTitle;
        }

        public void setUnReadMsgCount(int unReadMsgCount) {
            this.unReadMsgCount = unReadMsgCount;
        }

        public int getUnReadMsgCount() {
            return this.unReadMsgCount;
        }
    }
}
