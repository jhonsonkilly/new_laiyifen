package com.ody.p2p.check.giftcard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

public class GiftCardConsumerActivity extends BaseActivity implements View.OnClickListener, GiftCardConsumerView, GiftCardConsumerAdapter.ItemClickListener {

    private ImageView iv_back;
    private OdySwipeRefreshLayout swipeLayout;
    private RecyclerView rv_details;
    private GiftCardConsumerAdapter adapter;
    private int PAGENO=1;
    private int TOTAL_NUM=0;
    private GiftCardConsumerPresenter presenter;
    private String giftCardId;

    @Override
    public void initPresenter() {
        presenter=new GiftCardConsumerPreImpl(this);
        giftCardId=getIntent().getStringExtra("giftCardId");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_gift_card_consumer;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        swipeLayout= (OdySwipeRefreshLayout) findViewById(R.id.swipeLayout);
        rv_details= (RecyclerView) findViewById(R.id.rv_details);
        iv_back.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_details.setLayoutManager(linearLayoutManager);
        adapter=new GiftCardConsumerAdapter(this);
        rv_details.setAdapter(adapter);
        adapter.setClicklistener(this);
        swipeLayout.setHeaderViewBackgroundColor(0x00ffffff);
        swipeLayout.setTargetScrollWithLayout(true);
        swipeLayout.setOdyDefaultView(true);
    }

    @Override
    public void doBusiness(Context mContext) {
        swipeLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(adapter.getItemCount()<TOTAL_NUM){
                    PAGENO++;
                    presenter.consumerDetail(giftCardId,PAGENO);
                }else{
                    swipeLayout.setLoadMore(false);
                    ToastUtils.showToast(getString(R.string.no_more));
                }
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        swipeLayout.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                PAGENO=1;
                adapter.clear();
                presenter.consumerDetail(giftCardId,PAGENO);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        presenter.consumerDetail(giftCardId,PAGENO);
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }
    }

    @Override
    public void consumerDetail(GiftCardConsumerBean response) {
        if(response.data!=null&&response.data.size()>0){
            if(PAGENO==1){

            }
            adapter.addData(response.data);
        }
    }

    @Override
    public void onItemClick(GiftCardConsumerBean.DataBean bean) {
        Bundle bundle=new Bundle();
        bundle.putString(Constants.ORDER_ID,bean.orderCode);
        JumpUtils.ToActivity(JumpUtils.ORDERDETAIL,bundle);
    }
}
