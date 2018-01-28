package com.ody.p2p.message.sysmessagelist;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/8/25.
 */
public class SysMessageListBean extends BaseRequestBean {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public class Data {
        private List<Dataes> data;

        private int pageNo;

        private int pageSize;

        private int totalCount;

        private int totalPage;

        public void setDatas(List<Dataes> data) {
            this.data = data;
        }

        public List<Dataes> getDatas() {
            return this.data;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageNo() {
            return this.pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return this.pageSize;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCount() {
            return this.totalCount;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalPage() {
            return this.totalPage;
        }

    }

    public class Dataes {
        private int companyId;

        private String id;

        private String msgContent;

        private String msgTitle;

        private long sendTime;

        private int sendType;

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getCompanyId() {
            return this.companyId;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
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

        public void setSendTime(long sendTime) {
            this.sendTime = sendTime;
        }

        public long getSendTime() {
            return this.sendTime;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public int getSendType() {
            return this.sendType;
        }

    }
}
