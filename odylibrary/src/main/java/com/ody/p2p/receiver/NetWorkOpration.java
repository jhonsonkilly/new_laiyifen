package com.ody.p2p.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by lxs on 2016/7/8.
 */
public class NetWorkOpration {

    public static final String NET_STATE = "com.odysaas.netstate";
    public static final String NET_TYPE = "net_type";
    public static final int WIFI_STATE = 0;
    public static final int NONET_STATE = 1;
    public static final int WAP_STATE = 2;

    public Context context;

    private BroadcastReceiver receiver;

    public NetWorkOpration(Context context){
        this.context = context;
    }

    public void dealWithNetState(final getNetInfoListener listener){
        if (receiver == null){
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    listener.getNetInfo(intent.getIntExtra(NET_TYPE,-1));
                }
            };
            context.registerReceiver(receiver,new IntentFilter(NET_STATE));
        }
    }

    public interface getNetInfoListener{
        void getNetInfo(int netType);
    }

    public void unregisterNetWork(){
        if (receiver != null){
            context.unregisterReceiver(receiver);
        }
    }


}
