package com.ody.p2p.addressmanage.city;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.recycleviewutils.DecorationSpace;
import com.ody.p2p.recycleviewutils.FullyLinearLayoutManager;
import com.ody.p2p.retrofit.city.MultiCity;
import com.ody.p2p.utils.CreateViewUtil;
import com.ody.p2p.utils.LocationManager;
import com.ody.p2p.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CityListActivity extends BaseActivity implements CityListView{
    private RecyclerView cityRv;
    private RecyclerView characterRv;
    private CityAdapter adapter;
    private CharacterAdapter cAdapter;
    private View head;
    private CityListPresenterImpl presenter;
    private ImageView clear;
    private EditText keyWord;
    private TextView searchTxt;
    private TextView city;//当前定位城市
    private ImageView backImg;

    protected LocationManager locationManager;

    @Override
    public void initPresenter() {
        presenter = new CityListPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.addressmanager_activity_city_list;
    }

    @Override
    public void initView(View view) {

        cityRv = (RecyclerView) findViewById(R.id.cityRv);
        characterRv = (RecyclerView) findViewById(R.id.characterRv);
        tv_title = (TextView) findViewById(R.id.tv_name);
        clear = (ImageView) findViewById(R.id.clear);
        keyWord = (EditText) findViewById(R.id.keyWord);
        searchTxt = (TextView) findViewById(R.id.search_txt);
        backImg = (ImageView) findViewById(R.id.common_btn_back);
        tv_title.setText(R.string.location);


        head = LayoutInflater.from(this).inflate(R.layout.location_head, null);
        city = (TextView) head.findViewById(R.id.city);
        adapter = new CityAdapter();

        cAdapter = new CharacterAdapter();

        cityRv.setLayoutManager(new LinearLayoutManager(this));
        cityRv.addItemDecoration(new DecorationSpace(1));
        cityRv.setAdapter(adapter);
        adapter.addHeaderView(head);
        adapter.setItemClick(new CityAdapter.OnItemClick() {
            @Override
            public void click(MultiCity multiCity) {
                ToastUtils.showShort(multiCity.areaName);
            }
        });


        characterRv.setLayoutManager(new FullyLinearLayoutManager(this));
        characterRv.setAdapter(cAdapter);
        locationManager = new LocationManager(getContext());
        locationManager.setLocationListener(new LocationManager.LocationListener() {
            @Override
            public void onLocationChanged(LocationManager.MapLocation location) {
                if (location != null) {
                    city.setText(location.city);
                }
            }
        }).setOnceLocation(true)
        .startLocation(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        characterRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String character = (String) baseQuickAdapter.getData().get(i);
                List<MultiCity> list = adapter.getData();
                int index = 0;
                for (int i1 = 0; i1 < list.size(); i1++) {
                    if (list.get(i1).areaName.equals(character)) {
                        index = i1;
                        break;
                    }
                }
                ToastUtils.showShort(character);
                LinearLayoutManager manager = (LinearLayoutManager) cityRv.getLayoutManager();
                int count = manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition();
                if (index > manager.findFirstVisibleItemPosition()) {
                    cityRv.scrollToPosition((index + count) > list.size() ? list.size() : (index + count));
                } else {
                    cityRv.scrollToPosition(index + 1);
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord.setText("");
            }
        });

        RxTextView.textChanges(keyWord)
                .debounce(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (charSequence == null || charSequence.length() == 0) {
                            presenter.getCityList();
                        } else {
                            presenter.searchArea(charSequence.toString());
                        }
                    }
                });
        searchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchArea(keyWord.getText().toString());
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getCityList();
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
    public void fillData(List<MultiCity> list, boolean isShowIndex) {
        if (list == null || list.size() == 0) {
            adapter.setNewData(list);
            adapter.removeAllHeaderView();
            ImageView emptyView = new ImageView(this);
            emptyView.setBackgroundResource(R.drawable.global_search);
            adapter.setEmptyView(CreateViewUtil.createSearchEmptyView(this, (ViewGroup) cityRv.getParent()));
            characterRv.setVisibility(View.GONE);
        } else {
            adapter.removeAllHeaderView();
            adapter.addHeaderView(head);
            adapter.setNewData(list);
            characterRv.setVisibility(isShowIndex ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void fillCharacterData(List<String> list) {
        cAdapter.setNewData(list);
    }

}
