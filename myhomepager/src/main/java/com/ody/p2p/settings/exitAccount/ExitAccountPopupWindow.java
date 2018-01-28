package com.ody.p2p.settings.exitAccount;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;


/**
 * 退出账号
 */
public class ExitAccountPopupWindow extends PopupWindow implements View.OnClickListener {

    public ExitAccountPopupWindow(Context context, View.OnClickListener okClickListener) {
        View mainView = LayoutInflater.from(context).inflate(R.layout.setting_view_exit_account_popupwindow, null);

        TextView tv_confirm = (TextView) mainView.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) mainView.findViewById(R.id.tv_cancel);

        setContentView(mainView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimBottom);
        this.setFocusable(true);
        //设置背景透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.getBackground().setAlpha(160);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(okClickListener);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_cancel){
            dismiss();
        }
    }
}
