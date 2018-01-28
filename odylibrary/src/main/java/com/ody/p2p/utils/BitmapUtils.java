package com.ody.p2p.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.graphics.Bitmap.createBitmap;
import static com.ody.p2p.utils.BitmapUtil.PIC_PATH;


/**
 * 图片工具类
 *
 * @author lxs
 * @version 1.0
 */
public class BitmapUtils {

    /**
     * 截取应用程序界面（去除状态栏）
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        Bitmap bitmap2 = createBitmap(bitmap, 0, statusBarHeight, OdySysEnv.SCREEN_WIDTH, OdySysEnv.SCREEN_HEIGHT - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap2;
    }

    /**
     * 截取指定view的图像
     * ps 需要注意存储权限
     *
     * @param activity
     * @param v
     * @return
     */
    public static Bitmap takeScreenShot(Activity activity, View v) {
        int x = 0, y = 0;
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        x = location[0];
        y = location[1];
        Bitmap bitmap2;
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
//            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();

            bitmap2 = createBitmap(bitmap, x, y, v.getWidth(), v.getHeight());
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(false);
            return bitmap2;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 截取应用程序界面
     *
     * @param activity 界面Activity
     * @return Bitmap对象
     */
    public static Bitmap takeFullScreenShot(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        View view = activity.getWindow().getDecorView();
        Bitmap bmp2 = createBitmap(480, 800, Config.RGB_565);
        //1、得到状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
//        System.out.println("状态栏高度：" + statusBarHeight);

        //2、得到标题栏高度
        int wintop = activity.getWindow().findViewById(android.R.id.content).getTop();
        int titleBarHeight = wintop - statusBarHeight;
//        System.out.println("标题栏高度:" + titleBarHeight);
        return bmp;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree 旋转的角度
     * @throws IOException
     */
    public static int gainPictureDegree(String path) throws Exception {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            throw (e);
        }

        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  角度
     * @param bitmap 源bitmap
     * @return Bitmap 旋转角度之后的bitmap
     */
    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        //重新构建Bitmap
        Bitmap resizedBitmap = createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * Drawable转成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap =
                    createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                    : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * Bitmap转成Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }


    /**
     * 从资源文件中获取图片
     *
     * @param context    上下文
     * @param drawableId 资源文件id
     * @return
     */
    public static Bitmap gainBitmap(Context context, int drawableId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        return bmp;
    }

    /**
     * 灰白图片（去色）
     *
     * @param bitmap 需要灰度的图片
     * @return 去色之后的图片
     */
    public static Bitmap toBlack(Bitmap bitmap) {
        Bitmap resultBMP = createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.RGB_565);
        Canvas c = new Canvas(resultBMP);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmap, 0, 0, paint);
        return resultBMP;
    }

    /**
     * 将bitmap转成 byte数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] toBtyeArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将byte数组转成 bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 将Bitmap转换成指定大小
     *
     * @param bitmap 需要改变大小的图片
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


    /**
     * 在图片右下角添加水印
     *
     * @param srcBMP  原图
     * @param markBMP 水印图片
     * @return 合成水印后的图片
     */
    public static Bitmap composeWatermark(Bitmap srcBMP, Bitmap markBMP) {
        if (srcBMP == null) {
            return null;
        }

        // 创建一个新的和SRC长度宽度一样的位图
        Bitmap newb = createBitmap(srcBMP.getWidth(), srcBMP.getHeight(), Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        // 在 0，0坐标开始画入原图
        cv.drawBitmap(srcBMP, 0, 0, null);
        // 在原图的右下角画入水印
        cv.drawBitmap(markBMP, srcBMP.getWidth() - markBMP.getWidth() + 5, srcBMP.getHeight() - markBMP.getHeight() + 5, null);
        // 保存
        cv.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        cv.restore();

        return newb;
    }

    /**
     * 将图片转成指定弧度（角度）的图片
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        //根据图片创建画布
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 缩放图片
     *
     * @param bmp  需要缩放的图片源
     * @param newW 需要缩放成的图片宽度
     * @param newH 需要缩放成的图片高度
     * @return 缩放后的图片
     */
    public static Bitmap zoom(Bitmap bmp, int newW, int newH) {

        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // 计算缩放比例
        float scaleWidth = ((float) newW) / width;
        float scaleHeight = ((float) newH) / height;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newbm = createBitmap(bmp, 0, 0, width, height, matrix, true);

        return newbm;
    }

    /**
     * 获得倒影的图片
     *
     * @param bitmap 原始图片
     * @return 带倒影的图片
     */
    public static Bitmap makeReflectionImage(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = createBitmap(width, (height + height / 2), Config.ARGB_8888);

        Paint deafalutPaint = new Paint();
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 获取验证码图片
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @return 验证码Bitmap对象
     */
    public synchronized static Bitmap makeValidateCode(int width, int height) {
        return ValidateCodeGenerator.createBitmap(width, height);
    }

    /**
     * 获取验证码值
     *
     * @return 验证码字符串
     */
    public synchronized static String gainValidateCodeValue() {
        return ValidateCodeGenerator.getCode();
    }

    final static class ValidateCodeGenerator {
        private static final char[] CHARS = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        //default settings
        private static final int DEFAULT_CODE_LENGTH = 4;
        private static final int DEFAULT_FONT_SIZE = 20;
        private static final int DEFAULT_LINE_NUMBER = 3;
        private static final int BASE_PADDING_LEFT = 5, RANGE_PADDING_LEFT = 10, BASE_PADDING_TOP = 15, RANGE_PADDING_TOP = 10;
        private static final int DEFAULT_WIDTH = 60, DEFAULT_HEIGHT = 30;

        //variables
        private static String value;
        private static int padding_left, padding_top;
        private static Random random = new Random();

        public static Bitmap createBitmap(int width, int height) {
            padding_left = 0;
            //创建画布
            Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas c = new Canvas(bp);

            //随机生成验证码字符
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
                buffer.append(CHARS[random.nextInt(CHARS.length)]);
            }
            value = buffer.toString();

            //设置颜色
            c.drawColor(Color.WHITE);

            //设置画笔大小
            Paint paint = new Paint();
            paint.setTextSize(DEFAULT_FONT_SIZE);
            for (int i = 0; i < value.length(); i++) {
                //随机样式
                randomTextStyle(paint);
                padding_left += BASE_PADDING_LEFT + random.nextInt(RANGE_PADDING_LEFT);
                padding_top = BASE_PADDING_TOP + random.nextInt(RANGE_PADDING_TOP);
                c.drawText(value.charAt(i) + "", padding_left, padding_top, paint);
            }
            for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
                drawLine(c, paint);
            }
            //保存
            c.save(Canvas.ALL_SAVE_FLAG);
            c.restore();

            return bp;
        }

        public static String getCode() {
            return value;
        }

        private static void randomTextStyle(Paint paint) {
            int color = randomColor(1);
            paint.setColor(color);
            paint.setFakeBoldText(random.nextBoolean());//true为粗体，false为非粗体
            float skewX = random.nextInt(11) / 10;
            skewX = random.nextBoolean() ? skewX : -skewX;
            paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
            paint.setUnderlineText(true); //true为下划线，false为非下划线
            paint.setStrikeThruText(true); //true为删除线，false为非删除线
        }

        private static void drawLine(Canvas canvas, Paint paint) {
            int color = randomColor(1);
            int startX = random.nextInt(DEFAULT_WIDTH);
            int startY = random.nextInt(DEFAULT_HEIGHT);
            int stopX = random.nextInt(DEFAULT_WIDTH);
            int stopY = random.nextInt(DEFAULT_HEIGHT);
            paint.setStrokeWidth(1);
            paint.setColor(color);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        private static int randomColor(int rate) {
            int red = random.nextInt(256) / rate;
            int green = random.nextInt(256) / rate;
            int blue = random.nextInt(256) / rate;
            return Color.rgb(red, green, blue);
        }
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * PxUtils.dipTopx(14) / bitmap.getHeight(), PxUtils.dipTopx(14), true);
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapAbSolute(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存bitmap图片至本地
     *
     * @param bitmap
     * @return
     */
    public static String saveBitmapFile(Bitmap bitmap) {
        return saveBitmapFile(null, bitmap);
    }

    /**
     * 保存bitmap图片至本地
     *
     * @param bitmap
     * @return
     */
    public static String saveBitmapFile(Context context, Bitmap bitmap) {

        if (bitmap == null) {
            return null;
        }
        File f = new File(Constants.IMAGEPATH);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
        File file = new File(Constants.IMAGEPATH + System.currentTimeMillis() + ".jpg");
        BufferedOutputStream bos = null;
        String path = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.JPEG, IMAGE_COMPRESSION_QUALITY, bos);
            bos.flush();
            path = file.getAbsolutePath();
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
        if (path != null) {
            File file2 = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int kb = (int) file2.length() / 1024;

            // 最后通知图库更新
            if (null != context) {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(new File(path))));
            }
            Log.i("BitmapUtil", "length = " + kb + "KB, " + "outWidth:outHeight = " + options.outWidth + ":" + options.outHeight);
        }
        return path;
    }

    /**
     * 随机生成验证码内部类
     */
    private static final int IMAGE_COMPRESSION_QUALITY = 95;

    /**
     * 在原图右下角加二维码
     *
     * @param context
     * @param picUrl
     * @return
     */
    public static Bitmap drawableBitMap(Context context, String picUrl, String qrurl) {
        try {
            Bitmap bitmap = getBitmapAbSolute(picUrl);
            Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(bit);
//            canvas.drawRGB(255, 255, 255);
            // 建立画笔
            Paint textPaint = new Paint();
            // 获取更清晰的图像采样，防抖动
            textPaint.setDither(true);
            // 过滤一下，抗剧齿
            textPaint.setFilterBitmap(true);
            canvas.drawBitmap(bitmap, 0, 0, textPaint);

            ImageView img = new ImageView(context);
            img.setImageBitmap(com.odysaxx.util.BitmapUtils.createQRImage(qrurl, 300, 300));

            Bitmap markBMP = ((BitmapDrawable) img.getDrawable()).getBitmap();
            // 在原图的底部居中填充
            canvas.drawBitmap(
                    markBMP,
                    (bitmap.getWidth() - markBMP.getWidth()) / 2,
                    bitmap.getHeight() - markBMP.getHeight() - 150,
                    null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            // 存储
            canvas.restore();
            return bit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
