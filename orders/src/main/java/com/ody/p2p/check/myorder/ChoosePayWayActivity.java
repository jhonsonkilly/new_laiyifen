package com.ody.p2p.check.myorder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.JumpUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public class ChoosePayWayActivity extends BaseActivity {

    private ImageView iv_back;
    private ListView lv_pay;
    protected PayWayAdapter adapter;
    private List<ConfirmOrderBean.DataEntity.PaymentsEntity> payments;

    @Override
    public void initPresenter() {
        payments= (List<ConfirmOrderBean.DataEntity.PaymentsEntity>) getIntent().getSerializableExtra("paywaylist");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_choose_pay_way;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_pay= (ListView) findViewById(R.id.lv_pay);
        adapter=new PayWayAdapter(this);
        lv_pay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setChecked(i);
                savePayment(adapter.getItem(i).paymentId);
            }
        });
        lv_pay.setAdapter(adapter);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        adapter.addData(payments);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void savePayment(long paymentId){
        Map<String,String> params=new HashMap<>();
        params.put("paymentId",paymentId+"");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.SAVE_PAYMENT,new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response==null){
                    return;
                }
                if(response.code.equals("0")){
                    JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
                    finish();
                }
            }
        },params);
    }

}
