package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by lxs on 2016/8/11.
 */
public class MenuPopAdapter extends BaseRecyclerViewAdapter{


    public MenuPopAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        MenuPopBean bean = (MenuPopBean)getDatas().get(position);
        final MenuViewHolder menuHolder = (MenuViewHolder)holder;
        menuHolder.iv_pic.setImageResource(bean.picRes);
        menuHolder.tv_content.setText(bean.content);
        menuHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(menuHolder.itemView,position,menuHolder.getItemViewType());
                }
            }
        });
        if (position == getItemCount() - 1){
            menuHolder.view_line.setVisibility(View.GONE);
        }else{
            menuHolder.view_line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        MenuViewHolder holder = new MenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu_pop, parent, false));
        return holder;
    }


    public static class MenuViewHolder extends BaseRecyclerViewHolder {

        public ImageView iv_pic;
        public TextView tv_content;
        public RelativeLayout rl_item;
        public View view_line;

        public MenuViewHolder(View view) {
            super(view);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            view_line = view.findViewById(R.id.view_line);
        }
    }

}
