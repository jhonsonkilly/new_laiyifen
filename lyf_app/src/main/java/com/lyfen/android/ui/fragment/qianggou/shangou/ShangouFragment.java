package com.lyfen.android.ui.fragment.qianggou.shangou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laiyifen.lyfframework.base.BaseFragment;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.recyclerview.RefreshRecyclerView;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerMode;
import com.laiyifen.lyfframework.recyclerview.manager.RecyclerViewManager;
import com.laiyifen.lyfframework.utils.PreferenceUtils;
import com.laiyifen.lyfframework.views.LoadingPage;
import com.lyfen.android.app.ActivityApi;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.PrefrenceKey;
import com.lyfen.android.entity.network.activity.ShangouEntity;
import com.lyfen.android.event.ShanGouEvent;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.adapter.ShangouAdapter;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;


/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class ShangouFragment extends BaseFragment {
    RefreshRecyclerView mRefreshRecyclerView;
    private int mPosition;
    private ActivityApi beanOfClass;

    LoadingPage mLoappager;
    private ShangouAdapter shangouAdapter;
    private View foot;
    private View foot2;
    ViewPager commonViewpager;

    public static Fragment newInstance(int i, ViewPager commonViewpager) {
        ShangouFragment likeFragment = new ShangouFragment();
        likeFragment.mPosition = i;
        likeFragment.commonViewpager = commonViewpager;
        return likeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beanOfClass = RestRetrofit.getBeanOfClass(ActivityApi.class);

        View inflate = View.inflate(getActivity(), R.layout.refresh_recyclerview, null);
        mRefreshRecyclerView = (RefreshRecyclerView) inflate.findViewById(R.id.id_recyclerview);

        foot = View.inflate(UIUtils.getContext(), R.layout.fragment_flash_foot, null);
        foot2 = View.inflate(UIUtils.getContext(), R.layout.fragment_flash_foot_tommor, null);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.layout_loading_pager, null);
        return inflate;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoappager = (LoadingPage) view.findViewById(R.id.common_loadpager);


    }

    @Subscribe
    public void onEventMainThread(ShanGouEvent event) {
        if (event.type == 2) {
            shangouAdapter.updateState(event.textView, event.isNotify);
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initData();
        foot.setOnClickListener(view -> {
            commonViewpager.setCurrentItem(2);

        });

    }

    private void initData() {
        Map<String, String> stringStringMap = NetWorkMap.defaultMap();
        stringStringMap.put("itemsPerPage", 10 + "");
        stringStringMap.put("currentPage", 1 + "");
        stringStringMap.put("categoryId", 0 + "");
        stringStringMap.put("nocache", System.currentTimeMillis() + "");
        stringStringMap.put("promotionType", 1 + "");
        stringStringMap.put("timeType", mPosition + "");
        stringStringMap.put("areaCode", PreferenceUtils.getString(PrefrenceKey.AREA_CODE, ""));
        stringStringMap.put("ut", LoginHelper.getUt());


        beanOfClass.getShangou(stringStringMap).subscribe(this::success, this::erro);
    }

    private void erro(Throwable throwable) {
        mLoappager.showPage(LoadingPage.LoadResult.ERROR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

    }

    private void success(ShangouEntity shangouEntity) {
        if (null == shangouAdapter) {
            shangouAdapter = new ShangouAdapter(mPosition);
            shangouAdapter.setData(shangouEntity.data);

            RecyclerViewManager.with(shangouAdapter, new LinearLayoutManager(getActivity()))
                    .setMode(RecyclerMode.TOP)
                    .addFooterView(mPosition == 0 ? foot : foot2)

                    .setOnPullDownListener(() -> initData()).into(mRefreshRecyclerView, getActivity());

            mLoappager.showPage(LoadingPage.LoadResult.SUCCEED, mRefreshRecyclerView);
        } else {
            shangouAdapter.setData(shangouEntity.data);
            mRefreshRecyclerView.onRefreshCompleted();
            shangouAdapter.notifyDataSetChanged();

        }


    }
}
