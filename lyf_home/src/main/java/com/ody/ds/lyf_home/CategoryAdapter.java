package com.ody.ds.lyf_home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by lxs on 2016/12/6.
 */
public class CategoryAdapter extends BaseRecyclerViewAdapter{

    public CategoryAdapter(Context context, List datas) {
        super(context, datas);
    }

    public CategoryAdapter(Context context){
        super(context);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ModuleDataCategoryBean.CategoryBean bean = (ModuleDataCategoryBean.CategoryBean) getDatas().get(position);
        CategoryViewHolder holder1 = (CategoryViewHolder) holder;
        holder1.tv_category_name.setText(bean.categoryName);
        if (bean.choose){
            holder1.tv_category_name.setTextColor(mContext.getResources().getColor(R.color.black));
        }else {
            holder1.tv_category_name.setTextColor(mContext.getResources().getColor(R.color.grey));
        }
        GlideUtil.display(mContext, bean.pictureUrl).into(holder1.iv_category);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_item, parent, false)));
    }

    //特色主题
    public static class CategoryViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_category_name;
        public ImageView iv_category;

        public CategoryViewHolder(View view) {
            super(view);
            tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
            iv_category = (ImageView) view.findViewById(R.id.iv_category);
        }
    }




}
