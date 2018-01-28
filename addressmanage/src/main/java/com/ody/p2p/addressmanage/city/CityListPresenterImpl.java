package com.ody.p2p.addressmanage.city;

import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.city.CityBean;
import com.ody.p2p.retrofit.city.MultiCity;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/5.
 */

public class CityListPresenterImpl {
    private CityListView view;

    public CityListPresenterImpl(CityListView view) {
        this.view = view;
    }

    public void getCityList() {
        RetrofitFactory.getAreaList()
                .map(new Func1<List<CityBean>, Temp>() {
                    @Override
                    public Temp call(List<CityBean> data) {
                        Temp temp = new Temp();
                        List<String> characterItems = new ArrayList<String>();
                        List<MultiCity> items = new ArrayList<MultiCity>();
                        characterItems.add("#");
                        for (CityBean cb : data) {
                            MultiCity city = new MultiCity();
                            city.itemType = MultiCity.SELECTION;
                            city.areaName = cb.key;
                            characterItems.add(cb.key);
                            if (cb.list != null && cb.list.size() > 0) {
                                items.add(city);
                                items.addAll(cb.list);
                            }
                        }
                        temp.items = items;
                        temp.characterItems = characterItems;
                        return temp;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<Temp>(new SubscriberListener<Temp>() {
                    @Override
                    public void onNext(Temp temp) {
                        view.fillData(temp.items, true);
                        view.fillCharacterData(temp.characterItems);
                    }
                }));

    }

    public void searchArea(String keyword){
        Subscription subscribe = RetrofitFactory.getSearchAreaList(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<MultiCity>>(new SubscriberListener<List<MultiCity>>() {
                    @Override
                    public void onNext(List<MultiCity> list) {
                        view.fillData(list, false);
                    }
                }));
    }

    class Temp{
        public List<MultiCity> items;
        public List<String> characterItems;
    }
}

