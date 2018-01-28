package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.WindowManager;

import com.odianyun.sharesdksharedemo.R;

import dsshare.DrawPhotoBean;

/**
 * Created by meizivskai on 2017/10/10.
 */

public class BitmapUtil {

    /**
     * Bitmap放大的方法
     */
    public static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 画海报图片
     *
     * @param drawPhotoBean
     * @return
     */
    public static Bitmap drawNewBitmap(Context mContext, DrawPhotoBean drawPhotoBean) {
        Bitmap logo = drawPhotoBean.getLogo();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = width;
        Bitmap photo = big(drawPhotoBean.getPhoto());

        //建立一个空的Bitmap
        Bitmap icon = Bitmap.createBitmap(width, (int) (height * 2.0), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        canvas.drawRGB(255, 255, 255);
        // 建立画笔
        Paint textPaint = new Paint();
        // 获取更清晰的图像采样，防抖动
        textPaint.setDither(true);
        // 过滤一下，抗剧齿
        textPaint.setFilterBitmap(true);

        canvas.save();
        canvas.drawBitmap(logo, (float) ((width - logo.getWidth()) * 0.5), 27, textPaint);
        canvas.restore();

        canvas.save();
        canvas.drawBitmap(photo, (float) ((width - photo.getWidth()) * 0.5), logo.getHeight() + 27, textPaint);
        canvas.restore();

        textPaint = new Paint();          // 创建画笔
        canvas.save();
        textPaint.setColor(mContext.getResources().getColor(R.color.gray));        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小
        Rect bounds = new Rect();
        canvas.drawLine(0, photo.getHeight() + logo.getHeight() + 100, (int) (width * 0.5 - 100), photo.getHeight() + logo.getHeight() + 100, textPaint);
        String enjoyFriend = "分享好友";
        textPaint.getTextBounds(enjoyFriend, 0, enjoyFriend.length(), bounds);
        canvas.drawText(enjoyFriend, width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 110, textPaint);
        canvas.drawLine(width / 2 + bounds.width() / 2, photo.getHeight() + logo.getHeight() + 100, width, photo.getHeight() + logo.getHeight() + 100, textPaint);
        canvas.restore();

        textPaint = new Paint();          // 创建画笔
        canvas.save();
        textPaint.setColor(mContext.getResources().getColor(R.color.light_orange));        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(70);              // 设置字体大小
        textPaint.getTextBounds(drawPhotoBean.getPrice(), 0, drawPhotoBean.getPrice().length(), bounds);
        canvas.drawText(drawPhotoBean.getPrice(), width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 220, textPaint);
        canvas.restore();

        canvas.save();
        textPaint.setColor(mContext.getResources().getColor(R.color.share_text_3));        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.getTextBounds(drawPhotoBean.getName(), 0, drawPhotoBean.getName().length(), bounds);
        canvas.drawText(drawPhotoBean.getName(), width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 300, textPaint);
        canvas.restore();

        canvas.save();
        textPaint.setColor(mContext.getResources().getColor(R.color.share_text_6));        // 设置颜色
        textPaint.setTextSize(40);              // 设置字体大小
        int spilt = 24;
        if (drawPhotoBean.getDescription().length() > spilt) {
            String oneLine = drawPhotoBean.getDescription().substring(0, spilt);
            String twoLine = drawPhotoBean.getDescription().substring(spilt, drawPhotoBean.getDescription().length());
            textPaint.getTextBounds(drawPhotoBean.getDescription(), 0, oneLine.length(), bounds);
            canvas.drawText(oneLine, width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 380, textPaint);
//            canvas.restore();
//            canvas.save();
            textPaint.getTextBounds(drawPhotoBean.getDescription(), 0, twoLine.length(), bounds);
            canvas.drawText(twoLine, width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 430, textPaint);
            canvas.restore();
        } else {
            textPaint.getTextBounds(drawPhotoBean.getDescription(), 0, drawPhotoBean.getDescription().length(), bounds);
            canvas.drawText(drawPhotoBean.getDescription(), width / 2 - bounds.width() / 2, photo.getHeight() + logo.getHeight() + 380, textPaint);
            canvas.restore();
        }


        canvas.save();
        canvas.drawBitmap(drawPhotoBean.getQRImage(), 0, photo.getHeight() + logo.getHeight() + 500, textPaint);
        canvas.restore();

        textPaint.setTextAlign(Paint.Align.LEFT);//设置居中对齐
        canvas.save();
        textPaint.setColor(Color.GRAY);        // 设置颜色
        textPaint.setTextSize(40);              // 设置字体大小
        canvas.drawText("长按图片，识别二维码", drawPhotoBean.getQRImage().getWidth(), photo.getHeight() + logo.getHeight() + 500 + 120, textPaint);
        canvas.restore();

        canvas.save();
        textPaint.setColor(Color.GRAY);        // 设置颜色
        textPaint.setTextSize(40);              // 设置字体大小
        canvas.drawText("查看商品详情", drawPhotoBean.getQRImage().getWidth(), photo.getHeight() + logo.getHeight() + 500 + 200, textPaint);
        canvas.restore();

        return icon;
    }
}
