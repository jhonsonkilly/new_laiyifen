package com.ody.p2p.check.myorder;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.check.R;

/**
 * Created by ${tang} on 2016/9/26.
 */
public class OverSeaPurchaseDialog extends Dialog {

    private String screenheight;
    private String screenwidth;
    private OverSeaClick overSeaClick;
    public OverSeaPurchaseDialog(Context context,int type ) {
        super(context);
        init();
        View view= LayoutInflater.from(context).inflate(R.layout.layout_oversea_dialog,null);
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        TextView tv_left= (TextView) view.findViewById(R.id.tv_left);
        TextView tv_right= (TextView) view.findViewById(R.id.tv_right);
        LinearLayout ll_choose= (LinearLayout) view.findViewById(R.id.ll_choose);
        TextView tv_bottom= (TextView) view.findViewById(R.id.tv_bottom);
        if(type==1){//实名认证提示
            tv_title.setText(R.string.sea_for_perfect_idcard);
            tv_bottom.setVisibility(View.GONE);
            ll_choose.setVisibility(View.VISIBLE);
            tv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overSeaClick.goShoppingcar();
                }
            });
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overSeaClick.goAddress();
                }
            });
        }else if(type==2){//海购商品超2000提示
            tv_title.setText(R.string.single_oreder_noe_moer_2000);
            tv_bottom.setVisibility(View.VISIBLE);
            ll_choose.setVisibility(View.GONE);
            tv_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    overSeaClick.goShoppingcar();
                }
            });
        }
        setContentView(view);
    }

    public interface OverSeaClick{
        void goShoppingcar();
        void goAddress();
    }

    public void setLinstener(OverSeaClick overSeaClick){
        this.overSeaClick=overSeaClick;
    }


    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        screenheight = d.getHeight() + "";
        screenwidth = d.getWidth() + "";
        WindowManager.LayoutParams p = getWindow().getAttributes();
        double rote = d.getWidth() / 480.0;
        p.width = (int) (480 * rote);
        dialogWindow.setAttributes(p);
        setCanceledOnTouchOutside(false);
    }
}
