package com.ody.p2p.views.countdown;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;

/**
 * Created by lxs on 2016/8/11.
 */
public class CountDownView extends LinearLayout{

    private LinearLayout mLayout;
    private long countTime;
    private TextView tv_day;
    private TextView tv_hour;
    private TextView tv_min;
    private TextView tv_sec;

    private boolean isCountDown = false;

    public final static int COUNT_MES = 0X01;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == COUNT_MES){
                if (countTime > 0){
                    tv_day.setText(getDay(countTime));
                    tv_hour.setText(getHour(countTime));
                    tv_min.setText(getMin(countTime));
                    tv_sec.setText(getSec(countTime));
                    countTime -= 1;
                    mHandler.sendEmptyMessageDelayed(COUNT_MES,1000);
                }else {
                    mHandler.removeMessages(COUNT_MES);
                }
            }
        }
    };

    public CountDownView(Context context) {
        super(context);
        initView();
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView(){
        mLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_count_down,null);
        addView(mLayout);
        tv_day = (TextView) mLayout.findViewById(R.id.tv_day);
        tv_hour = (TextView) mLayout.findViewById(R.id.tv_hour);
        tv_min = (TextView) mLayout.findViewById(R.id.tv_min);
        tv_sec = (TextView) mLayout.findViewById(R.id.tv_sec);

    }


    public void setCountTime(long countTime){
        this.countTime = countTime / 1000;
        mHandler.removeMessages(COUNT_MES);
        mHandler.sendEmptyMessage(COUNT_MES);
    }


    public String getDay(long countTime){
        int day = (int)(countTime / ( 60 * 60 * 24));
        return  day >= 10 ? day + "" : "" + day;
    }


    public String getHour(long countTime){
        int hour = (int)(countTime % ( 60 * 60 * 24)/ ( 60 * 60));
        return  hour >= 10 ? hour + "" : "0" + hour;
    }

    public String getMin(long countTime){
        int min = (int)(countTime % (60 * 60) / 60);
        return  min >= 10 ? min + "" : "0" + min;
    }

    public String getSec(long countTime){
        int sec = (int)(countTime % (60 * 60 ) % 60) ;
        return  sec >= 10 ? sec + "" : "0" + sec;
    }

}
