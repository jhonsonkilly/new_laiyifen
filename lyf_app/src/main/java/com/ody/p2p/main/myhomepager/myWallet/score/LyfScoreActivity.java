package com.ody.p2p.main.myhomepager.myWallet.score;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreDetailsBean;
import com.ody.p2p.main.myhomepager.myWallet.score.adapter.LyfScoreListAdapter;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;


/**
 * Created by meijunqiang on 2017/3/14.
 * 描述：来伊份积分
 */

public class LyfScoreActivity extends BaseActivity implements LyfScoreView, View.OnClickListener {

    protected LyfScoreImpl mLyfScorePresenter;
    private RelativeLayout mRelativeLayoutBack;
    protected LinearLayout mLinearLayoutRule;
    protected TextView mTextViewRule;
    private TextView mTextViewAccount;//积分数量
    protected TextView mTextViewExchange;//兑换伊豆

    private OdySwipeRefreshLayout mOdySwipeRefreshLayout;
    private ListView mListView;
    private TextView mTextViewNoData;

    private LinearLayout mLinearLayoutAllDetails;//全部明细
    private TextView mTextViewAllDetails;
    private View mViewAllDetails;
    private LinearLayout mLinearLayoutIncomeDetails;//收入明细
    private TextView mTextViewIncomeDetails;
    private View mViewIncomeDetails;
    private LinearLayout mLinearLayoutOutcomeDetails;//支出明细
    private TextView mTextViewOutcomeDetails;
    private View mViewOutcomeDetails;

    private LyfScoreListAdapter mLyfScoreListAdapter;
    protected int mPageNo = 1;
    private int mTotalCount = 0;

    protected String mType = "0";//0-全部 1-收入 2-支出

    @Override
    public void initPresenter() {
        mLyfScorePresenter = new LyfScoreImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLyfScorePresenter.getLyfScore();
        mLyfScorePresenter.getList(String.valueOf(mPageNo), mType);
        mLyfScorePresenter.getExchangeRule();
    }

    @Override
    public int bindLayout() {
        return R.layout.lyf_score_activity;
    }

    @Override
    public void initView(View view) {
        mRelativeLayoutBack = (RelativeLayout) view.findViewById(R.id.activity_lyf_score_back);
        mTextViewAccount = (TextView) view.findViewById(R.id.lyf_score_activity_current_score);
        mTextViewExchange = (TextView) view.findViewById(R.id.lyf_score_activity_exchange);
        mLinearLayoutRule = (LinearLayout) view.findViewById(R.id.activity_lyf_score_linearlayout_rule);
        mTextViewRule = (TextView) view.findViewById(R.id.lyf_score_activity_rule);
        mOdySwipeRefreshLayout = (OdySwipeRefreshLayout) view.findViewById(R.id.activity_lyf_score_swiperefreshlayout);
        mOdySwipeRefreshLayout.setHeaderViewBackgroundColor(0x00ffffff);
        mOdySwipeRefreshLayout.setTargetScrollWithLayout(true);
        mOdySwipeRefreshLayout.setOdyDefaultView(true);
        mOdySwipeRefreshLayout.setCanRefresh(false);
        mListView = (ListView) view.findViewById(R.id.activity_lyf_score_listview);
        mLyfScoreListAdapter = new LyfScoreListAdapter(this);
        mTextViewNoData = (TextView) view.findViewById(R.id.activity_lyf_score_textview_no_data);

        mLinearLayoutAllDetails = (LinearLayout) view.findViewById(R.id.activity_lyf_score_details_all);
        mTextViewAllDetails = (TextView) view.findViewById(R.id.activity_lyf_score_details_all_textview);
        mViewAllDetails = view.findViewById(R.id.activity_lyf_score_details_all_view);

        mLinearLayoutIncomeDetails = (LinearLayout) view.findViewById(R.id.activity_lyf_score_details_income);
        mTextViewIncomeDetails = (TextView) view.findViewById(R.id.activity_lyf_score_details_income_textview);
        mViewIncomeDetails = view.findViewById(R.id.activity_lyf_score_details_income_view);

        mLinearLayoutOutcomeDetails = (LinearLayout) view.findViewById(R.id.activity_lyf_score_details_outcome);
        mTextViewOutcomeDetails = (TextView) view.findViewById(R.id.activity_lyf_score_details_outcome_textview);
        mViewOutcomeDetails = view.findViewById(R.id.activity_lyf_score_details_outcome_view);
    }

