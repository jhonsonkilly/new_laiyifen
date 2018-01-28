package com.ody.p2p.productdetail.store.storecategory;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.produtdetail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2017/7/17.
 */

public class StoreCategoryActivity extends BaseActivity implements ShopCateView, View.OnClickListener {

    private RecyclerView rv_one;
    private RecyclerView rv_two;
    private String merchantId;
    private ShopCatePresenter presenter;
    private ShopCatParentAdapter parentAdapter;
    private ShopCateSubAdapter subAdapter;
    private ImageView backIcon;
    private List<ShopCateSubBean.DataEntity.ChildListBean> subData;


    @Override
    public void initPresenter() {
        presenter=new ShopCatePresenter(this);
        presenter.getCategoryId(merchantId+"");
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_shop_category;
    }

    @Override
    public void initView(View view) {
        merchantId=getIntent().getStringExtra("merchantId");
        rv_one= (RecyclerView) findViewById(R.id.rv_one);
        rv_two= (RecyclerView) findViewById(R.id.rv_two);
        backIcon= (ImageView) findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        parentAdapter=new ShopCatParentAdapter(this);
        parentAdapter.setItemClickListener(new ShopCatParentAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ShopCateSubBean.DataEntity.ChildListBean childListBean) {
                refreshData(childListBean);
            }
        });
        subAdapter=new ShopCateSubAdapter(this, merchantId);
        rv_one.setAdapter(parentAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_one.setLayoutManager(linearLayoutManager);
        rv_two.setAdapter(subAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        rv_two.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(subAdapter.getmData().get(position).itemType==1){
                    return 3;
                }else{
                    return 1;
                }

            }
        });
    }

    private void refreshData(ShopCateSubBean.DataEntity.ChildListBean childListBean){
        if(subData==null){
            subData=new ArrayList<>();
        }
        subData.clear();
        if(childListBean!=null&&childListBean.childList!=null&&childListBean.childList.size()>0){
            for(int i=0;i<childListBean.childList.size();i++){
                childListBean.childList.get(i).itemType=1;
                subData.add(childListBean.childList.get(i));
                if(childListBean.childList.get(i).childList!=null&&childListBean.childList.get(i).childList.size()>0){
                    for(int i1=0;i1<childListBean.childList.get(i).childList.size();i1++){
                        childListBean.childList.get(i).childList.get(i1).itemType=2;
                        subData.add(childListBean.childList.get(i).childList.get(i1));
                    }
                }
            }
        }
        subAdapter.setData(subData);
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
    public void cateIdresult(ShopCateParentBean shopCateParentBean) {
        if(shopCateParentBean.data!=null){
            presenter.getCategoryTree(merchantId+"",shopCateParentBean.data.id);
        }
    }

    @Override
    public void shopCateTree(ShopCateSubBean shopCateSubBean) {
        if(shopCateSubBean.data!=null&&shopCateSubBean.data.childList!=null&&shopCateSubBean.data.childList.size()>0){
            parentAdapter.setData(shopCateSubBean.data.childList);
            if(shopCateSubBean.data.childList.get(0)!=null){
                refreshData(shopCateSubBean.data.childList.get(0));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backIcon){
            finish();
        }
    }
}
