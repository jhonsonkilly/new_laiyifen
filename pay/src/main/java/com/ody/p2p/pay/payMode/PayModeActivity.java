package com.ody.p2p.pay.payMode;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.pay.R;
import com.ody.p2p.pay.payMode.payOnline.PayOnlineActivity;

import java.util.List;

import cn.campusapp.router.annotation.RouterMap;

/**
 * 支付方式
 */
@RouterMap("activity://payMode")
public class PayModeActivity extends BaseActivity implements View.OnClickListener ,PayModeView{

    private TextView tv_name;
    private RelativeLayout rl_big_back;
    private RecyclerView rv_pay_mode;

    private String userId;
    private String orderId;
    private String orderMoney;
    private String deliveryFee;

    private PayModePresenter payModePresenter;

    @Override
    public void initPresenter() {
        payModePresenter = new PayModePresenterImpl(this);
        payModePresenter.getPayMode();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_mode;
    }

    @Override
    public void initView(View view) {

        userId = getIntent().getStringExtra(Constants.USER_ID);
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        orderMoney = getIntent().getStringExtra(Constants.ORDER_MONEY);
        deliveryFee = getIntent().getStringExtra(Constants.ORDER_DELIVERYfEE);

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        rv_pay_mode = (RecyclerView) view.findViewById(R.id.rv_pay_mode);

        rv_pay_mode.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pay_mode.setLayoutManager(linearLayoutManager);

        tv_name.setText(getString(R.string.pay_way));

        rl_big_back.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
    }

    @Override
    public void setPayMode(List<PayModeBean.PayModeData> payModedata) {
        final PayModeAdapter pmAdapter = new PayModeAdapter(this);
        rv_pay_mode.setAdapter(pmAdapter);
        pmAdapter.setDatas(payModedata);

        pmAdapter.setOnItemClickListener(new PayModeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i=0;i<pmAdapter.getItemCount();i++){
                    pmAdapter.getItem(i).isChoose = false;
                }
                pmAdapter.getItem(position).isChoose = true;
                pmAdapter.notifyDataSetChanged();

                if (position==0){//跳转到"在线支付列表选择"界面
                    mBaseOperation.addParameter(Constants.USER_ID,userId);
                    mBaseOperation.addParameter(Constants.ORDER_ID,orderId);
                    mBaseOperation.addParameter(Constants.ORDER_MONEY,orderMoney);
                    mBaseOperation.addParameter(Constants.ORDER_DELIVERYfEE,deliveryFee);
                    mBaseOperation.forward(PayOnlineActivity.class);
                }
            }
        });
    }
}
