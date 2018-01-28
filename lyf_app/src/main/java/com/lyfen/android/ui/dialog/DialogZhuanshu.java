package com.lyfen.android.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.WriterException;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.lyfen.android.app.DialogApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.entity.network.QrcodeEntity;
import com.lyfen.android.ui.BaseDialog;
import com.lyfen.android.utils.EncodingHandler;
import com.ody.p2p.main.R;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * <p> Created by qiujie on 2017/12/15/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class DialogZhuanshu extends BaseDialog {

    private DialogApi beanOfClass;
    private final SimpleDraweeView simpleDraweeView;
    private final SimpleDraweeView simpleDraweeView2;
    private final TextView tvnumber;
    private final Subscriber longSubcribe;
    private Observable<Long> longObservable;
    private Observable<Long> initOBservale;
    private final Subscriber initObscribe;

    public DialogZhuanshu(Context context, int layoutResID) {
        super(context, layoutResID);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.common_img_2);
        simpleDraweeView2 = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
        tvnumber = (TextView) findViewById(R.id.tv_number);
        findViewById(R.id.tv_reflea).setOnClickListener(view -> initData());
        findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        beanOfClass = RestRetrofit.getBeanOfClass(DialogApi.class);

        //                initData();
        longSubcribe = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
//                initData();

                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                stringStringMap.put("st", st);
                beanOfClass.getcheckSingleTokenStatus(stringStringMap).subscribe(new Subscriber<QrcodeEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QrcodeEntity qrcodeEntity) {
                        if (qrcodeEntity.data.status == 1) {
                            dismiss();
                        } else if (qrcodeEntity.data.status == 2) {
                            initData();

                        }

                    }
                });


            }
        };

        initObscribe = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                beanOfClass.getQrcode(stringStringMap).subscribe(new Observer<QrcodeEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QrcodeEntity qrcodeEntity) {

                        try {
                            st = qrcodeEntity.data.st;
                            simpleDraweeView.setImageBitmap(EncodingHandler.create2Code(qrcodeEntity.data.st, 500));
                            simpleDraweeView2.setImageBitmap(EncodingHandler.createBarCode(qrcodeEntity.data.st, 1200, 300));
                        } catch (WriterException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        tvnumber.setText(qrcodeEntity.data.st);
                    }
                });
            }
        };
        initData();


    }

    private String st;

    private void initLongData() {
        longObservable = Observable.timer(0, 2, TimeUnit.SECONDS);
        longObservable.subscribe(longSubcribe);


    }

    private void initData() {


        initOBservale = Observable.timer(0, 60, TimeUnit.SECONDS);

        initOBservale.subscribe(initObscribe);


        initLongData();
    }

    @Override
    public void dismiss() {
        super.dismiss();



        if (!longSubcribe.isUnsubscribed()) {
            longSubcribe.unsubscribe();
        }
        if (!initObscribe.isUnsubscribed()) {
            initObscribe.unsubscribe();
        }

    }
}
