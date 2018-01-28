package com.ody.p2p.main.myhomepager.myWallet.score;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LyfExchangeBean;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;

/**
 * 来伊份 我的钱包 --> 积分 兑换伊豆界面
 * Created by caishengya on 2017/3/21.
 */

public class LyfExchangeYPeasActivity extends BaseActivity implements LyfExchangeView, View.OnClickListener {

    private LyfExchangePresenter mLyfExchangePresenter;
    private RelativeLayout mRelativeBack;
    private TextView mTextViewRule;
    private EditText mEditText;
    private ImageView mImageViewClear;
    private TextView mTextViewSurplus;
    private TextView mTextViewExchangeAll;//全部兑换
    private TextView mTextViewExchange;//兑换

    private long mPeasCount = 0;

    @Override
    public void initPresenter() {
        mLyfExchangePresenter = new LyfExchangeImpl(this);
        mLyfExchangePresenter.getRule();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_lyf_exchange_ypeas;
    }

    @Override
    public void initView(View view) {
        mRelativeBack = (RelativeLayout) view.findViewById(R.id.activity_lyf_exchange_back);
        mTextViewRule = (TextView) view.findViewById(R.id.activity_lyf_exchange_ypeas_rule);
        mEditText = (EditText) view.findViewById(R.id.activity_lyf_exchange_ypeas_edittext);
        mImageViewClear = (ImageView) view.findViewById(R.id.activity_lyf_exchange_ypeas_clear);
        mTextViewSurplus = (TextView) view.findViewById(R.id.activity_lyf_exchange_ypeas_surplus);
        mTextViewExchangeAll = (TextView) view.findViewById(R.id.activity_lyf_exchange_ypeas_exchange_all);
        mTextViewExchange = (TextView) view.findViewById(R.id.activity_lyf_exchange_ypeas_exchange);
    }

    @Override
    public void initListener() {
        mRelativeBack.setOnClickListener(this);
        mImageViewClear.setOnClickListener(this);
        mTextViewExchangeAll.setOnClickListener(this);
        mTextViewExchange.setOnClickListener(this);
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
        if (v.getId() == R.id.activity_lyf_exchange_back) {
            finish();
        } else if (v.getId() == R.id.activity_lyf_exchange_ypeas_clear) {
            mEditText.setText("");
        } else if (v.getId() == R.id.activity_lyf_exchange_ypeas_exchange_all) {
            if (mPeasCount > 0) {
                mLyfExchangePresenter.exchange(mPeasCount);
            } else {
                Toast.makeText(this, "暂无可兑换积分", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.activity_lyf_exchange_ypeas_exchange) {
            String countStr = mEditText.getText().toString();
            if (!TextUtils.isEmpty(countStr)) {
                long count = Long.parseLong(countStr);
                if (mPeasCount > 0) {
                    if (count < mPeasCount) {
                        mLyfExchangePresenter.exchange(count);
                    } else {
                        Toast.makeText(this, "最多只兑换" + mPeasCount + "积分", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "暂无可兑换积分", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入需要兑换的积分", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void initRule(LyfRuleBean bean) {
        if (bean != null) {
            mTextViewRule.setText("兑换规则: " + bean.data.beforeRate + "积分=" + bean.data.afterRate + "伊豆");
            mPeasCount = bean.data.amount;
            mTextViewSurplus.setText(" " + mPeasCount);
            mEditText.setHint("最多可兑换" + bean.data.canExchangeAmount + "伊豆");
        }
    }

    @Override
    public void exchangeYPeas(LyfExchangeBean bean) {
        if (bean != null) {
            if (bean.code.equals("0")) {
                Toast.makeText(this, "兑换成功", Toast.LENGTH_SHORT).show();
                mLyfExchangePresenter.getRule();
            }
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
