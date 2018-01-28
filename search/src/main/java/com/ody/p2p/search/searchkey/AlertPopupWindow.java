package com.ody.p2p.search.searchkey;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.search.R;


/**
 * Created by admin on 2016/1/25.
 */
public class AlertPopupWindow extends PopupWindow implements View.OnClickListener {

    public AlertPopupWindow(Context context, View.OnClickListener okClickListener) {
        View mainView = LayoutInflater.from(context).inflate(R.layout.view_popupwindow, null);

        TextView tvPopupwCancel = (TextView) mainView.findViewById(R.id.tvPopupwCancel);
        TextView tvPopupwOk = (TextView) mainView.findViewById(R.id.tvPopupwOk);
        setContentView(mainView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置显示隐藏动画
//        setAnimationStyle(R.style.AnimBottom);
        this.setFocusable(true);
        //设置背景透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        tvPopupwCancel.setOnClickListener(this);
        tvPopupwOk.setOnClickListener(okClickListener);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tvPopupwCancel){
            dismiss();
        }
    }
}
