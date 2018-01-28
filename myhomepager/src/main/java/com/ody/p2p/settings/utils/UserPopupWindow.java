package com.ody.p2p.settings.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;


/**
 * Created by admin on 2016/1/25.
 */
public class UserPopupWindow extends PopupWindow{

    //三个textview监听,从上到下  title ok ok2 cancel
    public UserPopupWindow(Context context, int flag, View.OnClickListener titleListener,View.OnClickListener okClickListener,View.OnClickListener ok2ClickListener,View.OnClickListener cancelListener) {
        View mainView = LayoutInflater.from(context).inflate(R.layout.setting_view_3_popupwindow, null);

        TextView tv_title = (TextView) mainView.findViewById(R.id.tv_title);
        TextView tv_confirm = (TextView) mainView.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) mainView.findViewById(R.id.tv_cancel);
        //从上到下第三行
        TextView tv_confirm2 = (TextView) mainView.findViewById(R.id.tv_confirm2);
        View v_divide_line2 = mainView.findViewById(R.id.v_divide_line2);

        setContentView(mainView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置显示隐藏动画
//        setAnimationStyle(R.style.AnimBottom);
        this.setFocusable(true);
        //设置背景透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.getBackground().setAlpha(160);
        tv_title.setOnClickListener(titleListener);
        tv_confirm.setOnClickListener(okClickListener);
        tv_confirm2.setOnClickListener(ok2ClickListener);
        tv_cancel.setOnClickListener(cancelListener);

        if (flag==1){//清除图片缓存
            tv_title.setText(context.getString(R.string.delete_cache_photos));
            tv_title.setTextSize(16);
            tv_title.setTextColor(context.getResources().getColor(R.color.main_title_color));
            tv_confirm.setText(context.getString(R.string.confirm));
            tv_confirm.setTextSize(16);
            tv_confirm.setTextColor(context.getResources().getColor(R.color.theme_color));
            tv_cancel.setText(context.getString(R.string.cancel));
            tv_cancel.setTextSize(16);
            tv_cancel.setTextColor(context.getResources().getColor(R.color.sub_title_color));
        }else if (flag==2){
            tv_title.setText(context.getString(R.string.photograph));
            tv_title.setTextSize(18);
            tv_title.setTextColor(context.getResources().getColor(R.color.blue));
            tv_confirm.setText(context.getString(R.string.myPhoto));
            tv_confirm.setTextSize(18);
            tv_confirm.setTextColor(context.getResources().getColor(R.color.blue));
            tv_cancel.setText(context.getString(R.string.cancel));
            tv_cancel.setTextSize(18);
            tv_cancel.setTextColor(context.getResources().getColor(R.color.blue));

        }else if (flag==3){
            tv_title.setText(context.getString(R.string.man));
            tv_title.setTextSize(18);
            tv_title.setTextColor(context.getResources().getColor(R.color.blue));
            tv_confirm.setText(context.getString(R.string.woman));
            tv_confirm.setTextSize(18);
            tv_confirm.setTextColor(context.getResources().getColor(R.color.blue));
            tv_confirm2.setText(context.getString(R.string.sex_secrecy));
            tv_confirm2.setTextSize(18);
            tv_confirm2.setTextColor(context.getResources().getColor(R.color.blue));
            tv_confirm2.setVisibility(View.VISIBLE);
            v_divide_line2.setVisibility(View.VISIBLE);
        }
    }
}
