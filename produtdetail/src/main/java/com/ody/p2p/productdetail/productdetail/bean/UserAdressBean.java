package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/14.
 */
public class UserAdressBean extends BaseRequestBean {
    /**
     * code : 0
     * message : 操作成功
     * data : {"usualAddress":[{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"},{"addressDetail":"环桥路1481弄15号401(周一到周五请放到快递柜里","addressName":"张娟","fullAddress":"上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里"}]}
     */


    public Data data;

    public static class Data {
        /**
         * addressDetail : 环桥路1481弄15号401(周一到周五请放到快递柜里
         * addressName : 张娟
         * fullAddress : 上海浦东新区康桥镇环桥路1481弄15号401(周一到周五请放到快递柜里
         */

        public List<UsualAddress> usualAddress;

        public static class UsualAddress {
            public String addressDetail;
            public String addressName;
            public String fullAddress;

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public String getAddressName() {
                return addressName;
            }

            public void setAddressName(String addressName) {
                this.addressName = addressName;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }
        }

        public List<UsualAddress> getUsualAddress() {
            return usualAddress;
        }

        public void setUsualAddress(List<UsualAddress> usualAddress) {
            this.usualAddress = usualAddress;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
