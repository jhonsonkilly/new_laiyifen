package com.ody.p2p.retrofit.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/7/21.
 */

public class QiangGouBean implements Serializable {

    public String code;
    public String message;
    public String errMsg;
    public QiangGouData data;

    public class QiangGouData implements Serializable {
        public List<TimeList> timeList;

        public class TimeList implements Serializable {
            public String data;
            public List<Times> times;

            public class Times implements Serializable {
                public String monthOrDay;
                public String timeStr;
                public String promotionId;
                public String statusStr;
                public String status;
                public String yearAndMonthAndDay;
                public String startTime;
                public String endTime;
                public String sysTime;

                public boolean isSelect;
            }
        }
    }
}
