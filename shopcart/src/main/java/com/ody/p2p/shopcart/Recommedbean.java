package com.ody.p2p.shopcart;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class Recommedbean extends BaseRequestBean {
    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        int totalCount;
        List<ShopCartBean.ProductList> productList;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ShopCartBean.ProductList> getProductList() {
            return productList;
        }

        public void setProductList(List<ShopCartBean.ProductList> productList) {
            this.productList = productList;
        }
    }
}
