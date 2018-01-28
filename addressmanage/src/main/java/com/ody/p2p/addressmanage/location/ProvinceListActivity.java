package com.ody.p2p.addressmanage.location;

import android.Manifest;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.city.CharacterAdapter;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.recycleviewutils.DecorationSpace;
import com.ody.p2p.retrofit.city.Area;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.SideBar;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.functions.Action1;

import static com.ody.p2p.addressmanage.R.string.location;

public class ProvinceListActivity extends BaseActivity implements ProvinceListView {
    private ProvinceAdapter adapter;
    private SecondLevelAdapter secondAdapter;
    private RecyclerView pRv;
    private RecyclerView areaRv;
    private CharacterAdapter cAdapter;
    private SideBar characterRv;
    private View head;
    private TextView city;//当前定位城市
    private ProvinceListPresenter presenter;
    private DrawerLayout drawer;
    private String province;
    private String cityName;
    private RelativeLayout rlBigBack;
    private String provinceLocation;
    private String cityLocation;
    private String districtLocation;
    private ImageView close;
    private TextView tv_car;

    protected LocationManager locationManager;

    @Override
    public void initPresenter() {
        presenter = new ProvinceListPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_provice_list;
    }

    @Override
    public void initView(final View view) {
        adapter = new ProvinceAdapter();
        pRv = (RecyclerView) findViewById(R.id.pRv);
        areaRv = (RecyclerView) findViewById(R.id.areaRv);
        tv_title = (TextView) findViewById(R.id.tv_name);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        close = (ImageView) findViewById(R.id.close);
        rlBigBack = (RelativeLayout) findViewById(R.id.rl_big_back);
        tv_title.setText(location);
        characterRv = (SideBar) findViewById(R.id.characterRv);
        head = LayoutInflater.from(this).inflate(R.layout.location_head, null, false);
        city = (TextView) head.findViewById(R.id.city);
        tv_car = (TextView) view.findViewById(R.id.tv_car);

        cAdapter = new CharacterAdapter();
        characterRv.setTextView(tv_car);
        characterRv.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                List<MultiAddress> list = adapter.getData();
                int index = 0;
                for (int i1 = 0; i1 < list.size(); i1++) {
                    if (list.get(i1).itemType == MultiAddress.SELECTION && list.get(i1).selectionName.equals(s)) {
                        index = i1;
                        break;
                    }
                }
                LinearLayoutManager manager = (LinearLayoutManager) pRv.getLayoutManager();
                int count = manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition();
                if (index > manager.findFirstVisibleItemPosition()) {
                    pRv.scrollToPosition((index + count) > list.size() ? list.size() : (index + count));
                } else {
                    pRv.scrollToPosition(index + 1);
                }

            }
        });

        areaRv.setLayoutManager(new LinearLayoutManager(this));
        areaRv.addItemDecoration(new DecorationSpace(1));
        secondAdapter = new SecondLevelAdapter();
        areaRv.setAdapter(secondAdapter);

        pRv.setLayoutManager(new LinearLayoutManager(this));
        pRv.addItemDecoration(new DecorationSpace(1));
        pRv.setAdapter(adapter);

        adapter.addHeaderView(head);
        locationManager = new LocationManager(getContext());
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted){
                            locationManager.setLocationListener(new LocationManager.LocationListener() {
                                @Override
                                public void onLocationChanged(LocationManager.MapLocation location) {
                                    if (location != null) {
                                        provinceLocation = location.province;
                                        cityLocation = location.city;
                                        districtLocation = location.district;
                                        city.setText(location.province + " " + location.city + " " + location.district);
                                    }
                                }
                            })
                                    .setOnceLocation(true)
                                    .startLocation(ProvinceListActivity.this);
                        } else {
                            ToastUtils.showShort("为了更好的使用体验，请开启定位权限!");
                        }
                    }
                });

    }

    @Override
    public void initListener() {
        super.initListener();

        pRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiAddress address = (MultiAddress) adapter.getItem(position);
                if (address.itemType == MultiAddress.TEXT_LOCATION) {
                    province = address.province.areaName;
                    presenter.getArea(address.province.areaCode);
                } else if (address.itemType == MultiAddress.TEXT_ADDRESS) {
                    OdyApplication.putString(Constants.AREA_CODE, address.address.regionCode);
                    OdyApplication.putString(Constants.AREA_CODE_ADDRESS, address.address.provinceName + "  " + address.address.cityName + "  " + address.address.regionName);
                    OdyApplication.putString(Constants.PROVINCE, address.address.provinceName);
                    OdyApplication.putString(Constants.CITY, address.address.cityName);
                    OdyApplication.putString(Constants.AREA_NAME, address.address.regionName);
                    OdyApplication.putString(Constants.ADDRESS_ID, "" + address.address.id);//商品详情收货地址的id
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.flag = EventMessage.CHANGE_AREA;
                    EventBus.getDefault().post(eventMessage);
                    finish();
                }
            }
        });

        areaRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case SecondLevelAdapter.TYPE_LEVEL_0:
                        CityLevel city = (CityLevel) adapter.getItem(position);
                        cityName = city.city.name;
                        ImageView arrowImg = (ImageView) view.findViewById(R.id.arrow);
                        if (city.isExpand) {
                            arrowImg.setImageResource(R.drawable.global_open);
                            secondAdapter.collapse(position);
                        } else {
                            arrowImg.setImageResource(R.drawable.global_stop);
                            if (city.getSubItems() != null && city.getSubItems().size() > 0) {
                                secondAdapter.expand(position);
                            } else {
                                presenter.getDistrict(position, city.city.code);
                            }
                        }
                        break;
                    case SecondLevelAdapter.TYPE_LEVEL_1:
                        Area area = (Area) adapter.getItem(position);
                        OdyApplication.putString(Constants.AREA_CODE, area.code);
                        OdyApplication.putString(Constants.AREA_CODE_ADDRESS, province + "  " + cityName + "  " + area.name);
                        OdyApplication.putString(Constants.PROVINCE, province);
                        OdyApplication.putString(Constants.CITY, cityName);
                        OdyApplication.putString(Constants.AREA_NAME, area.name);
                        OdyApplication.putString(Constants.ADDRESS_ID, "");//商品详情收货地址的id
                        finish();
                        EventMessage eventMessage = new EventMessage();
                        eventMessage.flag = EventMessage.CHANGE_AREA;
                        EventBus.getDefault().post(eventMessage);
                        break;
                }
            }
        });

        rlBigBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.convertAddress(provinceLocation, cityLocation, districtLocation);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getPs();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        if (locationManager != null){
            locationManager.stopLocation();
        }
    }

    @Override
    public void setProvinceData(List<MultiAddress> list) {
        adapter.setNewData(list);
    }

    @Override
    public void setSecondLevelData(List<MultiItemEntity> list) {
        secondAdapter.setNewData(list);
        drawer.openDrawer(GravityCompat.END);
    }

    @Override
    public void expand(int position, List<MultiItemEntity> list) {
        secondAdapter.setNewData(list);
        secondAdapter.expand(position);
    }

    @Override
    public void fillCharacterData(List<String> list) {
        characterRv.setCrList(list);
    }

    @Override
    public void finishActivity() {
        finish();
    }



}
