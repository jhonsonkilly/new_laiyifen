package com.ody.p2p.classesification;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.retrofit.category.Category;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class ParentCategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {
    public ParentCategoryAdapter() {
        this(R.layout.item_categoty, new ArrayList<Category>());
    }

    public ParentCategoryAdapter(int layoutResId, List<Category> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Category category) {
        Context context = baseViewHolder.getConvertView().getContext();
        //ColorFilterTransformation
        if (category.isSelected) {
            baseViewHolder.setTextColor(R.id.category_name, ContextCompat.getColor(context, R.color.theme_color));
            baseViewHolder.setVisible(R.id.selected, true);
            baseViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_color));
//            if (category.imgId > 0) {
//                GlideUtil.display(context, category.imgId).bitmapTransform(new ColorFilterTransformation(context, ContextCompat.getColor(context, R.color.theme_color))).into((ImageView) baseViewHolder.getView(R.id.img));
//            } else {
//                GlideUtil.display(context, category.pictureUrl).bitmapTransform(new ColorFilterTransformation(context, ContextCompat.getColor(context, R.color.theme_color))).into((ImageView) baseViewHolder.getView(R.id.img));
//            }
        } else {
            baseViewHolder.setVisible(R.id.selected, false);
            baseViewHolder.setTextColor(R.id.category_name, ContextCompat.getColor(context, R.color.main_title_color));
            baseViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            GlideUtil.display(context, category.pictureUrl).into((ImageView) baseViewHolder.getView(R.id.img));
        }
        if (category.imgId > 0) {
            GlideUtil.display(context, category.imgId).into((ImageView) baseViewHolder.getView(R.id.img));
        } else {
            GlideUtil.display(context, category.pictureUrl).into((ImageView) baseViewHolder.getView(R.id.img));
        }
        baseViewHolder.setText(R.id.category_name, category.categoryName);

    }
}
