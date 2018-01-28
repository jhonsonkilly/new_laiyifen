package com.ody.p2p.main.myhomepager.myWallet.yidiancard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.yidiancard.adapter.YidianCardAdapter;
import com.ody.p2p.main.myhomepager.myWallet.yidiancard.bean.ECardListBean;
import com.ody.p2p.main.views.ImToolBar;
import com.ody.p2p.main.views.SimpleCellLinearLayout;
import com.ody.p2p.main.views.YidianFilterPopupWindow;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.PxUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meijunqiang on 2017/3/14.
 * 描述：伊点卡页面
 */

public class YidianCardActivity extends BaseActivity implements View.OnClickListener {
    private SimpleCellLinearLayout yidianCardNumber;
    private SimpleCellLinearLayout yidianCardPwd;
    private Button yidianCardAdd;
    private RadioGroup yidianCardRgpFilter;
    private RecyclerView yidianCardRecycler;
    private ImToolBar yidianCardTb;
    //    private String[] filterStr = {"金额从低到高", "金额从高到低", "<50元", "50元", "100元", "200元", "500元", "1000元"};
    private String[] filterStr = {};
    private int filterButtonId = R.drawable.selector_yidian_card_filter;
    private int pageNo = 1;
    private YidianCardAdapter mAdapter;
    private int status = 1;
    private int queryCondition = 1;
    private List<ECardListBean.DataEntity.ConditionListEntity> mConditionList;
    private View yidianCardNolist;//无列表显示

    @Override
    public void initPresenter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.yidian_card_activity;
    }

    @Override
    public void initView(View view) {
        yidianCardNolist = findViewById(R.id.yidian_card_no_list);
        yidianCardTb = (ImToolBar) findViewById(R.id.yidian_card_tb);
        yidianCardNumber = (SimpleCellLinearLayout) findViewById(R.id.yidian_card_number);
        yidianCardPwd = (SimpleCellLinearLayout) findViewById(R.id.yidian_card_pwd);
        yidianCardAdd = (Button) findViewById(R.id.yidian_card_add);
        yidianCardRgpFilter = (RadioGroup) findViewById(R.id.yidian_card_rgp_filter);
        yidianCardRecycler = (RecyclerView) findViewById(R.id.yidian_card_recycler);
        yidianCardAdd.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        //伊点卡列表适配
        mAdapter = new YidianCardAdapter(this, new ArrayList<ECardListBean.DataEntity.EcardListEntity>());
        yidianCardRecycler.setLayoutManager(new LinearLayoutManager(this));
        yidianCardRecycler.setAdapter(mAdapter);
        //简单的滑到底部加载更多
        yidianCardRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑过的高度和当前高度与整个控件高度的对比
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    int scrollState = yidianCardRecycler.getScrollState();
                    if (scrollState == 0) {
                        return;
                    }
                    if (mAdapter != null && mAdapter.getItemCount() % 10 != 0) {
                        Toast.makeText(YidianCardActivity.this, "没有更多了!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requestECardList();
                }
            }
        });
        yidianCardTb.getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YidianFilterPopupWindow.getInstance().showAsDropDown(yidianCardTb.getTvRightText(), PxUtils.dipTopx(-75), PxUtils.dipTopx(10));
            }
        });
        //筛选栏点击事件
        YidianFilterPopupWindow.getInstance().setOnCheckedChangeListener(new YidianFilterPopupWindow.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position) {
                pageNo = 1;
                mAdapter.setData(new ArrayList<ECardListBean.DataEntity.EcardListEntity>());
                if (mConditionList != null) {
                    Log.d("mConditionList", "筛选栏点击");
                    queryCondition = mConditionList.get(position).getValueId();
                    requestECardList();
                }
            }
        });
        //选择伊点卡状态
        yidianCardRgpFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pageNo = 1;
                mAdapter.setData(new ArrayList<ECardListBean.DataEntity.EcardListEntity>());
                switch (checkedId) {
                    case R.id.yidian_card_grp_0:
                        status = 1;
                        requestECardList();
                        break;
                    case R.id.yidian_card_grp_1:
                        status = 2;
                        requestECardList();
                        break;
                    case R.id.yidian_card_grp_2:
                        status = 3;
                        requestECardList();
                        break;
                    case R.id.yidian_card_grp_3:
                        status = 4;
                        requestECardList();
                        break;
                }
            }
        });
        requestECardList();
    }

    /**
     * 获取伊点卡列表
     */
    private void requestECardList() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("pageSize", "20");
        params.put("pageNo", pageNo + "");
        params.put("status", status + "");//1 未使用 2 已使用 3 已赠送 4 已过期
        params.put("queryCondition", queryCondition + "");//1 金额从低到高 2 金额从高到低 3 <50 5、 50 6、 100 7、200 8、500 9、1000
        showLoading();
        OkHttpManager.postAsyn(Constants.ECardList, new OkHttpManager.ResultCallback<ECardListBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                hideLoading();
            }

            @Override
            public void onResponse(ECardListBean response) {
                hideLoading();
                yidianCardNolist.setVisibility(View.GONE);
                if (null != response && null != response.getData()) {
                    if (null != response.getData().getEcardList() && response.getData().getEcardList().size() > 0) {
                        pageNo++;
                        mAdapter.addData(response.getData().getEcardList());
                    }
                    List<ECardListBean.DataEntity.ConditionListEntity> conditionList = response.getData().getConditionList();
                    if (conditionList != null && conditionList.size() > 0) {
                        mConditionList = conditionList;
                        filterStr = new String[conditionList.size()];
                        for (int i = 0; i < conditionList.size(); i++) {
                            filterStr[i] = conditionList.get(i).getValueName();
                        }
                        YidianFilterPopupWindow.getInstance().builder(YidianCardActivity.this, filterStr, filterButtonId);
                    }
                }
                if (null != mAdapter && mAdapter.getItemCount() <= 0) {
                    yidianCardNolist.setVisibility(View.VISIBLE);
                } else if (mAdapter != null && mAdapter.getItemCount() >= 20 && null != response && null != response.getData() && null != response.getData().getEcardList() && response.getData().getEcardList().size() == 0) {
                    //服务端所有数据刚好是20的整数倍
                    Toast.makeText(YidianCardActivity.this, "没有更多了!", Toast.LENGTH_SHORT).show();
                }

            }
        }, params);
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yidian_card_add:
                bindECard();
                break;
        }
    }

    /**
     * 添加伊点卡
     */
    private void bindECard() {
        if (TextUtils.isEmpty(yidianCardNumber.getEtText())) {
            Toast.makeText(YidianCardActivity.this, "请输入卡号", Toast.LENGTH_SHORT).show();
            yidianCardNumber.etRequestFocus();
            return;
        }
        if (TextUtils.isEmpty(yidianCardPwd.getEtText())) {
            Toast.makeText(YidianCardActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            yidianCardPwd.etRequestFocus();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("cardCode", yidianCardNumber.getEtText());
        params.put("cardPwd", yidianCardPwd.getEtText());
        showLoading();
        OkHttpManager.postAsyn(Constants.BindECard, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                hideLoading();
                Toast.makeText(YidianCardActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                hideLoading();
                if (response != null) {
                    if (TextUtils.isEmpty(response.message)) {
                        if ("0".equals(response.code)) {
                            response.message = "添加成功";
                        } else {
                            response.message = "添加失败";
                        }
                    }
                    Toast.makeText(YidianCardActivity.this, response.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(YidianCardActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, params);
    }
}
