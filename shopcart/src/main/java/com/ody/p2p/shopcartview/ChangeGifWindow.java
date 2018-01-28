package com.ody.p2p.shopcartview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.shopcart.R;
import com.ody.p2p.shopcart.ShopCartBean;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;

/**
 * Created by ody on 2016/8/29.
 */
public class ChangeGifWindow extends BasePopupWindow<ShopCartBean.GiftProductList> implements View.OnClickListener {
    RecyclerView recyclerview_changegif_list;
    Boolean isFlage;
    public Button btn_complete_changegif_window;
    public TextView tv_max_num,tv_title;
    ImageView img_close;
    int MaxSelecked = 0;
    ShopCartBean.Promotion promotion;
    public ChangeGifAdapter adapter;

    public ChangeGifWindow(Context mContext, ShopCartBean.GiftProductList data, ShopCartBean.Promotion promotion, Boolean isFlage, int maxSelecked) {
        super(mContext, data);
        this.isFlage = isFlage;
        this.promotion = promotion;
        this.MaxSelecked = maxSelecked;
        init(mContext, R.layout.layout_changegif_window);
        view();
    }

    private void view() {
        tv_title=(TextView)mMenuView.findViewById(R.id.tv_title);
        img_close = (ImageView) mMenuView.findViewById(R.id.img_close);
        tv_max_num = (TextView) mMenuView.findViewById(R.id.tv_max_num);
        recyclerview_changegif_list = (RecyclerView) mMenuView.findViewById(R.id.recyclerview_changegif_list);
        btn_complete_changegif_window = (Button) mMenuView.findViewById(R.id.btn_complete_changegif_window);
        if (promotion.getPromotionType()==1018||promotion.getPromotionType()==1019){
            tv_max_num.setText(mContext.getString(R.string.max_choose) + MaxSelecked +mContext.getString(R.string.individual)+mContext.getString(R.string.redemption));
            tv_title.setText(mContext.getString(R.string.redemption));
        }else{
            tv_max_num.setText(mContext.getString(R.string.max_choose) + MaxSelecked +mContext.getString(R.string.individual)+mContext.getString(R.string.gift));
            tv_title.setText(mContext.getString(R.string.gift));
        }
        btn_complete_changegif_window.setOnClickListener(this);
        img_close.setOnClickListener(this);

        dismissWindow(mMenuView.findViewById(R.id.fragment_itemtop));

        recyclerview_changegif_list.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
        if (null != mData.getGiftProducts() && mData.getGiftProducts().size() > 0) {
            adapter = new ChangeGifAdapter(mContext, promotion, MaxSelecked, mData.getGiftProducts(), isFlage);
            recyclerview_changegif_list.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_close) {
            dismiss();
        } else if (v.getId() == R.id.btn_complete_changegif_window) {
            if (null != mChangeGifCallBack) {
                mChangeGifCallBack.updateGif(getCheckedNum());
            }
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != mChangeGifCallBack) {
            mChangeGifCallBack.windowdismiss();
        }
    }

    ChangeGifCallBack mChangeGifCallBack;

    public void setmChangeGifCallBack(ChangeGifCallBack mChangeGifCallBack) {
        this.mChangeGifCallBack = mChangeGifCallBack;
    }

    public interface ChangeGifCallBack {
        void updateGif(String mpids);

        void windowdismiss();
    }

    /**
     * 获取选中数量
     */
    private String getCheckedNum() {
        String checkedMpIds = "";
        for (ShopCartBean.GiftProducts data : mData.getGiftProducts()) {
            if (data.getChecked() == 1) {
                checkedMpIds += data.getMpId() + ",";
            }
        }
        return checkedMpIds.length() > 0 ? checkedMpIds.substring(0, checkedMpIds.length() - 1) : "";
    }
}