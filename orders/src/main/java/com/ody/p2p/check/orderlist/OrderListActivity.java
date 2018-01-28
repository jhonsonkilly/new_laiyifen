package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.MessageUtil;
import com.ody.p2p.views.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends BaseActivity implements View.OnClickListener, OrderTabAdapter.ItemClickListener {

    protected NoScrollViewPager viewPager;
    private OrderViewPagerAdapter adapter;
    public List<OrderTabBean> tabs;
    protected OrderTabAdapter tabAdapter;
    private RecyclerView rv_order_tab;
    protected List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private ImageView iv_back;
    protected TextView tv_title;
    protected ImageView iv_more;
    protected ImageView ibt_search;



    @Override
    public void initPresenter() {

    }

    public void setTabs(){
        OrderTabBean one=new OrderTabBean();
        one.orderStatus=0;
        one.orderStatusName=getString(R.string.all);
        tabs.add(one);

        OrderTabBean two=new OrderTabBean();
        two.orderStatus=1;
        two.orderStatusName=getString(R.string.unpay);
        tabs.add(two);

        OrderTabBean three=new OrderTabBean();
        three.orderStatus=2;
        three.orderStatusName=getString(R.string.undeliver_goods);
        tabs.add(three);

        OrderTabBean four=new OrderTabBean();
        four.orderStatus=3;
        four.orderStatusName=getString(R.string.unReceiving);
        tabs.add(four);

        OrderTabBean five=new OrderTabBean();
        five.orderStatus=8;
        five.orderStatusName=getString(R.string.completed);
        tabs.add(five);
    }


    private void init(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_order_tab.setLayoutManager(linearLayoutManager);
        setTabs();
        tabAdapter=new OrderTabAdapter(this);
        tabAdapter.setData(tabs);
        tabAdapter.setOnItemClickListener(this);
        rv_order_tab.setAdapter(tabAdapter);
        makeupFragmentList();
//	 *  订单状态(0 代表查询所有 1 待支付 2 待发货 3 待收货 10已取消 8已完成)
        viewPager.setOffscreenPageLimit(mFragmentList.size());
        adapter=new OrderViewPagerAdapter(getSupportFragmentManager(),mFragmentList);
        viewPager.setAdapter(adapter);
    }

    protected void makeupFragmentList(){
        if(tabs.size()>0){
            for(int i=0;i<tabs.size();i++){
                OrderFragment orderFragment=new OrderFragment();
                mFragmentList.add(OrderFragment.getInstance(tabs.get(i).orderStatus,orderFragment));
            }
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.iv_back){
            finish();
        }else if(view.getId()==R.id.iv_more){
            MessageUtil.setMegScan(this, iv_more);
        }else if(view.getId()==R.id.ibt_search){
            JumpUtils.ToActivity(JumpUtils.ORDERSEARCH);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }

    private void setCheckedTab(){
        int status=getIntent().getIntExtra("orderlist_Key",0);
        for(int i=0;i<tabs.size();i++){
            if(status==tabs.get(i).orderStatus){
                tabAdapter.setChecked(i);
                viewPager.setCurrentItem(i);
                rv_order_tab.smoothScrollToPosition(i);
            }
        }
    }

    @Override
    public void onItemClicklistener(int pos,OrderTabBean orderTabBean) {
        tabAdapter.setChecked(pos);
        viewPager.setCurrentItem(pos);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView(View view) {
        tabs=new ArrayList<>();
        rv_order_tab= (RecyclerView) findViewById(R.id.rv_order_tab);
        viewPager= (NoScrollViewPager) findViewById(R.id.viewPager);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        ibt_search= (ImageView) findViewById(R.id.ibt_search);
        tv_title= (TextView) findViewById(R.id.tv_title);
        iv_more= (ImageView) findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);
        ibt_search.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        init();
        setCheckedTab();
        iv_back.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabAdapter.setChecked(position);
                rv_order_tab.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

}
