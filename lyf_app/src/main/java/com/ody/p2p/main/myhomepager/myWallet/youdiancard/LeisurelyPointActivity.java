package com.ody.p2p.main.myhomepager.myWallet.youdiancard;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LeisurelyPointBean;
import com.ody.p2p.main.views.LyfPayTypePopupWindow;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;
import com.ody.p2p.utils.JumpUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 我的钱包 --> 悠点卡 界面
 * <p>
 * Created by caishengya on 2017/2/27.
 */

public class LeisurelyPointActivity extends BaseActivity implements LeisurelyPointView, View.OnClickListener {

    private RelativeLayout mRelativeLayoutBack;
    private TextView mTextViewRecord;//充值记录
    private TextView mTextViewBalance;//悠点卡余额
    private EditText mTextViewRechargeMoney;//充值金额
    private TextView mTextViewRecharge;//充值操作
    private TextView mTextViewSendRedPacket;//发红包
    private LeisurelyPointImpl leisurelyPointPresenter;
    private LyfPayTypePopupWindow mLyfPayTypePopupWindow;
    private IWXAPI api;
    private String money;

    @Override
    public void initPresenter() {
        leisurelyPointPresenter = new LeisurelyPointImpl(this);
        leisurelyPointPresenter.getLeisurePointCount();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_lyf_leisurely_point;
    }

    @Override
    public void initView(View view) {
        mRelativeLayoutBack = (RelativeLayout) view.findViewById(R.id.activity_lyf_leisurely_back);
        mRelativeLayoutBack.setOnClickListener(this);
        mTextViewRecord = (TextView) view.findViewById(R.id.activity_lyf_leisurely_point_recharge_record);
        mTextViewRecord.setOnClickListener(this);
        mTextViewBalance = (TextView) view.findViewById(R.id.activity_lyf_leisurely_point_balance);
        mTextViewRechargeMoney = (EditText) view.findViewById(R.id.activity_lyf_leisurely_point_recharge_money);
        mTextViewRecharge = (TextView) view.findViewById(R.id.activity_lyf_leisurely_point_recharge);
        mTextViewRecharge.setOnClickListener(this);
        mTextViewSendRedPacket = (TextView) view.findViewById(R.id.activity_lyf_leisurely_point_send_red_packet);
        mTextViewSendRedPacket.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        String wxAppID = "wxfac2b15cf7763717";
        api = WXAPIFactory.createWXAPI(mContext, wxAppID);
        api.registerApp(wxAppID);
    }


    @Override
    public void initLeisurelyPoint(LeisurelyPointBean bean) {
        mTextViewBalance.setText(String.valueOf(bean.data.yCardBalance));
    }

    @Override
    public void initPayType(PayTypeListBean bean) {
        if (bean != null && bean.data.commonPayGatewayList != null && bean.data.commonPayGatewayList.size() > 0) {
            mLyfPayTypePopupWindow = new LyfPayTypePopupWindow(this, bean, api, money) {
                @Override
                public void payResult(int code) {
                    if (code == 0) {
                        //充值成功
                        leisurelyPointPresenter.getLeisurePointCount();
                    } else if (code == 1) {
                        //充值失败
                    }
                }
            };
            mLyfPayTypePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else {
            Toast.makeText(this, "当前无法获取支付方式!", Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.activity_lyf_leisurely_back) {
            finish();
        }

        //充值记录
        if (v.getId() == R.id.activity_lyf_leisurely_point_recharge_record) {
            skipActivity(JumpUtils.PAY_RECORD);
        }

        //充值操作
        if (v.getId() == R.id.activity_lyf_leisurely_point_recharge) {
            //获取充值金额
            money = mTextViewRechargeMoney.getText().toString().trim();
            //对输入金额的校验
            if (TextUtils.isEmpty(money)) {
                Toast.makeText(this, "请输入充值金额！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                double doubleMoney = Double.parseDouble(money);
                if (doubleMoney == 0) {
                    Toast.makeText(this, "请输入充值金额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (doubleMoney > 3000) {
                    Toast.makeText(this, "充值金额不能超过3000元！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            leisurelyPointPresenter.getPayType();
        }

        //发红包
        if (v.getId() == R.id.activity_lyf_leisurely_point_send_red_packet) {
            Toast.makeText(this, "发红包", Toast.LENGTH_SHORT).show();
        }
    }
}
