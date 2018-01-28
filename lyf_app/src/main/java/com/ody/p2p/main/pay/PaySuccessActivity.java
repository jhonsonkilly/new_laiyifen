package com.ody.p2p.main.pay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.pay.success.PaySuccessInfoBean;
import com.ody.p2p.pay.success.PaySuccessPresenterImpl;
import com.ody.p2p.pay.success.PaySuccessView;
import com.ody.p2p.pay.success.PaySucessPresenter;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recmmend.RecommendAdapter;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.ody.p2p.webactivity.WebActivity;

/**
 * Created by ${tang} on 2016/12/1.
 */

public class PaySuccessActivity extends BaseActivity implements PaySuccessView, View.OnClickListener {

    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_money;
    private TextView tv_check;
    private TextView tv_go_on;
    private TextView tv_reward;
    private ImageView iv_back;

    private RecyclerView rv_like;
    private RelativeLayout rl_like_title;
    private OdySwipeRefreshLayout swipeLayout;
    private ImageView iv_pay_com;

    private RecommendAdapter adapter;

    private PaySucessPresenter presenter;

    private String orderId;

    private int pageNo=1;
    private int totalCount=0;

    @Override
    public void initPresenter() {
        presenter=new PaySuccessPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_pay_success;
    }

    @Override
    public void initView(View view) {
        tv_name= (TextView)findViewById(R.id.tv_name);
        tv_address= (TextView)findViewById(R.id.tv_address);
        tv_money= (TextView)findViewById(R.id.tv_money);
        tv_check= (TextView)findViewById(R.id.tv_check);
        tv_go_on= (TextView)findViewById(R.id.tv_go_on);
        tv_reward= (TextView)findViewById(R.id.tv_reward);
        iv_pay_com = (ImageView) view.findViewById(R.id.iv_pay_com);

        iv_back= (ImageView) findViewById(R.id.iv_back);

        swipeLayout= (OdySwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setCanRefresh(false);

        rl_like_title= (RelativeLayout) findViewById(R.id.rl_like_title);
        rv_like= (RecyclerView) findViewById(R.id.rv_like);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv_like.setLayoutManager(gridLayoutManager);

        adapter=new RecommendAdapter(this,null);
        rv_like.setAdapter(adapter);

        iv_back.setOnClickListener(this);
        tv_check.setOnClickListener(this);
        tv_go_on.setOnClickListener(this);

        orderId=getIntent().getStringExtra(Constants.ORDER_ID);

    }

    @Override
    public void doBusiness(Context mContext) {
        swipeLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(adapter.getDatas().size()<totalCount){
                    pageNo++;
                    presenter.guessYouLike(pageNo);
                }else{
                    swipeLayout.setCanLoadMore(false);
                    ToastUtils.showToast("没有更多");
                }
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        presenter.payInfo(orderId);
        presenter.guessYouLike(pageNo);
        presenter.getAd("img_pay_spread");
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void payInfo(PaySuccessInfoBean response) {
        if(response.data.receiver!=null){
            tv_name.setText(response.data.receiver.receiverName);
            tv_address.setText(response.data.receiver.cityName+response.data.receiver.areaName+response.data.receiver.detailAddress);
        }
        tv_money.setText("￥"+ UiUtils.getDoubleForDouble(response.data.amount));
    }

    @Override
    public void likeList(Recommedbean recommedbean) {
        if(recommedbean.data!=null&&recommedbean.data.getDataList()!=null&&recommedbean.data.getDataList().size()>0){
            if(pageNo==1){
                totalCount=recommedbean.data.getTotalNum();
            }
            rl_like_title.setVisibility(View.VISIBLE);
            adapter.addItemLast(recommedbean.data.getDataList());
        }
    }

    @Override
    public void initAd(final FuncBean bean) {
        if (bean != null && bean.data != null && bean.data.img_pay_spread != null && bean.data.img_pay_spread.size() > 0){
            iv_pay_com.setVisibility(View.VISIBLE);
            GlideUtil.display(getContext(),bean.data.img_pay_spread.get(0).imageUrl).into(iv_pay_com);
            iv_pay_com.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpUtils.toActivity(bean.data.img_pay_spread.get(0));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_check){
            String url = OdyApplication.H5URL + "/my/order-detail.html?orderCode=" + orderId;
            JumpUtils.ToWebActivity(url, WebActivity.NO_TITLE, -1, "");
            finish();
        }else if(v.getId()==R.id.tv_go_on){
            Bundle db=new Bundle();
            db.putInt(Constants.GO_MAIN,0);
            JumpUtils.ToActivity(JumpUtils.MAIN,db);
            finish();
        }else if(v.getId()==R.id.iv_back){
            finish();
        }
    }
}
