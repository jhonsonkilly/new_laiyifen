package com.ody.p2p.RefoundInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2016/10/11.
 */
public class LogisticsCompanyActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageView img_finish_icon;
    private EditText et_company_name;
    private ListView lv_logistics;
    private LogisticCompanyAdapter adapter;
    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.layout_logistics_company;
    }

    @Override
    public void initView(View view) {
        img_finish_icon= (ImageView) findViewById(R.id.img_finish_icon);
        et_company_name= (EditText) findViewById(R.id.et_company_name);
        lv_logistics= (ListView) findViewById(R.id.lv_logistics);
        adapter=new LogisticCompanyAdapter();
        lv_logistics.setAdapter(adapter);
        lv_logistics.setOnItemClickListener(this);
        img_finish_icon.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        et_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getLogisticCompany(et_company_name.getText().toString());
            }
        });
    }

    @Override
    public void resume() {
        getLogisticCompany("");
    }

    @Override
    public void destroy() {

    }



    private void getLogisticCompany(final String keyword){
        showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("name",keyword);
        OkHttpManager.getAsyn(Constants.LOGISTC_COMPANY_LIST,params, new OkHttpManager.ResultCallback<LogisticsCompanyBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(LogisticsCompanyBean response) {
                if(TextUtils.isEmpty(keyword)){
                    if(response!=null&&response.data!=null&&response.data.size()>0){
                        adapter.setData(response.data);
                    }
                }else{
                    LogisticsCompanyBean.DataBean bean=new LogisticsCompanyBean.DataBean();
                    bean.name=keyword;
                    if(response!=null){
                        adapter.addTop(bean,response.data);
                    }else{
                        adapter.addTop(bean,null);
                    }

                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.putExtra("logisticsName",adapter.getItem(position).name);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_finish_icon){
            finish();
        }
    }
}
