package com.ody.p2p.main.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.wxlib.util.NetworkUtil;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.dao.SearchRiCiEntityDao;
import com.ody.p2p.entity.SearchRiCiEntity;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.searchkey.HistoryBean;
import com.ody.p2p.search.searchkey.SearchHistoryBean;
import com.ody.p2p.search.searchkey.SearchHotWordAdapter;
import com.ody.p2p.search.searchkey.SearchKeywordListAdapter;
import com.ody.p2p.search.searchkey.SearchKeywordListBean;
import com.ody.p2p.search.searchkey.SearchPresenter;
import com.ody.p2p.search.searchkey.SearchPresenterImpl;
import com.ody.p2p.search.searchkey.SearchView;
import com.ody.p2p.search.searchkey.flowTagLayout.FlowTagLayout;
import com.ody.p2p.search.searchkey.flowTagLayout.OnTagClickListener;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.MyGridView;
import com.ody.p2p.views.MyScrollView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by ${tang} on 2016/12/8.
 */

public class LYFSearchActivity extends BaseActivity implements SearchView, View.OnClickListener, OnTagClickListener, AdapterView.OnItemClickListener {

    private ImageView iv_scan;
    private TextView tv_search;
    private ImageView iv_delete;
    private EditText et_keyword;
    private ImageView iv_back;
    private FlowTagLayout fl_hot;
    private FlowTagLayout fl_history;
    private TextView tv_clear;
    private MyGridView rv_history;
    private SearchPresenter mPresenter;
    private SearchHotWordAdapter hotAdapter;
    private SearchHotWordAdapter historyAdapter;
    private LinearLayout ll_history;
    private LinearLayout ll_hot;
    private RecyclerView rv_search_list;
    private SearchKeywordListAdapter searchAdapter;
    private MyScrollView ms_root;

    private String keyword;
    private OdySwipeRefreshLayout swipeLayout;
    private SearchFootAdapter footAdapter;
    private RelativeLayout rl_foot;
    private int pageNo = 1;
    private int totalNum = 0;

    private List<FuncBean.Data.AdSource> hotList;

    protected String hisMpIds = "";

    protected List<HistoryBean.FootStepVO> searchList;

    private FuncBean.Data.AdSource mSearchDefaultAd;

    private String merchantId;

    @Override
    public void initPresenter() {
        mPresenter = new SearchPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_search;
    }

