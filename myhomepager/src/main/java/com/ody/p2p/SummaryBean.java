package com.ody.p2p;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/6/16.
 */
public class SummaryBean extends BaseRequestBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int undelivery;

        private int unpay;

        private int unerceive;
        private int unEvaluate;

        public int getUnEvaluate() {
            return unEvaluate;
        }

        public void setUnEvaluate(int unEvaluate) {
            this.unEvaluate = unEvaluate;
        }

        public void setUndelivery(int undelivery) {
            this.undelivery = undelivery;
        }

        public int getUndelivery() {
            return this.undelivery;
        }

        public void setUnpay(int unpay) {
            this.unpay = unpay;
        }

        public int getUnpay() {
            return this.unpay;
        }

        public void setUnerceive(int unerceive) {
            this.unerceive = unerceive;
        }

        public int getUnerceive() {
            return this.unerceive;
        }

    }
}
