package com.ody.p2p.check.orderlist;

import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.BaseView;

/**
 * Created by ody on 2016/8/29.
 */
public interface EvaluateView extends BaseView{
    void showEvaluateInfo(EvaluateBean response);
    void commitEvaluate(BaseRequestBean response);
    void upLoadResult(UpLoadBean response,PhotoFileBean bean,int position);
    void remove(PhotoFileBean bean,int position);
    void skipToWeb(String url);
}
