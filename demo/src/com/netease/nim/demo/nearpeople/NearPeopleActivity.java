package com.netease.nim.demo.nearpeople;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.contact.model.GetUserIdModel;
import com.netease.nim.demo.main.activity.BaseActivity;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author SevenCheng
 */
@RuntimePermissions
public class NearPeopleActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    private RecyclerView   mRcv;
    private ProgressDialog mProgressDialog;
    private int count = 0;
    private NearPeopleAdapter mNearPeopleAdapter;
    private double            lat;
    private double            lng;
    private TextView address;
    private Location mLocation;
    private ImageView mRefresh;
    private EditText mSearchContent;
    private TextView query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {


        //保存用户位置信息
        initPermission();
        saveLocation();


    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, NearPeopleActivity.class);
        activity.startActivity(intent);

    }

    private void saveLocation() {
        NearPeopleActivityPermissionsDispatcher.doACacheNeedsPermissionWithPermissionCheck(this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void doACacheNeedsPermission() {
        try {
            //处理当用户允许该权限时需要处理的方法
            String locationPro = null;
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = manager.getProviders(true);
            /*if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationPro = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationPro = LocationManager.NETWORK_PROVIDER;
            } else {
                Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
                return;
            }*/

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            // 查找到服务信息
            Criteria criteria = new Criteria();
            // 高精度
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            // 低功耗
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            // 获取GPS信息
            String provider = manager.getBestProvider(criteria, true);
            // 通过GPS获取位置
            mLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("获取位置信息中...");
            mProgressDialog.show();
            while (mLocation == null) {
                //监视地理位置变化
                count++;
                if (count >= 3) {
                    Toast.makeText(this, "获取位置信失败,请检查权限或开启GPS和网络", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    finish();
                    return;
                }
                //
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locationListener);
            }

            if (mLocation != null) {
                //不为空,显示地理位置经纬度
                count = 0;
                showLocation(mLocation);
            } else {

            }
        } catch (Exception e) {
            Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void ACacheOnPermissionDenied() {
        ToastUtil.showLongToast(this, "请开启位置权限");
        finish();
    }

    private void initPermission() {
        ArrayList<String> perList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            perList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            perList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (perList.size() > 0) {
            String[] perArr = new String[perList.size()];
            perList.toArray(perArr);
            ActivityCompat.requestPermissions(this, perArr, 100);
        }
    }

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        String locationStr = "维度：" + location.getLatitude() + "\n"
                + "经度：" + location.getLongitude();
        Log.i("locationStr", "showLocation: " + locationStr);
        double latitude = location.getLatitude(); //纬度
        double longitude = location.getLongitude();//经度
        if (latitude == 0 && longitude == 0) {
            ToastUtil.showLongToast(this, "请开启所需权限");
            finish();
            return;
        } else {
            //调接口查询附近的人
            updateWithNewLocation(location);
            queryFriendNear(latitude, longitude);

        }
    }

    private void updateWithNewLocation(Location location) {
        String coordinate;
        String addressStr = "no address \n";
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            coordinate = "Latitude：" + lat + "\nLongitude：" + lng;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat,
                        lng, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append(" ");
                    }
                /*sb.append(address.getCountryName());
                Log.i("location", "address.getCountryName()==" + address.getCountryName());//国家名*/
                    sb.append(address.getLocality()).append(" ");
                    Log.i("location", "address.getLocality()==" + address.getLocality());//城市名
                    sb.append(address.getSubLocality());
                    Log.i("location", "address.getSubLocality()=2=" + address.getSubLocality());//---区名
                    addressStr = sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //如果用户没有允许app访问位置信息 则默认取上海松江经纬度的数据
            lat = 39.25631486;
            lng = 115.63478961;
            coordinate = "no coordinate!\n";
        }
        Log.i("location", "经纬度为===" + coordinate);
        Log.i("location", "地址为====" + addressStr);
        String[] split = addressStr.split(" ");
        if (split.length > 1) {
            address.setText((split[0].equals("null") ? "" : split[0]) + (split[1].equals("null") ? "" : split[1]));
        } else {
            address.setText(split[0].equals("null") ? "" : split[0]);
        }


    }

    private List<NearPeopleModel.DataBean> mPeopleModels = new ArrayList<>();

    private void queryFriendNear(double latitude, double longitude) {
        mPeopleModels.clear();
        Map map = new HashMap();
        map.put("token", Preferences.getUserMainToken());
        //        map.put("token", "6c420414bb466532750a86f08a865f0408");
        map.put("longitude", longitude);
        map.put("latitude", latitude);

        OKhttpHelper.send(this, new Gson().toJson(map), Common.AdapterPath + "users_by_location", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    System.out.println();
                    NearPeopleModel nearPeopleModel = new Gson().fromJson(s, new TypeToken<NearPeopleModel>() {
                    }.getType());

                    mPeopleModels.addAll(nearPeopleModel.getData());
                    mNearPeopleAdapter.notifyDataSetChanged();

                } catch (Exception e) {

                } finally {
                    mRefresh.setAnimation(null);
                }
            }

            @Override
            public void onSendFail(String err) {
                mRefresh.setAnimation(null);
            }
        });


    }


    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            mLocation = location;

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //        NearPeopleActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void initView() {

        setBarTitle("附近的人");

        address = (TextView) findViewById(R.id.tv_address);

        mRcv = (RecyclerView) findViewById(R.id.rcv);

        mNearPeopleAdapter = new NearPeopleAdapter(this, mPeopleModels);


        mRcv.setLayoutManager(new LinearLayoutManager(this));

        mRcv.setAdapter(mNearPeopleAdapter);


        mRefresh = (ImageView) findViewById(R.id.iv_refresh);


        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnim();
            }
        });


        mSearchContent = (EditText) findViewById(R.id.et_search);


        query = (TextView) findViewById(R.id.query);


        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mSearchContent.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NearPeopleActivity.this, R.string.not_allow_empty, Toast.LENGTH_SHORT).show();
                } else if (content.equals(DemoCache.getAccount())) {
                    Toast.makeText(NearPeopleActivity.this, R.string.add_friend_self_tip, Toast.LENGTH_SHORT).show();
                } else {
                    getuserId(content.toLowerCase(),"搜索添加");
                }
            }
        });


        ImageView cancel = (ImageView) findViewById(R.id.iv_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchContent.setText("");
            }
        });
    }

    private void getuserId(String s, final String source) {
        Map<Object, Object> body = new HashMap();
        body.put("mobile", s);

        OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "getuserId ", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                GetUserIdModel model = new Gson().fromJson(s, new TypeToken<GetUserIdModel>() {
                }.getType());

                if (model.getCode().equals("200")) {
                    String user_id = model.getUser_id();

                    query(user_id,source);
                }

            }

            @Override
            public void onSendFail(String err) {
                query("",source);
            }
        });
    }

    private void query(String user_id, final String way) {
        DialogMaker.showProgressDialog(this, null, false);
        String account = "";
        if (user_id.equals("")) {
            account = mSearchContent.getText().toString().toLowerCase();
        } else {
            account = user_id;
        }
        final String finalAccount = account;
        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo user) {
                DialogMaker.dismissProgressDialog();
                if (user == null) {
                    EasyAlertDialogHelper.showOneButtonDiolag(NearPeopleActivity.this, R.string.user_not_exsit,
                            R.string.user_tips, R.string.ok, false, null);
                } else {
                    UserProfileActivity.start(NearPeopleActivity.this, finalAccount,"附近的人添加");
                }
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                if (code == 408) {
                    Toast.makeText(NearPeopleActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NearPeopleActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(NearPeopleActivity.this, "on exception:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doAnim() {
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1500);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        //        mRefresh.setAnimation(rotate);

        mRefresh.startAnimation(rotate);


        doACacheNeedsPermission();
    }

    @Override
    public int getResourceId() {
        return R.layout.layout_near_people;
    }


}
