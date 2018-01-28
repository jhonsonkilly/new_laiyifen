package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ody.p2p.R;

/**
 * Created by ody on 2016/6/17.
 */
public class CouponWindow extends PopupWindow {
    ImageView img_close;
    Button btn_OK;
    RecyclerView recyclerview_coupon;
    View mMenuView;
    FrameLayout framlayout_mm;
    Context mContext;

    public CouponWindow(Context context, CouponBean data) {
        super(context);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.gift_product_window_layout, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体点击其他部分退出
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        initView();
        initListener();

        if (null != data && null != data.getData() && data.getData().size() > 0) {
            initData(data);
        } else {
            Toast.makeText(mContext, "数据异常..", Toast.LENGTH_LONG).show();
        }
    }


    private void initView() {
        img_close = (ImageView) mMenuView.findViewById(R.id.img_close);
        btn_OK = (Button) mMenuView.findViewById(R.id.btn_OK);
        framlayout_mm = (FrameLayout) mMenuView.findViewById(R.id.framlayout_mm);

    }

    private void initListener() {
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.framlayout_mm).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initData(CouponBean mData) {
        recyclerview_coupon = (RecyclerView) mMenuView.findViewById(R.id.recyclerview_coupon);
        recyclerview_coupon.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_coupon.setAdapter(new CouponAdapter(mContext, mData.getData()));
    }
}
