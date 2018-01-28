package com.ody.p2p.check.orderoinfo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.check.orderlist.OrderTabAdapter;
import com.ody.p2p.check.orderlist.OrderTabBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.campusapp.router.annotation.RouterMap;

@RouterMap("activity://logistics")
public class LogisticsActivity extends BaseActivity implements View.OnClickListener, OrderTabAdapter.ItemClickListener {
    private RecyclerView logistics_recycleview;
    private ImageView img_logback;
    protected TextView tv_logitics_orderstutaus;
    protected TextView tv_deliveryCompanyName;
    protected TextView tv_deliveryExpressNbr;
    protected RelativeLayout rl_pic;
    protected TextView tv_num;
    protected RecyclerView rv_logistics_tab;
    protected ImageView iv_product;
    protected PackageDialog packageDialog;
    protected List<OrderTabBean> tabs;
    protected OrderTabAdapter orderTabAdapter;
    protected LogisticsAdapter logisticsAdapter;
    public int type=1;//1 买家订单  2、卖家订单  3、售后
    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_logistics;
    }

    @Override
    public void initView(View view) {
        tv_logitics_orderstutaus = (TextView) findViewById(R.id.tv_logitics_orderstutaus);
        tv_deliveryCompanyName = (TextView) findViewById(R.id.tv_deliveryCompanyName);
        tv_deliveryExpressNbr = (TextView) findViewById(R.id.tv_deliveryExpressNbr);

        logistics_recycleview = (RecyclerView) findViewById(R.id.logistics_recycleview);
        logistics_recycleview.setLayoutManager(new LinearLayoutManager(this));

        rl_pic= (RelativeLayout) findViewById(R.id.rl_pic);
        tv_num= (TextView) findViewById(R.id.tv_num);
        rv_logistics_tab= (RecyclerView) findViewById(R.id.rv_logistics_tab);
        iv_product= (ImageView) findViewById(R.id.iv_product);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_logistics_tab.setLayoutManager(linearLayoutManager);

        setPackageDialog();
        packageDialog=new PackageDialog(this);
        logistics_recycleview.setAdapter(logisticsAdapter);

        img_logback = (ImageView) findViewById(R.id.img_logback);
        img_logback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_num.getBackground().setAlpha(100);
    }

    public void setPackageDialog(){
        logisticsAdapter=new LogisticsAdapter(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        type=getIntent().getIntExtra("Logisticstype",1);
        if(type==1||type==2){
            tabs= (List<OrderTabBean>) getIntent().getSerializableExtra("packageList");
            if(tabs!=null&&tabs.size()>0){
                getLogistics(tabs.get(0).orderCode,tabs.get(0).packageCode);
                if(tabs.size()==1){
                    rv_logistics_tab.setVisibility(View.GONE);
                }else{
                    rv_logistics_tab.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rv_logistics_tab.setLayoutManager(linearLayoutManager);
                    orderTabAdapter=new OrderTabAdapter(this);
                    orderTabAdapter.setData(tabs);
                    rv_logistics_tab.setAdapter(orderTabAdapter);
                    orderTabAdapter.setOnItemClickListener(this);
                }
            }
        }else if(type==3){
            String orderCode=getIntent().getStringExtra("orderCode");
            String returnCode=getIntent().getStringExtra("returnCode");
            String exchangeCode=getIntent().getStringExtra("exchangeCode");
            if(!TextUtils.isEmpty(orderCode)&&!TextUtils.isEmpty(returnCode)){
                getAftersaleLogistics(orderCode,returnCode,exchangeCode);
            }
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    /**
     * 适配数据
     *
     * @param data
     */
    private void setLogistics_recycleview(final LogisticsBean.DataBean data) {
        if(data.orderInfo!=null){
            if(!TextUtils.isEmpty(data.orderInfo.packageStatusName)){
                tv_logitics_orderstutaus.setText(data.orderInfo.packageStatusName.trim());
            }
            if(!TextUtils.isEmpty(data.orderInfo.distributors)){
                tv_deliveryCompanyName.setText(data.orderInfo.distributors.trim());
            }
            if(!TextUtils.isEmpty(data.orderInfo.trackingNumber)){
                tv_deliveryExpressNbr.setText(data.orderInfo.trackingNumber.trim());
            }

            if(data.orderInfo.productList!=null&&data.orderInfo.productList.size()>0){
                if(!TextUtils.isEmpty(data.orderInfo.productList.get(0).picUrl)){
                    GlideUtil.display(this,data.orderInfo.productList.get(0).picUrl).into(iv_product);
                }
                if(data.orderInfo.productList.size()>1){
                    tv_num.setVisibility(View.VISIBLE);
                    tv_num.getBackground().setAlpha(100);
                    tv_num.setText(getString(R.string.common)+data.orderInfo.productList.size()+mContext.getString(R.string.piece)+mContext.getString(R.string.product));
                    List<String> paths=new ArrayList<>();
                    for(int i=0;i<data.orderInfo.productList.size();i++){
                        paths.add(data.orderInfo.productList.get(i).picUrl);
                    }
                    packageDialog.setPaths(paths);
                    iv_product.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            packageDialog.showAtLocation(getLayoutInflater().inflate(R.layout.activity_order_logistics, null), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                        }
                    });
                }
            }
        }
        if(data.orderMessageList!=null&&data.orderMessageList.size()>0){
            logisticsAdapter.setData(data.orderMessageList);
        }
    }


    /**
     * 获取物流信息
     */
    protected void getLogistics(String orderCode,String packageCode) {
        if(TextUtils.isEmpty(orderCode)||TextUtils.isEmpty(packageCode)){
            return;
        }
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("packageCode", packageCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        String url="";
        if(type==1){
            url=Constants.ORDER_LOGSITICS;
        }else if(type==2){
            url=Constants.SHOP_ORDER_LOGISTICS;
        }
        OkHttpManager.getAsyn(url, params, new OkHttpManager.ResultCallback<LogisticsBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(LogisticsActivity.this, "onError", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(LogisticsBean response) {
                if (response != null && null != response.data) {
                    setLogistics_recycleview(response.data);
                } else {
                    Toast.makeText(getContext(), R.string.no_logistics_information, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getAftersaleLogistics(String orderCode, String returnCode,String exchangeCode){
        showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("orderCode",orderCode);
        params.put("returnCode",returnCode);
        params.put("exchangeCode",exchangeCode);
        OkHttpManager.postAsyn(Constants.AFTERSALE_LOGISTICS, new OkHttpManager.ResultCallback<LogisticsBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(LogisticsBean response) {
                if (response != null && null != response.data) {
                    setLogistics_recycleview(response.data);
                }else{
                    ToastUtils.showToast(R.string.no_logistics_information);
                }
            }
        },params);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.rl_pic){
            packageDialog.showAtLocation(getLayoutInflater().inflate(R.layout.activity_order_logistics, null), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void onItemClicklistener(int pos, OrderTabBean orderTabBean) {
        orderTabAdapter.setChecked(pos);
        getLogistics(orderTabBean.orderCode,orderTabBean.packageCode);
    }

}
