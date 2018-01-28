package com.umaman.laiyifen.wxapi;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.pay.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.RUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseActivity implements payResult, IWXAPIEventHandler{
    Intent intent;
    private static final int PAY_SUCCESS= 0;
    private static final int PAY_FAIL= 1;
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == PAY_SUCCESS) {
                EventBus.getDefault().post(new PayEvent(PAY_SUCCESS));
            } else {
                EventBus.getDefault().post(new PayEvent(PAY_FAIL));
            }
        }
        finish();
    }


    /**
     * 支付成功
     */
    @Override
    public void paySueecss() {
        JumpUtils.ToActivity(JumpUtils.PAYSUCCESS);
    }

    /**
     * 支付失败
     */
    @Override
    public void payDefeated() {
        JumpUtils.ToActivity(JumpUtils.PAYFAIL);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_settle_accounts_result;
    }

    @Override
    public void initView(View view) {
        String wxAppID = getString(RUtils.getStringRes(this, RUtils.WX_APP_ID));
        api = WXAPIFactory.createWXAPI(this, wxAppID);
        api.handleIntent(getIntent(), this);
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
}