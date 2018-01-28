package com.ody.p2p.main;

import android.content.Context;
import android.view.View;

import com.ody.p2p.base.BaseActivity;

/**
 * Created by meijunqiang on 2017/6/29.
 * 描述:分享赚佣金说明页
 */

public class LyfCommissionCode extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_commission;
    }

    @Override
    public void initView(View view) {
        findViewById(R.id.img_finish_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {

    }
}
