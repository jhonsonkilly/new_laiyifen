package com.netease.nim.demo.main.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jasmin on 2017/11/9.
 */

public class TimeUtil {
    //时间戳转字符串
    public static String getStrTime(String timeStamp){
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        long  l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }
}
