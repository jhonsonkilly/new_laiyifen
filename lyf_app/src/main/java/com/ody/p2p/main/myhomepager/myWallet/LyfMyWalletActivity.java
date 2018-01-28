package com.ody.p2p.main.myhomepager.myWallet;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.MyWalletBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;


/**
 * 来伊份 我的钱包 页面
 * Created by caishengya on 2017/2/27.
 */

public class LyfMyWalletActivity extends BaseActivity implements LyfMyWalletView, View.OnClickListener {

    private LyfMyWalletPresenter lyfMyWalletPresenter;
    private RelativeLayout mRelativeBack;
    private LinearLayout mLinearLayoutLeisurely, mLinearLayoutYICard, mLinearLayoutYIBeans;
    private TextView mTextViewLeisurely, mTextViewYICard, mTextViewYIBeans;

    @Override
    public void initPresenter() {
        lyfMyWalletPresenter = new LyfMyWalletImpl(this);
        lyfMyWalletPresenter.getCounts();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_lyf_my_wallet;
    }

    @Override
    public void initView(View view) {
        mRelativeBack = (RelativeLayout) view.findViewById(R.id.activity_lyf_my_wallet_back);
        mRelativeBack.setOnClickListener(this);
        mTextViewLeisurely = (TextView) view.findViewById(R.id.activity_lyf_my_wallet_leisurely_point_card);
        mTextViewYICard = (TextView) view.findViewById(R.id.activity_lyf_my_wallet_yi_point_card);
        mTextViewYIBeans = (TextView) view.findViewById(R.id.activity_lyf_my_wallet_yi_beans);
        mLinearLayoutLeisurely = (LinearLayout) view.findViewById(R.id.activity_lyf_my_wallet_go_leisurely);
        mLinearLayoutLeisurely.setOnClickListener(this);
        mLinearLayoutYICard = (LinearLayout) view.findViewById(R.id.activity_lyf_my_wallet_go_yi_point_card);
        mLinearLayoutYICard.setOnClickListener(this);
        mLinearLayoutYIBeans = (LinearLayout) view.findViewById(R.id.activity_lyf_my_wallet_go_yi_beans);
        findViewById(R.id.activity_lyf_my_wallet_coupon).setOnClickListener(this);
        findViewById(R.id.activity_lyf_my_wallet_integral).setOnClickListener(this);
        findViewById(R.id.activity_lyf_my_wallet_pick).setOnClickListener(this);
        mLinearLayoutYIBeans.setOnClickListener(this);
        if (OdyApplication.getBoolean(Constants.IS_YCARD, false)) {
            mLinearLayoutLeisurely.setVisibility(View.VISIBLE);
        } else {
            mLinearLayoutLeisurely.setVisibility(View.GONE);
        }
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
        if (v.getId() == R.id.activity_lyf_my_wallet_back) {
            finish();
        } else if (v.getId() == R.id.activity_lyf_my_wallet_go_leisurely) {//去悠点卡
            skip(JumpUtils.LEISURELY);
        } else if (v.getId() == R.id.activity_lyf_my_wallet_go_yi_point_card) {//去伊点卡
            skip(JumpUtils.YIDIAN_CARD);
        } else if (v.getId() == R.id.activity_lyf_my_wallet_go_yi_beans) {//去伊豆
            skip(JumpUtils.YIDOU);
        } else if (v.getId() == R.id.activity_lyf_my_wallet_coupon) {//去优惠券
            skip(JumpUtils.LAIYIFEN_COUPON);
        } else if (v.getId() == R.id.activity_lyf_my_wallet_integral) {//去积分
            skip(JumpUtils.LYFSCORE);
        } else if (v.getId() == R.id.activity_lyf_my_wallet_pick) {//去提货券
            //TODO:meiyizhi 提货券UI有疑问！
            ToastUtils.showShort("提货券");
        }
    }

    @Override
    public void showCounts(MyWalletBean bean) {
        mTextViewLeisurely.setText(String.valueOf(bean.data.yCardBalance));
        mTextViewYICard.setText(String.valueOf(bean.data.eCardBalance));
        mTextViewYIBeans.setText(String.valueOf(bean.data.yBean));
    }

    public void skip(String toName) {
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
            JumpUtils.ToActivity(toName);
        } else {
            //这里未登录，跳转至登录页面
            JumpUtils.ToActivity(JumpUtils.LOGIN);
        }
    }
}
