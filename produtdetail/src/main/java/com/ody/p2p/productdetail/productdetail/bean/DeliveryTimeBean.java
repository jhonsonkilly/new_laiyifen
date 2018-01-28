package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by pzy on 2017/1/10.
 */

public class DeliveryTimeBean extends BaseRequestBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String deliveryDescription;

        public void setDeliveryDescription(String deliveryDescription){
            this.deliveryDescription = deliveryDescription;
        }
        public String getDeliveryDescription(){
            return this.deliveryDescription;
        }

    }
}
