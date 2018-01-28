package com.ody.p2p.main.productDetail;

import android.content.Context;
import android.view.View;

import com.ody.p2p.main.R;
import com.ody.p2p.productdetail.productdetail.frangment.ProductDetailWebFragment;

/**
 * Created by pzy on 2016/12/6.
 */
public class LyfProductWebFragment extends ProductDetailWebFragment {

    @Override
    public int bindLayout() {
        return R.layout.fragment_lyfproduct_detail_web;
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
        guigedeatilAdapter.setLayoutView(R.layout.item_guigelayout);
    }
}
