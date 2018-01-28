package com.ody.p2p.settings.utils.pickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ody.p2p.myhomepager.R;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LoopView extends View {

    // Timer mTimer;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;
    int totalScrollY;
    Handler handler;
    LoopListener loopListener;
    private GestureDetector gestureDetector;
    private int selectedItem;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;
    Context context;
    Paint paintA;  //paint that draw top and bottom text 除中间一层外的其余四层!!
    Paint paintB;  // paint that draw center text  中间一层
    Paint paintC;  // paint that draw line besides center text  中间一层上下的两条线!!!
    ArrayList arrayList;
    int textSize;
    int fourTextColor;//外面四层字体颜色
    int centerTextColor;//中间一层字体颜色
    int centerLineTextColor;//中间一层上下两条线的颜色
    int maxTextWidth;
    int maxTextHeight;
    int colorGray;
    int colorBlack;
    int colorGrayLight;
    float lineSpacingMultiplier;
    boolean isLoop;
    int firstLineY;
    int secondLineY;
    int preCurrentIndex;
    int initPosition;
    int itemCount;
    int measuredHeight;
    int halfCircumference;
    int radius;
    int measuredWidth;
    //    int paddingLeft = 0;
//    int paddingRight = 0;
    int change;
    float y1;
    float y2;
    float dy;

    public LoopView(Context context) {
        super(context);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        textSize = 0;
        fourTextColor = getResources().getColor(R.color.theme_color);
        centerTextColor = getResources().getColor(R.color.theme_color);
        centerLineTextColor = getResources().getColor(R.color.theme_color);

        colorGray = 0xffafafaf;
        colorBlack = 0xff313131;
        colorGrayLight = 0xffc5c5c5;
        lineSpacingMultiplier = 2.0F;
        isLoop = true;
        initPosition = -1;
        itemCount = 7;
        y1 = 0.0F;
        y2 = 0.0F;
        dy = 0.0F;
        totalScrollY = 0;
        simpleOnGestureListener = new LoopViewGestureListener(this);
        handler = new MessageHandler(this);
        this.context = context;
        setTextSize(16F);//改变值无影响.......???

        paintA = new Paint();
        paintB = new Paint();
        paintC = new Paint();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
        gestureDetector.setIsLongpressEnabled(false);
    }

    static int getSelectedItem(LoopView loopview) {
        return loopview.selectedItem;
    }

    static void smoothScroll(LoopView loopview) {
        loopview.smoothScroll();
    }

    private void initData() {
        if (arrayList == null) {
            return;
        }

        /**
         *
         paintA.setColor(getResources().getColor(R.color.btn_cancel_color));//text_color  333333 颜色最深 位置在中间最上面
         paintA.setAntiAlias(true);//抗锯齿
         paintA.setTypeface(Typeface.DEFAULT);//字体
         paintA.setTextSize(textSize);

         paintB.setColor(getResources().getColor(R.color.text_color_below));//text_color_below  666
         paintB.setAntiAlias(true);
         paintB.。
         paintB.setTypeface(Typeface.MONOSPACE);
         paintB.setTextSize(textSize);

         paintC.setColor(getResources().getColor(R.color.text_color));//btn_cancel_color  999
         paintC.setAntiAlias(true);
         paintC.setTypeface(Typeface.MONOSPACE);
         paintC.setTextSize(textSize);
         */

        /**
         setTypeface
         Typeface.DEFAULT：默认字体。
         Typeface.DEFAULT_BOLD：加粗字体。
         Typeface.MONOSPACE：monospace字体。
         Typeface.SANS_SERIF：sans字体。
         Typeface.SERIF：serif字体。

         setTextScaleX(1.05F) 参数scaleX为字体比例因子，当大于1的时候表示横向拉伸，当小于1的时候表示横向压缩
         */
        paintA.setColor(fourTextColor);//外面四层!!
        paintA.setAntiAlias(true);//抗锯齿
        paintA.setTypeface(Typeface.MONOSPACE);//字体
        paintA.setTextSize(textSize);

        paintB.setColor(centerTextColor);//中间一层
        paintB.setAntiAlias(true);
        paintB.setTextScaleX(1F);
        paintB.setTypeface(Typeface.MONOSPACE);
        paintB.setTextSize(textSize);

        paintC.setColor(centerLineTextColor);//中间一层上下的两条线
        paintC.setAntiAlias(true);
        paintC.setTypeface(Typeface.MONOSPACE);
        paintC.setTextSize(textSize);
        measureTextWidthHeight();

        /**
         * 下面在干啥????????????
         */

        halfCircumference = (int) (maxTextHeight * lineSpacingMultiplier * (itemCount - 1));
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        radius = (int) (halfCircumference / Math.PI);
//        int extraRightWidth = (int) (maxTextWidth * 0.05) + 1;
//        if (paddingRight<=extraRightWidth) {
//            paddingRight = extraRightWidth;
//        }
        //TODO  padding may be not useful,so ignore this
//        measuredWidth = maxTextWidth + paddingLeft + paddingRight;
        firstLineY = (int) ((measuredHeight - lineSpacingMultiplier * maxTextHeight) / 2.0F);
        secondLineY = (int) ((measuredHeight + lineSpacingMultiplier * maxTextHeight) / 2.0F);
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (arrayList.size() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }
        preCurrentIndex = initPosition;
    }

    /**
     * ??????????????????????
     */
    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < arrayList.size(); i++) {
            String s1 = (String) arrayList.get(i);
            paintB.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintB.getTextBounds("\u661F\u671F", 0, 2, rect); // 星期
            int textHeight = rect.height();
            if (textHeight > maxTextHeight) {
                maxTextHeight = textHeight;
            }
        }

    }

