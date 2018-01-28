package com.lyfen.android.ui.activity.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.laiyifen.lyfframework.utils.ProgressDialogUtils;
import com.laiyifen.lyfframework.views.ClearEditText;
import com.laiyifen.lyfframework.views.LoadingPage;
import com.laiyifen.lyfframework.views.screen.DetectSoftInputFrameLayout;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.entity.network.activity.OrderSearchEntity;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.adapter.OrderSearchAdapter;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.ToastUtils;

import java.util.Map;

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

public class OrderSearchActivity extends ActionBarActivity {
    @Bind(R.id.view_input_bg)
    View viewInputBg;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.iv_message)
    TextView ivMessage;
    @Bind(R.id.redFlag)
    TextView redFlag;
    @Bind(R.id.fl_message)
    FrameLayout flMessage;
    @Bind(R.id.iv_sweep)
    ImageView ivSweep;
    @Bind(R.id.et_search)
    TextView etSearch;
    @Bind(R.id.rl_search_content)
    RelativeLayout rlSearchContent;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.fl_content)
    DetectSoftInputFrameLayout detectSoftInputFrameLayout;
    @Bind(R.id.common_edit_1)
    ClearEditText commonEdit1;
    int pageNo = 1;
    private String keyword;
    RefreshRecyclerView mRefreshRecyclerView;
    private LoadingPage loadingPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionTitleBar().hide();
        setContentView(R.layout.activity_order_serach);


        ButterKnife.bind(this);
        loadingPage = new LoadingPage(OrderSearchActivity.this);
        loadingPage.showPage(LoadingPage.LoadResult.EMPTY);
        detectSoftInputFrameLayout.removeAllViews();
        detectSoftInputFrameLayout.addView(loadingPage);


        View inflate = View.inflate(this, R.layout.refresh_recyclerview, null);
        mRefreshRecyclerView = (RefreshRecyclerView) inflate.findViewById(R.id.id_recyclerview);
        tvAddress.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }

        });


        ivMessage.setOnClickListener(view -> {
            keyword = commonEdit1.getText().toString().trim();
            if (TextUtils.isEmpty(keyword)) {
                //UIUtils.showToastSafe("请输入关键字");
                ToastUtils.showStr("请输入关键字");
            } else {
                ProgressDialogUtils.showDialog(this, null);
                initData(keyword, pageNo);

            }


        });
    }

    private void initData(String s, int pagernu) {
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("keyword", s);
        stringStringMap.put("pageNo", pagernu + "");
        stringStringMap.put("pageSize", Integer.MAX_VALUE + "");
        stringStringMap.put("ut", LoginHelper.getUt());
        RestRetrofit.getBeanOfClass(OrderApi.class).getorderSearchList(stringStringMap).compose(bindToLifecycle()).subscribe(this::success, this::erro);


    }

    private void erro(Throwable throwable) {

        ProgressDialogUtils.cancleDialog();
    }

    OrderSearchAdapter shangouAdapter;

    private void success(OrderSearchEntity orderSearchEntity) {
        ProgressDialogUtils.cancleDialog();
        if (null != orderSearchEntity && null != orderSearchEntity.data && null != orderSearchEntity.data.data && orderSearchEntity.data.data.size() > 0) {


            shangouAdapter = new OrderSearchAdapter();
            ;
            shangouAdapter.setData(orderSearchEntity.data.data);

            RecyclerViewManager.with(shangouAdapter, new LinearLayoutManager(OrderSearchActivity.this))
                    .setMode(RecyclerMode.NONE)
                    .setOnItemClickListener((viewHolder, i) -> {
                        Intent intent = new Intent(OrderSearchActivity.this, OrderDetailActivity.class);
                        intent.putExtra("OrderId", orderSearchEntity.data.data.get(i).orderCode);
                        startActivity(intent);
                    })
                    .into(mRefreshRecyclerView, OrderSearchActivity.this);

            loadingPage.showPage(LoadingPage.LoadResult.SUCCEED, mRefreshRecyclerView);


        } else {


            UIUtils.showToastSafe("无数据");

            loadingPage.showPage(LoadingPage.LoadResult.EMPTY);
        }


    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
