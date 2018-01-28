package com.lyfen.android.ui.fragment.qianggou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laiyifen.lyfframework.base.BaseFragment;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.laiyifen.lyfframework.views.LoadingPage;

import com.lyfen.android.app.ActivityApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.entity.network.activity.QiangGouFrgBean;
import com.lyfen.android.entity.network.activity.QiangGouTimeEntity;
import com.lyfen.android.event.QiangGouEvent;
import com.lyfen.android.ui.adapter.QiangGouAdapter;
import com.ody.p2p.main.R;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * Created by qiujie on 2017/7/21.
 */

public class QiangGouFragment extends BaseFragment {
    int position;
    QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity bean;
    //    private LoadingPage loadingPage;
    private ActivityApi beanOfClass;
    private RefreshRecyclerView recyclerView;
    private QiangGouAdapter qiangGouAdapter;
    private LoadingPage loadingPage;

    public static Fragment newInstance() {
        QiangGouFragment fragment = new QiangGouFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("i");
        bean = (QiangGouTimeEntity.DataEntity.TimeListEntity.TimesEntity) getArguments().getSerializable("bean");
        beanOfClass = RestRetrofit.getBeanOfClass(ActivityApi.class);
        EventBus.getDefault().register(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getContext());
        return loadingPage;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View inflate = View.inflate(getActivity(), R.layout.refresh_recyclerview, null);
        recyclerView = (RefreshRecyclerView) inflate.findViewById(R.id.id_recyclerview);
        initData();

    }





    private void initData() {
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("promotionId", bean.promotionId);
        stringStringMap.put("nocache", System.currentTimeMillis() + "");
        stringStringMap.put("sortType", 1 + "");
        stringStringMap.put("categoryId", 0 + "");
        stringStringMap.put("pageSize", Integer.MAX_VALUE + "");
        stringStringMap.put("pageNo", 1 + "");


        beanOfClass.lyfKillList(stringStringMap).subscribe(this::success, this::erro);


    }


    private void erro(Throwable throwable) {
        loadingPage.showPage(LoadingPage.LoadResult.ERROR).setOnClickListener(view -> initData());

    }

    private void success(QiangGouFrgBean qiangGouEntity) {
//        try {


        if (qiangGouAdapter == null) {
            qiangGouAdapter = new QiangGouAdapter(getActivity(), qiangGouEntity.data.listObj.get(0));
            //qiangGouAdapter.setData(qiangGouEntity.data.listObj.get(0).merchantProducts);
            RecyclerViewManager.with(qiangGouAdapter, new LinearLayoutManager(getActivity()))
                    .setMode(RecyclerMode.TOP)
                    .setOnPullDownListener(() -> initData())
                    .into(recyclerView, getActivity());
            loadingPage.showPage(LoadingPage.LoadResult.SUCCEED, recyclerView);
        } else {

            qiangGouAdapter.setData(qiangGouEntity.data.listObj.get(0));
            qiangGouAdapter.notifyDataSetChanged();
            recyclerView.onRefreshCompleted();
        }

//            TextView textView = new TextView(getActivity());
//            textView.setText("aaaaa");
//            textView.setText(Color.BLACK);


//        } catch (Exception e) {
//            LogUtils.i("aaa",e.getMessage());
//
//        }


    }

    @Subscribe
    public void onEventMainThread(final QiangGouEvent event) {

        qiangGouAdapter.updateState(event.textView, event.isNotify);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
