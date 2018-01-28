package com.ody.p2p.productdetail.productdetail.frangment.productappraise;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;

/**
 * Created by ody on 2016/6/16.
 */
public interface ProductCommentView extends BaseView {
//    void selectEvaluate(AppariseBean response);

    void loadingError();



    void setCommentDate(ProductComment.Data data);

    void setErrorComment(String msg);



}
