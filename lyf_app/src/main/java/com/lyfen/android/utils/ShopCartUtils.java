package com.lyfen.android.utils;

import android.os.Handler;
import android.os.Looper;

import com.laiyifen.lyfframework.network.RestRetrofit;
import com.lyfen.android.app.ShoppingCartApi;
import com.lyfen.android.entity.network.CommonEntity;
import com.ody.p2p.utils.ToastUtils;

import java.util.Map;


import rx.Observer;

/**
 * <p> Created by qiujie on 2017/12/21/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class ShopCartUtils {

    public static void addShop2Cart(Map<String, String> map) {

//        ut:387eaa301c07ffe7f47a3cf940a42e4471
//        mpId:1007020801002137
//        num:1
//        sessionId:1513746458935262
        RestRetrofit.getBeanOfClass(ShoppingCartApi.class).addItem2Cart(map).subscribe(new Observer<CommonEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommonEntity commonEntity) {

                if (commonEntity.code == 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showStr("添加成功");
                        }
                    });


                } else {
                    UIUtils.showToastSafe(commonEntity.message);
                }

            }
        });

    }
}
