package com.ody.p2p.classesification.Bean;


import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/5/27.
 */
public class SecCategoryBean    extends BaseRequestBean {

    public Data data;

    public static class Data {
        /**
         * categoryId : 754
         * categoryName : yh02级
         * pictureUrl : null
         * categoryTreeNodeId : 868
         * children : [{"categoryId":755,"categoryName":"yh02级","pictureUrl":null,"categoryTreeNodeId":869,"children":null}]
         */

        public List<Categorys> categorys;

        public List<Categorys> getCategorys() {
            return categorys;
        }

        public void setCategorys(List<Categorys> categorys) {
            this.categorys = categorys;
        }

        public static class Categorys {
            public String categoryId;
            public String categoryName;
            public String pictureUrl;
            public String categoryTreeNodeId;
            /**
             * categoryId : 755
             * categoryName : yh02级
             * pictureUrl : null
             * categoryTreeNodeId : 869
             * children : null
             */

            public List<Children> children;

            public static class Children {
                public String categoryId;
                public String categoryName;
                public String pictureUrl;
                public String categoryTreeNodeId;
                public String children;


                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public Object getPictureUrl() {
                    return pictureUrl;
                }


                public Object getChildren() {
                    return children;
                }

            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public Object getPictureUrl() {
                return pictureUrl;
            }



            public List<Children> getChildren() {
                return children;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getCategoryTreeNodeId() {
                return categoryTreeNodeId;
            }

            public void setCategoryTreeNodeId(String categoryTreeNodeId) {
                this.categoryTreeNodeId = categoryTreeNodeId;
            }

            public void setChildren(List<Children> children) {
                this.children = children;
            }
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
