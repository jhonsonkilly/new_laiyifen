package com.odianyun.downloadfile;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.odianyun.downloadview.DownloadUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzy on 2016/7/21.
 */
public class Download {
    private String urlstr;// 下载的地址
    private String localfile;// 保存路径
    private int threadcount;// 线程数
    private Handler mHandler;// 消息处理器
    private int fileSize;// 所要下载的文件的大小
    private Context context;
    private List<DownloadInfo> infos;// 存放下载信息类的集合
    private static final int INIT = 1;//定义三种下载的状态：初始化状态，正在下载状态，暂停状态
    private static final int DOWNLOADING = 2;
    private static final int PAUSE = 3;
    private static final int DOWNLOAD_COMPLETE = 4;
    private int state = INIT;
    int DownloadfileSize = 0;
    int TIME = 1000;//刷新间隔

    public Download(String urlstr, String localfile, int threadcount,
                    final Context context, final Handler mHandler) {
        this.urlstr = urlstr;
        this.localfile = localfile;
        this.threadcount = threadcount;
        this.mHandler = mHandler;
        this.context = context;
    }

    /**
     * 判断是否正在下载
     */
    public boolean isdownloading() {
        return state == DOWNLOADING;
    }

    /**
     * 检验是否下载完成
     *
     * @return
     */
    public boolean isDownLoadSucceed() {
        return state == DOWNLOAD_COMPLETE;
    }


    /**
     * 得到downloader里的信息
     * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
     * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器
     */
    public LoadInfo getDownloaderInfors() {
        if (isFirst(urlstr)) {
            Log.v("TAG", "isFirst");
            init();
            int range = fileSize / threadcount;
            infos = new ArrayList<DownloadInfo>();
            for (int i = 0; i < threadcount - 1; i++) {
                DownloadInfo info = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, urlstr);
                infos.add(info);
            }
            DownloadInfo info = new DownloadInfo(threadcount - 1, (threadcount - 1) * range, fileSize - 1, 0, urlstr);
            infos.add(info);
            //保存infos中的数据到数据库
            GetDownloadInfos.getInstance(context).saveInfos(infos);
            //创建一个LoadInfo对象记载下载器的具体信息
            LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);
            return loadInfo;
        } else {
            //得到数据库中已有的urlstr的下载器的具体信息
            infos = GetDownloadInfos.getInstance(context).getInfos(urlstr);
            Log.v("TAG", "not isFirst size=" + infos.size());
            int size = 0;
            int compeleteSize = 0;
            for (DownloadInfo info : infos) {
                size += info.getEndPos() - info.getStartPos() + 1;
                compeleteSize += info.getCompeleteSize();
                DownloadfileSize += info.getCompeleteSize();
            }
            return new LoadInfo(size, compeleteSize, urlstr);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        state = INIT;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(500);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(localfile);
            if (!file.exists()) {
                file.getParentFile().mkdirs();//新建文件夹
                file.createNewFile();//新建文件
            }
            // 本地访问文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是第一次 下载
     */
    private boolean isFirst(String urlstr) {
        return GetDownloadInfos.getInstance(context).isHasInfors(urlstr);
    }

    /**
     * 利用线程开始下载数据
     */
    public void opeanDownload() {
        if (infos != null) {
            if (state == DOWNLOADING)
                return;
            state = DOWNLOADING;
            for (DownloadInfo info : infos) {
                new MyThread(info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompeleteSize(), info.getUrl()).start();
            }
            upUI();
        }
    }

    private void upUI() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (state == PAUSE) {
                        return;
                    }
                    if (DownloadUtils.isNetworkAvailable((Activity) context)) {//判断网络状态是否正常
                        mHandler.postDelayed(this, TIME);
                        Message message = Message.obtain();
                        message.what = 1;
                        message.arg1 = DownloadfileSize;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = Message.obtain();
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, TIME);
    }

    public class MyThread extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int compeleteSize;
        private String urlstr;

        public MyThread(int threadId, int startPos, int endPos,
                        int compeleteSize, String urlstr) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.compeleteSize = compeleteSize;
            this.urlstr = urlstr;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;
            try {
                URL url = new URL(urlstr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置范围，格式为Range：bytes x-y;
                connection.setRequestProperty("Range", "bytes=" + (startPos + compeleteSize) + "-" + endPos);

                randomAccessFile = new RandomAccessFile(localfile, "rwd");
                randomAccessFile.seek(startPos + compeleteSize);
                // 将要下载的文件写到保存在保存路径下的文件中
                is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    compeleteSize += length;
                    DownloadfileSize += length;
                    // 更新数据库中的下载信息
                    GetDownloadInfos.getInstance(context).updataInfos(threadId, compeleteSize, urlstr);
                    // 用消息将下载信息传给进度条，对进度条进行更新
                    Log.v("TAG", "isdownloaf..DownloadfileSize=" + DownloadfileSize);
                    if (state == PAUSE) {
                        return;
                    }
                }
                state = DOWNLOAD_COMPLETE;
                delete(urlstr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                delete(urlstr);
                Message message = Message.obtain();
                message.what = 3;
                mHandler.sendMessage(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //删除数据库中urlstr对应的下载器信息
    public void delete(String urlstr) {
        GetDownloadInfos.getInstance(context).delete(urlstr);
    }

    //设置暂停
    public void pause() {
        state = PAUSE;
    }

    //重置下载状态
    public void reset() {
        state = INIT;
    }
}
