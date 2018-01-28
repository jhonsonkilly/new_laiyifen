package com.ody.p2p.productdetail.productdetail.bean;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/23.
 */
public class StandardBean extends BaseRequestBean {

    /**
     * id : 322
     * name : 效果
     * sortValue : 0
     * attrs : [{"attrName":{"id":null,"name":"效果","sortValue":null},"attrVal":{"id":null,"value":"美白","sortValue":null,"checked":false}}]
     */

    public List<Data> data;

    public static class Data {
        public long id;
        public String name;
        public String sortValue;
        /**
         * attrName : {"id":null,"name":"效果","sortValue":null}
         * attrVal : {"id":null,"value":"美白","sortValue":null,"checked":false}
         */

        public List<Attrs> attrs;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSortValue() {
            return sortValue;
        }

        public void setSortValue(String sortValue) {
            this.sortValue = sortValue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public List<Attrs> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<Attrs> attrs) {
            this.attrs = attrs;
        }

        public static class Attrs {
            /**
             * id : null
             * name : 效果
             * sortValue : null
             */

            public AttrName attrName;
            /**
             * id : null
             * value : 美白
             * sortValue : null
             * checked : false
             */

            public AttrVal attrVal;


            public AttrName getAttrName() {
                return attrName;
            }

            public void setAttrName(AttrName attrName) {
                this.attrName = attrName;
            }

            public AttrVal getAttrVal() {
                return attrVal;
            }

            public void setAttrVal(AttrVal attrVal) {
                this.attrVal = attrVal;
            }

            public static class AttrName {
                public String id;
                public String name;
                public String sortValue;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setSortValue(String sortValue) {
                    this.sortValue = sortValue;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getSortValue() {
                    return sortValue;
                }

            }

            public static class AttrVal {
                public String id;
                public String value;
                public String sortValue;
                public boolean checked;

                public Object getId() {
                    return id;
                }


                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public Object getSortValue() {
                    return sortValue;
                }


                public boolean isChecked() {
                    return checked;
                }

                public void setChecked(boolean checked) {
                    this.checked = checked;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setSortValue(String sortValue) {
                    this.sortValue = sortValue;
                }
            }
        }
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
