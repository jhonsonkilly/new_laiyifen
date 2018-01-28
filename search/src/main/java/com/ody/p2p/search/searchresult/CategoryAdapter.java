package com.ody.p2p.search.searchresult;

/**
 * Created by lxs on 2017/1/3.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;

import java.util.List;

/**
 * Created by lxs on 2016/12/6.
 */
public class CategoryAdapter extends BaseRecyclerViewAdapter {

    public CategoryAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ResultCategoryBean bean = (ResultCategoryBean) getDatas().get(position);
        CategoryViewHolder holder1 = (CategoryViewHolder) holder;
        holder1.tv_category_name.setText(bean.name);
        if (bean.choose) {
            holder1.tv_category_name.setSelected(true);
            holder1.tv_category_name.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        } else {
            holder1.tv_category_name.setTextColor(mContext.getResources().getColor(R.color.main_title_color));
            holder1.tv_category_name.setSelected(false);
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false)));
    }

    //特色主题
    public static class CategoryViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_category_name;

        public CategoryViewHolder(View view) {
            super(view);
            tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
        }
    }


    public void refreshCateList(List<ResultCategoryBean> cateList, int position) {
        for (int i = 0; i < cateList.size(); i++) {
            if (position == i) {
                cateList.get(i).choose = true;
            } else {
                cateList.get(i).choose = false;
            }
        }
    }


}
