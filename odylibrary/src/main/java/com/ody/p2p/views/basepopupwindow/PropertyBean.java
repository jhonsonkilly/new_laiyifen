package com.ody.p2p.views.basepopupwindow;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class PropertyBean extends BaseRequestBean {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }


    public class SerialProducts {
        private String key;

        private ProductBean product;

        private String productSimpleVO;

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public void setProductSimpleVO(String productSimpleVO) {
            this.productSimpleVO = productSimpleVO;
        }

        public String getProductSimpleVO() {
            return this.productSimpleVO;
        }

    }

    public class Values {
        private String id;

        private String value;

        private int sortValue;

        private boolean checked;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public void setSortValue(int sortValue) {
            this.sortValue = sortValue;
        }

        public int getSortValue() {
            return this.sortValue;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean getChecked() {
            return this.checked;
        }

    }

    public class Attributes {
        private String id;

        private String name;

        private String sortValue;

        private List<Values> values;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setSortValue(String sortValue) {
            this.sortValue = sortValue;
        }

        public String getSortValue() {
            return this.sortValue;
        }

        public void setValues(List<Values> values) {
            this.values = values;
        }

        public List<Values> getValues() {
            return this.values;
        }

    }

    public class Data {

        private List<SerialProducts> associateProducts;//关联商品

        private List<SerialProducts> serialProducts;

        private List<Attributes> attributes;

        public List<SerialProducts> getAssociateProducts() {
            return associateProducts;
        }

        public void setAssociateProducts(List<SerialProducts> associateProducts) {
            this.associateProducts = associateProducts;
        }

        public void setSerialProducts(List<SerialProducts> serialProducts) {
            this.serialProducts = serialProducts;
        }

        public List<SerialProducts> getSerialProducts() {
            return this.serialProducts;
        }



        public void setAttributes(List<Attributes> attributes) {
            this.attributes = attributes;
        }

        public List<Attributes> getAttributes() {
            return this.attributes;
        }

    }


}