    @Override
    public void initListener() {
        mRelativeLayoutBack.setOnClickListener(this);
        mTextViewExchange.setOnClickListener(this);
        mLinearLayoutAllDetails.setOnClickListener(this);
        mLinearLayoutIncomeDetails.setOnClickListener(this);
        mLinearLayoutOutcomeDetails.setOnClickListener(this);
        mOdySwipeRefreshLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mLyfScoreListAdapter.getCount() < mTotalCount) {
                    mPageNo++;
                    mLyfScorePresenter.getList(String.valueOf(mPageNo), mType);
                } else {
                    Toast.makeText(LyfScoreActivity.this, "不要再拉了,没有更多了!", Toast.LENGTH_SHORT).show();
                    mOdySwipeRefreshLayout.setTargetScrollWithLayout(false);
                    mOdySwipeRefreshLayout.setOdyDefaultView(false);
                    mOdySwipeRefreshLayout.setLoadMore(false);
                }
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
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
    public void initScore(LyfScoreBean bean) {
        mTextViewAccount.setText(String.valueOf(bean.data.amountBalance));
    }

    @Override
    public void initRule(LyfRuleBean bean) {
        if (bean != null) {
            if (bean.data.canExchange == 0) {
                mLinearLayoutRule.setVisibility(View.INVISIBLE);
            } else if (bean.data.canExchange == 1) {
                mLinearLayoutRule.setVisibility(View.VISIBLE);
                mTextViewRule.setText(bean.data.beforeRate + "积分=" + bean.data.afterRate + "伊豆");
            }
        }
    }

    @Override
    public void initListDetails(LyfScoreDetailsBean bean) {
        if (bean != null && bean.data != null && bean.data.data != null && bean.data.data.size() > 0) {
            mOdySwipeRefreshLayout.setVisibility(View.VISIBLE);
            mTextViewNoData.setVisibility(View.GONE);
            if (mPageNo == 1) {
                mTotalCount = bean.data.totalCount;
                mLyfScoreListAdapter.setmArrayList(bean.data.data);
                mListView.setAdapter(mLyfScoreListAdapter);
            } else {
                mLyfScoreListAdapter.addArrayList(bean.data.data);
            }
        } else {
            mOdySwipeRefreshLayout.setVisibility(View.GONE);
            mTextViewNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_lyf_score_back) {
            finish();
        } else if (v.getId() == R.id.lyf_score_activity_exchange) {
            JumpUtils.ToActivity(JumpUtils.LYF_SCORE_EXCHANGE);
        } else if (v.getId() == R.id.activity_lyf_score_details_all) {
            mType = "0";
            detailsOnClick(0);
            mLyfScorePresenter.getList(String.valueOf(mPageNo), mType);
        } else if (v.getId() == R.id.activity_lyf_score_details_income) {
            mType = "1";
            detailsOnClick(1);
            mLyfScorePresenter.getList(String.valueOf(mPageNo), mType);
        } else if (v.getId() == R.id.activity_lyf_score_details_outcome) {
            mType = "2";
            detailsOnClick(2);
            mLyfScorePresenter.getList(String.valueOf(mPageNo), mType);
        }
    }

    /**
     * 积分明细点击事件  0-全部 1-收入 2-支出
     *
     * @param i
     */
    private void detailsOnClick(int i) {
        mOdySwipeRefreshLayout.setLoadMore(true);
        mOdySwipeRefreshLayout.setTargetScrollWithLayout(true);
        mOdySwipeRefreshLayout.setOdyDefaultView(true);
        mPageNo = 1;
        mTotalCount = 0;
        if (i == 0) {
            mTextViewAllDetails.setTextColor(getResources().getColor(R.color.c_ff690));
            mViewAllDetails.setBackgroundColor(getResources().getColor(R.color.c_ff690));
            mViewAllDetails.setVisibility(View.VISIBLE);
            mTextViewIncomeDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewIncomeDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewIncomeDetails.setVisibility(View.INVISIBLE);
            mTextViewOutcomeDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewOutcomeDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewOutcomeDetails.setVisibility(View.INVISIBLE);
        } else if (i == 1) {
            mTextViewAllDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewAllDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewAllDetails.setVisibility(View.INVISIBLE);
            mTextViewIncomeDetails.setTextColor(getResources().getColor(R.color.c_ff690));
            mViewIncomeDetails.setBackgroundColor(getResources().getColor(R.color.c_ff690));
            mViewIncomeDetails.setVisibility(View.VISIBLE);
            mTextViewOutcomeDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewOutcomeDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewOutcomeDetails.setVisibility(View.INVISIBLE);
        } else if (i == 2) {
            mTextViewAllDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewAllDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewAllDetails.setVisibility(View.INVISIBLE);
            mTextViewIncomeDetails.setTextColor(getResources().getColor(R.color.gray_6));
            mViewIncomeDetails.setBackgroundColor(getResources().getColor(R.color.gray_6));
            mViewIncomeDetails.setVisibility(View.INVISIBLE);
            mTextViewOutcomeDetails.setTextColor(getResources().getColor(R.color.c_ff690));
            mViewOutcomeDetails.setBackgroundColor(getResources().getColor(R.color.c_ff690));
            mViewOutcomeDetails.setVisibility(View.VISIBLE);
        }

    }
}
