package com.ody.p2p.main.myhomepager.myWallet.yidou;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;
import com.ody.p2p.main.myhomepager.myWallet.score.LyfScoreActivity;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * Created by meijunqiang on 2017/3/14.
 * 描述：伊豆
 */

public class YiDouActivity extends LyfScoreActivity {
    @Override
    public void initView(View view) {
        super.initView(view);
        TextView yidou_title = (TextView) findViewById(R.id.lyf_score_activity_title);
        ImageView yidou_img = (ImageView) findViewById(R.id.lyf_score_activity_img);
        TextView yidou_unit = (TextView) findViewById(R.id.lyf_score_activity_unit);
        TextView yidou_text = (TextView) findViewById(R.id.lyf_score_activity_current_text);
        TextView yidou_details_text = (TextView) findViewById(R.id.activity_lyf_score_details_text);
        findViewById(R.id.lyf_yidou_activity_rule).setVisibility(View.VISIBLE);
        mTextViewRule.setVisibility(View.GONE);
        mTextViewExchange.setVisibility(View.GONE);
        mLinearLayoutRule.setVisibility(View.VISIBLE);
        findViewById(R.id.lyf_yidou_activity_exchange).setVisibility(View.VISIBLE);
        yidou_title.setText("伊豆");
        yidou_img.setImageResource(R.drawable.wallet_yd_ic);
        yidou_unit.setText("个");
        yidou_text.setText("当前伊豆数");
        yidou_details_text.setText("伊豆明细");
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLinearLayoutRule.getLayoutParams();
        layoutParams.rightMargin = 0;
        mLinearLayoutRule.setLayoutParams(layoutParams);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mLyfScorePresenter.pageType = "2";
    }

    @Override
    public void initRule(LyfRuleBean bean) {

    }

    /**
     * 去伊豆规则页
     *
     * @param view
     */
    public void toYidouRule(View view) {
        JumpUtils.ToActivity(JumpUtils.YIDOU_RULE);
    }

    /**
     * 去兑换优惠券页面
     *
     * @param view
     */
    public void changeCoupon(View view) {
        ToastUtils.showShort("兑换优惠券");
    }
}
