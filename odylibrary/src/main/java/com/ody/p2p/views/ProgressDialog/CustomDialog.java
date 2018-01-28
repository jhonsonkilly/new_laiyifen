package com.ody.p2p.views.ProgressDialog;

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

import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.utils.RUtils;

/**
 * Created by pzy on 2016/6/21.
 */
public class CustomDialog extends Dialog {
    public String screenheight;
    public String screenwidth;
    View mView;
    public TextView titleMessage;
    public TextView tv_dialog_cancel;
    public TextView tv_dialog_confirm;
    private TextView tv_confirm_bottom;
    private LinearLayout ll_operate;
    private View view_vertical_linr;
    private ConfirmOrderOutNumberCallback confirmCallBack;

    /**
     * 初始化dialog数据
     */
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
    }

    public CustomDialog(Context context, String titleName, int type) {
        super(context);
        init();
        mView = LayoutInflater.from(getContext()).inflate(R.layout.custdialog_layout, null);
        setContentView(mView);//默认布局
        titleMessage = (TextView) mView.findViewById(R.id.tv_dialog_messagetitle);
        tv_dialog_confirm = (TextView) mView.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_cancel = (TextView) mView.findViewById(R.id.tv_dialog_cancel);
        tv_confirm_bottom = (TextView) mView.findViewById(R.id.tv_confirm_bottom);
        ll_operate = (LinearLayout) mView.findViewById(R.id.ll_operate);
        view_vertical_linr = mView.findViewById(R.id.view_line);
        titleMessage.setText(titleName);
        if (OdyApplication.SCHEME.equals("lyf")){
            tv_dialog_confirm.setTextColor(RUtils.getColorById(context,RUtils.LYF_SURE_COLOR));
        }else {
            tv_dialog_confirm.setTextColor(RUtils.getColorById(context,RUtils.THEME_COLOR));
        }
        tv_dialog_cancel.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
        if (type == 100) {
            tv_dialog_cancel.setText("知道了");
            tv_dialog_confirm.setText("如何开店");
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        callBack.Confirm();
                        dismiss();
                    }
                }
            });
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        dismiss();
                    }
                }
            });
        }
        if (type == 200) {
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("我再想想");
            tv_dialog_confirm.setText("原价购买");
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confirmCallBack != null) {
                        confirmCallBack.goOn();
                    }
                }
            });
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confirmCallBack != null) {
                        confirmCallBack.thinkAgain();
                    }
                }
            });
        }
        if (type == 2) {
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        callBack.Confirm();
                        dismiss();
                    }
                }
            });
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        dismiss();
                    }
                }
            });
        }
        if (type == 3) {
            setCanceledOnTouchOutside(false);
            ll_operate.setVisibility(View.GONE);
            tv_confirm_bottom.setVisibility(View.VISIBLE);
            tv_confirm_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.Confirm();
                    dismiss();
                }
            });
        }
        if(type==4){
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("继续逛逛");
            tv_dialog_cancel.setTextColor(context.getResources().getColor(R.color.blue));
            tv_dialog_confirm.setTextColor(context.getResources().getColor(R.color.blue));
            tv_dialog_confirm.setText("不取消");

            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        dismiss();
                    }
                }
            });
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        callBack.Confirm();
                        dismiss();
                    }
                }
            });
        }
        if(type==5){
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("拒绝");
            tv_dialog_cancel.setTextColor(context.getResources().getColor(R.color.blue));
            tv_dialog_confirm.setText("好");

            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        dismiss();
                    }
                }
            });
            tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        callBack.Confirm();
                        dismiss();
                    }
                }
            });
        }
        if (type == 6) {
            tv_dialog_cancel.setVisibility(View.GONE);
            view_vertical_linr.setVisibility(View.GONE);
            tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack) {
                        callBack.Confirm();
                        dismiss();
                    }
                }
            });
        }
    }


    public CustomDialog(Context context, int res) {
        super(context);
        init();
        setContentView(res);
    }

    public CustomDialog(Context context, View res) {
        super(context);
//        init();
        setContentView(res);
    }

    public CustomDialog(Context context, String titleName) {
        super(context);
        init();
        initView(titleName);
    }

    private void initView(String titleName) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.custdialog_layout, null);
        setContentView(mView);//默认布局
        titleMessage = (TextView) mView.findViewById(R.id.tv_dialog_messagetitle);
        tv_dialog_confirm = (TextView) mView.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_cancel = (TextView) mView.findViewById(R.id.tv_dialog_cancel);

        titleMessage.setText(titleName);
        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.Confirm();
                    dismiss();
                }
            }
        });
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    CustomDialogCallBack callBack;

    public void SetCustomDialogCallBack(CustomDialogCallBack callBack) {
        this.callBack = callBack;
    }



    public interface CustomDialogCallBack {
        void Confirm();
    }

    public void setConfirmCallBack(ConfirmOrderOutNumberCallback confirmCallBack) {
        this.confirmCallBack = confirmCallBack;
    }

    public interface ConfirmOrderOutNumberCallback {
        void thinkAgain();

        void goOn();
    }


}
