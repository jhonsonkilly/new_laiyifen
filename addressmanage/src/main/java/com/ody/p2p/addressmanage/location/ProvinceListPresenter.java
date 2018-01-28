package com.ody.p2p.addressmanage.location;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.retrofit.AreacodeBean;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.city.Address;
import com.ody.p2p.retrofit.city.AddressBean;
import com.ody.p2p.retrofit.city.Area;
import com.ody.p2p.retrofit.city.AreaBean;
import com.ody.p2p.retrofit.city.Group;
import com.ody.p2p.retrofit.city.LocationBean;
import com.ody.p2p.retrofit.city.Province;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/19.
 */

public class ProvinceListPresenter {
    private ProvinceListView mView;
    private ArrayList<MultiItemEntity> mL = new ArrayList<>();
    private List<String> characterItems = new ArrayList<String>();
    private List<MultiAddress> mList = new ArrayList<>();

    public ProvinceListPresenter(ProvinceListView view) {
        mView = view;
    }

    public void getPs() {
        getReceiveMessage();
        getCityList();
    }

    private void getReceiveMessage() {
        RetrofitFactory.getAddress()
                .filter(new Func1<AddressBean, Boolean>() {
                    @Override
                    public Boolean call(AddressBean addressBean) {
                        return addressBean != null && addressBean.data != null && addressBean.data.size() > 0;
                    }
                })
                .map(new Func1<AddressBean, List<MultiAddress>>() {
                    @Override
                    public List<MultiAddress> call(AddressBean addressBean) {
                        List<MultiAddress> list = new ArrayList<>();
                        if (addressBean != null && addressBean.data != null) {
                            if (addressBean.data.size() > 0) {
                                MultiAddress ma = new MultiAddress();
                                ma.itemType = MultiAddress.SELECTION;
                                ma.selectionName = "收货地址";
                                list.add(ma);
                            }
                            int count = 0;
                            for (Address address : addressBean.data) {
                                MultiAddress ma = new MultiAddress();
                                ma.itemType = MultiAddress.TEXT_ADDRESS;
                                ma.address = address;
                                list.add(ma);
                                if (++count > 2) {
                                    break;
                                }
                            }
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<MultiAddress>>(new SubscriberListener<List<MultiAddress>>() {
                    @Override
                    public void onNext(List<MultiAddress> list) {
                        mList.addAll(0, list);
                        mView.setProvinceData(mList);
                    }
                }));
    }

    private void getCityList() {
        RetrofitFactory.getGroupProvince("1")
                .map(new Func1<LocationBean, List<MultiAddress>>() {
                    @Override
                    public List<MultiAddress> call(LocationBean locationBean) {
                        characterItems.add("#");
                        for (Group group : locationBean.data) {
                            if (group.list != null) {
                                MultiAddress selection = new MultiAddress();
                                selection.itemType = MultiAddress.SELECTION;
                                selection.selectionName = group.key;
                                characterItems.add(group.key);
                                mList.add(selection);

                                for (Province province : group.list) {
                                    MultiAddress p = new MultiAddress();
                                    p.itemType = MultiAddress.TEXT_LOCATION;
                                    p.province = province;
                                    mList.add(p);
                                }
                            }
                        }
                        return mList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<MultiAddress>>(new SubscriberListener<List<MultiAddress>>() {
                    @Override
                    public void onNext(List<MultiAddress> list) {
//                        mList.addAll(list);
                        mView.setProvinceData(mList);
                        mView.fillCharacterData(characterItems);
                    }
                }));
    }

    public void getArea(String code) {
        mL.clear();
        RetrofitFactory.getAreaList(code)
                .map(new Func1<AreaBean, List<Area>>() {
                    @Override
                    public List<Area> call(AreaBean areaBean) {
                        return areaBean.data;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<Area>>(new SubscriberListener<List<Area>>() {
                    @Override
                    public void onNext(List<Area> list) {
                        for (Area area : list) {
                            CityLevel level0 = new CityLevel();
                            level0.city = area;
                            mL.add(level0);
                        }
                        mView.setSecondLevelData(mL);
                    }
                }));
    }

    public void getDistrict(final int position, String code) {
        RetrofitFactory.getAreaList(code)
                .map(new Func1<AreaBean, List<Area>>() {
                    @Override
                    public List<Area> call(AreaBean areaBean) {
                        return areaBean.data;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<Area>>(new SubscriberListener<List<Area>>() {
                    @Override
                    public void onNext(List<Area> list) {
                        CityLevel level0 = (CityLevel) mL.get(position);
                        for (Area area : list) {
                            level0.addSubItem(area);
                        }
                        mView.expand(position, mL);
                    }
                }));
    }

    public void convertAddress(String p, String c, String d) {
        RetrofitFactory.getArea(p, c, d)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AreacodeBean>(new SubscriberListener<AreacodeBean>() {
                    @Override
                    public void onNext(AreacodeBean o) {
                        OdyApplication.putString(Constants.AREA_CODE, o.getData().getFouCode() + "");
                        OdyApplication.putString(Constants.PROVINCE, o.getData().getTwoAddress());
                        OdyApplication.putString(Constants.CITY, o.getData().getThrAddress());
                        OdyApplication.putString(Constants.AREA_NAME, o.getData().getFouAddress());
                        OdyApplication.putString(Constants.AREA_CODE_ADDRESS, o.getData().getTwoAddress() + " " + o.getData().getThrAddress() + " " + o.getData().getFouAddress());
                        OdyApplication.putString(Constants.ADDRESS_ID, "");//商品详情收货地址的id
                    }

                    @Override
                    public void onCompleted() {
                        mView.finishActivity();
                        super.onCompleted();
                    }
                }));

    }
}
