package com.ody.p2p.views.ProgressDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ody.p2p.R;

import static com.ody.p2p.R.id.title;
import static com.ody.p2p.R.id.tv_dialog_cancel;
import static com.ody.p2p.R.id.tv_dialog_confirm;

/**
 * Created by pzy on 2017/1/19.
 */
public class ReminderDialog extends Dialog {
    View mView;
    public ReminderDialog(Context context, int res) {
        super(context);
        init();
        setContentView(res);
    }

    public ReminderDialog(Context context, View res) {
        super(context);
        init();
        setContentView(res);
    }

    public ReminderDialog(Context context, String titleName,String message) {
        super(context);
        init();
        initView(titleName,message);
    }

    /**
     * 初始化dialog数据
     */
    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.reminder_dialog_layout, null);
        setContentView(mView);//默认布局
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        double rote = d.getWidth() / 480.0;
        p.width = (int) (480 * rote);
        dialogWindow.setAttributes(p);
    }

    private void initView(String titleName,String message) {

        ((TextView) mView.findViewById(R.id.tv_dialog_messagetitle)).setText(titleName);
        ((TextView)mView.findViewById(R.id.tv_dialog_message)).setText(message);

        mView.findViewById(R.id.tv_confirm_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
