package com.ody.p2p.login.login;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.login.Bean.ThirdLoginLogoBean;
import com.ody.p2p.login.R;
import com.ody.p2p.login.utils.CircleImageView;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * 第三方登录logo使用的adapter,布局没设置好,暂不使用
 */
public class ThirdLoginLogoAdapter extends RecyclerView.Adapter<ThirdLoginLogoAdapter.ViewHolder> {

    private List<ThirdLoginLogoBean.LogoData.Logos> mData;
    private OnItemClickListener listener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView civ_third_logo;
        public TextView tv_jiange;

        public ViewHolder(View itemView) {
            super(itemView);
            civ_third_logo = (CircleImageView) itemView.findViewById(R.id.civ_third_logo);
            tv_jiange = (TextView)itemView.findViewById(R.id.tv_jiange);
        }
    }

    public ThirdLoginLogoAdapter(Context context,List<ThirdLoginLogoBean.LogoData.Logos> mData) {
        this.mData = mData;
        this.context = context;
    }

//    public void setData(List<SearchKeywordListBean.WareList> mData){
//        this.mData = mData;
//        notifyDataSetChanged();
//    }

    //onCreateViewHolder 和 onBindViewHolder是在recycleView可见的时候才调用?也就是说xml布局里如果有问题,导致recycleView不可见(即便设置了visiable),那么这些
    //方法就不会调用...
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_third_login_logo, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GlideUtil.display(context, mData.get(position).logoPic).override(120,120).into(holder.civ_third_logo);//.override(200, 200)  .centerCrop()
        if (position==mData.size()-1){
            holder.tv_jiange.setVisibility(View.GONE);
        }

        //设置条目点击事件
        holder.civ_third_logo.setTag(position);
        holder.civ_third_logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //给recycleView的条目设置点击事件
    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
