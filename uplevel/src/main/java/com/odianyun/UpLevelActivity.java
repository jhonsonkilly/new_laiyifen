package com.odianyun;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.odianyun.downloadview.DownloadUtils;
import com.odianyun.downloadview.NetWorkDialog;
import com.odianyun.downloadview.PlanDialog;
import com.odianyun.downloadview.UpLevelDialog;
import com.odianyun.uplevel.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class UpLevelActivity extends Activity {

    private DownloadManager downloadManager;
    private SharedPreferences prefs;
    private static final String DL_ID = "downloadId";


    String appName;//文件名称
    String describe;//描述
    String obtainUrl;//下载路径
    String versionName;//版本名称
    String versionCode;//版本code
    String packageSize;//文件大小
    int type = 1;// 0 不更新  1 选择更新  2 强制更新
    private UpLevelDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_level);
        appName = getIntent().getStringExtra("appName");
        describe = getIntent().getStringExtra("describe");
        obtainUrl = getIntent().getStringExtra("obtainUrl");
        versionName = getIntent().getStringExtra("versionName");
        versionCode = getIntent().getStringExtra("versionCode");
        packageSize = getIntent().getStringExtra("packageSize");
        type = getIntent().getIntExtra("type", 1);

        dialog = new UpLevelDialog(this, obtainUrl);
        if (type == 2) {
            dialog.tv_updata_no.setVisibility(View.GONE);
            dialog.tv_cancel.setVisibility(View.GONE);
        }
        dialog.setUpLevelBack(new UpLevelDialog.UpLevelBack() {
            @Override
            public void updataNo() {
                //不在提示需要做的操作
                finish();
            }

            @Override
            public void activityfinish() {
                finish();
            }

            @Override
            public void startDialog() {
                if (DownloadUtils.isWifi(UpLevelActivity.this) == ConnectivityManager.TYPE_WIFI) {//检测是否是wifi状态

                    startDownload();
                } else {
                    NetWorkDialog netWorkDialog = new NetWorkDialog(UpLevelActivity.this, obtainUrl);
                    netWorkDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                    netWorkDialog.setNetWorkListener(new NetWorkDialog.NetworkDialogCallBack() {
                        @Override
                        public void continueUpadate() {


                            startDownload();

                        }

                        @Override
                        public void cancel() {
                            if (type == 2) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("kill_main", 1);
                                JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                                finish();
                            } else {
                                finish();
                            }
                        }
                    });
                    netWorkDialog.show();
                }
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
    }

    public void startDownload() {
        if (type != 2) {
            DownloadUtils.downLoadApk(this, obtainUrl);
            finish();
        } else {
            PlanDialog planDialog = new PlanDialog(UpLevelActivity.this, obtainUrl, type);
            planDialog.setCallback(new PlanDialog.ProgressDialogCallBack() {
                @Override
                public void stop() {
                    finish();
                }

                @Override
                public void killAll() {
                    Bundle bundle = new Bundle();
                    bundle.putInt("kill_main", 1);
                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    finish();
                }
            });
            planDialog.show();
        }
    }

}
