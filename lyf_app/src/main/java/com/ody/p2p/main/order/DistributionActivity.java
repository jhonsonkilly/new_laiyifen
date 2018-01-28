package com.ody.p2p.main.order;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

/**
 * Created by ${tang} on 2016/12/5.
 */

public class DistributionActivity extends BaseActivity implements View.OnClickListener, Distributionview {

    private ImageView iv_back;
    private TextView tv_submit;
    private ListView lv_distribution;
    private ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity modeEntity;
    private DistributionAdapter adapter;

    private DistruibutionPresenter presenter;

    @Override
    public void initPresenter() {
        presenter=new DistributionPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_distribution;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_submit= (TextView) findViewById(R.id.tv_submit);
        lv_distribution= (ListView) findViewById(R.id.lv_distribution);
        adapter=new DistributionAdapter();
        lv_distribution.setAdapter(adapter);
        tv_submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        modeEntity= (ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity) getIntent().getSerializableExtra("dispatchMode");
        if(modeEntity!=null&&modeEntity.deliveryModeList.size()>0){
            adapter.addData(modeEntity.deliveryModeList);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_submit){
            String delieveryCode="";
            int isTakebyself=-1;
            if(adapter.getCount()>0){
                for(int i=0;i<adapter.getCount();i++){
                    if(adapter.getItem(i)!=null&&adapter.getItem(i).isDefault==1){
                        delieveryCode=adapter.getItem(i).code;
                        isTakebyself=adapter.getItem(i).isTakeTheir;
                    }
                }
            }
            if(isTakebyself==1){//门店自提跳转到门店列表页
                    Bundle bundle=new Bundle();
                    bundle.putString("merchantId",modeEntity.merchantId+"");
                    bundle.putString("deliverycode",delieveryCode);
                    JumpUtils.ToActivity(JumpUtils.CHOOSE_STORE,bundle);
            }else if(isTakebyself==0){//保存
                presenter.saveDeliveryMode(modeEntity.merchantId+"",delieveryCode);
            }
        }else if(v.getId()==R.id.iv_back){
            finish();
        }
    }

    @Override
    public void finishActivity() {
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
        finish();
    }
}