    @Override
    public void initView(View view) {
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        et_keyword = (EditText) findViewById(R.id.et_keyword);
        fl_hot = (FlowTagLayout) findViewById(R.id.fl_hot);
        fl_history = (FlowTagLayout) findViewById(R.id.fl_history);
        rv_history = (MyGridView) findViewById(R.id.rv_history);
        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
        ll_history = (LinearLayout) findViewById(R.id.ll_history);
        swipeLayout = (OdySwipeRefreshLayout) findViewById(R.id.swipeLayout);
        ms_root = (MyScrollView) findViewById(R.id.ms_root);
        rl_foot = (RelativeLayout) findViewById(R.id.rl_foot);
        swipeLayout.setCanRefresh(false);
        swipeLayout.setOdyDefaultView(true);
        searchList = new ArrayList<>();
        footAdapter = new SearchFootAdapter(this, searchList);
        rv_history.setAdapter(footAdapter);
        rv_history.setOnItemClickListener(this);
        rv_search_list = (RecyclerView) findViewById(R.id.rv_search_list);
        LinearLayoutManager mZDLayoutManager = new LinearLayoutManager(this);
        mZDLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search_list.setLayoutManager(mZDLayoutManager);

        et_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    TaklingDataEventMessage msg = new TaklingDataEventMessage();
                    msg.setAction(TaklingDataEventMessage.ONSEARCH);
                    Map<String, String> map = new HashMap<>();
                    map.put("searchword", et_keyword.getText().toString());
                    map.put("recommend", false + "");
                    map.put("historySearch", false + "");
                    msg.setExtra(map);
                    EventBus.getDefault().post(msg);
                    jumpTosearch(et_keyword.getText().toString());
                }
                return false;
            }
        });

        hotAdapter = new SearchHotWordAdapter();
        fl_hot.setAdapter(hotAdapter);

        historyAdapter = new SearchHotWordAdapter();
        fl_history.setAdapter(historyAdapter);

        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        fl_hot.setOnTagClickListener(this);
        fl_history.setOnTagClickListener(this);

        tv_clear.setOnClickListener(this);
        iv_delete.setOnClickListener(this);

        iv_scan.setOnClickListener(this);

        et_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mPresenter.getSearchList(s.toString());
                }
            }
        });
        swipeLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (pageNo < totalNum) {
                    pageNo++;
                    mPresenter.traceProduct(pageNo);
                } else {
                    swipeLayout.setCanLoadMore(false);
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

    private void jumpTosearch(String keyword) {


        swipeLayout.setVisibility(View.VISIBLE);
        rv_search_list.setVisibility(View.GONE);
        if (!StringUtils.isEmpty(keyword)) {
            saveSearchData(keyword, 1);
            this.keyword = keyword;

            if (canLink(keyword)) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("keyword", keyword);
            JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bundle);
        } else {
            if (!StringUtils.isEmpty(OdyApplication.getValueByKey("searchword", ""))) {
                saveSearchData(OdyApplication.getValueByKey("searchword", ""), 1);
                this.keyword = OdyApplication.getValueByKey("searchword", "");
                if (canLink(keyword)) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("keyword", this.keyword);
                JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bundle);
            }
        }
    }

    private boolean canLink(String keyword) {
        if (mSearchDefaultAd != null) {
            if (TextUtils.isEmpty(keyword) || keyword.equals(mSearchDefaultAd.content)) {
                JumpUtils.toActivity(mSearchDefaultAd);
                return true;
            }
        }
        return false;
    }

    @Override
    public void doBusiness(Context mContext) {
        et_keyword.setHint(OdyApplication.getValueByKey("searchword", ""));
        mDao = OdyApplication.daoSession.getSearchRiCiEntityDao();
        mPresenter.getDefaultWord();
        mPresenter.getHotWord();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
            mPresenter.traceProduct(pageNo);
        }
    }

    @Override
    public void resume() {
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {//德升且登录时,搜索历史走接口
            mPresenter.getSearchHistory(10);
        } else {
            getNoLoginSearchHistory();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void showTag() {

    }

    @Override
    public void hideTag() {

    }

    @Override
    public void setSearchResultList(List<SearchKeywordListBean.Data> data) {
        if (data != null && data.size() > 0) {
            swipeLayout.setVisibility(View.GONE);
            rv_search_list.setVisibility(View.VISIBLE);
            searchAdapter = new SearchKeywordListAdapter(getContext(), data);
            searchAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<SearchKeywordListBean.Data>() {
                @Override
                public void onItemClick(View view, int position, SearchKeywordListBean.Data model) {
                    TaklingDataEventMessage msg = new TaklingDataEventMessage();
                    msg.setAction(TaklingDataEventMessage.ONSEARCH);
                    Map<String, String> map = new HashMap<>();
                    map.put("searchword", model.keyword + "");
                    map.put("recommend", true + "");
                    map.put("historySearch", false + "");
                    msg.setExtra(map);
                    EventBus.getDefault().post(msg);
                    jumpTosearch(model.keyword);
                }

                @Override
                public void onItemLongClick(View view, int position, SearchKeywordListBean.Data model) {

                }
            });
            rv_search_list.setAdapter(searchAdapter);
        } else {
            swipeLayout.setVisibility(View.VISIBLE);
            rv_search_list.setVisibility(View.GONE);
        }

    }

    @Override
    public void setHotWord(List<FuncBean.Data.AdSource> searchHotWordList) {
        hotList = searchHotWordList;
        showFailed(false, 1);
        if (null != searchHotWordList && searchHotWordList.size() > 0) {
            ll_hot.setVisibility(View.VISIBLE);
            hotAdapter.setDatas(searchHotWordList);
        } else {
            ll_hot.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSearchHistory(List<SearchHistoryBean.SearchHistoryData.SearchHistoryList> searchHistoryList) {
        showFailed(false, 1);
        if (searchHistoryList != null && searchHistoryList.size() > 0) {
            ll_history.setVisibility(View.VISIBLE);
            historyAdapter.clearAndAddAll(searchHistoryList);
        } else {
            ll_history.setVisibility(View.GONE);
        }
    }


    private SearchRiCiEntityDao mDao;
    private List<SearchRiCiEntity> mSearchList;

    @Override
    public void getNoLoginSearchHistory() {
        mSearchList = mDao.queryBuilder().orderDesc(SearchRiCiEntityDao.Properties._id).limit(10).build().list();
        historyAdapter.clearAndAddAll(mSearchList);
        if (mSearchList != null && mSearchList.size() > 0) {
            ll_history.setVisibility(View.VISIBLE);
            tv_clear.setVisibility(View.VISIBLE);
        } else {
            ll_history.setVisibility(View.GONE);
            tv_clear.setVisibility(View.GONE);

        }
    }

    @Override
    public void hideHotSearch(boolean flag) {

    }

    @Override
    public void setDefaultWord(List<FuncBean.Data.AdSource> searchword) {
        if (searchword != null && searchword.size() > 0) {
            mSearchDefaultAd = searchword.get(0);
            et_keyword.setHint(mSearchDefaultAd.content);
            OdyApplication.putValueByKey("searchword", mSearchDefaultAd.content);
        } else {
            et_keyword.setHint(OdyApplication.getValueByKey("searchword", ""));
        }
    }

    @Override
    public void clearn() {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void footHistory(List<HistoryBean.FootStepVO> historylist, int totalCount) {
//        if (searchList == null) {
//            searchList = historylist;
//            footAdapter = new SearchFootAdapter(getContext(), searchList);
//        } else {
//            searchList.addAll(historylist);
////            footAdapter.notifyDataSetChanged();
//        }
        if (pageNo == 1) {
            searchList.clear();
        }
        searchList.addAll(historylist);
        for (int i = 0; i < historylist.size(); i++) {
            if (StringUtils.isEmpty(hisMpIds)) {
                hisMpIds += historylist.get(i).mpId;
            } else {
                hisMpIds += ",";
                hisMpIds += historylist.get(i).mpId;
            }
        }
        mPresenter.getCurrentPrice(hisMpIds);
        totalNum = totalCount;
//        if (historylist != null && historylist.size() > 0) {
//            rl_foot.setVisibility(View.VISIBLE);
//            rv_history.setVisibility(View.VISIBLE);
//            footAdapter.addData(historylist);
//            ViewUtils.setListHeightAllClum(rv_history, 2);
//        } else {
//            if (pageNo == 1) {
//                rl_foot.setVisibility(View.GONE);
//                rv_history.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {
        if (bean.data != null && bean.data.plist != null && bean.data.plist.size() > 0) {
            for (int i = 0; i < searchList.size(); i++) {
                for (int j = 0; j < bean.data.plist.size(); j++) {
                    if (bean.data.plist.get(j).mpId.equals(searchList.get(i).mpId)) {
                        if (!StringUtils.isEmpty(bean.data.plist.get(j).promotionPrice)) {
                            searchList.get(i).price = bean.data.plist.get(j).promotionPrice;
                        } else {
                            searchList.get(i).price = bean.data.plist.get(j).price + "";
                        }
                    }
                }
            }
        }
        if (searchList != null && searchList.size() > 0) {
            rl_foot.setVisibility(View.VISIBLE);
            rv_history.setVisibility(View.VISIBLE);
            footAdapter.notifyDataSetChanged();
        } else {
            rl_foot.setVisibility(View.GONE);
            rv_history.setVisibility(View.GONE);
        }
    }

    // TODO: 2017/7/25  店铺相关
    @Override
    public void setStoreHotKey(StoreHotKeyBean storeHotKeyBean) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search) {
            jumpTosearch(et_keyword.getText().toString());
        } else if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_clear) {
            CustomDialog dialog = new CustomDialog(this, "确定清除历史吗？", 2);
            dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                @Override
                public void Confirm() {
                    clearHistory();
                }
            });
            dialog.show();
        } else if (v.getId() == R.id.iv_delete) {
            et_keyword.setText("");
        } else if (v.getId() == R.id.iv_scan) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(
                    //mTODO:meiyizhi
                    android.Manifest.permission.CAMERA//相机
            )
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                JumpUtils.ToActivity(JumpUtils.SWEEP);
                            } else {
                                ToastUtils.showShort("为了更好的使用体验，请开启相机使用权限!");
                            }
                        }
                    });
        }
    }

    private void clearHistory() {
        mDao.deleteAll();
        mSearchList = new ArrayList<>();
        historyAdapter.setDatas(mSearchList);
        ToastUtils.showShort("清空成功!");
        tv_clear.setVisibility(View.GONE);
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {//德升  清除搜索历史走接口
            mPresenter.cleanSearchHistory(null);
        }
        ll_history.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(FlowTagLayout parent, View view, int position) {
        if (parent.getId() == R.id.fl_hot) {
            keyword = hotAdapter.getItem(position).toString();

            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONSEARCH);
            Map<String, String> map = new HashMap<>();
            map.put("searchword", keyword + "");
            map.put("recommend", true + "");
            map.put("historySearch", false + "");
            msg.setExtra(map);
            EventBus.getDefault().post(msg);
            JumpUtils.toActivity(hotList.get(position));
            saveSearchData(keyword, 1);
        } else if (parent.getId() == R.id.fl_history) {
            keyword = historyAdapter.getItem(position).toString();

            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONSEARCH);
            Map<String, String> map = new HashMap<>();
            map.put("searchword", keyword + "");
            map.put("recommend", false + "");
            map.put("historySearch", true + "");
            msg.setExtra(map);
            EventBus.getDefault().post(msg);

            jumpTosearch(keyword);


        }
    }


    /**
     * 检查数据库中是否已经有该条记录
     */
    private SearchRiCiEntity hasData(String tempName) {
        //判断是否有下一个
        SearchRiCiEntity entity = mDao.queryBuilder().where(SearchRiCiEntityDao.Properties.RiCiName.eq(tempName)).build().unique();
        return entity;
    }


    /*保存搜索纪录**/
    public void saveSearchData(String strSearch, int type) {
        if (StringUtils.isEmpty(strSearch)) {
            return;
        }
        SearchRiCiEntity entity = hasData(strSearch);
        if (hasData(strSearch) != null) {//有同名的riCiName
            // delete from 表名 where name ='****'
            mDao.deleteByKey(entity.get_id());
        }
        SearchRiCiEntity newEntity = new SearchRiCiEntity();
        newEntity.setRiCiName(strSearch);
        newEntity.set_id(null);
        mDao.insert(newEntity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle extra = new Bundle();
        extra.putString(Constants.SP_ID, footAdapter.getItem(position).mpId);
        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);
    }

    @Override
    protected void loadAgain() {
        super.loadAgain();
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            showFailed(true, 1);
        } else {
            mPresenter.getDefaultWord();
            mPresenter.getHotWord();
            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {//德升且登录时,搜索历史走接口
                mPresenter.getSearchHistory(10);
            } else {
                getNoLoginSearchHistory();
            }
        }
    }
}
