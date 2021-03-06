package com.ody.p2p.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by lxs on 2016/12/5
 */
public class FuncAdapter extends BaseRecyclerViewAdapter {

    public FuncAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final FuncHolder viewHolder = (FuncHolder) holder;
        final Ad bean = ((List<Ad>) getDatas()).get(position);
        viewHolder.tv_func_content.setText(bean.name);
        //支持gif图片显示
        if(bean.imageUrl.endsWith(".gif")){
            GlideUtil.displayGif(mContext, viewHolder.iv_func, bean.imageUrl);
        }else if(bean.imageUrl.endsWith(".jpg") || bean.imageUrl.endsWith(".png")){
            GlideUtil.display(mContext, bean.imageUrl).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(viewHolder.iv_func);
        }
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position, bean);
                }
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        FuncHolder holder = new FuncHolder(LayoutInflater.from(mContext).inflate(R.layout.item_func_item, parent, false));
        return holder;
    }

    public static class FuncHolder extends BaseRecyclerViewHolder {

        public ImageView iv_func;
        public TextView tv_func_content;
        public LinearLayout ll_item;

        public FuncHolder(View view) {
            super(view);
            iv_func = (ImageView) view.findViewById(R.id.iv_func);
            tv_func_content = (TextView) view.findViewById(R.id.tv_func_content);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }
}
