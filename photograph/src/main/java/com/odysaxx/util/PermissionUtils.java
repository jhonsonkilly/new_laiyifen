package com.odysaxx.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by lxs on 2016/9/12.
 */
public class PermissionUtils {



    public static boolean hasPermission(Context context){
        int camera = -1;
        camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        boolean permission = camera == PackageManager.PERMISSION_GRANTED;
        return permission;
    }

    public static void getPermission(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pack = pm.getPackageInfo("packageName",PackageManager.GET_PERMISSIONS);
                    String[] permissionStrings = pack.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
