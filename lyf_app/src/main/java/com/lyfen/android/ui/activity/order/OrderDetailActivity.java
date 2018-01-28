package com.lyfen.android.ui.activity.order;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.manager.FullyLinearLayoutManager;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.laiyifen.lyfframework.utils.PreferenceUtils;
import com.laiyifen.lyfframework.utils.SystemUtils;
import com.laiyifen.lyfframework.views.LoadingPage;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.app.PrefrenceKey;
import com.lyfen.android.app.ProductApi;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.entity.network.product.ProductRecommendEntity;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.adapter.OrderDetailAdapter;
import com.lyfen.android.ui.adapter.ProductRecommendHolder;
import com.lyfen.android.ui.viewholder.order.OrderAddressHolder;
import com.lyfen.android.ui.viewholder.order.OrderInfoHolder;
import com.lyfen.android.ui.viewholder.order.OrderPeisongHolder;
import com.lyfen.android.ui.viewholder.order.OrderShangjiaHolder;
import com.ody.p2p.main.R;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;

/**
 * Created by mac on 2017/12/13.
 */

public class OrderDetailActivity extends ActionBarActivity {


    OrderApi beanOfClass;
    @Bind(R.id.common_loadpager)
    LoadingPage commonLoadpager;
    private RefreshRecyclerView mRefreshRecyclerView;
    private FrameLayout frameLayout;
    private FrameLayout peisongInfo;
    private FrameLayout merchant;
    private FrameLayout info;
    private FrameLayout address;
    private String id;
    String mipId;
    private View view;
    private View rootView;

    private FrameLayout fl_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_loading_pager);
        ButterKnife.bind(this);

        rootView = View.inflate(this, R.layout.order_layout_recycle, null);
        mRefreshRecyclerView = (RefreshRecyclerView) rootView.findViewById(R.id.id_recyclerview);
        fl_layout=(FrameLayout)rootView.findViewById(R.id.fl_layout);
        beanOfClass = RestRetrofit.getBeanOfClass(OrderApi.class);

        id = getIntent().getStringExtra("OrderId");

        mipId = getIntent().getStringExtra("mipId");


        frameLayout = new FrameLayout(this);
        peisongInfo = new FrameLayout(this);
        merchant = new FrameLayout(this);
        info = new FrameLayout(this);
        address = new FrameLayout(this);

        address = new FrameLayout(this);

        initData(id);
    }

    private void initData(String id) {

        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("companyId", "30");
        stringStringMap.put("orderCode", id);
        stringStringMap.put("ut", LoginHelper.getUt());
        stringStringMap.put("v", "2.1");
        RestRetrofit.getBeanOfClass(OrderApi.class).getorderSearchList(stringStringMap).compose(bindToLifecycle());
        beanOfClass.getdetailList(stringStringMap).subscribe(this::success, this::erro);
        if (TextUtils.isEmpty(mipId)) {
            mipId = "1008020801002090";
        }
        initRecommendProductList(mipId);


    }

    private void success(OrderDetailEntity orderDetailEntity) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Observable.from(orderDetailEntity.data.childOrderList).flatMap(ChildOrderListBean ->
                    Observable.from(ChildOrderListBean.packageList).flatMap(PackageListBean ->
                            Observable.from(PackageListBean.productList).flatMap(ProductListBean ->
                                    Observable.just(ProductListBean.mpId)))).subscribe(s -> {
                stringBuffer.append(",");
                stringBuffer.append(s);
            });

        } catch (Exception e) {

        }
        initAddress(orderDetailEntity.data);
        initSmerchant(orderDetailEntity.data);
        initPeisong(orderDetailEntity.data);
        initInfo(orderDetailEntity.data);

        initState(orderDetailEntity.data);


        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter();
        orderDetailAdapter.setData(orderDetailEntity.data.childOrderList.get(0).packageList);

        RecyclerViewManager.with(orderDetailAdapter, new FullyLinearLayoutManager(this))
                .setMode(RecyclerMode.NONE)
                .addHeaderView(address)
                .addHeaderView(merchant)
                .addFooterView(peisongInfo)
                .addFooterView(info)
                .addFooterView(frameLayout)
                .into(mRefreshRecyclerView, this);


        commonLoadpager.showPage(LoadingPage.LoadResult.SUCCEED, rootView);
        orderDetailAdapter.notifyDataSetChanged();

    }

    private void initAddress(OrderDetailEntity.DataBean data) {
        OrderAddressHolder shangjiaHolder = new OrderAddressHolder();
        shangjiaHolder.setData(data);

        shangjiaHolder.refreshView();
        address.removeAllViews();
        address.addView(shangjiaHolder.getRootView());
    }

    private void initState(OrderDetailEntity.DataBean data){
        OrderStateHolder orderStateHolder = new OrderStateHolder(this);

        orderStateHolder.setData(data);


        orderStateHolder.refreshView();
        fl_layout.removeAllViews();
        fl_layout.addView(orderStateHolder.getRootView());
    }

    private void initInfo(OrderDetailEntity.DataBean data) {

        OrderInfoHolder shangjiaHolder = new OrderInfoHolder();
        shangjiaHolder.setRootView(rootView,this);
        shangjiaHolder.setData(data);

        shangjiaHolder.refreshView();
        info.removeAllViews();
        info.addView(shangjiaHolder.getRootView());
    }

    private void initSmerchant(OrderDetailEntity.DataBean childOrderListBean) {
        OrderShangjiaHolder shangjiaHolder = new OrderShangjiaHolder();
        shangjiaHolder.setData(childOrderListBean);

        shangjiaHolder.refreshView();
        merchant.removeAllViews();
        merchant.addView(shangjiaHolder.getRootView());
    }

    private void initPeisong(OrderDetailEntity.DataBean childOrderListBean) {
        OrderPeisongHolder orderPeisongHolder = new OrderPeisongHolder();
        orderPeisongHolder.setData(childOrderListBean);

        orderPeisongHolder.refreshView();
        peisongInfo.removeAllViews();
        peisongInfo.addView(orderPeisongHolder.getRootView());

    }

    private void erro(Throwable throwable) {
        commonLoadpager.showPage(LoadingPage.LoadResult.ERROR).setOnClickListener(view -> initData(id));
    }

    private void initRecommendProductList(String mips) {

        RestRetrofit.getBeanOfClass(ProductApi.class).recommendProductList(
                mips,
                PreferenceUtils.getString(PrefrenceKey.AREA_CODE, ""),
                SystemUtils.getUUid(),
                "1").subscribe(new Observer<ProductRecommendEntity>() {


            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductRecommendEntity productRecommendEntity) {
                try {
                    ProductRecommendHolder mProductRecommendHolder = new ProductRecommendHolder();
                    mProductRecommendHolder.setData(productRecommendEntity.data.dataList);
                    mProductRecommendHolder.setShowBottom(false);

                    frameLayout.addView(mProductRecommendHolder.getRootView());


                } catch (Exception e) {

                }


            }
        });

    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
