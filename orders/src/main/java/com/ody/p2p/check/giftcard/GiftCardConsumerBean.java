package com.ody.p2p.check.giftcard;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/18.
 */
public class GiftCardConsumerBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : [{"giftCardId":12345,"monetary":3.4,"orderCode":"1609191243928987","userTimeStr":"2016-09-29 15:06:05","num":1,"productList":[{"soItemId":null,"merchantId":null,"productId":null,"mpId":12,"name":"测试商品","picUrl":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg","price":null,"attrs":null,"num":0,"isGift":0,"standard":null,"managementState":null,"securityList":null,"url60x60":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=800&w=800&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=160&w=160&m=0"}]}]
     * trace : 14!$6#@16%&172!$,null,61871552597884682940371
     */

    public Object errMsg;
    public String trace;
    /**
     * giftCardId : 12345
     * monetary : 3.4
     * orderCode : 1609191243928987
     * userTimeStr : 2016-09-29 15:06:05
     * num : 1
     * productList : [{"soItemId":null,"merchantId":null,"productId":null,"mpId":12,"name":"测试商品","picUrl":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg","price":null,"attrs":null,"num":0,"isGift":0,"standard":null,"managementState":null,"securityList":null,"url60x60":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=800&w=800&m=0","url100x100":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=100&w=100&m=0","url120x120":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=160&w=160&m=0"}]
     */

    public List<DataBean> data;

    public static class DataBean {
        public String giftCardId;
        public String monetary;
        public String orderCode;
        public String userTimeStr;
        public int num;
        /**
         * soItemId : null
         * merchantId : null
         * productId : null
         * mpId : 12
         * name : 测试商品
         * picUrl : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg
         * price : null
         * attrs : null
         * num : 0
         * isGift : 0
         * standard : null
         * managementState : null
         * securityList : null
         * url60x60 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=60&w=60&m=0
         * url220x220 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=220&w=220&m=0
         * url500x500 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=500&w=500&m=0
         * url800x800 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=800&w=800&m=0
         * url100x100 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=100&w=100&m=0
         * url120x120 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=120&w=120&m=0
         * url160x160 : http://odianyun.kssws.ks-cdn.com/saas/back_product/1474428423483_77.29570813556161_296762e0-506c-4f46-a5d7-2c599a5395af.jpg@base@tag=imgScale&h=160&w=160&m=0
         */

        public List<ProductListBean> productList;

        public static class ProductListBean {
            public String soItemId;
            public String merchantId;
            public String productId;
            public String mpId;
            public String name;
            public String picUrl;
            public String price;
            public Object attrs;
            public int num;
            public int isGift;
            public Object standard;
            public Object managementState;
            public Object securityList;
            public String url60x60;
            public String url220x220;
            public String url500x500;
            public String url800x800;
            public String url100x100;
            public String url120x120;
            public String url160x160;
        }
    }
}
