package com.ody.p2p.productdetail.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.productdetail.productdetail.adapter.ServiceAssuranceAdapter;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.views.basepopupwindow.ProductBean;

import java.util.List;

/**
 * Created by Mazoh on 2016/1/15.
 * 从底部滑上来的popup
 */
public class ProdutServiceSlide extends PopupWindow implements View.OnClickListener {

    private View popupView;
    private RecyclerView list_adress;
    List<ProductBean.securityVOList> list;
    Activity context;
    int themcolor=R.color.theme_color;
    int textThemeColor=R.color.white;//标题
    TextView  text_minukonw;
    ImageView img_close;

    public ProdutServiceSlide(Activity context, List<ProductBean.securityVOList> list, int themcolor,int textThemeColor) {
        super(context);
        this.context = context;
        this.list = list;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.produtdetail_slide_service, null);
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
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupView.findViewById(R.id.popup_anima).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.themcolor=themcolor;
        this.textThemeColor=textThemeColor;
        init();
        bindEvent(list);
    }

    private void init() {
        list_adress = (RecyclerView) popupView.findViewById(R.id.list_minues);

        text_minukonw = (TextView) popupView.findViewById(R.id.text_minukonw);
        text_minukonw.setTextColor(context.getResources().getColor(R.color.white));
        text_minukonw.setOnClickListener(this);
        img_close = (ImageView) popupView.findViewById(R.id.img_close);//關閉
        img_close.setOnClickListener(this);
    }


    private void bindEvent( List<ProductBean.securityVOList> lists) {
        if (popupView != null) {
            if (null!=lists&&lists.size()>0){
            ServiceAssuranceAdapter minuAdapter = new ServiceAssuranceAdapter(context, lists);
            list_adress.setLayoutManager(new LinearLayoutManager(context));
            list_adress.setAdapter(minuAdapter);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_minukonw) {
            dismiss();
        } else if (v.getId() == R.id.img_close) {
            dismiss();
        }
    }



}
