package com.lyfen.android.ui.activity.redpacket.redpacket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.lyfen.android.ui.activity.redpacket.RedPacketRetrofit;
import com.lyfen.android.ui.activity.redpacket.entity.RedPacketInitEntity;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.statusbar.StatusBarUtil;

import java.util.HashMap;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */
public class RedPacketActivity extends ActionBarActivity {
//    public static String BETA = "http://beta.touch.laiyifen.com";
    public static String BETA = "http://touch.laiyifen.com";

    ViewFlipper fillper;
    RelativeLayout ll_red;
    LinearLayout ll_sendRed;

    RedPacketInitEntity loadResult;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpacket_viewflipper);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.CF79100));
        getActionTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.CF79100));
        setTitle("来伊份红包");
        fillper = (ViewFlipper) findViewById(R.id.view_flipper);
        ll_red = (RelativeLayout) findViewById(R.id.ll_red);
        ll_sendRed = (LinearLayout) findViewById(R.id.ll_sendRed);

        RedPacketRetrofit retrofitHelper = new RedPacketRetrofit(BETA);
        final HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("method", "envelop.init");
        stringStringHashMap.put("session", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        retrofitHelper.getRedPacketService().redpacketInit(stringStringHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedPacketInitEntity>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onNext(RedPacketInitEntity loadResult) {
                        RedPacketActivity.this.loadResult=loadResult;

                    }
                });

        new RedPagerCilck(this, ll_red);


    }

    public void setDisplay(int chidren) {

        if (chidren == 0) {
            new MySendRedPager(this, ll_sendRed, loadResult,true);
        } else {
            new MySendRedPager(this, ll_sendRed,loadResult,false);
        }
        fillper.setDisplayedChild(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            goback();
        }
        return true;
    }



    private void goback() {
        if (fillper.getDisplayedChild() == 0) {

            finish();
        } else {

            if (fillper.getDisplayedChild() == 3) {
                getActionTitleBar().setTitle("普通红包");
                fillper.showPrevious();
            } else {
                getActionTitleBar().setTitle("来伊份红包");
                fillper.setDisplayedChild(0);
            }
        }
    }

}

