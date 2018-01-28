package com.lyfen.android.ui.activity.qianggou;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.views.LoadingPage;
import com.lyfen.android.app.ActivityApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.entity.network.activity.CancelNotifyBean;
import com.lyfen.android.entity.network.activity.NotifyBean;
import com.lyfen.android.entity.network.activity.QiangGouTimeEntity;
import com.lyfen.android.event.QiangGouEvent;
import com.lyfen.android.ui.fragment.qianggou.QiangGouFragment;
import com.lyfen.android.utils.ShopCartUtils;
import com.ody.p2p.main.R;
import com.ody.p2p.receiver.RemmindReceiver;
import com.ody.p2p.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by qiujie on 2017/7/21.
 */

public class QiangGouActivity extends ActionBarActivity {

    @Bind(R.id.gallery)
    Gallery gallery;
    @Bind(R.id.vp_content)
    ViewPager viewPager;
    private ActivityApi beanOfClass;
    private LoadingPage loadingPage;
    private View inflate;
    private QianggouGalleryAdapter qianggouGalleryAdapter;
    private List<QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity> times;
    private int gallerySelect;
    private Handler handler = new Handler();


    private PendingIntent pi;
    private AlarmManager am;

    long noticeTime;
    String mpid;
    String id;


    TextView textView;

    TextView cancelText;


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(gallerySelect, false);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beanOfClass = RestRetrofit.getBeanOfClass(ActivityApi.class);

        loadingPage = new LoadingPage(this);

        setContentView(loadingPage);

        inflate = View.inflate(this, R.layout.activity_qianggou, null);
        ButterKnife.bind(this, inflate);

        initData();

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gallerySelect = i;
                for (int j = 0; j < times.size(); j++) {
                    if (j == i) {
                        times.get(j).isSelect = true;
                    } else {
                        times.get(j).isSelect = false;
                    }
                }
                qianggouGalleryAdapter.setData(times);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                gallery.setSelection(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }


    private void initData() {

        beanOfClass.lyfTimeList(System.currentTimeMillis() + "").subscribe(this::success, this::erro);

    }


    private void erro(Throwable throwable) {
        loadingPage.showPage(LoadingPage.LoadResult.ERROR).setOnClickListener(view -> initData());

    }

    private void success(QiangGouTimeEntity qiangGouTimeEntity) {


        try {


            times = qiangGouTimeEntity.data.timeList.get(0).times;

            if (qianggouGalleryAdapter == null) {
                qianggouGalleryAdapter = new QianggouGalleryAdapter();
                qianggouGalleryAdapter.setData(qiangGouTimeEntity.data.timeList.get(0).times);
                gallery.setAdapter(qianggouGalleryAdapter);


                viewPager.setAdapter(new Myadapter(
                        getSupportFragmentManager(), qiangGouTimeEntity.data.timeList.get(0).times));

                viewPager.setCurrentItem(0);
                gallery.setSelection(0);


            } else {
                qianggouGalleryAdapter.setData(qiangGouTimeEntity.data.timeList.get(0).times);
                qianggouGalleryAdapter.notifyDataSetChanged();
            }


            loadingPage.showPage(LoadingPage.LoadResult.SUCCEED, inflate);


        } catch (Exception e) {

        }


    }

    public class QianggouGalleryAdapter extends BaseAdapter {
        List<QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity> beans;


        public void setData(List<QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity> beans) {
            this.beans = beans;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int i) {
            return beans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            final Item item;
            if (convertView == null) {
                convertView = LayoutInflater.from(QiangGouActivity.this).inflate(R.layout.item_qianggou_gallery, viewGroup, false);
                item = new Item(convertView);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();

            }
//            QiangGouEntity.QiangGouData.timeScroll bean = beans.get(i);
//            String status = bean.status;
//            String result = null;
//            if ("1".equals(status)) {
//                result = "已结束";
//            }
//            if ("2".equals(status)) {
//                result = "抢购进行时";
//            }
//
//            if ("3".equals(status)) {
//                result = "即将开场";
//            }
            if (beans.get(i).isSelect) {
                item.tv_time.setTextColor(getResources().getColor(R.color.white));
                item.tv_status.setTextColor(getResources().getColor(R.color.white));
                item.tv_status.setTextSize(15);


            } else {
                item.tv_time.setTextColor(getResources().getColor(R.color.text_min_grey));
                item.tv_status.setTextColor(getResources().getColor(R.color.text_min_grey));
                item.tv_status.setTextSize(14);
            }
//
//            if (!TextUtils.isEmpty(bean.stime)) {
//                item.tv_time.setText(bean.stime);
//            }
            item.tv_status.setText(beans.get(i).statusStr);
            item.tv_time.setText(beans.get(i).timeStr);
            return convertView;
        }

        class Item {
            private TextView tv_time, tv_status;

            public Item(View view) {
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                tv_status = (TextView) view.findViewById(R.id.tv_status);

            }
        }
    }

    class Myadapter extends FragmentStatePagerAdapter {
        private List<QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity> mQiangGouEntity;
        private Fragment fragment;

        public Myadapter(FragmentManager fm, List<QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity> qiangGouEntity) {
            super(fm);
            this.mQiangGouEntity = qiangGouEntity;
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            fragment = QiangGouFragment.newInstance();
            bundle.putSerializable("bean", mQiangGouEntity.get(i));
            bundle.putInt("i", i);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mQiangGouEntity.size();

        }
    }

    public void addToCart(String id) {


        Map<String, String> params = NetWorkMap.defaultMap();

        params.put("mpId", id);
        params.put("num", "1");
        ShopCartUtils.addShop2Cart(params);


    }

    public void notifyMes(String mpid, String id, long noticeTime, TextView textView) {


        this.mpid = mpid;
        this.noticeTime = noticeTime;
        this.textView = textView;
        this.id = id;


        Map<String, String> params = NetWorkMap.defaultMap();

        params.put("mpId", id);
        params.put("nocache", System.currentTimeMillis() + "");
        params.put("promotionId", mpid);
        params.put("contentType", "18");
        params.put("noticeTime", noticeTime + "");

        beanOfClass.saveNotice(params).subscribe(this::success, this::erro);

    }


    public void cancelNotifyMes(String id, String mpid, String noticeTime, final TextView textView) {

        this.cancelText = textView;


        Map<String, String> params = NetWorkMap.defaultMap();

        params.put("mpId", id);
        params.put("nocache", System.currentTimeMillis() + "");
        params.put("promotionId", mpid);
        params.put("contentType", "18");
        params.put("noticeTime", noticeTime + "");

        beanOfClass.cancelNotice(params).subscribe(this::success, this::erro);
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

        QiangGouEvent eventMessage = new QiangGouEvent(textView);
        eventMessage.isNotify = true;
        EventBus.getDefault().post(eventMessage);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        // create a PendingIntent that will perform a broadcast
        Intent intent = new Intent(this, RemmindReceiver.class);
        intent.putExtra("id", mid);

        pi = PendingIntent.getBroadcast(this, new Random().nextInt(10000), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // just use current time as the Alarm time.
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(notifyTime));
        // schedule an alarm
        am.set(AlarmManager.RTC_WAKEUP, Long.parseLong(notifyTime) * 1000, pi);


        Toast.makeText(this, "提醒成功", Toast.LENGTH_SHORT).show();
    }

    public void cancelNotify() {
        QiangGouEvent eventMessage = new QiangGouEvent(textView);
        eventMessage.isNotify = false;
        EventBus.getDefault().post(eventMessage);

        am.cancel(pi);
        Toast.makeText(this, "提醒取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
