package com.ody.p2p.check.myorder;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ody.p2p.check.R;


/**
 * Created by ${tang} on 2017/3/31.
 */

public class NoAddressDialog extends Dialog {

    private TextView add;
    private TextView cancel;
    private TextView tv_content;

    private AddAddressCallback callback;

    public NoAddressDialog(Context context) {
        super(context, com.ody.p2p.R.style.CustomProgressDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_no_address, null);
        setContentView(view);
        add = (TextView) view.findViewById(R.id.add);
        cancel = (TextView) view.findViewById(R.id.cancel);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addAddress();
                dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
    }

    public void setCallBack(AddAddressCallback callback) {
        this.callback = callback;
    }

    public interface AddAddressCallback {
        void addAddress();
    }

    public void setNoAddress(int noAddress) {
        if (noAddress == 0) {//无收货地址
            if (tv_content != null && add != null) {
                tv_content.setText("您还没有收货地址，请新增收货地址");
                add.setText("新增收货地址");
            }
        } else if (noAddress == 1) {//当前位置无收货地址
            if (tv_content != null && add != null) {
                tv_content.setText("当前位置没有收货地址，请选择其他地址");
                add.setText("选择其他地址");
            }
        }
    }

}
