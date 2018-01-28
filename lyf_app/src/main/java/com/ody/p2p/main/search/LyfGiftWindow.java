package com.ody.p2p.main.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.search.searchresult.CartExtBean;
import com.ody.p2p.search.searchresult.PromotionDetailAdapter;
import com.ody.p2p.search.searchresult.PromotionDetailBean;
import com.ody.p2p.shopcart.ShopCartBean;
import com.ody.p2p.shopcartview.ChangeGifAdapter;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;

import java.util.List;

/**
 * Created by lxs on 2016/12/26.
 */
public class LyfGiftWindow extends BasePopupWindow<List<PromotionDetailBean.PromotionGiftDetailVO>> implements View.OnClickListener{

    RecyclerView recyclerview_changegif_list;
    public Button btn_complete_changegif_window;
    ImageView img_close;
    public PromotionDetailAdapter adapter;

    public LyfGiftWindow(Context mContext, List<PromotionDetailBean.PromotionGiftDetailVO> data) {
        super(mContext, data);
        init(mContext, com.ody.p2p.shopcart.R.layout.layout_changegif_window);
        view();
    }

    private void view() {
        img_close = (ImageView) mMenuView.findViewById(com.ody.p2p.shopcart.R.id.img_close);
        recyclerview_changegif_list = (RecyclerView) mMenuView.findViewById(com.ody.p2p.shopcart.R.id.recyclerview_changegif_list);
        btn_complete_changegif_window = (Button) mMenuView.findViewById(com.ody.p2p.shopcart.R.id.btn_complete_changegif_window);
        TextView tv_max_num = (TextView) mMenuView.findViewById(com.ody.p2p.shopcart.R.id.tv_max_num);
        TextView tv_title = (TextView) mMenuView.findViewById(com.ody.p2p.shopcart.R.id.tv_title);
        tv_title.setText("促销活动");
        tv_max_num.setVisibility(View.GONE);
        btn_complete_changegif_window.setText("知道了");
        btn_complete_changegif_window.setOnClickListener(this);
        img_close.setOnClickListener(this);

        dismissWindow(mMenuView.findViewById(com.ody.p2p.shopcart.R.id.fragment_itemtop));

        recyclerview_changegif_list.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
        if (null != mData && mData.size() > 0) {
            adapter = new PromotionDetailAdapter(mContext,mData);
            recyclerview_changegif_list.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == com.ody.p2p.shopcart.R.id.img_close) {
            dismiss();
        }
        if (v.getId() == com.ody.p2p.shopcart.R.id.btn_complete_changegif_window){
            dismiss();
        }
    }


}
