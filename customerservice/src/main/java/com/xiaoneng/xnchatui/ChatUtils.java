package com.xiaoneng.xnchatui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import cn.xiaoneng.chatcore.XNSDKCore;
import cn.xiaoneng.coreapi.ChatParamsBody;
import cn.xiaoneng.uiapi.Ntalker;
import cn.xiaoneng.uiapi.XNSDKListener;
import cn.xiaoneng.utils.CoreData;

/**
 * Created by lxs on 2016/8/25.
 */
public class ChatUtils {

    public static String sdkkey = "85FA559D-E53E-4694-A917-1B049BAC4A79";// 示例FB7677EF-00AC-169D-1CAD-DEDA35F9C07B

    public static void startChat(final Context context,String settingId) {

        Uri notification = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.xnsdkconfig);
        final Ringtone ringtonenotification = RingtoneManager.getRingtone(context, notification);
//        /*************************************** 设置小能客服SDK的监听器 **********************************************/
//        Ntalker.getInstance().setSDKListener(new XNSDKListener() {
//            @Override
//            public void onChatMsg(boolean isSelfMsg, String settingid, String setingname, String username, String msgcontent, long msgtime) {
//                if (ringtonenotification == null)
//                    return;
//                if (isSelfMsg == false){
//                }
//                    ringtonenotification.play();
//                ((Activity)context).runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        // TODO Auto-generated method stub
//                    }
//                });
//            }
//            @Override
//            public void onUnReadMsg(String s, String s1, String s2, String s3, int i) {
//
//            }
//            @Override
//            public void onClickMatchedStr(String s, String s1) {
//
//            }
//            @Override
//            public void onClickUrlorEmailorNumber(int i, String s) {
//            }
//            @Override
//            public void onClickShowGoods(int i, int i1, String s, String s1, String s2, String s3, String s4, String s5) {
//
//            }
//            @Override
//            public void onError(int i) {
//            }
//        });// 小能监听接口
        Ntalker.getInstance().setShowCard(true);
        ChatParamsBody chatparams = null;
        chatparams = new ChatParamsBody();
//        // 咨询发起页（专有参数）
//        chatparams.startPageTitle = "女装/女士精品 - 服饰 - 搜索店铺 - ECMall演示站";
//        chatparams.startPageUrl = "http://img.meicicdn.com/p/51/050010/h-050010-1.jpg";
//        // 文本匹配域名参数
//        chatparams.matchstr = "www.meici.com/";
//        // erp参数,此参数小能不做任何处理
//        chatparams.erpParam = "";
////		chatparams.clickurltoshow_type = CoreData.CLICK_TO_APP_COMPONENT;
//        // 商品展示（专有参数）
//        chatparams.itemparams.appgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID;

//        chatparams.itemparams.itemparam = "";//
//        //使用id方式，（SHOW_GOODS_BY_ID）
//        chatparams.itemparams.goods_id = "ntalker_test";//ntalker_test
//        //使用widget方式，（SHOW_GOODS_BY_WIDGET）
//        chatparams.itemparams.goods_name = "2015年最新潮流时尚T恤偶像同款一二线城市包邮 速度抢购有超级大礼包等你来拿";
//        chatparams.itemparams.goods_price = "￥：188";
//        chatparams.itemparams.goods_image = "http://img.meicicdn.com/p/51/050010/h-050010-1.jpg";
//        chatparams.itemparams.goods_url = "http://www.baidu.com";
//        //使用商品url方式（SHOW_GOODS_BY_URL）
//        chatparams.itemparams.goods_showurl = "http://pic.shopex.cn/pictures/goodsdetail/29b.jpg?rnd=111111";//
        int startChat = 0;
        String groupName = "";// 客服组默认名称
        startChat = Ntalker.getInstance().startChat(context.getApplicationContext(), settingId, groupName, null, null, chatparams);
        if (0 == startChat) {
            Log.e("startChat", context.getString(R.string.opean_window_succeed));
        } else {
            Log.e("startChat", context.getString(R.string.opean_faile_failecode) + startChat);
        }
    }


    public static void startChat(final Context context,String settingId,String goods_name,String goods_price,String goods_image,String goods_url) {
        Ntalker.getInstance().setShowCard(true);
        ChatParamsBody chatparams = null;
        chatparams = new ChatParamsBody();
        chatparams.itemparams.appgoodsinfo_type = CoreData.SHOW_GOODS_BY_WIDGET;
        chatparams.itemparams.goods_name = goods_name;
        chatparams.itemparams.goods_price = goods_price;
        chatparams.itemparams.goods_image = goods_image;
        chatparams.itemparams.goods_url = goods_url;
        int startChat = 0;
        String groupName = "";// 客服组默认名称
        startChat = Ntalker.getInstance().startChat(context.getApplicationContext(), settingId, groupName, null, null, chatparams);
        if (0 == startChat) {
            Log.e("startChat", context.getString(R.string.opean_window_succeed));
        } else {
            Log.e("startChat", context.getString(R.string.opean_faile_failecode) + startChat);
        }
    }

    public static void startChat(final Context context,String settingId,String goods_id) {
        Ntalker.getInstance().setShowCard(true);
        ChatParamsBody chatparams = null;
        chatparams = new ChatParamsBody();
        chatparams.itemparams.appgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID;
        chatparams.itemparams.clientgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID;
        chatparams.itemparams.goods_id = goods_id;
        chatparams.itemparams.clicktoshow_type = CoreData.CLICK_TO_APP_COMPONENT;
        int startChat = 0;
        String groupName = "";// 客服组默认名称
        startChat = Ntalker.getInstance().startChat(context.getApplicationContext(), settingId, groupName, null, null, chatparams);
        if (0 == startChat) {
            Log.e("startChat", context.getString(R.string.opean_window_succeed));
        } else {
            Log.e("startChat", context.getString(R.string.opean_faile_failecode)+ startChat);
        }
    }


    public static void initSdk(Context context,String siteid){
        int enableDebug = Ntalker.getInstance().enableDebug(true);
        if (0 == enableDebug) {
            Log.e("enableDebug", context.getString(R.string.execute_succeed));
        } else {
            Log.e("enableDebug", context.getString(R.string.execute_faile_failecoed) + enableDebug);
        }
        /*******************************************************************************************************/
        int initSDK = Ntalker.getInstance().initSDK(context.getApplicationContext(), siteid, sdkkey);
        if (0 == initSDK) {
            Log.e("initSDK", context.getString(R.string.init_sdk_succeed));
        } else {
            Log.e("initSDK", context.getString(R.string.init_faile_failecoed) + initSDK);
        }
        /*******************************************************************************************************/
        /********************************* 设置访客没有关闭会话，点击退出按钮，关闭会话时间：取值范围1~10分钟 *************************************/
        XNSDKCore.getInstance().setReceiveUnReadMsgTime(5);
    }

    public static void login(String uid,String userName,int level){
        XNSDKCore.getInstance().login(uid,userName,level);
    }
}
