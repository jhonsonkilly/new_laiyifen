package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ody on 2016/8/22.
 */
public class FreightBean extends BaseRequestBean {


    /**
     * freight : 0
     * distributionMode : 物流配送
     * distributionCode : 1001
     */

    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        public double freight;
        public String distributionMode;
        public String distributionCode;



        public String getDistributionMode() {
            return distributionMode;
        }

        public void setDistributionMode(String distributionMode) {
            this.distributionMode = distributionMode;
        }

        public String getDistributionCode() {
            return distributionCode;
        }

        public void setDistributionCode(String distributionCode) {
            this.distributionCode = distributionCode;
        }
    }
}
