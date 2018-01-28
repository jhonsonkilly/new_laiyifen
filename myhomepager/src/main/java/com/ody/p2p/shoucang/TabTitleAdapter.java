package com.ody.p2p.shoucang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.PxUtils;

import java.util.List;

/**
 * Created by ody on 2016/8/17.
 */
public class TabTitleAdapter extends BaseRecyclerViewAdapter<String> {

    private int checked;
    private boolean noScroll = false;

    public TabTitleAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holders, final int position) {
        TitleViewHolder holder = (TitleViewHolder) holders;
        if (checked == position) {
            holder.view_line.setVisibility(View.VISIBLE);
        }else {
            holder.view_line.setVisibility(View.GONE);
        }
        holder.tv_name.setText(mDatas.get(position));

        if (noScroll) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) holder.view_line.getLayoutParams();
            ViewGroup.LayoutParams lp = holder.lay_root.getLayoutParams();
            lp.width = OdySysEnv.SCREEN_WIDTH / mDatas.size();
            mlp.leftMargin = PxUtils.dipTopx(20);
            mlp.rightMargin = PxUtils.dipTopx(20);
            holder.lay_root.requestLayout();
        }
        holder.lay_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemSelected(position);
                }
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tab_title, parent, false);
        return new TitleViewHolder(view);
    }


    class TitleViewHolder extends BaseRecyclerViewHolder {
        LinearLayout lay_root;
        TextView tv_name;
        View view_line;

        public TitleViewHolder(View itemView) {
            super(itemView);
            lay_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
            tv_name = (TextView) itemView.findViewById(R.id.tv_order_name);
            view_line = itemView.findViewById(R.id.view_line);


        }
    }

    public void setChecked(int cheked) {
        this.checked = cheked;
        notifyDataSetChanged();
    }

    public interface onItemSelectedListener {
        void onItemSelected(int position);
    }

    private onItemSelectedListener mListener;

    public void setOnIemSelectedListener(onItemSelectedListener listener) {
        mListener = listener;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
        notifyDataSetChanged();
    }

}
