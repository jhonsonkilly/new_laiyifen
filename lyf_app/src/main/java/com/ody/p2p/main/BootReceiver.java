package com.ody.p2p.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ody on 2016/7/17.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.e("test",context.getString(R.string.install_up) + packageName + context.getString(R.string.Package_name_program));
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Log.e("test",context.getString(R.string.uninstallup) + packageName + context.getString(R.string.Package_name_program));
        }
    }
}
