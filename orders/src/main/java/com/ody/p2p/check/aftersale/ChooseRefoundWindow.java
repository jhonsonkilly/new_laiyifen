package com.ody.p2p.check.aftersale;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.ody.p2p.check.R;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;

import java.util.List;


/**
 * Created by ody on 2016/6/29.
 */
public class ChooseRefoundWindow extends PopupWindow {
    View mMenuView;
    RecyclerView recyclerView;

    public ChooseRefoundWindow(final Context context, final List<ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs> nums) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.apply_dialog_cancle, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体点击其他部分退出
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        mMenuView.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.viewtop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setContentView(mMenuView);

        recyclerView = (RecyclerView) mMenuView.findViewById(R.id.reservation_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new RecyclerView.Adapter<viewHolder>() {
            @Override
            public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new viewHolder(LayoutInflater.from(context).inflate(R.layout.foeundz_item, parent, false));
            }

            @Override
            public void onBindViewHolder(viewHolder holder, final int position) {
                holder.tv_name.setText(nums.get(position).getValue());
                holder.tv_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mCallBack) {
                            mCallBack.chooseRefound(nums.get(position));
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return nums.size();
            }
        });
    }

    RefoundCallBack mCallBack;

    public void setRefoundCallBack(RefoundCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface RefoundCallBack {
        void chooseRefound(ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs key);
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public viewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
