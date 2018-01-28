package com.ody.p2p.utils;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;


/**
 * 自定义Toast控件
 *
 * @author lxs
 * @version 1.0
 */
public class ToastUtils {

    private static Toast mToast;
    private static Toast mNewToast;
    private static TextView tv_toast_content;
    private static ImageView iv_label;

    static {
        mNewToast = new Toast(OdyApplication.gainContext());
        View view = LayoutInflater.from(OdyApplication.gainContext()).inflate(R.layout.layout_toast, null);
        tv_toast_content = (TextView) view.findViewById(R.id.tv_toast_content);
        iv_label = (ImageView) view.findViewById(R.id.iv_label);
        mNewToast.setView(view);
        mNewToast.setDuration(Toast.LENGTH_SHORT);
        mNewToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    }


    public static void sucessToast(String str) {
        iv_label.setVisibility(View.VISIBLE);
        iv_label.setImageResource(R.drawable.icon_success);
        if (mNewToast != null) {
            tv_toast_content.setText(str);
            mNewToast.show();
        }
    }

    public static void failToast(String str) {
        iv_label.setVisibility(View.VISIBLE);
        iv_label.setImageResource(R.drawable.icon_fail);
        tv_toast_content.setText(str);
        if (mNewToast != null) {
            mNewToast.show();
        }
    }

    public static void showStr(String str) {
        iv_label.setVisibility(View.GONE);
        if (mNewToast != null) {
            tv_toast_content.setText(str);
            mNewToast.show();
        }
    }

    public static void icondefineToast(int res, String str) {
        iv_label.setVisibility(View.VISIBLE);
        iv_label.setImageResource(res);
        if (mNewToast != null) {
            tv_toast_content.setText(str);
            mNewToast.show();
        }
    }

    public static void showShort(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(OdyApplication.gainContext(), str, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setText(str);
        mToast.show();
    }

    public static void showPage(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(OdyApplication.gainContext(), str, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 20, 0);
        mToast.setText(str);
        mToast.show();
    }


    public static void showToast(Context context, String str) {
        if (mToast == null) {
            mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setText(str);
        mToast.show();
    }

    public static void showToast(Context context, int strID) {
        if (mToast == null) {
            mToast = Toast.makeText(context, strID, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setText(context.getString(strID));
        mToast.show();
    }

    public static void showLongToast(Context context, String str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();
    }

    public static void showToast(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(OdyApplication.gainContext(), str, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setText(str);
        mToast.show();
    }

    public static void showToast(int strID) {
        if (mToast == null) {
            mToast = Toast.makeText(OdyApplication.gainContext(), strID, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.setText(OdyApplication.gainContext().getString(strID));
        mToast.show();
    }

    public static void showLongToast(String str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(OdyApplication.gainContext(), str, Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();
    }

}
