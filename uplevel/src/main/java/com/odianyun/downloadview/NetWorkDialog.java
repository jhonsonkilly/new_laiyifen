package com.odianyun.downloadview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.odianyun.uplevel.R;

import static com.odianyun.uplevel.R.id.tv_dialog_confirm;

/**
 * Created by ody on 2016/7/19.
 */
public class NetWorkDialog extends Dialog {

    private NetworkDialogCallBack networkDialogCallBack;

    public NetWorkDialog(final Context mContext, final String url) {
        super(mContext);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        View view = LayoutInflater.from(mContext).inflate(R.layout.network_layout, null);
        setContentView(view);

        TextView tv_dialog_confirm = (TextView) view.findViewById(R.id.tv_dialog_confirm);
        TextView tv_dialog_cancle = (TextView) view.findViewById(R.id.tv_dialog_cancle);

        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkDialogCallBack.continueUpadate();
                dismiss();
//                PlanDialog mDialog = new PlanDialog(mContext, url);
//                mDialog.show();
//                dismiss();
            }
        });
        tv_dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkDialogCallBack.cancel();
                dismiss();
            }
        });

    }
    public void setNetWorkListener(NetworkDialogCallBack networkDialogCallBack){
        this.networkDialogCallBack=networkDialogCallBack;
    }

    public interface NetworkDialogCallBack{
        void continueUpadate();
        void cancel();
    }


}
