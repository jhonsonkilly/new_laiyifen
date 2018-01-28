package com.ody.p2p.main.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ody.p2p.main.R;
import com.ody.p2p.utils.PxUtils;

import static com.alibaba.mobileim.YWChannel.getResources;


/**
 * Created by meijunqiang on 2017/3/21.
 * 描述：伊点卡筛选栏
 */

public class YidianFilterPopupWindow extends PopupWindow {
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private static YidianFilterPopupWindow instance;
    private int currentCondition = 0;

    public static YidianFilterPopupWindow getInstance() {
        if (instance == null) {
            instance = new YidianFilterPopupWindow();
        }
        return instance;
    }

    public YidianFilterPopupWindow builder(Context context, String[] list, int radioButton) {
        RadioGroup group = new RadioGroup(context);
        group.setGravity(Gravity.RIGHT);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        group.setLayoutParams(layoutParams);
        RadioButton button;
        group.setOrientation(RadioGroup.VERTICAL);
        if (list != null && list.length > 0) {
            for (int i = 0; i < list.length; i++) {
                button = new RadioButton(context);
                if (i == list.length - 1) {
                    button.setPadding(0, PxUtils.dipTopx(24), PxUtils.dipTopx(10), PxUtils.dipTopx(24));
                } else {
                    button.setPadding(0, PxUtils.dipTopx(24), PxUtils.dipTopx(10), 0);
                }
                button.setText(list[i]);
                button.setTextSize(PxUtils.pxTosp(56));
                button.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                if (radioButton == 0) {
                    button.setTextColor(getResources().getColor(R.color.white));
                } else {
                    button.setTextColor(getResources().getColorStateList(radioButton));
                }
                if (i == currentCondition) {
                    button.setChecked(true);
                }
                button.setId(i);
                group.addView(button);
            }
        }
        //放到初始化之后，防止被动点击
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mOnCheckedChangeListener != null) {
                    currentCondition = checkedId;
                    mOnCheckedChangeListener.onCheckedChanged(checkedId);
                }
            }
        });
        instance.setContentView(group);
        instance.setWidth(PxUtils.dipTopx(132));
        instance.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        Drawable drawable = getResources().getDrawable(com.ody.p2p.R.drawable.bg_menu_select);
        drawable.setAlpha(218);
        instance.setBackgroundDrawable(drawable);
        instance.setFocusable(true);
        instance.setOutsideTouchable(true);
        return instance;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position);
    }
}
