package com.netease.nim.demo.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.search.FlowLayout;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.contact.core.item.ContactItem;
import com.netease.nim.uikit.contact.core.item.ItemTypes;
import com.netease.nim.uikit.contact.core.item.MsgItem;
import com.netease.nim.uikit.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.contact.core.model.ContactGroupStrategy;
import com.netease.nim.uikit.contact.core.provider.ContactDataProvider;
import com.netease.nim.uikit.contact.core.query.IContactDataProvider;
import com.netease.nim.uikit.contact.core.viewholder.ContactHolder;
import com.netease.nim.uikit.contact.core.viewholder.LabelHolder;
import com.netease.nim.uikit.contact.core.viewholder.MsgHolder;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.search.model.MsgIndexRecord;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * 全局搜索页面
 * 支持通讯录搜索、消息全文检索
 * <p/>
 * Created by huangjun on 2015/4/13.
 */
public class GlobalSearchActivity extends UI implements OnItemClickListener {

    EditText  mEtSearch;
    TextView  mTvSearch;
    ImageView mIvCancel;
    ImageView mIvBack;
    private ContactDataAdapter adapter;

    private ListView lvContacts;

    private SearchView searchView;


    private List<String> seachStrs = new ArrayList<>();

    /************
     * 以下为流式标签相关
     ************/
    public static final String EXTRA_KEY_TYPE             = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD          = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private FlowLayout     mFlowLayout;
    private LayoutInflater mInflater;
    private String queryStr = "";
    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private LinearLayout      layout_notice;//历史搜索记录
    private View              noContent;
    private TextView          mTv_content;
    private TextView          mContent;
    private View              mEView;
    private View              mEmptyView;
    private View              no_content_history;

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GlobalSearchActivity.class);
        context.startActivity(intent);
    }

    private void initFlowView() {

        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        TextView delete = (TextView) findViewById(R.id.tv_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanHistory();
            }
        });
        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                if (!StringUtil.isEmpty(o + ""))
                    list.add((String) o);
            }
            seachStrs = list;
        }
        initData();
    }


    /**
     * 将数据放入流式布局
     */
    private void initData() {
        mFlowLayout.removeAllViews();
        removeDuplicate(seachStrs);
        layout_notice = (LinearLayout) findViewById(R.id.layout_notice);

        if (seachStrs.size() == 0) {
            layout_notice.setVisibility(View.GONE);

        } else {
            layout_notice.setVisibility(View.VISIBLE);
        }
        String history = "";
        for (int i = 0; i < seachStrs.size(); i++) {

            history = history + "," + seachStrs.get(i);
            View root = mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            TextView tv = (TextView) root.findViewById(R.id.tv_his);
            final ImageView delete = (ImageView) root.findViewById(R.id.delete);

            tv.setText(seachStrs.get(i));
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtSearch.setText(str);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                        mTvSearch.callOnClick();
                    }
                }
            });
            //长按
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    delete.setVisibility(View.VISIBLE);

                    return true;
                }
            });

            //删除
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seachStrs.remove(seachStrs.indexOf(str));
                    initData();
                }
            });
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(KEY_SEARCH_HISTORY_KEYWORD, history);
            editor.commit();
            mFlowLayout.addView(root);
        }
    }

    private void saveHistory() {
        String history = "";
        for (int i = 0; i < seachStrs.size(); i++) {
            history = history + "," + seachStrs.get(i);
        }
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_SEARCH_HISTORY_KEYWORD, history);
        editor.commit();
    }

    /*public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.global_search_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        getHandler().post(new Runnable() {
            @Override
            public void run() {
                MenuItemCompat.expandActionView(item);
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                finish();

                return false;
            }
        });

        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setBackgroundResource(R.drawable.actionbar_bg);

        searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                showKeyboard(false);
                if (StringUtil.isEmpty(query)) {
                    queryStr = "";
                    lvContacts.setVisibility(View.GONE);
                } else {
                    layout_notice.setVisibility(View.GONE);
                    lvContacts.setVisibility(View.VISIBLE);
                    queryStr = query;
                    seachStrs.add(queryStr);
                }
                adapter.query(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (StringUtil.isEmpty(query)) {
                    queryStr = "";
                    lvContacts.setVisibility(View.GONE);
                    initData();
                } else {
                    lvContacts.setVisibility(View.VISIBLE);
                    queryStr = query;
                }
                //                adapter.query(query);
                return true;
            }
        });

        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //            case R.id.search_btn:
            //                seachStrs.add(queryStr);
            //
            //                initData();
            //                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.global_search_result);


        ToolBarOptions options = new ToolBarOptions();
        //        setToolBar(R.id.toolbar, options);
        initView();

        lvContacts = (ListView) findViewById(R.id.searchResultList);
        lvContacts.setVisibility(View.GONE);
        SearchGroupStrategy searchGroupStrategy = new SearchGroupStrategy();
        IContactDataProvider dataProvider = new ContactDataProvider(ItemTypes.FRIEND, ItemTypes.TEAM, ItemTypes.MSG);

        adapter = new ContactDataAdapter(this, searchGroupStrategy, dataProvider);
        adapter.addViewHolder(ItemTypes.MSG, MsgHolder.class);
        adapter.addViewHolder(ItemTypes.LABEL, LabelHolder.class);
        adapter.addViewHolder(ItemTypes.FRIEND, ContactHolder.class);
        adapter.addViewHolder(ItemTypes.TEAM, ContactHolder.class);

        //listView没有数据展示的布局添加
        mEmptyView = LayoutInflater.from(GlobalSearchActivity.this).inflate(R.layout.no_content_history, null);
        mEmptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));//设置LayoutParams
        ((ViewGroup) lvContacts.getParent()).addView(mEmptyView);//添加到当前的View hierarchy
        mContent = (TextView) mEmptyView.findViewById(R.id.tv_content);


        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener(this);
        lvContacts.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                showKeyboard(false);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        findViewById(R.id.global_search_root).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //                    finish();
                    return true;
                }
                return false;
            }
        });

        initFlowView();
        Log.e("Search","-------true");
        SharedPreferences sharedPreferences = getSharedPreferences("Search", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("TEAM", true);
        editor.putBoolean("FRIEND", true);
        editor.commit();
    }

    private void initView() {
        //搜不到展示界面
        //        noContent = findViewById(R.id.no_content);
        //        mTv_content = (TextView) findViewById(R.id.tv_content);

        mEtSearch = (EditText) findViewById(R.id.et_search);

        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mIvCancel = (ImageView) findViewById(R.id.iv_cancel);

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        no_content_history = findViewById(R.id.no_content_history);


        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Search","-------false");
                SharedPreferences sharedPreferences = getSharedPreferences("Search", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("TEAM", false);
                editor.putBoolean("FRIEND", false);
                editor.commit();
                finish();

            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //                initData();
                no_content_history.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    return;
                }
                //                String query = s.toString();
                //                if (StringUtil.isEmpty(query)) {
                //                    queryStr = "";
                //                    lvContacts.setVisibility(View.GONE);
                //                    initData();
                //                } else {
                //                    lvContacts.setVisibility(View.VISIBLE);
                //                    queryStr = query;
                //                }
                //                adapter.query(query);

                if (s.toString().equals("")) {
                    initData();
                }
            }
        });

        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvContacts.setEmptyView(mEmptyView);

                String query = mEtSearch.getText().toString();
                showKeyboard(false);
                if (StringUtil.isEmpty(query)) {
                    queryStr = "";
                    lvContacts.setVisibility(View.GONE);
                    //                    noContent.setVisibility(View.VISIBLE);
                } else {
                    layout_notice.setVisibility(View.GONE);
                    lvContacts.setVisibility(View.VISIBLE);
                    queryStr = query;
                    seachStrs.add(queryStr);

                }

                if (StringUtil.isEmpty(query)) {
                    return;
                }
                //                List<NimUserInfo> allUsersOfMyFriend =
                //                        NimUserInfoCache.getInstance().getAllUsersOfMyFriend();

                //                adapter.query(query);
                //搜索好友手机号,可能会卡,后期优化
                /*for (NimUserInfo nimUserInfo : allUsersOfMyFriend) {
                    boolean contains = nimUserInfo.getMobile().contains(query);
                    if (contains) {
                        query = nimUserInfo.getAccount();
                    }
                }*/

                saveHistory();
                adapter.query(query);
                //                hasHistory(lvContacts.getCount() > 0);
                mContent.setText("\"" + mEtSearch.getText().toString() + "\"");
            }
        });


        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText("");
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (searchView != null) {
            //            searchView.clearFocus();
        }
    }

    private static class SearchGroupStrategy extends ContactGroupStrategy {
        public static final String GROUP_FRIEND = "FRIEND";
        public static final String GROUP_TEAM   = "TEAM";
        public static final String GROUP_MSG    = "MSG";

        SearchGroupStrategy() {
            add(ContactGroupStrategy.GROUP_NULL, 0, "");
            add(GROUP_FRIEND, 1, "通讯录");
            add(GROUP_TEAM, 2, "群组");
            add(GROUP_MSG, 3, "消息记录");
        }

        @Override
        public String belongs(AbsContactItem item) {
            switch (item.getItemType()) {
                case ItemTypes.FRIEND:
                    return GROUP_FRIEND;
                case ItemTypes.TEAM:
                    return GROUP_TEAM;
                case ItemTypes.MSG:
                    return GROUP_MSG;
                default:
                    return null;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AbsContactItem item = (AbsContactItem) adapter.getItem(position);
        switch (item.getItemType()) {
            case ItemTypes.TEAM: {
                SessionHelper.startTeamSession(this, ((ContactItem) item).getContact().getContactId());
                break;
            }

            case ItemTypes.FRIEND: {
                //联系人详情
                //                UserProfileActivity.start(GlobalSearchActivity.this, ((ContactItem) item).getContact().getContactId());
                SessionHelper.startP2PSession(this, ((ContactItem) item).getContact().getContactId(), false);
                break;
            }

            case ItemTypes.MSG: {
                MsgIndexRecord msgIndexRecord = ((MsgItem) item).getRecord();

                if (msgIndexRecord.getCount() > 1) {
                    GlobalSearchDetailActivity2.start(this, msgIndexRecord);
                } else {
                    SessionTypeEnum sessionType = ((MsgItem) item).getRecord().getSessionType();
                    switch (sessionType.name()) {
                        case "Team":
                            SessionHelper.startTeamSession(this, ((MsgItem) item).getContact().getContactId());
                            break;
                        case "P2P":
                            SessionHelper.startP2PSession(this, ((MsgItem) item).getContact().getContactId(), false);
                            break;
                    }


                }
                break;
            }

            default:
                break;
        }
    }

    //去重
    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    /**
     * 清除历史纪录
     */
    public void cleanHistory() {
        mPref = getSharedPreferences("input", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
        seachStrs.clear();
        initData();
        Toast.makeText(GlobalSearchActivity.this, "清除搜索历史成功", Toast.LENGTH_LONG).show();
    }

}
