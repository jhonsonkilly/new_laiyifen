package com.ody.p2p.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.ody.p2p.utils.NetworkUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * Created by lxs on 2016/7/8.
 */
public class NetWorkBroadCastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkUtils.getInstance().init(context);
            Intent intentnet = new Intent();
            if (NetworkUtils.getInstance().getNetworkType().equals(NetworkUtils.NETWORK_CMNET)){
                //ToastUtils.showShort("数据连接");
                intentnet.setAction(NetWorkOpration.NET_STATE);
                intentnet.putExtra(NetWorkOpration.NET_TYPE,NetWorkOpration.WAP_STATE);
                context.sendBroadcast(intentnet);
            }
            else if (NetworkUtils.getInstance().getNetworkType().equals(NetworkUtils.NETWORK_WIFI)){
                //ToastUtils.showShort("WIFI");
                intentnet.setAction(NetWorkOpration.NET_STATE);
                intentnet.putExtra(NetWorkOpration.NET_TYPE,NetWorkOpration.WIFI_STATE);
                context.sendBroadcast(intentnet);
            }
            else if (NetworkUtils.getInstance().getNetworkType().equals(NetworkUtils.NETWORK_CMWAP)){
                //ToastUtils.showShort("数据连接");
                intentnet.setAction(NetWorkOpration.NET_STATE);
                intentnet.putExtra(NetWorkOpration.NET_TYPE,NetWorkOpration.WAP_STATE);
                context.sendBroadcast(intentnet);
            }
            else if (NetworkUtils.getInstance().getNetworkType().equals("")){
                //ToastUtils.showShort("无网络连接");
                intentnet.setAction(NetWorkOpration.NET_STATE);
                intentnet.putExtra(NetWorkOpration.NET_TYPE,NetWorkOpration.NONET_STATE);
                context.sendBroadcast(intentnet);
            }
        }
    }
}
