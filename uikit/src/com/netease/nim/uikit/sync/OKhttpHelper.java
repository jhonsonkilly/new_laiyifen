package com.netease.nim.uikit.sync;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;

import com.netease.nim.uikit.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author SevenCheng
 */

public class OKhttpHelper {

    public static List<Dialog> mDialogs = new ArrayList<>();
    private static OnSendSuccessListener mOnSendSuccessListener;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static AlertDialog mDialog;
    private static Handler handler = new Handler(Looper.getMainLooper());
    ;


    /**
     * @param body                  josn格式的参数
     * @param url                   请求地址
     * @param onSendSuccessListener 回调
     */
    public static void send(final Context mContext, final String body, final String url, OnSendSuccessListener onSendSuccessListener) {
        mOnSendSuccessListener = onSendSuccessListener;
        Log.e("body:", body);
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);
            builder.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            mDialog = builder.create();
            mDialog.show();
            mDialogs.add(mDialog);

        } catch (Exception e) {

        } finally {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = RequestBody.create(JSON, body);
                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            final String string = response.body().string();
                            Log.e("下载:", string);
                            if (mOnSendSuccessListener != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendSuccess(string);
                                    }
                                });
                            }

                        } else {
                            Log.e("下载失败:", response.message());
                            if (mOnSendSuccessListener != null) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendFail(response.message());
                                    }
                                });


                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        for (Dialog dialog : mDialogs) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }).start();
        }
    }


    public static void putWithHeader(final Context mContext, final Map header, final String body, final String url, OnSendSuccessListener onSendSuccessListener) {
        mOnSendSuccessListener = onSendSuccessListener;
        Log.e("body:", body);
        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);
            builder.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            mDialog = builder.create();
            mDialog.show();
            mDialogs.add(mDialog);

        } catch (Exception e) {

        } finally {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = RequestBody.create(JSON, body);
                        Request request = new Request.Builder()
                                .url(url)
                                .put(requestBody)
                                .headers(Headers.of(header))
                                .build();
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            final String string = response.body().string();
                            Log.e("下载:", string);
                            if (mOnSendSuccessListener != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendSuccess(string);
                                    }
                                });
                            }

                        } else {
                            Log.e("下载失败:", response.message());
                            if (mOnSendSuccessListener != null) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendFail(response.message());
                                    }
                                });

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        for (Dialog dialog : mDialogs) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public static void sendWithHeader(final Context mContext, final Map header, final String body, final String url, OnSendSuccessListener onSendSuccessListener) {
        mOnSendSuccessListener = onSendSuccessListener;
        Log.e("body:", body);
        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);
            builder.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            mDialog = builder.create();
            mDialog.show();
            mDialogs.add(mDialog);

        } catch (Exception e) {

        } finally {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = RequestBody.create(JSON, body);
                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .headers(Headers.of(header))
                                .build();
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            final String string = response.body().string();
                            Log.e("下载:", string);
                            if (mOnSendSuccessListener != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendSuccess(string);
                                    }
                                });
                            }

                        } else {
                            Log.e("下载失败:", response.message());
                            if (mOnSendSuccessListener != null) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendFail(response.message());
                                    }
                                });

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        for (Dialog dialog : mDialogs) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }).start();
        }
    }


    public static void getSend(final Context mContext, final String url, OnSendSuccessListener onSendSuccessListener) {
        mOnSendSuccessListener = onSendSuccessListener;
        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);

            builder.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            mDialog = builder.create();
            mDialog.show();
            mDialogs.add(mDialog);

        } catch (Exception e) {

        } finally {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .build();
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            final String string = response.body().string();
                            Log.e("下载:", string);
                            if (mOnSendSuccessListener != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendSuccess(string);
                                    }
                                });
                            }

                        } else {
                            Log.e("下载失败:", response.message());
                            if (mOnSendSuccessListener != null) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendFail(response.message());
                                    }
                                });

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        for (Dialog dialog : mDialogs) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }).start();
        }
    }


    public static void getWithHeader(final Context mContext, final Map header, final String url, OnSendSuccessListener onSendSuccessListener) {
        mOnSendSuccessListener = onSendSuccessListener;
        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.customDialog);

            builder.setView(LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null));
            mDialog = builder.create();
            mDialog.show();
            mDialogs.add(mDialog);

        } catch (Exception e) {

        } finally {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .headers(Headers.of(header))
                                .build();
                        final Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {

                            final String string = response.body().string();
                            Log.e("下载:", string);
                            if (mOnSendSuccessListener != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendSuccess(string);
                                    }
                                });
                            }

                        } else {
                            Log.e("下载失败:", response.message());
                            if (mOnSendSuccessListener != null) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnSendSuccessListener.onSendFail(response.message());
                                    }
                                });

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        for (Dialog dialog : mDialogs) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
            }).start();
        }
    }


    public interface OnSendSuccessListener {
        void onSendSuccess(String s);

        void onSendFail(String err);
    }

}
