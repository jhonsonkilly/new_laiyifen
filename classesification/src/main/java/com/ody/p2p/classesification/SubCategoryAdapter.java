package com.ody.p2p.classesification;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.classesification.Bean.MultiCategory;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class SubCategoryAdapter extends BaseMultiItemQuickAdapter<MultiCategory, BaseViewHolder> {
    public SubCategoryAdapter() {
        this(new ArrayList<MultiCategory>());
    }

    public SubCategoryAdapter(List<MultiCategory> data) {
        super(data);
        addItemType(MultiCategory.TYPE_CONTENT, R.layout.item_category_selection_img);
        addItemType(MultiCategory.TYPE_SELECTION, R.layout.item_selection_txt);
        addItemType(MultiCategory.TYPE_AD, R.layout.item_selection_ad);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MultiCategory multiCategory) {
        switch (baseViewHolder.getItemViewType()) {
            case MultiCategory.TYPE_CONTENT:
                GlideUtil.display(mContext, 150, multiCategory.category.pictureUrl)
                        .placeholder(R.drawable.icon_stub)
                        .into((ImageView) baseViewHolder.getView(R.id.img));
                baseViewHolder.setText(R.id.name, multiCategory.category.categoryName);
                break;
            case MultiCategory.TYPE_SELECTION:
                baseViewHolder.setText(R.id.selection, multiCategory.category.categoryName);
                break;
            case MultiCategory.TYPE_AD:
            GlideUtil.display(mContext, multiCategory.ad.imageUrl)
                    .into((ImageView) baseViewHolder.getView(R.id.adImg));
                break;
        }
    }
}
