package com.ody.p2p.views.ProgressDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ody.p2p.R;
import com.ody.p2p.utils.LoadAnimationDrawable;


/**
 * 自定义对话框
 *
 * @author lxs
 * @version 1.0
 */
public class ProgressDialog extends Dialog {

    /**
     * 消息TextView
     */
//    private TextView tvMsg;
//    private ImageView iv_load;
    private LoadAnimationDrawable loadAnimationDrawable;

    public ProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }

    public ProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.view_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        ImageView imageView = (ImageView) findViewById(R.id.iv_load);
        Glide.with(context).load(R.drawable.dialog_loading).asGif().into(imageView);
//        iv_load= (ImageView) findViewById(R.id.iv_load);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
//        loadAnimationDrawable = new LoadAnimationDrawable();
//        iv_load.setBackgroundResource(R.drawable.loading);
//        animationDrawable= (AnimationDrawable) iv_load.getBackground();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                loadAnimationDrawable.recycler();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) return;

//        loadAnimationDrawable.animateRawManuallyFromXML(R.drawable.loading, iv_load, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onDetachedFromWindow() {
//        loadAnimationDrawable.recycler();
        super.onDetachedFromWindow();
    }


    /**
     * 设置进度条消息
     *
     * @param strMessage 消息文本
     */
    public void setMessage(String strMessage) {
//        if (tvMsg != null) {
//            tvMsg.setText(strMessage);
//        }
    }

    @Override
    protected void onStop() {
//        loadAnimationDrawable.recycler();
        System.gc();
        super.onStop();
    }
}