package com.ody.p2p.check.orderoinfo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/19.
 */
public class PackageDialog extends PopupWindow {

    private Context context;
    private View mMenuView;
    private List<String> paths;
    protected TextView tv_close;
    private RecyclerView rv_product;
    public PackageDialog(Context context){
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_package_dialog, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
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
        rv_product= (RecyclerView)mMenuView.findViewById(R.id.rv_product);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_product.setLayoutManager(linearLayoutManager);
        tv_close= (TextView) mMenuView.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
        this.setContentView(mMenuView);
    }

    public void setPaths(List<String> paths){
        this.paths=paths;
        rv_product.setAdapter(new PackageProductAdapter(context,paths));
    }


    class PackageProductAdapter extends RecyclerView.Adapter{
        List<String> mData;
        Context mContext;
        public PackageProductAdapter(Context mContext, List<String> mData){
            this.mContext = mContext;
            this.mData = mData;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package_pic,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder= (ViewHolder) holder;
            if(mData.get(position)!=null){
                GlideUtil.display(mContext, mData.get(position)).override(200, 200).into(viewHolder.iv_pic);
            }
        }

        @Override
        public int getItemCount() {
            if (mData!=null&&mData.size()>0){
                return mData.size();
            }else {
                return 0;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_pic;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_pic= (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }

}
