package com.netease.nim.demo.sync.util;

import android.util.Log;


public class LogUtils {

    /**
     * 如果正式发布 值设为false
     */
    private static boolean IsDebug = true;
    public static void LogV(String tag, String msg) {

        if (IsDebug) {
            Log.v(tag, msg);
        }

    }

    public static void LogV(String msg) {

        if (IsDebug) {

            if (msg.length() > 4000) {
                for (int i = 0; i < msg.length(); i += 4000) {
                    if (i + 4000 < msg.length())
                        Log.i("rescounter" + i, msg.substring(i, i + 4000));
                    else
                        Log.i("rescounter" + i, msg.substring(i, msg.length()));
                }
            } else
                Log.i("log ==", msg);
        }

    }
}
