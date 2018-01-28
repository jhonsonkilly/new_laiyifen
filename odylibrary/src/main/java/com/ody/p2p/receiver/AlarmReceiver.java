package com.ody.p2p.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.ody.p2p.webactivity.WebActivity;

/**
 * Created by lxs on 2017/2/20.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("lyf.lingzaocan"))
        {
            long intervalMillis = intent.getLongExtra("intervalMillis", 0);
            if (intervalMillis != 0) {
                setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                        intent);
            }
            NotifyUtils.showNotification(context, WebActivity.class);
        }

    }

    public void setAlarmTime(Context context, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = (int) intent.getLongExtra("intervalMillis", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
    }
}