//    private void smoothScroll() {
//        int offset = (int) ((float) totalScrollY % (lineSpacingMultiplier * (float) maxTextHeight));
//        Timer timer = new Timer();
//        mTimer = timer;
//        timer.schedule(new MTimer(this, offset, timer), 0L, 10L);
//    }

    private void smoothScroll() {
        int offset = (int) (totalScrollY % (lineSpacingMultiplier * maxTextHeight));
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new MTimer(this, offset), 0, 10, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public final void setNotLoop() {
        isLoop = false;
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
        }
    }

    //外面四层字体颜色
    public final void setFourTextColor(int color){
        fourTextColor = color;
    }
    //中间一层字体颜色
    public final void setCenterTextColor(int color){
        centerTextColor = color;
    }
    //中间一层上下两条线的颜色
    public final void setCenterLineTextColor(int color){
        centerLineTextColor = color;
    }

    public final void setInitPosition(int initPosition) {
        this.initPosition = initPosition;
    }

    public final void setListener(LoopListener LoopListener) {
        loopListener = LoopListener;
    }

    public final void setArrayList(ArrayList arraylist) {
        this.arrayList = arraylist;
        initData();
        invalidate();
    }

//    @Override
//    public int getPaddingLeft() {
//        return paddingLeft;
//    }
//
//    @Override
//    public int getPaddingRight() {
//        return paddingRight;
//    }
//
//    public void setViewPadding(int left, int top, int right, int bottom) {
//        paddingLeft = left;
//        paddingRight = right;
//    }

    public final int getSelectedItem() {
        return selectedItem;
    }
