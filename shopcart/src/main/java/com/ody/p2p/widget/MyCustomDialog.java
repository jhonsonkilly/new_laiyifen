package com.ody.p2p.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ody.p2p.shopcart.R;


public class MyCustomDialog extends Dialog {

    public String screenheight;
    public String screenwidth;

    //	dialog_enter_exit 进出动画
    @SuppressLint("NewApi")
    public MyCustomDialog(Context context, int res, String str) {
        super(context, R.style.dialog_enter_exit);
        setContentView(res);
        init();
        if (res == R.layout.dialog_shopcart_layout) {
//            TextView dialog_hint_address_new = (TextView) findViewById(R.id.dialog_hint_address_new);
//            dialog_hint_address_new.setOnClickListener(l);
            TextView tv_dialog_title = (TextView) findViewById(R.id.tv_shopcart_dialog_title);
            tv_dialog_title.setText(str);
            TextView tv_dialog_confirm = (TextView) findViewById(R.id.tv_shopcart_dialog_confirm);
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mDialogcallback) {
                        mDialogcallback.confirm();
                    }
                    dismiss();
                }
            });
            TextView tv_dialog_cancle = (TextView) findViewById(R.id.tv_shopcart_dialog_cancle);
            tv_dialog_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    dialogcallback mDialogcallback;

    public void setdialogcallback(dialogcallback mDialogcallback) {
        this.mDialogcallback = mDialogcallback;
    }

    public interface dialogcallback {
        void confirm();
    }

    public MyCustomDialog(Context context, AdapterView.OnItemClickListener l, int res, SimpleAdapter odyAdapter) {
        super(context, R.style.massage_dialog);
        setContentView(res);
        init();
//        if (res == R.layout.dialog_layout_delivery_time) {
//            ListView list = (ListView) findViewById(R.id.dailog_delibery_time);
//
//            list.setAdapter(odyAdapter);
//            list.setOnItemClickListener(l);
//        }
    }

    /**
     * 初始化dialog数据
     */
    private void init() {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        screenheight = d.getHeight() + "";
        screenwidth = d.getWidth() + "";
        WindowManager.LayoutParams p = getWindow().getAttributes();
        double rote = d.getWidth() / 480.0;
        p.width = (int) (480 * rote);
        dialogWindow.setAttributes(p);
    }
}
