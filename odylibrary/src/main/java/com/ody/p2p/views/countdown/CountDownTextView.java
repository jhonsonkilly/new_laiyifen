package com.ody.p2p.views.countdown;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ody.p2p.PromotionInfo;
import com.ody.p2p.utils.DateTimeUtils;

import static com.ody.p2p.utils.DateTimeUtils.DF_HH_MM_SS;

/**
 * Created by pzy on 2016/12/13.
 */
public class CountDownTextView extends TextView{

    public final static int COUNT_SECOND = 0X01;
    private long countTime;
    private PromotionInfo prData;
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String HH_MM_SS = "HH:mm:ss";

    String mTimeType=HH_MM_SS;//时间类型

    String leftText="";
    String rightText="";


    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == COUNT_SECOND){
                if (null!=prData){
                    countTime=prData.getCountdown();
                }
                if (countTime > 0){
                    countTime --;
                    String str=leftText+ DateTimeUtils.getCountDown(countTime*1000)+rightText;
                    CountDownTextView.this.setText(Html.fromHtml(str));
                    if (null!=prData){
                        prData.setCountdown(countTime);
                    }
                    mHandler.sendEmptyMessageDelayed(COUNT_SECOND,1000);
                }else{
                    if (null!=countCallBack){
                        countCallBack.finish();
                    }
                }
            }
        }
    };

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCountTime(long countTime,String mTimeType){
        this.countTime = countTime;//默认是秒
        this.mTimeType=mTimeType;
        mHandler.removeMessages(COUNT_SECOND);
        mHandler.sendEmptyMessage(COUNT_SECOND);
    }
    public void setCountTime(PromotionInfo pr,String mTimeType){
        this.prData = pr;//默认是秒
        this.mTimeType=mTimeType;
        mHandler.removeMessages(COUNT_SECOND);
        mHandler.sendEmptyMessage(COUNT_SECOND);
    }

    public void setStop(){
        mHandler.removeMessages(COUNT_SECOND);
        mHandler.sendEmptyMessage(COUNT_SECOND);
    }

    public long getTime(){
        return  countTime;
    }

    CountCallBack countCallBack;

    public CountCallBack getCountCallBack() {
        return countCallBack;
    }

    public void setCountFinish(CountCallBack countCallBack) {
        this.countCallBack = countCallBack;
    }

    public interface CountCallBack{
        void finish();
    }

}
