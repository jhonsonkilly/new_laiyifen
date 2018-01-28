package com.odianyun.downloadview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.odianyun.UpDateService;
import com.odianyun.uplevel.R;

import java.io.File;
import java.text.NumberFormat;

/**
 * Created by ody on 2016/8/2.
 */
public class DownloadUtils {
    /**
     * 版本升级apk文件存储地址
     */
    public static final String FILEPATH = Environment.getExternalStorageDirectory() + File.separator + "ly" + File.separator;
    public static final String DOWNLOAD_PATH = File.separator + "ly" + File.separator;


    /**
     * 获取文件名称
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(Context context, File file) {
        if (DownloadUtils.fileIsExists(file.getAbsolutePath())) {
            // TODO Auto-generated method stub
            Log.e("OpenFile", file.getName());
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            Log.d("downloderror", "存的地址不对?");
        }

    }

    /**
     * 计算百分比
     *
     * @param p
     * @return
     */
    public static String getPercentum(int p, int max) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float) p / (float) max * 100) + " %";
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===" + context.getString(R.string.status) + "===" + networkInfo[i].getState());
                    System.out.println(i + "===" + context.getString(R.string.type) + "===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查网络状态
     *
     * @param mContext
     * @return
     */
    public static int isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null ? activeNetInfo.getType() : 0;
    }

    /**
     * 检验文件是否存在
     *
     * @param url
     * @return
     */
    public static boolean fileIsExists(String url) {
        try {
            File f = new File(url);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public static void downLoadApk(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, UpDateService.class);
        intent.putExtra("apkUrl", url);
        context.startService(intent);
    }


}
