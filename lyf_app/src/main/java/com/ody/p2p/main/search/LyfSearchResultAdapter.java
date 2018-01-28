package com.ody.p2p.main.search;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.search.searchresult.SearchResultActivity;
import com.ody.p2p.search.searchresult.SearchResultAdapter;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by lxs on 2016/12/7.
 */
public class LyfSearchResultAdapter extends SearchResultAdapter {

    public LyfSearchResultAdapter(Context context, List datas, int showFlag) {
        super(context, datas, showFlag);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        super.showViewHolder(holder, position);
        if (position < mDatas.size()) {
            final ResultBean.ProductBean bean = (ResultBean.ProductBean) getDatas().get(position);
            SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;
            viewHolder.tv_product_cost.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            viewHolder.tv_product_sold.setVisibility(View.GONE);
            viewHolder.iv_addtocart.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(bean.promotionPrice)) {
                viewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
            } else {
                if (bean.marketPrice != null) {
                    viewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_productcost_old.setVisibility(View.GONE);
                }
            }
          /*  RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.tv_product_cost.getLayoutParams();
            params.setMargins(PxUtils.dipTopx(12), PxUtils.dipTopx(8), 0, 0);
            RelativeLayout.LayoutParams paramsOldCost = (RelativeLayout.LayoutParams) viewHolder.tv_productcost_old.getLayoutParams();
            paramsOldCost.setMargins(PxUtils.dipTopx(12), PxUtils.dipTopx(12), 0, 0);*/
            if (showFlag == SearchResultActivity.LIST_TYPE) {
                //viewHolder.tv_product_cost.setLayoutParams(params);
                //viewHolder.tv_productcost_old.setLayoutParams(paramsOldCost);
            }
           // viewHolder.tv_product_praise.setVisibility(View.VISIBLE);
            //viewHolder.tv_praise.setVisibility(View.VISIBLE);
            //viewHolder.tv_product_praise_good.setVisibility(View.VISIBLE);
            //viewHolder.tv_praise_num.setVisibility(View.VISIBLE);

            viewHolder.iv_addtocart.setVisibility(View.VISIBLE);
            if (showFlag == SearchResultActivity.TAB_TYPE) {
                /*RelativeLayout.LayoutParams cartParams = (RelativeLayout.LayoutParams) viewHolder.iv_addtocart.getLayoutParams();
                cartParams.setMargins(0, 0, PxUtils.dipTopx(15), PxUtils.dipTopx(20));
                viewHolder.iv_addtocart.setLayoutParams(cartParams);*/
            }
            if (bean.commentInfo != null) {
                viewHolder.tv_praise_num.setText(bean.commentInfo.commentNum);
                viewHolder.tv_product_praise_good.setText(bean.commentInfo.goodRate + "%");
            }
            if (bean.titleIconUrls != null && bean.titleIconUrls.size() > 0){
                UiUtils.getStringSpan(viewHolder.tv_product_name, mContext, bean.titleIconUrls.get(0),bean.name);
            }else {
                viewHolder.tv_product_name.setText(bean.name.trim());
            }
        }
    }
}
