package com.ody.p2p.check.bill;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ody.p2p.check.R;


/**
 * Created by ${tang} on 2017/3/13.
 */

public class InvoiceRuleDialog extends Dialog {

    private TextView tv_close;
    public InvoiceRuleDialog(Context context) {
        super(context, com.ody.p2p.R.style.CustomProgressDialog);
        setContentView(R.layout.layout_invoice_rule_dialog);
        setCanceledOnTouchOutside(false);
        tv_close= (TextView) findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
