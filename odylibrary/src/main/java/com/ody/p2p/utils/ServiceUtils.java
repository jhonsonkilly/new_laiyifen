package com.ody.p2p.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by lxs on 2016/7/18.
 */
public class ServiceUtils {

    /*
     *Context mContext
     * String className
     * 判断Service是否在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(100);

        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
