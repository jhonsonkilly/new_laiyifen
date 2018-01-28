package com.ody.p2p.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by pzy on 2016/12/1.
 */

public class CodeUtils {
    private static final char[] CHARS_a = {//不区分大小写
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final char[] CHARS_A = {//区分大小写
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
            ,'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static CodeUtils mCodeUtils;
    private int mPaddingLeft, mPaddingTop;
    private StringBuilder mBuilder = new StringBuilder();
    private Random mRandom = new Random();

    //Default Settings
    private boolean CASE_SENSITIVE=false;//区分大小写
    private int DEFAULT_CODE_LENGTH = 6;//验证码的长度  这里是6位
    private int DEFAULT_FONT_SIZE = 60;//字体大小
    private int DEFAULT_LINE_NUMBER = 5;//干扰线条数
    private int BASE_PADDING_LEFT = 20; //左边距
    private int RANGE_PADDING_LEFT = 30;//左边距范围值
    private int BASE_PADDING_TOP = 70;//上边距
    private int RANGE_PADDING_TOP = 15;//上边距范围值
    private int DEFAULT_WIDTH = 300;//默认宽度.图片的总宽
    private int DEFAULT_HEIGHT = 100;//默认高度.图片的总高
    private int DEFAULT_COLOR = 0xDF;//默认背景颜色值

    /**
     * 是否区分大小写
     * @param caseSensitive
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.CASE_SENSITIVE = caseSensitive;
    }

    /**
     * 背景颜色值
     * @param DEFAULT_COLOR
     */
    public void setBackground(int DEFAULT_COLOR) {
        this.DEFAULT_COLOR = DEFAULT_COLOR;
    }

    /**
     * 图片的高度.
     * @param DEFAULT_HEIGHT
     */
    public void setImageHeigth(int DEFAULT_HEIGHT) {
        this.DEFAULT_HEIGHT = DEFAULT_HEIGHT;
    }

    /**
     * 图片的宽度
     * @param DEFAULT_WIDTH
     */
    public void setImageWidth(int DEFAULT_WIDTH) {
        this.DEFAULT_WIDTH = DEFAULT_WIDTH;
    }

    /**
     * 验证码的长度
     * @param DEFAULT_CODE_LENGTH
     */
    public void setCodeLength(int DEFAULT_CODE_LENGTH) {
        this.DEFAULT_CODE_LENGTH = DEFAULT_CODE_LENGTH;
    }

    /**
     * 验证码的字体大小
     * @param DEFAULT_FONT_SIZE
     */
    public void setFontSize(int DEFAULT_FONT_SIZE) {
        this.DEFAULT_FONT_SIZE = DEFAULT_FONT_SIZE;
    }

    private String code;

    public static CodeUtils getInstance() {
        if (mCodeUtils == null) {
            mCodeUtils = new CodeUtils();
        }
        return mCodeUtils;
    }

    //生成验证码图片
    public Bitmap createBitmap() {
        return createBitmap(null);
    }

    public Bitmap createBitmap(String codeNum){
        mPaddingLeft = 0; //每次生成验证码图片时初始化
        mPaddingTop = 0;

        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (StringUtils.isEmpty(codeNum)){
            code = createCode();
        }else{
            code=codeNum;
        }
        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i) + "", mPaddingLeft, mPaddingTop, paint);
        }

        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }

    /**
     * 得到图片中的验证码字符串
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    //生成验证码
    public String createCode() {
        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容

        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
            if(CASE_SENSITIVE){
                mBuilder.append(CHARS_A[mRandom.nextInt(CHARS_A.length)]);
            }else{
                mBuilder.append(CHARS_a[mRandom.nextInt(CHARS_a.length)]);
            }
        }

        return mBuilder.toString();
    }

    //生成干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = mRandom.nextInt(DEFAULT_WIDTH);
        int startY = mRandom.nextInt(DEFAULT_HEIGHT);
        int stopX = mRandom.nextInt(DEFAULT_WIDTH);
        int stopY = mRandom.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    //随机颜色
    private int randomColor() {
        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容

        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(mRandom.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }

            mBuilder.append(haxString);
        }

        return Color.parseColor("#" + mBuilder.toString());
    }

    //随机文本样式
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(mRandom.nextBoolean());  //true为粗体，false为非粗体
        float skewX = mRandom.nextInt(11) / 10;
        skewX = mRandom.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
//        paint.setUnderlineText(true); //true为下划线，false为非下划线
//        paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    //随机间距
    private void randomPadding() {
        mPaddingLeft += BASE_PADDING_LEFT + mRandom.nextInt(RANGE_PADDING_LEFT);
        mPaddingTop = BASE_PADDING_TOP + mRandom.nextInt(RANGE_PADDING_TOP);
    }
}
