package com.ody.p2p.main.order;

import android.Manifest;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by ${tang} on 2016/11/29.
 */

public class ChooseStoreActivity extends AppCompatActivity implements View.OnClickListener, LocationSource, AMapLocationListener, ChooseStoreView, AdapterView.OnItemClickListener {

    private EditText et_search;
    private ListView lv_store_list;
    private ListView lv_keyword;
    private ChooseStroreAdapter adapter;
    private TextView tv_submit;
    private ImageView iv_back;
    private OdySwipeRefreshLayout swipeLayout;
    private TextView tv_search;

    private MapView mapView;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener;
    private AMap aMap;

    private ChooseStrorePresenter presenter;
    private String merchantId;
    private String deliverycode;
    private String phone;

    private int totalCount = 0;
    private int pageNo = 1;
    private boolean isNeedMore = false;

    private InputMethodManager imm;
    private String lat;
    private String longt;

    private boolean isLoad = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_store);
        initView();
        setLoadListener();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap();
    }


    public void initView() {

        merchantId = getIntent().getStringExtra("merchantId");
        deliverycode = getIntent().getStringExtra("deliverycode");
        phone = getIntent().getStringExtra("phone");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_keyword = (ListView) findViewById(R.id.lv_keyword);
        lv_store_list = (ListView) findViewById(R.id.lv_store_list);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_search = (TextView) findViewById(R.id.tv_search);
        swipeLayout = (OdySwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setHeaderViewBackgroundColor(0x00ffffff);
        swipeLayout.setTargetScrollWithLayout(true);
        swipeLayout.setOdyDefaultView(true);
        swipeLayout.setCanRefresh(false);

        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        adapter = new ChooseStroreAdapter();
        lv_store_list.setAdapter(adapter);
        lv_store_list.setOnItemClickListener(this);

        imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        tv_submit.setOnClickListener(this);
        presenter = new ChooseStorePresenterImpl(this);
    }

    private void setLoadListener() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(et_search.getText().toString())) {
                        isNeedMore = true;
                        pageNo = 1;
                        presenter.getStoreList(pageNo, et_search.getText().toString(), lat, longt);
                        et_search.clearFocus();
                    }
                }
                return false;
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || isNeedMore) {
                    pageNo = 1;
                    presenter.getStoreList(pageNo, "", lat, longt);
                }
            }
        });
        swipeLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (adapter.getCount() < totalCount) {
                    pageNo++;
                    presenter.getStoreList(pageNo, et_search.getText().toString(), lat, longt);
                } else {
                    swipeLayout.setLoadMore(false);
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

    private void initMap() {
        aMap = mapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location2));// 设置小蓝点的图标
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        aMap.getUiSettings().setZoomControlsEnabled(false);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_submit) {
            String shopId = "";
            if (adapter.getCount() > 0) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (adapter.getItem(i).isSelected == 1) {
                        shopId = adapter.getItem(i).merchantId;
                    }
                }
            }
            if (TextUtils.isEmpty(shopId)) {
                ToastUtils.showToast("请选择门店");
                return;
            }
            presenter.saveShop(merchantId, deliverycode, shopId, phone);
        } else if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_search) {
            if (!TextUtils.isEmpty(et_search.getText().toString())) {
                isNeedMore = true;
                pageNo = 1;
                presenter.getStoreList(pageNo, et_search.getText().toString(), lat, longt);
                et_search.clearFocus();
            }
        }
    }

    @Override
    public void activate(final OnLocationChangedListener onLocationChangedListener) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                //mTODO:meiyizhi 定位需要的权限
                Manifest.permission.ACCESS_FINE_LOCATION,//位置
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            mListener = onLocationChangedListener;
                            if (mLocationClient == null) {
                                mLocationClient = new AMapLocationClient(ChooseStoreActivity.this);
                                mLocationOption = new AMapLocationClientOption();
                                // 设置定位监听
                                mLocationClient.setLocationListener(ChooseStoreActivity.this);
                                // 设置为高精度定位模式
                                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                                // 设置定位参数
                                mLocationClient.setLocationOption(mLocationOption);
                                // 只定位一次
                                mLocationOption.setOnceLocation(true);
                                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                                mLocationClient.startLocation();
                            }
                        } else {
                            ToastUtils.showShort("为了更好的使用体验，请开启定位权限!");
                        }
                    }
                });
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Log.e("position", "lat--" + amapLocation.getLatitude() + "--long" + amapLocation.getLongitude());
                if (isLoad) {
                    isLoad = false;
                    lat = amapLocation.getLatitude() + "";
                    longt = amapLocation.getLongitude() + "";
                    presenter.getStoreList(pageNo, "", lat, longt);
                }
            } else {
                ToastUtils.showToast("定位失败");
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void storeList(ChooseStoreBean chooseStoreBean) {
        if (chooseStoreBean.data != null && chooseStoreBean.data.dataList != null && chooseStoreBean.data.dataList.size() > 0) {
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
            }
            if (pageNo == 1) {
                aMap.clear();
                totalCount = chooseStoreBean.data.totalNum;
                adapter.setData(chooseStoreBean.data.dataList);
            } else {
                adapter.addData(chooseStoreBean.data.dataList);
            }
            for (int i = 0; i < chooseStoreBean.data.dataList.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.draggable(true);
                LatLng latLng = new LatLng(chooseStoreBean.data.dataList.get(i).latitude, chooseStoreBean.data.dataList.get(i).longitude);
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.location1)));
                aMap.addMarker(markerOptions);
            }
        }
    }

    @Override
    public void finishActivity() {
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter.getAllData() != null && adapter.getAllData().size() > 0) {
            for (int i = 0; i < adapter.getAllData().size(); i++) {
                if (i == position) {
                    adapter.getAllData().get(i).isSelected = 1;
                } else {
                    adapter.getAllData().get(i).isSelected = 0;
                }
            }
        }
        adapter.notifyDataSetChanged();
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(adapter.getAllData().get(position).latitude, adapter.getAllData().get(position).longitude)));
    }
}
