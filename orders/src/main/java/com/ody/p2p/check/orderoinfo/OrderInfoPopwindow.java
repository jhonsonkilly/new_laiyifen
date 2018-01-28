package com.ody.p2p.check.orderoinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.check.R;

/**
 * Created by ${tang} on 2016/8/23.
 */
public class OrderInfoPopwindow extends PopupWindow {

    private Context context;
    private View mMenuView;
    private CallBack callBack;

    public OrderInfoPopwindow(Context context){
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_orderinfo_pop, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体点击其他部分退出
        this.setFocusable(true);
        setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.ll_root).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        TextView tv_delete= (TextView) mMenuView.findViewById(R.id.tv_delete);
        TextView tv_logitics= (TextView) mMenuView.findViewById(R.id.tv_logitics);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.delete();
            }
        });
        tv_logitics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.logistics();
            }
        });
    }

    interface CallBack{
        void delete();

        void logistics();
    }

    public void setCallBack(CallBack callBack){
        this.callBack=callBack;
    }

}
