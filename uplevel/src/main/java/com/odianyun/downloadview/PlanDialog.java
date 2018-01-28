package com.odianyun.downloadview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.odianyun.UpGradeBean;
import com.odianyun.downloadfile.Download;
import com.odianyun.downloadfile.LoadInfo;
import com.odianyun.uplevel.R;

import java.io.File;

import static com.ody.p2p.utils.BitmapBlurUtil.FILEPATH;

/**
 * Created by ody on 2016/7/18.
 */
public class PlanDialog extends Dialog {

    private int TheardCount = 3;//开启的线程数量

    Context mContext;
    TextView tv_jindu, tv_wangsu;
    ProgressBar pro_progress;
    String UrlPath;
    Dialog cancelDialog;
    Download downloader;
    int mFeilSize;
    boolean isDownloadOK = false;
    private int type;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://每秒更新UI
                    int length = msg.arg1;

                    tv_jindu.setText(DownloadUtils.getPercentum(length, mFeilSize));
//                    tv_wangsu.setVisibility(View.VISIBLE);
//                    tv_wangsu.setText(getInternetSpeed());//UI上不用显示网速
                    pro_progress.setProgress(length);
//                    if (length >= pro_progress.getMax() && !isDownloadOK) {
//                        isDownloadOK = true;
//                        DownloadUtils.openFile(mContext, new File(FILEPATH + DownloadUtils.getFileName(UrlPath)));
//                        downloader.delete(UrlPath);
//                        downloader.reset();
//                        dismiss();
//                    }
                    if (downloader.isDownLoadSucceed()) {
                        isDownloadOK = true;
                        DownloadUtils.openFile(mContext, new File(FILEPATH + DownloadUtils.getFileName(UrlPath)));
                        downloader.delete(UrlPath);
                        downloader.reset();
                        dismiss();
                    }
                    break;
                case 2:
                    Toast.makeText(mContext, mContext.getString(R.string.Network_connection_broken), Toast.LENGTH_LONG).show();
                    dismiss();//网络错误
                    break;
                case 3:
                    Toast.makeText(mContext, mContext.getString(R.string.up_fail), Toast.LENGTH_LONG).show();
                    dismiss();
                    break;
            }
        }
    };

    public PlanDialog(final Context mContext, final String UrlPath, int type) {
        super(mContext, R.style.update_dialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        this.mContext = mContext;
        this.UrlPath = UrlPath;
        this.type = type;
        final View view = LayoutInflater.from(mContext).inflate(R.layout.plan_layout, null);
        setContentView(view);
        tv_wangsu = (TextView) view.findViewById(R.id.tv_wangsu);
        tv_jindu = (TextView) view.findViewById(R.id.tv_jindu);
        pro_progress = (ProgressBar) view.findViewById(R.id.pro_progress);

        opeanDownload();
        CancelUpLevel();
    }

    private void opeanDownload() {
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(UrlPath, FILEPATH + DownloadUtils.getFileName(UrlPath), TheardCount + "");
    }

    /**
     * 取消更新
     */
    private void CancelUpLevel() {
        cancelDialog = new Dialog(mContext, R.style.update_dialog);
        cancelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancelDialog.setCanceledOnTouchOutside(false);
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.network_layout, null);
        cancelDialog.setContentView(view1);

        cancelDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        TextView tv_uotitle = (TextView) view1.findViewById(R.id.tv_uotitle);
        tv_uotitle.setText(mContext.getString(R.string.confirm_cancel_up));

        TextView tv_dialog_confirm = (TextView) view1.findViewById(R.id.tv_dialog_confirm);
        TextView tv_dialog_cancle = (TextView) view1.findViewById(R.id.tv_dialog_cancle);
        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != downloader) {
                    downloader.pause();
                }
                cancelDialog.dismiss();
                dismiss();
                if (type == 2) {
                    progressDialogCallBack.killAll();
                } else {
                    progressDialogCallBack.stop();
                }

            }
        });
        tv_dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });

        /**
         * 监听返回键
         */
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {
                    cancelDialog.show();
                    return true;
                }
                return false;
            }
        });
    }


    class DownloadTask extends AsyncTask<String, Integer, LoadInfo> {

        @Override
        protected LoadInfo doInBackground(String... strings) {
            downloader = new Download(UrlPath, DownloadUtils.FILEPATH + DownloadUtils.getFileName(UrlPath), TheardCount, mContext, mHandler);
            if (downloader.isdownloading()) {
                return null;
            }
            return downloader.getDownloaderInfors();
        }

        @Override
        protected void onPostExecute(LoadInfo loadInfo) {
            if (loadInfo != null) {
                // 调用方法开始下载
                mFeilSize = loadInfo.getFileSize();
                pro_progress.setMax(mFeilSize);
                downloader.opeanDownload();
            }
        }

    }

    long lastTimeStamp = 0;
    long lastTotalRxBytes = 0;

    /**
     * 获取网速
     *
     * @return
     */
    public String getInternetSpeed() {
        long nowTotalRxBytes = TrafficStats.getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        long l = speed / 1024;
        if (l > 1024) {
            return l / 1024 + " M/s";
        }
        return l + " KB/s";
    }

    private ProgressDialogCallBack progressDialogCallBack;

    public void setCallback(ProgressDialogCallBack progressDialogCallBack) {
        this.progressDialogCallBack = progressDialogCallBack;
    }

    public interface ProgressDialogCallBack {
        void stop();

        void killAll();//取消强制更新
    }

}
