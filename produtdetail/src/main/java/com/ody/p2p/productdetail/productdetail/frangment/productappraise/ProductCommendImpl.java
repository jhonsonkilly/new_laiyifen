package com.ody.p2p.productdetail.productdetail.frangment.productappraise;

import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/8/3.
 */
public class ProductCommendImpl implements ProductCommendPresent {
    ProductCommentView aView;

    public ProductCommendImpl(ProductCommentView aView) {
        this.aView = aView;
    }


    /**
     * //评价数据
     */
    public void productComment(String mpId, int hasPic, long rateFlag, int pageNo, int pageSize) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mpId", mpId);
        params.put("hasPic", hasPic + "");    // 	是否有图 0:全部;1:有图;2:无图
        params.put("rateFlag", rateFlag + "");//0:全部；1:好评;2:中评;3:差评
        params.put("pageNo", pageNo + "");  //  页数
        params.put("pageSize", pageSize + "");//条数
        aView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_APPRAISE, params, new OkHttpManager.ResultCallback<ProductComment>() {

            @Override
            public void onNetError() {
                super.onNetError();
                aView.setCommentDate(null);
            }

            @Override
            public void onError(Request request, Exception e) {
                aView.hideLoading();
            }

            @Override
            public void onResponse(ProductComment response) {
                if (response != null && null != response.data) {
                    aView.setCommentDate(response.data);
                } else if (response != null && response.data == null) {

                    aView.setErrorComment(response.message);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                aView.hideLoading();
            }
        });

    }
}
