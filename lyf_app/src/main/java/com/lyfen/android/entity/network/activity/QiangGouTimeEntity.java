package com.lyfen.android.entity.network.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiujie on 2017/7/21.
 */

public class QiangGouTimeEntity implements Serializable {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"timeList":[{"date":"今日秒杀","times":[{"monthOrDay":"07月21日","timeStr":"10:00","promotionId":1040035400000092,"statusStr":"秒杀中","status":2,"yearAndMonthAndDay":"2017/07/21","startTime":1500602400000,"endTime":1500609600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"12:00","promotionId":1040035400000095,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500609600000,"endTime":1500613200000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"13:00","promotionId":1040035400000098,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500613200000,"endTime":1500620400000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"15:00","promotionId":1040035400000101,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500620400000,"endTime":1500627600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"17:00","promotionId":1040035400000104,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500627600000,"endTime":1500634800000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"19:00","promotionId":1040035400000107,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500634800000,"endTime":1500642000000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"21:00","promotionId":1040035400000110,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500642000000,"endTime":1500645600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"22:00","promotionId":1040035400000113,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500645600000,"endTime":1500652800000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"00:00","promotionId":1047035500000102,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500652800000,"endTime":1500674400000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"06:00","promotionId":1047035500000108,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500674400000,"endTime":1500681600000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"08:00","promotionId":1047035500000114,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500681600000,"endTime":1500688800000,"sysTime":1500608080218}]},{"date":"07月22日","times":[{"monthOrDay":"07月22日","timeStr":"00:00","promotionId":1047035500000102,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500652800000,"endTime":1500674400000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"06:00","promotionId":1047035500000108,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500674400000,"endTime":1500681600000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"08:00","promotionId":1047035500000114,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500681600000,"endTime":1500688800000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"10:00","promotionId":1047035500000120,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500688800000,"endTime":1500696000000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"12:00","promotionId":1047035500000126,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500696000000,"endTime":1500699600000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"13:00","promotionId":1047035500000132,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500699600000,"endTime":1500706800000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"15:00","promotionId":1047035500000138,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500706800000,"endTime":1500714000000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"17:00","promotionId":1047035500000144,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500714000000,"endTime":1500721200000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"19:00","promotionId":1047035500000150,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500721200000,"endTime":1500728400000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"21:00","promotionId":1047035500000156,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500728400000,"endTime":1500732000000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"22:00","promotionId":1047035500000162,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500732000000,"endTime":1500739200000,"sysTime":1500608080218}]}]}
     * trace : 30!$1#@18%&10!$,0,62940064732522170983197
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<TimeListEntity> timeList;

        public static class TimeListEntity  implements Serializable {
            /**
             * date : 今日秒杀
             * times : [{"monthOrDay":"07月21日","timeStr":"10:00","promotionId":1040035400000092,"statusStr":"秒杀中","status":2,"yearAndMonthAndDay":"2017/07/21","startTime":1500602400000,"endTime":1500609600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"12:00","promotionId":1040035400000095,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500609600000,"endTime":1500613200000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"13:00","promotionId":1040035400000098,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500613200000,"endTime":1500620400000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"15:00","promotionId":1040035400000101,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500620400000,"endTime":1500627600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"17:00","promotionId":1040035400000104,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500627600000,"endTime":1500634800000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"19:00","promotionId":1040035400000107,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500634800000,"endTime":1500642000000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"21:00","promotionId":1040035400000110,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500642000000,"endTime":1500645600000,"sysTime":1500608080218},{"monthOrDay":"07月21日","timeStr":"22:00","promotionId":1040035400000113,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/21","startTime":1500645600000,"endTime":1500652800000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"00:00","promotionId":1047035500000102,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500652800000,"endTime":1500674400000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"06:00","promotionId":1047035500000108,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500674400000,"endTime":1500681600000,"sysTime":1500608080218},{"monthOrDay":"07月22日","timeStr":"08:00","promotionId":1047035500000114,"statusStr":"即将开始","status":1,"yearAndMonthAndDay":"2017/07/22","startTime":1500681600000,"endTime":1500688800000,"sysTime":1500608080218}]
             */

            public String date;
            public List<TimesEntity> times;

            public static class TimesEntity  implements Serializable {
                /**
                 * monthOrDay : 07月21日
                 * timeStr : 10:00
                 * promotionId : 1040035400000092
                 * statusStr : 秒杀中
                 * status : 2
                 * yearAndMonthAndDay : 2017/07/21
                 * startTime : 1500602400000
                 * endTime : 1500609600000
                 * sysTime : 1500608080218
                 */

                public boolean isSelect;
                public String monthOrDay;
                public String timeStr;
                public String promotionId;
                public String statusStr;
                public String status;
                public String yearAndMonthAndDay;
                public String startTime;
                public String endTime;
                public String sysTime;
            }
        }
    }
}
