package com.lyfen.android.ui.activity.shangou;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.lyfen.android.app.ActivityApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.entity.network.activity.CancelNotifyBean;
import com.lyfen.android.entity.network.activity.NotifyBean;
import com.lyfen.android.event.ShanGouEvent;
import com.lyfen.android.ui.fragment.qianggou.shangou.ShangouFragment;
import com.ody.p2p.main.R;
import com.ody.p2p.receiver.RemmindReceiver;
import com.ody.p2p.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class ShangouActivity extends ActionBarActivity implements View.OnClickListener {

    @Bind(R.id.common_tv_vertical_number_1)
    TextView commonTvVerticalNumber1;
    @Bind(R.id.common_tv_vertical_number_2)
    TextView commonTvVerticalNumber2;
    @Bind(R.id.common_lllayout_horizontal_number_2)
    LinearLayout commonLllayoutHorizontalNumber2;
    @Bind(R.id.common_rllayout_horizontal_number_1)
    RelativeLayout commonRllayoutHorizontalNumber1;
    @Bind(R.id.common_tv_vertical_number_4)
    TextView commonTvVerticalNumber4;
    @Bind(R.id.common_img_horizontal_number_3)
    ImageView commonImgHorizontalNumber3;
    @Bind(R.id.common_tv_vertical_number_5)
    TextView commonTvVerticalNumber5;
    @Bind(R.id.common_lllayout_horizontal_number_3)
    LinearLayout commonLllayoutHorizontalNumber3;
    @Bind(R.id.common_rllayout_horizontal_number_2)
    RelativeLayout commonRllayoutHorizontalNumber2;
    @Bind(R.id.common_lllayout_horizontal_number_1)
    LinearLayout commonLllayoutHorizontalNumber1;
    @Bind(R.id.common_viewpager)
    ViewPager commonViewpager;
    Myadapter mAdapter;
    private ActivityApi beanOfClass;


    boolean isNotify;
    TextView textView;
    long noticeTime;
    String id;


    private PendingIntent pi;
    private AlarmManager am;

    private static final int CLICK = 1;
    private static final int CLICKCOLOR = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangou);
        ButterKnife.bind(this);
        initView();
        initData();
        EventBus.getDefault().register(this);
        beanOfClass = RestRetrofit.getBeanOfClass(ActivityApi.class);

    }

    private void initData() {

        mAdapter = new Myadapter(
                getSupportFragmentManager());


        commonViewpager.setAdapter(mAdapter);

    }

    @Subscribe
    public void onEventMainThread(ShanGouEvent event) {
        if (event.type == CLICK) {
            this.isNotify = event.isNotify;
            this.textView = event.textView;
            this.id = event.mpId;
            this.noticeTime = event.time;
            if (event.isNotify) {
                Map<String, String> params = NetWorkMap.defaultMap();

                params.put("mpId", event.mpId);
                params.put("nocache", System.currentTimeMillis() + "");
                params.put("promotionId", event.promotionId);
                params.put("contentType", "10");
                params.put("noticeTime", event.time + "");

                beanOfClass.saveNotice(params).subscribe(this::success, this::erro);
            } else {
                Map<String, String> params = NetWorkMap.defaultMap();

                params.put("mpId", event.mpId);
                params.put("nocache", System.currentTimeMillis() + "");
                params.put("promotionId", event.promotionId);
                params.put("contentType", "10");
                params.put("noticeTime", event.time + "");

                beanOfClass.cancelNotice(params).subscribe(this::success, this::erro);
            }
        }


    }

    private void success(CancelNotifyBean cancelNotifyBean) {
        if (cancelNotifyBean != null) {


            if (cancelNotifyBean.code.equals("0")) {

                cancelNotify();
            } else {
                ToastUtils.showToast(cancelNotifyBean.message);
            }
        }
    }

    public void cancelNotify() {
        ShanGouEvent eventMessage = new ShanGouEvent();
        eventMessage.isNotify = false;
        eventMessage.textView = textView;
        eventMessage.type = CLICKCOLOR;
        EventBus.getDefault().post(eventMessage);

        am.cancel(pi);
        Toast.makeText(this, "提醒取消", Toast.LENGTH_SHORT).show();
    }


    private void erro(Throwable throwable) {
        String mes = throwable.getMessage();
        Log.i("ShanGou", mes);

    }

    private void success(NotifyBean notifyBean) {

        if (notifyBean != null) {
            if (notifyBean.code.equals("0")) {
                saveNotify(id, noticeTime + "");
            } else {
                ToastUtils.showToast(notifyBean.message);
            }
        }

    }

    public void saveNotify(String mid, String notifyTime) {

        ShanGouEvent eventMessage = new ShanGouEvent();
        eventMessage.isNotify = true;
        eventMessage.textView = textView;
        eventMessage.type = CLICKCOLOR;
        EventBus.getDefault().post(eventMessage);


        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        // create a PendingIntent that will perform a broadcast
        Intent intent = new Intent(this, RemmindReceiver.class);
        intent.putExtra("id", mid);

        pi = PendingIntent.getBroadcast(this, new Random().nextInt(10000), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // just use current time as the Alarm time.
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(notifyTime) * 1000);
        // schedule an alarm
        am.set(AlarmManager.RTC_WAKEUP, Long.parseLong(notifyTime), pi);


        Toast.makeText(this, "提醒成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_rllayout_horizontal_number_1:
                commonViewpager.setCurrentItem(0);

                break;
            case R.id.common_rllayout_horizontal_number_2:
                commonViewpager.setCurrentItem(1);
                break;
        }
    }

    class Myadapter extends FragmentPagerAdapter {


        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return ShangouFragment.newInstance(i, commonViewpager);
        }


        @Override
        public int getCount() {
            return 2;
        }
    }

    private void initView() {
        commonRllayoutHorizontalNumber1.setOnClickListener(this);
        commonRllayoutHorizontalNumber2.setOnClickListener(this);
        commonViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {//明天预告被选中
                    commonLllayoutHorizontalNumber1.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.C00B4FF));
                    commonRllayoutHorizontalNumber1.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));
                    commonRllayoutHorizontalNumber2.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.C0289C4));
                    commonTvVerticalNumber1.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.text_med_grey));
                    commonTvVerticalNumber2.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.text_med_grey));
                    commonTvVerticalNumber4.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));
                    commonImgHorizontalNumber3.setBackgroundDrawable(getResources().getDrawable(R.drawable.shap_point_white));//白点
                    commonTvVerticalNumber5.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));

                } else if (position == 0) {//今日闪购被选中
                    commonLllayoutHorizontalNumber1.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.app_color));
                    commonRllayoutHorizontalNumber1.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.app_color));
                    commonRllayoutHorizontalNumber2.setBackgroundColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));
                    commonTvVerticalNumber1.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));
                    commonTvVerticalNumber2.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.white));
                    commonTvVerticalNumber4.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.text_med_grey));
                    commonImgHorizontalNumber3.setBackgroundDrawable(getResources().getDrawable(R.drawable.shap_point_grey));//白点
                    commonTvVerticalNumber5.setTextColor(ContextCompat.getColor(ShangouActivity.this, R.color.text_med_grey));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        commonViewpager.setCurrentItem(0);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
