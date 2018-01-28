package com.ody.p2p.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.webactivity.UrlBean;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.R.attr.path;
import static com.odysaxx.photograph.Application.getContext;

public class BitmapUtil {
    public static final int UPLOAD_LARGE_IMAGE_MAX_1080 = 1080;
    public static final int UPLOAD_MIDDLE_IMAGE_MAX_720 = 720;
    public static final int UPLOAD_SMALL_IMAGE_MAX_480 = 480;
    public static final int UPLOAD_MINI_IMAGE_MAX_100 = 100;

    public static final int DOWNLOAD_LARGE_IMAGE_MAX_1080 = 1080;
    public static final int DOWNLOAD_MIDDLE_IMAGE_MAX_720 = 720;
    public static final int DOWNLOAD_SMALL_IMAGE_MAX_480 = 480;
    public static final int DOWNLOAD_MINI_IMAGE_MAX_100 = 100;

    private static final int IMAGE_COMPRESSION_QUALITY = 95;

    public static String PIC_PATH = StringUtils.getAppFIlePath() + "/pic/";

    public static String saveBitmapFile(Bitmap bitmap) {
        return saveBitmapFile(bitmap, Constants.IMAGEPATH);
    }

    public static Observable<String> saveBitmapFile(final Activity context, final Bitmap bitmap, final String imgPath) {
        if (bitmap == null) {
            return Observable.empty();
        }
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                PermissionUtils.checkPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (!subscriber.isUnsubscribed()) {
                                    if (aBoolean) {
                                        String imagPath = saveBitmapFile(bitmap, imgPath);
                                        if (StringUtils.isEmpty(imgPath)) {
                                            subscriber.onError(new Exception("保存失败"));
                                        } else {
                                            subscriber.onNext(imagPath);
                                        }
                                    } else {//未开启权限
                                        subscriber.onError(new Exception("未开启权限"));
                                    }
                                    subscriber.onCompleted();
                                }
                            }
                        });
            }
        });
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @return
     */
    public static String saveBitmapFile(Bitmap bitmap, String imgPath) {
        if (bitmap == null) {
            return "";
        }
        File f = new File(imgPath);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
        File file = new File(imgPath + System.currentTimeMillis() + ".jpg");
        BufferedOutputStream bos = null;
        String path = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.JPEG, IMAGE_COMPRESSION_QUALITY, bos);
            bos.flush();
            path = file.getAbsolutePath();
            if (path != null) {
                File file2 = new File(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                int kb = (int) file2.length() / 1024;
                Log.i("BitmapUtil", "length = " + kb + "KB, " + "outWidth:outHeight = " + options.outWidth + ":" + options.outHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
//            ToastUtils.showShort(e.toString());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return path;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        Log.i("BitmapUtil", "width ：height = " + width + ":" + height);
        Log.i("BitmapUtil", "reqWidth ：reqHeight = " + reqWidth + ":" + reqHeight);
        if (height > reqHeight || width > reqWidth) {
            float heightRatio = height * 1.0f / reqHeight;
            float widthRatio = width * 1.0f / reqWidth;
            Log.i("BitmapUtil", "widthRatio ：heightRatio = " + widthRatio + ":" + heightRatio);
            inSampleSize = Math.round(heightRatio < widthRatio ? heightRatio : widthRatio);
        }
        return inSampleSize < 1 ? 1 : inSampleSize;
    }

    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;


        //根据尺寸算一个压缩因子
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int sampleSize = calculateInSampleSize(options, w, h);
        options.inJustDecodeBounds = false;
        Log.i("BitmapUtil", "sampleSize 1 = " + sampleSize);

        //大于200K,根据文件大小算一个压缩因子
        int fileSize = 1;
        File f = new File(filePath);
        Log.i("BitmapUtil", "inSampleSize f.length() = " + f.length());
        if (f.exists() && f.length() > 200 * 1024) {
            int scale = (int) (f.length() / (200 * 1024));
            Log.i("BitmapUtil", "inSampleSize scale = " + scale);
            fileSize = (int) Math.round(Math.sqrt(scale));
            Log.i("BitmapUtil", "fileSize 2 = " + fileSize);
        }

        options.inSampleSize = Math.max(sampleSize, fileSize);//取最大值
        Log.i("BitmapUtil", "options.inSampleSize 3 = " + options.inSampleSize);
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getBitmap(String path) {
        if (path == null) {
            return null;
        }
        File f = new File(path);
        if (f.exists()) {
            return BitmapFactory.decodeFile(f.getAbsolutePath());
        } else {
            return null;
        }

    }

    private Bitmap readBitMap(Context context, String fileName) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        if (StringUtils.getScreenWidth((Activity) context) < 720) {
            opt.inSampleSize = 2;
        }
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String check(String path) {
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")
                || path.endsWith(".JPG") || path.endsWith(".JPEG")) {
            return path;
        }
        Bitmap bmp = getSmallBitmap(path, 50, 50);
        return saveBitmapFile(bmp);
    }

    public static boolean checkCanSend(String path) {
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")
                || path.endsWith(".JPG") || path.endsWith(".JPEG")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isImage(String fileName) {
        // TODO Auto-generated method stub
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        if (fileName.endsWith(".png") || fileName.endsWith(".PNG")
                || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                || fileName.endsWith(".JPG") || fileName.endsWith(".JPEG")) {
            return true;
        }
        return false;
    }

    public static String toJPG(File absoluteFile) {

        return null;
    }


    public static final int IMAGE_MAX_SIZE_LIMIT = 100;
    private static final int NUMBER_OF_RESIZE_ATTEMPTS = 100;
    private static final int MINIMUM_IMAGE_COMPRESSION_QUALITY = 50;

    public static String compressImage(String path) {

        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);

        int outWidth = op.outWidth;
        int outHeight = op.outHeight;

        int widthLimit = op.outWidth;
        int heightLimit = op.outHeight;

        int byteLimit = IMAGE_MAX_SIZE_LIMIT * 1024;

        float scaleFactor = 1.F;
        while ((outWidth * scaleFactor > widthLimit)
                || (outHeight * scaleFactor > heightLimit)) {
            scaleFactor *= .75F;
        }
        try {
            ByteArrayOutputStream os = null;
            int attempts = 1;
            int sampleSize = 1;
            BitmapFactory.Options options = new BitmapFactory.Options();
            int quality = IMAGE_COMPRESSION_QUALITY;
            Bitmap b = null;
            do {
                options.inSampleSize = sampleSize;
                try {
                    b = BitmapFactory.decodeFile(path, options);
                    if (b == null) {
                        return null; // Couldn't decode and it wasn't because of
                    }
                } catch (OutOfMemoryError e) {
                    sampleSize *= 2; // works best as a power of two
                    attempts++;
                    continue;
                }
            } while (b == null && attempts < NUMBER_OF_RESIZE_ATTEMPTS);

            if (b == null) {
                return null;
            }
            boolean resultTooBig = true;
            attempts = 1; // reset count for second loop
            do {
                try {
                    if (options.outWidth > widthLimit
                            || options.outHeight > heightLimit
                            || (os != null && os.size() > byteLimit)) {
                        int scaledWidth = (int) (outWidth * scaleFactor);
                        int scaledHeight = (int) (outHeight * scaleFactor);

                        b = Bitmap.createScaledBitmap(b, scaledWidth,
                                scaledHeight, false);
                        if (b == null) {
                            return null;
                        }
                    }
                    os = new ByteArrayOutputStream();
                    b.compress(CompressFormat.JPEG, quality, os);
                    int jpgFileSize = os.size();
                    if (jpgFileSize > byteLimit) {
                        quality = (quality * byteLimit) / jpgFileSize; // watch
                        if (quality < MINIMUM_IMAGE_COMPRESSION_QUALITY) {
                            quality = MINIMUM_IMAGE_COMPRESSION_QUALITY;
                        }
                        os = new ByteArrayOutputStream();
                        b.compress(CompressFormat.JPEG, quality, os);
                    }
                } catch (java.lang.OutOfMemoryError e) {
                    // Log.w(TAG,
                }
                scaleFactor *= .75F;
                attempts++;
                resultTooBig = os == null || os.size() > byteLimit;
            } while (resultTooBig && attempts < NUMBER_OF_RESIZE_ATTEMPTS);
            b.recycle(); // done with the bitmap, release the memory
            if (resultTooBig) {
                return path;
            }
            FileOutputStream out = null;
            try {
                String p = Constants.IMAGEPATH + System.currentTimeMillis() + ".jpg";
                File file = new File(Constants.IMAGEPATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                out = new FileOutputStream(p);
                out.write(os.toByteArray());
                out.close();
                return p;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return path;

        } catch (java.lang.OutOfMemoryError e) {

            return path;
        }
    }
}
