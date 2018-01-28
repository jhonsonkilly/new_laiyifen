package com.lyfen.android.entity.network;

import java.util.List;

/**
 * Created by qj on 2017/5/18.
 */

public class StockPriceEntity {

    /**
     * code : 0
     * message :
     * errMsg : null
     * data : {"plist":[{"mpId":1006024200000074,"price":48,"marketPrice":null,"tax":0,"stockNum":843,"lackOfStock":0,"promotionType":1,"promotionPrice":26.9,"preferentialPrice":21.1,"promotionId":1048028900001004,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661462131_2079_93.png"],"promotionIconTexts":["特价"],"promotionStartTime":1494568868000,"promotionEndTime":1496210471000,"isSeckill":0,"individualLimitNum":111,"totalLimitNum":1111,"availablePrice":26.9},{"mpId":1007027700000002,"price":128,"marketPrice":null,"tax":0,"stockNum":243,"lackOfStock":0,"promotionType":1013,"promotionPrice":88,"preferentialPrice":40,"promotionId":1048029000000923,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487756083093_6979_78.png"],"promotionIconTexts":["闪购"],"promotionStartTime":1495036800000,"promotionEndTime":1495123199000,"isSeckill":0,"individualLimitNum":200,"totalLimitNum":200,"availablePrice":88},{"mpId":1008020801002564,"price":24,"marketPrice":24,"tax":0,"stockNum":489,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":24,"promotionId":null,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png"],"promotionIconTexts":["满赠"],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":24},{"mpId":1007023500000005,"price":7.8,"marketPrice":null,"tax":0,"stockNum":938,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":7.8,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":7.8},{"mpId":1007020801001671,"price":48.5,"marketPrice":54.81,"tax":0,"stockNum":1150,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":48.5,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":48.5},{"mpId":1006020801014840,"price":9.8,"marketPrice":10.86,"tax":0,"stockNum":540,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":9.8,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":9.8},{"mpId":1008020801001664,"price":13.9,"marketPrice":15.44,"tax":0,"stockNum":319,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":13.9,"promotionId":null,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png"],"promotionIconTexts":["满赠"],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":13.9}],"timestamp":1495099570083}
     * trace : 41!$1#@18%&10!$,153537,62709021067666438581108
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * plist : [{"mpId":1006024200000074,"price":48,"marketPrice":null,"tax":0,"stockNum":843,"lackOfStock":0,"promotionType":1,"promotionPrice":26.9,"preferentialPrice":21.1,"promotionId":1048028900001004,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661462131_2079_93.png"],"promotionIconTexts":["特价"],"promotionStartTime":1494568868000,"promotionEndTime":1496210471000,"isSeckill":0,"individualLimitNum":111,"totalLimitNum":1111,"availablePrice":26.9},{"mpId":1007027700000002,"price":128,"marketPrice":null,"tax":0,"stockNum":243,"lackOfStock":0,"promotionType":1013,"promotionPrice":88,"preferentialPrice":40,"promotionId":1048029000000923,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487756083093_6979_78.png"],"promotionIconTexts":["闪购"],"promotionStartTime":1495036800000,"promotionEndTime":1495123199000,"isSeckill":0,"individualLimitNum":200,"totalLimitNum":200,"availablePrice":88},{"mpId":1008020801002564,"price":24,"marketPrice":24,"tax":0,"stockNum":489,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":24,"promotionId":null,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png"],"promotionIconTexts":["满赠"],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":24},{"mpId":1007023500000005,"price":7.8,"marketPrice":null,"tax":0,"stockNum":938,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":7.8,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":7.8},{"mpId":1007020801001671,"price":48.5,"marketPrice":54.81,"tax":0,"stockNum":1150,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":48.5,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":48.5},{"mpId":1006020801014840,"price":9.8,"marketPrice":10.86,"tax":0,"stockNum":540,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":9.8,"promotionId":null,"promotionIconUrls":[],"promotionIconTexts":[],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":9.8},{"mpId":1008020801001664,"price":13.9,"marketPrice":15.44,"tax":0,"stockNum":319,"lackOfStock":0,"promotionType":null,"promotionPrice":null,"preferentialPrice":13.9,"promotionId":null,"promotionIconUrls":["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661448791_6278_43.png"],"promotionIconTexts":["满赠"],"promotionStartTime":null,"promotionEndTime":null,"isSeckill":0,"individualLimitNum":-1,"totalLimitNum":-1,"availablePrice":13.9}]
         * timestamp : 1495099570083
         */

        public long timestamp;
        public List<PlistEntity> plist;

        public static class PlistEntity {
            /**
             * mpId : 1006024200000074
             * price : 48.0
             * marketPrice : null
             * tax : 0.0
             * stockNum : 843
             * lackOfStock : 0
             * promotionType : 1
             * promotionPrice : 26.9
             * preferentialPrice : 21.1
             * promotionId : 1048028900001004
             * promotionIconUrls : ["http://cdn.oudianyun.com/lyf-local/branch/back-promotion/1487661462131_2079_93.png"]
             * promotionIconTexts : ["特价"]
             * promotionStartTime : 1494568868000
             * promotionEndTime : 1496210471000
             * isSeckill : 0
             * individualLimitNum : 111
             * totalLimitNum : 1111
             * availablePrice : 26.9
             */

            public String mpId;
            public String price;
            public String marketPrice;
            public double tax;
            public int stockNum;
            public int lackOfStock;
            public int promotionType;
            public String promotionPrice;
            public double preferentialPrice;
            public long promotionId;
            public long promotionStartTime;
            public long promotionEndTime;
            public int isSeckill;
            public int individualLimitNum;
            public int totalLimitNum;
            public double availablePrice;
            public List<String> promotionIconUrls;
            public List<String> promotionIconTexts;
        }
    }
}
