package com.ody.p2p.views.basepopupwindow;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/17.
 */
public class CouponBean extends BaseRequestBean {
    private List<Data> data;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }

    public class Data {
        private int couponPrice;

        private String couponName;

        private String couponT;

        public void setCouponPrice(int couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getCouponPrice() {
            return this.couponPrice;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponName() {
            return this.couponName;
        }

        public void setCouponT(String couponT) {
            this.couponT = couponT;
        }

        public String getCouponT() {
            return this.couponT;
        }

    }
}
