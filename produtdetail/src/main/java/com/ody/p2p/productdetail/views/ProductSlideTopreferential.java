package com.ody.p2p.productdetail.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.productdetail.productdetail.adapter.AddressAdapter;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductPresent;
import com.ody.p2p.produtdetail.R;

import java.util.List;


/**
 * Created by Mazoh on 2016/1/15.
 * 从底部滑上来的popup
 */
public class ProductSlideTopreferential extends PopupWindow implements View.OnClickListener {

    private View popupView;
    private ListView listview;
    private AddressAdapter adressadapter;
    List<UserAdressBean.Data.UsualAddress> list;
    ProductPresent ppreesent;
    Activity context;
    //标题
    TextView tx_1, text_2;
    ImageView img_close;

    public ProductSlideTopreferential(Activity context, List<UserAdressBean.Data.UsualAddress> list) {
        super(context);
        this.context = context;
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
//        init();
//        bindEvent(list);
    }

    private void init() {
//        tx_1= (TextView) popupView.findViewById(R.id.tx_1);
//        tx_1.setText("配送至");
//        text_2= (TextView) popupView.findViewById(R.id.text_2);
//        text_2.setText("选择其他地址");
//        text_2.setOnClickListener(this);
//        img_close= (ImageView) popupView.findViewById(R.id.img_close);//關閉
//        img_close.setOnClickListener(this);
//        listview= (ListView) popupView.findViewById(R.id.list_list);

    }


    private void bindEvent(List<UserAdressBean.Data.UsualAddress> lists) {
        if (popupView != null) {

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_close) {
            dismiss();
        }
    }


}
