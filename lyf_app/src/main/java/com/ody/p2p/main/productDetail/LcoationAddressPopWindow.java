package com.ody.p2p.main.productDetail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by pzy on 2016/12/7.
 */
public class LcoationAddressPopWindow extends BasePopupWindow<AddressBean> {

    protected LocationManager locationManager;

    public LcoationAddressPopWindow(Context mContext, AddressBean data) {
        super(mContext, data);
        init(mContext, R.layout.layout_location_popwindow);
        initView();
    }

    TextView location_address;
    ImageView iv_dismiss, img_location_choose;
    RecyclerView recyle_pop_select_address;
    Button btn_newaddress;
    ChooseAddressAdapter adapter;

    public void initView() {
        dismissWindow(mMenuView.findViewById(R.id.dismisstop));

        location_address = (TextView) mMenuView.findViewById(R.id.location_address);
        recyle_pop_select_address = (RecyclerView) mMenuView.findViewById(R.id.recyle_pop_select_address);
        iv_dismiss = (ImageView) mMenuView.findViewById(R.id.iv_dismiss);
        btn_newaddress = (Button) mMenuView.findViewById(R.id.btn_newaddress);
        img_location_choose = (ImageView) mMenuView.findViewById(R.id.img_location_choose);

        if (null != mData && null != mData.getData() && mData.getData().size() > 0) {
            adapter = new ChooseAddressAdapter(mContext, mData.getData());
            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<AddressBean.Address>() {
                @Override
                public void onItemClick(View view, int position, AddressBean.Address model) {
                    isChooseLocatio = false;
//                    img_location_choose.setVisibility(View.INVISIBLE);
                    adapter.setChoosePostion(position);
                }

                @Override
                public void onItemLongClick(View view, int position, AddressBean.Address model) {

                }
            });
            recyle_pop_select_address.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
            recyle_pop_select_address.setAdapter(adapter);
            setPostion(OdyApplication.getString(Constants.ADDRESS_ID, ""));
        }
        iv_dismiss.setOnClickListener(this);
        btn_newaddress.setOnClickListener(this);
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            getLocation();
                        } else {
                            ToastUtils.showShort("为了更好的使用体验，请开启定位权限!");
                        }
                    }
                });
    }

    /**
     * 获取定位信息
     */
    public void getLocation() {
        locationManager = new LocationManager(mContext);
        locationManager
                .setOnceLocation(true)
                .setLocationListener(new LocationManager.LocationListener() {
                    @Override
                    public void onLocationChanged(LocationManager.MapLocation location) {
                        if (location == null) return;
                        mLocation = location;
                        location_address.setOnClickListener(LcoationAddressPopWindow.this);
                        location_address.setText(location.province + "  " + location.city + "  " + location.district);
                        if (isChooseLocatio) {
                            img_location_choose.setVisibility(View.VISIBLE);
                        }
                    }
                }).startLocation((Activity) mContext);
    }

    LocationManager.MapLocation mLocation;
    boolean isChooseLocatio = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_address:
                isChooseLocatio = !isChooseLocatio;
                if (isChooseLocatio) {
                    img_location_choose.setVisibility(View.VISIBLE);
                } else {
                    img_location_choose.setVisibility(View.INVISIBLE);
                }
                JumpUtils.ToActivity(JumpUtils.LOCATION);
                dismiss();
//                if (null!=adapter){
//                    adapter.setChoosePostion(-1);
//                }
                break;
            case R.id.iv_dismiss:
                dismiss();
                break;
            case R.id.btn_newaddress:
                dismiss();
                boolean isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);//登录状态
                if (isLogin) {
                    JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS);
                } else {
                    JumpUtils.ToActivity(JumpUtils.LOGIN);
                }
                break;
        }
    }

    public void setPostion(String addressId) {
        if (!StringUtils.isEmpty(addressId) && null != mData && null != mData.getData() && mData.getData().size() > 0) {
            for (int i = 0; i < mData.getData().size(); i++) {
                if (mData.getData().get(i).getId().equals(addressId)) {
                    isChooseLocatio = false;
                    adapter.setChoosePostion(i);
                    break;
                }
            }
        } else {
            isChooseLocatio = true;
        }
    }

    public void setmCallBack(chooseCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    chooseCallBack mCallBack;

    public interface chooseCallBack {
        void chooseAddress(AddressBean.Address address);
//        void chooseLocation(LocationManager.MapLocation location);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (isChooseLocatio && null != mLocation && null != mCallBack) {
//            mCallBack.chooseLocation(mLocation);定位地址直接跳转，不走这里的定位
        } else if (null != mCallBack && null != adapter) {
            mCallBack.chooseAddress(adapter.getChooseData());
        }
    }
}
