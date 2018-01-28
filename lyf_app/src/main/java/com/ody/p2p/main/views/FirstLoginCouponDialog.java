package com.ody.p2p.main.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ody.p2p.Constants;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.FirstLoginCouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.search.searchresult.popupwindow.SpaceItemDecoration;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

/**
 * Created by user on 2017/5/22.
 */

public class FirstLoginCouponDialog extends Dialog {

    public Context context;
    public List<FirstLoginCouponBean.MyCouponOutputDTO> source;

    public FirstLoginCouponDialog(Context context, List<FirstLoginCouponBean.MyCouponOutputDTO> source, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.source = source;
        initDialog(context, source);
    }


    public void initDialog(Context context, final List<FirstLoginCouponBean.MyCouponOutputDTO> source) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_firstcoupon, null);
        setContentView(view);
        RecyclerView recycler_coupon = (RecyclerView) view.findViewById(R.id.recycler_coupon);
        ImageView image_close = (ImageView) view.findViewById(R.id.image_close);
        Button btn_buy = (Button) view.findViewById(R.id.btn_buy);
        LinearLayoutManager couponManger = new LinearLayoutManager(context);
        couponManger.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_coupon.setLayoutManager(couponManger);
        FirstLoginCouponAdapter firstLoginCouponAdapter = new FirstLoginCouponAdapter(context, source);
        recycler_coupon.setAdapter(firstLoginCouponAdapter);
        recycler_coupon.addItemDecoration(new SpaceItemDecoration(9));
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //���ÿ��
        getWindow().setAttributes(lp);
    }
}
