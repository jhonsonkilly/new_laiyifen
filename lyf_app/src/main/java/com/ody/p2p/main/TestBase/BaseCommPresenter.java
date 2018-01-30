package com.ody.p2p.main.TestBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;




/**
 * Created by wei on 2015/6/18.
 * <p>
 * 这是一个基类由于项目使用观察者模式
 * Perseter中尽量避免操作UI 所以UI操作都要放到IView中进行操作
 */
public abstract class BaseCommPresenter<V extends IBaseCommView>  {

    protected V iView;
    protected long mTimeLength;

    private long mCreateTime;

    public BaseCommPresenter() {
        //必要的时候请重写 这个默认构造函数  在这里加一些方法

        //很不幸现在框架只会执行默认构造函数 不会执行带参数构造函数
    }



    public void sendBroadFilter(String filter) {

        LocalBroadcastManager.getInstance(iView.getActivity()).sendBroadcast(new Intent(filter));
    }

    public void sendBroadCastIntent(Intent intent) {

        LocalBroadcastManager.getInstance(iView.getActivity()).sendBroadcast(intent);
    }

    public void onReciver(String filter, Intent intent) {

        iView.onReciver(filter, intent);
        reciver(filter, intent);
    }

    protected void reciver(String filter, Intent intent) {

    }

    protected void unregisterBroadcast() {


    }

    /**
     * 构造函数之后必须要设置setIView  否则后面一些东东西没法操作
     * iv 是Activity
     */
    public void setIView(V iv) {

        iView = iv;
    }

    //实例化一些数据  可以根据bundle参数是否为空  非空则可以选择性的恢复一些数据  保存会在saveData中保存
    //不要在这里操作UI
    abstract public void initData(Bundle saveInstnce);

    abstract public void handMsg(Message msg);

    //save数据不强制要求重写 就没abstract
    public void onSaveInstanceState(Bundle saveInstnce) {
        /*

        这里保存的时候使用了key  但是 key请不要拼写一堆的 "isuodafefjds"  这种字符串
        请使用规范的写法 在save之前写几个  static final String KEY_IS_UPDALOAD="isupload";
        像这样子  这样的好处就是  initData的时候直接使用的key如果拼错了IDE会自动给你指出来
        你如果使用"isupload"  就算你拼写错了IDE会认为你这是两个字符串不会给你指出错误

        */
    }

    protected Handler getHandler() {

        return iView.getHandler();
    }

    protected Activity getActivity() {

        return iView.getActivity();
    }

    //几个必要的生命周期 用于一些数据必要的释放 begin
    public void onResume() {

    }

    public void onPause() {

    }
    //几个必要的生命周期 用于一些数据必要的释放 end

    public void onDestory() {

        mTimeLength = System.currentTimeMillis() / 1000 - mCreateTime;
        unregisterBroadcast();

    }//destory的时候请选择性的释放一些数据

    //activity可见的时候
    public void onWindowShow() {

    }






    //view层的act frag的控件都绑定完毕之后执行这个方法
    public void firstShow() {

        mCreateTime = System.currentTimeMillis() / 1000;
    }

    public boolean getNetWorkStatus() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        for (int i = 0; i < networkInfo.length; i++) {
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }


}
