package com.netease.nim.demo.main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.demo.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {
    public ImageView mBack;

    /**
     * 记录处于前台的Activity
     */
    private static BaseActivity mForegroundActivity = null;
    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivity> mActivities         = new LinkedList<BaseActivity>();
    public TextView mViewById;
    public TextView tv_right;
    public TextView tv_right_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(getResourceId(), null);
        setContentView(view);
        setInitialConfiguration();

        mBack = (ImageView) findViewById(R.id.back);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right_num = (TextView) findViewById(R.id.tv_right_num);


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivities.add(this);
        mForegroundActivity = this;
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }

    public abstract int getResourceId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.remove(this);

    }

    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static BaseActivity getCurrentActivity() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }


    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    private void setInitialConfiguration() {
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //        if (getSupportActionBar() != null) {
            //            getSupportActionBar().setTitle(R.string.app_name);
            //        }

            //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //            getWindow().setStatusBarColor(UtilsUI.darker(appPreferences.getPrimaryColorPref(), 0.8));
            //            toolbar.setBackgroundColor(appPreferences.getPrimaryColorPref());
            //            if (!appPreferences.getNavigationBlackPref()) {
            //                getWindow().setNavigationBarColor(appPreferences.getPrimaryColorPref());
            //            }
            //        }
        }*/
    }


    protected void setBarTitle(String string) {
        mViewById = (TextView) findViewById(R.id.tv_title);
        mViewById.setText(string);
        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(string);
        }*/
    }


}
