package com.ody.p2p.productdetail.productdetail.frangment;

import com.ody.p2p.productdetail.productdetail.bean.StandardBean;
import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/6/24.
 */
public interface ProductWebView extends BaseView {
    void standard(StandardBean bean);
    void loadingError();
}
