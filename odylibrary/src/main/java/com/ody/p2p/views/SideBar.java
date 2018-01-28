package com.ody.p2p.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.utils.PxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/21.
 */

public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
//    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
//            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
//            "W", "X", "Y", "Z", "#"};
    public List<String> crList = new ArrayList<>();
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;
    private int startPos = 0;
    private float totalHeight;
    private float singleHeight;

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setCrList(List<String> crList) {
        this.crList = crList;
        invalidate();
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
//        int width = getWidth(); // 获取对应宽度
//        int singleHeight = height / crList.size();// 获取每一个字母的高度
        float yPos = 0;
        paint.setColor(Color.rgb(33, 65, 98));
        // paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);
        paint.setTextSize(PxUtils.dipTopx(13));
        if (crList != null && crList.size() > 0) {
            singleHeight = paint.measureText(crList.get(0)) + PxUtils.dipTopx(15);
            yPos = (height - singleHeight * crList.size()) / 2;
            startPos = (int) ((height - singleHeight * crList.size()) / 2);
        }


        float xPos = getWidth() / 2;
        for (int i = 0; i < crList.size(); i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            paint.setTextSize(PxUtils.dipTopx(13));
            // 选中的状态
            if (i == choose) {
                paint.setColor(getResources().getColor(R.color.theme_color));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            yPos += singleHeight;
            canvas.drawText(crList.get(i), xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
        totalHeight = yPos;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) ((y - startPos) / (totalHeight - startPos) * crList.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        Log.d("samuel", "totalHeight : " + totalHeight);
        Log.d("samuel", "singleHeight : " + singleHeight);
        Log.d("samuel", "startPos : " + startPos);
        Log.d("samuel", "y - startPos : " + (y - startPos));
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < crList.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(crList.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(crList.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}
