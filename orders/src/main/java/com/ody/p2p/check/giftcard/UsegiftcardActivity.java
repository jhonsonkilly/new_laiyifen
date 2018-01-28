package com.ody.p2p.check.giftcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

public class UsegiftcardActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private RelativeLayout rl_add;
    private ListView lv_cp;
    private TextView tv_ok;
    private ImageView iv_back;
    private TextView tv_add;
    private GiftCardAdapter adapter;
    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.layout_use_coupon;
    }

    @Override
    public void initView(View view) {
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.used_gift_card);
        rl_add= (RelativeLayout) findViewById(R.id.rl_add);
        lv_cp= (ListView) findViewById(R.id.lv_cp);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_add= (TextView) findViewById(R.id.tv_add);
        tv_add.setTextColor(RUtils.getColorRes(this,RUtils.THEME_COLOR));
        iv_back.setOnClickListener(this);
        rl_add.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        adapter=new GiftCardAdapter(this);
        adapter.isUse(true);
        lv_cp.setAdapter(adapter);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {
        requestList();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }else if(v.getId()==R.id.rl_add){
            JumpUtils.ToActivity(JumpUtils.BIND_GIFTCARD);
        }else if(v.getId()==R.id.tv_ok){
            String ids="";
            if(adapter.getAll()!=null&&adapter.getAll().size()>0){
                for(int i=0;i<adapter.getAll().size();i++){
                    if(adapter.getAll().get(i).selected==1){
                        ids+=adapter.getAll().get(i).giftCardId+",";
                    }
                }
            }
            if(!TextUtils.isEmpty(ids)){
                ids=ids.substring(0,ids.length()-1);
                saveGiftcard(ids);
            }
        }
    }

    private void requestList(){
        showLoading();
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.GET_GIFTCARD, params,new OkHttpManager.ResultCallback<GiftCardBean>(){
            @Override
            public void onFinish() {
                super.onFinish();
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(GiftCardBean response) {
                if(response!=null&&response.data!=null&&response.data.giftCartBindRecords!=null&&response.data.giftCartBindRecords.size()>0){
                    for(int i=0;i<response.data.giftCartBindRecords.size();i++){
                        response.data.giftCartBindRecords.get(i).selected=0;
                    }
                    adapter.addData(response.data.giftCartBindRecords);
                }
            }
        });

    }

    private void saveGiftcard(String ids){
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("giftCardIds",ids);
        showLoading();
        OkHttpManager.postAsyn(Constants.SAVE_GIFECARD,new OkHttpManager.ResultCallback<BaseRequestBean>() {

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
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        },params);
    }
}
