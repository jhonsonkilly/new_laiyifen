package com.ody.p2p.main.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.main.R;
import com.ody.p2p.main.ScanResultBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.search.searchresult.SearchResultActivity;
import com.ody.p2p.search.searchresult.SearchResultAdapter;
import com.ody.p2p.search.searchresult.popupwindow.SpaceItemDecoration;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2017/2/9.
 */
public class ScanProductListActivity extends BaseActivity implements View.OnClickListener{

    protected RelativeLayout rl_big_back;
    protected RecyclerView recycler_seachreuslt;

    protected String productJson;
    protected ScanResultBean bean;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scanproductlist;
    }

    @Override
    public void initView(View view) {
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        recycler_seachreuslt = (RecyclerView) view.findViewById(R.id.recycler_seachreuslt);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_seachreuslt.setLayoutManager(gridLayoutManager);
        recycler_seachreuslt.addItemDecoration(new SpaceItemDecoration(2));
        rl_big_back.setOnClickListener(this);
        productJson = getIntent().getStringExtra(Constants.PRODUCT_JSON);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (!StringUtils.isEmpty(productJson)){
            Gson gson = new Gson();
            try {
                bean = gson.fromJson(productJson,ScanResultBean.class);
            }catch (Exception e){
                return;
            }
        }
        if (bean != null && bean.data != null && bean.data.productList != null && bean.data.productList.size() > 0){
            SearchResultAdapter adapter = new SearchResultAdapter(getContext(),bean.data.productList, SearchResultActivity.TAB_TYPE);
            adapter.setToSpInfo(new SearchResultAdapter.ToSpInfo() {
                @Override
                public void toShop(String mpId) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.SP_ID,mpId);
                    JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL,bundle);
                }
            });
            adapter.setAddBuyCart(new SearchResultAdapter.AddBuyCart() {
                @Override
                public void addtoCart(String mpId, int position) {
                    addToCart(mpId);
                }
            });
            recycler_seachreuslt.setAdapter(adapter);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_big_back){
            finish();
        }
    }

    public void addToCart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params,getContext().getClass().toString(), new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    ToastUtils.showShort("添加成功");
                }
            }
        });
    }
}
