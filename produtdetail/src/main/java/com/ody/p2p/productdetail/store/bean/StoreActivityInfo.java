package com.ody.p2p.productdetail.store.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by hanzhifengyun on 2017/7/10.
 * 店铺活动信息
 */

public class StoreActivityInfo extends BaseRequestBean {

    /**
     * errMsg : null
     * data : {"listObj":[{"promotionId":1011028101000092,"frontPromotionType":1001,"promTitle":"满额折勿动1112","description":"<p>满额折勿动说明说明说明<\/p>","startTime":1491816571000,"endTime":1502011773000},{"promotionId":1011028101000162,"frontPromotionType":1002,"promTitle":"满额减勿动","description":"<p>满额减勿动说明说明说明<\/p>","startTime":1491816571000,"endTime":1502011773000},{"promotionId":1011028101000141,"frontPromotionType":1003,"promTitle":"满量折勿动","description":"<p>满量折勿动<\/p><p>*活动说明<\/p><p>自定义标题<\/p><p>段落<\/p><p>arial<\/p><p>16px<\/p><p><br/><\/p>","startTime":1491816975000,"endTime":1502012176000}],"total":3}
     * trace : 196!$10#@18%&10!$,0,62900590097817850601963
     */

    private Object errMsg;
    private DataBean data;
    private String trace;

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
         * listObj : [{"promotionId":1011028101000092,"frontPromotionType":1001,"promTitle":"满额折勿动1112","description":"<p>满额折勿动说明说明说明<\/p>","startTime":1491816571000,"endTime":1502011773000},{"promotionId":1011028101000162,"frontPromotionType":1002,"promTitle":"满额减勿动","description":"<p>满额减勿动说明说明说明<\/p>","startTime":1491816571000,"endTime":1502011773000},{"promotionId":1011028101000141,"frontPromotionType":1003,"promTitle":"满量折勿动","description":"<p>满量折勿动<\/p><p>*活动说明<\/p><p>自定义标题<\/p><p>段落<\/p><p>arial<\/p><p>16px<\/p><p><br/><\/p>","startTime":1491816975000,"endTime":1502012176000}]
         * total : 3
         */

        private int total;
        private List<ListObjBean> listObj;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListObjBean> getListObj() {
            return listObj;
        }

        public void setListObj(List<ListObjBean> listObj) {
            this.listObj = listObj;
        }

        public static class ListObjBean {
            /**
             * promotionId : 1011028101000092
             * frontPromotionType : 1001
             * promTitle : 满额折勿动1112
             * description : <p>满额折勿动说明说明说明</p>
             * startTime : 1491816571000
             * endTime : 1502011773000
             */

            private long promotionId;
            private int frontPromotionType;
            private String promTitle;
            private String description;
            private long startTime;
            private long endTime;

            public long getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(long promotionId) {
                this.promotionId = promotionId;
            }

            public int getFrontPromotionType() {
                return frontPromotionType;
            }

            public void setFrontPromotionType(int frontPromotionType) {
                this.frontPromotionType = frontPromotionType;
            }

            public String getPromTitle() {
                return promTitle;
            }

            public void setPromTitle(String promTitle) {
                this.promTitle = promTitle;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }
        }
    }
}
