package com.ody.p2p.check.aftersale.Bean;

import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.basepopupwindow.PropertyWindow;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/24.
 */
public class ChangeProductBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : {"mpId":6827,"productUrl":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png","chineseName":"系列一一","mayExchangeProductNum":1,"productPriceFinal":300,"stockNum":299,"map":{"serialProducts":[{"key":"_301_","product":{"productId":6827,"mpId":6827,"code":"P1T9WLNQCHCQ4MA","name":"系列一一","subTitle":null,"price":300,"stockNum":299,"lackOfStock":null,"brandId":1073,"brandName":"朵以","merchantSeriesId":257,"companyId":1001,"merchantId":101,"merchantName":null,"merchantType":null,"standard":null,"pics":[{"name":"暂无图片","url":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png","url60x60":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=800&w=800&m=0"}],"attrs":[{"attrName":{"id":null,"name":"属性0805088颜色","sortValue":null},"attrVal":{"id":301,"value":"黄黄","sortValue":1,"checked":false,"mpId":[]}}]}}],"attributes":[{"id":7,"name":"属性0805088颜色","sortValue":null,"values":[{"id":301,"value":"黄黄","sortValue":1,"checked":true,"mpId":[6827]}]}]},"isSerial":1,"soItemId":2451737}
     * trace : 46!$6#@16%&172!$,null,61719729385933587853886
     */

    public Object errMsg;
    /**
     * mpId : 6827
     * productUrl : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png
     * chineseName : 系列一一
     * mayExchangeProductNum : 1
     * productPriceFinal : 300
     * stockNum : 299
     * map : {"serialProducts":[{"key":"_301_","product":{"productId":6827,"mpId":6827,"code":"P1T9WLNQCHCQ4MA","name":"系列一一","subTitle":null,"price":300,"stockNum":299,"lackOfStock":null,"brandId":1073,"brandName":"朵以","merchantSeriesId":257,"companyId":1001,"merchantId":101,"merchantName":null,"merchantType":null,"standard":null,"pics":[{"name":"暂无图片","url":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png","url60x60":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=800&w=800&m=0"}],"attrs":[{"attrName":{"id":null,"name":"属性0805088颜色","sortValue":null},"attrVal":{"id":301,"value":"黄黄","sortValue":1,"checked":false,"mpId":[]}}]}}],"attributes":[{"id":7,"name":"属性0805088颜色","sortValue":null,"values":[{"id":301,"value":"黄黄","sortValue":1,"checked":true,"mpId":[6827]}]}]}
     * isSerial : 1
     * soItemId : 2451737
     */

    public DataBean data;
    public String trace;

    public static class DataBean {
        public String mpId;
        public String productUrl;
        public String chineseName;
        public int mayExchangeProductNum;
        public double productPriceFinal;
        public int stockNum;
        public PropertyBean.Data map;
        public int isSerial;
        public String soItemId;

        public static class MapBean {
            /**
             * key : _301_
             * product : {"productId":6827,"mpId":6827,"code":"P1T9WLNQCHCQ4MA","name":"系列一一","subTitle":null,"price":300,"stockNum":299,"lackOfStock":null,"brandId":1073,"brandName":"朵以","merchantSeriesId":257,"companyId":1001,"merchantId":101,"merchantName":null,"merchantType":null,"standard":null,"pics":[{"name":"暂无图片","url":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png","url60x60":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=800&w=800&m=0"}],"attrs":[{"attrName":{"id":null,"name":"属性0805088颜色","sortValue":null},"attrVal":{"id":301,"value":"黄黄","sortValue":1,"checked":false,"mpId":[]}}]}
             */

            public List<SerialProductsBean> serialProducts;
            /**
             * id : 7
             * name : 属性0805088颜色
             * sortValue : null
             * values : [{"id":301,"value":"黄黄","sortValue":1,"checked":true,"mpId":[6827]}]
             */

            public List<AttributesBean> attributes;

            public static class SerialProductsBean {
                public String key;
                /**
                 * productId : 6827
                 * mpId : 6827
                 * code : P1T9WLNQCHCQ4MA
                 * name : 系列一一
                 * subTitle : null
                 * price : 300
                 * stockNum : 299
                 * lackOfStock : null
                 * brandId : 1073
                 * brandName : 朵以
                 * merchantSeriesId : 257
                 * companyId : 1001
                 * merchantId : 101
                 * merchantName : null
                 * merchantType : null
                 * standard : null
                 * pics : [{"name":"暂无图片","url":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png","url60x60":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=800&w=800&m=0"}]
                 * attrs : [{"attrName":{"id":null,"name":"属性0805088颜色","sortValue":null},"attrVal":{"id":301,"value":"黄黄","sortValue":1,"checked":false,"mpId":[]}}]
                 */

                public ProductBean product;

                public static class ProductBean {
                    public String productId;
                    public String mpId;
                    public String code;
                    public String name;
                    public String subTitle;
                    public String price;
                    public int stockNum;
                    public int lackOfStock;
                    public int brandId;
                    public String brandName;
                    public int merchantSeriesId;
                    public int companyId;
                    public int merchantId;
                    public String merchantName;
                    public int merchantType;
                    public String standard;
                    public PropertyWindow  propertyWindow;
                    /**
                     * name : 暂无图片
                     * url : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png
                     * url60x60 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=60&w=60&m=0
                     * url100x100 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=100&w=100&m=0
                     * url120x120 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=120&w=120&m=0
                     * url160x160 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=160&w=160&m=0
                     * url220x220 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=220&w=220&m=0
                     * url500x500 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=500&w=500&m=0
                     * url800x800 : http://odianyun.kssws.ks-cdn.com/saas/1466167399254_44.20842700396289_logo-bl.png@base@tag=imgScale&h=800&w=800&m=0
                     */

                    public List<PicsBean> pics;
                    /**
                     * attrName : {"id":null,"name":"属性0805088颜色","sortValue":null}
                     * attrVal : {"id":301,"value":"黄黄","sortValue":1,"checked":false,"mpId":[]}
                     */

                    public List<AttrsBean> attrs;

                    public static class PicsBean {
                        public String name;
                        public String url;
                        public String url60x60;
                        public String url100x100;
                        public String url120x120;
                        public String url160x160;
                        public String url220x220;
                        public String url500x500;
                        public String url800x800;
                    }

                    public static class AttrsBean {
                        /**
                         * id : null
                         * name : 属性0805088颜色
                         * sortValue : null
                         */

                        public AttrNameBean attrName;
                        /**
                         * id : 301
                         * value : 黄黄
                         * sortValue : 1
                         * checked : false
                         * mpId : []
                         */

                        public AttrValBean attrVal;

                        public static class AttrNameBean {
                            public String id;
                            public String name;
                            public String sortValue;
                        }

                        public static class AttrValBean {
                            public String id;
                            public String value;
                            public int sortValue;
                            public boolean checked;
                            public List<?> mpId;
                        }
                    }
                }
            }

            public static class AttributesBean {
                public String id;
                public String name;
                public Object sortValue;
                /**
                 * id : 301
                 * value : 黄黄
                 * sortValue : 1
                 * checked : true
                 * mpId : [6827]
                 */

                public List<ValuesBean> values;

                public static class ValuesBean {
                    public String id;
                    public String value;
                    public int sortValue;
                    public boolean checked;
                    public List<Long> mpId;
                }
            }
        }
    }
}
