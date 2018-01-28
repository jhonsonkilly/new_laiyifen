package com.ody.p2p.okhttputils;

import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * <p> Created by qiujie on 2018/1/9/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class UbtUtils {


    public static void init(UbtEvent init) {

        try {

            Map<String, String> map = UbtObjectUtils.toMap(init);


            OkHttpManager.postAsyn("http://tracker.laiyifen.com/tracker/post", new OkHttpManager.ResultCallback<String>() {
                @Override
                public void onFinish() {
                    super.onFinish();

                }

                @Override
                public void onFailed(String code, String msg) {
                    super.onFailed(code, msg);
                }

                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(String response) {


                }
            }, map);
        } catch (Exception e) {

        }


    }


//    public static void  pvData(UbtEvent initp){
//
//        Map<String, String> map = UbtObjectUtils.toMap(init);
//        OkHttpManager.postAsyn("http://tracker.laiyifen.com/tracker/post", new OkHttpManager.ResultCallback<String>() {
//            @Override
//            public void onFinish() {
//                super.onFinish();
//
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                super.onFailed(code, msg);
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//
//            }
//        }, map);
//
//
//
//    }


}
