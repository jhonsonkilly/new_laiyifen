package com.ody.p2p.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;


import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.webactivity.WebActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RemmindReceiver extends BroadcastReceiver {
    private int num = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        //String title = intent.getStringExtra ("title");
        String id = intent.getStringExtra("id");

        String message = intent.getStringExtra("mes");

        String url = intent.getStringExtra("url");


        // 创建一个NotificationManager的引用

        String ns = NOTIFICATION_SERVICE;

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);


        // 定义Notification的各种属性


        long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示

        // 用上面的属性初始化Nofification

        Intent notificationIntent = null;
        try {
            if (TextUtils.isEmpty(url)) {
                notificationIntent = new Intent(context, Class.forName("com.ody.p2p.main.productDetail.LyfProductDetailActivity"));
                notificationIntent.putExtra(Constants.SP_ID, id);
            } else {
                notificationIntent = new Intent(context, WebActivity.class);

                intent.putExtra("loadUrl", url);
            }

        } catch (Exception e) {

        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        // 用上面的属性初始化Nofification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle("来伊份");
        if (!TextUtils.isEmpty(message)) {
            builder.setContentText(message);
        } else {
            builder.setContentText("限时优惠已开始，赶紧来抢购");
        }

        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(when);
        builder.setDefaults(Notification.DEFAULT_ALL);
        mNotificationManager.notify(0, builder.build());


    }

}
