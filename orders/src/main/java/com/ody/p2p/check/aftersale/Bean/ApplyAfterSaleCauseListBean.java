package com.ody.p2p.check.aftersale.Bean;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

//申请售后原因列表
public class ApplyAfterSaleCauseListBean extends BaseRequestBean {
    public class OrderAfterSalesCauseVOs {
        private int key;

        private String value;

        public void setKey(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return value;
        }

    }

    public class Data {
        private List<OrderAfterSalesCauseVOs> orderAfterSalesCauseVOs;

        public void setOrderAfterSalesCauseVOs(
                List<OrderAfterSalesCauseVOs> orderAfterSalesCauseVOs) {
            this.orderAfterSalesCauseVOs = orderAfterSalesCauseVOs;
        }

        public List<OrderAfterSalesCauseVOs> getOrderAfterSalesCauseVOs() {
            return this.orderAfterSalesCauseVOs;
        }

    }

    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

}
