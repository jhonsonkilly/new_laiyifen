package com.ody.p2p.search.searchkey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.dao.SearchRiCiEntityDao;
import com.ody.p2p.entity.SearchRiCiEntity;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.StoreHotKeyBean;
import com.ody.p2p.search.R;
import com.ody.p2p.search.searchkey.flowTagLayout.FlowTagLayout;
import com.ody.p2p.search.searchkey.flowTagLayout.OnTagClickListener;
import com.ody.p2p.search.searchkey.utils.MyScrollview;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.basepopupwindow.SelectMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements SearchView, TextWatcher, View.OnClickListener, OnTagClickListener {
    //可以根据项目，修改搜索结果页面
    protected static String SEARCH_RESULT = JumpUtils.SEARCH_RESULT;
    public RecyclerView rv_search_list;
    public RecyclerView rv_search_history;
    public FlowTagLayout ftl_hot_search;
    public EditText et_search_keywords;
    public LinearLayout ll_tag;
    public TextView tv_clean_search_history;
    public TextView closebutton;//取消
    public ImageView iv_saoyisao;//扫一扫
    public MyScrollview sv_tag;
    public RelativeLayout rl_hot_search_title;
    public RelativeLayout rl_hot_search_content;
    public RelativeLayout rl_search_title;
    protected int flag;//给不同的项目使用,标记搜索历史和清除搜索历史走不走接口  德升要走接口,flag为1

    //德升新加
    public RelativeLayout search_keywords_bg;
    public ImageView iv_left_icon;
    public TextView tv_search;
    public ImageView iv_more;
    public ImageView iv_cha;

    public SearchHistoryAdapter shAdapter;//搜索历史走接口
    public SearchHistoryAdapter2 sh2Adapter;//搜索历史不走接口,流布局,暂不用
    public SearchHotWordAdapter hotAdapter;//热门搜索
    public SearchHistoryNewAdapter shnAdapter;//搜索历史,未走接口
    public SearchPresenter mPresenter;
    public SearchKeywordListAdapter sklAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(View view) {
        flag = 1;// 1 为所搜记录走接口 0则不走接口
        rv_search_list = (RecyclerView) view.findViewById(R.id.rv_search_list);
        rv_search_history = (RecyclerView) view.findViewById(R.id.rv_search_history);
        ftl_hot_search = (FlowTagLayout) view.findViewById(R.id.ftl_hot_search);
        et_search_keywords = (EditText) view.findViewById(R.id.et_search_keywords);
        ll_tag = (LinearLayout) view.findViewById(R.id.ll_tag);
        tv_clean_search_history = (TextView) view.findViewById(R.id.tv_clean_search_history);
        closebutton = (TextView) view.findViewById(R.id.closebutton);
        iv_saoyisao = (ImageView) view.findViewById(R.id.iv_saoyisao);
        sv_tag = (MyScrollview) view.findViewById(R.id.sv_tag);
        rl_hot_search_title = (RelativeLayout) view.findViewById(R.id.rl_hot_search_title);
        rl_hot_search_content = (RelativeLayout) view.findViewById(R.id.rl_hot_search_content);
        rl_search_title = (RelativeLayout) view.findViewById(R.id.rl_search_title);

        search_keywords_bg = (RelativeLayout) view.findViewById(R.id.search_keywords_bg);
        iv_left_icon = (ImageView) view.findViewById(R.id.iv_left_icon);
        iv_left_icon.setOnClickListener(this);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        iv_cha = (ImageView) view.findViewById(R.id.iv_cha);
        iv_cha.setOnClickListener(this);

        // RecyclerView 初始化  设置固定大小
        LinearLayoutManager mZDLayoutManager = new LinearLayoutManager(this);
        mZDLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search_list.setLayoutManager(mZDLayoutManager);

        et_search_keywords.addTextChangedListener(this);
        et_search_keywords.setOnKeyListener(onKeyListener);//监听软键盘的"搜索"键
        tv_clean_search_history.setOnClickListener(this);
        closebutton.setOnClickListener(this);
        iv_saoyisao.setOnClickListener(this);
        ftl_hot_search.setOnTagClickListener(this);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initPresenter() {
        mPresenter = new SearchPresenterImpl(this);
        mPresenter.getHotWord();
        // mPresenter.getDefaultWord();
    }

    @Override
    public void doBusiness(Context mContext) {

        mDao = OdyApplication.daoSession.getSearchRiCiEntityDao();

        menuStr =  new String[]{getString(R.string.home),getString(R.string.message)};

        et_search_keywords.setHint(OdyApplication.getValueByKey("searchword", ""));
        hotAdapter = new SearchHotWordAdapter();
        sh2Adapter = new SearchHistoryAdapter2();
        shnAdapter = new SearchHistoryNewAdapter();
        //        rv_search_history.setHasFixedSize(true);
//        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
//        manager.setSmoothScrollbarEnabled(true);
        rv_search_history.setLayoutManager(new LinearLayoutManager(getContext()));

        ftl_hot_search.setAdapter(hotAdapter);
    }

    @Override
    public void showTag() {
//        sv_tag.setVisibility(View.VISIBLE);
        ll_tag.setVisibility(View.VISIBLE);
        rv_search_list.setVisibility(View.GONE);
        //德升新加
        iv_cha.setVisibility(View.GONE);
    }

    @Override
    public void hideTag() {
        sv_tag.setVisibility(View.GONE);
        rv_search_list.setVisibility(View.VISIBLE);
    }

    //展示和搜索关键字相关的条目
    @Override
    public void setSearchResultList(final List<SearchKeywordListBean.Data> data) {
        sklAdapter = new SearchKeywordListAdapter(this, data);
        rv_search_list.setAdapter(sklAdapter);
        if (data.size() > 0) {
            hideTag();
        } else {
            sv_tag.setVisibility(View.VISIBLE);
            ll_tag.setVisibility(View.VISIBLE);
            rv_search_list.setVisibility(View.GONE);
        }
        sklAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<SearchKeywordListBean.Data>() {
            @Override
            public void onItemClick(View view, int position, SearchKeywordListBean.Data model) {
                saveSearchData(model.keyword, 1);
                Bundle bundle = new Bundle();
                bundle.putString("keyword", model.keyword);
                JumpUtils.ToActivity(SEARCH_RESULT, bundle);
//                et_search_keywords.setText("");
            }

            @Override
            public void onItemLongClick(View view, int position, SearchKeywordListBean.Data model) {

            }
        });
        //给recycleView里的流布局设置数据
//        sklAdapter.setFlowAdapterDataListener(new SearchKeywordListAdapter.FlowAdapterDataListener() {
//            @Override
//            public void setAdapter(FlowTagLayout flowTagLayout, List<SearchKeywordListBean.WareList.Tag> tag) {
//                SearchListTagAdapter sltAdapter = new SearchListTagAdapter();
//                flowTagLayout.setAdapter(sltAdapter);
//                sltAdapter.setDatas(tag);
//            }
//        });

        //recycleView里的流布局里的item的点击事件
//        sklAdapter.setShowToastListenter(new SearchKeywordListAdapter.ShowToastListenter() {
//            @Override
//            public void setToast(int position, int position2) {
////                ToastUtils.showShort(wareList.get(position).tag.get(position2).tagshowquery);
//                et_search_keywords.setText("");
//                saveSearchData(wareList.get(position).tag.get(position2).tagsearchquery, 1);
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://searchResult");
//                activityRoute.withParams("keyword",wareList.get(position).tag.get(position2).tagsearchquery).open();
//            }
//        });
    }

    @Override
    public void setHotWord(List<FuncBean.Data.AdSource> searchHotWordList) {
        if (null != searchHotWordList && searchHotWordList.size() > 0) {
            rl_hot_search_title.setVisibility(View.VISIBLE);
        } else {
            rl_hot_search_title.setVisibility(View.GONE);
        }
        hotAdapter.setDatas(searchHotWordList);
    }

    /**
     * 搜索历史走接口时用
     *
     * @param searchHistoryList
     */
    @Override
    public void setSearchHistory(List<SearchHistoryBean.SearchHistoryData.SearchHistoryList> searchHistoryList) {
        if (searchHistoryList.size() > 0) {
            tv_clean_search_history.setVisibility(View.VISIBLE);
            rl_search_title.setVisibility(View.VISIBLE);
        } else {
            tv_clean_search_history.setVisibility(View.GONE);
            rl_search_title.setVisibility(View.GONE);
        }
        if (null == shAdapter) {
            shAdapter = new SearchHistoryAdapter(getContext(), searchHistoryList);
            rv_search_history.setAdapter(shAdapter);
        } else {
            shAdapter.setDatas(searchHistoryList);
        }
        shAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String keyword) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                JumpUtils.ToActivity(SEARCH_RESULT, bundle);
//                et_search_keywords.setText("");
            }
        });
    }

    @Override
    public void clearn() {
        shAdapter.setDatas(null);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void footHistory(List<HistoryBean.FootStepVO> historylist,int totalCount) {

    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {

    }

    // TODO: 2017/7/25  店铺相关
    @Override
    public void setStoreHotKey(StoreHotKeyBean storeHotKeyBean) {

    }

    private List<SearchRiCiEntity> mSearchList;
    private SearchRiCiEntityDao mDao;

    @Override
    public void getNoLoginSearchHistory() {

        mSearchList = mDao.queryBuilder().orderDesc(SearchRiCiEntityDao.Properties._id).limit(5).build().list();

        if (mSearchList != null && mSearchList.size() > 0) {
            tv_clean_search_history.setVisibility(View.VISIBLE);
            rl_search_title.setVisibility(View.VISIBLE);
        } else {
            tv_clean_search_history.setVisibility(View.GONE);
            rl_search_title.setVisibility(View.GONE);
        }
        rv_search_history.setAdapter(shnAdapter);
        shnAdapter.setDatas(mSearchList);

        shnAdapter.setOnItemClickListener(new SearchHistoryNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tag = shnAdapter.getItem(position).getRiCiName();//上面将keyword和type用逗号拼接在一起了,所以土司会有逗号和数字1
                String realKeyword = "";
                try {
                    realKeyword = tag.substring(0, tag.lastIndexOf(","));
                    saveSearchData(realKeyword, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putString("keyword", realKeyword);
                JumpUtils.ToActivity(SEARCH_RESULT, bundle);
            }
        });
    }

    @Override
    public void hideHotSearch(boolean flag) {
        if (flag) {
            rl_hot_search_title.setVisibility(View.GONE);
            rl_hot_search_content.setVisibility(View.GONE);
        } else {
            rl_hot_search_title.setVisibility(View.VISIBLE);
            rl_hot_search_content.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDefaultWord(List<FuncBean.Data.AdSource> searchword) {
//        if (searchword != null && searchword.size() > 0) {
//            et_search_keywords.setHint(searchword.get(0).title);
//        } else {
//            et_search_keywords.setHint("请输入关键字");
//        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && !s.equals("") && !StringUtils.isEmpty(s.toString())) {
            mPresenter.getSearchList(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (et_search_keywords == null) {
            showTag();
            return;
        }
        if (et_search_keywords.getText() == null) {
            showTag();
            return;
        }
        if (StringUtils.isEmpty(et_search_keywords.getText().toString())) {
            showTag();
            return;
        } else {//德升新加
            iv_cha.setVisibility(View.VISIBLE);
        }
    }

    //按下软键盘里的"搜索"键时,默认让其走一次接口
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            //ACTION_DOWN这个一定得加,不然抬起和放下会走两次,导致搜索结果页回退到搜索页时,需要关闭两次
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN
                    && et_search_keywords != null && et_search_keywords.getText() != null
                    && !StringUtils.isEmpty(et_search_keywords.getText().toString())) {
                mPresenter.getSearchList(et_search_keywords.getText().toString());
                saveSearchData(et_search_keywords.getText().toString(), 1);//保存搜索记录
                String keyword = et_search_keywords.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                JumpUtils.ToActivity(SEARCH_RESULT, bundle);
//                et_search_keywords.setText("");
            }
            return false;
        }
    };

    @Override
    public void resume() {
        //走接口拿的搜索记录
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && flag == 1) {//德升且登录时,搜索历史走接口
            mPresenter.getSearchHistory(10);
        } else {
            //本地存的搜索记录
            getNoLoginSearchHistory();
        }
        if (StringUtils.isEmpty(et_search_keywords.getText().toString())) {
            showTag();
        }

        //当搜索历史很多的时候,如果不加这个,从其他activity返回的时候,界面不是显示在开头,而是显示在搜索历史的开头,但是如果锁屏再开屏后,会强制显示在activity的最上面
        //所以这句要在合适的条件下放在这个位置
        sv_tag.scrollTo(0, 0);
    }

    @Override
    public void destroy() {

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
        newEntity.setRiCiName(strSearch + "," + type);
        newEntity.set_id(null);
        mDao.insert(newEntity);
    }


    /**
     * 检查数据库中是否已经有该条记录
     */
    private SearchRiCiEntity hasData(String tempName) {
        //判断是否有下一个
        SearchRiCiEntity entity = mDao.queryBuilder().where(SearchRiCiEntityDao.Properties.RiCiName.eq(tempName + ",1")).build().unique();
        return entity;
    }



    private AlertPopupWindow alertPopupWindow;

    private String[] menuStr ;
//    private int[] menuRes = {R.drawable.ic_homepage,R.drawable.ic_message};


    @Override
    public void onClick(View v) {
        if (v.equals(tv_clean_search_history)) {
            hideSoftInput(et_search_keywords);
            alertPopupWindow = new AlertPopupWindow(SearchActivity.this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDao.deleteAll();
                    mSearchList = new ArrayList<>();
                    if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && flag == 1) {//德升  清除搜索历史走接口
                        mPresenter.cleanSearchHistory(null);
                    } else {
                        shnAdapter.setDatas(mSearchList);
                        rv_search_history.setAdapter(shnAdapter);
                        ToastUtils.showShort("清空成功!");
                    }
                    rl_search_title.setVisibility(View.GONE);
                    alertPopupWindow.dismiss();
                    tv_clean_search_history.setVisibility(View.GONE);
                }
            });
            alertPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        if (v.equals(closebutton)) {
            finish();
        }
        if (v.equals(iv_saoyisao)) {//扫一扫

        }
        if (v.equals(iv_left_icon)) {//搜索框左侧向左箭头
            hideSoftInput(et_search_keywords);
            finish();
        }
        if (v.equals(tv_search)) {//搜索框右侧"搜索"
            if (et_search_keywords != null && et_search_keywords.getText() != null
                    && !StringUtils.isEmpty(et_search_keywords.getText().toString())) {
                mPresenter.getSearchList(et_search_keywords.getText().toString());
                saveSearchData(et_search_keywords.getText().toString(), 1);//保存搜索记录
                String keyword = et_search_keywords.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                JumpUtils.ToActivity(SEARCH_RESULT, bundle);
//                et_search_keywords.setText("");
            }
        }
        if (v.equals(iv_more)) {//搜索框右侧三个点
            final List<MenuPopBean> popList = new ArrayList<>();
            for (int i = 0; i < menuStr.length; i++) {
                MenuPopBean bean = new MenuPopBean();
                bean.content = menuStr[i];
//                bean.picRes = menuRes[i];
                popList.add(bean);
            }
            SelectMenu menu = new SelectMenu(this, popList);
            menu.setClickSelectListener(new SelectMenu.clickSelectMenuListener() {
                @Override
                public void click(int position) {
                    if (position == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 0);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    } else if (position == 1) {
                        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                            JumpUtils.ToActivity(JumpUtils.MESSAGE);
                        } else {
                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                        }
                    }

                }
            });
            menu.showAsDropDown(iv_more, PxUtils.dipTopx(-55), 0);
        }
        if (v.equals(iv_cha)) {//搜索框内的叉图标
            et_search_keywords.setText("");
        }
    }

    //点击两个流布局里的条目
    String tag = "";

    @Override
    public void onItemClick(FlowTagLayout parent, View view, int position) {
        if (parent.getId() == R.id.ftl_hot_search) {
            tag = hotAdapter.getItem(position).toString();
            if (StringUtils.isEmpty(tag)) {
                ToastUtils.showShort(getString(R.string.search_key_cannot_null));
                return;
            }
            saveSearchData(tag, 1);
            Bundle bundle = new Bundle();
            bundle.putString("keyword", tag);
            JumpUtils.ToActivity(SEARCH_RESULT, bundle);
        }
    }

    public void toActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    //隐藏edittext弹出的软键盘
    public void hideSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
