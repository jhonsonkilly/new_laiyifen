package com.ody.p2p.main.shopcart.shopcartview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ody.p2p.main.R;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.util.List;

/**
 * Created by pzy on 2016/12/9.
 */
public class ShopCartAdvertisementWindow extends BasePopupWindow<List<Ad>> {

    public ShopCartAdvertisementWindow(Context mContext, List<Ad> data) {
        super(mContext, data);
        init(mContext, R.layout.layout_shopcart_advertisement_window);
    }

    ImageView tv_dismiss;
    RecyclerView recyle_pop_select_address;
    AdvertisementAdapter adapter;

    @Override
    public void init(Context mContext, int layout) {
        super.init(mContext, layout);
        tv_dismiss=(ImageView)mMenuView.findViewById(R.id.tv_dismiss);
        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyle_pop_select_address=(RecyclerView)mMenuView.findViewById(R.id.recyle_pop_select_address);
        adapter=new AdvertisementAdapter(mContext,null);
        recyle_pop_select_address.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
        recyle_pop_select_address.setAdapter(adapter);

        adapter.setDatas(mData);
//        if (null!=mData&&null!=mData.notice_shopping&&mData.notice_shopping.size()>0){
//            adapter.setDatas(mData.notice_shopping);
//        }
    }

}