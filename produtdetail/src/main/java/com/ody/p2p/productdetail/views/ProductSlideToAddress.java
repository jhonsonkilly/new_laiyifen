package com.ody.p2p.productdetail.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.productdetail.productdetail.adapter.AddressAdapter;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductPresent;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.views.selectaddress.RequestAddressBean;
import com.ody.p2p.views.selectaddress.SeclectAddressPopupWindow;
import com.ody.p2p.views.selectaddress.selectAddressListener;

import java.util.List;


/**
 * Created by Mazoh on 2016/1/15.
 * 从底部滑上来的popup
 */
public class ProductSlideToAddress extends PopupWindow implements View.OnClickListener, SeclectAddressPopupWindow.OnDismissListener,AddressAdapter.adressBack {

    private View popupView;
    private RecyclerView list_adress;
    private AddressAdapter adressadapter;
    List<UserAdressBean.Data.UsualAddress> list;
    ProductPresent ppreesent;
    TextView text_otheradress;
    Activity context;
    ImageView img_close;

    public ProductSlideToAddress(Activity context, List<UserAdressBean.Data.UsualAddress> list) {
        super(context);
        this.context = context;
        this.list = list;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.produtdetail_popup_slide_from_bottom, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(popupView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        init();
        bindEvent(list);
    }

    private void init() {
        list_adress = (RecyclerView) popupView.findViewById(R.id.list_adress);
        img_close = (ImageView) popupView.findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
        text_otheradress = (TextView) popupView.findViewById(R.id.text_otheradress);
        text_otheradress.setOnClickListener(this);
    }


    private void bindEvent(List<UserAdressBean.Data.UsualAddress> lists) {
        if (popupView != null) {
            adressadapter = new AddressAdapter(lists,context);
            list_adress.setAdapter(adressadapter);
            list_adress.setLayoutManager(new LinearLayoutManager(context));
            adressadapter.notifyDataSetChanged();
            adressadapter.setAdressback(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_otheradress) {
            choosecenter();
        } else if (v.getId() == R.id.img_close) {
            dismiss();
        }
    }

    //选择城市
    private void choosecenter() {
        dismiss();
        SeclectAddressPopupWindow seclectAddressPopupWindow = new SeclectAddressPopupWindow(context);
        seclectAddressPopupWindow.showAtLocation(popupView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        seclectAddressPopupWindow.setSelectAddressListener(new selectAddressListener() {
            @Override
            public void getAddress(RequestAddressBean.Data first, RequestAddressBean.Data second, RequestAddressBean.Data third) {

            }
        });
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onclik(int position) {
        adressadapter.setCheckedData(position);
    }
}
