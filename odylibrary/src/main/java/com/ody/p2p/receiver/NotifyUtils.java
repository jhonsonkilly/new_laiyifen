package com.ody.p2p.receiver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ody.p2p.Constants;
import com.ody.p2p.R;

import java.util.Calendar;

/**
 * Created by lxs on 2017/2/20.
 */
public class NotifyUtils {


    public static String message = "";

    public static String loadUrl = "";


    // 默认显示的的Notification
    public static void showNotification(Context context, Class cls) {

        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("loadUrl", Constants.H5_URL + loadUrl);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("来伊份")
                .setContentText(message)
                .setContentIntent(p)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1001, notificationBuilder.build());
    }


    public static void cancelNotification(Context context, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1001);
    }

    public static void pendingNotify(Context context, Class cls, int hour, int min, String url, String mes) {
        loadUrl = url;
        message = mes;
        long intervalMillis = 24 * 60 * 60 * 1000;
        int id = 0;
        Intent intent = new Intent(context, cls);
        intent.putExtra("intervalMillis", intervalMillis);
        intent.putExtra("id", id);
        intent.setAction("lyf.lingzaocan");
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        long firstTime;
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
// 这里时区需要设置一下，不然会有8个小时的时间差
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
// 选择的定时时间
        long selectTime = calendar.getTimeInMillis();
// 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if (systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        firstTime = selectTime;
        AlarmManager manager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setWindow(AlarmManager.RTC_WAKEUP, firstTime, intervalMillis, sender);
        } else {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, firstTime, intervalMillis, sender);
        }
    }


    public static void cancelAlarm(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction("lyf.lingzaocan");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        manager.cancel(sender);
    }

}
