package com.lyfen.android.entity.network.dialog;

import java.util.List;

/**
 * <p> Created by qiujie on 2017/12/28/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class BuyAgainEntity {


    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : {"hasInvalidProducts":0,"availableProductList":[{"soItemId":1036032100003843,"merchantId":101,"productId":1008020801001681,"mpId":1008020801001682,"name":"沙嗲牛肉片 250g","picUrl":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg","price":56.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":null,"url60x60":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"}]}
     * trace : 43!$1#@18%&10!$,197461,63520413175204299201559
     */

    public int code;
    public String message;
    public Object errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * hasInvalidProducts : 0
         * availableProductList : [{"soItemId":1036032100003843,"merchantId":101,"productId":1008020801001681,"mpId":1008020801001682,"name":"沙嗲牛肉片 250g","picUrl":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg","price":56.9,"attrs":null,"num":1,"isGift":0,"standard":null,"managementState":1,"securityList":null,"propertyTags":null,"commentStatus":0,"combinedProductMpId":null,"seriesParentId":null,"usedYidou":null,"url60x60":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60","url100x100":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100","url120x120":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120","url160x160":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160","url220x220":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220","url300x300":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300","url400x400":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400","url500x500":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500","url800x800":"http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800"}]
         */

        public int hasInvalidProducts;
        public List<AvailableProductListEntity> availableProductList;

        public static class AvailableProductListEntity {
            /**
             * soItemId : 1036032100003843
             * merchantId : 101
             * productId : 1008020801001681
             * mpId : 1008020801001682
             * name : 沙嗲牛肉片 250g
             * picUrl : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg
             * price : 56.9
             * attrs : null
             * num : 1
             * isGift : 0
             * standard : null
             * managementState : 1
             * securityList : null
             * propertyTags : null
             * commentStatus : 0
             * combinedProductMpId : null
             * seriesParentId : null
             * usedYidou : null
             * url60x60 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=60&w=60
             * url100x100 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=100&w=100
             * url120x120 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=120&w=120
             * url160x160 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=160&w=160
             * url220x220 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=220&w=220
             * url300x300 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=300&w=300
             * url400x400 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=400&w=400
             * url500x500 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=500&w=500
             * url800x800 : http://cdn.oudianyun.com/1503556096277_50.17155572846891_b292ba83-06a8-42d0-8bca-e99cc42d12ab.jpg@base@tag=imgScale&q=80&m=0&h=800&w=800
             */

            public long soItemId;
            public int merchantId;
            public long productId;
            public long mpId;
            public String name;
            public String picUrl;
            public double price;
            public Object attrs;
            public int num;
            public int isGift;
            public Object standard;
            public int managementState;
            public Object securityList;
            public Object propertyTags;
            public int commentStatus;
            public Object combinedProductMpId;
            public Object seriesParentId;
            public Object usedYidou;
            public String url60x60;
            public String url100x100;
            public String url120x120;
            public String url160x160;
            public String url220x220;
            public String url300x300;
            public String url400x400;
            public String url500x500;
            public String url800x800;
        }
    }
}
