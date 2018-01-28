package com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord.adapter.PayRecordAdapter;
import com.ody.p2p.main.myhomepager.myWallet.youdiancard.payrecord.bean.YRecordBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meijunqiang on 2017/3/15.
 * 描述：悠点卡充值记录页面
 */

public class PayRecordActivity extends BaseActivity {
    private RecyclerView pay_reecord_list;
    private int pageNo = 1;
    private PayRecordAdapter mAdapter;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_record;
    }

    @Override
    public void initView(View view) {
        pay_reecord_list = (RecyclerView) findViewById(R.id.pay_reecord_list);
        mAdapter = new PayRecordAdapter(this, new ArrayList<YRecordBean.DataEntity.DataEntityBean>());
        pay_reecord_list.setLayoutManager(new LinearLayoutManager(this));
        pay_reecord_list.setAdapter(mAdapter);
        //简单的滑到底部加载更多
        pay_reecord_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑过的高度和当前高度与整个控件高度的对比
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    int scrollState = pay_reecord_list.getScrollState();
                    if (scrollState == 0) {
                        return;
                    }
                    if (mAdapter != null && mAdapter.getItemCount() % 10 != 0) {
                        Toast.makeText(PayRecordActivity.this, "没有更多了!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getYRecordList();
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getYRecordList();
    }

    /**
     * 获取充值记录
     */
    public void getYRecordList() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("pageSize", "20");
        params.put("pageNo", pageNo + "");
        showLoading();
        OkHttpManager.postAsyn(Constants.YRECORDLIST, new OkHttpManager.ResultCallback<YRecordBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                hideLoading();
            }

            @Override
            public void onResponse(YRecordBean response) {
                hideLoading();
                if (null != response && null != response.getData() && null != response.getData().getData() && response.getData().getData().size() > 0) {
                    pageNo++;
                    mAdapter.addData(response.getData().getData());
                } else {
                    if (null == mAdapter || mAdapter.getItemCount() <= 0) {
                        Toast.makeText(PayRecordActivity.this, "您还没有充值呦！", Toast.LENGTH_SHORT).show();
                    } else if (mAdapter != null && mAdapter.getItemCount() >= 20 && null != response && null != response.getData() && response.getData().getData().size() == 0) {
                        //服务端所有数据刚好是20的整数倍
                        Toast.makeText(PayRecordActivity.this, "没有更多了!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, params);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