//
//    protected final void smoothScroll(float velocityY) {
//        Timer timer = new Timer();
//        mTimer = timer;
//        timer.schedule(new LoopTimerTask(this, velocityY, timer), 0L, 20L);
//    }

    protected final void smoothScroll(float velocityY) {
        cancelFuture();
        int velocityFling = 20;
        mFuture = mExecutor.scheduleWithFixedDelay(new LoopTimerTask(this, velocityY), 0, velocityFling, TimeUnit.MILLISECONDS);
    }


    protected final void itemSelected() {
        if (loopListener != null) {
            postDelayed(new LoopRunnable(this), 200L);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String as[];
        if (arrayList == null) {
            super.onDraw(canvas);
            return;
        }
        as = new String[itemCount];
        change = (int) (totalScrollY / (lineSpacingMultiplier * maxTextHeight));
        preCurrentIndex = initPosition + change % arrayList.size();
//        Log.i("test", (new StringBuilder("scrollY1=")).append(totalScrollY).toString());
//        Log.i("test", (new StringBuilder("change=")).append(change).toString());
//        Log.i("test", (new StringBuilder("lineSpacingMultiplier * maxTextHeight=")).append(lineSpacingMultiplier * maxTextHeight).toString());
//        Log.i("test", (new StringBuilder("preCurrentIndex=")).append(preCurrentIndex).toString());
        if (!isLoop) {
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > arrayList.size() - 1) {
                preCurrentIndex = arrayList.size() - 1;
            }
            // break;
        } else {
            if (preCurrentIndex < 0) {
                preCurrentIndex = arrayList.size() + preCurrentIndex;
            }
            if (preCurrentIndex > arrayList.size() - 1) {
                preCurrentIndex = preCurrentIndex - arrayList.size();
            }
            // continue;
        }

        int j2 = (int) (totalScrollY % (lineSpacingMultiplier * maxTextHeight));
        int k1 = 0;
        while (k1 < itemCount) {
            int l1 = preCurrentIndex - (itemCount / 2 - k1);
            if (isLoop) {
                if (l1 < 0) {
                    l1 = l1 + arrayList.size();
                }
                if (l1 > arrayList.size() - 1) {
                    l1 = l1 - arrayList.size();
                }
                as[k1] = (String) arrayList.get(l1);
            } else if (l1 < 0) {
                as[k1] = "";
            } else if (l1 > arrayList.size() - 1) {
                as[k1] = "";
            } else {
                as[k1] = (String) arrayList.get(l1);
            }
            k1++;
        }
//        int left = paddingLeft;
        //auto calculate the text's left value when draw
        int left = (measuredWidth - maxTextWidth) / 2;
//        Log.e("measuredWidth","onDraw:"+measuredWidth);
//        Log.e("measuredWidth","onDraw / 2:"+ (measuredWidth - maxTextWidth)/2);

        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintC);
        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintC);
        int j1 = 0;
        while (j1 < itemCount) {
            canvas.save();
            // L=α* r
            // (L * π ) / (π * r)
            float itemHeight = maxTextHeight * lineSpacingMultiplier;
//            Log.e("test","itemHeight======="+itemHeight);
            double radian = ((itemHeight * j1 - j2) * Math.PI) / halfCircumference;
//            double radian = 90;
//            Log.e("test","radian======="+radian);
            float angle = (float) (90D - (radian / Math.PI) * 180D);
//            float angle = 1;
//            Log.e("test","angle======="+angle);
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                int translateY = (int) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));
//                Log.e("test","translateY======="+translateY);
//                Log.e("test","firstLineY======="+firstLineY);
//                Log.e("test","secondLineY======="+secondLineY);
//                Log.e("test","maxTextHeight======="+maxTextHeight);
//                Log.e("test","                                                              ");
//                Log.e("test","                                                              ");
//                Log.e("test","                                                              ");
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    canvas.save();
                    //top = 0,left = (measuredWidth - maxTextWidth)/2
                    String item = as[j1];
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.drawText(item, left, maxTextHeight, paintA);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.drawText(item, left, maxTextHeight, paintB);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    String item = as[j1];
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.drawText(item, left, maxTextHeight, paintB);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.drawText(item, left, maxTextHeight, paintA);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    String item = as[j1];
                    canvas.drawText(item, left, maxTextHeight, paintB);
                    selectedItem = arrayList.indexOf(as[j1]);
                } else {
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    String item = as[j1];
                    canvas.drawText(item, left, maxTextHeight, paintA);
                }
                canvas.restore();
            }
            j1++;
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initData();
        measuredWidth = getMeasuredWidth();
//        Log.e("measuredWidth", "onMeasure:" + measuredWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = motionevent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2 = motionevent.getRawY();
                dy = y1 - y2;
                y1 = y2;
                totalScrollY = (int) ((float) totalScrollY + dy);
                if (!isLoop) {
                    int initPositionCircleLength = (int) (initPosition * (lineSpacingMultiplier * maxTextHeight));
                    int initPositionStartY = -1 * initPositionCircleLength;
                    if (totalScrollY < initPositionStartY) {
                        totalScrollY = initPositionStartY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == MotionEvent.ACTION_UP) {
                    smoothScroll();
                }
                return true;
        }

        if (!isLoop) {
            int circleLength = (int) ((float) (arrayList.size() - 1 - initPosition) * (lineSpacingMultiplier * maxTextHeight));
            if (totalScrollY >= circleLength) {
                totalScrollY = circleLength;
            }
        }
        invalidate();

        if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == MotionEvent.ACTION_UP) {
            smoothScroll();
        }
        return true;
    }
}
