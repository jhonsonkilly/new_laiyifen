package com.ody.p2p.views.clockview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.ody.p2p.R;

/**
 * Created by lxs on 2016/5/16.
 */
public class ClockView extends View{
    private Paint percentPaint;
    private Paint textPaint;
    private int textSize = 40;
    private int percent;
    private int allLineColor;
    private int percentLineColorLow;
    private int percentLineColorHight;
    private int allLineWidth = 2;
    private int percentLineWidth = 4;
    private int lineHeight = 30;

    private Paint paintsec;
    private Paint paintmin;
    private Paint painthour;
    private Paint backPaint;
    private Paint rountPaint;

    public ClockView(Context context) {
        super(context);
        init(null, 0);
    }
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public ClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    private void init(AttributeSet attrs, int defStyle) {
// TODO Auto-generated method stub
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ClockView, defStyle, 0);
        count = (System.currentTimeMillis() % (3600 * 24 * 1000) ) * 6;
        percent = a.getInt(R.styleable.ClockView_percent, 0);
        allLineColor = a.getColor(R.styleable.ClockView_allLineColor, Color.GRAY);
        percentLineColorLow = a.getColor(R.styleable.ClockView_percentLineColorLow, Color.GREEN);
        percentLineColorHight = a.getColor(R.styleable.ClockView_percentLineColorHight, Color.RED);
        a.recycle();
        percentPaint = new Paint();
        percentPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        paintsec = new Paint();
        paintsec.setAntiAlias(true);
        paintsec.setColor(Color.RED);
        paintsec.setStrokeWidth(percentLineWidth);
        paintmin = new Paint();
        paintmin.setAntiAlias(true);
        paintmin.setColor(Color.GREEN);
        paintmin.setStrokeWidth(percentLineWidth + 1);
        painthour = new Paint();
        painthour.setAntiAlias(true);
        painthour.setColor(Color.BLUE);
        painthour.setStrokeWidth(percentLineWidth + 2);
        backPaint = new Paint();
        backPaint.setColor(Color.YELLOW);
        backPaint.setAntiAlias(true);
        rountPaint = new Paint();
        rountPaint.setColor(allLineColor);
        rountPaint.setStrokeWidth(15);
        rountPaint.setAntiAlias(true);
        rountPaint.setStyle(Paint.Style.STROKE);
        percentPaint.setColor(allLineColor);
        percentPaint.setStrokeWidth(allLineWidth);
        new MyThread().start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
// TODO Auto-generated method stub
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int pointX =  width/2;
        int pointY = height/2;
        canvas.drawCircle(pointX,pointY,pointX,backPaint);
        canvas.drawCircle(pointX,pointY,pointX - 7,rountPaint);
        canvas.drawCircle(pointX,pointY,10,textPaint);
        float degrees = (float) (1.0);
        canvas.save();
        canvas.translate(0,pointY);
        canvas.rotate(90, pointX, 0);
        for(int i = 0;i<360;i++){
            if (i % 30 == 0){
                percentPaint.setStrokeWidth(6);
                canvas.drawLine(0, 0, lineHeight + 10, 0, percentPaint);
            }
            else if (i % 6 == 0){
                percentPaint.setStrokeWidth(4);
                canvas.drawLine(0, 0, lineHeight, 0, percentPaint);
            }
            canvas.rotate(degrees, pointX, 0);
        }
        canvas.restore();
        canvas.save();
        canvas.translate(0,pointY);
        canvas.rotate(180, pointX, 0);
        for(int i = 0;i<12;i++){
            canvas.rotate(degrees * 30, pointX, 0);
            if (i < 9){
                canvas.drawText(i + 1 + "", pointX - 10, pointY - 50,textPaint);
            }
            else{
                canvas.drawText(i + 1 + "",pointX - 22, pointY - 50,textPaint);
            }
        }
        canvas.restore();
        canvas.save();
        canvas.restore();
        canvas.save();
        canvas.translate(0,pointY);
        canvas.rotate(90 , pointX, 0);
        canvas.rotate(degrees * percent, pointX, 0);
        canvas.drawLine(100, 0, pointX , 0, paintsec);
        canvas.restore();
        canvas.save();
        canvas.translate(0,pointY);
        canvas.rotate(90 , pointX, 0);
        canvas.rotate(degrees * (float)((float)count / (float) 60), pointX, 0);
        canvas.drawLine(140, 0, pointX , 0, paintmin);
        canvas.restore();
        canvas.save();
        canvas.translate(0,pointY);
        canvas.rotate(90 , pointX, 0);
        canvas.rotate(degrees * (float)((float)count / (float) 300), pointX, 0);
        canvas.drawLine(180, 0, pointX , 0, painthour);
        canvas.restore();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
// TODO Auto-generated method stub
//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;
        setMeasuredDimension(d,d);
    }

    public void setPercent(int percent) {
// TODO Auto-generated method stub
        this.percent = percent;
        postInvalidate();
    }


    private boolean flag = true;

    private long count;

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (flag){
                try {
                    Thread.sleep(100);
                    setPercent((int)count % 360);
                    count ++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
